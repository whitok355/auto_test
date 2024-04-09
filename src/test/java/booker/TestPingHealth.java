package booker;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPingHealth extends TestAbstractBooker{
    @Test
    public void checkPingHealth(){
        Response res = given()
                .when().get(getBaseUrl() +"ping")
                .then().statusCode(201)
                .extract().response();
        assertEquals("Created", res.asString());
    }
}
