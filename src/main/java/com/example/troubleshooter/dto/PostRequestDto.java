package com.example.troubleshooter.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostRequestDto {

    @NotBlank
    String title;

    @NotBlank
    String category;

    @NotBlank
    String content;

}
