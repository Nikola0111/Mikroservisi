package com.Booking.Booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.Booking.Booking.dtos.ItemInCartFrontDTO;
import com.Booking.Booking.service.SessionService;
import com.Booking.Booking.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;



@RestController
// @RequestMapping(value = "shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RestTemplate restTemplate;

    @PreAuthorize("hasAuthority('itemInCart:read')")
    @GetMapping(value = "/forCart")
    public ResponseEntity<List<ItemInCartFrontDTO>> getAllForCart(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        HttpEntity<String> entity = sessionService.makeAuthorizationHeader(authorization);

        Long id = restTemplate
                .exchange("http://auth/getUserId", HttpMethod.GET, entity, new ParameterizedTypeReference<Long>() {
                }).getBody();



              
        List<ItemInCartFrontDTO> items = shoppingCartService.fotCart(id);

        System.out.println("pogodio je kontroler, broj oglasa vraca==" + items.size());

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping(value = "/createShoopingCart")
    public ResponseEntity<Long> createNewCart(@RequestBody Long userId) {

        shoppingCartService.save(userId);

        return new ResponseEntity<Long>(userId, HttpStatus.OK);
    }

}