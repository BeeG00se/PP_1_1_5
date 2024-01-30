package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/myschema?useSSL=false";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";


    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() throws HibernateException {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, USER_NAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("We are connected");
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Не удалось подключиться к БД" + e.getMessage());
        }
    }
}
