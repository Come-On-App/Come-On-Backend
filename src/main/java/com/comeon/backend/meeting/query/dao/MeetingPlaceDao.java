package com.comeon.backend.meeting.query.dao;

import com.comeon.backend.meeting.query.dao.result.PlaceListResult;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("meetingPlaceDao")
@RequiredArgsConstructor
public class MeetingPlaceDao {

    private final SqlSession sqlSession;

    public List<PlaceListResult> findPlaceList(Long meetingId) {
        return sqlSession.selectList("findPlaceList", meetingId);
    }

}
