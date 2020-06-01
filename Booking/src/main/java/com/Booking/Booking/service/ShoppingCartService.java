package com.Booking.Booking.service;

import com.Booking.Booking.model.ItemInCart;
import com.Booking.Booking.model.ShoppingCart;
import com.Booking.Booking.repository.ItemInCartRepository;
import com.Booking.Booking.repository.ShoppingCartRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.core.ParameterizedTypeReference;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ItemInCartRepository itemInCartRepository;

    @Autowired
    RestTemplate restTemplate;

public void save(Long id){

    shoppingCartRepository.save(new ShoppingCart(id, new ArrayList<Long>()));

}


public void addAItemInCart(Long itemId){
    ShoppingCart cart=shoppingCartRepository.findOneByuserId(getLogedUserId());
    cart.addOneItemInCart(itemId);
    shoppingCartRepository.save(cart);
}

public void removeItemInCart(Long itemId){
    ShoppingCart cart=shoppingCartRepository.findOneByuserId(getLogedUserId());
    cart.removeOneItemInCart(itemId);
    shoppingCartRepository.save(cart);

}

public void removeAll(){
    System.out.println("OVO JE USER"+getLogedUserId());
    ShoppingCart cart=shoppingCartRepository.findOneByuserId(getLogedUserId());
    cart.removeAllItems();
    shoppingCartRepository.save(cart);

}

public ShoppingCart getShoppingCart(Long id){
    return shoppingCartRepository.findOneByuserId(id);
}


public List<ItemInCart> fotCart(){
    List< Long> oglasi=new ArrayList<>();
    List<ItemInCart> vrati=new ArrayList<ItemInCart>();

    for(ShoppingCart shoppingCart: shoppingCartRepository.findAll()) {
        if(shoppingCart.getUserId().equals(getLogedUserId())){
            oglasi=shoppingCart.getItemInCartList();
        }


    }
    for(ItemInCart itemInCart: itemInCartRepository.findAll()){
    
        for(Long id : oglasi){
        
            if(itemInCart.getId().equals(id)){
             
                vrati.add(itemInCart);
            }
        }

    }
    
return vrati;

}


public Long getLogedUserId(){
    

    Long id = restTemplate.exchange("http://auth/getUserId", HttpMethod.GET, null, 
    new ParameterizedTypeReference<Long>() {} ).getBody();

    System.out.println("Nasao je id="+id);

    return id;
}

    


}