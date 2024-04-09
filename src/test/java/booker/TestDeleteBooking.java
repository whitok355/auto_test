package booker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;

public class TestDeleteBooking extends TestAbstractBooker{

    @ParameterizedTest
    @ValueSource(ints={4})
    public void checkDeleteBooking(int id){
        given().header("Content-Type", "application/json").header("Authorization", getBookerToken())
                .when().delete(getBaseUrl() + "booking/" + id)
                .then().statusCode(201);

        given()
                .when().get(getBaseUrl() + "booking/"+ id)
                .then().statusCode(404);
    }
}
