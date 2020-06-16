package com.Booking.Booking.controller;

import java.util.ArrayList;
import java.util.List;

import com.Booking.Booking.dtos.ItemInCartDTO;
import com.Booking.Booking.model.ItemInCart;
import com.Booking.Booking.service.ItemInCartService;
import com.Booking.Booking.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "itemInCart")
public class ItemInCartController {

	@Autowired
	private ItemInCartService itemInCartService;

	@PreAuthorize("hasAuthority('itemInCart:write')")
	@PostMapping(value = "/addItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addAdvToCart(@RequestBody ItemInCartDTO itemInCartDTO) {

		System.out.println("POGODIO");
		itemInCartService.save(itemInCartDTO);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('itemInCart:write')")
	@PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ItemInCart>> removeFromCart(@RequestBody ItemInCart itemInCart) {

		List<ItemInCart> items = itemInCartService.remove(itemInCart);

		return new ResponseEntity<>(items, HttpStatus.OK);
	}

}