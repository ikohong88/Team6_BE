package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.UserRequestDto;
import com.example.troubleshooter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // 회원가입
    // Request
    // {userId: password: nickname }
    // Response
    // {'ok':true, message:'회원가입 성공' } OR {'ok':false,message:'회원가입 실패'}
    // 문제상황 : ARC를 사용해서 테스트를 진행했지만, 500에러 발생
    // 해결 : HEADER에 Content-Type을 application/json으로 보내, 인자에 @RequestBody가 필요했음
    @PostMapping("/api/signup")
    public void signup(@RequestBody UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
    }

    // 로그인
    //
    @PostMapping("/api/login")
    @ResponseBody
    public void login(@RequestBody UserRequestDto userRequestDto) {

    }
}
