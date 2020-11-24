package com.just.sevice;

import com.just.dto.PaginationDTO;
import com.just.dto.QuestionDTO;
import com.just.mapper.QuestionMapper;
import com.just.mapper.UserMapper;
import com.just.model.Question;
import com.just.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()&&paginationDTO.getTotalPage()>0){
            page=paginationDTO.getTotalPage();
        }

        Integer offSize = size*(page-1);
        List<Question> questions = questionMapper.list(offSize,size);
        List<QuestionDTO> list=new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //属性值对拷
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        paginationDTO.setQuestions(list);
        return paginationDTO;
    }

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(id);
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()&&paginationDTO.getTotalPage()>0){
            page=paginationDTO.getTotalPage();
        }

        Integer offSize = size*(page-1);
        List<Question> questions = questionMapper.listByUserId(id,offSize,size);
        List<QuestionDTO> list=new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //属性值对拷
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        paginationDTO.setQuestions(list);
        return paginationDTO;
    }

    public QuestionDTO getByID(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
