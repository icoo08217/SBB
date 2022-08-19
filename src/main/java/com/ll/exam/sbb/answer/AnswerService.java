package com.ll.exam.sbb.answer;

import com.ll.exam.sbb.DataNotFoundException;
import com.ll.exam.sbb.question.Question;
import com.ll.exam.sbb.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content , SiteUser author) {

        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());

        answer.setQuestion(question);
        answer.setAuthor(author);
        answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

}
