package com.dael.daelpay.controller;

import com.dael.daelpay.dto.UserFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        System.out.println("signup get method called");
        return "signup";
    }
}
