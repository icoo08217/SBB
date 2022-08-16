package com.ll.exam.sbb.answer;


import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String detail(Model model, @PathVariable int id, @RequestParam String content) {
        Question question = this.questionService.getQuestion(id);
        
        //답변 등록 시작
        answerService.create(question, content);

        return String.format("redirect:/question/detail/%s", id);
    }
}
