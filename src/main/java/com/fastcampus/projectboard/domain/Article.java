package com.fastcampus.projectboard.domain;

import java.time.LocalDateTime;

public class Article {
    private Long id; //게시글 id
    private String title; //게시글 제목
    private String content; //게시글 내용
    private String hashtag; //해시태그

    private LocalDateTime createdAt; //게시글 생성일시
    private String createdBt; //게시글 생성자

    private LocalDateTime modifiedAt; //게시글 수정일시
    private String modifiedBy; //게시글 수정자
}
