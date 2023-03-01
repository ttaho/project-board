package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //게시글 id
    //위에까지가 id를 잡아준거임 id에 setter를 안걸어준 이유는 JPA persistence context가 자동으로 부여해주는 고유 번호이기떄문임

    //setter를 전체 클래스에 걸지않고 각필드(title,content ....)에 거는 이유는
    //일부러 사용자가 특정 필드에 접근해서 setting하는것을 막기위해서 임.
    //@Column(nullable = false)은 디폴드가 true인데, 널값을 넣을수 없는 필드에만 해당 애노테이션을 추가한다.
    @Setter @ManyToOne(optional = false) private UserAccount userAccount; // 유저 정보(ID)
    @Setter @Column(nullable = false) private String title; //게시글 제목
    @Setter @Column(nullable = false, length = 10000) private String content; //게시글 내용

    @Setter
    private String hashtag; //해시태그

    @ToString.Exclude //ToString의 순환참조를 막기위해 끊어주기위해 사용
    @OrderBy("createdAt DESC") //-> 정렬기준을 id로 한다.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
//-> article 테이블로부터 온것이다를 명시한것, cascadeType.All-> 모든경우에 대해서 cascade를 적용시킨다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article() {
    }

    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id); //id가 부여되지않았다(영속화 되지 않았다)면 false,
        // id가 부여됐다면 id가 같은지 보고 id가 같다면 두개가 같은 게시글일것이다.-> 동일성검사 id로만 한다.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
