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

    // 댓글 작성
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity postComment(@PathVariable Long postId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.writeComment(postId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity putComment(@PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.editComment(commentId, commentRequestDto, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

}
