package com.ll.exam.sbb;

import com.ll.exam.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionService questionService;

    // test data 300개 생성
    @Test
    void testJpa(){
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다.:[%03d]", i);
            String content = "내용내용";

            questionService.create(subject, content);
        }
    }


}