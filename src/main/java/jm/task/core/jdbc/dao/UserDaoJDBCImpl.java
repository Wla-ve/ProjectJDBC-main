package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(45) NOT NULL, lastName CHAR(45) NOT NULL, age MEDIUMINT NOT NULL, PRIMARY KEY(id) )");
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute("DROP TABLE users");
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("INSERT INTO users VALUES (NOT NULL,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            int a = (int) id;
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setInt(1, a);
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usb = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement("SELECT * FROM users");
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {
                String name = set.getString("name");
                String lastName = set.getString("lastName");
                byte age = (byte) set.getInt("age");
                User user = new User(name, lastName, age);
                usb.add(user);
            }
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usb;
    }

    public void cleanUsersTable() {
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM users");
            Util.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
