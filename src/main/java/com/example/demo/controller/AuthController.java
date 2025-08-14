package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.CustomUserDetailsService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;






@Controller
@RequestMapping("/auth")
public class AuthController {
    private final CustomUserDetailsService userDetailsService;

    public AuthController(CustomUserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;   
    }
    // ログインページ
    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }
    
    // 登録ページ
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }
    

    // 登録処理
    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        userDetailsService.registerUser(user); 
        return "redirect:/auth/login";
    }
    

}
