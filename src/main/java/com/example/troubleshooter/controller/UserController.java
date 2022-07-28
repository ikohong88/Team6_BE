package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.UserRequestDto;
import com.example.troubleshooter.service.KakaoUserService;
import com.example.troubleshooter.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    @ResponseBody
    public ResponseEntity<Object> registerUser(@RequestBody UserRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }

    //카카오톡 로그인
    @GetMapping("/api/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}