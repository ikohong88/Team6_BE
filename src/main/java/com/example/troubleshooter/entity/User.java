package com.example.troubleshooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
// 스프링 버전업으로 인한 User테이블 생성 시도시 에러발생
// 해결 : UserTable로 테이블명 변경
@Table(name="UserTable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 사용자 ID
    @Column(nullable = false, unique = true)
    private String userId;

    // 사용자 닉네임
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    public User(String userId, String nickname, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }
}
