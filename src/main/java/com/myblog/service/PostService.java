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
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final FileService fileService;
    private final TagService tagService;

    @Transactional
    public Long create(AddPostDTO dto) {
        var entity = postMapper.toEntity(dto);

        var tags = tagService.createMultipleFromString(dto.getTags());
        entity.setTags(tags);

        if (dto.getImage() != null) {
            entity.setImagePath(fileService.uploadFile(dto.getImage()));
        }

        return postRepository.save(entity);
    }

    @Transactional
    public PostDTO getById(long id) {
        var entity = postRepository.findById(id);
        return postMapper.toDTO(entity);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void update(EditPostDTO dto) {
        var entity = postRepository.findById(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());

        if (dto.getImage() != null) {
            fileService.deleteFile(entity.getImagePath());
            entity.setImagePath(fileService.uploadFile(dto.getImage()));
        }

        postRepository.save(entity);
        postMapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        var post = postRepository.findById(id);
        fileService.deleteFile(post.getImagePath());
        postRepository.deleteById(id);
    }
}
