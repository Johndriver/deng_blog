package com.just.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BlogController {
    @GetMapping("/hello")
    @ResponseBody
    public String greeting(@RequestParam("name") String name){
        return "hello "+name;
    }
}
