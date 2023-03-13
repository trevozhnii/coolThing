package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.service.UserService;
public class Main {
    public static void main(String[] args) {

        UserService usi = new UserServiceImpl();
        usi.createUsersTable();
        usi.saveUser("Vasya", "Pupkin", (byte) 18);
        usi.saveUser("Sonya", "Svetaya", (byte) 21);
        usi.saveUser("Sveta", "Kikimorova", (byte) 45);
        usi.saveUser("Nikita", "Prekrasnii", (byte) 20);
        usi.getAllUsers();
        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
