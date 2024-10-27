package com.attendence.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendence.app.entity.Users;
import com.attendence.app.repository.UserRepository;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;

    public void save(Users users){
        Optional<Users> user=repository.findById(users.getUsername());
        if(user.isEmpty()){
            users.setPassword("{noop}"+users.getPassword());
            users.setRoles(List.of("ROLE_USER"));
            repository.save(users);
        }else{
            return;
        }
    }

    public Optional<Users> findUserById(String id){
        return repository.findById(id);
    }

    public List<Users> getAllUsers(){
        return repository.findAll();
    }

    public boolean isUsernameExisits(String username){
        return repository.existsUsersByUsername(username);
    }
}
