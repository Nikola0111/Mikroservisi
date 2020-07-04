package com.projekat.XML.repository;


import com.projekat.XML.model.ItemInCart;

import org.springframework.data.jpa.repository.JpaRepository;





public interface ItemInCartRepository extends JpaRepository <ItemInCart,Long> {

public ItemInCart findOneByid(Long id);


    
}