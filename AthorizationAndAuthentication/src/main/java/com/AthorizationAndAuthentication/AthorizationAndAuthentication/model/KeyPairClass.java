package com.AthorizationAndAuthentication.AthorizationAndAuthentication.model;

import java.security.Key;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KeyPairClass{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


private Key privateKey;

private Key publicKey;

private String username;


public KeyPairClass(){


    
        }

        public Key getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(Key privateKey) {
            this.privateKey = privateKey;
        }

        public Key getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(Key publicKey) {
            this.publicKey = publicKey;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }






}