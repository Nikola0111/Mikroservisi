package com.projekat.XML.model;

import com.projekat.XML.enums.*;
import com.projekat.XML.model.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "endentity")
public class EndUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfRequestsCanceled;

    @Column(name = "activated")
    private boolean activity;

    @Column(name = "admin_approved")
    private boolean adminApproved;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "numberOfAds")
    private int numberOfAds;

    @ManyToMany
    @JoinTable(name = "enduser_rented", joinColumns = @JoinColumn(name = "endentity_id"),
            inverseJoinColumns = @JoinColumn(name = "ad_id"))
    private List<Advertisement> rentedCars;

    @ManyToOne
    @JoinColumn
    private EntityUser user;

    public EndUser() {

    }

    public EndUser(String name, String surname, LoginInfo loginInfo, String jmbg, String phoneNumber, UserType ut,
            int number_of_requests, boolean activity, boolean adminApproved, boolean blocked, int numberOfAds) {
        this.numberOfRequestsCanceled = number_of_requests;
        this.activity = activity;
        this.adminApproved = adminApproved;
        this.blocked = blocked;
        this.numberOfAds = numberOfAds;
    }

    public EndUser(String name, String surname, LoginInfo loginInfo, String jmbg,
    String phoneNumber, UserType ut, int numberOfRequestsCanceled, boolean
    activity, boolean adminApproved, boolean blocked, List<Advertisement>
    rentedCars) {
        this.numberOfRequestsCanceled = numberOfRequestsCanceled;
        this.activity = activity;
        this.adminApproved = adminApproved;
        this.blocked = blocked;
        this.rentedCars = rentedCars;
    }

    @Override
    public String toString() {
        return "EndUser{}";
    }

    public int getNumber_of_requests() {
        return numberOfRequestsCanceled;
    }

    public void setNumber_of_requests(int number_of_requests) {
        this.numberOfRequestsCanceled = number_of_requests;
    }

    public boolean isAccount_activated() {
        return activity;
    }

    public void setAccount_activated(boolean account_activated) {
        this.activity = account_activated;
    }

    public boolean isAdminApproved() {
        return adminApproved;
    }

    public void setAdminApproved(boolean adminApproved) {
        this.adminApproved = adminApproved;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public EntityUser getUser() {
        return this.user;
    }

    public void setUser(EntityUser user) {
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumberOfAds() {
        return this.numberOfAds;
    }

    public void setNumberOfAds(int numberOfAds) {
        this.numberOfAds = numberOfAds;
    }

    public List<Advertisement> getRentedCars() {
        return this.rentedCars;
    }

    public void setRentedCars(List<Advertisement> rentedCars) {
        this.rentedCars = rentedCars;
    }
}
