package com.projekat.XML.repository;

import com.projekat.XML.model.CarReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarReportRepository extends JpaRepository<CarReport, Long> {
}
