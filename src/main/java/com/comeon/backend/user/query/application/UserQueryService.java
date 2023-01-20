package com.comeon.backend.user.query.application;

import com.comeon.backend.user.query.dao.UserDao;
import com.comeon.backend.user.query.dto.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserDao userDao;

    public UserDetails findUserDetails(Long userId) {
        return userDao.findUserDetailsByUserId(userId);
    }
}
