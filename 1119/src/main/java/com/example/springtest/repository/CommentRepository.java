package com.example.springtest.repository;

import com.example.springtest.domain.Article;
import com.example.springtest.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
