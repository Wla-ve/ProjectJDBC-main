package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    private void Begin(String SQL) {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users(Id SERIAL PRIMARY KEY, " +
                "name VARCHAR(200) NOT NULL," +
                " lastName VARCHAR(200) NOT NULL, " +
                "age SMALLINT NOT NULL)";
        Begin(SQL);
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE users";
        Begin(SQL);
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement("INSERT INTO users VALUES (NOT NULL,?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE ID = id";
        Begin(SQL);
    }

    public List<User> getAllUsers() {
        List<User> usb = new ArrayList<User>();
        try (Statement preparedStatement = Util.getConnection().createStatement()) {
            ResultSet set = preparedStatement.executeQuery("SELECT * FROM users");
            while (set.next()) {
                String name = set.getString("name");
                String lastName = set.getString("lastName");
                byte age = set.getByte("age");
                User user = new User(name, lastName, age);
                usb.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usb;
    }

    public void cleanUsersTable() {
        String SQL = "DELETE FROM users";
        Begin(SQL);
    }
}
