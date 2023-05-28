package com.dael.daelpay.controller;

import com.dael.daelpay.dto.UserFormDto;
import com.dael.daelpay.dto.UserLoginDto;
import com.dael.daelpay.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApiController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserApiController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 계정 정보를 만들고 회원가입을 진행한다.")
    public ResponseEntity<?> signUp(@RequestBody UserFormDto userFormDto) {
        userService.signUp(userFormDto);
        return ResponseEntity.ok(userFormDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "기존 가입 회원 정보를 확인하고 일치할 경우 로그인을 진행한다.")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok().build();
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "인증을 해제하고 로그아웃을 진행한다.")
    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
