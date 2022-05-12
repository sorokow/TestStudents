package com.example.teststudents.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "name_test_id")
    private Theme theme;

    @Column(name = "text_question")
    private String textQuestion;

    @Column(name = "var1")
    private String var1;

    @Column(name = "var2")
    private String var2;

    @Column(name = "var3")
    private String var3;

    @Column(name = "var4")
    private String var4;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @ManyToMany(cascade = CascadeType.MERGE , mappedBy = "questionList", fetch = FetchType.EAGER)
    private List<StartTest> startTestList;
}
