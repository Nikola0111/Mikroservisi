package com.Advertisement.Advertisement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import SaveAdvertisementSoap.GetAdvertisementRequest;

@Service
public class SoapClient {

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public String saveAdvertisement(GetAdvertisementRequest request){
        template = new WebServiceTemplate(marshaller);
        String ret = (String) template.marshalSendAndReceive("http://localhost:9090/ws", request);
        System.out.println("The advertisement is here: " + ret);
        return ret;
    }
}
