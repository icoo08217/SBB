package com.ll.exam.sbb.question;

import com.ll.exam.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Question getQuestion(int id) {
        Optional<Question> oq = questionRepository.findById(id);

        if (oq.isPresent()) {
            return oq.get();
        }

        throw new DataNotFoundException("question is not found");
    }

    public void create(String subject , String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        questionRepository.save(q);
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10 , Sort.by(sorts));

        return questionRepository.findAll(pageable);
    }
}
