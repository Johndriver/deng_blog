package com.just.controller;

import com.just.dto.QuestionDTO;
import com.just.mapper.QuestionMapper;
import com.just.model.Question;
import com.just.sevice.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model){
        QuestionDTO questionDTO = questionService.getByID(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
