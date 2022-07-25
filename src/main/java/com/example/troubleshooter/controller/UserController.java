package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.UserRequestDto;
import com.example.troubleshooter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/signup")
    @ResponseBody
    public ResponseEntity<Object> registerUser(@RequestBody UserRequestDto requestDto) {
        return userService.registerUser(requestDto);
    }
}