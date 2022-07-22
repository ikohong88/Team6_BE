package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.UserRequestDto;
import com.example.troubleshooter.entity.User;
import com.example.troubleshooter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 로직
    public void registerUser(UserRequestDto requestDto) {
        String userId = requestDto.getUserId();
        // DB에서 userId 값을 찾아본다.
        Optional<User> found = userRepository.findByUserId(userId);
        // DB에 같은 값이 있을경우, 예외발생 - 중복 처리
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();

        User user = new User(userId, nickname, password);
        userRepository.save(user);
    }
}
