package repository.security;

import model.Right;
import model.Role;
import model.User;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.RIGHT;
import static database.Constants.Tables.ROLE;
import static database.Constants.Tables.ROLE_RIGHT;
import static database.Constants.Tables.USER_ROLE;
//AICI SQL INJECTION!!
public class RightsRolesRepositoryMySQL implements RightsRolesRepository {

    private final Connection connection;

    public RightsRolesRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ROLE + " values (null, ?)");
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public void addRight(String right) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + RIGHT + "` values (null, ?)");
            insertStatement.setString(1, right);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public Role findRoleByTitle(String role) {
        String sql = "SELECT * FROM " + ROLE + " WHERE `role` = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long roleId = rs.getLong("id");
                    String roleTitle = rs.getString("role");
                    return new Role(roleId, roleTitle, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Role findRoleById(Long roleId) {
        String sql = "SELECT * FROM " + ROLE + " WHERE `id` = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String roleTitle = rs.getString("role");
                    return new Role(roleId, roleTitle, null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Right findRightByTitle(String right) {
        String sql = "SELECT * FROM `" + RIGHT + "` WHERE `right` = ? LIMIT 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, right);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long rightId = rs.getLong("id");
                    String rightTitle = rs.getString("right");
                    return new Right(rightId, rightTitle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addRolesToUser(User user, List<Role> roles) {
        try {
            for (Role role : roles) {
                PreparedStatement insertUserRoleStatement = connection
                        .prepareStatement("INSERT INTO `user_role` values (null, ?, ?)");
                insertUserRoleStatement.setLong(1, user.getId());
                insertUserRoleStatement.setLong(2, role.getId());
                insertUserRoleStatement.executeUpdate();
            }
        } catch (SQLException e) {

        }
    }

    @Override
    public List<Role> findRolesForUser(Long userId) {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM " + USER_ROLE + " WHERE `user_id` = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    long roleId = rs.getLong("role_id");
                    roles.add(findRoleById(roleId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public void addRoleRight(Long roleId, Long rightId) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ROLE_RIGHT + " values (null, ?, ?)");
            insertStatement.setLong(1, roleId);
            insertStatement.setLong(2, rightId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}