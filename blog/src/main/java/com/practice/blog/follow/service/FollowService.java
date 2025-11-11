package com.practice.blog.follow.service;

import com.practice.blog.account.entity.Account;
import com.practice.blog.account.service.AccountService;
import com.practice.blog.follow.domain.Follow;
import com.practice.blog.follow.dto.request.FollowRequestDto;
import com.practice.blog.follow.dto.response.FollowListResponseDto;
import com.practice.blog.follow.dto.response.FollowStatusResponseDto;
import com.practice.blog.follow.repository.FollowRepository;
import com.practice.blog.global.exception.BlogException;
import com.practice.blog.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final AccountService accountService;
    private final FollowRepository followRepository;

    // 팔로우 추가
    public FollowStatusResponseDto addFollow(Long accountId, FollowRequestDto followRequestDto){
        Account follower = accountService.findByAccountId(accountId);
        Account following = accountService.findByAccountId(followRequestDto.getFollowingId());
        if (followRepository.existsByFollowerAndFollowing(follower, following)) {
            throw new BlogException(ExceptionCode.ALREADY_FOLLOWED);
        }
        followRepository.save(followRequestDto.toEntity(follower, following));
        String status = FollowStatus(follower, following);

        return FollowStatusResponseDto.of(following, status);
    }

    // 팔로우 여부 확인
    @Transactional(readOnly = true)
    public FollowStatusResponseDto isFollowing(Long followerId, Long followingId){
        Account follower = accountService.findByAccountId(followerId);
        Account following = accountService.findByAccountId(followingId);
        String status = FollowStatus(follower, following);

        return FollowStatusResponseDto.of(following, status);
    }

    // 팔로우 & 팔로잉 리스트 전체 조회
    @Transactional(readOnly = true)
    public FollowListResponseDto getFollowList(Long accountId) {
        Account account = accountService.findByAccountId(accountId);
        List<Follow> followers = followRepository.findAllByFollowing(account);
        List<Follow> followings = followRepository.findAllByFollower(account);

        return FollowListResponseDto.of(followers, followings);
    }

    // 팔로우 삭제
    public FollowStatusResponseDto deleteFollow(Long accountId, Long followingId){
        Account follower = accountService.findByAccountId(accountId);
        Account following = accountService.findByAccountId(followingId);
        Follow findFollow = followRepository.findByFollowerAndFollowing(follower, following);
        if (findFollow == null) {
            throw new BlogException(ExceptionCode.FOLLOW_NOT_FOUND);
        }
        followRepository.delete(findFollow);
        String status = FollowStatus(follower, following);

        return FollowStatusResponseDto.of(following, status);
    }

    // 팔로우 상태 반환용 - 분리
    public String FollowStatus(Account follower, Account following){
        boolean isFollowed = followRepository.existsByFollowerAndFollowing(follower, following);
        return isFollowed ? "FOLLOWED" : "UNFOLLOWED";
    }
}
