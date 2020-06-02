package com.Booking.Booking.service;

import com.Booking.Booking.dtos.ItemInCartDTO;
import com.Booking.Booking.model.ItemInCart;
import com.Booking.Booking.repository.ItemInCartRepository;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

@Service
public class ItemInCartService {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ItemInCartRepository itemInCartRepository;

    @Autowired
    RestTemplate restTemplate;

    public void save(ItemInCartDTO itemInCartDTO) {

        ItemInCart item = new ItemInCart(getLogedUserId(),itemInCartDTO.getAdvertisementPostedById(),itemInCartDTO.getAdvertisementId(), itemInCartDTO.getTimeFrom(),
                itemInCartDTO.getTimeTo());
        itemInCartRepository.save(item);


        System.out.println("Item in cart id je="+item.getId());

        shoppingCartService.addAItemInCart(item.getId());

    }

    public List<ItemInCart> remove(ItemInCart itemInCart) {

        //itemInCartRepository.delete(itemInCart);
        shoppingCartService.removeItemInCart(itemInCart.getId());

        return shoppingCartService.fotCart();

    }

    public void removeAll(){

        //itemInCartRepository.deleteAll();
        shoppingCartService.removeAll();

        
    }

 
public Long getLogedUserId(){
    

    Long id = restTemplate.exchange("http://auth/getUserId", HttpMethod.GET, null, 
    new ParameterizedTypeReference<Long>() {} ).getBody();

    System.out.println("Nasao je id="+id);

    return id;
}



}
