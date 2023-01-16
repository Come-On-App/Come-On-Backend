package com.comeon.backend.user.domain;

import com.comeon.backend.common.model.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // oauth 정보 시작
    private String oauthId;
    @Enumerated(EnumType.STRING)
    private OauthProvider provider;
    private String email;
    private String name;
    // oauth 정보 종료

    private String nickname;

    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Builder
    public User(String oauthId, OauthProvider provider, String email, String name) {
        this.oauthId = oauthId;
        this.provider = provider;
        this.email = email;
        this.name = name;

        this.nickname = name;
        this.role = Role.USER;
        this.status = UserStatus.ACTIVATE;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
