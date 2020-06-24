package com.Booking.Booking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.Booking.Booking.dtos.ItemInCartDTO;
import com.Booking.Booking.dtos.ItemInCartFrontDTO;
import com.Booking.Booking.model.ItemInCart;
import com.Booking.Booking.repository.ItemInCartRepository;
import com.Booking.Booking.service.ItemInCartService;
import com.Booking.Booking.service.SessionService;
import com.Booking.Booking.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

@RestController
// @RequestMapping(value = "itemInCart")
public class ItemInCartController {

	@Autowired
	private ItemInCartService itemInCartService;

	@Autowired
	private ItemInCartRepository itemInCartRepository;

	@Autowired
	SessionService sessionService;

	@Autowired
	RestTemplate restTemplate;

	@PreAuthorize("hasAuthority('itemInCart:write')")
	@PostMapping(value = "/addItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> addAdvToCart(@RequestBody ItemInCartDTO itemInCartDTO, HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");
		HttpEntity<String> entity = sessionService.makeAuthorizationHeader(authorization);
		Long id = restTemplate
				.exchange("http://auth/getUserId", HttpMethod.GET, entity, new ParameterizedTypeReference<Long>() {
				}).getBody();

		int indicator = itemInCartService.save(itemInCartDTO, id);
		if (indicator == 1) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@PreAuthorize("hasAuthority('itemInCart:write')")
	@PostMapping(value = "/remove", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ItemInCartFrontDTO>> removeFromCart(@RequestBody ItemInCart itemInCart,
			HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		HttpEntity<String> entity = sessionService.makeAuthorizationHeader(authorization);
		Long id = restTemplate
				.exchange("http://auth/getUserId", HttpMethod.GET, entity, new ParameterizedTypeReference<Long>() {
				}).getBody();

		List<ItemInCartFrontDTO> items = itemInCartService.remove(itemInCart, id);
		return new ResponseEntity<>(items, HttpStatus.OK);
	}

}