package com.ll.exam.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String content;

    private LocalDateTime createDate;

    @ManyToOne // 관계 설정
    // Many -> Answer , One -> Question 을 의미한다
    private Question question;
}
