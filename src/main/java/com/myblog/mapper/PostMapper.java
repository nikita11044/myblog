package com.myblog.mapper;

import com.myblog.dto.post.PostRequestDTO;
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
    Post toEntity(PostRequestDTO dto);

    @Mapping(target = "tagsAsString", source = "tags", qualifiedByName = "mapTagsToString")
    @Mapping(target = "textPreview", source = "text", qualifiedByName = "mapTextToTextPreview")
    PostDTO toDTO(Post entity);

    @Named("mapTagsToString")
    static String mapTagsToString(Set<Tag> tags) {
        return (tags != null) ? tags.stream().map(Tag::getName).collect(Collectors.joining(", ")) : "";
    }

    @Named("mapTextToTextPreview")
    static String mapTextToTextPreview(String text) {
        if (text == null) {
            return "";
        }

        return text.length() > 100 ? text.substring(0, 100) + "..." : text;
    }
}
