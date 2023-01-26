package com.comeon.backend.user.query.dao;

import com.comeon.backend.user.query.dao.dto.UserDetailsResponse;

public interface UserDao {

    UserDetailsResponse findUserDetails(Long userId);
}
