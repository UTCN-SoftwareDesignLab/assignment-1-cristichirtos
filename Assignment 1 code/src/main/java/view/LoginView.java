package view;

import model.DTO.CredentialsDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame {

    private JTextField tfEmail;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginView() throws HeadlessException {
        setTitle("Bank App");
        setSize(300, 150);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfEmail);
        add(tfPassword);
        add(btnLogin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfEmail = new JTextField();
        tfPassword = new JTextField();
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
    }

    public CredentialsDTO getCredentialsDTO() {
        return new CredentialsDTO(tfEmail.getText(), tfPassword.getText());
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }
}
