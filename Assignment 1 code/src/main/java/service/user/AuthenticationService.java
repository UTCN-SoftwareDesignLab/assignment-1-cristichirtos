package service.user;

import model.entity.User;
import model.validation.Notification;

public interface AuthenticationService {
    Notification<Boolean> register(String username, String password);

    Notification<User> login(String username, String password);
}
