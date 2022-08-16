package com.ll.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @RequestMapping("/question/list")
    // 이 자리에 @ResponseBody가 없으면 resources/template/question_list.html 파일을 view 로 삼는다.
    public String list(Model model) {

        List<Question> questionList = questionRepository.findAll();

        // 미래에 실행될 question_list.html에서
        // "questionList"라는 이름으로 questionList
        model.addAttribute("questionList", questionList);

        return "question_list";
    }
}
