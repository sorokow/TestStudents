package com.example.teststudents.conrollers;

import com.example.teststudents.entity.Discipline;
import com.example.teststudents.entity.TestConfig;
import com.example.teststudents.service.DisciplineService;
import com.example.teststudents.service.GroupService;
import com.example.teststudents.service.TestConfigService;
import com.example.teststudents.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testConfig")
public class TestConfigController {
    private final GroupService groupService;
    private final DisciplineService disciplineService;
    private final ThemeService themeService;
    private final TestConfigService testConfigService;

    @Autowired
    public TestConfigController(GroupService groupService, DisciplineService disciplineService, ThemeService themeService, TestConfigService testConfigService) {
        this.groupService = groupService;
        this.disciplineService = disciplineService;
        this.themeService = themeService;
        this.testConfigService = testConfigService;
    }

    @RequestMapping()
    public String getAll(Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("disciplines", disciplineService.getAll());
        return "admin/test/createTest";
    }

    @RequestMapping("/{id}")
    public String getByDiscipline(Model model, @PathVariable("id") Long id) {
        Discipline discipline = disciplineService.getById(id);
        model.addAttribute("testConfigs", testConfigService.getAll());
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("discipline", discipline);
        model.addAttribute("themes", themeService.getAllByDiscipline(discipline));
        model.addAttribute("testConfig", new TestConfig());
        return "admin/test/testConfig";
    }

    @PostMapping("/{id}")
    public String createTest(@ModelAttribute("testConfig") TestConfig testConfig, @PathVariable("id") Long id) {
        Discipline discipline = disciplineService.getById(id);
        testConfig.setDiscipline(discipline);
        testConfigService.save(testConfig);
        return "redirect:/testConfig/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTest(@PathVariable("id") Long id) {
        TestConfig testConfig = testConfigService.getById(id);
        String idDisc = testConfig.getDiscipline().getId().toString();
        testConfigService.delete(testConfig);
        return "redirect:/testConfig/" + idDisc;
    }
}
