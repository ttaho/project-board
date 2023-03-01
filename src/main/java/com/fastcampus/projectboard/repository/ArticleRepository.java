package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,//기본적으로 Article안에 있는 기본검색기능을 추가시켜줌. 하지만 exact 검색만 가능.
        QuerydslBinderCustomizer<QArticle>
{
    Page<Article> findByTitle(String title, Pageable pageable);
    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true); //리스팅을 하지않은 property를 검색에서 제외시키는것을 true로 한다.-> 아마 이게 필터를 ON시키는거인듯
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); //api문서대로 필터조건을 넣어주었다.
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '${v}' -> 이건우리가 %를 수동으로 넣어줘야함.

        //title,content,hashtag,createdBy는 일부포함된 검색을 허용하지만, createdAt은 Datetime이니까 전부다 보이게 해야하므로 eq를한다.
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%${v}%' -> %를 넣어주지 않아도 돼서 편리함.
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); //이건 String형식이아니고 DateTime형식이라서 DateTimeExpression으로 해줌.
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}
