package controller;

import model.Authentication;
import model.DTO.*;
import model.builder.ActivityBuilder;
import model.entity.Account;
import model.entity.Activity;
import model.entity.Client;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final AccountService accountService;
    private final ClientService clientService;
    private final ActivityService activityService;
    private final Authentication authentication;

    public EmployeeController(EmployeeView employeeView, AccountService accountService, ClientService clientService,
                              ActivityService activityService, Authentication authentication) {
        this.employeeView = employeeView;
        this.accountService = accountService;
        this.clientService = clientService;
        this.activityService = activityService;
        this.authentication = authentication;
        employeeView.setViewClientButtonListener(new ViewClientListener());
        employeeView.setCreateClientButtonListener(new CreateClientListener());
        employeeView.setUpdateClientButtonListener(new UpdateClientListener());
        employeeView.setDeleteClientButtonListener(new DeleteClientListener());
        employeeView.setViewAccountButtonListener(new ViewAccountListener());
        employeeView.setCreateAccountButtonListener(new CreateAccountListener());
        employeeView.setUpdateAccountButtonListener(new UpdateAccountListener());
        employeeView.setDeleteAccountButtonListener(new DeleteAccountListener());
        employeeView.setTransferMoneyButtonListener(new TransferMoneyListener());
        employeeView.setProcessBillButtonListener(new ProcessBillListener());
    }

    private class ViewClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientPersonalNumericalCode = employeeView.getClientPersonalNumericalCode();
            if (clientPersonalNumericalCode.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                Notification<Client> viewClientNotification = clientService.findClientByPersonalNumericalCode(Long.parseLong(clientPersonalNumericalCode));
                if (viewClientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), viewClientNotification.getFormattedErrors());
                } else {
                    Client client = viewClientNotification.getResult();
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Viewed client " + client.getId() + " data");
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), clientService.getStringFromClient(client));
                }
            }
        }
    }

    private class CreateClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeeView.getClientPersonalNumericalCode().isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                ClientDTO clientDTO = employeeView.getClientDTO();
                Notification<Boolean> createClientNotification = clientService.createClient(clientDTO);
                if (createClientNotification.hasErrors() || !createClientNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), createClientNotification.getFormattedErrors());
                } else {
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Created client for PNC " + clientDTO.getPersonalNumericalCode());
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client created successfully.");
                }
            }
        }
    }

    private class UpdateClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeeView.getClientPersonalNumericalCode().isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                ClientDTO clientDTO = employeeView.getClientDTO();
                Notification<Boolean> updateClientNotification = clientService.updateClient(clientDTO);
                if (updateClientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), updateClientNotification.getFormattedErrors());
                } else {
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Updated client for PNC " + clientDTO.getPersonalNumericalCode());
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client updated successfully.");
                }
            }
        }
    }

    private class DeleteClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientPersonalNumericalCode = employeeView.getClientPersonalNumericalCode();
            if (clientPersonalNumericalCode.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                Notification<Boolean> deleteClientNotification = clientService.deleteClientByPersonalNumericalCode(Long.parseLong(clientPersonalNumericalCode));
                if (deleteClientNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), deleteClientNotification.getFormattedErrors());
                } else {
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Deleted client with PNC " + clientPersonalNumericalCode);
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client deleted successfully.");
                }
            }
        }
    }

    private class ViewAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientPersonalNumericalCode = employeeView.getClientPersonalNumericalCode();
            if (clientPersonalNumericalCode.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                Notification<Account> viewAccountNotification = accountService.getAccountByClientPersonalNumericalCode(Long.parseLong(clientPersonalNumericalCode));
                if (viewAccountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), viewAccountNotification.getFormattedErrors());
                } else {
                    Account account = viewAccountNotification.getResult();
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Viewed account " + account.getId() + " data");
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), accountService.getStringFromAccount(account));
                }
            }
        }
    }

    private class CreateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeeView.getClientPersonalNumericalCode().isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                AccountDTO accountDTO = employeeView.getAccountDTO();
                Notification<Boolean> createAccountNotification = accountService.createAccount(accountDTO);
                if (createAccountNotification.hasErrors() || !createAccountNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), createAccountNotification.getFormattedErrors());
                } else {
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Created account for client with PNC " + accountDTO.getOwnerPersonalNumericalCode());
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account created successfully.");
                }
            }
        }
    }

    private class UpdateAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (employeeView.getClientPersonalNumericalCode().isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                AccountDTO accountDTO = employeeView.getAccountDTO();
                Notification<Boolean> updateAccountNotification = accountService.updateAccount(accountDTO);
                if (updateAccountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), updateAccountNotification.getFormattedErrors());
                } else {
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Updated account for client with PNC " + accountDTO.getOwnerPersonalNumericalCode());
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account updated successfully.");
                }
            }
        }
    }

    private class DeleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String clientPersonalNumericalCode = employeeView.getClientPersonalNumericalCode();
            if (clientPersonalNumericalCode.isEmpty()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Please enter a client PNC");
            } else {
                Notification<Boolean> deleteAccountNotification = accountService.deleteAccountByClientPersonalNumericalCode(Long.parseLong(clientPersonalNumericalCode));
                if (deleteAccountNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), deleteAccountNotification.getFormattedErrors());
                } else {
                    ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                            "Deleted account for client with PNC " + clientPersonalNumericalCode);
                    activityService.addActivity(activityDTO);
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account deleted successfully.");
                }
            }
        }
    }

    private class TransferMoneyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TransferDTO transferDTO = employeeView.getTransferDTO();
            Notification<Boolean> transferMoneyNotification = accountService.transferMoney(transferDTO);
            if (transferMoneyNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), transferMoneyNotification.getFormattedErrors());
            } else {
                ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                        "Transferred money from client with PNC " + transferDTO.getSenderPersonalNumericalCode()
                                + " to client with PNC " + transferDTO.getReceiverPersonalNumericalCode());
                activityService.addActivity(activityDTO);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Money transferred successfully.");
            }
        }
    }

    private class ProcessBillListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            BillDTO billDTO = employeeView.getBillDTO();
            Notification<Boolean> processBillNotification = accountService.processBill(billDTO);
            if (processBillNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), processBillNotification.getFormattedErrors());
            } else {
                ActivityDTO activityDTO = new ActivityDTO(authentication.getCurrentUserId(),
                        "Processed bill for client with PNC " + billDTO.getClientPNC());
                activityService.addActivity(activityDTO);
                JOptionPane.showMessageDialog(employeeView.getContentPane(), "Bill processed successfully.");
            }
        }
    }
}
