import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractTest {

    private static SessionFactory ourSessionFactors;
    private static Connection connection;
    private static FileInputStream config;
    private static Properties prop = new Properties();
    private static String BASE_URL;
    @BeforeAll
    public static void initMethod(){
        ourSessionFactors = createOurSessionFactory();
        connection = createConnection();
    }
    private static SessionFactory createOurSessionFactory(){
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
    }
    private static Connection createConnection() {
        try{
            config = new FileInputStream("src/test/resources/properties.properties");
            prop.load(config);
            BASE_URL = prop.getProperty("url");
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(BASE_URL);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(SQLException e) {
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Session getOpenSession() {
        return ourSessionFactors.openSession();
    }
    public static Connection getConnection() {
        return connection;
    }

    @AfterAll
    public static void closeMethod() {
        ourSessionFactors.close();
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
