package com.just.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {
    @GetMapping("/ot")
    public String greeting(){
        return "index";
    }
}
