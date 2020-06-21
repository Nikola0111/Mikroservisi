package com.projekat.XML.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.projekat.XML.model.requests.BookingRequest;




public interface BookingRequestRepository extends JpaRepository <BookingRequest,Long> {

public BookingRequest findOneByid(Long id);

public List<BookingRequest> findAll();

public List<BookingRequest> findByUserForId(Long userForId);

public List<BookingRequest> findByUserToId(Long userForId);


public List<BookingRequest> findAllByGroupId(Long groupId);

public BookingRequest findTopByOrderByIdDesc();
    
}