package com.ll.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
// 컨트롤러는 Repository가 있는지 몰라야한다.
// 서비스는 웹브라우저라는 것이 이 세상에 존재하는지 몰라야 한다.
// 세션은 어디서 다뤄야 할까 ? ==> 컨트롤러에서 다뤄야한다.
// 리포지터리는 서비스의 존재를 몰라야 한다.
// 서비스는 컨트롤러를 몰라야 한다.
// DB는 리포지터리를 몰라야한다.
// SPRING DATA JPA는 MySQL을 몰라야한다.
// SPRING DATA JPA -> JPA -> 하이버네이트 -> JDBC -> MySQL Driver -> MySQL
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;

    @RequestMapping("/list")
    // 이 자리에 @ResponseBody가 없으면 resources/template/question_list.html 파일을 view 로 삼는다.
    public String list(Model model) {

        List<Question> questionList = questionService.getList();

        // 미래에 실행될 question_list.html에서
        // "questionList"라는 이름으로 questionList
        model.addAttribute("questionList", questionList);

        return "question_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable int id) {

        Question question = questionService.getQuestion(id);
        model.addAttribute("question" , question);

        return "question_detail";
    }
}
