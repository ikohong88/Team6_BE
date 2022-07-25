package com.example.troubleshooter.repository;

import com.example.troubleshooter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
