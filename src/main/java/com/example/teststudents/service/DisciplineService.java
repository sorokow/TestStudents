package com.example.teststudents.service;

import com.example.teststudents.entity.Discipline;
import com.example.teststudents.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;

    @Autowired
    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    public List<Discipline> getAll() {
        return disciplineRepository.findAll();
    }

    public void save(Discipline discipline) {
        disciplineRepository.save(discipline);
    }

    public Discipline getById(Long id) {
        return disciplineRepository.findById(id).orElse(null);
    }

    public void delete(Discipline discipline) {
        disciplineRepository.delete(discipline);
    }
}
