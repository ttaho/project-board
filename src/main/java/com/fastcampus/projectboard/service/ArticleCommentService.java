package com.fastcampus.projectboard.service;

import com.fastcampus.projectboard.dto.ArticleCommentDto;
import com.fastcampus.projectboard.repository.ArticleCommentRepository;
import com.fastcampus.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //필수 필드에대한 생성자를 자동으로 만들어줌.
@Transactional
@Service
public class ArticleCommentService {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true) //조회에 관한 내용이므로 추가
    public List<ArticleCommentDto> searchArticleComment(long l) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {

    }
}
