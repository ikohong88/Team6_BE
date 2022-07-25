package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.RestApi;
import com.example.troubleshooter.dto.UserRequestDto;
import com.example.troubleshooter.entity.User;
import com.example.troubleshooter.entity.UserRoleEnum;
import com.example.troubleshooter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Object> registerUser(UserRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        String nickname = requestDto.getNickname();


        Optional<User> foundUsername = userRepository.findByUsername(username);
        Optional<User> foundNickname = userRepository.findByNickname(nickname);
        if (foundUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        } else if(foundNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 NICKNAME 이 존재합니다.");
        }
        // 패스워드 일치 확인
        else if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 패스워드 암호화
        password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.ADMIN;

        User user = new User(username, password, nickname, role);
        userRepository.save(user);

        RestApi restApi = new RestApi();
        restApi.setHttpStatus(HttpStatus.OK);
        restApi.setMessage("회원가입이 완료되었습니다.");

        return new ResponseEntity(
                restApi,
                HttpStatus.OK
        );
    }
}