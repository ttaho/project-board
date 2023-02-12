package com.fastcampus.projectboard.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long article_id; //게시글 id
    private Article article; //게시글 객체
    private String content; //댓글 내용
    private LocalDateTime createdAt; //댓글 생성일시
    private String createdBy; //댓글 작성자
    private LocalDateTime modifiedAt; //댓글 수정일시
    private String modifiedBy; //댓글 수정자
}
