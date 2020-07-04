package com.projekat.XML.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.projekat.XML.model.*;


public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    public FuelType findOneByid(Long id);
    public List<FuelType> findAll();
    public void deleteByCode(String code);
    public FuelType findByName(String name);
}