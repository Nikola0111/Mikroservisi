package com.Advertisement.Advertisement.dtos;

public class AdvertisementReportDTO {
    private Long id;

    private String name;

    private String model;

    private String brand;

    private int travelled;

    private Long bookingID;

    public AdvertisementReportDTO(Long id, String name, String model, String brand, int travelled, Long bookingID) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.travelled = travelled;
        this.bookingID = bookingID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getTravelled() {
        return travelled;
    }

    public void setTravelled(int travelled) {
        this.travelled = travelled;
    }

    public Long getBookingID() {
        return bookingID;
    }

    public void setBookingID(Long bookingID) {
        this.bookingID = bookingID;
    }
}