package com.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

    @GetMapping("/home")
    @ResponseBody
    public String homePage() {
        return "<h1>Hello, world!</h1>";
    }

}
