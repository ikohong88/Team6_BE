package com.example.troubleshooter.entity;

import com.example.troubleshooter.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String category;

    private String title;

    private String content;

    private boolean solved;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Post(PostRequestDto postRequestDto, User user) {
        category = postRequestDto.getCategory();
        title = postRequestDto.getTitle();
        content = postRequestDto.getContent();
        this.user = user;
    }

    public void edit(PostRequestDto postRequestDto) {
        title = postRequestDto.getTitle();
        content = postRequestDto.getContent();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

}
