package controller;

import model.DTO.ActivityDTO;
import model.DTO.CredentialsDTO;
import model.DTO.ReportDTO;
import model.entity.Activity;
import model.entity.Client;
import model.entity.User;
import model.validation.Notification;
import service.activity.ActivityService;
import service.user.AuthenticationService;
import view.AdministratorView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorController {

    private final AdministratorView administratorView;
    private final AuthenticationService authenticationService;
    private final ActivityService activityService;

    public AdministratorController(AdministratorView administratorView, AuthenticationService authenticationService, ActivityService activityService) {
        this.administratorView = administratorView;
        this.authenticationService = authenticationService;
        this.activityService = activityService;
        administratorView.setViewEmployeeButtonListener(new ViewEmployeeListener());
        administratorView.setCreateEmployeeButtonListener(new CreateEmployeeListener());
        administratorView.setUpdateEmployeeButtonListener(new UpdateEmployeeListener());
        administratorView.setDeleteEmployeeButtonListener(new DeleteEmployeeListener());
        administratorView.setGenerateReportsButtonListener(new GenerateReportsListener());
    }

    private class ViewEmployeeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeEmail = administratorView.getEmployeeEmail();
            Notification<User> viewEmployeeNotification = authenticationService.findUserByEmail(employeeEmail);
            if (viewEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), viewEmployeeNotification.getFormattedErrors());
            } else {
                User employee = viewEmployeeNotification.getResult();
                JOptionPane.showMessageDialog(administratorView.getContentPane(), authenticationService.getStringFromUser(employee));
            }
        }
    }

    private class CreateEmployeeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CredentialsDTO credentialsDTO = administratorView.getCredentialsDTO();

            Notification<Boolean> createEmployeeNotification = authenticationService.register(credentialsDTO);

            if (createEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), createEmployeeNotification.getFormattedErrors());
            } else {
                if (!createEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Failed to create employee.");
                } else {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee created successfully.");
                }
            }
        }
    }

    private class UpdateEmployeeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CredentialsDTO credentialsDTO = administratorView.getCredentialsDTO();

            Notification<Boolean> updateEmployeeNotification = authenticationService.updateUser(credentialsDTO);

            if (updateEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), updateEmployeeNotification.getFormattedErrors());
            } else {
                if (!updateEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Failed to update employee.");
                } else {
                    JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee updated successfully.");
                }
            }
        }
    }

    private class DeleteEmployeeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String employeeEmail = administratorView.getEmployeeEmail();
            Notification<Boolean> deleteEmployeeNotification = authenticationService.deleteUser(employeeEmail);
            if (deleteEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), deleteEmployeeNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), "Employee deleted successfully.");
            }
        }
    }

    private class GenerateReportsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ReportDTO reportDTO = administratorView.getReportDTO();
            Notification<String> generateReportsNotification = activityService.getActivities(reportDTO);
            if (generateReportsNotification.hasErrors()) {
                JOptionPane.showMessageDialog(administratorView.getContentPane(), generateReportsNotification.getFormattedErrors());
            } else {
                JTextArea textArea = new JTextArea(generateReportsNotification.getResult());
                JScrollPane scrollPane = new JScrollPane(textArea);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
                JOptionPane.showMessageDialog(null, scrollPane, "Activity Report", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
