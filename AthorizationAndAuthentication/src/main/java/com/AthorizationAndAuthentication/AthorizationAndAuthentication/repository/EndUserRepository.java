package com.AthorizationAndAuthentication.AthorizationAndAuthentication.repository;

import com.AthorizationAndAuthentication.AthorizationAndAuthentication.model.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EndUserRepository extends JpaRepository<EndUser, Long> {

    public List<EndUser> findByActivity(boolean activity);

    public List<EndUser> findByAdminApproved(boolean admin);

    public List<EndUser> findAllByActivity(boolean act);

    public EndUser findOneById(Long id);
}
