package com.projekat.XML.repository;

import com.projekat.XML.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository <Message, Long>{

}