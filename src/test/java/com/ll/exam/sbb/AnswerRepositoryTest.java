package com.ll.exam.sbb;

import com.ll.exam.sbb.answer.Answer;
import com.ll.exam.sbb.answer.AnswerRepository;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

        // 관련 답변이 하나도 없는 상태에서 쿼리 발생
        Question q = questionRepository.findById(1).get();

        Answer a1 = new Answer();
        a1.setContent("sbb는 질문답변 게시판 입니다.");
        a1.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a1.setCreateDate(LocalDateTime.now());
        answerRepository.save(a1);

        q.getAnswerList().add(a1);

        Answer a2 = new Answer();
        a2.setContent("sbb에서는 주로 스프링부트를 사용합니다.");
        a2.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a2.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a2);

        q.getAnswerList().add(a2);
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

        Question q = a.getQuestion();
//        questionRepository.findById(1).get(); 위 코드와 같은 의미 이다.
    }

    @Test
    void 관련된_question_조회() {
        Answer a = answerRepository.findById(1).get();
        Question q = a.getQuestion();

        assertThat(q.getId()).isEqualTo(1);
    }

    @Test
    @Transactional // annotation이 붙어 있으면 db 연결을 이 메서드가 실행되는 순간동안에는 끊지 않는다.
    // transactional은 rollback을 자기가 스스로 알아서 진행한다.
    @Rollback(value = false)
    void question으로부터_관련된_질문들_조회() {
        //SELECT * FROM question WHERE id = 1
        Question q = questionRepository.findById(1).get();
        // DB 연결 끊킴
        // 동일 트랜잭션 내에서는 정말로 객체를 공유하는가 ?
        // 영속성 컨텍스트로 인하여 객체는 하나만 사용된다.

        // SELECT * FROM answer WHERE question_id = 1
        List<Answer> answerList = q.getAnswerList(); // null ?

        assertThat(answerList.size()).isEqualTo(2);
        assertThat(answerList.get(0).getContent()).isEqualTo("sbb는 질문답변 게시판 입니다.");
    }
}
