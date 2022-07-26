package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.PostRequestDto;
import com.example.troubleshooter.dto.PostResponseDto;
import com.example.troubleshooter.security.UserDetailsImpl;
import com.example.troubleshooter.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 게시글 목록 조회
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponseDto>> getPosts() {
        List<PostResponseDto> postList = postService.getPostList();
        return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    // 게시글 상세정보 조회
    @GetMapping("/api/posts/{postId}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
        PostResponseDto postDetail = postService.getPostDetail(postId);
        return new ResponseEntity<>(postDetail, HttpStatus.OK);
    }

    // 게시글 작성
    @PostMapping("/api/posts")
    public ResponseEntity postPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.writePost(postRequestDto, userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 게시글 수정
    @PutMapping("/api/posts/{postId}")
    public ResponseEntity putPost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.editPost(postId, postRequestDto, userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
