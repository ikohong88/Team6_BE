package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.Comment;
import com.example.troubleshooter.entity.User;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;

    private String content;

    private String nickname;

    public CommentResponseDto(Comment comment, String nickname) {
        content = comment.getContent();
        this.nickname = nickname;
    }

    public CommentResponseDto(Comment comment, User user) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = user.getNickname();
    }
}
