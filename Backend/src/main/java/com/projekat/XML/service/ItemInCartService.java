package com.projekat.XML.service;

import com.projekat.XML.dtos.ItemInCartDTO;
import com.projekat.XML.model.EntityUser;
import com.projekat.XML.model.ItemInCart;
import com.projekat.XML.repository.ItemInCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemInCartService {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ItemInCartRepository itemInCartRepository;

    @Autowired
    UserService userService;

    public void save(ItemInCartDTO itemInCartDTO) {

        EntityUser user = userService.getLoggedUser();
        ItemInCart item = new ItemInCart(user,itemInCartDTO.getAdvertisement(), itemInCartDTO.getTimeFrom(),
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
