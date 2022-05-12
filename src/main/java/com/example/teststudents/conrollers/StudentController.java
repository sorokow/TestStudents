package com.example.teststudents.conrollers;

import com.example.teststudents.entity.Result;
import com.example.teststudents.entity.Role;
import com.example.teststudents.entity.StartTest;
import com.example.teststudents.entity.User;
import com.example.teststudents.service.GroupService;
import com.example.teststudents.service.ResultService;
import com.example.teststudents.service.StartTestService;
import com.example.teststudents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final GroupService groupService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final StartTestService startTestService;
    private final ResultService resultService;

    @Autowired
    public StudentController(GroupService groupService, UserService userService, PasswordEncoder passwordEncoder, StartTestService startTestService, ResultService resultService) {
        this.groupService = groupService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.startTestService = startTestService;
        this.resultService = resultService;
    }

    @GetMapping()
    public String getStudents(Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("students", userService.getAll());
        return "admin/students/students";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "admin/students/register";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        User userFromDB = userService.getUser(user.getUsername());

        if (userFromDB != null) {
            model.addAttribute("error", "Пользователь уже существует! Попробуйте зарегистрировать другой username.");
            return "admin/students/register";
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.STUDENT));
        userService.save(user);
        model.addAttribute("success", "Вы успешно зарегистрировали студента в системе");
        return "admin/students/register";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        StartTest startTest = startTestService.getByUser(userService.getById(id));
        if (startTest != null)
            startTestService.delete(startTest);
        List<Result> result = resultService.getByUser(userService.getById(id));
        result.forEach(result1 -> resultService.delete(result1));
        userService.delete(id);
        return "redirect:/students";
    }

    @GetMapping("/results")
    public String getResults( Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("results", resultService.getAll());
        return "admin/students/studentResults";
    }
}
