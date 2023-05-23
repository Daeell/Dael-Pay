package com.dael.daelpay.service;

import com.dael.daelpay.domain.User;
import com.dael.daelpay.dto.UserDto;
import com.dael.daelpay.exception.UserAlreadyExistException;
import com.dael.daelpay.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void signUp(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("이미 존재하는 회원입니다");
        }
        User user = User.makeUser(userDto, bCryptPasswordEncoder);
        userRepository.save(user);
    }
}
