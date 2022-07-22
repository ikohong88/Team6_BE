package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.CommentRequestDto;
import com.example.troubleshooter.entity.Comment;
import com.example.troubleshooter.entity.Post;
import com.example.troubleshooter.repository.CommentRepository;
import com.example.troubleshooter.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public void writeComment(Long postId, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        post.addComment(new Comment(commentRequestDto));
    }

    public void editComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        comment.setContent(commentRequestDto.getContent());
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
