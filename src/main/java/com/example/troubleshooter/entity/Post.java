package com.example.troubleshooter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Post {
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

    @ManyToOne
    @JoinColumn(name="USER_ID",nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;
}
