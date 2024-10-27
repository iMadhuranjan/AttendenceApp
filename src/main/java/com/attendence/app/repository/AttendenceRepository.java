package com.attendence.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendence.app.entity.Attendence;
import com.attendence.app.entity.Users;

@Repository
public interface AttendenceRepository extends JpaRepository<Attendence, Integer>{

    List<Attendence> getAttendenceByUsers(Users users);

    Optional<Attendence> getAttendenceByUsersAndDate(Users user, String Date);
    
}