package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.CommentRequestDto;
import com.example.troubleshooter.security.UserDetailsImpl;
import com.example.troubleshooter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity postComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        commentService.writeComment(postId, commentRequestDto, userDetails.getUser().getId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity putComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        commentService.editComment(commentId, commentRequestDto, userDetails.getUser().getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) throw new IllegalArgumentException("로그인이 필요합니다.");
        commentService.deleteComment(commentId, userDetails.getUser().getId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
