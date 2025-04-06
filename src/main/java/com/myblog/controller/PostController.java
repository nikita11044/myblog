package com.myblog.controller;

import com.myblog.dto.comment.CommentDTO;
import com.myblog.dto.post.PostRequestDTO;
import com.myblog.dto.post.PostDTO;
import com.myblog.entity.Post;
import com.myblog.service.CommentService;
import com.myblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/")
    public String homePage() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts(@RequestParam(name = "search", defaultValue = "") String search,
                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                           @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                           Model model) {
        Page<PostDTO> postPage = postService.getByTagName(search, pageNumber, pageSize);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("paging", Map.of(
                "pageNumber", pageNumber,
                "pageSize", pageSize,
                "hasNext", postPage.hasNext(),
                "hasPrevious", postPage.hasPrevious()
        ));

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

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String addPost(@ModelAttribute PostRequestDTO dto) {
        var id = postService.create(dto);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(@PathVariable("id") Long id,
                           @RequestParam("like") boolean like) {
        postService.updateLikes(id, like);
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
    public String editPost(@PathVariable("id") Long id, @ModelAttribute PostRequestDTO dto) {
        dto.setId(id);
        postService.update(dto);
        return "redirect:/posts/" + id;
    }

    @PostMapping( "/posts/{id}/comments")
    public String addComment(@PathVariable("id") Long postId, CommentDTO dto) {
        dto.setPostId(postId);
        commentService.create(dto);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(@PathVariable("id") Long postId,
                              @PathVariable("commentId") Long commentId,
                              CommentDTO dto) {
        dto.setId(commentId);
        dto.setPostId(postId);
        commentService.update(dto);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long postId,
                                @PathVariable("commentId") Long commentId) {
        commentService.delete(commentId, postId);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long id) {
        postService.delete(id);
        return "redirect:/posts";
    }
}
