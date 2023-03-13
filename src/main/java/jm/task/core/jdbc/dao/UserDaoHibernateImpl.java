package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

  private static final String createUsersTableDdl = "CREATE TABLE IF NOT EXISTS User (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(64), lastName VARCHAR(64), age TINYINT)";
  private static final String dropUsersTableDdl = "DROP TABLE IF EXISTS User";
  private static final String cleanUsersTableDml = "DELETE FROM User";

  public UserDaoHibernateImpl() {

  }


  @Override
  public void createUsersTable() {
    Transaction transaction = null;
    try (Session session = Util.getSession()) {
      transaction = session.beginTransaction();
      session.createNativeQuery(createUsersTableDdl).executeUpdate();
      transaction.commit();
      System.out.println("create users Table");
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void dropUsersTable() {
    Transaction transaction = null;
    try (Session session = Util.getSession()) {
      transaction = session.beginTransaction();
      session.createNativeQuery(dropUsersTableDdl).executeUpdate();
      transaction.commit();
      System.out.println("drop users table");
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    Transaction transaction = null;
    try (Session session = Util.getSession()) {
      transaction = session.beginTransaction();
      User user = new User(name, lastName, age);
      session.save(user);
      transaction.commit();
      System.out.println("save user " + name + lastName + age);
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();

      }
      e.printStackTrace();
    }
  }

  @Override
  public void removeUserById(long id) {
    Transaction transaction = null;
    try (Session session = Util.getSession()) {
      transaction = session.beginTransaction();
      User user = session.get(User.class, id);
      if (user != null) {
        session.delete(user);
        transaction.commit();
        System.out.println("remove user iy id " + id);
      } else {
        System.out.println("user by Id " + id + " not found");
      }
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    try (Session session = Util.getSession()) {
      users = session.createQuery("FROM User").list();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    Transaction transaction = null;
    try (Session session = Util.getSession()) {
      transaction = session.beginTransaction();
      session.createQuery(cleanUsersTableDml).executeUpdate();
      transaction.commit();
      System.out.println("clean users table");
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }
}
