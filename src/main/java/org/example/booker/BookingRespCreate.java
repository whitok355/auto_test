
package org.example.booker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "bookingid",
    "booking"
})
public class BookingRespCreate {

    @JsonProperty("bookingid")
    private String bookingid;
    @JsonProperty("booking")
    private Booking booking;

    @JsonProperty("bookingid")
    public String getBookingid() {
        return bookingid;
    }

    @JsonProperty("bookingid")
    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    @JsonProperty("booking")
    public Booking getBooking() {
        return booking;
    }

    @JsonProperty("booking")
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    @Override
    public String toString() {
        return String.format("%s", bookingid);
    }
}
