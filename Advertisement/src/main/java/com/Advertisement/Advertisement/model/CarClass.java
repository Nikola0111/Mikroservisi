package com.Advertisement.Advertisement.model;

import java.util.ArrayList;

import javax.persistence.*;

@Entity
public class CarClass {
    

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getID(){
        return this.id;
    }

    public void setID(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public CarClass() {

        
    }
    public CarClass(String name) {
        this.name = name;
    }
}