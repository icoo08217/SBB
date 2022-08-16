package com.ll.exam.sbb.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 주입
public class QuestionService {

    private final QuestionRepository questionRepository;

//    필드 주입
//    @Autowired
//    private QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question findById(int id) {
        Question q1 = questionRepository.findById(2).get();
        Question q2 = questionRepository.findById(2).get();
        System.out.println(q2.getAnswerList());

        return q2;
    }
}
