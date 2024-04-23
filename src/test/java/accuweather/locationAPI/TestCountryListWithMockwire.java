package accuweather.locationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.accuweather.Country;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCountryListWithMockwire extends AbstractLocationTest {

    private static final Logger logger = LoggerFactory.getLogger(TestCountryListWithMockwire.class);
    @ParameterizedTest
    @ValueSource(strings = "/locations/v1/countries/autocomplete")
    public void check200(String ADVANCED_URL) throws IOException, URISyntaxException {
        logger.info("Кейс check200 класса" + this.getClass().getSimpleName() + "запущен");
        ObjectMapper mapper = new ObjectMapper();
        Country countryOk = new Country();
        countryOk.setLocalizedName("Ok");

        Country countryErr = new Country();
        countryErr.setLocalizedName("Error");

        logger.debug("Формирование мока для GET " + ADVANCED_URL);

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(ADVANCED_URL))
                .withQueryParam("apiKey", WireMock.equalTo("RU"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody(mapper.writeValueAsString(countryOk))));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(ADVANCED_URL))
                .withQueryParam("apiKey", WireMock.equalTo("Error"))
                .willReturn(WireMock.aResponse()
                        .withStatus(400)
                        .withBody(mapper.writeValueAsString(countryErr))));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        logger.debug("http клиент создан");

        HttpGet request = new HttpGet(getBaseUrl()+ADVANCED_URL);
        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("apiKey", "RU")
                .build();

        request.setURI(uriOk);
        HttpResponse responseOk = httpClient.execute(request);

        logger.debug("Отправка позитивного запроса по адресу" + request.getURI());

        URI uriErr = new URIBuilder(request.getURI())
                .addParameter("apiKey", "Error")
                .build();

        request.setURI(uriErr);
        HttpResponse responseErr = httpClient.execute(request);

        logger.debug("Отправка негативного запроса по адресу" + request.getURI());

        WireMock.verify(2, WireMock.getRequestedFor(WireMock.urlPathEqualTo(ADVANCED_URL + "?token=correctly")));


        Assertions.assertEquals(200, responseOk.getStatusLine().getStatusCode());
        Assertions.assertEquals(400, responseErr.getStatusLine().getStatusCode());

        Assertions.assertEquals("Ok", mapper.readValue(responseOk.getEntity().getContent(), Country.class).getLocalizedName());
        Assertions.assertEquals("Error", mapper.readValue(responseErr.getEntity().getContent(), Country.class).getLocalizedName());
    }

    @ParameterizedTest
    @ValueSource(strings = "/locations/v1/countries/autocomplete")
    public void checkAuth(String advanced_url) throws URISyntaxException, IOException {
        String correctly_token ="correctly";
        String unCorrectly_token = "uncorrectly";

        logger.info("Старт теста 401");
        logger.debug("Создание mock для авторизации"  +advanced_url);

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("token", WireMock.equalTo(correctly_token))
                .willReturn(WireMock.aResponse().withStatus(200).withBody(correctly_token))
        );

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("token", WireMock.equalTo(unCorrectly_token))
                .willReturn(WireMock.aResponse().withStatus(401).withBody(unCorrectly_token))
        );

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + advanced_url);

        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("token", correctly_token)
                .build();

        request.setURI(uriOk);

        HttpResponse responseOK = httpClient.execute(request);

        URI uriErr = new URIBuilder(request.getURI())
                .addParameter("token", unCorrectly_token)
                .build();

        request.setURI(uriErr);
        HttpResponse responseErr = httpClient.execute(request);

        WireMock.verify(2, WireMock.getRequestedFor(WireMock.urlPathEqualTo(advanced_url)));

        Assertions.assertEquals(200, responseOK.getStatusLine().getStatusCode());
        Assertions.assertEquals(401, responseErr.getStatusLine().getStatusCode());

        assertEquals(correctly_token, convertResponseToString(responseOK));
        assertEquals(unCorrectly_token, convertResponseToString(responseErr));
    }
}
