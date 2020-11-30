package com.just.sevice;

import com.just.dto.PaginationDTO;
import com.just.dto.QuestionDTO;
import com.just.exception.CustomizeErrorCode;
import com.just.exception.CustomizeException;
import com.just.mapper.QuestionExtMapper;
import com.just.mapper.QuestionMapper;
import com.just.mapper.UserMapper;
import com.just.model.Question;
import com.just.model.QuestionExample;
import com.just.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Resource
    private QuestionExtMapper questionExtMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;

    public PaginationDTO<QuestionDTO> list(Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();


        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()&&paginationDTO.getTotalPage()>0){
            page=paginationDTO.getTotalPage();
        }
        Integer offSize = size*(page-1);
        QuestionExample questionExample= new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offSize, size));
        List<QuestionDTO> list=new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //属性值对拷
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        paginationDTO.setData(list);
        return paginationDTO;
    }

    public PaginationDTO<QuestionDTO> list(Long id, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(id);
        Integer totalCount = (int)questionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1){
            page = 1;
        }
        if(page>paginationDTO.getTotalPage()&&paginationDTO.getTotalPage()>0){
            page=paginationDTO.getTotalPage();
        }

        Integer offSize = size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offSize, size));
        List<QuestionDTO> list=new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //属性值对拷
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            list.add(questionDTO);
        }
        paginationDTO.setData(list);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //无id则新建
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            //有则更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated !=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void addViewCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.addViewCount(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions
                .stream()
                .map(q -> {
                            QuestionDTO questionDTO = new QuestionDTO();
                            BeanUtils.copyProperties(q, questionDTO);
                            return questionDTO;
                        })
                .collect(Collectors.toList());
        return questionDTOS;
    }
}
