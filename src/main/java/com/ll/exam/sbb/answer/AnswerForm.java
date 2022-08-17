package com.ll.exam.sbb.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class AnswerForm {

    @NotEmpty(message = "댓글 내용은 필수 항목입니다.")
    private String content;
}
