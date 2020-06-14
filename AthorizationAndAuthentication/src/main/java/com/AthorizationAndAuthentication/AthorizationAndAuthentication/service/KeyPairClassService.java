package com.AthorizationAndAuthentication.AthorizationAndAuthentication.service;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository.KeyPairClassRepository;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.KeyPairClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyPairClassService  {


  
    private PrivateKey privateKey;

    private PublicKey publicKey;


    

    public void setKeyPair() {
        
        try{

        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();

       

       privateKey=keyPair.getPrivate();
       publicKey=keyPair.getPublic();

        System.out.println("Prodje kompletno vrati kljuceve");

        }
        
        catch(Exception e){
            System.out.println("Ups");
        }

  }

  public Key getPrivateKey() {
      return privateKey;
  }

  public void setPrivateKey(PrivateKey privateKey) {
      this.privateKey = privateKey;
  }

  public Key getPublicKey() {
      return publicKey;
  }

  public void setPublicKey(PublicKey publicKey) {
      this.publicKey = publicKey;
  }

}