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

    private static final String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS user " +
            "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
            "age TINYINT NOT NULL)";
    private static final String DROP_USERS_QUERY = "DROP TABLE IF EXISTS User";
    private static final String GET_ALL_USERS_QUERY = "FROM User";
    private static final String CLEAN_USERS_QUERY = "TRUNCATE TABLE User";


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(CREATE_USERS_QUERY).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не получилось создать таблицу" + e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(DROP_USERS_QUERY).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не получилось удалить таблицу" + e.getMessage());
        }
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
            throw new RuntimeException("Не получилось удалить User по id" + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(GET_ALL_USERS_QUERY).list();
        } catch (HibernateException | NoResultException e) {
            throw new RuntimeException("Не получилось получить всех Users" + e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery(CLEAN_USERS_QUERY).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException("Не получилось очистить таблицу" + e.getMessage());
        }
    }
}
