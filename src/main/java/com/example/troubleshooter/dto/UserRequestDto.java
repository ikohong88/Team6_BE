package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.User;
import lombok.Data;

@Data
public class UserRequestDto {
    private String userId;
    private String password;
    private String nickname;

    public UserRequestDto(User user) {
        userId = user.getUserId();
        password = user.getPassword();
        nickname = user.getNickname();
    }
}
