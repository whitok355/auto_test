package accuweather.locationAPI;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestTopCitiesListWithMockWire extends AbstractLocationTest {
    private static Logger logger = LoggerFactory.getLogger(TestTopCitiesListWithMockWire.class);
    private static String token = "123456ythgfds";
    private static String msgOk = "Auth ok";
    private String advanced_url ="/locations/v1/topcities/50";

    @Test
    public void check200() throws IOException, URISyntaxException {
        logger.info("Тест кейс check200 для класса " + this.getClass().getSimpleName() + "запущен");
        String msg = "Ok";
        String tokenUrl = advanced_url + "?apikey=" +token;

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("apikey", WireMock.equalTo(token))
                .willReturn(WireMock.aResponse().withStatus(200).withBody(msg))
        );
        logger.debug("Mock create");
        CloseableHttpClient http_client = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + advanced_url);

        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("apikey", token)
                .build();

        request.setURI(uriOk);
        HttpResponse response = http_client.execute(request);
        logger.debug("HTTP create");
        WireMock.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo(tokenUrl)));

        Assertions.assertEquals(200, response.getStatusLine().getStatusCode());
        Assertions.assertEquals(msg, convertResponseToString(response));

    }

    @ParameterizedTest
    @CsvSource( value ={
            "apikey, 123as"
        }
    )
    void check401(String key, String token) throws URISyntaxException, IOException {
        logger.info("Test keys check401 start by class" + this.getClass().getSimpleName());
        String msg = "Auth error";
        String tokenUrl = advanced_url + "?"+key+"=" + token;

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam(key, WireMock.equalTo(token))
                .willReturn(WireMock.aResponse().withStatus(401).withBody(msg))
        );
        logger.info("Mock created");
        CloseableHttpClient clientHTTP = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + advanced_url);

        URI getUri = new URIBuilder(request.getURI())
                .addParameter(key, token)
                .build();

        request.setURI(getUri);

        HttpResponse response = clientHTTP.execute(request);
        logger.debug("http send");
        WireMock.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo(tokenUrl)));

        Assertions.assertEquals(401, response.getStatusLine().getStatusCode());
        Assertions.assertEquals(msg, convertResponseToString(response));
    }

    @Test
    void check404() throws URISyntaxException, IOException {
        logger.info("Test keys check404 start by class" + this.getClass().getSimpleName());
        String msg = "Request err";
        String newAdvancedUrl = advanced_url +1;
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(newAdvancedUrl))
                .withQueryParam("apikey", WireMock.equalTo(token))
                .willReturn(WireMock.aResponse().withStatus(404).withBody(msg))
        );
        logger.info("Mock created");
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseUrl() + newAdvancedUrl);

        URI uriGet = new URIBuilder(request.getURI())
                .addParameter("apikey", token)
                .build();

        request.setURI(uriGet);

        HttpResponse response = httpClient.execute(request);
        logger.debug("http send");
        WireMock.verify(1, WireMock.getRequestedFor(WireMock.urlPathEqualTo(newAdvancedUrl)));

        Assertions.assertEquals(404, response.getStatusLine().getStatusCode());
        Assertions.assertEquals(msg, convertResponseToString(response));
    }
}
