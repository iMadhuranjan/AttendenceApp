package com.attendence.app.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.attendence.app.entity.Attendence;
import com.attendence.app.entity.Users;
import com.attendence.app.security.Helper;
import com.attendence.app.service.AttendenceService;
import com.attendence.app.service.UserService;

@Controller
@RequestMapping("/user")
public class AttendenceController {

@Autowired
private AttendenceService attendenceService;

@Autowired
private UserService userService;

    private final DateTimeFormatter dateformat=DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("hh:mm:ss");

    // @PostMapping("/dashboard")
    // public String getPostMapping(){
    //     return "user/dashboard";
    // }

    @GetMapping("/dashboard")
    public String AttendenceHomePage(Model modal, Authentication authentication){
        String  username = Helper.getuserNameofLoginUser(authentication);
        Users LoggedInUser=userService.findUserById(username).orElseThrow(()-> new RuntimeException("User Not Found"));
        Optional<Attendence> userAttendence=attendenceService.getAttendeceByUserAndDate(LoggedInUser, LocalDateTime.now().format(dateformat));
       boolean isSignIn= userAttendence.isPresent() && "sign_in".equals(userAttendence.get().getStatus());
       modal.addAttribute("time", LocalDateTime.now().format(timeFormatter));
       modal.addAttribute("date", LocalDateTime.now().format(dateformat));
       modal.addAttribute("isSignIn", isSignIn);
       return "user/dashboard";
    }

    @PostMapping("/signin")
    public String SignIn(Users user,  String Date, String time, Authentication authentication){
        String  username = Helper.getuserNameofLoginUser(authentication);
        Users LoggedInUser=userService.findUserById(username).orElseThrow(()-> new RuntimeException("User Not Found"));

        attendenceService.SignIn(LoggedInUser, LocalDateTime.now().format(dateformat), LocalDateTime
        .now().format(timeFormatter));

        return "redirect:/user/dashboard";
        
    }

    @PostMapping("/signout")
    public String signout(Users user,  String Date, String time, Authentication  authentication){
        String  username = Helper.getuserNameofLoginUser(authentication);
        Users LoggedInUser=userService.findUserById(username).orElseThrow(()-> new RuntimeException("User Not Found"));
        attendenceService.signOut(LoggedInUser, LocalDateTime.now().format(dateformat), LocalDateTime
        .now().format(timeFormatter));
        return "redirect:/user/dashboard";
        
    }

    @GetMapping("/reports")
    public String UserReports(Model modal, Authentication authentication){
        String  username = Helper.getuserNameofLoginUser(authentication);
        Users LoggedInUser=userService.findUserById(username).orElseThrow(()-> new RuntimeException("User Not Found"));
        List<Attendence> StudentReport=attendenceService.getAttendenceByUsers(LoggedInUser);
        
        modal.addAttribute("userReport", StudentReport);
        modal.addAttribute("username", username);
        return "/user/report";
    }

    
    
}
