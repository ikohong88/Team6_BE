package com.example.troubleshooter.entity;

import com.example.troubleshooter.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne
    private User user;

    public Comment(CommentRequestDto commentRequestDto, User user) {
        content = commentRequestDto.getContent();
        this.user = user;
    }

}
