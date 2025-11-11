package com.practice.blog.post;

import com.practice.blog.account.entity.Account;
import com.practice.blog.account.repository.AccountsRepository;
import com.practice.blog.post.domain.Post;
import com.practice.blog.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Transactional // 롤백 비활성화
public class QueryDslTest {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void insertDataForSearch() {
        Account account1 = accountsRepository.save(Account.builder()
                .email("ewha@example.com")
                .password("pass1")
                .nickname("ewhaaaaaaa")
                .build());

        Account account2 = accountsRepository.save(Account.builder()
                .email("fubi@example.com")
                .password("pass2")
                .nickname("fubiiiii")
                .build());

        postRepository.save(Post.builder()
                .title("eWHa")
                .content("good")
                .writer(account1)
                .build());

        postRepository.save(Post.builder()
                .title("cat or dog")
                .content("cat")
                .writer(account1)
                .build());

        postRepository.save(Post.builder()
                .title("univ")
                .content("EWHA")
                .writer(account2)
                .build());

        postRepository.save(Post.builder()
                .title("ewha university")
                .content("efub")
                .writer(account2)
                .build());
    }
}

