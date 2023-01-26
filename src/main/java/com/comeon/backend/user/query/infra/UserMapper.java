package com.comeon.backend.user.query.infra;

import com.comeon.backend.user.query.dao.dto.UserDetailsResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDetailsResponse selectUserDetailsByUserId(Long userId);
}
