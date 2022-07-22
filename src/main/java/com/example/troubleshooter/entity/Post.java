package com.example.troubleshooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean solved;

    @Column(nullable = false)
    private String userId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Post(String title, String content, String category, boolean solved, String userId, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.solved = false;
        this.userId = userId;
        this.comments = comments;
    }
}
