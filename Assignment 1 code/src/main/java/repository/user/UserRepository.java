package repository.user;

import model.entity.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    Notification<User> findByEmailAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();
}
