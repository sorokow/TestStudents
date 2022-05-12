package com.example.teststudents.repository;

import com.example.teststudents.entity.Theme;
import com.example.teststudents.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByTheme(Theme theme);

    @Query(value = "SELECT * FROM question where name_test_id=:idTheme ORDER BY random() LIMIT :number", nativeQuery = true)
    List<Question> getRandomQuestions(Long idTheme, Integer number);

    @Transactional
    void deleteAllByTheme(Theme theme);
}
