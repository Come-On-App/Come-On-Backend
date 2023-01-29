package com.comeon.backend.user.infrastructure.query;

import com.comeon.backend.user.query.dao.UserDetailsResponse;
import com.comeon.backend.user.query.dao.UserSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDetailsResponse selectUserDetailsByUserId(Long userId);
    UserSimpleResponse selectUserSimpleByUserId(Long userId);
}
