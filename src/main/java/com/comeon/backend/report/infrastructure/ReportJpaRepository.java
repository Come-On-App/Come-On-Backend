package com.comeon.backend.report.infrastructure;

import com.comeon.backend.report.command.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
}
