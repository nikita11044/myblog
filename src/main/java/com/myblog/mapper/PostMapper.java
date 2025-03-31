package com.myblog.mapper;

import com.myblog.dto.post.PlainPostDTO;
import com.myblog.dto.post.PostDTO;
import com.myblog.entity.Post;
import com.myblog.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toEntity(PlainPostDTO plainPostDTO);

    @Mapping(target = "tagsAsString", source = "tags", qualifiedByName = "mapTagsToString")
    PostDTO toDTO(Post post);

    @Named("mapTagsToString")
    static String mapTagsToString(Set<Tag> tags) {
        return (tags != null) ? tags.stream().map(Tag::getName).collect(Collectors.joining(", ")) : "";
    }
}
