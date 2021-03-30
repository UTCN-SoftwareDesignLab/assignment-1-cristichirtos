package service.account;

import model.DTO.AccountDTO;
import model.DTO.BillDTO;
import model.DTO.TransferDTO;
import model.builder.AccountBuilder;
import model.entity.Account;
import model.entity.Client;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.time.LocalDate;

public class AccountServiceMySQL implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountServiceMySQL(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Boolean> createAccount(AccountDTO accountDTO) {
        Notification<Boolean> notification = new Notification<>();
        Client client = clientRepository.findByPersonalNumericalCode(accountDTO.getOwnerPersonalNumericalCode());
        if (client == null) {
            notification.addError("Error retrieving client with PNC = " + accountDTO.getOwnerPersonalNumericalCode());
        } else {
            AccountValidator accountValidator = new AccountValidator(accountDTO);
            boolean accountValid = accountValidator.validate();

            if (!accountValid) {
                accountValidator.getErrors().forEach(notification::addError);
                notification.setResult(Boolean.FALSE);
            } else {
                Account account = new AccountBuilder()
                        .setClientId(client.getId())
                        .setType(accountDTO.getType())
                        .setBalance(Long.parseLong(accountDTO.getBalance()))
                        .setCreationTimestamp(LocalDate.now())
                        .build();
                if (!accountRepository.save(account)) {
                    notification.addError("Failed to create account. It might already exist.");
                } else {
                    notification.setResult(true);
                }
            }
        }
        return notification;
    }

    @Override
    public Notification<Account> getAccountByClientPersonalNumericalCode(Long clientPNC) {
        Notification<Account> notification = new Notification<>();
        Client client = clientRepository.findByPersonalNumericalCode(clientPNC);
        if (client == null) {
            notification.addError("Failed to retrieve client");
        } else {
            Account account = accountRepository.findByClientId(client.getId());
            if (account == null) {
                notification.addError("Failed to retrieve account");
            } else {
                notification.setResult(account);
            }
        }
        return notification;
    }

    @Override
    public Notification<Boolean> updateAccount(AccountDTO accountDTO) {
        Notification<Boolean> notification = new Notification<>();
        Notification<Account> accountNotification = getAccountByClientPersonalNumericalCode(accountDTO.getOwnerPersonalNumericalCode());
        if (accountNotification.hasErrors()) {
            notification.addError(accountNotification.getFormattedErrors());
        } else {
            Account account = accountNotification.getResult();
            if (!accountDTO.getBalance().isEmpty()) account.setBalance(Long.parseLong(accountDTO.getBalance()));
            account.setType(accountDTO.getType());
            notification.setResult(accountRepository.update(account));
        }
        return notification;
    }

    @Override
    public Notification<Boolean> deleteAccountByClientPersonalNumericalCode(Long clientPNC) {
        Notification<Boolean> notification = new Notification<>();
        Notification<Account> accountNotification = getAccountByClientPersonalNumericalCode(clientPNC);
        if (accountNotification.hasErrors()) {
            notification.addError(accountNotification.getFormattedErrors());
        } else {
            notification.setResult(accountRepository.deleteAccount(accountNotification.getResult().getId()));
        }
        return notification;
    }

    @Override
    public Notification<Boolean> transferMoney(TransferDTO transferDTO) {
        Notification<Boolean> notification = new Notification<>();
        if (transferDTO.getSenderPersonalNumericalCode().isEmpty() || transferDTO.getReceiverPersonalNumericalCode().isEmpty() || transferDTO.getAmount().isEmpty()) {
            notification.addError("Invalid accounts or balance");
            return notification;
        }
        Notification<Account> senderAccountNotification = getAccountByClientPersonalNumericalCode(Long.parseLong(transferDTO.getSenderPersonalNumericalCode()));
        Notification<Account> receiverAccountNotification = getAccountByClientPersonalNumericalCode(Long.parseLong(transferDTO.getReceiverPersonalNumericalCode()));
        if (senderAccountNotification.hasErrors()) {
            notification.addError(senderAccountNotification.getFormattedErrors());
            return notification;
        }
        if (receiverAccountNotification.hasErrors()) {
            notification.addError(receiverAccountNotification.getFormattedErrors());
            return notification;
        }
        Account senderAccount = senderAccountNotification.getResult();
        Account receiverAccount = receiverAccountNotification.getResult();
        if (senderAccount.getBalance() < Long.parseLong(transferDTO.getAmount())) {
            notification.addError("Sender does not have enough money");
        } else {
            transferDTO.setSenderAccountId(senderAccount.getId());
            transferDTO.setReceiverAccountId(receiverAccount.getId());
            notification.setResult(accountRepository.transferMoney(transferDTO));
        }
        return notification;
    }

    @Override
    public Notification<Boolean> processBill(BillDTO billDTO) {
        Notification<Boolean> notification = new Notification<>();
        if (billDTO.getClientPNC().isEmpty() || billDTO.getAmount().isEmpty()) {
            notification.addError("Invalid client PNC or amount");
            return notification;
        }
        Notification<Account> accountNotification = getAccountByClientPersonalNumericalCode(Long.parseLong(billDTO.getClientPNC()));
        if (accountNotification.hasErrors()) {
            notification.addError(accountNotification.getFormattedErrors());
        } else {
            Account account = accountNotification.getResult();
            if (account.getBalance() < Long.parseLong(billDTO.getAmount())) {
                notification.addError("Client does not have enough money");
            } else {
                account.setBalance(account.getBalance() - Long.parseLong(billDTO.getAmount()));
                notification.setResult(accountRepository.update(account));
            }
        }
        return notification;
    }

    @Override
    public String getStringFromAccount(Account account) {
        return ("Account id: " + account.getId() +
                "\nOwner id: " + account.getClientId() +
                "\nBalance: " + account.getBalance() +
                "\nType: " + account.getType().name() +
                "\nCreation date: " + account.getCreationTimestamp().toString());
    }
}
