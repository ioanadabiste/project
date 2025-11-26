package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();
    Notification<User> findByUsernameAndPassword(String username, String password);
    Notification<User> save(User user);
    void removeAll();
    Notification<Boolean> delete(Long id);
    Notification<Boolean> update(User user);
    boolean existsByUsername(String username);
}
