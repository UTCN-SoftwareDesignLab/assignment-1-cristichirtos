package service.account;

import model.DTO.AccountDTO;
import model.DTO.BillDTO;
import model.DTO.TransferDTO;
import model.entity.Account;
import model.validation.Notification;

public interface AccountService {

    Notification<Boolean> createAccount(AccountDTO accountDTO);

    Notification<Account> getAccountByClientPersonalNumericalCode(Long clientPNC);

    Notification<Boolean> updateAccount(AccountDTO account);

    Notification<Boolean> deleteAccountByClientPersonalNumericalCode(Long clientPNC);

    Notification<Boolean> transferMoney(TransferDTO transferDTO);

    String getStringFromAccount(Account account);

    Notification<Boolean> processBill(BillDTO billDTO);
}
