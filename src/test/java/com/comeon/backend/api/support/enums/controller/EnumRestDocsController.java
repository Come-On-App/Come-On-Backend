package com.comeon.backend.api.support.enums.controller;

import com.comeon.backend.common.response.ProfileImageType;
import com.comeon.backend.meeting.command.domain.MemberRole;
import com.comeon.backend.meeting.command.domain.PlaceCategory;
import com.comeon.backend.user.command.domain.OauthProvider;
import com.comeon.backend.user.command.domain.Role;
import com.comeon.backend.user.command.domain.UserStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enums")
public class EnumRestDocsController {

    @GetMapping("/meeting-member-role")
    public Map<String, String> meetingMemberRoles() {
        return Arrays.stream(MemberRole.values())
                .collect(Collectors.toMap(MemberRole::name, MemberRole::getDescription));
    }

    @GetMapping("/user-role")
    public Map<String, String> userRoles() {
        return Arrays.stream(Role.values())
                .collect(Collectors.toMap(Role::name, Role::getDescription));
    }

    @GetMapping("/oauth-provider")
    public Map<String, String> oauthProviders() {
        return Arrays.stream(OauthProvider.values())
                .collect(Collectors.toMap(OauthProvider::name, OauthProvider::getDescription));
    }

    @GetMapping("/user-status")
    public Map<String, String> userStatus() {
        return Arrays.stream(UserStatus.values())
                .collect(Collectors.toMap(UserStatus::name, UserStatus::getDescription));
    }

    @GetMapping("/place-category")
    public Map<String, String> placeCategory() {
        return Arrays.stream(PlaceCategory.values())
                .collect(Collectors.toMap(PlaceCategory::name, PlaceCategory::getDescription));
    }

    @GetMapping("/profile-image-type")
    public Map<String, String> profileImageTypes() {
        return Arrays.stream(ProfileImageType.values())
                .collect(Collectors.toMap(ProfileImageType::name, ProfileImageType::getDescription));
    }
}
