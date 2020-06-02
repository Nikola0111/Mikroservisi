package com.Advertisement.Advertisement.dtos;

import java.time.LocalDateTime;

public class BookingDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long advertisementID;

    public BookingDTO(LocalDateTime startTime, LocalDateTime endTime, Long advertisementID) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.advertisementID = advertisementID;
    }

    public BookingDTO() {
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getAdvertisementID() {
        return this.advertisementID;
    }

    public void setAdvertisementID(Long advertisementID) {
        this.advertisementID = advertisementID;
    }

}