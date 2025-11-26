package service.admin;

import database.Constants;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.user.AuthenticationService;

import java.util.Collections;
import java.util.List;

public class UserManagementServiceImpl implements UserManagementService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final AuthenticationService authenticationService;

    public UserManagementServiceImpl(UserRepository userRepository,
                                     RightsRolesRepository rightsRolesRepository,
                                     AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Notification<Boolean> createUser(String username, String password, String roleName) {

        Notification<Boolean> notif = new Notification<>();

        Role role = rightsRolesRepository.findRoleByTitle(roleName);
        if (role == null) {
            notif.addError("Invalid role!");
            notif.setResult(false);
            return notif;
        }

        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(authenticationService.encodePassword(password))
                .setRoles(Collections.singletonList(role))
                .build();

        Notification<User> saveNotif = userRepository.save(user);

        if (saveNotif.hasError()) {
            saveNotif.getErrors().forEach(notif::addError);
            notif.setResult(false);
            return notif;
        }

        notif.setResult(true);
        return notif;
    }

    @Override
    public Notification<Boolean> deleteUser(Long id) {
        return userRepository.delete(id);
    }
    @Override
    public List<User> findAllCustomers() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getRole().equalsIgnoreCase("customer")))
                .toList();
    }
    @Override
    public Notification<Boolean> update(User user) {
        return userRepository.update(user);
    }
    @Override
    public Role findRoleByName(String roleName) {
        return rightsRolesRepository.findRoleByTitle(roleName);
    }



}
