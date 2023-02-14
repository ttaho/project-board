package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //게시글 id

    @Setter @ManyToOne(optional = false) private Article article; //게시글 객체    -> 댓글에서 게시글로는 매핑을 했음.
    @Setter @Column(nullable = false, length=500) private String content; //댓글 내용

    // 아래의 필드들은 자동으로 auditing해주기위해 아래와같은 애노테이션을 붙임
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; //댓글 생성일시
    @CreatedBy @Column(nullable = false, length=100) private String createdBy; //댓글 작성자
    @LastModifiedDate @Column(nullable = false)private LocalDateTime modifiedAt; //댓글 수정일시
    @LastModifiedBy @Column(nullable = false, length=100) private String modifiedBy; //댓글 수정자

    protected ArticleComment() {
    }

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
