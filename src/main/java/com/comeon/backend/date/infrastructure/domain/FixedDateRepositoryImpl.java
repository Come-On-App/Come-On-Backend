package com.comeon.backend.date.infrastructure.domain;

import com.comeon.backend.date.command.domain.confirm.FixedDate;
import com.comeon.backend.date.command.domain.confirm.FixedDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FixedDateRepositoryImpl implements FixedDateRepository {

    private final FixedDateJpaRepository fixedDateJpaRepository;

    @Override
    public FixedDate save(FixedDate fixedDate) {
        return fixedDateJpaRepository.save(fixedDate);
    }

    @Override
    public Optional<FixedDate> findFixedDateByMeetingId(Long meetingId) {
        return fixedDateJpaRepository.findByMeetingId(meetingId);
    }
}
