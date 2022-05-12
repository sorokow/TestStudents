package com.example.teststudents.service;

import com.example.teststudents.entity.GroupStudent;
import com.example.teststudents.entity.Role;
import com.example.teststudents.entity.User;
import com.example.teststudents.repository.GroupRepository;
import com.example.teststudents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public List<GroupStudent> getAll() {
        return groupRepository.findAll();}

    public void save(GroupStudent groupStudent) {
        groupRepository.save(groupStudent);
    }

    public void delete(Long id) {
        GroupStudent groupStudent = groupRepository.findById(id).orElse(null);
        if (groupStudent == null)
            return;
        List<User> userList = userRepository.findAllByGroupStudent(groupStudent);
        for (User user : userList) {
            user.setGroupStudent(null);
            userRepository.save(user);
        }
        groupRepository.deleteById(id);
    }

    public GroupStudent getById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }
}
