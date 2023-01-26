package com.comeon.backend.user.query.infra;

import com.comeon.backend.user.query.dao.dto.UserDetailsResponse;
import com.comeon.backend.user.query.dao.dto.UserSimpleResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDetailsResponse selectUserDetailsByUserId(Long userId);
    UserSimpleResponse selectUserSimpleByUserId(Long userId);
}
