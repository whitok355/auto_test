package accuweather.forecastAPI;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
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

@Epic("Тестирование проекта accuweather")
@Feature("Тестирование Hourly API")
@Story("Получение объекта с информацией о погоде за 1,12,120,24, 72 часа")
@Owner("Я")
public class TestHourlyForecast extends AbstractForecastTest{
    private static String Base_URL_FORECAST = "/forecasts/v1/hourly/";
    private static String correctlyToken = "123";
    private static String unCorrectlyToken = "321";
    private static Logger logger = LoggerFactory.getLogger(TestHourlyForecast.class);

    @Test
    @DisplayName("Test TestHourlyForecast" )
    @Description("check401")
    @Link("https://developer.accuweather.com/apis")
    @Severity(SeverityLevel.CRITICAL)
    public void check401() throws URISyntaxException, IOException {
        logger.info("Start test fall check401 in class" + this.getClass().getSimpleName());
        String msg = "authorization failed";
        String fullURL = Base_URL_FORECAST +"1hour/1";

        stubFor(get(urlPathEqualTo(fullURL))
                .withQueryParam("apikey", equalTo(unCorrectlyToken))
                .willReturn(aResponse().withStatus(401).withBody(msg))
        );

        logger.debug("Mocke create");
        CloseableHttpClient clientHTTP = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://" + getBaseMockHost()+":" + getBaseMockPort() + fullURL);

        URI getUri = new URIBuilder(request.getURI())
                .addParameter("apikey", unCorrectlyToken)
                .build();

        request.setURI(getUri);
        HttpResponse response = clientHTTP.execute(request);
        logger.debug("http create");

        assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals(msg, convertResponseToString(response));
    }

    @Test
    @DisplayName("Test TestHourlyForecast" )
    @Description("check404")
    @Link("https://developer.accuweather.com/apis")
    @Severity(SeverityLevel.BLOCKER)
    public void check404() throws URISyntaxException, IOException {
        logger.info("Start test fall in class" + this.getClass().getSimpleName());
        String urlUncorrectlyHour = Base_URL_FORECAST + "asd/1";
        String urlUncorrectlyLocation = Base_URL_FORECAST + "1/asd";
        String msgUncorrectlyHour = "Uncorrectly hour value";
        String msgUncorrectlyLocation = "Uncorrectly location value";

        stubFor(get(urlPathEqualTo(urlUncorrectlyHour))
                .withQueryParam("apikey", equalTo(correctlyToken))
                .willReturn(aResponse().withStatus(404).withBody(msgUncorrectlyHour))
        );

        logger.debug("Mock urlUncorrectlyDay create");

        stubFor(get(urlPathEqualTo(urlUncorrectlyLocation))
                .withQueryParam("apikey", equalTo(correctlyToken))
                .willReturn(aResponse().withStatus(404).withBody(msgUncorrectlyLocation))
        );

        CloseableHttpClient clientHttp = HttpClients.createDefault();

        HttpGet requestDay = new HttpGet(getBaseMockeUrl() + urlUncorrectlyHour);

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

        verify(1, getRequestedFor(urlPathEqualTo(urlUncorrectlyHour)));

        assertEquals(404, responseDay.getStatusLine().getStatusCode());
        assertEquals(msgUncorrectlyHour, convertResponseToString(responseDay));

        verify(1, getRequestedFor(urlPathEqualTo(urlUncorrectlyLocation)));

        assertEquals(404, responseLocation.getStatusLine().getStatusCode());
        assertEquals(msgUncorrectlyLocation, convertResponseToString(responseLocation));
    }

    @ParameterizedTest
    @ValueSource(strings={"1hour", "12hour", "120hour", "24hour", "72hour"})
    @DisplayName("Test TestHourlyForecast" )
    @Description("check200")
    @Link("https://developer.accuweather.com/apis")
    @Severity(SeverityLevel.CRITICAL)
    public void check200(String hours) throws URISyntaxException, IOException {
        logger.info("Start test fall in class" + this.getClass().getSimpleName());
        String fullUrl =Base_URL_FORECAST + hours +"/1";
        String param = "apikey";

        stubFor(get(urlPathEqualTo(fullUrl))
                .withQueryParam(param, equalTo(correctlyToken))
                .willReturn(aResponse().withStatus(200).withBody(hours))
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

        assertEquals(hours, convertResponseToString(response));
    }
}
