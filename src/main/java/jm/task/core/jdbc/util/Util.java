package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection connect() throws ClassNotFoundException {
        String userName = "root";
        String password = "root";
        String connectionURL = "jdbc:mysql://localhost:3306/myschema";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(connectionURL, userName, password);
            System.out.println("We are connected");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
