package com.just.mapper;

import com.just.model.Question;

import java.util.List;

public interface QuestionExtMapper {
    void addViewCount(Question record);
    int incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}