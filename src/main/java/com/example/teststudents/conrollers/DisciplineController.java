package com.example.teststudents.conrollers;

import com.example.teststudents.entity.Discipline;
import com.example.teststudents.entity.Question;
import com.example.teststudents.entity.StartTest;
import com.example.teststudents.entity.Theme;
import com.example.teststudents.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/discipline")
public class DisciplineController {
    private final GroupService groupService;
    private final DisciplineService disciplineService;
    private final ThemeService themeService;
    private final QuestionService questionService;
    private final TestConfigService testConfigService;
    private final StartTestService startTestService;

    @Autowired
    public DisciplineController(GroupService groupService, DisciplineService disciplineService, ThemeService themeService, QuestionService questionService, TestConfigService testConfigService, StartTestService startTestService) {
        this.groupService = groupService;
        this.disciplineService = disciplineService;
        this.themeService = themeService;
        this.questionService = questionService;
        this.testConfigService = testConfigService;
        this.startTestService = startTestService;
    }

    @RequestMapping()
    public String getAll(Model model) {
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("disciplines", disciplineService.getAll());
        model.addAttribute("discipline", new Discipline());
        return "admin/test/discipline";
    }

    @PostMapping()
    public String create(@ModelAttribute("discipline") Discipline discipline) {
        disciplineService.save(discipline);
        return "redirect:/discipline";
    }

    @RequestMapping("/{id}/themes")
    public String getAllThemes(@PathVariable("id") Long id, Model model) {
        Discipline discipline = disciplineService.getById(id);
        model.addAttribute("discipline", discipline);
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("themes", themeService.getAllByDiscipline(discipline));
        model.addAttribute("theme", new Theme());
        return "admin/test/themes";
    }

    @PostMapping("/themesCreate")
    public String createInDiscipline(@RequestParam(value = "discipline") Long idDisp, @ModelAttribute("theme") Theme theme) {
        theme.setDiscipline(disciplineService.getById(idDisp));
        themeService.save(theme);
        return "redirect:/discipline/" + idDisp.toString() +"/themes";
    }

    @DeleteMapping("/themes/{id}")
    public String deleteTheme(@PathVariable("id") Long id) {
        Theme theme = themeService.getById(id);
        String disciplineId = theme.getDiscipline().getId().toString();
        testConfigService.deleteByTheme(theme);
        delete(theme);
        return "redirect:/discipline/" + disciplineId + "/themes";
    }

    public void delete(Theme theme) {
        for (Question question : questionService.getByTheme(theme)) {
            question.getStartTestList().forEach(q -> q.getQuestionList().remove(question));
            questionService.save(question);
            questionService.delete(question.getId());
        }

        List<StartTest> startTest = startTestService.getByTheme(theme);
        startTest.forEach(startTestService::delete);

        themeService.delete(theme);
    }
}
