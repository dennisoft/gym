package com.win.components.reports;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<Reports, Integer> {
    List<Reports> findAll();
    List<Reports> findReportsByUserID(Integer ID);
    Reports findAllByUserID(Integer ID);
    Reports findReportsBySubmissionDate(Date myDate);
}
