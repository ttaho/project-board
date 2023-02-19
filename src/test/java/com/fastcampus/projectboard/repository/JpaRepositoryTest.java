package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) //-> 테스트시 이 애노테이션을 달지않으면 JpaConfig에서 @EnableJpaAuditing 기능이 작동하지 않는다.
@DataJpaTest //slice 테스트를 위해 사용
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {

        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){

        //Given

        //When
        List<Article> articles = articleRepository.findAll();
        //Then

        //articles이 NULL이 아니고, 사이즈가 100개 인지 확인
        assertThat(articles)
                .isNotNull()
                .hasSize(100);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){

        //Given
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");
        //When
        Article savedArticle = articleRepository.save(article);
        //Then

        //articleRepository 카운트가 1증가했는지 확인
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){

        //Given
        Article article = articleRepository.findById(1L).orElseThrow(); //1L이라는 아이디를 가진 게시글이 있으면 저장, 없으면 던짐
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag); //해시태그를 수정한다.

        //When
        // save(article)대신 saveAndFlush(article)을 사용하는 이유
        // update에서 영속성 컨텍스트로 부터 가져온 데이터를 그냥 save하고, 아무런 동작을 하지 않고 끝내버리면 어차피 롤백할거라서 의미가 없음 -> 원래의 hashtag로 돌아갈것임.
        // 그래서 saveAndFlush를 사용하면 테스트시 update쿼리를 확인할 수 있다. 다만, 롤백되기때문에, 실제 DB에는 반영 X!
        Article savedArticle = articleRepository.saveAndFlush(article);
        //Then

        //hashtag 필드가 업데이트 되었는지 확인
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){

        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommnetcCount = articleCommentRepository.count();
        //When
        Article savedArticle = articleRepository.save(article);
        //Then

        //articleRepository 카운트가 1증가했는지 확인
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);
    }
}