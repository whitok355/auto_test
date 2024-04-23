package accuweather.locationAPI;

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

public class AbstractLocationTest {
    private static WireMockServer wireMockServer = new WireMockServer();
    private static final int port = 8080;
    private static String baseMockUrl;
    private static final Logger logger
            = LoggerFactory.getLogger(AbstractLocationTest.class);
    @BeforeAll
    static void startServer() {
        baseMockUrl = "http://localhost:" + port;
        wireMockServer.start();
        WireMock.configureFor("localhost", port);
        logger.info("Start WireMockServer on port {}",port);
    }
    //Вспомогательный метод - конвертор body to string
    public String convertResponseToString(HttpResponse response) throws IOException {
        logger.debug("convertResponseToString method call");
        try(InputStream responseStream = response.getEntity().getContent();
            Scanner scanner = new Scanner(responseStream, "UTF-8");) {
            String responseString = scanner.useDelimiter("\\Z").next();
            return responseString;
        }

    }
    public static String getBaseUrl() {
        return baseMockUrl;
    }
    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
        logger.info("Stop WireMockServer");
    }
}