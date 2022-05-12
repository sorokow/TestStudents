package com.example.teststudents.repository;

import com.example.teststudents.entity.StartTest;
import com.example.teststudents.entity.Theme;
import com.example.teststudents.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartTestRepository extends JpaRepository<StartTest, Long> {
    StartTest findByUserAndTheme(User user, Theme theme);

    List<StartTest> findAllByTheme(Theme theme);

    StartTest findByUser(User user);
}
