package com.example.troubleshooter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PostResponseDto {
    String title;
    String nickname;
    String content;
    String category;
    boolean solved;
    List<CommentResponseDto> comments;
}
