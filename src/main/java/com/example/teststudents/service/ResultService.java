package com.example.teststudents.service;

import com.example.teststudents.entity.Result;
import com.example.teststudents.entity.User;
import com.example.teststudents.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public void save(Result result) {
        resultRepository.save(result);
    }

    public Result getResult(Long id) {
        return resultRepository.findById(id).orElse(null);
    }

    public List<Result> getByUser(User user) {
        return resultRepository.findAllByUser(user);
    }

    public void delete(Result result) {
        resultRepository.delete(result);
    }

    public List<Result> getAll() {
        return resultRepository.findAll();}
}
