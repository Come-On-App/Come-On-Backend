package com.comeon.backend.user.query.dao;

public interface UserDao {

    UserDetailsResponse findUserDetails(Long userId);
    UserSimpleResponse findUserSimple(Long userId);
}
