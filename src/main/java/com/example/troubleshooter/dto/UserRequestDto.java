package com.example.troubleshooter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
// 생성자 자동 생성
@AllArgsConstructor
public class UserRequestDto {
    // 회원 가입
    private String userId;
    private String password;
    private String nickname;
}
