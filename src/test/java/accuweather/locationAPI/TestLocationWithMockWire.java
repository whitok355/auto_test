package accuweather.locationAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.accuweather.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TestLocationWithMockWire extends AbstractLocationTest {
    private static final Logger logger = LoggerFactory.getLogger(TestLocationWithMockWire.class);
    @Test
    void get_shouldReturn200() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 200 запущен");
        //given
        ObjectMapper mapper = new ObjectMapper();
        Location bodyOk = new Location();
        bodyOk.setKey("OK");

        Location bodyError = new Location();
        bodyError.setKey("Error");

        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", WireMock.equalTo("Samara"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyOk))));

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("q", WireMock.equalTo("error"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200).withBody(mapper.writeValueAsString(bodyError))));

        CloseableHttpClient httpClient = HttpClients.createDefault();
        logger.debug("http клиент создан");
        //when

        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("q", "Samara")
                .build();
        request.setURI(uriOk);
        HttpResponse responseOk = httpClient.execute(request);

        URI uriError = new URIBuilder(request.getURI())
                .addParameter("q", "error")
                .build();
        request.setURI(uriError);

        HttpResponse responseError = httpClient.execute(request);

        //then

        WireMock.verify(2, WireMock.getRequestedFor(WireMock.urlPathEqualTo("/locations/v1/cities/autocomplete")));
        Assertions.assertEquals(200, responseOk.getStatusLine().getStatusCode());
        Assertions.assertEquals(200, responseError.getStatusLine().getStatusCode());
        Assertions.assertEquals("OK", mapper.readValue(responseOk.getEntity().getContent(), Location.class).getKey());
        Assertions.assertEquals("Error", mapper.readValue(responseError.getEntity().getContent(), Location.class).getKey());


    }

    @Test
    void get_shouldReturn401() throws IOException, URISyntaxException {
        logger.info("Тест код ответ 401 запущен");
        //given
        logger.debug("Формирование мока для GET /locations/v1/cities/autocomplete");
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/locations/v1/cities/autocomplete"))
                .withQueryParam("apiKey", WireMock.notMatching("82c9229354f849e78efe010d94150807"))
                .willReturn(WireMock.aResponse()
                        .withStatus(401).withBody("ERROR")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl()+"/locations/v1/cities/autocomplete");
        URI uri = new URIBuilder(request.getURI())
                .addParameter("apiKey", "A_82c9229354f849e78efe010d94150807")
                .build();
        request.setURI(uri);
        logger.debug("http клиент создан");
        //when
        HttpResponse response = httpClient.execute(request);
        //then
        WireMock.verify(WireMock.getRequestedFor(WireMock.urlPathEqualTo("/locations/v1/cities/autocomplete")));
        Assertions.assertEquals(401, response.getStatusLine().getStatusCode());
        assertEquals("ERROR", convertResponseToString(response));

    }
}


















