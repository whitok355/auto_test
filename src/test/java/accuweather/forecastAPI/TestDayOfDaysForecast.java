package accuweather.forecastAPI;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDayOfDaysForecast extends AbstractForecastTest{
    private static String Base_URL_FORECAST = "/forecasts/v1/daily/";
    private static String correctlyToken = "123";
    private static String unCorrectlyToken = "321";
    private static Logger logger = LoggerFactory.getLogger(TestDayOfDaysForecast.class);

    @Order(1)
    @Test
    public void check401() throws URISyntaxException, IOException {
        logger.info("Start test fall check401 in class" + this.getClass().getSimpleName());
        String msg = "authorization failed";
        String fullURL = Base_URL_FORECAST + "1day/1";

        stubFor(get(urlPathEqualTo(fullURL))
                .withQueryParam("apikey", equalTo(unCorrectlyToken))
                .willReturn(aResponse().withStatus(401).withBody(msg))
        );

        logger.debug("Mocke create");
        CloseableHttpClient clientHTTP = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseMockeUrl() + fullURL);

        URI getUri = new URIBuilder(request.getURI())
                .addParameter("apikey", unCorrectlyToken)
                .build();

        request.setURI(getUri);
        HttpResponse response = clientHTTP.execute(request);
        logger.debug("http create");
        System.out.println(request.getURI());
        verify(1, getRequestedFor(urlPathEqualTo(fullURL+"?apikey="+unCorrectlyToken)));

        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals(msg, convertResponseToString(response));
    }
    @Order(2)
    @Test
    public void check404() throws URISyntaxException, IOException {
        logger.info("Start test fall in class" + this.getClass().getSimpleName());
        String urlUncorrectlyDay = Base_URL_FORECAST + "asd/1";
        String urlUncorrectlyLocation = Base_URL_FORECAST + "1/asd";
        String msgUncorrectlyDay = "Uncorrectly day value";
        String msgUncorrectlyLocation = "Uncorrectly location value";

        stubFor(get(urlPathEqualTo(urlUncorrectlyDay))
                .withQueryParam("apikey", equalTo(correctlyToken))
                .willReturn(aResponse().withStatus(404).withBody(msgUncorrectlyDay))
        );

        logger.debug("Mock urlUncorrectlyDay create");

        stubFor(get(urlPathEqualTo(urlUncorrectlyLocation))
                .withQueryParam("apikey", equalTo(correctlyToken))
                .willReturn(aResponse().withStatus(404).withBody(msgUncorrectlyLocation))
        );

        CloseableHttpClient clientHttp = HttpClients.createDefault();

        HttpGet requestDay = new HttpGet(getBaseMockeUrl() + urlUncorrectlyDay);

        URI uriDay = new URIBuilder(requestDay.getURI())
                .addParameter("apikey", correctlyToken)
                .build();

        requestDay.setURI(uriDay);
        HttpResponse responseDay = clientHttp.execute(requestDay);
        logger.debug("Request urlUncorrectlyDay create");

        HttpGet requestLocation = new HttpGet(getBaseMockeUrl() +urlUncorrectlyLocation);

        URI uriLocation = new URIBuilder(requestLocation.getURI())
                .addParameter("apikey", correctlyToken)
                .build();

        requestDay.setURI(uriLocation);

        HttpResponse responseLocation = clientHttp.execute(requestDay);
        logger.debug("Request urlUncorrectlyLocation create");

        verify(1, getRequestedFor(urlPathEqualTo(urlUncorrectlyDay)));

        assertEquals(404, responseDay.getStatusLine().getStatusCode());
        assertEquals(msgUncorrectlyDay, convertResponseToString(responseDay));

        verify(1, getRequestedFor(urlPathEqualTo(urlUncorrectlyLocation)));

        assertEquals(404, responseLocation.getStatusLine().getStatusCode());
        assertEquals(msgUncorrectlyLocation, convertResponseToString(responseLocation));
    }

    @Order(3)
    @ParameterizedTest
    @ValueSource(strings={"1day", "10day", "5day", "15day"})
    public void check200(String quantityDays) throws URISyntaxException, IOException {
        logger.info("Start test fall in class" + this.getClass().getSimpleName());
        String fullUrl =Base_URL_FORECAST + quantityDays +"/1";
        String param = "apikey";

        stubFor(get(urlPathEqualTo(fullUrl))
                .withQueryParam(param, equalTo(correctlyToken))
                .willReturn(aResponse().withStatus(200).withBody(quantityDays))
        );
        logger.debug("Mock create");

        CloseableHttpClient httpCLient = HttpClients.createDefault();

        HttpGet request = new HttpGet(getBaseMockeUrl() + fullUrl);

        URI uriGet = new URIBuilder(request.getURI())
                .addParameter(param, correctlyToken)
                .build();

        request.setURI(uriGet);

        HttpResponse response = httpCLient.execute(request);
        logger.debug("HTTP create");

        verify(1, getRequestedFor(urlPathEqualTo(fullUrl)));

        assertEquals(200, response.getStatusLine().getStatusCode());

        assertEquals(quantityDays, convertResponseToString(response));
    }
}
