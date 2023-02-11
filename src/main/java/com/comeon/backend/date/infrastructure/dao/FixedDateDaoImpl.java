package com.comeon.backend.date.infrastructure.dao;

import com.comeon.backend.date.query.dao.FixedDateDao;
import com.comeon.backend.date.query.dto.MeetingFixedDateSimple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FixedDateDaoImpl implements FixedDateDao {

    private final FixedDateMapper fixedDateMapper;

    @Override
    public MeetingFixedDateSimple findFixedDateSimple(Long meetingId) {
        return fixedDateMapper.selectFixedDateSimple(meetingId);
    }
}
