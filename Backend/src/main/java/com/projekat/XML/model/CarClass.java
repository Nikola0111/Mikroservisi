package com.projekat.XML.model;

import java.util.ArrayList;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "carClass",
        namespace="com.projekat.XML.model",
        propOrder = {"id", "name", "code"})
@Entity
public class CarClass {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @XmlElement(name="id", required = true)
    private Long id;

    @XmlElement(name="name", required = true)
    private String name;

    @XmlElement(name="code", required = true)
    private String code;


    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
