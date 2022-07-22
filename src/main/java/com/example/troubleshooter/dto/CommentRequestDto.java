package com.example.troubleshooter.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentRequestDto {

    @NotNull
    String content;

}
