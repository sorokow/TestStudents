package com.example.teststudents.service;

import com.example.teststudents.entity.StartTest;
import com.example.teststudents.entity.Theme;
import com.example.teststudents.entity.User;
import com.example.teststudents.repository.StartTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartTestService {
    private final StartTestRepository startTestRepository;

    @Autowired
    public StartTestService(StartTestRepository startTestRepository) {
        this.startTestRepository = startTestRepository;
    }

    public StartTest getTest(User user, Theme theme) {
        return startTestRepository.findByUserAndTheme(user, theme);
    }

    public void save(StartTest startTest) {
        startTestRepository.save(startTest);
    }

    public StartTest getStartTest(Long id) {
        return startTestRepository.findById(id).orElse(null);
    }

    public List<StartTest> getByTheme(Theme theme) {
        return startTestRepository.findAllByTheme(theme);
    }

    public void delete(StartTest startTest) {
        startTestRepository.delete(startTest);
    }

    public StartTest getByUser(User user) {
        return startTestRepository.findByUser(user);
    }
}
