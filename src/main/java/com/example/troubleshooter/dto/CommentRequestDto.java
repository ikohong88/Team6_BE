package com.example.troubleshooter.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용이 없습니다.")
    String content;

}
