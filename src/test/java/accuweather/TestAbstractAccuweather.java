package accuweather;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class TestAbstractAccuweather {
    static Properties prop = new Properties();
    private static String API_KEY;
    private static InputStream configFile;
    private static String BASE_URL;
    public static String getApiKey() {
        return API_KEY;
    }
    public static String getBaseUrl() {
        return BASE_URL;
    }
    @BeforeAll
    static void initTest () throws IOException {
        configFile = new FileInputStream("src/test/resources/hw3.properties");
        prop.load(configFile);

        API_KEY = prop.getProperty("ACCUWEATHER_API_KEY");
        BASE_URL = prop.getProperty("BASE_URL_ACCUWEATHER");
    }
}
