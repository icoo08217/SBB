package com.ll.exam.sbb;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SbbApplicationMyTests {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void init() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2);
    }

    @AfterEach
    void end() {
        questionRepository.disableForeignKeyCheck();
        questionRepository.truncate();
        questionRepository.enableForeignKeyCheck();
    }

    @Test
    void testSave() {
        long questionSize = questionRepository.count();
        assertEquals(2 ,questionSize);
    }

    @Test
    void testDelete(){
        Optional<Question> oq = questionRepository.findById(1);
        Question q = oq.get();
        questionRepository.delete(q);
        assertEquals(1, questionRepository.count());
    }

    @Test
    void testModify() {
        Optional<Question> oq = questionRepository.findById(1);
        Question q = oq.get();
        q.setSubject("수정된 제목입니다.");
        questionRepository.save(q);
        assertEquals("수정된 제목입니다.", q.getSubject());
    }

    @Test
    void findBySubject() {
        Question question = questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1 , question.getId());
    }

    @Test
    void findBySubjectAndContent() {
        Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1 , question.getId());
    }

    @Test
    void findBySubjectLike() {
        List<Question> questions = questionRepository.findBySubjectLike("sbb%");
        assertEquals(1 , questions.get(0).getId());
    }

}
