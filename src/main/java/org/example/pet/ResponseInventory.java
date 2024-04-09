
package org.example.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "sold",
    "Updated_available",
    "placed",
    "string",
    "unavailable",
    "pending",
    "available",
    "active",
    "strings",
    "peric",
    "Solds",
    "avaliables",
    "not_available"
})
public class ResponseInventory {

    @JsonProperty("sold")
    private Integer sold;
    @JsonProperty("Updated_available")
    private Integer updatedAvailable;
    @JsonProperty("placed")
    private Integer placed;
    @JsonProperty("string")
    private Integer string;
    @JsonProperty("unavailable")
    private Integer unavailable;
    @JsonProperty("pending")
    private Integer pending;
    @JsonProperty("available")
    private Integer available;
    @JsonProperty("active")
    private Integer active;
    @JsonProperty("strings")
    private Integer strings;
    @JsonProperty("peric")
    private Integer peric;
    @JsonProperty("Solds")
    private Integer solds;
    @JsonProperty("avaliables")
    private Integer avaliables;
    @JsonProperty("not_available")
    private Integer notAvailable;

    @JsonProperty("sold")
    public Integer getSold() {
        return sold;
    }

    @JsonProperty("sold")
    public void setSold(Integer sold) {
        this.sold = sold;
    }

    @JsonProperty("Updated_available")
    public Integer getUpdatedAvailable() {
        return updatedAvailable;
    }

    @JsonProperty("Updated_available")
    public void setUpdatedAvailable(Integer updatedAvailable) {
        this.updatedAvailable = updatedAvailable;
    }

    @JsonProperty("placed")
    public Integer getPlaced() {
        return placed;
    }

    @JsonProperty("placed")
    public void setPlaced(Integer placed) {
        this.placed = placed;
    }

    @JsonProperty("string")
    public Integer getString() {
        return string;
    }

    @JsonProperty("string")
    public void setString(Integer string) {
        this.string = string;
    }

    @JsonProperty("unavailable")
    public Integer getUnavailable() {
        return unavailable;
    }

    @JsonProperty("unavailable")
    public void setUnavailable(Integer unavailable) {
        this.unavailable = unavailable;
    }

    @JsonProperty("pending")
    public Integer getPending() {
        return pending;
    }

    @JsonProperty("pending")
    public void setPending(Integer pending) {
        this.pending = pending;
    }

    @JsonProperty("available")
    public Integer getAvailable() {
        return available;
    }

    @JsonProperty("available")
    public void setAvailable(Integer available) {
        this.available = available;
    }

    @JsonProperty("active")
    public Integer getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Integer active) {
        this.active = active;
    }

    @JsonProperty("strings")
    public Integer getStrings() {
        return strings;
    }

    @JsonProperty("strings")
    public void setStrings(Integer strings) {
        this.strings = strings;
    }

    @JsonProperty("peric")
    public Integer getPeric() {
        return peric;
    }

    @JsonProperty("peric")
    public void setPeric(Integer peric) {
        this.peric = peric;
    }

    @JsonProperty("Solds")
    public Integer getSolds() {
        return solds;
    }

    @JsonProperty("Solds")
    public void setSolds(Integer solds) {
        this.solds = solds;
    }

    @JsonProperty("avaliables")
    public Integer getAvaliables() {
        return avaliables;
    }

    @JsonProperty("avaliables")
    public void setAvaliables(Integer avaliables) {
        this.avaliables = avaliables;
    }

    @JsonProperty("not_available")
    public Integer getNotAvailable() {
        return notAvailable;
    }

    @JsonProperty("not_available")
    public void setNotAvailable(Integer notAvailable) {
        this.notAvailable = notAvailable;
    }

}
