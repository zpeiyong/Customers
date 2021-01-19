package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.MD5Util;
import com.dataint.service.datapack.db.IArticleEvent;
import com.dataint.service.datapack.db.dao.IArticleDao;
import com.dataint.service.datapack.db.dao.ICountryDao;
import com.dataint.service.datapack.db.dao.IFocusDiseaseDao;
import com.dataint.service.datapack.db.dao.ISiteDao;
import com.dataint.service.datapack.db.entity.*;
import com.dataint.service.datapack.model.form.*;
import com.dataint.service.datapack.model.param.ArticleListQueryParam;
import com.dataint.service.datapack.model.vo.ArticleBasicVO;
import com.dataint.service.datapack.model.vo.ArticleReportVO;
import com.dataint.service.datapack.model.vo.ArticleVO;
import com.dataint.service.datapack.model.vo.BIArticleBasicVO;
import com.dataint.service.datapack.service.IArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl extends AbstractBuild implements IArticleService {

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private ISiteDao siteDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IFocusDiseaseDao diseaseDao;

    @Override
    public List<Map<String, Object>> queryEventList(Long diseaseId, int pageSize, int current, String  releaseTime,String searchTime) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");


        //默认查询情况，查询最近七天的事件列表信息
        if (releaseTime==null){
            Date searchingTime = null;
            try {
                searchingTime = sdformat.parse(searchTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> utilTimeList = buildRespList(searchingTime, 7);
            for (int i=0; i<utilTimeList.size(); i++){
                String day = utilTimeList.get(i).get("day").toString();
                Page<IArticleEvent> releaseLike = articleDao.findGmtTime(diseaseId,day+"%", PageRequest.of(current-1, pageSize));

                Pagination pagination  = new Pagination();
                pagination.setCurrent(current);
                pagination.setPageSize(pageSize);
                pagination.setTotal(releaseLike.getTotalElements());

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("list", releaseLike.getContent());
                responseMap.put("pagination", pagination);

                utilTimeList.get(i).put("value", responseMap);
            }
            return  utilTimeList;
        }
        //查询指定日期的事件记录
        else {
            Date dates = null;
            try {
                dates = sdformat.parse(releaseTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> listOneDay = buildRespListOneDay(dates);
            Page<IArticleEvent> releaseLike = articleDao.findGmtTime(diseaseId,releaseTime+"%", PageRequest.of(current-1, pageSize));

            Pagination pagination  = new Pagination();
            pagination.setCurrent(current);
            pagination.setPageSize(pageSize);
            pagination.setTotal(releaseLike.getTotalElements());


            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("list", releaseLike.getContent());
            responseMap.put("pagination", pagination);

            listOneDay.get(0).put("value", responseMap);
            return listOneDay;
        }
    }

    private static List<Map<String, Object>> buildRespList(Date date, Integer days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>(days);
        Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);
        }
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        for (int i = days; i > 0; i--) {
            Map<String, Object> dataMap = new HashMap<>(2);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dataMap.put("day", sdf.format(calendar.getTime()));
            dataMap.put("value", "");
            respList.add(dataMap);
        }

        return respList;
    }
    private static List<Map<String, Object>> buildRespListOneDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> respList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

            Map<String, Object> dataMap = new HashMap<>(2);
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dataMap.put("day", sdf.format(calendar.getTime()));
            dataMap.put("value", "");
            respList.add(dataMap);
        return respList;
    }

    @Override
    public void storeData(StoreDataForm storeDataForm) {
        String md5 = MD5Util.md5(storeDataForm.toString());
        /*
         * 数据转换 && 数据存储
         */
        // 组装网站对象并存储
        SiteForm siteForm = storeDataForm.getSiteForm();
        Site site = siteDao.findByNameCnAndUrl(siteForm.getNameCn(), siteForm.getUrl());
        if (site == null) {
            site = siteForm.toPo(Site.class);
            siteDao.save(site);
        }

        // 组装 Article 对象, 暂不保存
        ArticleForm articleForm = storeDataForm.getArticleForm();
        Article ifArticleExist = articleDao.findByArticleKey(articleForm.getArticleKey());
        if (ifArticleExist != null) {
            if (md5.equals(ifArticleExist.getInputMd5()))
                throw new DataAlreadyExistException("该数据已存在");
            else
                // TODO: 当同一条articleKey不同数据请求存储, 是update还是忽略??
                // article.setId(ifArticleExist.getId());
                throw new DataAlreadyExistException("该数据已存在");
        }
        Article article = buildArticle(articleForm, site);
        article.setInputMd5(md5);

        // 组装 ArticleOrigin 对象并存储
        ArticleOriginForm articleOriginForm = storeDataForm.getArticleOriginForm();
        ArticleOrigin articleOrigin = buildArticleOrigin(articleOriginForm, article.getId());
        article.setArticleOrigin(articleOrigin);

        // 组装 ArticleExt 对象并存储
        ArticleExtForm articleExtForm = storeDataForm.getArticleExtForm();
        ArticleExt articleExt = buildArticleExt(articleExtForm, article.getId());
        article.setArticleExt(articleExt);

        // 组装 ArticleAttach 列表并存储
        List<ArticleAttach> attachList = buildArticleAttaches(storeDataForm.getArticleAttachFormList(), article.getId());
        article.setAttachList(attachList);

        // 组装 ArticleDisease 国家传染病对应关系
        List<ArticleDisease> diseaseList = buildArticleDisease(storeDataForm.getStaDiseaseFormList(), articleForm.getCountryDiseaseRels());
        article.setDiseaseList(diseaseList);

        // 舆情信息保存
        articleDao.save(article);
    }

    @Override
    public Article buildArticle(ArticleForm articleForm, Site site) {
        Article article = articleForm.toPo(Article.class);
        // siteId
        article.setSite(site);
        // keywords
        if (!CollectionUtils.isEmpty(articleForm.getKeywordList())) {
            article.setKeywords(StringUtils.join(articleForm.getKeywordList(), Constants.JOINER));
        }

        /*
        若需要, 字段长度截取
         */
        if (article.getSummary() != null && article.getSummary().length() > 2000)
            article.setSummary(getSubString(article.getSummary(), 1997) + "...");

        return article;
    }

    @Override
    public List<ArticleDisease> buildArticleDisease(List<StaDiseaseForm> staDiseaseFormList, Map<String, String> countryDiseaseRels) {
        /* [国家|传染病]通过算法应存在对应关系 */
        List<ArticleDisease> diseaseList = new ArrayList<>();

        // pubinfo类型舆情才会有该字段
        if (!CollectionUtils.isEmpty(countryDiseaseRels)) {
            for (Map.Entry<String, String> entry : countryDiseaseRels.entrySet()) {
                ArticleDisease articleDisease = new ArticleDisease();

                // 根据预处理传过来的值获取国家和传染病信息
                Country country = countryDao.findByNameCn(entry.getKey());
                if (country != null) {
                    articleDisease.setCountryId(country.getId());
                    articleDisease.setCountryCode(country.getCode());
                } else {
                    articleDisease.setCountryCode(entry.getKey());
                }
                FocusDisease disease = diseaseDao.findByNameCn(entry.getValue());
                if (disease != null) {
                    articleDisease.setDiseaseId(disease.getId());
                    articleDisease.setDiseaseCode(disease.getCode());
                } else {
                    articleDisease.setDiseaseCode(entry.getValue());
                }

                diseaseList.add(articleDisease);
            }
        }

        // statistic类型舆情才会有该字段
        if (!CollectionUtils.isEmpty(staDiseaseFormList)) {
            for (StaDiseaseForm staDiseaseForm : staDiseaseFormList) {
                ArticleDisease articleDisease = new ArticleDisease();

                //
                Country country = countryDao.findByNameCn(staDiseaseForm.getCountry());
                if (country != null) {
                    articleDisease.setCountryId(country.getId());
                    articleDisease.setCountryCode(country.getCode());
                } else {
                    articleDisease.setCountryCode(staDiseaseForm.getCountry());
                }
                FocusDisease disease = diseaseDao.findByNameCn(staDiseaseForm.getDiseaseName());
                if (disease != null) {
                    articleDisease.setDiseaseId(disease.getId());
                    articleDisease.setDiseaseCode(disease.getCode());
                }

                //
                if (staDiseaseForm.getTimePeriodStart() != null) {
                    articleDisease.setDiseaseStart(staDiseaseForm.getTimePeriodStart());
                }
                if (staDiseaseForm.getTimePeriodEnd() != null) {
                    articleDisease.setDiseaseEnd(staDiseaseForm.getTimePeriodEnd());
                }
                if (staDiseaseForm.getPeriodConfirm() != null) {
                    articleDisease.setPeriodConfirm(Integer.valueOf(staDiseaseForm.getPeriodConfirm()));
                }
                if (staDiseaseForm.getPeriodDeath() != null) {
                    articleDisease.setPeriodDeath(Integer.valueOf(staDiseaseForm.getPeriodDeath()));
                }
                if (staDiseaseForm.getPeriodCure() != null) {
                    articleDisease.setPeriodCure(Integer.valueOf(staDiseaseForm.getPeriodCure()));
                }
                if (staDiseaseForm.getConfirmCases() != null) {
                    articleDisease.setConfirmCases(Integer.valueOf(staDiseaseForm.getConfirmCases()));
                }
                if (staDiseaseForm.getDeathCases() != null) {
                    articleDisease.setDeathCases(Integer.valueOf(staDiseaseForm.getDeathCases()));
                }
                if (staDiseaseForm.getCureCases() != null) {
                    articleDisease.setCureCases(Integer.valueOf(staDiseaseForm.getCureCases()));
                }

                diseaseList.add(articleDisease);
            }
        }

        //
        if (diseaseList.size() == 0) {
            return null;
        }

        return diseaseList;
    }

    @Override
    public String convertCountryName(List<String> countryNameList, String language) {
        if (countryNameList == null) {
            return null;
        }

        StringBuffer sb =  new StringBuffer();
        for (String countryName : countryNameList) {
            Country countryItem;
            // 若语言为中文, 则使用国家中文名称匹配
            if ("cn".equals(language))
                countryItem = countryDao.findByNameCn(countryName);
                // 若语言为其他语言, 则使用国家英文名称匹配
            else
                countryItem = countryDao.findByNameEn(countryName);

            sb.append(countryItem == null ? countryName : countryItem.getCode()).append(Constants.JOINER);
        }
        String countryCode = sb.toString();

        if (!StringUtils.isEmpty(countryCode) && countryCode.contains(Constants.JOINER))
            countryCode = countryCode.substring(0, countryCode.length()-1);

        return countryCode;
    }

    @Override
    public ResultVO queryBasicList(PageParam pageParam) {
        Page<Article> pageResult = articleDao.findAllByIfDeleted(false, pageParam.toPageRequest("gmtRelease"));
        List<ArticleBasicVO> collect = pageResult.getContent().stream().map(ArticleBasicVO::new).collect(Collectors.toList());
        Pagination pagination = new Pagination();
        pagination.setCurrent(pageParam.getCurrent());
        pagination.setPageSize(pageParam.getPageSize());
        pagination.setTotal(pageResult.getTotalElements());
        return ResultVO.success(collect, pagination);
    }

    @Override
    public BIArticleBasicVO queryBasicById(Long articleId) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException();
        }

        return new BIArticleBasicVO(articleOpt.get());
    }

    @Override
    public ResultVO queryMapBasicList(Long countryId, Long diseaseId, String searchTime, PageParam pageParam) {
        Page<Article> mapArticlePage = articleDao.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                // 疫情id and 国家id
                if (!ObjectUtils.isEmpty(diseaseId) && diseaseId != 0) {
                    Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
                    Root<ArticleDisease> adSubRoot = subQuery.from(ArticleDisease.class);

                    // 子查询的条件列表
                    List<Predicate> subList = new ArrayList<>();

                    subList.add(criteriaBuilder.equal(adSubRoot.get("diseaseId").as(Long.class), diseaseId));
                    if (!ObjectUtils.isEmpty(countryId) && countryId != 0) {
                        subList.add(criteriaBuilder.equal(adSubRoot.get("countryId").as(Long.class), countryId));
                    }
                    subQuery.distinct(true)
                            .select(adSubRoot.get("articleId").as(Long.class))
                            .where(subList.toArray(new Predicate[subList.size()]));

                    //
                    list.add(criteriaBuilder.in(root.get("id")).value(subQuery));
                }

                // 查询日期
                if (!StringUtils.isEmpty(searchTime)) {
                    // 转换日期格式
                    Date searchDate;
                    try {
                        searchDate = Constants.getDateTimeFormat().parse(searchTime + " 23:59:59");
                    } catch (ParseException e) {
                        throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
                    }
                    list.add(criteriaBuilder.lessThanOrEqualTo(root.get("gmtRelease").as(Date.class), searchDate));
                }

                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, pageParam.toPageRequest( "gmtRelease"));

        List<BIArticleBasicVO> mapBasicVOList = mapArticlePage.getContent().stream().map(BIArticleBasicVO::new).collect(Collectors.toList());
        Pagination pagination = new Pagination(pageParam.getPageSize(), mapArticlePage.getTotalElements(), pageParam.getCurrent());

        return ResultVO.success(mapBasicVOList, pagination);
    }

    @Override
    public ResultVO getArticleList(ArticleListQueryParam queryParam) {
        // 大洲对应国家列表准备
        final List<String> countryCodeList;
        if (!ObjectUtils.isEmpty(queryParam.getRegionId())) {
            List<String> codeTempList;
            if (queryParam.getRegionId() == 0) {
                codeTempList = com.dataint.service.datapack.utils.Constants.focusCountryMap.values()
                        .stream()
                        .map(Country::getCode)
                        .collect(Collectors.toList());
            } else {
                codeTempList = countryDao.findAllByRegionId(queryParam.getRegionId())
                        .stream()
                        .map(Country::getCode)
                        .collect(Collectors.toList());
            }
            countryCodeList = codeTempList;
        } else {
            countryCodeList = null;
        }

        Page<Article> articlePage = articleDao.findAll(new Specification<Article>() {
            Calendar calendar = Calendar.getInstance();
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();

                // 关键词
                if (!StringUtils.isEmpty(queryParam.getKeyword())) {
                    Predicate p1 = criteriaBuilder.like(root.get("title").as(String.class), "%"+queryParam.getKeyword()+"%");
                    Predicate p2 = criteriaBuilder.like(root.get("content").as(String.class), "%"+queryParam.getKeyword()+"%");
                    list.add(criteriaBuilder.or(p1, p2));
                }

                // 疫情类型id
                if (!ObjectUtils.isEmpty(queryParam.getDiseaseId()) && queryParam.getDiseaseId() != 0) {
                    Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
                    Root<ArticleDisease> adSubRoot = subQuery.from(ArticleDisease.class);
                    //
                    Predicate subPredicate = criteriaBuilder.equal(adSubRoot.get("diseaseId").as(Long.class), queryParam.getDiseaseId());

                    subQuery.distinct(true)
                            .select(adSubRoot.get("articleId").as(Long.class))
                            .where(subPredicate);
                    list.add(criteriaBuilder.in(root.get("id")).value(subQuery));
                }

                // 来源媒体
                if (!ObjectUtils.isEmpty(queryParam.getMediaTypeId()) && queryParam.getMediaTypeId() != 0) {
                    list.add(criteriaBuilder.equal(root.get("mediaTypeId").as(Long.class), queryParam.getMediaTypeId()));
                }

                // 舆情文章内容类型
                if (!StringUtils.isEmpty(queryParam.getArticleType()) && !"all".equals(queryParam.getArticleType())) {
                    list.add(criteriaBuilder.equal(root.get("articleType").as(String.class), queryParam.getArticleType()));
                }

                // 地区
                if (!CollectionUtils.isEmpty(countryCodeList)) {
                    Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
                    Root<ArticleDisease> adSubRoot = subQuery.from(ArticleDisease.class);
                    //
                    Predicate subPredicate = adSubRoot.get("countryCode").as(String.class).in(countryCodeList);
                    subQuery.distinct(true)
                            .select(adSubRoot.get("articleId").as(Long.class))
                            .where(subPredicate);
                    list.add(criteriaBuilder.in(root.get("id")).value(subQuery));
                }

                // 爬取时间
                try {
                    if (!StringUtils.isEmpty(queryParam.getCrawlTimeStart())) {
                        calendar.setTime(Constants.DateTimeSDF.parse(queryParam.getCrawlTimeStart()));
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("gmtCrawl").as(Date.class), calendar.getTime()));
                    }
                    if (!StringUtils.isEmpty(queryParam.getCrawlTimeEnd())) {
                        calendar.setTime(Constants.DateTimeSDF.parse(queryParam.getCrawlTimeEnd()));
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("gmtCrawl").as(Date.class), calendar.getTime()));
                    }
                } catch (ParseException e) {
                    log.error("日期解析错误!", e);
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, queryParam.toPageRequest( "gmtRelease"));

        // 封装为文章基础字段vo对象列表
        List<ArticleBasicVO> basicVOList = articlePage.getContent()
                .stream()
                .map(article -> {
                    ArticleBasicVO basicVO = new ArticleBasicVO(article);
                    /*
                    TODO: 需要返回- 当前用户是否关注, 相似文章数量; 后台管理另需要- 评审数量, 是否已加入日报, 是否有原文, 是否已关联到相似文章
                    service-datapack:
                        - 相似文章数量
                        - 是否有原文
                        - 是否已关联到相似文章
                    app-monitor:
                        - 当前用户是否关注
                        - 评审数量
                        - 是否已加入日报
                     */
                    // 相似文章数量, 是否已关联到相似文章
                    if ("pubinfo".equals(article.getArticleType()) && article.getIfSimilar()) {
                        int similarArticleCnt;
                        if (article.getSimilarArticleId() == 0) {
                            similarArticleCnt = articleDao.countBySimilarArticleId(article.getId());
                        } else {
                            similarArticleCnt = articleDao.countBySimilarArticleId(article.getSimilarArticleId());
                        }
                        basicVO.setIfSimilar(true);
                        basicVO.setSimilarArticleCnt(similarArticleCnt);
                    } else {
                        basicVO.setIfSimilar(false);
                        basicVO.setSimilarArticleCnt(0);
                    }
                    return basicVO;
                }).collect(Collectors.toList());

        Pagination pagination = new Pagination(queryParam.getPageSize(),articlePage.getTotalElements(), queryParam.getCurrent());

        return ResultVO.success(basicVOList, pagination);
    }

    @Override
    public ArticleVO getArticleById(Long articleId) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException("当前数据不存在");
        }
        Article article = articleOpt.get();

        return new ArticleVO(article);
    }

    @Override
    public ResultVO getSimilarArticlesById(Long articleId, PageParam pageParam) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException("当前数据不存在");
        }
        Article article = articleOpt.get();

        // 当前舆情similarId=0则根据当前舆情id找，否则同时要找similarId这条舆情
        Page<Article> similarArticlePage;
        if (article.getSimilarArticleId() == 0) {
            similarArticlePage = articleDao.findAllBySimilarArticleId(
                    article.getId(),
                    pageParam.toPageRequest());
        } else {
            similarArticlePage = articleDao.findAllBySimilarArticleIdOrId(
                    article.getSimilarArticleId(),
                    article.getSimilarArticleId(),
                    pageParam.toPageRequest());
        }

        List<ArticleBasicVO> articleBasicVOList = similarArticlePage.getContent().stream().map(ArticleBasicVO::new).collect(Collectors.toList());
        Pagination pagination = new Pagination(pageParam.getPageSize(), similarArticlePage.getTotalElements(), pageParam.getCurrent());

        return ResultVO.success(articleBasicVOList, pagination);
    }

    @Transactional
    @Override
    public void delArticleById(Long articleId) {
        // check if article exist
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Article article = articleOpt.get();
        article.setIfDeleted(true);
        articleDao.save(article);
    }

    @Transactional
    @Override
    public List<ArticleBasicVO> addKeyword(List<Long> idList, String keyword) {
        List<ArticleBasicVO> basicVOList = new ArrayList<>();

        for (Long id : idList) {
            Optional<Article> articleOpt = articleDao.findById(id);
            if (articleOpt.isPresent()) {
                Article article = articleOpt.get();

                // keyword转存到有序不重复集合中
                Set<String> keywordSet;
                if (!StringUtils.isEmpty(article.getKeywords())) {
                    keywordSet = new LinkedHashSet<>(Arrays.asList(article.getKeywords().split(Constants.SPLITTER)));
                } else {
                    keywordSet = new LinkedHashSet<>();
                }
                keywordSet.add(keyword);

                // 持久化
                String keywordListStr = StringUtils.join(keywordSet, Constants.JOINER);
                article.setKeywords(keywordListStr);
                articleDao.save(article);

                // 转换为VO以返回
                basicVOList.add(new ArticleBasicVO(article));
            } else
                basicVOList.add(new ArticleBasicVO());
        }

        return basicVOList;
    }

    @Transactional
    @Override
    public ArticleBasicVO delKeyword(Long id, String keyword) {
        Optional<Article> articleOpt = articleDao.findById(id);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Article article = articleOpt.get();

        // keyword转存到集合中
        Set<String> keywordSet;
        if (!StringUtils.isEmpty(article.getKeywords())) {
            keywordSet = new HashSet<>(Arrays.asList(article.getKeywords().split(Constants.SPLITTER)));
        } else {
            keywordSet = new HashSet<>();
        }
        keywordSet.remove(keyword);

        // 持久化
        String keywordListStr = StringUtils.join(keywordSet, Constants.JOINER);
        article.setKeywords(keywordListStr);
        articleDao.save(article);

        // 转换为VO以返回
        return new ArticleBasicVO(article);
    }

    @Transactional
    @Override
    public ArticleVO updateArticle(ArticleUpdateForm articleUpdateForm) {
        // check if article exist
        Optional<Article> articleOpt = articleDao.findById(articleUpdateForm.getArticleId());
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Article article = articleOpt.get();

        // diseaseList
        if (articleUpdateForm.getDiseaseFormList() != null) {
            List<ArticleDisease> diseaseList = new ArrayList<>();
            for (ArticleDiseaseForm diseaseForm : articleUpdateForm.getDiseaseFormList()) {
                ArticleDisease articleDisease = new ArticleDisease();
                BeanUtils.copyProperties(diseaseForm, articleDisease);

                // diseases
                FocusDisease diseases = diseaseDao.getOne(diseaseForm.getDiseaseId());
                articleDisease.setDiseaseCode(diseases.getNameCn());
                articleDisease.setDiseaseId(diseaseForm.getDiseaseId());
                try {
                    if (!StringUtils.isEmpty(diseaseForm.getDiseaseStart()))
                        articleDisease.setDiseaseStart(Constants.DateSDF.parse(diseaseForm.getDiseaseStart()));
                    if (!StringUtils.isEmpty(diseaseForm.getDiseaseEnd()))
                        articleDisease.setDiseaseEnd(Constants.DateSDF.parse(diseaseForm.getDiseaseEnd()));
                } catch (ParseException pe) {
                    System.out.println("时间格式有误!");
                    pe.printStackTrace();
                }
//                // countries
//                List<Long> countryIdList = diseaseForm.getCountryIdList();
//                if (countryIdList != null && countryIdList.size()>0) {
//                    List<Country> countryList = countryDao.findAllById(countryIdList);
////                    articleDisease.setCountryIds(StringUtils.join(
////                            countryIdList, Constants.JOINER));
////                    articleDisease.setCountryCodes(StringUtils.join(
////                            countryList.stream().map(Country::getCode).collect(Collectors.toList()), Constants.JOINER));
//                }

                diseaseList.add(articleDisease);
            }
//            article.setDiseaseList(diseaseList);
        }
        // summary
        if (!StringUtils.isEmpty(articleUpdateForm.getSummary()))
            article.setSummary(articleUpdateForm.getSummary());

        // other infos
        article.setUpdatedTime(new Date());

        articleDao.save(article);
        return new ArticleVO(article);
    }

    @Override
    public List<String> searchByKeyword(String keyword) {

        return articleDao.searchByKeyword(keyword);
    }

    @Override
    public List<ArticleReportVO> queryReportContent(List<Long> articleIdList) {
        List<Article> articleList = articleDao.findAllByIdInOrderByGmtRelease(articleIdList);
        List<ArticleReportVO> voList = articleList.stream().map(ArticleReportVO::new).collect(Collectors.toList());

        return voList;
    }
}
