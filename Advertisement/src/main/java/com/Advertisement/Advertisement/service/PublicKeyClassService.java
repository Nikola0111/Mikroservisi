package com.Advertisement.Advertisement.service;

import java.security.Key;

import org.springframework.stereotype.Service;

@Service
public class PublicKeyClassService {
    

    private Key publicKey;


    public PublicKeyClassService(){


    }

    public void setPublicKeyClass(Key publicKey){

        this.publicKey=publicKey;
    }

    public Key getPublicKeyClass(){
        return this.publicKey;
    }

}