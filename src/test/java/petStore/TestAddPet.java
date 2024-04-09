package petStore;

import org.example.pet.Category;
import org.example.pet.FindResponse;
import org.example.pet.Pet;
import org.example.pet.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAddPet extends AbstractTestPetStore{

    private static Stream<Pet> getPet(){
        Pet pet = new Pet();
        pet.setId(101L);
        pet.setName("Test");
        pet.setStatus("solid");

        Category category = new Category();
        category.setId(1L);
        category.setName("testtest");
        pet.setCategory(category);

        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("testtesttest");
        tags.add(tag);
        pet.setTags(tags);

        return Stream.of(
                pet
        );
    }
    @ParameterizedTest
    @MethodSource("getPet")
    public void checkAddPet(Pet pet){
        FindResponse resp = given().header("Content-Type", "application/json")
                .body(pet)
                .when().post(getBaseUrl()+ "pet")
                .then().statusCode(200).extract().jsonPath().getObject(".", FindResponse.class);

        assertEquals(pet.getId().toString(), resp.getId().toString());
        assertEquals(pet.getName(), resp.getName());
        assertEquals(pet.getStatus(), resp.getStatus());

        assertEquals(pet.getCategory().getId().toString(), resp.getCategory().getId().toString());
        assertEquals(pet.getCategory().getName(), resp.getCategory().getName());

    }
}
