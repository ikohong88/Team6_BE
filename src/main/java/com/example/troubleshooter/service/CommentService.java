package com.example.troubleshooter.service;

import com.example.troubleshooter.dto.CommentRequestDto;
import com.example.troubleshooter.entity.Comment;
import com.example.troubleshooter.entity.Post;
import com.example.troubleshooter.repository.CommentRepository;
import com.example.troubleshooter.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public void writeComment(Long postId, CommentRequestDto commentRequestDto, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
        Comment comment = new Comment(commentRequestDto, userId);
        post.addComment(comment);
        commentRepository.save(comment);
    }

    public void editComment(Long commentId, CommentRequestDto commentRequestDto, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
        if (comment.isCommenter(userId)) {
            comment.setContent(commentRequestDto.getContent());
            commentRepository.save(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 작성자만 수정할 수 있습니다.");
        }
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
        if (comment.isCommenter(userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "댓글 작성자만 삭제할 수 있습니다.");
        }
    }

}
