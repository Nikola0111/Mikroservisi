package com.projekat.XML.repository;
import java.util.List;

import com.projekat.XML.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    public ShoppingCart findOneByid(Long id);
    public ShoppingCart findOneByuserId(Long id);
    public List<ShoppingCart> findAll();
}

