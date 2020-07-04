package com.projekat.XML.controller;

import java.util.List;

import com.projekat.XML.dtos.ItemInCartDTO;
import com.projekat.XML.model.ItemInCart;
import com.projekat.XML.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "booking")
public class ShoppingCartController {


    @Autowired
	private ShoppingCartService shoppingCartService;
    


    @GetMapping(value = "/forCart")
    public ResponseEntity<List<ItemInCart>> getAllForCart() {
		
	   
		List<ItemInCart> items = shoppingCartService.fotCart();

		System.out.println("pogodio je kontroler, broj oglasa vraca=="+items.size());
		
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


   
    
}