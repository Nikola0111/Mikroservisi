package com.Booking.Booking.controller;

import com.Booking.Booking.service.SessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class SessionController {

    @Autowired
    SessionService sessionService;

    @GetMapping(value = "/logout")
    public ResponseEntity logOut() {

        sessionService.invalidateSession();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}