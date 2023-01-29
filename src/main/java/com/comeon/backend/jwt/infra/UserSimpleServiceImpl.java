package com.comeon.backend.jwt.infra;

import com.comeon.backend.jwt.application.UserSimple;
import com.comeon.backend.jwt.application.UserSimpleService;
import com.comeon.backend.user.query.dao.UserDao;
import com.comeon.backend.user.query.dao.UserSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSimpleServiceImpl implements UserSimpleService {

    private final UserDao userDao;

    @Override
    public UserSimple loadUserSimple(Long userId) {
        UserSimpleResponse response = userDao.findUserSimple(userId);
        return new UserSimple(response.getUserId(), response.getNickname(), "ROLE_" + response.getRole());
    }
}
