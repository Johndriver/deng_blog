package com.just.controller;

import com.just.dto.CommentDTO;
import com.just.dto.ResultDTO;
import com.just.exception.CustomizeErrorCode;
import com.just.model.Comment;
import com.just.model.User;
import com.just.sevice.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Resource
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentDTO==null||StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        long now = System.currentTimeMillis();
        comment.setGmtCreate(now);
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        comment.setGmtModified(now);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
