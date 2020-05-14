package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO implements UserDAO {
    private Connection connection;

    public UserJdbcDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> getAllUsers() {
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
        try (PreparedStatement  stmt = connection.prepareStatement("UPDATE users SET name=?, email=?, password=? WHERE id=?")) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setLong(4, user.getId());
            stmt.execute();
        }
    }

    public void deleteUserById(Long id) throws SQLException {
        try (PreparedStatement  stmt = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

    public void createTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE if not exists users (id bigint auto_increment, name varchar(256), email varchar(256), password varchar(256), primary key (id))");
        }
    }

    public void dropTable() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
        }
    }

}
