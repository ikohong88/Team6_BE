package com.example.troubleshooter.repository;

import com.example.troubleshooter.dto.PostResponseDto;
import com.example.troubleshooter.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<PostResponseDto> findAllByOrderByIdDesc();
    List<PostResponseDto> findAllById(Long id);
}