package repository.user;
import model.User;
import model.builder.UserBuilder;
//import model.validator.Notification;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;
import static java.util.Collections.singletonList;

public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM `" + USER + "`";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User user = new UserBuilder()
                        .setId(rs.getLong("id"))
                        .setUsername(rs.getString("username"))
                        .setPassword(rs.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
                        .build();
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // SQL Injection Attacks should not work after fixing functions
    // Be careful that the last character in sql injection payload is an empty space
    // alexandru.ghiurutan95@gmail.com' and 1=1; --
    // ' or username LIKE '%admin%'; --

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification();
        String sql = "SELECT * FROM `" + USER + "` WHERE `username` = ? AND `password` = ? LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                 if(rs.next()) {
                    User user= new UserBuilder()
                            .setId(rs.getLong("id"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
                            .build();
                    findByUsernameAndPasswordNotification.setResult(user);
                }
                 else{
                     findByUsernameAndPasswordNotification.addError("Invalid username or password!");
                     return findByUsernameAndPasswordNotification;
                 }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database!");
        }

        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<User> save(User user) {
        Notification<User> saveNotification = new Notification();
        try {
            if(existsByUsername(user.getUsername())){
                saveNotification.addError("Username already exists!");
                return saveNotification;
            }
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user VALUES (null, ?, ?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2,user.getPassword());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                long userId = rs.getLong(1);
                user.setId(userId);
            }

            rightsRolesRepository.addRolesToUser(user,user.getRoles());

            saveNotification.setResult(user);
            return saveNotification;
        }
        catch(SQLException e){
            saveNotification.addError("Something is wrong with the Database!");
            return saveNotification;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsByUsername(String email) {

            String sql = "SELECT 1 FROM `" + USER + "` WHERE `username` = ? LIMIT 1";

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    }

