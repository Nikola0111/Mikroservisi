package com.projekat.XML.dtos;

import com.projekat.XML.model.*;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "advertisementSoapDTO",
        namespace="com.projekat.XML.dtos",
        propOrder = {"id", "name", "model", "brand", "fuelType", "transmissionType","carClass", "travelled",
                "carSeats", "price", "discount", "priceWithDiscount", "pictures", "grade", "postedByID"})
public class AdvertisementSoapDTO implements Serializable {

    @XmlElement(name="id", required = true)
    private Long id;

    @XmlElement(name="name", required = true)
    private String name;

    @XmlElement(name="model", required = false)
    private Model model;

    @XmlElement(name="brand", required = false)
    private Brand brand;

    @XmlElement(name="fuelType", required = false)
    private FuelType fuelType;

    @XmlElement(name="transmissionType", required = false)
    private TransmissionType transmissionType;

    @XmlElement(name="carClass", required = false)
    private CarClass carClass;

    @XmlElement(name="travelled", required = false)
    private int travelled;

    @XmlElement(name="carSeats", required = false)
    private int carSeats;

    @XmlElement(name="price", required = false)
    private double price;

    @XmlElement(name="discount", required = false)
    private double discount;

    @XmlElement(name="priceWithDiscount", required = false)
    private double priceWithDiscount;

    @XmlElementWrapper(name="pictures", required=false)
    @XmlElement(name="picture", required = false)
    private ArrayList<String> pictures;

    @XmlElement(name="grade", required = false)
    private double grade;

    @XmlElement(name="postedByID", required = false)
    private long postedByID;

    public AdvertisementSoapDTO() {
    }

    public AdvertisementSoapDTO(String name, Model model, Brand brand, FuelType fuelType, TransmissionType transmissionType, CarClass carClass, int travelled, int carSeats, double price, double discount, ArrayList<String> pictures, long postedByID) {
        this.name=name;
        this.model = model;
        this.brand = brand;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.carClass = carClass;
        this.travelled = travelled;
        this.carSeats = carSeats;
        this.price=price;
        this.discount = discount;
        this.priceWithDiscount = price - (price * discount / 100);
        this.pictures=pictures;
        this.postedByID = postedByID;
    }

    public AdvertisementSoapDTO(Advertisement ad)
    {
        this.id = ad.getId();
        this.name = ad.getName();
        this.model = ad.getModel();
        this.brand = ad.getBrand();
        this.fuelType = ad.getFuelType();
        this.transmissionType = ad.getTransmissionType();
        this.carClass = ad.getCarClass();
        this.travelled = ad.getTravelled();
        this.carSeats = ad.getCarSeats();
        this.price=ad.getPrice();
        this.discount = ad.getDiscount();
        this.priceWithDiscount = ad.getPriceWithDiscount();
        this.grade = ad.getGrade();
        this.postedByID = ad.getPostedBy().getId();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
    public ArrayList<String> getPictures() {
        return this.pictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public long getPostedByID() {
        return postedByID;
    }

    public void setPostedByID(long postedByID) {
        this.postedByID = postedByID;
    }
}
