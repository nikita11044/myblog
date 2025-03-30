package com.myblog.repository;

import com.myblog.entity.Tag;
import com.myblog.jpa.TagJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    private final TagJpaRepository tagJpaRepository;

    public Set<Tag> findMultipleByNames(Set<String> names) {
        return tagJpaRepository.findAllByNameIn(names);
    }

    public void saveAll(List<Tag> tags) {
        tagJpaRepository.saveAll(tags);
    }
}
