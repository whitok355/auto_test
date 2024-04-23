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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TestRegionListWithMock extends AbstractLocationTest {
    private static final Logger logger = LoggerFactory.getLogger(TestRegionListWithMock.class);
    @ParameterizedTest
    @ValueSource(strings="/locations/v1/regions")
    public void check200(String advanced_url) throws IOException, URISyntaxException {
        logger.info("СТарт теста check200" + " " + this.getClass().getSimpleName());
        ObjectMapper mapper = new ObjectMapper();
        Location resOk = new Location();
        resOk.setKey("testRegionOk");
        Location resErr = new Location();
        resErr.setKey("testRegionErr");

        logger.debug("Формирование мока для GET" + advanced_url);
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("apikey", WireMock.equalTo(resOk.getKey()) )
                .willReturn(WireMock.aResponse().withStatus(200).withBody(mapper.writeValueAsString(resOk)))
        );

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("apikey", WireMock.equalTo(resErr.getKey()))
                .willReturn(WireMock.aResponse().withStatus(404).withBody(mapper.writeValueAsString(resErr)))
        );

        logger.debug("Mock объекты созданы ");

        CloseableHttpClient clientHTTP = HttpClients.createDefault();

        logger.debug("Http клиент создан");

        HttpGet request = new HttpGet(getBaseUrl() + advanced_url);

        URI uriOk = new URIBuilder(request.getURI())
                .addParameter("apikey", resOk.getKey())
                .build();

        request.setURI(uriOk);
        HttpResponse responseOk = clientHTTP.execute(request);

        URI uriErr = new URIBuilder(getBaseUrl() + advanced_url)
                .addParameter("apikey", resErr.getKey())
                .build();

        request.setURI(uriErr);

        HttpResponse responseErr = clientHTTP.execute(request);

        WireMock.verify(2, WireMock.getRequestedFor(WireMock.urlPathEqualTo(advanced_url + "?token=ok")));
        Assertions.assertEquals(200, responseOk.getStatusLine().getStatusCode());
        Assertions.assertEquals(404, responseErr.getStatusLine().getStatusCode());

        Assertions.assertEquals(resOk.getKey(), mapper.readValue(responseOk.getEntity().getContent(), Location.class).getKey());
        Assertions.assertEquals(resErr.getKey(), mapper.readValue(responseErr.getEntity().getContent(), Location.class).getKey());
    }

    @ParameterizedTest
    @ValueSource(strings="/locations/v1/regions")
    public void checkAuth(String advanced_url) throws URISyntaxException, IOException {
        logger.info("Старт теста check200" + " " + this.getClass().getSimpleName());

        String token_ok = "ok";
        String token_err ="err";

        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("token", WireMock.equalTo(token_ok))
                .willReturn(WireMock.aResponse().withStatus(200).withBody(token_ok))
        );
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo(advanced_url))
                .withQueryParam("token", WireMock.equalTo(token_err))
                .willReturn(WireMock.aResponse().withStatus(401).withBody(token_err))
        );
        logger.debug("Mock объекты созданы ");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(getBaseUrl() + advanced_url);
        logger.debug("Http клиент создан");

        URI uri_ok = new URIBuilder(request.getURI())
                .addParameter("token", token_ok)
                .build();

        request.setURI(uri_ok);

        HttpResponse responseOk = httpClient.execute(request);

        URI uri_err = new URIBuilder(request.getURI())
                .addParameter("token", token_err)
                .build();

        request.setURI(uri_err);
        HttpResponse responseErr = httpClient.execute(request);

        WireMock.verify(2, WireMock.getRequestedFor(WireMock.urlPathEqualTo(advanced_url)));
        Assertions.assertEquals(200, responseOk.getStatusLine().getStatusCode());
        Assertions.assertEquals(401, responseErr.getStatusLine().getStatusCode());

        Assertions.assertEquals(token_ok, convertResponseToString(responseOk));
        Assertions.assertEquals(token_err, convertResponseToString(responseErr));

    }
}
