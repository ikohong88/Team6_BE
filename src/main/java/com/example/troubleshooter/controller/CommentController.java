package com.example.troubleshooter.controller;

import com.example.troubleshooter.dto.CommentRequestDto;
import com.example.troubleshooter.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity postComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.writeComment(postId, commentRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity putComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.editComment(commentId, commentRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
