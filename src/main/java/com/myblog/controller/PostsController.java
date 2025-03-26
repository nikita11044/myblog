package com.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostsController {

    private List<String> posts = new ArrayList<>();

    @GetMapping("/")
    public String postsPage(Model model) {
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/post/{id}")
    public String postPage(@PathVariable int id, Model model) {
        if (id < 0 || id >= posts.size()) {
            return "redirect:/posts"; // Redirect if post ID is invalid
        }
        model.addAttribute("title", "View Post");
        model.addAttribute("post", posts.get(id));
        model.addAttribute("postId", id);
        return "post";
    }

    // Show Add Post Page
    @GetMapping("/add-post")
    public String addPostPage(Model model) {
        model.addAttribute("title", "Add a New Post");
        return "add-post";
    }
}
