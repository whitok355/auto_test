package accuweather.forecastAPI;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public abstract class AbstractForecastTest {
    private static WireMockServer wireMockServer = new WireMockServer();
    private static Logger logger = LoggerFactory.getLogger(AbstractForecastTest.class);
    private static String BASE_MOCK_HOST = "localhost";
    private static int BASE_MOCK_PORT = 8080;
    private static String BASE_MOCKE_URL;

    public static String getBaseMockHost() {
        return BASE_MOCK_HOST;
    }

    public static int getBaseMockPort() {
        return BASE_MOCK_PORT;
    }

    @BeforeAll
    public static void init(){
        logger.info("Start init Tests in Abstract");
        BASE_MOCKE_URL = "http://" + BASE_MOCK_HOST +":" + BASE_MOCK_PORT;
        wireMockServer.start();
        WireMock.configureFor(BASE_MOCK_HOST, BASE_MOCK_PORT);

        logger.info("Abstract class init and working");
    }

    public static String getBaseMockeUrl() {
        return BASE_MOCKE_URL;
    }

    public String convertResponseToString(HttpResponse response) throws IOException {
        try(InputStream inpStream = response.getEntity().getContent();
            Scanner scanner = new Scanner(inpStream, "utf-8");
        ) {
            String responseString = scanner.useDelimiter("\\Z").next();
            return responseString;
        }
    }

    @AfterAll
    public static void close(){
        wireMockServer.stop();
        logger.info("Abstract class stope");
    }

}
