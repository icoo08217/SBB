package com.ll.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AnswerRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void init() {
        clearData();
        createSampleData();
    }

    private void createSampleData() {
        QuestionRepositoryTests.createSampleData(questionRepository);

        Question q = questionRepository.findById(1).get();

        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a1.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트를 사용합니다.");
        a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a2.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a2);
    }

    private void clearData() {

        QuestionRepositoryTests.clearData(questionRepository);

        answerRepository.truncate();
    }

    @Test
    void save() {
        Question q = questionRepository.findById(2).get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    @Test
    void 조회() {
        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertThat(a.getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }
}
