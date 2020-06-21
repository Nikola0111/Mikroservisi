package com.projekat.XML.dtos;

import com.projekat.XML.enums.CarClass;
import com.projekat.XML.enums.FuelType;
import com.projekat.XML.enums.TransmissionType;
import com.projekat.XML.model.Advertisement;
import com.projekat.XML.model.EntityUser;


public class AdvertisementInCartDTO {

    private Long id;

    private String name;

    private String model;

    private String brand;

    private FuelType fuelType;

    private TransmissionType transmissionType;

    private CarClass carClass;

    private int travelled;
  
    private int carSeats;

    private double price;

    private EntityUser postedBy;

    private boolean owner;

    private boolean together;

    
   

    public AdvertisementInCartDTO(String name,String model, String brand, FuelType fuelType, TransmissionType transmissionType, CarClass carClass, int travelled, int carSeats, double price,EntityUser user, boolean owner, boolean together) {
        this.name=name;
        this.model = model;
        this.brand = brand;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.carClass = carClass;
        this.travelled = travelled;
        this.carSeats = carSeats;
        this.price=price;
        this.postedBy=user;
        this.owner=owner;
        this.together=together;
        
    }

    public AdvertisementInCartDTO(Advertisement ad)
    {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
		return this.brand;
	}

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public TransmissionType getTransType() {
        return this.transmissionType;
    }

    public void setTransType(TransmissionType transType) {
        this.transmissionType = transType;
    }

    public CarClass getCarClass() {
        return this.carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }


    public int getTravelled() {
        return this.travelled;
    }

    public void setTravelled(int travelled) {
        this.travelled = travelled;
    }

    public int getCarSeats() {
        return this.carSeats;
    }

    public void setCarSeats(int carSeats) {
        this.carSeats = carSeats;
    }


    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityUser getPostedBy() {
        return this.postedBy;
    }

    public void setPostedBy(EntityUser postedBy) {
        this.postedBy = postedBy;
    }

    public boolean isOwner() {
        return this.owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean isTogether() {
        return this.together;
    }

    public void setTogether(boolean together) {
        this.together = together;
    }


}
