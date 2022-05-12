package com.example.teststudents.conrollers;

import com.example.teststudents.entity.GroupStudent;
import com.example.teststudents.entity.TestConfig;
import com.example.teststudents.entity.User;
import com.example.teststudents.service.GroupService;
import com.example.teststudents.service.ResultService;
import com.example.teststudents.service.TestConfigService;
import com.example.teststudents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;
    private final TestConfigService testConfigService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, TestConfigService testConfigService) {
        this.groupService = groupService;
        this.userService = userService;
        this.testConfigService = testConfigService;
    }

    @RequestMapping()
    public String getAll(Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("group", new GroupStudent());
        return "admin/groups/groups";
    }

    @PostMapping()
    public String create(@ModelAttribute("group") GroupStudent groupStudent) {
        groupService.save(groupStudent);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        TestConfig testConfig = testConfigService.getByGroup(groupService.getById(id));
        if (testConfig != null)
            testConfigService.delete(testConfig);
        groupService.delete(id);
        return "redirect:/groups";
    }

    @RequestMapping("/{id}")
    public String getStudentsByGroup(@PathVariable("id") Long id, Model model) {
        GroupStudent groupStudent = groupService.getById(id);
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("group", groupStudent);
        model.addAttribute("students", userService.getByGroup(groupStudent));
        model.addAttribute("user", new User());
        return "admin/groups/studentInGroup";
    }

    @PostMapping("/{id}")
    public String addStudentInGroup(@PathVariable("id") Long id, @ModelAttribute("user") User user, Model model) {
        GroupStudent groupStudent = groupService.getById(id);
        User userInGroup = userService.getUser(user.getUsername());
        if (userInGroup == null) {
            model.addAttribute("groups", groupService.getAll());
            model.addAttribute("group", groupStudent);
            model.addAttribute("students", userService.getByGroup(groupStudent));
            model.addAttribute("user", new User());
            model.addAttribute("error", "Студента с таким username не существует!");
            return "admin/groups/studentInGroup";
        }
        userInGroup.setGroupStudent(groupStudent);
        userService.save(userInGroup);
        return "redirect:/groups/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudentInGroup(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        String idGroup = user.getGroupStudent().getId().toString();
        user.setGroupStudent(null);
        userService.save(user);
        return "redirect:/groups/" + idGroup;
    }

}
