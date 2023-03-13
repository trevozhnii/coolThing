package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

  public static final String USER_NAME = "root";
  public static final String PASSWORD = "rootroot";
  public static final String URL = "jdbc:mysql://localhost:3306/mysql";

  public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  public static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
  public static SessionFactory sessionFactory;


  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }


  static {
    try {
      Properties properties = new Properties();
      properties.setProperty("hibernate.connection.driver_class", DRIVER);
      properties.setProperty("hibernate.connection.url", URL);
      properties.setProperty("hibernate.connection.username", USER_NAME);
      properties.setProperty("hibernate.connection.password", PASSWORD);
      properties.setProperty("hibernate.dialect", DIALECT);
      Configuration configuration = new Configuration();
      configuration.setProperties(properties);
      configuration.addAnnotatedClass(User.class);
      sessionFactory = configuration.buildSessionFactory();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public static Session getSession() {

    return sessionFactory.openSession();
  }

}
