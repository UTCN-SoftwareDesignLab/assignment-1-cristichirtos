package view;

import model.entity.Account;

import javax.swing.*;

import java.awt.*;

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

    private JSpinner spAccountType;

    private JLabel lClientName;
    private JLabel lClientPersonalNumericalCode;
    private JLabel lClientIdentityCardNumber;
    private JLabel lClientAddress;
    private JLabel lClientPhone;

    private JLabel lAccountType;

    private JLabel lAmount;
    private JLabel lSenderPNC;
    private JLabel lReceiverPNC;

    public EmployeeView() throws HeadlessException {
        setTitle("Bank App - Employee");
        setSize(400, 800);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

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

        add(lAccountType);
        add(spAccountType);

        add(btnViewAccount);
        add(btnCreateAccount);
        add(btnUpdateAccount);
        add(btnDeleteAccount);

        add(lAmount);
        add(tfAmount);
        add(lSenderPNC);
        add(tfSenderPNC);
        add(lReceiverPNC);
        add(tfReceiverPNC);

        add(btnTransferMoney);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnViewClient = new JButton("View Client");
        btnCreateClient = new JButton("Create Client");
        btnUpdateClient = new JButton("Update Client");
        btnDeleteClient = new JButton("Delete Account");

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

        spAccountType = new JSpinner(new SpinnerListModel(Account.Type.values()));
        ((JSpinner.DefaultEditor) spAccountType.getEditor()).getTextField().setEditable(false);

        lClientName = new JLabel("Client name: ");
        lClientPersonalNumericalCode = new JLabel("Client PNC: ");
        lClientIdentityCardNumber = new JLabel("Client id card no: ");
        lClientAddress = new JLabel("Client address: ");
        lClientPhone = new JLabel("Client phone no: ");

        lAccountType = new JLabel("Account type: ");

        lAmount = new JLabel("Amount to transfer: ");
        lSenderPNC = new JLabel("Sender PNC: ");
        lReceiverPNC = new JLabel("Receiver PNC: ");
    }
}
