package booker;

import org.example.booker.BookingRequest;
import org.example.booker.BookingRespUpdate;
import org.example.booker.Bookingdates;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBookingUpdate extends TestAbstractBooker{
    public static Stream<BookingRequest> getUpdatejson(){
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setFirstname("new First name");
        bookingRequest.setLastname("new Last name");
        bookingRequest.setTotalprice(123132);
        bookingRequest.setDepositpaid(false);
        Bookingdates bookingDates = new Bookingdates();
        bookingDates.setCheckin("2024-01-01");
        bookingDates.setCheckout("2024-02-01");
        bookingRequest.setBookingdates(bookingDates);
        bookingRequest.setAdditionalneeds("all");

        return Stream.of(
                bookingRequest
        );
    }
    @ParameterizedTest
    @MethodSource("getUpdatejson")
    public void checkBookingUpdate(BookingRequest bookingRequest){
        BookingRespUpdate bookingRespUpdate = given()
                .header("Authorization", getBookerToken())
                .header("Content-Type", "application/json")
                .body(bookingRequest)
                .when().put(getBaseUrl() + "booking/" + 1)
                .then().statusCode(200)
                .extract().body().jsonPath().getObject(".", BookingRespUpdate.class);

        assertEquals(bookingRequest.getFirstname(), bookingRespUpdate.getFirstname());
        assertEquals(bookingRequest.getLastname(), bookingRespUpdate.getLastname());
    }
    @ParameterizedTest
    @MethodSource("getUpdatejson")
    public void checkResponseWhenUpdate(BookingRequest bookingRequest){
        BookingRespUpdate bookingRespUpdate = given()
                .header("Content-Type", "application/json")
                .header("Authorization", getBookerToken())
                .body(bookingRequest)
                .when().put(getBaseUrl() + "booking/" + 1)
                .then().statusCode(200)
                .extract().body().jsonPath().getObject(".", BookingRespUpdate.class);

        assertEquals(bookingRespUpdate.getFirstname(), bookingRequest.getFirstname());
        assertEquals(bookingRespUpdate.getLastname(), bookingRequest.getLastname());
        assertEquals(bookingRespUpdate.getTotalprice(), bookingRequest.getTotalprice());
        assertEquals(bookingRespUpdate.getDepositpaid(), bookingRequest.getDepositpaid());
        assertEquals(bookingRespUpdate.getAdditionalneeds(), bookingRequest.getAdditionalneeds());

        Bookingdates bookingdates = bookingRespUpdate.getBookingdates();
        assertEquals(bookingdates.getCheckin(), bookingRequest.getBookingdates().getCheckin());
        assertEquals(bookingdates.getCheckout(), bookingRequest.getBookingdates().getCheckout());
    }
    @ParameterizedTest
    @MethodSource("getUpdatejson")
    public void checkPartialUpdate(BookingRequest bookingRequest){
        bookingRequest.setLastname("partial test3");
        BookingRespUpdate bookingRespUpdate = given()
                .body("{" +
                        "\"lastname\":" + "\""+bookingRequest.getLastname()+"\""+
                        "}")
                .header("Content-Type", "application/json")
                .header("Authorization", getBookerToken())
                .when().patch(getBaseUrl()+ "booking/"+ 3)
                .then()
                .statusCode(200)
                .contentType(JSON)
                .extract().body().jsonPath().getObject(".", BookingRespUpdate.class);

        assertEquals(bookingRespUpdate.getLastname(), bookingRequest.getLastname());
    }
}
