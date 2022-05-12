package com.example.teststudents.repository;

import com.example.teststudents.entity.Discipline;
import com.example.teststudents.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    List<Theme> findAllByDiscipline(Discipline discipline);
}
