package com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository;




import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.KeyPairClass;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface KeyPairClassRepository extends  JpaRepository<KeyPairClass, Long>  {
    

    public KeyPairClass findOneById(Long id);
}