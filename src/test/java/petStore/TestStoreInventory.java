package petStore;

import org.example.pet.ResponseInventory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static io.restassured.RestAssured.given;

public class TestStoreInventory extends AbstractTestPetStore{

    @Test //в ответе возвраается неверный формат, задвоенные значения, тест Fail
    public void checkStoreInventory(){
        given()
                .when().get(getBaseUrl() + "store/inventory")
                .then().statusCode(200);

    }
}
