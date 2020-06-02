package com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EndUser;
import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken save(VerificationToken verificationToken);

    VerificationToken findByEndUser(EndUser endUser);

    VerificationToken findByEndUser_Id(Long id);

    VerificationToken findByToken(String token);

    void deleteByEndUser(EndUser endUser);
}
