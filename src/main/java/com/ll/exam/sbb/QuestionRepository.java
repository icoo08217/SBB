package com.ll.exam.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question , Integer> { // < 해당 클래스 , primary key의 타입>
    // public Question findById(int id);
}
