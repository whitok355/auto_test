package booker;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
public abstract class TestAbstractBooker {
    static Properties prop = new Properties();
    private static InputStream confFile;
    private static String BOOKER_USERNAME;
    private static String BOOKER_PASS;
    private static String BASE_URL;
    private static String AUTH_JSON;
    private static String CREATE_JSON;
    private  static String BOOKER_TOKEN;
    public static String getBookerUsername() {
        return BOOKER_USERNAME;
    }
    public static String getBookerPass() {
        return BOOKER_PASS;
    }
    public static String getBaseUrl() {
        return BASE_URL;
    }
    public static String getBookerToken() {
        return BOOKER_TOKEN;
    }
    @BeforeAll
    public static void initTest() throws IOException {
        confFile = new FileInputStream("src/test/resources/hw3.properties");
        prop.load(confFile);

        BASE_URL = prop.getProperty("BASE_URL_BOOKER");
        BOOKER_USERNAME = prop.getProperty("BOOKER_USERNAME");
        BOOKER_PASS = prop.getProperty("BOOKER_PASS");
        BOOKER_TOKEN = prop.getProperty("BOOKER_TOKEN");
    }
}