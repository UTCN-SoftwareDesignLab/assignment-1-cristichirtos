package view;

import model.DTO.CredentialsDTO;
import model.DTO.ReportDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdministratorView extends JFrame {

    private JLabel lInstructions;
    private JLabel lReportsInstructions;
    private JLabel lEmployeeEmail;
    private JLabel lEmployeePassword;
    private JLabel lEmployeeId;
    private JLabel lReportsPeriodStartDate;
    private JLabel lReportsPeriodEndDate;

    private JTextField tfEmployeeEmail;
    private JTextField tfEmployeePassword;
    private JTextField tfEmployeeId;
    private JTextField tfReportsPeriodStartDate;
    private JTextField tfReportsPeriodEndDate;

    private JButton btnViewEmployee;
    private JButton btnCreateEmployee;
    private JButton btnUpdateEmployee;
    private JButton btnDeleteEmployee;
    private JButton btnGenerateReports;

    public AdministratorView() throws HeadlessException {
        setTitle("Bank App - Admin");
        setSize(500, 400);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(lInstructions);

        add(lEmployeeEmail);
        add(tfEmployeeEmail);
        add(lEmployeePassword);
        add(tfEmployeePassword);

        add(btnViewEmployee);
        add(btnCreateEmployee);
        add(btnUpdateEmployee);
        add(btnDeleteEmployee);

        add(lReportsInstructions);

        add(lEmployeeId);
        add(tfEmployeeId);
        add(lReportsPeriodStartDate);
        add(tfReportsPeriodStartDate);
        add(lReportsPeriodEndDate);
        add(tfReportsPeriodEndDate);

        add(btnGenerateReports);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        btnViewEmployee = new JButton("View Employee");
        btnCreateEmployee = new JButton("Create Employee");
        btnUpdateEmployee = new JButton("Update Employee");
        btnDeleteEmployee = new JButton("Delete Employee");
        btnGenerateReports = new JButton("Generate reports");

        lInstructions = new JLabel("View, Update, Delete: input employee email (unmodifiable)");
        lReportsInstructions = new JLabel("Input the id of the employee and two dates in yyyy-mm-dd format.");
        lEmployeeEmail = new JLabel("Employee email: ");
        lEmployeePassword = new JLabel("Employee password: ");
        lEmployeeId = new JLabel("Employee id: ");
        lReportsPeriodStartDate = new JLabel("Start date for reports:");
        lReportsPeriodEndDate = new JLabel("End date for reports:");

        tfEmployeeEmail = new JTextField();
        tfEmployeePassword = new JTextField();
        tfEmployeeId = new JTextField();
        tfReportsPeriodStartDate = new JTextField();
        tfReportsPeriodEndDate = new JTextField();
    }

    public void setViewEmployeeButtonListener(ActionListener viewEmployeeButtonListener) {
        btnViewEmployee.addActionListener(viewEmployeeButtonListener);
    }

    public void setCreateEmployeeButtonListener(ActionListener createEmployeeButtonListener) {
        btnCreateEmployee.addActionListener(createEmployeeButtonListener);
    }

    public void setUpdateEmployeeButtonListener(ActionListener updateEmployeeButtonListener) {
        btnUpdateEmployee.addActionListener(updateEmployeeButtonListener);
    }

    public void setDeleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener) {
        btnDeleteEmployee.addActionListener(deleteEmployeeButtonListener);
    }

    public void setGenerateReportsButtonListener(ActionListener generateReportsButtonListener) {
        btnGenerateReports.addActionListener(generateReportsButtonListener);
    }

    public CredentialsDTO getCredentialsDTO() {
        return new CredentialsDTO(tfEmployeeEmail.getText(), tfEmployeePassword.getText());
    }

    public ReportDTO getReportDTO() {
        return new ReportDTO(tfEmployeeId.getText(), tfReportsPeriodStartDate.getText(), tfReportsPeriodEndDate.getText());
    }

    public String getEmployeeEmail() {
        return tfEmployeeEmail.getText();
    }
}
