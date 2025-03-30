package com.myblog.jpa;

import com.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
