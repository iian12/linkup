package com.dju.linkup.domain.user.service;

import com.dju.linkup.domain.user.dto.UpdateNicknameDto;
import com.dju.linkup.domain.user.model.Users;
import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.global.security.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void UpdateNickname(UpdateNicknameDto dto, String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.updateNickname(dto.getNickname());
        if (!user.isSignUp())
            user.updateSignUp();
    }

    public boolean isSignUp(String token) {
        Users user = userRepository.findById(TokenUtils.getUserIdFromToken(token))
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.isSignUp();
    }
}
