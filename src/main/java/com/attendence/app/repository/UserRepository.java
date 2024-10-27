package com.attendence.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendence.app.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    boolean existsUsersByUsername(String name);
}