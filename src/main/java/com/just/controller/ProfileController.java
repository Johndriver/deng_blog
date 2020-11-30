package com.just.controller;

import com.just.dto.PaginationDTO;
import com.just.mapper.UserMapper;
import com.just.model.User;
import com.just.sevice.NotificationService;
import com.just.sevice.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size ){
        //登录验证
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if("replies".equals(action)){
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            Long unreadCount = notificationService.unreadCount(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","我的回复");
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("pagination",paginationDTO);
        }
        return "profile";
    }
}
