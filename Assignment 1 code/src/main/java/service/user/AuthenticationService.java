package service.user;

import model.DTO.CredentialsDTO;
import model.entity.User;
import model.validation.Notification;

public interface AuthenticationService {
    Notification<Boolean> register(CredentialsDTO credentialsDTO);

    Notification<User> login(CredentialsDTO credentialsDTO);
}
