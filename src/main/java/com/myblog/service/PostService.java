package com.myblog.service;

import com.myblog.dto.post.AddPostDTO;
import com.myblog.dto.post.EditPostDTO;
import com.myblog.dto.post.PostDTO;
import com.myblog.entity.Post;
import com.myblog.mapper.PostMapper;
import com.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public Long create(AddPostDTO dto) {
        var entity = postMapper.toEntity(dto);
        return postRepository.save(entity);
    }

    public PostDTO getById(long id) {
        var entity = postRepository.findById(id);
        return postMapper.toDTO(entity);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public void update(EditPostDTO dto) {
        var entity = postRepository.findById(dto.getId());

        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());
        // entity.setImagePath(dto.getImagePath());

        postRepository.save(entity);
        postMapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
