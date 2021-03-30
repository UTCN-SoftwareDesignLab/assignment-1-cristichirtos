package service.user;

import model.DTO.CredentialsDTO;
import model.builder.UserBuilder;
import model.entity.Role;
import model.entity.User;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import static database.Constants.Roles.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(CredentialsDTO credentialsDTO) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setEmail(credentialsDTO.getEmail())
                .setPassword(credentialsDTO.getPassword())
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(credentialsDTO.getPassword()));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(CredentialsDTO credentialsDTO) {
        return userRepository.findByEmailAndPassword(credentialsDTO.getEmail(), encodePassword(credentialsDTO.getPassword()));
    }

    @Override
    public Notification<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Notification<Boolean> updateUser(CredentialsDTO credentialsDTO) {
        Notification<Boolean> notification = new Notification<>();
        Notification<User> userNotification = findUserByEmail(credentialsDTO.getEmail());
        if (userNotification.hasErrors()) {
            notification.addError(userNotification.getFormattedErrors());
            return notification;
        }
        User user = userNotification.getResult();
        user.setPassword(credentialsDTO.getPassword());
        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            userRegisterNotification.setResult(userRepository.update(user.getEmail(), encodePassword(user.getPassword())));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<Boolean> deleteUser(String email) {
        return userRepository.delete(email);
    }

    @Override
    public String getStringFromUser(User user) {
        return "User id: " + user.getId() +
                "\nUser email: " + user.getEmail() +
                "\nUser role: " + user.getRoles().get(0).getRole();
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
