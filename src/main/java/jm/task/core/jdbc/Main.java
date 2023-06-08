package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("John", "Connor", (byte) 17);
        userService.saveUser("Jonathan", "Wick", (byte) 38);
        userService.saveUser("Peter", "Packer", (byte) 22);
        userService.saveUser("Ethan", "Hunt", (byte) 29);
        Thread.sleep(2500);
        userService.removeUserById(1);
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            System.out.println(user.toString());
        }
        Thread.sleep(7000);
        userService.cleanUsersTable();
        Thread.sleep(2500);
        userService.dropUsersTable();
    }
}
