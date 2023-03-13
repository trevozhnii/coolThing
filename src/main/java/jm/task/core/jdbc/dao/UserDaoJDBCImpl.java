package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private static final String createUsersTableDdl = "CREATE TABLE IF NOT EXISTS Users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(64), lastName VARCHAR(64), age TINYINT)";
    private static final String dropUsersTableDdl = "DROP TABLE IF EXISTS Users";
    private static final String saveUserDml = "INSERT INTO Users (name, lastName,age ) VALUES(?,?,?)";
    private static final String removeUserByIdDml = "DELETE FROM Users WHERE id = ?";
    private static final String getAllUsersDql = "SELECT id, name, lastName, age FROM Users";
    private static final String cleanUsersTableDml = "DELETE FROM Users";
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createUsersTableDdl);
                System.out.println("create Users Table");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(dropUsersTableDdl);
                System.out.println("drop Users Table");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(saveUserDml)) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
                System.out.println("save User: " + name + " " + lastName + " " + age + " age");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(removeUserByIdDml)) {
                statement.setLong(1, id);
                statement.executeUpdate();
                System.out.println("remove User By Id " + id);
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getAllUsersDql)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setLastName(resultSet.getString("lastName"));
                    user.setAge(resultSet.getByte("age"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(cleanUsersTableDml)) {
                statement.executeUpdate();
                System.out.println("clean Users Table");
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
