package com.myblog.mapper;

import com.myblog.dto.post.AddPostDTO;
import com.myblog.dto.post.PostDTO;
import com.myblog.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(AddPostDTO addPostDTO);
    PostDTO toDTO(Post post);
}
