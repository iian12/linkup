package com.dju.linkup.global.auth;

import com.dju.linkup.domain.user.repository.UserRepository;
import com.dju.linkup.global.security.JwtTokenProvider;
import com.dju.linkup.global.security.TokenDto;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public AuthService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

}
