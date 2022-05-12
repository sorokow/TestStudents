package com.example.teststudents.repository;

import com.example.teststudents.entity.GroupStudent;
import com.example.teststudents.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
    List<User> findAllByGroupStudent(GroupStudent groupStudent);
}
