package org.example.booker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "bookingid"
})
public class BookingId {
    @JsonProperty("bookingid")
    private String bookingid;
    @JsonProperty("bookingid")
    public String getBookingId() {
        return bookingid;
    }
    @JsonProperty("bookingid")
    public void setBookingI(String bookingid) {
        this.bookingid = bookingid;
    }
    @Override
    public String toString() {
        return String.format("%s", bookingid);
    }
}
