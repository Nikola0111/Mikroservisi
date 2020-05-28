package com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    
}
