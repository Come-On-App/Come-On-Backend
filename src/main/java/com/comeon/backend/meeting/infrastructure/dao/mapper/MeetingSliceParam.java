package com.comeon.backend.meeting.infrastructure.dao.mapper;

import com.comeon.backend.meeting.query.dao.MeetingSliceCondition;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MeetingSliceParam {

    private Long userId;

    private List<String> words;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    private int pageSize;
    private long offset;

    public MeetingSliceParam(Long userId, MeetingSliceCondition cond, Pageable pageable) {
        this.userId = userId;
        this.dateFrom = cond.getDateFrom();
        this.dateTo = cond.getDateTo();
        this.pageSize = pageable.getPageSize() + 1;
        this.offset = pageable.getOffset();
        if (cond.getSearchWords() != null) {
            this.words = Arrays.stream(cond.getSearchWords().split(" "))
                    .distinct()
                    .collect(Collectors.toList());
        }
    }
}
