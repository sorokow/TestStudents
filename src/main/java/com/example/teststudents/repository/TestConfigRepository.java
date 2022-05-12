package com.example.teststudents.repository;

import com.example.teststudents.entity.Discipline;
import com.example.teststudents.entity.GroupStudent;
import com.example.teststudents.entity.TestConfig;
import com.example.teststudents.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TestConfigRepository extends JpaRepository<TestConfig, Long> {
    List<TestConfig> findAllByGroupStudent(GroupStudent groupStudent);

    @Transactional
    void deleteAllByDiscipline(Discipline discipline);

    @Transactional
    void deleteAllByTheme(Theme theme);

    TestConfig findByGroupStudent(GroupStudent groupStudent);
}
