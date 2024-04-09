package petStore;

import org.example.pet.FindResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFindStatus extends AbstractTestPetStore{

    @ParameterizedTest
    @ValueSource(strings={"available", "pending", "sold"})
    public void checkFindStatus(String value){
        List<FindResponse> findResponse = given().params("status", value).header("Content-Type", "application/json")
                .when().get(getBaseUrl() + "pet/findByStatus")
                .then().statusCode(200).extract().jsonPath().getList(".", FindResponse.class);

        for(FindResponse fr : findResponse){
            assertEquals(value, fr.getStatus());
        }
    }
}
