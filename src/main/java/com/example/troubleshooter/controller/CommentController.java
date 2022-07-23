package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.CommentRequestDto;
import com.example.troubleshooter.security.UserDetailsImpl;
import com.example.troubleshooter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void postComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.writeComment(postId, commentRequestDto, userDetails.getUser().getId());
    }

    @PutMapping("/api/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.editComment(commentId, commentRequestDto, userDetails.getUser().getId());
    }

    @DeleteMapping("/api/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser().getId());
    }

}
