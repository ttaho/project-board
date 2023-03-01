package com.fastcampus.projectboard.controller;

import com.fastcampus.projectboard.domain.type.SearchType;
import com.fastcampus.projectboard.dto.response.ArticleResponse;
import com.fastcampus.projectboard.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.projectboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor

@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService; //서비스를 갖다쓰겠다.
    @GetMapping
    public String articles(
            //RequestParam을 통해서 get파라미터를 불러오고, 반드시있지않아도됨(null 들어오면 전체게시글 띄워주면됨)그래서 required = false
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size=10, sort = "createdAt", direction= Sort.Direction.DESC) Pageable pageable,//한페이지 게시글 10개 시간순 내림차순
            ModelMap map) {
        map.addAttribute("articles", articleService.searchArticles(searchType,searchValue,pageable).map(ArticleResponse::from));
        return "articles/index";
    }

    //단건조회
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());
        return "articles/detail";
    }
}
