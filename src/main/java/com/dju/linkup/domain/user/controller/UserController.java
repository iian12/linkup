package com.dju.linkup.domain.user.controller;

import com.dju.linkup.domain.user.dto.UpdateNicknameDto;
import com.dju.linkup.domain.user.service.UserService;
import com.dju.linkup.global.security.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/is-signup")
    public ResponseEntity<Boolean> getIsSignUp(HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);
        boolean isSignUp = userService.isSignUp(token);
        return ResponseEntity.ok(isSignUp);
    }

    @PostMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(
        @RequestBody UpdateNicknameDto dto, HttpServletRequest request) {
        String token = TokenUtils.extractTokenFromRequest(request);
        userService.UpdateNickname(dto, token);
        return ResponseEntity.ok().build();
    }
}
