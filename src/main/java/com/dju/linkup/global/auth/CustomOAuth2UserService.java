package com.dju.linkup.global.auth;

import com.dju.linkup.domain.user.model.Provider;
import com.dju.linkup.domain.user.model.Role;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Provider oauthProvider = getProviderFromRegistrationId(provider);

        String email = getAttribute(oAuth2User, provider, "email");
        String picture = getAttribute(oAuth2User, provider, "picture");
        String subjectId = getAttribute(oAuth2User, provider, "sub");
        String nickname = getAttribute(oAuth2User, provider, "name");

        Users user = Users.builder()
                .email(email)
                .profileImgUrl(picture)
                .subjectId(subjectId)
                .nickname(nickname)
                .provider(oauthProvider)
                .role(Role.USER)
                .build();
        userRepository.save(user);

        return new CustomUserDetail(user, user.getId());
    }

    private Provider getProviderFromRegistrationId(String registrationId) {
        if (registrationId.equals("google")) {
            return Provider.GOOGLE;
        }

        throw new OAuth2AuthenticationException(new OAuth2Error("Unknown provider: " + registrationId));
    }

    private String getAttribute(OAuth2User oAuth2User, String provider, String attributeName) {
        return switch (provider) {
            case "google" -> (String) oAuth2User.getAttributes().get(attributeName);

            default -> throw new OAuth2AuthenticationException(new OAuth2Error("Unknown provider: " + provider));
        };
    }
}
