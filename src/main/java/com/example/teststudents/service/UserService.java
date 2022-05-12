package com.example.teststudents.service;

import com.example.teststudents.entity.GroupStudent;
import com.example.teststudents.entity.Role;
import com.example.teststudents.entity.User;
import com.example.teststudents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> userList = userRepository.findAll();
        userList.removeIf(usr -> usr.getRoles().contains(Role.ADMIN));
        return userList;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getByGroup(GroupStudent groupStudent) {
        List<User> userList = userRepository.findAllByGroupStudent(groupStudent);
        if (userList.isEmpty())
            return null;
        return userList;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
