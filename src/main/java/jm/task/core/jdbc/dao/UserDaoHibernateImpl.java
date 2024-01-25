package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        Util util = new Util();
        sessionFactory = util.getSessionFactory();
    }

    private static final String createUsersQuery = "CREATE TABLE IF NOT EXISTS user " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";
    private static final String dropUsersQuery = "DROP TABLE IF EXISTS User";
    private static final String getAllUsersQuery = "FROM User";
    private static final String cleanQuery = "TRUNCATE TABLE User";


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(createUsersQuery).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(dropUsersQuery).addEntity(User.class);
        query.executeUpdate();
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transss = null;
        try (Session session = sessionFactory.openSession()) {
            transss = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transss.commit();
        } catch (RuntimeException e) {
            transss.rollback();
            throw e;
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = (User) session.find(User.class, id);
            session.remove(user);
        } catch (HibernateException | NoResultException e) {
            throw new RuntimeException("...." + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(getAllUsersQuery).list();
        } catch (HibernateException | NoResultException e) {
            throw new RuntimeException("...." + e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createSQLQuery(cleanQuery).addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }
}
