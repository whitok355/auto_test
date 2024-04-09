package booker;

import org.example.booker.*;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCreateBooking extends TestAbstractBooker{

    private Stream<BookingRequest> getJSONData(){
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setFirstname("old First name");
        bookingRequest.setLastname("old Last name");
        bookingRequest.setTotalprice(123);
        bookingRequest.setDepositpaid(false);
        Bookingdates bookingDates = new Bookingdates();
        bookingDates.setCheckin("2023-01-01");
        bookingDates.setCheckout("2022-02-01");
        bookingRequest.setBookingdates(bookingDates);
        bookingRequest.setAdditionalneeds("All inclusive");

        return Stream.of(
                bookingRequest
        );
    }

    @ParameterizedTest
    @MethodSource("getJSONData")
    public void checkCreateBooking(BookingRequest bookingRequest){
            given().header("Content-Type", "application/json").body(bookingRequest)
                    .when().post(getBaseUrl() + "booking")
                    .then()
                    .contentType(JSON)
                    .statusCode(200);
    }

    @ParameterizedTest
    @MethodSource("getJSONData")
    public void checkResponseJSON(BookingRequest bookingRequest) {
        BookingRespCreate bookingRespCreate = given().header("Content-Type", "Application/json").body(bookingRequest)
                .when().post(getBaseUrl() + "booking")
                .then()
                .contentType(JSON)
                .statusCode(200)
                .extract().body().jsonPath().getObject(".", BookingRespCreate.class);

        assertNotNull(bookingRespCreate.getBookingid());

        Booking booking = bookingRespCreate.getBooking();
        assertNotNull(booking.getFirstname());
        assertNotNull(booking.getLastname());
        assertNotNull(booking.getTotalprice());
        assertNotNull(booking.getDepositpaid());
        assertNotNull(booking.getAdditionalneeds());

        Bookingdates bookingdates = booking.getBookingdates();

        assertNotNull(bookingdates.getCheckin());
        assertNotNull(bookingdates.getCheckout());
    }
}