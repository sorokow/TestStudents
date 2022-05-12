package com.example.teststudents.conrollers;

import com.example.teststudents.entity.*;
import com.example.teststudents.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/test")
public class TestController {
    private final TestConfigService testConfigService;
    private final UserService userService;
    private final StartTestService startTestService;
    private final QuestionService questionService;
    private final ResultService resultService;

    @Autowired
    public TestController(TestConfigService testConfigService, UserService userService, StartTestService startTestService, QuestionService questionService, ResultService resultService) {
        this.testConfigService = testConfigService;
        this.userService = userService;
        this.startTestService = startTestService;
        this.questionService = questionService;
        this.resultService = resultService;
    }

    @RequestMapping("/{id}")
    public String getTest(@PathVariable("id") Long id, Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        Theme theme = testConfigService.getById(id).getTheme();
        StartTest startTest = startTestService.getTest(user, theme);
        List<Result> result = resultService.getByUser(user);
        for (Result r : result) {
            if (r.getNameTestRes().equals(testConfigService.getById(id).getNameTest())) {
                return "redirect:/test/{id}/result/" + r.getId();
            }
        }
        if (startTest != null) {
            return "redirect:/test/{id}/questions/" + startTest.getId();
        }

        TestConfig testConfig = testConfigService.getById(id);
        model.addAttribute("testConfig", testConfig);
        model.addAttribute("testConfigs", testConfigService.getUserTest(user.getGroupStudent()));
        return "student/testDescription";
    }

    @PostMapping("/createStartTest")
    public String startTest(@RequestParam(value = "theme") Long id, Principal principal) {
        StartTest startTest = new StartTest();
        List<Question> questionList = generateTest(id);
        startTest.setQuestionList(questionList);
        startTest.setTheme(testConfigService.getById(id).getTheme());
        startTest.setUser(userService.getUser(principal.getName()));
        startTestService.save(startTest);
        return "redirect:/test/" + id.toString() + "/questions/" + startTest.getId();
    }

    public List<Question> generateTest(Long id) {
        Long idTheme = testConfigService.getById(id).getTheme().getId();
        Integer number = testConfigService.getById(id).getNumberQues();
        return questionService.getQuestionForTest(idTheme, number);
    }

    @GetMapping("/{id}/questions/{idStartTest}")
    public String processTest(@PathVariable(name = "id") Long id, @PathVariable(name = "idStartTest") Long idST, Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        StartTest startTest = startTestService.getStartTest(idST);
        model.addAttribute("questions", startTest.getQuestionList());
        model.addAttribute("testConfig", testConfigService.getById(id));
        model.addAttribute("testConfigs", testConfigService.getUserTest(user.getGroupStudent()));
        return "student/test";
    }

    @PostMapping("/{id}/questions")
    public String checkTest(@RequestBody MultiValueMap<String, String> data, @PathVariable Long id, Principal principal, Model model) {
        double count = 0;

        User user = userService.getUser(principal.getName());
        Theme theme = testConfigService.getById(id).getTheme();
        StartTest startTest = startTestService.getTest(user, theme);
        List<Question> questionList= startTest.getQuestionList();

        int numberQ = testConfigService.getById(id).getNumberQues();

        Collections.reverse(questionList);

        for (Map.Entry entry : data.entrySet()) {
            for (Question ques : questionList) {
                if (ques.getId().toString().equals(entry.getKey().toString())) {
                    if (entry.getValue().toString().replaceAll("[\\[\\](){}]", "").equals(ques.getCorrectAnswer())) {
                        count += 1.0;
                    }
                }
            }
        }

        double procent = (count / numberQ) * 100;
        String res;

        if (procent < 50.0)
            res = "Ваша оценка: неудовлетворительно " + String.format("%.2f", procent);
        else if (procent < 75.0)
            res = "Ваша оценка: удовлетворительно " +String.format("%.2f", procent);
        else if (procent < 90.0)
            res = "Ваша оценка: хорошо " +String.format("%.2f", procent);
        else
            res = "Ваша оценка: отлично " +String.format("%.2f", procent);

        Result result = new Result();
        result.setResult(res);
        result.setNameTestRes(testConfigService.getById(id).getNameTest());
        result.setUser(user);
        resultService.save(result);

        return "redirect:/test/{id}/result/" + result.getId().toString();
    }

    @GetMapping("/{id}/result/{idResult}")
    public String getResult(@PathVariable("id") Long id, Model model, @PathVariable("idResult") Long idResult) {
        TestConfig testConfig = testConfigService.getById(id);
        model.addAttribute("testConfigs", testConfigService.getAll());
        model.addAttribute("testConfig", testConfig);
        model.addAttribute("result", resultService.getResult(idResult));
        return "student/result";
    }
}

