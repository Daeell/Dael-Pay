package com.dael.daelpay.controller;

import com.dael.daelpay.dto.UserFormDto;
import com.dael.daelpay.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public String signUp(@ModelAttribute UserFormDto userFormDto) {
        System.out.println("apiController Signup method called");
        userService.signUp(userFormDto);
        return "redirect:/login";
    }
}
