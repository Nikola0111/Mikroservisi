package com.Booking.Booking.service;

import com.Booking.Booking.dtos.ItemInCartDTO;
import com.Booking.Booking.model.ItemInCart;
import com.Booking.Booking.repository.ItemInCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemInCartService {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ItemInCartRepository itemInCartRepository;

    public void save(ItemInCartDTO itemInCartDTO) {

        ItemInCart item = new ItemInCart(itemInCartDTO.getAdvertisement(), itemInCartDTO.getTimeFrom(),
                itemInCartDTO.getTimeTo());
        itemInCartRepository.save(item);

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

}
