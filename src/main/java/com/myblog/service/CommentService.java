package com.myblog.service;

import com.myblog.dto.comment.CommentDTO;
import com.myblog.entity.Comment;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(CommentDTO dto) {
        var post = postRepository.findById(dto.getPostId());

        var comment = Comment.builder()
                .post(post)
                .text(dto.getText())
                .build();

        commentRepository.save(comment);
    }

    @Transactional
    public void update(CommentDTO dto) {
        var comment = commentRepository.findById(dto.getId());

        comment.setText(dto.getText());

        commentRepository.save(comment);
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
