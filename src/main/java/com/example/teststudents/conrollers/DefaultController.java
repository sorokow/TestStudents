package com.example.teststudents.conrollers;

import com.example.teststudents.entity.User;
import com.example.teststudents.service.DisciplineService;
import com.example.teststudents.service.GroupService;
import com.example.teststudents.service.TestConfigService;
import com.example.teststudents.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class DefaultController {
    private final GroupService groupService;
    private final UserService userService;
    private final TestConfigService testConfigService;

    @Autowired
    public DefaultController(GroupService groupService, UserService userService, TestConfigService testConfigService) {
        this.groupService = groupService;
        this.userService = userService;
        this.testConfigService = testConfigService;
    }

    @GetMapping("/")
    public String getAll(Model model, Principal principal) {
        model.addAttribute("groups", groupService.getAll());
        User user = userService.getUser(principal.getName());;
        model.addAttribute("testConfigs", testConfigService.getUserTest(user.getGroupStudent()));
        return "index";
    }


}
