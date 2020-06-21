package com.projekat.XML.repository;

import com.projekat.XML.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken save(VerificationToken verificationToken);

    VerificationToken findByUser(EndUser endUser);

    VerificationToken findByToken(String token);

    void deleteByUser(EndUser endUser);
}
