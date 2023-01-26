package com.comeon.backend.user.query.infra;

import com.comeon.backend.user.query.dao.UserDao;
import com.comeon.backend.user.query.dao.dto.UserDetailsResponse;
import com.comeon.backend.user.query.dao.dto.UserSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserMapper userMapper;

    @Override
    public UserDetailsResponse findUserDetails(Long userId) {
        return userMapper.selectUserDetailsByUserId(userId);
    }

    @Override
    public UserSimpleResponse findUserSimple(Long userId) {
        return userMapper.selectUserSimpleByUserId(userId);
    }
}
