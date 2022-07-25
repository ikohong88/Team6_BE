package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String content;

    private String nickname;

    public CommentResponseDto(Comment comment, String nickname) {
        content = comment.getContent();
        this.nickname = nickname;
    }

}
