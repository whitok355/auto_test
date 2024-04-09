package petStore;

import io.restassured.response.Response;
import org.example.pet.ResponseUser;
import org.example.pet.User;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPetStoreCreateUser extends AbstractTestPetStore {

    public Stream<User> getUser(){
        User user = new User();
        user.setId(10);
        user.setUsername("test10");
        user.setFirstName("test10");
        user.setLastName("test10");
        user.setEmail("test10@test10.ru");
        user.setPassword("123");
        user.setPhone("+7987-987-98-98");
        user.setUserStatus(1);
        return Stream.of(
                user
        );
    }

    @ParameterizedTest
    @MethodSource("getUser")
    public void checkCreateUser(User user){
        ResponseUser resp =  given()
                .header("Content-Type", "application/json")
                .body(user)
                .when().post(getBaseUrl()+ "user")
                .then().statusCode(200).extract().body().jsonPath().getObject(".", ResponseUser.class);

        assertEquals(200, resp.getCode());
        assertEquals("unknown", resp.getType());
        assertEquals(user.getId().toString(), resp.getMessage().toString());
    }

    @ParameterizedTest
    @MethodSource("getUser")
    public void checkCreateUserWithList(User user){
        List<User> users = new ArrayList<>();
        users.add(user);
        user.setId(12);
        users.add(user);

        ResponseUser resp = given()
                .header("Content-Type", "application/json")
                .body(users)
                .when().post(getBaseUrl() +"user/createWithList")
                .then().statusCode(200).extract().jsonPath().getObject(".", ResponseUser.class);

        assertEquals(200, resp.getCode());
        assertEquals("unknown", resp.getType());
        assertEquals("ok", resp.getMessage());
    }
}
