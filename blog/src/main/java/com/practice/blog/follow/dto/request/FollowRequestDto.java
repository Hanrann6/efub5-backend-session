package com.practice.blog.follow.dto.request;

import com.practice.blog.account.entity.Account;
import com.practice.blog.follow.domain.Follow;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowRequestDto {

    @NotNull
    private Long followingId;

    public Follow toEntity(Account follower, Account following){
        return Follow.builder()
                .follower(follower)
                .following(following)
                .build();
    }
}
