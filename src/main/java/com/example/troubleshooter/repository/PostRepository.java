package com.example.troubleshooter.repository;

import com.example.troubleshooter.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByIdDesc();
    Post findAllById(Long id);
}