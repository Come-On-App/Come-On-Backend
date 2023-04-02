package com.comeon.backend.report.infrastructure;

import com.comeon.backend.report.command.domain.Report;
import com.comeon.backend.report.command.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {

    private final ReportJpaRepository reportJpaRepository;

    @Override
    public Report save(Report report) {
        return reportJpaRepository.save(report);
    }
}
