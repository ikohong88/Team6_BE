package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;

    private String content;

    private String nickname;

    public CommentResponseDto(Comment comment) {
        id = comment.getId();
        content = comment.getContent();
        nickname = comment.getUser().getNickname();
    }

}
