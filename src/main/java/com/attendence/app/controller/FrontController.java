package com.attendence.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.attendence.app.entity.Users;
import com.attendence.app.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class FrontController {

    @Autowired
    private UserService service;

    @GetMapping("/register")
    public String GetRegister(Model modal){
        Users user= new Users();
        modal.addAttribute("users", user);
        return "register";
    }

    @PostMapping("/register")
    public String RegisterPostMapping(@Valid @ModelAttribute("users") Users users, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "register";
        }

        service.save(users);
        return "login";
    }

    @GetMapping("/login")
    public String GetLogging(){
        return "login";
    }
    
}
