package com.Advertisement.Advertisement.endpoints;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.Advertisement.Advertisement.dtos.AdvertisementSoapDTO;
import com.Advertisement.Advertisement.model.Advertisement;
import com.Advertisement.Advertisement.service.AdvertisementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import advertisement.com.javageneratedfiles.GetAdvertisementRequest;
import advertisement.com.javageneratedfiles.GetAdvertisementResponse;

@Endpoint
public class AdvertisementEndpoint {

    private static final String namespace = "http://com.Advertisement/JavaGeneratedFiles";
    
    @Autowired
    AdvertisementService advertisementService;

    @PayloadRoot(namespace = namespace, localPart = "getAdvertisementRequest")
    @ResponsePayload
    public JAXBElement<GetAdvertisementResponse> saveAdvertisementSoap(@RequestPayload GetAdvertisementRequest request){
        GetAdvertisementResponse response = new GetAdvertisementResponse();
        response.setName("Saved");
        QName qName = new QName("getAdvertisementRequest");
        advertisementService.saveSoapAdvertisement(request);

        JAXBElement<GetAdvertisementResponse> jaxbElement = new JAXBElement<GetAdvertisementResponse>(qName, GetAdvertisementResponse.class, response);
        return jaxbElement;
    }
}
