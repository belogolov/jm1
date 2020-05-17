package dao;

import model.Role;
import model.User;
import util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserJdbcDAO implements UserDAO {

    @Override
    public List<User> getAllUsers() {
        Connection connection = DBHelper.getInstance().getConnection();
        List<User> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            if (stmt.execute("SELECT * FROM users")) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        list.add(new User(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getUserById(long id) {
        Connection connection = DBHelper.getInstance().getConnection();
        User user = null;
        try (Statement stmt = connection.createStatement()) {
            if (stmt.execute("SELECT * FROM users WHERE id='" + id + "'")) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        user = new User(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        Connection connection = DBHelper.getInstance().getConnection();
        User user = null;
        try (Statement stmt = connection.createStatement()) {
            if (stmt.execute("SELECT * FROM users WHERE email='" + login + "'")) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        user = new User(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void addUser(User user) throws SQLException {
        Connection connection = DBHelper.getInstance().getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO users (name, email, password) " +
                    "VALUES ('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "')");
        }
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        deleteUserById(user.getId());
    }

    @Override
    public void updateUser(User user) throws SQLException {
        Connection connection = DBHelper.getInstance().getConnection();
        try (PreparedStatement  stmt = connection.prepareStatement("UPDATE users SET name=?, email=?, password=? WHERE id=?")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setLong(4, user.getId());
            stmt.execute();
        }
    }

    public void deleteUserById(Long id) throws SQLException {
        Connection connection = DBHelper.getInstance().getConnection();
        try (PreparedStatement  stmt = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    @Override
    public boolean isValidUser(String login, String password) throws SQLException {
        Connection connection = DBHelper.getInstance().getConnection();
        User user = null;
        try (Statement stmt = connection.createStatement()) {
            if (stmt.execute("SELECT * FROM users WHERE email='" + login+"'")) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        user = new User(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    @Override
    public Set<Role> getUserRoles(User user) {
        Connection connection = DBHelper.getInstance().getConnection();
        Set<Role> roles = new HashSet<>();
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT r.* FROM users_roles ur INNER JOIN roles r ON ur.role_id = r.id WHERE user_id='" + user.getId() + "'";
            if (stmt.execute(sql)) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        roles.add(new Role(rs));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    @Override
    public Role getRoleById(long id) {
        Connection connection = DBHelper.getInstance().getConnection();
        Role role = null;
        try (Statement stmt = connection.createStatement()) {
            if (stmt.execute("SELECT * FROM roles WHERE id='" + id + "'")) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        role = new Role(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}
