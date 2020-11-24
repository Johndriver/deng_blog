package com.just.controller;

import com.just.mapper.QuestionMapper;
import com.just.model.Question;
import com.just.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Resource
    private QuestionMapper questionMapper;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") String id){
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            HttpServletRequest request,
            Model model,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title == null|| title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null|| description.equals("")){
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if(tag == null|| tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        long now = System.currentTimeMillis();
        Question question = new Question();
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setGmtCreate(now);
        question.setGmtModified(now);
        questionMapper.create(question);
        return "redirect:/";
    }

}