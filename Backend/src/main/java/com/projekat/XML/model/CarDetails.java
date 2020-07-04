package com.projekat.XML.model;

import com.projekat.XML.enums.Data;

import javax.persistence.*;

@Entity
@Table(name = "cardetails")
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Data type;

    public CarDetails(){

    }

    public CarDetails(String code, String name, Data type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getType() {
        return type;
    }

    public void setType(Data type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CarDetails{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
