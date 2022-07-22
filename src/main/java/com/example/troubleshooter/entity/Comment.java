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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    private Post post;

    private Long userId;

    public Comment(CommentRequestDto commentRequestDto) {
        content = commentRequestDto.getContent();
    }

}
