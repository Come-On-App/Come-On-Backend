package com.comeon.backend.user.query;

public interface UserDao {

    UserDetails findUserDetails(Long userId);
    UserSimple findUserSimple(Long userId);
}
