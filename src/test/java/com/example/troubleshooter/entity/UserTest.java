package com.example.troubleshooter.entity;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("정상 케이스")
    void createProduct_Normal() {
        // given
        Long Id = 100L;
        String username = "test";
        String password = "test";
        String nickname = "nickname";
        UserRoleEnum role =  UserRoleEnum.ADMIN;

//        UserRequestDto requestDto = new UserRequestDto(
//                userId,
//                password,
//                nickname
//        );

        // when
        User user = new User(username, password, nickname, role);

        // then
        assertNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(nickname, user.getNickname());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}