package com.example.teststudents.conrollers;

import com.example.teststudents.entity.Theme;
import com.example.teststudents.entity.Question;
import com.example.teststudents.service.GroupService;
import com.example.teststudents.service.ThemeService;
import com.example.teststudents.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/themes")
public class QuestionController {
    private final QuestionService questionService;
    private final ThemeService themeService;
    private final GroupService groupService;

    @Autowired
    public QuestionController(QuestionService questionService, ThemeService themeService, GroupService groupService) {
        this.questionService = questionService;
        this.themeService = themeService;
        this.groupService = groupService;
    }

    @RequestMapping("/{id}/questions")
    public String getAllQuestions(@PathVariable("id") Long id, Model model) {
        Theme theme = themeService.getById(id);
        model.addAttribute("theme", theme);
        model.addAttribute("groups", groupService.getAll());
        model.addAttribute("questions", questionService.getByTheme(theme));
        model.addAttribute("question", new Question());
        return "admin/test/question";
    }

    @PostMapping("/questionsCreate")
    public String createQuestion(@RequestParam(value = "theme") Long idTheme, @ModelAttribute("question") Question question) {
        Theme theme = themeService.getById(idTheme);
        question.setTheme(theme);
        questionService.save(question);
        return "redirect:/themes/" + idTheme.toString() + "/questions";
    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable("id") Long id) {
        Question question = questionService.getById(id);
        String nameTestId = question.getTheme().getId().toString();
        this.delete(question, id);
        return "redirect:/themes/" + nameTestId + "/questions";
    }

    public void delete(Question question, Long id) {
        question.getStartTestList().forEach(q -> q.getQuestionList().remove(question));
        questionService.save(question);
        questionService.delete(id);
    }


}
