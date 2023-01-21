package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dao.param.FindMeetingSliceParam;
import com.comeon.backend.meeting.query.dao.result.FindMeetingSliceResult;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("meetingDao")
@RequiredArgsConstructor
public class MeetingDao {

    private final SqlSession sqlSession;

    public Slice<FindMeetingSliceResult> findMeetingSlice(Long userId, Pageable pageable, MeetingCondition cond) {
        FindMeetingSliceParam param = new FindMeetingSliceParam(userId, cond, pageable);
        List<FindMeetingSliceResult> results = sqlSession.selectList("findMeetingSlice", param);
        return new SliceImpl<>(results, pageable, hasNext(pageable, results));
    }

    private boolean hasNext(Pageable pageable, List<?> contents) {
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            return true;
        }
        return false;
    }
}
