package repository.user;
import model.User;
import model.builder.UserBuilder;
//import model.validator.Notification;
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

    //TODO implementare
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

    //TODO de modificat pentru SQL INJECTION!!!
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM `" + USER + "` WHERE `username` = ? AND `password` = ? LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password); // ATENTIE: daca parolele sunt hash-uite, trimite aici hashPassword(password)
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserBuilder()
                            .setId(rs.getLong("id"))
                            .setUsername(rs.getString("username"))
                            .setPassword(rs.getString("password"))
                            .setRoles(rightsRolesRepository.findRolesForUser(rs.getLong("id")))
                            .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    //TODO sql injection!!?
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

