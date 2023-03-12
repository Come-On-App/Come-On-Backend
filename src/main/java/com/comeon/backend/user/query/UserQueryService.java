package com.comeon.backend.user.query;

import com.comeon.backend.common.response.ProfileImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryService {

    @Value("${user.default-image}")
    private String defaultImageUrl;

    private final UserDao userDao;

    public UserDetailsResponse findUserDetails(Long userId) {
        UserDetails userDetails = userDao.findUserDetails(userId);

        ProfileImageType profileImageType = userDetails.getProfileImageUrl() == null
                ? ProfileImageType.DEFAULT
                : ProfileImageType.CUSTOM;
        String profileImageUrl = userDetails.getProfileImageUrl() == null
                ? defaultImageUrl
                : userDetails.getProfileImageUrl();

        return new UserDetailsResponse(
                userDetails.getUserId(),
                userDetails.getNickname(),
                profileImageType,
                profileImageUrl,
                userDetails.getRole(),
                userDetails.getEmail(),
                userDetails.getName()
        );
    }
}
