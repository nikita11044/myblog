package com.myblog.service;

import com.myblog.dto.post.PlainPostDTO;
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
    private final FileService fileService;
    private final TagService tagService;

    @Transactional
    public Long create(PlainPostDTO dto) {
        var post = postMapper.toEntity(dto);

        var tags = tagService.createMultipleFromString(dto.getTagsAsString());
        post.setTags(tags);

        if (dto.getImage() != null) {
            post.setImagePath(fileService.uploadFile(dto.getImage()));
        }

        return postRepository.save(post);
    }

    @Transactional
    public PostDTO getById(long id) {
        var post = postRepository.findById(id);
        return postMapper.toDTO(post);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void update(PlainPostDTO dto) {
        var post = postRepository.findById(dto.getId());

        post.setTitle(dto.getTitle());
        post.setText(dto.getText());

        if (dto.getTagsAsString() != null) {
            post.setTags(tagService.createMultipleFromString(dto.getTagsAsString()));
        }

        if (dto.getImage() != null) {
            fileService.deleteFile(post.getImagePath());
            post.setImagePath(fileService.uploadFile(dto.getImage()));
        }

        postRepository.save(post);
    }

    @Transactional
    public void updateLikes(Long postId, boolean like) {
        var post = postRepository.findById(postId);

        if (like) {
            post.setLikesCount(post.getLikesCount() + 1);
        } else if (post.getLikesCount() > 0) {
            post.setLikesCount(post.getLikesCount() - 1);
        }

        postRepository.save(post);
    }


    @Transactional
    public void delete(Long id) {
        var post = postRepository.findById(id);
        fileService.deleteFile(post.getImagePath());
        postRepository.deleteById(id);
    }
}
