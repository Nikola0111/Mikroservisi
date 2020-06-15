package com.Advertisement.Advertisement.service;

import java.security.Key;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

@Service
public class PublicKeyClassService {
    

    private PublicKey publicKey;


    public PublicKeyClassService(){


    }

    public void setPublicKeyClass(PublicKey publicKey){

        this.publicKey=publicKey;
    }

    public PublicKey getPublicKeyClass(){
        return this.publicKey;
    }

}