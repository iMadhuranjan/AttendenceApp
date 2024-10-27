package com.attendence.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.attendence.app.entity.Attendence;
import com.attendence.app.entity.Users;
import com.attendence.app.repository.UserRepository;
import com.attendence.app.service.AttendenceService;
import com.attendence.app.service.UserService;

@Controller
// @RequestMapping("/admin")
public class AdminReportController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttendenceService attendenceService;

    @GetMapping("admin/report")
    public String AdminReport(Model modal){
      List<Users> allUsers=  userService.getAllUsers();
      modal.addAttribute("allusers", allUsers);
      return "/admin/reports";
    }

    @GetMapping("/admin/user/{id}")
    public String AdminUserReport(@PathVariable String id, Model modal ){
        List<Attendence> AllAttendence=attendenceService.getAttendenceByUsers(userService.findUserById(id)
        .orElseThrow(()-> new RuntimeException("User Not Found")));
        modal.addAttribute("stud", AllAttendence);
        return "admin/userreport";
    }

}
