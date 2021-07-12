package com.dataint.service.datapack;

import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.IFocusDiseaseDao;
import com.dataint.service.datapack.model.param.ArticleListQueryParam;
import com.dataint.service.datapack.service.IArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ArticleTests {

    @Autowired
    private IArticleService articleService;

    private String disName = "登革热";

    @Test
    void testGetKeywordsByFoDiseaseName() {

        List<String> keywords = this.articleService.getKeywordsByFoDiseaseName(this.disName);
        for(String keyword : keywords) {
            System.out.println(keyword);
        }

        System.out.println(keywords.size());
    }

    @Test
    public void testGetArticleList() {

        ArticleListQueryParam param = new ArticleListQueryParam();
        param.setKeyword("key");

        this.articleService.getArticleList(param);
    }
}
