package com.practice.blog.follow.dto.response;

import com.practice.blog.account.entity.Account;
import com.practice.blog.follow.domain.Follow;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder @Getter
public class FollowListResponseDto {

    private List<SingleFollow> followerList;
    private List<SingleFollow> followingList;
    private int followerCount;
    private int followingCount;

    @Getter
    @Builder
    public static class SingleFollow{
        private Long accountId;
        private String nickname;
        private String email;

        public static SingleFollow fromFollower(Follow follow) {
            Account follower = follow.getFollower();
            return SingleFollow.builder()
                    .accountId(follower.getAccountId())
                    .nickname(follower.getNickname())
                    .email(follower.getEmail())
                    .build();
        }

        public static SingleFollow fromFollowing(Follow follow) {
            Account following = follow.getFollowing();
            return SingleFollow.builder()
                    .accountId(following.getAccountId())
                    .nickname(following.getNickname())
                    .email(following.getEmail())
                    .build();
        }
    }

    public static FollowListResponseDto of(List<Follow> followers ,List<Follow> followings) {
        return FollowListResponseDto.builder()
                .followerList(followers.stream().map(SingleFollow::fromFollower).collect(Collectors.toList()))
                .followingList(followings.stream().map(SingleFollow::fromFollowing).collect(Collectors.toList()))
                .followerCount(followers.size())
                .followingCount(followings.size())
                .build();
    }
}
