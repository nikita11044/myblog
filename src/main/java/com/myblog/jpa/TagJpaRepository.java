package com.myblog.jpa;

import com.myblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagJpaRepository extends JpaRepository<Tag, Long> {
    Set<Tag> findAllByNameIn(Set<String> names);
}
