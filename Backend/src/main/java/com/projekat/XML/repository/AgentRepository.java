package com.projekat.XML.repository;

import com.projekat.XML.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    

    public Agent findOneById(Long id);
}
