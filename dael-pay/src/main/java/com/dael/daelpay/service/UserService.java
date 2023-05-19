package com.dael.daelpay.service;

import com.dael.daelpay.domain.User;
import com.dael.daelpay.dto.UserDto;
import com.dael.daelpay.exception.UserAlreadyExistException;
import com.dael.daelpay.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void singUp(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("이미 존재하는 회원입니다");
        }
        User user = User.makeUser(userDto, passwordEncoder);
        userRepository.save(user);
    }
}
