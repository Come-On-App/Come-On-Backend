package com.comeon.backend.user.infrastructure.query;

import com.comeon.backend.user.query.UserDetails;
import com.comeon.backend.user.query.UserSimple;
import com.comeon.backend.user.query.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserMapper userMapper;

    @Override
    public UserDetails findUserDetails(Long userId) {
        return userMapper.selectUserDetailsByUserId(userId);
    }

    @Override
    public UserSimple findUserSimple(Long userId) {
        return userMapper.selectUserSimpleByUserId(userId);
    }
}
