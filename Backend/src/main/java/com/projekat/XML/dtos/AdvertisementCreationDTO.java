package com.projekat.XML.dtos;

import java.util.ArrayList;
import java.util.List;

import com.projekat.XML.model.Advertisement;
import com.projekat.XML.model.EntityUser;

public class AdvertisementCreationDTO {
    private Long id;

    private String name;

    private String model;

    private String brand;

    private String fuelType;

    private String transType;

    private String carClass;

    private int travelled;

    private int carSeats;

    private double price;

    private EntityUser postedBy;

    private double discount;

    private double priceWithDiscount;

    private ArrayList<String> pictures;

    public AdvertisementCreationDTO(Long id, String name, String model, String brand, String fuelType, String transType, String carClass, int travelled, int carSeats, double price,EntityUser postedBy, double discount, double priceWithDiscount, ArrayList<String> pictures, double grade) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.fuelType = fuelType;
        this.transType = transType;
        this.carClass = carClass;
        this.travelled = travelled;
        this.carSeats = carSeats;
        this.price = price;
        this.postedBy=postedBy;
        this.discount = discount;
        this.priceWithDiscount = priceWithDiscount;
        this.pictures = pictures;
        this.grade = grade;
    }

    public AdvertisementCreationDTO(Advertisement ad){
        this.id = ad.getId();
        this.name = ad.getName();
        this.model = ad.getModel().getName();
        this.brand = ad.getBrand().getName();
        this.fuelType = ad.getFuelType().getName();
        this.transType = ad.getTransmissionType().getName();
        this.carClass = ad.getCarClass().getName();
        this.pictures = ad.getPictures();
        this.price = ad.getPrice();
        this.carSeats = ad.getCarSeats();
        this.discount = ad.getDiscount();
        this.postedBy=ad.getPostedBy();
        this.grade = ad.getGrade();
        this.travelled = ad.getTravelled();
        this.priceWithDiscount = ad.getPriceWithDiscount();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<String> getPictures() {
        return this.pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    private double grade;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTransType() {
        return this.transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getCarClass() {
        return this.carClass;
    }

    public void setCarClass(String carClass) {
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

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPriceWithDiscount() {
        return this.priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }

    public double getGrade() {
        return this.grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public EntityUser getPostedBy() {
        return postedBy;
    }

    public void setPostedByID(EntityUser postedBy) {
        this.postedBy = postedBy;
    }

}
