package com.dataint.service.datapack;

import com.dataint.service.datapack.model.param.ArticleListQueryParam;
import com.dataint.service.datapack.service.IArticleService;
import com.dataint.service.datapack.service.IDiseaseCountryCaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CountryDiseaseCaseTests {

    @Autowired
    private IDiseaseCountryCaseService diseaseCountryCaseService;

    private int diseaseId = 636;
    private int countryId = 2;
    private int week = 12;

    @Test
    void testGetKeywordsByFoDiseaseName() {

       List result = this.diseaseCountryCaseService.getForCountryRisk1(diseaseId,countryId,week);

       System.out.println(result.size());
       System.out.println(result);
    }

}
