package accuweather;
import org.example.accuweather.Area;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
public class TestAccuweather extends TestAbstractAccuweather {

    @Test
    public void testGetAdminAreaList(){
        List<Area> areas = given()
                .queryParam("apikey", getApiKey())
                .when()
                .get(getBaseUrl() + "adminareas/RU")
                .then()
                .statusCode(200)
                .extract()
                .body().jsonPath().getList(".", Area.class);

        for(Area area: areas){
            assertNotNull(area.getId());
            assertNotNull(area.getLocalizedName());
            assertNotNull(area.getEnglishName());
            assertNotNull(area.getLevel());
            assertNotNull(area.getLocalizedType());
            assertNotNull(area.getEnglishType());
            assertNotNull(area.getCountryID());
        }
    }
}
