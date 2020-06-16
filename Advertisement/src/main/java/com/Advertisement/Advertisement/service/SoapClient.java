package com.Advertisement.Advertisement.service;

import SaveAdvertisementSoap.GetAdvertisementRequest;
import com.Advertisement.Advertisement.dtos.AdvertisementSoapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

@Service
public class SoapClient {

    @Autowired
    private Jaxb2Marshaller marshaller;

    private WebServiceTemplate template;

    public AdvertisementSoapDTO getAdvertisement(GetAdvertisementRequest request){
        template = new WebServiceTemplate(marshaller);
        AdvertisementSoapDTO advertisement = (AdvertisementSoapDTO) template.marshalSendAndReceive("http://localhost:9090/ws", request);
        System.out.println("The advertisement is here: " + advertisement.getName());
        return advertisement;
    }
}
