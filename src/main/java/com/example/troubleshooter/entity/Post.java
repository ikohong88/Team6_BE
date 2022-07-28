package com.example.troubleshooter.entity;

import com.example.troubleshooter.dto.PickedCommentDto;
import com.example.troubleshooter.dto.PostRequestDto;
import com.example.troubleshooter.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length=1000)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private boolean solved;
//    private Long pickedComment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Post(PostRequestDto requestDto, UserDetailsImpl userDetails) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.userId = userDetails.getUser().getId();
    }

    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

//    public void patch(PickedCommentDto pickedCommentDto){
//        this.pickedComment = pickedCommentDto.getCommentId();
//    }
    public void patch(PickedCommentDto pickedCommentDto){
        this.solved = pickedCommentDto.isSolved();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
}
