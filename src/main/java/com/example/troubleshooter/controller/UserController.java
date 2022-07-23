package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.UserRequestDto;
import com.example.troubleshooter.security.UserDetailsImpl;
import com.example.troubleshooter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 테스트용 - 로그인시 @AuthenticationPrincipal 정상 작동?
    @GetMapping("/")
    @ResponseBody
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getUsername();
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
    // 시큐리티에서 처리를 하기 떄문에 controller에 구현 필요 X
//    @PostMapping("/api/login")
//    @ResponseBody
//    public void login(@RequestBody UserRequestDto userRequestDto) {
//
//    }
}
