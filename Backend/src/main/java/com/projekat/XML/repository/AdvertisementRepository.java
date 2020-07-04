package com.projekat.XML.repository;

import com.projekat.XML.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    public Advertisement findOneByid(Long id);
    public List<Advertisement> findAll();
    public List<Advertisement> findAllByPostedBy_Id(Long id);
}
