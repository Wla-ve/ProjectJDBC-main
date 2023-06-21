package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import lombok.Value;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String HOST = "jdbc:mysql://localhost:3306/mytest";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(HOST, LOGIN, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", HOST);
            prop.setProperty("hibernate.connection.username", LOGIN);
            prop.setProperty("hibernate.connection.password", PASSWORD);
            prop.setProperty("dialect", DIALECT);
            sessionFactory = new org.hibernate.cfg.Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (HibernateException e) {
            System.out.println("Неверное подключение к БД");
        }
    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

}
