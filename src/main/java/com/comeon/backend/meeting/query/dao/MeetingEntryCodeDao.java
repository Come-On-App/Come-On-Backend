package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dao.param.FindEntryCodeDetailsParam;
import com.comeon.backend.meeting.query.dao.result.FindEntryCodeDetailsResult;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository("meetingEntryCodeDao")
@RequiredArgsConstructor
public class MeetingEntryCodeDao {

    private final SqlSession sqlSession;

    public FindEntryCodeDetailsResult findEntryCodeDetailsByMeetingId(FindEntryCodeDetailsParam param) {
        return sqlSession.selectOne("findEntryCodeDetailsByMeetingId", param);
    }
}
