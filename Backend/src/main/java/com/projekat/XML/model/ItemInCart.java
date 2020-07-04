package com.projekat.XML.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class ItemInCart {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private EntityUser user;
    
    @ManyToOne
    @JoinColumn
    private Advertisement advertisement;

    private LocalDateTime timeFrom;

    private LocalDateTime timeTo;

   

    public ItemInCart(){
        
    }

    public ItemInCart( EntityUser user, Advertisement adv, LocalDateTime from, LocalDateTime to) {
        this.user =user;
        this.advertisement= adv;
        this.timeFrom=from;
        this.timeTo=to;
    }

    public ItemInCart( Advertisement adv, LocalDateTime from, LocalDateTime to) {
        this.advertisement= adv;
        this.timeFrom=from;
        this.timeTo=to;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityUser getUserId() {
        return this.user;
    }

    public void setUserId(EntityUser userId) {
        this.user = user;
    }

    public Advertisement getAdvertisement() {
        return this.advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
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

   

}