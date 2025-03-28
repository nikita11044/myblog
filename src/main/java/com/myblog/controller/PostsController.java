package com.myblog.controller;

import com.myblog.dto.post.AddPostDTO;
import com.myblog.dto.post.EditPostDTO;
import com.myblog.dto.post.PostDTO;
import com.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;

    @GetMapping("/")
    public String homePage(Model model) {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String postsPage(Model model) {
        var posts = postService.getAll();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String postPage(@PathVariable("id") Long id, Model model) {
        PostDTO postDTO = postService.getById(id);
        if (postDTO == null) {
            return "redirect:/";
        }
        model.addAttribute("title", postDTO.getTitle());
        model.addAttribute("post", postDTO);
        return "post";
    }

    @GetMapping("/posts/add")
    public String addPostPage(Model model) {
        model.addAttribute("title", "Add a New Post");
        return "add-post";
    }

    @PostMapping("/posts")
    public String addPost(AddPostDTO dto) {
        var id = postService.create(dto);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(@PathVariable("id") Long id,
                           @RequestParam("like") boolean like) {
        // postService.updateLikes(id, like);
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostPage(@PathVariable("id") Long id, Model model) {
        PostDTO postDTO = postService.getById(id);
        if (postDTO == null) {
            return "redirect:/";
        }
        model.addAttribute("title", "Edit Post: " + postDTO.getTitle());
        model.addAttribute("post", postDTO);
        return "add-post";
    }

    @PostMapping(value = "/posts/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updatePost(@PathVariable("id") Long id, EditPostDTO dto) {
        dto.setId(id);
        postService.update(dto);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments")
    public String addComment(@PathVariable("id") Long postId,
                             @RequestParam("text") String text) {
        // postService.addComment(postId, text);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(@PathVariable("id") Long postId,
                              @PathVariable("commentId") Long commentId,
                              @RequestParam("text") String text) {
        // postService.updateComment(postId, commentId, text);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long postId,
                                @PathVariable("commentId") Long commentId) {
        // postService.deleteComment(postId, commentId);
        return "redirect:/posts/" + postId;
    }


    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}
