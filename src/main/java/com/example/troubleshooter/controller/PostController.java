package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.*;
import com.example.troubleshooter.security.UserDetailsImpl;
import com.example.troubleshooter.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    //조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts(){
        return postService.posts();
    }

    //읽기
    @GetMapping("/api/posts/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postService.post(postId);
    }

    //작성
    @PostMapping("/api/posts")
    public RestApi createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.create(requestDto, userDetails);
    }

    // pickedComment patch
    @PutMapping("/api/posts/{postId}/comments")
    public RestApi pickedComment(@PathVariable Long postId, @RequestBody PickedCommentDto pickedCommentDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.pickedComment(postId,pickedCommentDto,userDetails);
    }

    //수정
    @PutMapping("/api/posts/{postId}")
    public RestApi updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.update(postId,requestDto,userDetails);
    }

    //삭제
    @DeleteMapping("/api/posts/{postId}")
    public RestApi daletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.delete(postId,userDetails);
    }
}
