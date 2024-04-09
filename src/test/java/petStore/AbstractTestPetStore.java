package petStore;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTestPetStore {
    private static Properties prop = new Properties();
    private static String BASE_URL;
    private static InputStream configFile;
    private static String BASE_LOGIN;
    private static String BASE_PASS;
    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getBaseLogin() {
        return BASE_LOGIN;
    }

    public static String getBasePass() {
        return BASE_PASS;
    }

    @BeforeAll
    public static void initTest() throws IOException{
        configFile = new FileInputStream("src/test/resources/hw3.properties");
        prop.load(configFile);

        BASE_URL = prop.getProperty("BASE_URL_PETSTORE");
        BASE_LOGIN = prop.getProperty("BASE_LOGIN_PETSTORE");
        BASE_PASS = prop.getProperty("BASE_PASS_PETSTORE");

    }

}
