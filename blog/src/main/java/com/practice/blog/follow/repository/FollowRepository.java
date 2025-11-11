package com.practice.blog.follow.repository;

import com.practice.blog.account.entity.Account;
import com.practice.blog.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로우 상태 확인 - 특정 사용자가 다른 사용자를 팔로우하고 있는지 여부를 boolean으로 반환
    Boolean existsByFollowerAndFollowing(Account follower, Account following);

    // 내가 팔로잉한 사람들 조회
    List<Follow> findAllByFollower(Account follower);

    // 나를 팔로워한 사람들 조회
    List<Follow> findAllByFollowing(Account following);

    // 팔로우 관계 조회 - 특정 팔로워와 팔로잉 간의 Follow 엔티티(팔로우 관계)를 단건 조회
    Follow findByFollowerAndFollowing(Account follower, Account following);
}
