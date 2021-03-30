package view;

import model.DTO.AccountDTO;
import model.DTO.BillDTO;
import model.DTO.ClientDTO;
import model.DTO.TransferDTO;
import model.entity.Account;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends JFrame {

    private JButton btnViewClient;
    private JButton btnCreateClient;
    private JButton btnUpdateClient;
    private JButton btnDeleteClient;

    private JButton btnViewAccount;
    private JButton btnCreateAccount;
    private JButton btnUpdateAccount;
    private JButton btnDeleteAccount;

    private JButton btnTransferMoney;

    private JButton btnProcessBill;

    private JTextField tfClientName;
    private JTextField tfClientPersonalNumericalCode;
    private JTextField tfClientIdentityCardNumber;
    private JTextField tfClientAddress;
    private JTextField tfClientPhone;

    private JTextField tfAmount;
    private JTextField tfSenderPNC;
    private JTextField tfReceiverPNC;

    private JTextField tfBalance;

    private JTextField tfBillPayerPNC;
    private JTextField tfBillAmount;

    private JSpinner spAccountType;

    private JLabel lClientInstructions;
    private JLabel lAccountInstructions;
    private JLabel lTransferInstructions;
    private JLabel lProcessBillInstructions;

    private JLabel lClientName;
    private JLabel lClientPersonalNumericalCode;
    private JLabel lClientIdentityCardNumber;
    private JLabel lClientAddress;
    private JLabel lClientPhone;

    private JLabel lBalance;
    private JLabel lAccountType;

    private JLabel lAmount;
    private JLabel lSenderPNC;
    private JLabel lReceiverPNC;

    private JLabel lBillPayerPNC;
    private JLabel lBillAmount;

    public EmployeeView() throws HeadlessException {
        setTitle("Bank App - Employee");
        setSize(600, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(lClientInstructions);

        add(lClientPersonalNumericalCode);
        add(tfClientPersonalNumericalCode);
        add(lClientName);
        add(tfClientName);
        add(lClientIdentityCardNumber);
        add(tfClientIdentityCardNumber);
        add(lClientAddress);
        add(tfClientAddress);
        add(lClientPhone);
        add(tfClientPhone);

        add(btnViewClient);
        add(btnCreateClient);
        add(btnUpdateClient);
        add(btnDeleteClient);

        add(lAccountInstructions);

        add(lBalance);
        add(tfBalance);
        add(lAccountType);
        add(spAccountType);

        add(btnViewAccount);
        add(btnCreateAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);

        add(lTransferInstructions);

        add(lAmount);
        add(tfAmount);
        add(lSenderPNC);
        add(tfSenderPNC);
        add(lReceiverPNC);
        add(tfReceiverPNC);

        add(btnTransferMoney);

        add(lProcessBillInstructions);

        add(lBillPayerPNC);
        add(tfBillPayerPNC);
        add(lBillAmount);
        add(tfBillAmount);

        add(btnProcessBill);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnViewClient = new JButton("View Client");
        btnCreateClient = new JButton("Create Client");
        btnUpdateClient = new JButton("Update Client");
        btnDeleteClient = new JButton("Delete Client");

        btnViewAccount = new JButton("View Account");
        btnCreateAccount = new JButton("Create Account");
        btnUpdateAccount = new JButton("Update Account");
        btnDeleteAccount = new JButton("Delete Account");

        btnTransferMoney = new JButton("Transfer Money");
        btnProcessBill = new JButton("Process Bill");

        tfClientName = new JTextField();
        tfClientPersonalNumericalCode = new JTextField();
        tfClientIdentityCardNumber = new JTextField();
        tfClientAddress = new JTextField();
        tfClientPhone = new JTextField();

        tfAmount = new JTextField();
        tfSenderPNC = new JTextField();
        tfReceiverPNC = new JTextField();

        tfBalance = new JTextField();

        tfBillPayerPNC = new JTextField();
        tfBillAmount = new JTextField();

        spAccountType = new JSpinner(new SpinnerListModel(Account.Type.values()));
        ((JSpinner.DefaultEditor) spAccountType.getEditor()).getTextField().setEditable(false);

        lClientInstructions = new JLabel("View, Update, Delete: input a PNC(unmodifiable) to access corresponding client");
        lAccountInstructions = new JLabel("Account operations: input a PNC for an existing client. Only balance and type can be modified.");
        lTransferInstructions = new JLabel("Transfer money: input sender and receiver PNCs and amount.");
        lProcessBillInstructions = new JLabel("Process bill: input client PNC and amount for bill.");

        lClientName = new JLabel("Client name: ");
        lClientPersonalNumericalCode = new JLabel("Client PNC: ");
        lClientIdentityCardNumber = new JLabel("Client id card no: ");
        lClientAddress = new JLabel("Client address: ");
        lClientPhone = new JLabel("Client phone no: ");

        lBalance = new JLabel("Account balance: ");
        lAccountType = new JLabel("Account type: ");

        lAmount = new JLabel("Amount to transfer: ");
        lSenderPNC = new JLabel("Sender PNC: ");
        lReceiverPNC = new JLabel("Receiver PNC: ");

        lBillPayerPNC = new JLabel("PNC of billed client: ");
        lBillAmount = new JLabel("Amount of bill: ");
    }

    public void setViewClientButtonListener(ActionListener viewClientButtonListener) {
        btnViewClient.addActionListener(viewClientButtonListener);
    }

    public void setCreateClientButtonListener(ActionListener createClientButtonListener) {
        btnCreateClient.addActionListener(createClientButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener) {
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setDeleteClientButtonListener(ActionListener deleteClientButtonListener) {
        btnDeleteClient.addActionListener(deleteClientButtonListener);
    }

    public void setViewAccountButtonListener(ActionListener viewAccountButtonListener) {
        btnViewAccount.addActionListener(viewAccountButtonListener);
    }

    public void setCreateAccountButtonListener(ActionListener createAccountButtonListener) {
        btnCreateAccount.addActionListener(createAccountButtonListener);
    }

    public void setUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        btnUpdateAccount.addActionListener(updateAccountButtonListener);
    }

    public void setDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        btnDeleteAccount.addActionListener(deleteAccountButtonListener);
    }

    public void setTransferMoneyButtonListener(ActionListener transferMoneyButtonListener) {
        btnTransferMoney.addActionListener(transferMoneyButtonListener);
    }

    public void setProcessBillButtonListener(ActionListener processBillButtonListener) {
        btnProcessBill.addActionListener(processBillButtonListener);
    }

    public String getClientPersonalNumericalCode() {
        return tfClientPersonalNumericalCode.getText();
    }

    public String getSenderPersonalNumericalCode() {
        return tfSenderPNC.getText();
    }

    public ClientDTO getClientDTO() {
        return new ClientDTO(tfClientName.getText(), Long.parseLong(tfClientPersonalNumericalCode.getText()),
                tfClientIdentityCardNumber.getText(), tfClientAddress.getText(), tfClientPhone.getText());
    }

    public AccountDTO getAccountDTO() {
        return new AccountDTO(Long.parseLong(tfClientPersonalNumericalCode.getText()),
                (Account.Type) spAccountType.getValue(), tfBalance.getText());
    }

    public TransferDTO getTransferDTO() {
        return new TransferDTO(tfAmount.getText(), tfSenderPNC.getText(), tfReceiverPNC.getText());
    }

    public BillDTO getBillDTO() {
        return new BillDTO(tfBillPayerPNC.getText(), tfBillAmount.getText());
    }
}
