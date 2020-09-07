package com.dataint.diseasepo.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.diseasepo.dao.IArticleAuditDao;
import com.dataint.diseasepo.dao.IArticleUserDao;
import com.dataint.diseasepo.dao.entity.ArticleAudit;
import com.dataint.diseasepo.dao.entity.ArticleUser;
import com.dataint.diseasepo.model.form.ArticleUpdateForm;
import com.dataint.diseasepo.model.param.ArticleListQueryParam;
import com.dataint.diseasepo.provider.ArticleProvider;
import com.dataint.diseasepo.service.IArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements IArticleService {

    @Autowired
    private ArticleProvider articleProvider;

    @Autowired
    private IArticleUserDao articleUserDao;

    @Autowired
    private IArticleAuditDao articleAuditDao;

    @Override
    public ResultVO getLatestList(PageParam pageParam) {

        return articleProvider.getLatestList(pageParam.getCurrent(), pageParam.getPageSize());
    }

    @Override
    public ResultVO getArticleBasicById(Integer articleId) {

        return articleProvider.getArticleBasicById(articleId);
    }

    @Override
    public JSONObject getArticleList(Integer userId, ArticleListQueryParam articleListQueryParam) {
        JSONObject retJO = articleProvider.getArticleList(articleListQueryParam.getCurrent(), articleListQueryParam.getPageSize(), articleListQueryParam.getArticleType()).getData();

        JSONArray rebuildJA = new JSONArray();
        for (Object object : retJO.getJSONArray("content")) {
            JSONObject rebuildJO = rebuildArticle(userId, (Map)object);

            rebuildJA.add(rebuildJO);
        }
        retJO.put("content", rebuildJA);

        return retJO;
    }

    @Override
    public JSONObject getArticleById(Integer userId, Integer id) {
        JSONObject retJO = (JSONObject) articleProvider.getArticleById(id).getData();

        if (retJO != null)
            return rebuildArticle(userId, retJO);

        return new JSONObject();
    }

    @Override
    public ResultVO delArticles(List<Integer> idList) {

        return articleProvider.delArticle(StringUtils.join(idList, Constants.JOINER));
    }

    @Override
    public boolean updateFavorites(Integer userId, List<Integer> articleIdList) {
        boolean ifMultiFlag = false;
        if (articleIdList.size() > 1) {
            ifMultiFlag = true;
        }

        //
        for (Integer articleId : articleIdList) {
            // check if exist
            ArticleUser ifExist = articleUserDao.findByUserIdAndArticleId(userId, articleId);

            if (ifExist == null) {
                articleUserDao.save(new ArticleUser(articleId, userId));
            } else {
                // 批量更新时不做删除操作
                if (!ifMultiFlag) {
                   articleUserDao.deleteById(ifExist.getId());
                }
            }
        }

        return true;
    }

    @Override
    public JSONArray addKeyword(Integer userId, List<Integer> idList, String keyword) {
        JSONArray retJA = articleProvider.addKeyword(StringUtils.join(idList, Constants.JOINER), keyword).getData();

        JSONArray rebuildJA = new JSONArray();
        if (retJA != null) {
            for (Object object : retJA) {
                JSONObject rebuildJO = rebuildArticle(userId, (Map)object);

                rebuildJA.add(rebuildJO);
            }
        }

        return rebuildJA;
    }

    @Override
    public JSONObject delKeyword(Integer userId, Integer id, String keyword) {
        JSONObject retJO = articleProvider.delKeyword(id, keyword).getData();

        if (retJO != null)
            return rebuildArticle(userId, retJO);

        return new JSONObject();
    }

    @Override
    public ResultVO updateLevel(Integer id, Integer levelId) {

        return articleProvider.updateLevel(id, levelId);
    }

    @Override
    public JSONObject updateArticle(int userId, ArticleUpdateForm articleUpdateForm) {
        ResultVO<JSONObject> retVO = articleProvider.updateArticle(articleUpdateForm);
        if (retVO.getCode() == 200) {
            ArticleAudit ifExist = articleAuditDao.findByArticleId(articleUpdateForm.getArticleId());
            if (ifExist != null) {
                ifExist.setUpdatedTime(new Date());
            } else {
                ifExist = new ArticleAudit(articleUpdateForm.getArticleId());
                ifExist.setCreatedTime(new Date());
            }

            articleAuditDao.save(ifExist);
        }

        JSONObject retJO = retVO.getData();
        if (retJO != null)
            return rebuildArticle(userId, retJO);

        return new JSONObject();
    }


    /**
     * 对舆情字段进行补充
     * @param userId
     * @param articleMap
     * @return
     */
    private JSONObject rebuildArticle(int userId, Map articleMap) {
        if (articleMap == null) {
            return new JSONObject();
        }

        JSONObject jsonObject = new JSONObject();

        Iterator it = articleMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            jsonObject.put((String) entry.getKey(), entry.getValue());

            if ("id".equals(entry.getKey())) {
                // 当前用户是否收藏(1:已收藏；0:未收藏)
                if (articleUserDao.findByUserIdAndArticleId(userId, (int)entry.getValue()) != null)
                    jsonObject.put("favorite", 1);
                else
                    jsonObject.put("favorite", 0);

                // 当前舆情是否已审核(1:已审核；0:未审核)
                if (articleAuditDao.findByArticleId((int)entry.getValue()) != null)
                    jsonObject.put("audit", 1);
                else
                    jsonObject.put("audit", 0);
            }
        }

        return jsonObject;
    }
}
