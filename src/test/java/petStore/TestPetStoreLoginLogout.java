package petStore;

import org.example.pet.ResponseUser;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPetStoreLoginLogout extends AbstractTestPetStore{

    @Test
    public void checkLogin(){
        ResponseUser resp = given().params("username", getBaseLogin()).params("password", getBasePass())
                .when().get(getBaseUrl() +"user/login")
                .then().statusCode(200).extract().jsonPath().getObject(".", ResponseUser.class);

        assertEquals(200, resp.getCode());
        assertEquals("unknown", resp.getType());
    }
    @Test
    public void checkLogout(){
        ResponseUser resp = given()
                .when().get(getBaseUrl()+"user/logout")
                .then().statusCode(200).extract().jsonPath().getObject(".", ResponseUser.class);

        assertEquals(200, resp.getCode());
        assertEquals("unknown", resp.getType());
        assertEquals("ok", resp.getMessage());

    }

}
