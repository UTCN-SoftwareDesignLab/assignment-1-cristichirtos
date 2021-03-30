package controller;

import launcher.ComponentFactory;
import model.Authentication;
import model.DTO.CredentialsDTO;
import model.entity.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.*;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final Authentication authentication;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, Authentication authentication) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.authentication = authentication;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CredentialsDTO credentialsDTO = loginView.getCredentialsDTO();

            Notification<User> loginNotification = authenticationService.login(credentialsDTO);

            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                authentication.setCurrentUserId(loginNotification.getResult().getId());
                loginView.setVisible(false);
                if (loginNotification.getResult().getRoles().get(0).getRole().equals(EMPLOYEE)) {
                    ComponentFactory.instance(false).getEmployeeView().setVisible(true);
                } else {
                    ComponentFactory.instance(false).getAdministratorView().setVisible(true);
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CredentialsDTO credentialsDTO = loginView.getCredentialsDTO();

            Notification<Boolean> registerNotification = authenticationService.register(credentialsDTO);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }
}
