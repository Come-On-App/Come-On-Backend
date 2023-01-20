package com.comeon.backend.user.query.dao;

import com.comeon.backend.user.query.dto.UserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository("userDao")
@RequiredArgsConstructor
public class UserDao {

    private final SqlSession sqlSession;

    public UserDetails findUserDetailsByUserId(Long userId) {
        return sqlSession.selectOne("findUserDetailsByUserId", userId);
    }
}
