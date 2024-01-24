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

    //todo: константы выносим из тела метода, например:
    private static final String DB_URL = "jdbc:mysql://localhost:3306/myschema?useSSL=false";




    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() throws HibernateException {//todo: избавляемся от static (так бы ломаем парадигму ООП)
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
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

    public static Connection connect() throws ClassNotFoundException {//todo: избавляемся от static (так бы ломаем парадигму ООП)
        String userName = "root";
        String password = "root";
//        String connectionURL = "jdbc:mysql://localhost:3306/myschema";//todo: нет необходимости.. подставляем сразу
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection connection = DriverManager.getConnection(DB_URL, userName, password);
            System.out.println("We are connected");
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("...." + e.getMessage());//todo: роняем приложение, где дальнейшая работа не целесообразна (везде ..просмотреть по коду)
        }
    }
}
