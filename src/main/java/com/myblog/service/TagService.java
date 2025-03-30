package com.myblog.service;

import com.myblog.entity.Tag;
import com.myblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public Set<Tag> createMultipleFromString(String tagsAsString) {
        Set<String> tagNames = Stream.of(tagsAsString.split(","))
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toSet());

        Set<Tag> existingTags = tagRepository.findMultipleByNames(tagNames);

        Set<String> nonExistingTagNames = tagNames.stream()
                .filter(tagName -> existingTags.stream().noneMatch(tag -> tag.getName().equals(tagName)))
                .collect(Collectors.toSet());

        List<Tag> newTags = nonExistingTagNames.stream()
                .map(tagName -> Tag.builder().name(tagName).build())
                .toList();

        tagRepository.saveAll(newTags);

        existingTags.addAll(newTags);

        return existingTags;
    }
}
