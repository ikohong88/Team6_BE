package com.example.troubleshooter.dto;

import com.example.troubleshooter.entity.Post;
import com.example.troubleshooter.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private String title;
    private String nickname;
    private String category;
    private boolean solved;
    private int commentCnt;
    private Long postId;
    private String content;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Post post, User user) {
        this.title = post.getTitle();
        this.nickname = user.getNickname();
        this.category = post.getCategory();
        this.solved = post.isSolved();
        this.commentCnt = post.getComments().size();
        this.postId = post.getId();
    }

    public PostResponseDto(Post post, User user, List<CommentResponseDto> comments) {
        this.title = post.getTitle();
        this.nickname = user.getNickname();
        this.category = post.getCategory();
        this.solved = post.isSolved();
        this.commentCnt = post.getComments().size();
        this.postId = post.getId();
        this.content = post.getContent();
        this.comments = comments;
    }
}
