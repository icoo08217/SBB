package com.ll.exam.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호는 암호화 하여 저정하기 위함이다.
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }
}
