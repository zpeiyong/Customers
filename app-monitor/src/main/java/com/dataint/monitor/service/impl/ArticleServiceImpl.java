package com.dataint.monitor.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.dao.*;
import com.dataint.monitor.dao.entity.ArticleUser;
import com.dataint.monitor.model.ArticleBasicAdminVO;
import com.dataint.monitor.model.ArticleBasicVO;
import com.dataint.monitor.model.form.ArticleUpdateForm;
import com.dataint.monitor.model.param.ArticleListQueryParam;
import com.dataint.monitor.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IArticleAuditDao articleAuditDao;
    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private IArticleReportDao articleReportDao;

    @Override
    public ResultVO getLatestList(PageParam pageParam) {

        return null;
//        return articleProvider.getLatestList(pageParam.getCurrent(), pageParam.getPageSize());
    }

    @Override
    public ResultVO getArticleBasicById(Integer articleId) {

        return null;
//        return articleProvider.getArticleBasicById(articleId);
    }

    @Override
    public ResultVO getArticleList(ArticleListQueryParam articleListQueryParam, Long userId, String systemType) {
        // 从datapack服务获取舆情列表
        JSONObject responseJO = articleAdapt.getArticleList(articleListQueryParam);

        // 解析并构造最终返回的ArticleBasicVO
        JSONObject data = responseJO.getJSONObject("data");
        if (data != null && data.containsKey("list")) {
            // 后台管理界面视点模块
            if ("admin".equals(systemType)) {
                List<ArticleBasicAdminVO> abVOList = JSONArray.parseArray(data.get("list").toString(), ArticleBasicAdminVO.class);
                for (ArticleBasicAdminVO abVO : abVOList) {
                    // 当前用户是否关注 ifLike
                    boolean ifLike = articleLikeDao.existsByArticleIdAndUserId(abVO.getId(), userId);
                    abVO.setIfLike(ifLike);
                    // 评审数量
                    Integer commentCnt = commentDao.countByArticleId(abVO.getId());
                    abVO.setReviewCount(commentCnt);
                    // 是否已加入日报
                    boolean ifInReport = articleReportDao.existsByArticleId(abVO.getId());
                    abVO.setIfInReport(ifInReport);
                }
                data.put("list", abVOList);
            } else {
                List<ArticleBasicVO> abVOList = JSONArray.parseArray(data.get("list").toString(), ArticleBasicVO.class);
                for (ArticleBasicVO abVO : abVOList) {
                    // 当前用户是否关注 ifLike
                    boolean ifLike = articleLikeDao.existsByArticleIdAndUserId(abVO.getId(), userId);
                    abVO.setIfLike(ifLike);
                }
                data.put("list", abVOList);
            }
        } else {
            return JSON.parseObject(responseJO.toString(), ResultVO.class);
        }
        
        return ResultVO.success(data);
    }

    @Override
    public JSONObject getArticleById(Integer userId, Integer id) {

        return null;
//        JSONObject retJO = (JSONObject) articleProvider.getArticleById(id).getData();
//
//        if (retJO != null)
//            return rebuildArticle(userId, retJO);
//
//        return new JSONObject();
    }

    @Override
    public ResultVO delArticles(List<Integer> idList) {

        return null;
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
    public JSONArray addKeyword(Integer userId, List<Integer> idList, String keyword) {

        return null;
//        JSONArray retJA = articleProvider.addKeyword(StringUtils.join(idList, Constants.JOINER), keyword).getData();
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
    }

    @Override
    public JSONObject delKeyword(Integer userId, Integer id, String keyword) {

        return null;
//        JSONObject retJO = articleProvider.delKeyword(id, keyword).getData();
//
//        if (retJO != null)
//            return rebuildArticle(userId, retJO);
//
//        return new JSONObject();
    }

    @Override
    public ResultVO updateLevel(Integer id, Integer levelId) {

        return null;
//        return articleProvider.updateLevel(id, levelId);
    }

    @Override
    public JSONObject updateArticle(int userId, ArticleUpdateForm articleUpdateForm) {

        return null;
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
    public JSONObject queryEventList(Long diseaseId,Long pageSize, Long current, String releaseTime) {
        JSONObject jsonObject = new JSONObject();
        Object queryEventList = articleAdapt.queryEventList(diseaseId, pageSize, current,releaseTime);
          jsonObject= (JSONObject) queryEventList;
        return jsonObject;
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
