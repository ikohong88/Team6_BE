package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDto {

    private Long postId;

    private String category;

    private String title;

    private String content;
    
    private boolean solved;

    private String nickname;

    private List<CommentResponseDto> comments;

    private int commentCount;

    public PostResponseDto(Post post) {
        postId = post.getId();
        category = post.getCategory();
        title = post.getTitle();
        nickname = post.getUser().getNickname();
        solved = post.isSolved();
        commentCount = post.getComments().size();
    }

    public PostResponseDto(Post post, List<CommentResponseDto> comments) {
        postId = post.getId();
        category = post.getCategory();
        title = post.getTitle();
        content = post.getContent();
        nickname = post.getUser().getNickname();
        solved = post.isSolved();
        commentCount = post.getComments().size();
        this.comments = comments;
    }

}
