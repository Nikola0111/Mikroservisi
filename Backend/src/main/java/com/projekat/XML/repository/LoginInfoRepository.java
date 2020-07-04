package com.projekat.XML.repository;

import com.projekat.XML.model.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

    public LoginInfo findByEmail(String password);
    public LoginInfo findByUsername(String username);
    public LoginInfo findOneById(Long id);
}
