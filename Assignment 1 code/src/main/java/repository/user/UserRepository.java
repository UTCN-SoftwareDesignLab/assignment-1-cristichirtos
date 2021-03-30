package repository.user;

import model.entity.User;
import model.validation.Notification;

public interface UserRepository {

    Notification<User> findByEmailAndPassword(String username, String password);

    boolean save(User user);

    void removeAll();

    Notification<User> findByEmail(String email);

    Boolean update(String email, String password);

    Notification<Boolean> delete(String email);
}
