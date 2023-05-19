package com.dael.daelpay.userservice;

import com.dael.daelpay.domain.User;
import com.dael.daelpay.dto.UserDto;
import com.dael.daelpay.exception.UserAlreadyExistException;
import com.dael.daelpay.repository.UserRepository;
import com.dael.daelpay.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserSignUpTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void signUp_success() {
        UserDto userDto = new UserDto();
        userDto.setName("TestUser");
        userDto.setEmail("testUser@example.com");
        userDto.setPassword("testPassword");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        userService.singUp(userDto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void signUp_failure_existingUser() {
        UserDto userDto = new UserDto();
        userDto.setName("TestUser");
        userDto.setEmail("testUser@example.com");
        userDto.setPassword("testPassword");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mock(User.class)));

        assertThrows(UserAlreadyExistException.class, () -> userService.singUp(userDto));
        verify(userRepository, times(0)).save(any(User.class));
    }
}
