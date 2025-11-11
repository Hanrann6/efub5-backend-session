package com.practice.blog.follow.dto.response;

import com.practice.blog.account.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class FollowStatusResponseDto {

    private Long accountId;
    private String nickname;
    private String email;
    private String status;

    public static FollowStatusResponseDto of(Account account, String status){
        return FollowStatusResponseDto.builder()
                .accountId(account.getAccountId())
                .nickname(account.getNickname())
                .email(account.getEmail())
                .status(status)
                .build();
    }
}
