package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    Connection connection;
    private static final String CREATE_USERS_QUERY = "CREATE TABLE IF NOT EXISTS  USER (" +
            "Id int, Name varchar(256), Lastname varchar(256), Age int" +
            ")";
    private static final String DROP_USERS_QUERY = "DROP TABLE IF EXISTS USER";
    private static final String SAVE_USERS_QUERY = "INSERT INTO USER (name, lastName, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USERS_QUERY = "DELETE FROM USER WHERE id = ?";
    private static final String GET_ALL_USERS_QUERY = "SELECT * FROM USER";
    private static final String CLEAN_USERS_QUERY = "TRUNCATE TABLE USER";


    public UserDaoJDBCImpl() {
        Util util = new Util();
        connection = util.connect();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_USERS_QUERY);
        } catch (Exception e) {
            throw new RuntimeException("Не получилось создать таблицу" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_USERS_QUERY);
        } catch (Exception e) {
            throw new RuntimeException("Не получилось удалить таблицу" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement sql = connection.prepareStatement(SAVE_USERS_QUERY)) {
            sql.setString(1, name);
            sql.setString(2, lastName);
            sql.setByte(3, age);
            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement sql = connection.prepareStatement(REMOVE_USERS_QUERY)) {
            sql.setLong(1, id);
            sql.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Не получилось удалить User по id" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement sql = connection.createStatement()) {
            sql.execute(GET_ALL_USERS_QUERY);
            ResultSet re = sql.getResultSet();
            while (re.next()) {
                String name = re.getString("name");
                String lastName = re.getString("lastName");
                byte age = re.getByte("age");
                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("Не получилось получить всех Users" + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_QUERY);
        } catch (Exception e) {
            throw new RuntimeException("Не получилось очистить таблицу" + e.getMessage());
        }
    }
}
