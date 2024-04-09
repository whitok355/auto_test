package booker;

import org.example.booker.BookingRespCreate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestGetBooking extends TestAbstractBooker{


    private Stream<List<String>> getArrIds(){
        List<String> arr = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("src/test/resources/booking_ids.txt"))){
            String line;
            while((line= br.readLine()) != null){
                arr.add(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return Stream.of(
                arr
        );
    }
    @Test
    public void checkGetAllBooking(){
        List<BookingRespCreate> ids = given()
                .when().get(getBaseUrl() + "booking")
                .then().statusCode(200)
                .extract().body().jsonPath().getList(".", BookingRespCreate.class);

        File fl = new File("src/test/resources/booking_ids.txt");
        if(fl.exists()){
            fl.delete();
        }

        for(BookingRespCreate id: ids){
            try(FileWriter fw = new FileWriter("src/test/resources/booking_ids.txt", true)){
                fw.write(id.getBookingid() + "\n");
                assertNotNull(id.getBookingid());
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getArrIds")
    public void checkGetFindBooking(List<String> arr){
        Random rnd = new Random();
        for(int i = 0 ; i < 10; i++){
            int randIndex = rnd.nextInt(0, arr.size());
            System.out.println(arr.get(randIndex) + "- Ok");
            given()
                    .when().get(getBaseUrl() + "booking/"+ arr.get(randIndex).trim())
                        .then().statusCode(200);
        }
    }
}
