package com.projekat.XML.endpoints;

import com.projekat.XML.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xml.projekat.com.javageneratedfiles.GetAdvertisementRequest;
import xml.projekat.com.javageneratedfiles.GetAdvertisementResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

@Endpoint
public class AdvertisementEndpoint {
    private static final String NAMESPACE_URI = "http://com.projekat.XML/JavaGeneratedFiles";

    @Autowired
    public AdvertisementService advertisementService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAdvertisementRequest")
    @ResponsePayload
    public JAXBElement<String> saveAdvertisement(@RequestPayload GetAdvertisementRequest request){
        GetAdvertisementResponse response = new GetAdvertisementResponse();
        response.setName("Saved");
        QName qName = new QName("getAdvertisementRequest");
        advertisementService.saveSoapAdvertisement(request);

        JAXBElement jaxbElement = new JAXBElement<GetAdvertisementResponse>(qName, GetAdvertisementResponse.class, response);
        return jaxbElement;
    }
}
