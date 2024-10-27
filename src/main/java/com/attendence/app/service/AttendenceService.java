package com.attendence.app.service;

import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.attendence.app.entity.Attendence;
import com.attendence.app.entity.Users;
import com.attendence.app.repository.AttendenceRepository;

@Service
public class AttendenceService {

    @Autowired
    private AttendenceRepository attendenceRepository;

    public void SignIn(Users user, String Date, String time){
        Optional<Attendence> users=attendenceRepository.getAttendenceByUsersAndDate(user, Date);
        if(users.isPresent()){
            Attendence att=users.get();
            if(att.getSign_in_time()==null){
                att.setSign_in_time(time);
                att.setStatus("sign_in");
                attendenceRepository.save(att);
            }
        }else{
            Attendence atuser=new Attendence();
            atuser.setDate(Date);
            atuser.setSign_in_time(time);
            atuser.setUsers(user);
            atuser.setStatus("sign_in");
            attendenceRepository.save(atuser);
        }
    }

    public void signOut(Users user, String Date, String time){
        Optional<Attendence> users=attendenceRepository.getAttendenceByUsersAndDate(user, Date);
        if(users.isPresent()){
            Attendence ps=users.get();
            ps.setSign_out_time(time);
            ps.setStatus("sign_out");
            attendenceRepository.save(ps);
        }
    }

    public List<Attendence> getAttendenceByUsers(Users user){
        return attendenceRepository.getAttendenceByUsers(user);
    }

    public Optional<Attendence> getAttendeceByUserAndDate(Users users, String Date){
        return attendenceRepository.getAttendenceByUsersAndDate(users, Date);
    }
}
