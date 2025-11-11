package com.practice.blog.follow.controller;

import com.practice.blog.account.entity.Account;
import com.practice.blog.account.service.AccountService;
import com.practice.blog.follow.dto.request.FollowRequestDto;
import com.practice.blog.follow.dto.response.FollowListResponseDto;
import com.practice.blog.follow.dto.response.FollowStatusResponseDto;
import com.practice.blog.follow.service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    // 팔로우 걸기(추가)
    @PostMapping("/{accountId}")
    public ResponseEntity<FollowStatusResponseDto> addFollow(@PathVariable("accountId") Long accountId,
                                                            @RequestBody @Valid FollowRequestDto requestDto){
        FollowStatusResponseDto responseDto = followService.addFollow(accountId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 팔로잉 & 팔로워 리스트 조회
    @GetMapping("/{accountId}")
    public ResponseEntity<FollowListResponseDto> getFollowList(@PathVariable("accountId") Long accountId){
        FollowListResponseDto responseDto = followService.getFollowList(accountId);
        return ResponseEntity.ok(responseDto);
    }

    // 팔로우 여부 조회
    @GetMapping("/{accountId}/search")
    public ResponseEntity<FollowStatusResponseDto> searchAccount(@PathVariable("accountId") Long accountId,
                                                                 @RequestParam String email){
        Account searchAccount = accountService.findByEmail(email);
        FollowStatusResponseDto responseDto = followService.isFollowing(accountId, searchAccount.getAccountId());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 팔로우 취소
    @DeleteMapping("/{accountId}")
    public ResponseEntity<FollowStatusResponseDto> deleteFollow(@PathVariable("accountId") Long accountId,
                                                                @RequestParam Long followingId){
        FollowStatusResponseDto responseDto = followService.deleteFollow(accountId, followingId);
        return ResponseEntity.ok(responseDto);
    }

}
