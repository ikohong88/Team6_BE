package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private String nickname;
    
    private String content;

    public CommentResponseDto(Comment comment) {
        nickname = comment.getUser().getNickname();
        content = comment.getContent();
    }
}
