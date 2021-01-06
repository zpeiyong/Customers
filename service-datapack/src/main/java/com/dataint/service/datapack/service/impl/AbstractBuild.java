package com.dataint.service.datapack.service.impl;

import com.dataint.service.datapack.db.entity.*;
import com.dataint.service.datapack.model.form.*;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract class AbstractBuild {
    /**
     * 组装Article
     * @param articleForm
     * @param site
     */
    abstract Article buildArticle(ArticleForm articleForm, Site site);

    /**
     * 组装ArticleOrigin
     * @param articleOriginForm
     * @param articleId
     */
    ArticleOrigin buildArticleOrigin(ArticleOriginForm articleOriginForm, Long articleId) {
        if (articleOriginForm == null || articleOriginForm.isEmpty())
            return null;

        ArticleOrigin articleOrigin = new ArticleOrigin(articleOriginForm);
//        // articleId
//        articleOrigin.setArticleId(articleId);

        /*
        若需要, 字段长度截取
         */
        if (articleOrigin.getOrigSummary() != null && articleOrigin.getOrigSummary().length() > 1000)
            articleOrigin.setOrigSummary(getSubString(articleOrigin.getOrigSummary(), 1000));


        return articleOrigin;
    }

    /**
     *
     * @param articleExtForm
     * @param articleId
     * @return
     */
    ArticleExt buildArticleExt(ArticleExtForm articleExtForm, Long articleId) {
        if (articleExtForm == null || articleExtForm.isEmpty())
            return null;

        ArticleExt articleExt = new ArticleExt(articleExtForm);
//        // articleId
//        articleExt.setArticleId(articleId);
        /*
        若需要, 字段长度截取
         */


        return articleExt;
    }

    /**
     *
     * @param attachFormList
     * @param articleId
     * @return
     */
    List<ArticleAttach> buildArticleAttaches(List<ArticleAttachForm> attachFormList, Long articleId) {
        if (CollectionUtils.isEmpty(attachFormList))
            return null;

        List<ArticleAttach> attachList = new ArrayList<>(attachFormList.size());
        for (ArticleAttachForm attachForm : attachFormList) {
            ArticleAttach attach = new ArticleAttach(attachForm);
//            attach.setArticleId(articleId);

            attachList.add(attach);
        }

        /*
        若需要, 字段长度截取
         */


        return attachList;
    }

    /**
     * 组装ArticleDisease
     * @param staDiseaseFormList
     * @param countryDiseaseRels
     * @return
     */
    abstract List<ArticleDisease> buildArticleDisease(List<StaDiseaseForm> staDiseaseFormList, Map<String, String> countryDiseaseRels);

    public String getSubString(String oldStr, int maxLength) {
        if (oldStr == null || oldStr.length() < maxLength) {
            return oldStr;
        }

        return oldStr.substring(0, maxLength-3) + "...";
    }

    abstract String convertCountryName(List<String> countryNameList, String language);
}
