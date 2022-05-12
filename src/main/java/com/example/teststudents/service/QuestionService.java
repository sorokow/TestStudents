package com.example.teststudents.service;

import com.example.teststudents.entity.Theme;
import com.example.teststudents.entity.Question;
import com.example.teststudents.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getByTheme(Theme theme) {
        return questionRepository.findAllByTheme(theme);
    }

    public void save(Question question) { questionRepository.save(question);}

    public Question getById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {

        questionRepository.deleteById(id);
    }


    public List<Question> getQuestionForTest(Long idTheme, Integer number) {
        return questionRepository.getRandomQuestions(idTheme, number);
    }
}
