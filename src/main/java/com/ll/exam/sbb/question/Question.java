package com.ll.exam.sbb.question;

import com.ll.exam.sbb.answer.Answer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity // 아래 Qestion 클래스는 엔티티 클래스이다.
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 없다면, 자동으로 생성되어야 한다.
public class Question {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200) // varchar(200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question" , cascade = CascadeType.REMOVE)
    // one -> Question , Many -> answer
    private List<Answer> answerList = new ArrayList<>(); // question 하나에 여러개의 answer가 있다.
}
