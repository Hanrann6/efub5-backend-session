package com.practice.blog.post.repository;

import com.practice.blog.post.domain.Post;

import java.util.List;

public interface CustomPostRepository {

    List<Post> search(String keyword, String writerNickname);
}
