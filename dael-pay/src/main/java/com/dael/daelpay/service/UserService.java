package com.dael.daelpay.service;

import com.dael.daelpay.domain.User;
import com.dael.daelpay.dto.UserFormDto;
import com.dael.daelpay.exception.UserAlreadyExistException;
import com.dael.daelpay.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private void validateDuplicateUser(UserFormDto userFormDto) {
        if (userRepository.findByEmail(userFormDto.getEmail()).isPresent()){
            throw new UserAlreadyExistException("이미 가입한 회원입니다");
        }
    }

    public void signUp(UserFormDto userFormDto) {
        validateDuplicateUser(userFormDto);
        User user = User.makeUser(userFormDto, bCryptPasswordEncoder);
        userRepository.save(user);
    }
}
