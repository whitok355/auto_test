package petStore;

import org.example.pet.FindResponse;
import org.example.pet.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFindPet extends AbstractTestPetStore{

    @ParameterizedTest
    @ValueSource(ints={1,2})
    public void checkFindPetPositive(int value){
        FindResponse resp = given()
                .when().get(getBaseUrl() + "pet/"+ value)
                .then().statusCode(200).extract().jsonPath().getObject(".", FindResponse.class);

        assertEquals(value, resp.getId());
        assertEquals(value, resp.getCategory().getId());
    }
    @ParameterizedTest
    @ValueSource(ints={6,7})
    public void checkFindPetNegative(int value){
        given()
                .when().get(getBaseUrl() + "pet/"+ value)
                .then().statusCode(404);

    }
}
