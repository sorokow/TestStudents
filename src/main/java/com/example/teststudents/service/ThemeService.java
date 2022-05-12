package com.example.teststudents.service;

import com.example.teststudents.entity.Discipline;
import com.example.teststudents.entity.Theme;
import com.example.teststudents.repository.StartTestRepository;
import com.example.teststudents.repository.ThemeRepository;
import com.example.teststudents.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository, QuestionRepository questionRepository) {
        this.themeRepository = themeRepository;
        this.questionRepository = questionRepository;
    }


    public List<Theme> getAllByDiscipline(Discipline discipline) {
        return themeRepository.findAllByDiscipline(discipline);
    }

    public void save(Theme theme) {
        themeRepository.save(theme);
    }

    public Theme getById(Long id) {
        return themeRepository.findById(id).orElse(null);
    }

    public void delete(Theme theme) {
        themeRepository.delete(theme);
    }

    public void deleteByDiscipline(Discipline discipline) {
        List<Theme> themeList = themeRepository.findAllByDiscipline(discipline);
        themeList.forEach(questionRepository::deleteAllByTheme);
        themeRepository.deleteAll(themeList);
    }
}
