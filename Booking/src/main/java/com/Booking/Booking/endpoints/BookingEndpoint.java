package com.Booking.Booking.endpoints;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import com.Booking.Booking.service.BookingRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import advertisement.com.javageneratedfiles.GetBookingRequest;
import advertisement.com.javageneratedfiles.GetBookingResponse;

@Endpoint
public class BookingEndpoint {
    
    private static final String namespace_uri = "http://com.Advertisement/JavaGeneratedFiles";

    @Autowired
    BookingRequestService bookingRequestService;

    @PayloadRoot(namespace = namespace_uri, localPart = "getBookingRequest")
    @ResponsePayload
    public JAXBElement<GetBookingResponse> saveSoapBooking(@RequestPayload GetBookingRequest request){
        GetBookingResponse response = new GetBookingResponse();
        response.setResponse("Saved");
        QName qName = new QName("getBookingRequest");
        bookingRequestService.saveSoapBooking(request);

        JAXBElement<GetBookingResponse> jaxbElement = new JAXBElement<GetBookingResponse>(qName, GetBookingResponse.class, response);
        return jaxbElement;
    }

}