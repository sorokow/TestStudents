package com.example.teststudents.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "test_config")
public class TestConfig {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_test")
    private String nameTest;

    @OneToOne
    @JoinColumn(name = "group_id")
    private GroupStudent groupStudent;

    @OneToOne
    @JoinColumn(name = "discipline_id")
    private Discipline discipline;

    @OneToOne
    @JoinColumn(name = "name_test_id")
    private Theme theme;

    @Column(name = "number_ques")
    private Integer numberQues;
}
