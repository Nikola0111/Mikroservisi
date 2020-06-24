package com.Booking.Booking.service;

import com.Booking.Booking.dtos.ItemInCartDTO;
import com.Booking.Booking.dtos.ItemInCartFrontDTO;
import com.Booking.Booking.model.ItemInCart;
import com.Booking.Booking.repository.ItemInCartRepository;
import org.springframework.http.HttpMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

@Service
public class ItemInCartService {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ItemInCartRepository itemInCartRepository;

    @Autowired
    RestTemplate restTemplate;

    public int save(ItemInCartDTO itemInCartDTO, Long id) {
        List<ItemInCart> all = itemInCartRepository.findAll();

        for (ItemInCart item : all) {
            if (item.getUserId().equals(id) && item.getAdvertisementId().equals(itemInCartDTO.getAdvertisementId())
                    && ((item.getTimeFrom().isAfter(itemInCartDTO.getTimeFrom())
                            && item.getTimeFrom().isBefore(itemInCartDTO.getTimeTo()))
                            || (item.getTimeTo().isAfter(itemInCartDTO.getTimeFrom())
                                    && item.getTimeTo().isBefore(itemInCartDTO.getTimeTo()))
                            || (itemInCartDTO.getTimeFrom().isAfter(item.getTimeFrom())
                                    && itemInCartDTO.getTimeFrom().isBefore(item.getTimeTo()))
                            || (itemInCartDTO.getTimeTo().isAfter(item.getTimeFrom())
                                    && itemInCartDTO.getTimeTo().isBefore(item.getTimeTo()))
                            || (item.getTimeFrom().equals(itemInCartDTO.getTimeFrom())
                                    || item.getTimeFrom().equals(itemInCartDTO.getTimeTo())
                                    || item.getTimeTo().equals(itemInCartDTO.getTimeFrom())
                                    || item.getTimeTo().equals(itemInCartDTO.getTimeTo()))
                                    && item.getTimeTo().equals(itemInCartDTO.getTimeTo()))) {
                return 0;
            }
        }

        ItemInCart item = new ItemInCart(id, itemInCartDTO.getAdvertisementPostedById(),
                itemInCartDTO.getAdvertisementId(), itemInCartDTO.getTimeFrom(), itemInCartDTO.getTimeTo());
        itemInCartRepository.save(item);

        shoppingCartService.addAItemInCart(item.getId(), id);

        return 1;

    }

    public List<ItemInCartFrontDTO> remove(ItemInCart itemInCart, Long id) {

        // itemInCartRepository.delete(itemInCart);
        shoppingCartService.removeItemInCart(itemInCart.getId(), id);

        return shoppingCartService.fotCart(id);

    }

    public void removeAll(Long id) {

        // itemInCartRepository.deleteAll();
        shoppingCartService.removeAll(id);

    }

}
