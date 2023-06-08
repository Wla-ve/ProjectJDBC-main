package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSession();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(45) NOT NULL, lastName CHAR(45) NOT NULL, age MEDIUMINT NOT NULL, PRIMARY KEY(id) )")
                    .addEntity(User.class);
            query.executeUpdate();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSession();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            query.executeUpdate();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name, lastName, age);
            Session session = Util.getSession();
            session.save(user);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getSession();
            session.get(User.class, id);
            session.delete(User.class);
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = Util.getSession().createQuery("FROM User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getSession();
            Query query = session.createSQLQuery("DELETE FROM users").addEntity(User.class);
            query.executeUpdate();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
