package repository.user;

import model.User;
import model.validation.Notification;
import repository.Cache;

import java.util.List;

public class UserRepositoryCacheDecorator extends UserRepositoryDecorator{
    private Cache<User> cache;
    public UserRepositoryCacheDecorator(UserRepository userRepository,Cache<User> cache) {
        super(userRepository);
        this.cache = cache;
    }
    @Override
    public List<User> findAll() {
        if(cache.hasResult()){
            return cache.load();
        }
        List<User> users = decoratorUserRepository.findAll();
        cache.save(users);
        return users;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        if(cache.hasResult()) {
            Notification<User> notification = new Notification<>();
            User found = cache.load().stream()
                    .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);
            if (found == null) {
                notification.addError("User not found");

            } else {
                notification.setResult(found);
            }
            return notification;
        }
            Notification<User> response = decoratorUserRepository.findByUsernameAndPassword(username, password);
            if (!response.hasError()) {
                cache.save(decoratorUserRepository.findAll());
            }
            return response;

    }

    @Override
    public Notification<User> save(User user) {
        cache.invalidateCache();
        return decoratorUserRepository.save(user);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratorUserRepository.removeAll();
    }

    @Override
    public Notification<Boolean> delete(Long id) {
        cache.invalidateCache();
        return decoratorUserRepository.delete(id);
    }

    @Override
    public Notification<Boolean> update(User user) {
        cache.invalidateCache();
        return decoratorUserRepository.update(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        if (cache.hasResult()) {
            return cache.load().stream()
                    .anyMatch(u -> u.getUsername().equals(username));
        }
        return decoratorUserRepository.existsByUsername(username);
    }
}
