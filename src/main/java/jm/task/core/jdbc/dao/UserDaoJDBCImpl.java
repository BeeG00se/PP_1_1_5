package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl implements UserDao {
//    private static final  = Util.connection();//todo: комментарии кода - не стоит оставлять

    //todo: прописываем общий на класс Connection connection и инициализируем через constructor, из Util

    //todo: выносим аналогично с Util константы ... и именуем их согласно codeStyle, например:
    private static final String createUsersQuery = "create table User (" +
            "Id int, Name varchar(256), Lastname varchar(256), Age int" +
            ")";//todo: запрос (нужно посмотреть к-нибудь пример) - заглавными символами операторы.. IF NOT EXISTS уже вижу не хватает

    public UserDaoJDBCImpl() {//todo: пустой constructor зачем?

    }

    public void createUsersTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {//todo: Statement - в качестве ресурса в try_with_resources
            connection = Util.connect();
            statement = connection.createStatement();
            statement.executeUpdate(createUsersQuery);
        } catch (Exception e) {
            System.out.println(e.getMessage());//todo: описал ситуацию в Util
        } finally {//todo: избавимся c введением - try_with_resources
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public void dropUsersTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.connect();
            statement = connection.createStatement();
            statement.executeUpdate("drop table if exists user");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        try {
            connection = Util.connect();
            PreparedStatement sql = connection.prepareStatement("INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)");
            sql.setString(1, name);
            sql.setString(2, lastName);
            sql.setByte(3, age);
            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        try {
            connection = Util.connect();
            PreparedStatement sql = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            sql.setLong(1, id);
            sql.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List <User> users = new ArrayList<>();
        Connection connection = null;
        try {
            connection = Util.connect();
            Statement sql = connection.createStatement();
            sql.execute("select * from user");
            ResultSet re = sql.getResultSet();
            while (re.next()) {
                String name = re.getString("name");
                String lastName = re.getString("lastName");
                byte age = re.getByte("age");
                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection connection = null;
        try {
            connection = Util.connect();
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
