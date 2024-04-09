package booker;

import org.example.booker.BookingRequestAuth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBookerAuth extends TestAbstractBooker{

    private Stream<BookingRequestAuth> getCorrectlyDataAuth(){
        BookingRequestAuth bookingRequestAuth = new BookingRequestAuth();
        bookingRequestAuth.setUsername(getBookerUsername());
        bookingRequestAuth.setPassword(getBookerPass());
        return Stream.of(
                bookingRequestAuth
        );
    }
    private Stream<BookingRequestAuth> getUncorrectlyDataAuth(){
        BookingRequestAuth bookingRequestAuth = new BookingRequestAuth();
        bookingRequestAuth.setUsername(getBookerUsername());
        bookingRequestAuth.setPassword(getBookerPass());
        return Stream.of(
                bookingRequestAuth
        );
    }
    @ParameterizedTest
    @MethodSource("getCorrectlyDataAuth")
    public void checkAuthBookerPositive(BookingRequestAuth correctlyJson){
        given().body(correctlyJson)
                .when().post(getBaseUrl() + "auth")
                .then().statusCode(200);
    }
    @ParameterizedTest
    @MethodSource("getUncorrectlyDataAuth")
    public void checkAuthBookerNegative(BookingRequestAuth uncorrectlyAuthData){
        given().body(uncorrectlyAuthData)
                .when().post(getBaseUrl() + "auth")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"));
    }
}