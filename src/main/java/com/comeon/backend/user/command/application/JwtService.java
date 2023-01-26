package com.comeon.backend.user.command.application;

import com.comeon.backend.user.command.application.dto.LoginDto;

public interface JwtService {

    LoginDto.OauthLoginResponse issueTokens(Long userId, String nickname, String authorities);
}
