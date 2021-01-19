package com.dataint.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.dao.IArticleLikeDao;
import com.dataint.monitor.dao.IArticleReportDao;
import com.dataint.monitor.dao.IArticleUserDao;
import com.dataint.monitor.dao.ICommentDao;
import com.dataint.monitor.dao.entity.ArticleUser;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import com.dataint.monitor.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private IArticleAdapt articleAdapt;
    @Autowired
    private IArticleUserDao articleUserDao;
    @Autowired
    private IArticleLikeDao articleLikeDao;
    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private IArticleReportDao articleReportDao;

    @Override
    public ResultVO getLatestList(PageParam pageParam) {
        return null;
    }
//    @Override
//    public ResultVO getLatestList(PageParam param) {
//
//        return null;
//    }

    @Override
    public ResultVO getArticleBasicById(Long articleId) {
        return ResultVO.success(articleAdapt.getArticleById(articleId));

    }

    @Override
    public ResultVO getArticleList(ArticleListQueryParam articleListQueryParam, Long userId, String systemType) {
        // 从datapack服务获取舆情列表
        JSONObject responseJO = articleAdapt.getArticleList(articleListQueryParam);

        // 解析并构造最终返回的ArticleBasicVO
        JSONObject data = responseJO.getJSONObject("data");
        if (data != null && data.containsKey("list")) {
            List<JSONObject> rebuildJA = new ArrayList<>();
            for (Object object : data.getJSONArray("list")) {
                JSONObject rebuildJO = rebuildArticle(userId, (Map)object, systemType);
                rebuildJA.add(rebuildJO);
            }
            data.put("list", rebuildJA);
        } else {
            return JSON.parseObject(responseJO.toString(), ResultVO.class);
        }
        
        return ResultVO.success(data);
    }

    @Override
    public ResultVO getArticleById(Long userId, Long id, String systemType) {
        // 从datapack服务获取详情信息
        JSONObject responseJO = articleAdapt.getArticleById(id);

        JSONObject data = responseJO.getJSONObject("data");
        if (data != null) {
            data = rebuildArticle(userId, data, systemType);
        } else {
            return JSON.parseObject(responseJO.toString(), ResultVO.class);
        }

        return ResultVO.success(data);
    }

    @Override
    public ResultVO getSimilarArticlesById(Long userId, Long id, PageParam pageParam, String systemType) {
        // 从datapack服务获取舆情相似文章列表
        JSONObject responseJO = articleAdapt.getSimilarArticlesById(id, pageParam);

        JSONObject data = responseJO.getJSONObject("data");
        if (data != null) {
            data = rebuildArticle(userId, data, systemType);
        } else {
            return JSON.parseObject(responseJO.toString(), ResultVO.class);
        }

        return ResultVO.success(data);
    }

    @Override
    public Object delArticles(String idList) {
        Object delArticles = articleAdapt.delArticles(idList);
        return delArticles;
//        return articleProvider.delArticle(StringUtils.join(idList, Constants.JOINER));
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
    public Object addKeyword(Long userId, String idList, String keyword) {
        Object addKeyword = articleAdapt.addKeyword(idList, keyword);
//        return null;
//        JSONArray retJA = articleProvider.addKeyword(StringUtils.join(idList, Constants.JOINER), keyword).getData();
//        JSONArray  jsonArray = articleAdapt.addKeyword(, );
//
//        JSONArray rebuildJA = new JSONArray();
//        if (retJA != null) {
//            for (Object object : retJA) {
//                JSONObject rebuildJO = rebuildArticle(userId, (Map)object);
//
//                rebuildJA.add(rebuildJO);
//            }
//        }
//
//        return rebuildJA;
        return addKeyword;
    }

    @Override
    public Object delKeyword(Long userId, Long id, String keyword) {

        Object delKeyword = articleAdapt.delKeyword(id, keyword);
        return  delKeyword;
    }

    @Override
    public Object updateLevel(Long id, Long levelId) {



        Object updateLevel = articleAdapt.updateLevel(id, levelId);
        return updateLevel;
    }

    @Override
    public Object updateArticle(Long userId, ArticleUpdateForm articleUpdateForm) {

        Object updateArticle = articleAdapt.updateArticle(articleUpdateForm);
        return  updateArticle;
//        return null;
//        ResultVO<JSONObject> retVO = articleProvider.updateArticle(articleUpdateForm);
//        if (retVO.getCode() == 200) {
//            ArticleAudit ifExist = articleAuditDao.findByArticleId(articleUpdateForm.getArticleId());
//            if (ifExist != null) {
//                ifExist.setUpdatedTime(new Date());
//            } else {
//                ifExist = new ArticleAudit(articleUpdateForm.getArticleId());
//                ifExist.setCreatedTime(new Date());
//            }
//
//            articleAuditDao.save(ifExist);
//        }
//
//        JSONObject retJO = retVO.getData();
//        if (retJO != null)
//            return rebuildArticle(userId, retJO);
//
//        return new JSONObject();
    }

    @Override
    public JSONObject queryEventList(Long diseaseId,Long pageSize, Long current, String releaseTime,String searchTime) {
        JSONObject jsonObject = new JSONObject();
        Object queryEventList = articleAdapt.queryEventList(diseaseId, pageSize, current,releaseTime,searchTime);
          jsonObject= (JSONObject) queryEventList;
        return jsonObject;
    }


    /**
     * 对舆情字段进行补充
     * @param userId
     * @param articleMap
     * @return
     */
    private JSONObject rebuildArticle(Long userId, Map articleMap, String systemType) {
        if (articleMap == null) {
            return new JSONObject();
        }

        JSONObject jsonObject = new JSONObject();
        Iterator it = articleMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            jsonObject.put((String) entry.getKey(), entry.getValue());

            if ("id".equals(entry.getKey())) {
                // 当前用户是否关注 ifLike
                boolean ifLike = articleLikeDao.existsByArticleIdAndUserId((Long.valueOf((Integer)entry.getValue())), userId);
                jsonObject.put("ifLike", ifLike);

                // 后台管理界面视点模块
                if ("admin".equals(systemType)) {
                    // 评审数量
                    Integer commentCnt = commentDao.countByArticleId((Long.valueOf((Integer)entry.getValue())));
                    jsonObject.put("reviewCount", commentCnt);
                    // 是否已加入日报
                    boolean ifInReport = articleReportDao.existsByArticleId((Long.valueOf((Integer)entry.getValue())));
                    jsonObject.put("ifReport", ifInReport);
                }
//                // 当前用户是否收藏(1:已收藏；0:未收藏)
//                if (articleUserDao.findByUserIdAndArticleId(userId, (int)entry.getValue()) != null)
//                    jsonObject.put("favorite", 1);
//                else
//                    jsonObject.put("favorite", 0);
//
//                // 当前舆情是否已审核(1:已审核；0:未审核)
//                if (articleAuditDao.findByArticleId((int)entry.getValue()) != null)
//                    jsonObject.put("audit", 1);
//                else
//                    jsonObject.put("audit", 0);
            }
        }

        return jsonObject;
    }
}
