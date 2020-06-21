package com.projekat.XML.dtos;

import java.time.LocalDateTime;


import com.projekat.XML.model.Advertisement;


public class ItemInCartDTO  {

    private Long id;

    private Advertisement advertisement;

    private LocalDateTime timeFrom;

    private LocalDateTime timeTo;

    private boolean owner;

    private boolean together;


    public ItemInCartDTO(){
        
    }

    public ItemInCartDTO( Advertisement adv, LocalDateTime from, LocalDateTime to) {
        
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