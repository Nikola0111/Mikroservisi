package com.projekat.XML.repository;

import com.projekat.XML.model.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {

    public CarDetails findByCode(String code);

    public CarDetails findByName(String name);

    public void deleteByCode(String code);
}
