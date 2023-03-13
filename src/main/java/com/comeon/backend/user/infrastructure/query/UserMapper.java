package com.comeon.backend.user.infrastructure.query;

import com.comeon.backend.user.query.UserDetails;
import com.comeon.backend.user.query.UserSimple;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDetails selectUserDetailsByUserId(Long userId);
    UserSimple selectUserSimpleByUserId(Long userId);
}
