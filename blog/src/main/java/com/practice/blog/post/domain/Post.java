package com.practice.blog.post.domain;

import com.practice.blog.account.entity.Account;
import com.practice.blog.comment.domain.Comment;
import com.practice.blog.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    private Long viewCount;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(String title, String content, Account writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.viewCount = 0L;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }
}
