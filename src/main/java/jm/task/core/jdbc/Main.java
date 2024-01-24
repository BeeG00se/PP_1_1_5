package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {

    //todo: просьба протестить обе реализации. Commit - это рабочий функционал, прошедший тесты

    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Иван", "Обломов", (byte) 36);
        userService.saveUser("Анатолий", "Булгаков", (byte) 37);
        userService.saveUser("Апостол", "Пётр", (byte) 38);
        userService.saveUser("Пушка", "Сашкин", (byte) 3);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
