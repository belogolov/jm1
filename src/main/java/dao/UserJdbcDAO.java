package dao;

import model.User;
import util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        User client = null;
        try (Statement stmt = connection.createStatement()) {
            if (stmt.execute("SELECT * FROM users WHERE id='" + id + "'")) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        client = new User(rs);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
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

}
