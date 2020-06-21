package com.projekat.XML.repository;

import com.projekat.XML.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    public List<Grade> findAllByAd_Id(Long id);

}
