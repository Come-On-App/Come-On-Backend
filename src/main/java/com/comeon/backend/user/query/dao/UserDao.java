package com.comeon.backend.user.query.dao;

import com.comeon.backend.user.query.dao.dto.UserDetailsResponse;
import com.comeon.backend.user.query.dao.dto.UserSimpleResponse;

public interface UserDao {

    UserDetailsResponse findUserDetails(Long userId);
    UserSimpleResponse findUserSimple(Long userId);
}
