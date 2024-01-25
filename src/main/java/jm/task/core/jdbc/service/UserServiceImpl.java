package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao;
public UserServiceImpl() {
//    userDao  = new UserDaoJDBCImpl();
    userDao = new UserDaoHibernateImpl();
}

    public void createUsersTable() throws SQLException {
        userDao.createUsersTable();
        System.out.println("Таблица создана");
    }

    public void dropUsersTable() throws SQLException {
        userDao.dropUsersTable();
        System.out.println("Таблица удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        System.out.println("User с id – " + id + " удален из базы данных");
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        System.out.println("Таблица очищена");
    }
}
