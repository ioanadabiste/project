package service.admin;

import model.Role;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserManagementService {
    public List<User> findAll();
    public Notification<Boolean> createUser(String username, String password, String roleName);
    public Notification<Boolean> deleteUser(Long id);
    List<User> findAllCustomers();
    Notification<Boolean> update(User user);
    Role findRoleByName(String roleName);

}
