package com.sparta.weeklytestspring.service;

import com.sparta.weeklytestspring.domain.User;
import com.sparta.weeklytestspring.domain.UserRole;
import com.sparta.weeklytestspring.dto.SignupRequestDto;
import com.sparta.weeklytestspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;

    public User registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 ROLE 확인
        UserRole role = UserRole.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return user;
    }
}
