package com.example.teststudents.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "start_test")
public class StartTest {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "name_test_id")
    private Theme theme;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "stack_question", joinColumns = @JoinColumn(name = "startTest_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questionList;
}
