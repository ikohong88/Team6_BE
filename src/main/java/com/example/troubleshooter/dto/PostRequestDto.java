package com.example.troubleshooter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PostRequestDto {
    @NotNull
    String title;
    @NotNull
    String category;
    @NotNull
    String content;
}
