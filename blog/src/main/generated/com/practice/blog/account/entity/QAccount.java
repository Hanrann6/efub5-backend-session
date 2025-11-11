package com.practice.blog.account.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 1427226313L;

    public static final QAccount account = new QAccount("account");

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    public final StringPath bio = createString("bio");

    public final ListPath<com.practice.blog.comment.domain.Comment, com.practice.blog.comment.domain.QComment> commentList = this.<com.practice.blog.comment.domain.Comment, com.practice.blog.comment.domain.QComment>createList("commentList", com.practice.blog.comment.domain.Comment.class, com.practice.blog.comment.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<AccountStatus> status = createEnum("status", AccountStatus.class);

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

