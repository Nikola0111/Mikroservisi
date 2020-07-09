package com.Advertisement.Advertisement.dtos;

import java.time.LocalDateTime;

public class BookingDTO {

    private Long id;
    private LocalDateTime timeFrom;
    private LocalDateTime timeTo;
    private Long advertisementId;

    public BookingDTO(LocalDateTime timeFrom, LocalDateTime timeTo, Long advertisementId) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.advertisementId = advertisementId;
    }

    public BookingDTO() {
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeFrom() {
        return this.timeFrom;
    }

    public void setTimeFrom(LocalDateTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalDateTime getTimeTo() {
        return this.timeTo;
    }

    public void setTimeTo(LocalDateTime timeTo) {
        this.timeTo = timeTo;
    }

    public Long getAdvertisementId() {
        return this.advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", timeFrom='" + timeFrom + '\'' + ", timeTo='" + timeTo + '\'' + ", adID='"
                + advertisementId + '}';
    }
}