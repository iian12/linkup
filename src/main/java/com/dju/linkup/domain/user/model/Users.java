package com.dju.linkup.domain.user.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @Tsid
    private String id;

    private String email;
    private String password;

    @Column(unique = true, nullable = true)
    private String profileId;

    private String nickname;
    private String profileImgUrl;
    private boolean isSignUp;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String subjectId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Users(String email, String password, String profileId, String nickname, String profileImgUrl,
                 Provider provider, String subjectId, Role role) {
        this.email = email;
        this.password = password;
        this.profileId = profileId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.isSignUp = false;
        this.provider = provider;
        this.subjectId = subjectId;
        this.role = role;
    }
}
