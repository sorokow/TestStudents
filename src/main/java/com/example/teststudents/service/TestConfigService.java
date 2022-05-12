package com.example.teststudents.service;

import com.example.teststudents.entity.*;
import com.example.teststudents.repository.TestConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestConfigService {
    private final TestConfigRepository testConfigRepository;

    @Autowired
    public TestConfigService(TestConfigRepository testConfigRepository) {
        this.testConfigRepository = testConfigRepository;
    }

    public void save(TestConfig testConfig) {
        testConfigRepository.save(testConfig);
    }

    public List<TestConfig> getAll() {
        return testConfigRepository.findAll();
    }

    public List<TestConfig> getUserTest(GroupStudent groupStudent) {
        return testConfigRepository.findAllByGroupStudent(groupStudent);
    }

    public TestConfig getByGroup(GroupStudent groupStudent) {
        return testConfigRepository.findByGroupStudent(groupStudent);
    }

    public void deleteByDiscipline(Discipline discipline) {
        testConfigRepository.deleteAllByDiscipline(discipline);
    }

    public void deleteByTheme(Theme theme) {
        testConfigRepository.deleteAllByTheme(theme);
    }

    public void delete(TestConfig testConfig) {
        testConfigRepository.delete(testConfig);
    }

    public TestConfig getById(Long id) {
        return testConfigRepository.findById(id).orElse(null);
    }
}
