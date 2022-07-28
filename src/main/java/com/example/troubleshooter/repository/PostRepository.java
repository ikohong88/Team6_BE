package com.example.troubleshooter.repository;

import com.example.troubleshooter.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByIdDesc(Pageable pageable);
    Post findAllById(Long id);
}