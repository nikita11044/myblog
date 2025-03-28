package com.myblog.repository;

import com.myblog.entity.Post;
import com.myblog.jpa.PostJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final PostJpaRepository postJpaRepository;

    public Post findById(Long id) {
        return postJpaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Post not found by id: %s", id))
        );
    }

    public List<Post> findAll() {
        return postJpaRepository.findAll();
    }

    public Long save(Post post) {
        return postJpaRepository.save(post).getId();
    }

    public void deleteById(Long id) {
        postJpaRepository.deleteById(id);
    }
}
