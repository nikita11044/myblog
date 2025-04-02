package com.myblog.jpa;

import com.myblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
