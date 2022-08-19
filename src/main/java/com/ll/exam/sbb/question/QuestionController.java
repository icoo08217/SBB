package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.AnswerForm;
import com.ll.exam.sbb.user.SiteUser;
import com.ll.exam.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    private final UserService userService;

    @RequestMapping("/list")
    // 이 자리에 @ResponseBody가 없으면 resources/template/question_list.html 파일을 view 로 삼는다.
    public String list(Model model , @RequestParam(value = "page" , defaultValue = "0") int page) {

        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);

        return "question_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable Long id , AnswerForm answerForm) {

        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()") // 로그인이 되어 있어야만 가능한 메서드라는 뜻이다.
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm , BindingResult bindingResult , Principal principal){

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser siteUser = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject() , questionForm.getContent() , siteUser);

        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동

    }
}
