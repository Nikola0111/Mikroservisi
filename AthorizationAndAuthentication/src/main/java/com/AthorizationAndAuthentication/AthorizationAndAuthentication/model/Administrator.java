package com.AthorizationAndAuthentication.AthorizationAndAuthentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Administrator extends EntityUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public Administrator() {
        super();
    }


}
