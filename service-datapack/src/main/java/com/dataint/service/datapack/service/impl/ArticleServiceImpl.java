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
import com.dataint.service.datapack.db.dao.*;
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
import org.springframework.data.domain.Pageable;
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
    private IFocusDiseaseDao fDiseaseDao;

    @Autowired
    private IDiseasesDao diseaseDao;

    @Autowired
    private IArticleDiseaseDao articleDiseaseDao;

    @Override
    public List<Map<String, Object>> queryEventList(Long diseaseId, int pageSize, int current, String releaseTime, String searchTime) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        //????????????????????????????????????????????????????????????
        if (releaseTime == null) {
            Date searchingTime = null;
            try {
                searchingTime = sdformat.parse(searchTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            List<Map<String, Object>> utilTimeList = buildRespList(searchingTime, 7);
            for (int i = 0; i < utilTimeList.size(); i++) {
                String day = utilTimeList.get(i).get("day").toString();
                Page<IArticleEvent> releaseLike = articleDao.findGmtTime(diseaseId, day + "%", PageRequest.of(current - 1, pageSize));

                Pagination pagination = new Pagination();
                pagination.setCurrent(current);
                pagination.setPageSize(pageSize);
                pagination.setTotal(releaseLike.getTotalElements());

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("list", releaseLike.getContent());
                responseMap.put("pagination", pagination);

                utilTimeList.get(i).put("value", responseMap);
            }
            return utilTimeList;
        }
        //?????????????????????????????????
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
         * ???????????? && ????????????
         */
        // ???????????????????????????
        SiteForm siteForm = storeDataForm.getSiteForm();
        Site site = siteDao.findByNameCnAndUrl(siteForm.getNameCn(), siteForm.getUrl());
        if (site == null) {
            site = siteForm.toPo(Site.class);
            siteDao.save(site);
        }

        // ?????? Article ??????, ????????????
        ArticleForm articleForm = storeDataForm.getArticleForm();
        Article ifArticleExist = articleDao.findByArticleKey(articleForm.getArticleKey());
        if (ifArticleExist != null) {
            if (md5.equals(ifArticleExist.getInputMd5()))
                throw new DataAlreadyExistException("??????????????????");
            else
                // TODO: ????????????articleKey????????????????????????, ???update??????????????
                // article.setId(ifArticle.Exist.getId());
                throw new DataAlreadyExistException("??????????????????");
        }
        Article article = buildArticle(articleForm, site);
        article.setInputMd5(md5);

        // ?????? ArticleOrigin ???????????????
        ArticleOriginForm articleOriginForm = storeDataForm.getArticleOriginForm();
        ArticleOrigin articleOrigin = buildArticleOrigin(articleOriginForm, article.getId());
        article.setArticleOrigin(articleOrigin);

        // ?????? ArticleExt ???????????????
        ArticleExtForm articleExtForm = storeDataForm.getArticleExtForm();
        ArticleExt articleExt = buildArticleExt(articleExtForm, article.getId());
        article.setArticleExt(articleExt);

        // ?????? ArticleAttach ???????????????
        List<ArticleAttach> attachList = buildArticleAttaches(storeDataForm.getArticleAttachFormList(), article.getId());
        article.setAttachList(attachList);

        // ?????? ArticleDisease ???????????????????????????
        List<ArticleDisease> diseaseList = buildArticleDisease(storeDataForm.getStaDiseaseFormList(), articleForm.getCountryDiseaseRels());
        article.setDiseaseList(diseaseList);

        // ??????????????????
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
        ?????????, ??????????????????
         */
        if (article.getSummary() != null && article.getSummary().length() > 2000)
            article.setSummary(getSubString(article.getSummary(), 1997) + "...");

        return article;
    }

    @Override
    public List<ArticleDisease> buildArticleDisease(List<StaDiseaseForm> staDiseaseFormList, Map<String, String> countryDiseaseRels) {
        /* [??????|?????????]????????????????????????????????? */
        List<ArticleDisease> diseaseList = new ArrayList<>();

        // pubinfo??????????????????????????????
        if (!CollectionUtils.isEmpty(countryDiseaseRels)) {
            for (Map.Entry<String, String> entry : countryDiseaseRels.entrySet()) {
                ArticleDisease articleDisease = new ArticleDisease();

                // ????????????????????????????????????????????????????????????
               // Country country = countryDao.findByNameCn(entry.getKey());

                String alias = "%|" + entry.getKey() + "|%";
                Country country = this.countryDao.findByNameCnOrAlias(entry.getKey(), alias);

                if (country != null) {
                    articleDisease.setCountryId(country.getId());
                    articleDisease.setCountryCode(country.getCode());
                } else {
                    articleDisease.setCountryCode(entry.getKey());
                }

                String diseaseName = entry.getValue();
                String diseaseAlias = "%|" + diseaseName + "|%";
             //   FocusDisease disease = diseaseDao.findByNameCn(entry.getValue());
                Diseases disease = this.diseaseDao.findByNameCnOrAlias(diseaseName,diseaseAlias);
                //FocusDisease disease = this.diseaseDao.findByNameCnOrAlias(diseaseName,diseaseAlias);

                if (disease != null) {
                    articleDisease.setDiseaseId(disease.getId());
                    articleDisease.setDiseaseCode(disease.getIcd10Code());
                } else {
                    articleDisease.setDiseaseCode(entry.getValue());
                }

                diseaseList.add(articleDisease);
            }
        }

        // statistic??????????????????????????????
        if (!CollectionUtils.isEmpty(staDiseaseFormList)) {
            for (StaDiseaseForm staDiseaseForm : staDiseaseFormList) {
                ArticleDisease articleDisease = new ArticleDisease();

                //
                String alias = "%|" + staDiseaseForm.getCountry() + "|%";
                Country country = this.countryDao.findByNameCnOrAlias(staDiseaseForm.getCountry(),alias);
                if (country != null) {
                    articleDisease.setCountryId(country.getId());
                    articleDisease.setCountryCode(country.getCode());
                } else {
                    articleDisease.setCountryCode(staDiseaseForm.getCountry());
                }
                String diseaseAlias = "%|" + staDiseaseForm.getDiseaseName() + "|%";

                Diseases disease = this.diseaseDao.findByNameCnOrAlias(staDiseaseForm.getDiseaseName(),diseaseAlias);
                if (disease != null) {
                    articleDisease.setDiseaseId(disease.getId());
                    articleDisease.setDiseaseCode(disease.getIcd10Code());
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
            // ??????????????????, ?????????????????????????????????
            if ("cn".equals(language))
                countryItem = countryDao.findByNameCn(countryName);
                // ????????????????????????, ?????????????????????????????????
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

                // ??????id and ??????id
                if (!ObjectUtils.isEmpty(diseaseId) && diseaseId != 0) {
                    Subquery<Long> subQuery = criteriaQuery.subquery(Long.class);
                    Root<ArticleDisease> adSubRoot = subQuery.from(ArticleDisease.class);

                    // ????????????????????????
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

                // ????????????
                if (!StringUtils.isEmpty(searchTime)) {
                    // ??????????????????
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
        // ??????????????????????????????
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

                // ?????????
                if (!StringUtils.isEmpty(queryParam.getKeyword())) {
                    Predicate p1 = criteriaBuilder.like(root.get("title").as(String.class), "%"+queryParam.getKeyword()+"%");
                    Predicate p2 = criteriaBuilder.like(root.get("content").as(String.class), "%"+queryParam.getKeyword()+"%");
                    list.add(criteriaBuilder.or(p1, p2));
                }

                // ????????????id
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

                // ????????????
                if (!ObjectUtils.isEmpty(queryParam.getMediaTypeId()) && queryParam.getMediaTypeId() != 0) {
                    list.add(criteriaBuilder.equal(root.get("mediaTypeId").as(Long.class), queryParam.getMediaTypeId()));
                }

                // ????????????????????????
                if (!StringUtils.isEmpty(queryParam.getArticleType()) && !"all".equals(queryParam.getArticleType())) {
                    list.add(criteriaBuilder.equal(root.get("articleType").as(String.class), queryParam.getArticleType()));
                }

                // ??????
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

                // ????????????
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
                    log.error("??????????????????!", e);
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        }, queryParam.toPageRequest( "gmtRelease"));

        // ???????????????????????????vo????????????
        List<ArticleBasicVO> basicVOList = articlePage.getContent()
                .stream()
                .map(article -> {
                    ArticleBasicVO basicVO = new ArticleBasicVO(article);
                    /*
                    TODO: ???????????? ??????????????????; ?????????????????????- ????????????, ?????????????????????, ???????????????, ??????????????????????????????
                    service-datapack:
                        - ??????????????????
                        - ???????????????
                        - ??????????????????????????????
                    app-monitor:
                        - ????????????????????????
                        - ????????????
                        - ?????????????????????
                     */
                    // ??????????????????, ??????????????????????????????
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
            throw new DataNotExistException("?????????????????????");
        }
        Article article = articleOpt.get();

        return new ArticleVO(article);
    }

    @Override
    public ResultVO getSimilarArticlesById(Long articleId, PageParam pageParam) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException("?????????????????????");
        }
        Article article = articleOpt.get();

        // ????????????similarId=0?????????????????????id????????????????????????similarId????????????
        Page<Article> similarArticlePage;
        if (article.getSimilarArticleId() == 0) {
            similarArticlePage = articleDao.findAllBySimilarArticleId(
                    article.getId(),
                    pageParam.toPageRequest());
        } else if (article.getKeywords() != null) {
            String articleKeywords = article.getKeywords();
            String[] keywords = articleKeywords.split("\\|");
            /*????????????????????????,similarId???????????????????????????????????????????????????????????????????????????
            ????????????????????????
            * */
            //????????????????????????finalList???????????????
            List<Long> finalList = new ArrayList<>();
            if (keywords[0] != null) {
                String firstKeyword = keywords[0];
                List<Long> firstKeywordList = articleDao.findKeyword(firstKeyword);
                finalList.addAll(firstKeywordList);
                if (keywords[1] != null) {
                    String secondKeyword = keywords[1];
                    List<Long> secondKeywordList = articleDao.findKeyword(secondKeyword);
                    finalList.retainAll(secondKeywordList);
                    if (keywords[2] != null) {
                        String thirdKeyword = keywords[2];
                        List<Long> thirdKeywordList = articleDao.findKeyword(thirdKeyword);
                        finalList.retainAll(thirdKeywordList);
                    }
                }
            }
            if (finalList.size()>=2) {
                finalList.remove(articleId);
                similarArticlePage = articleDao.findAllById(finalList, pageParam.toPageRequest());
            }else{
                throw new DataNotExistException("???????????????");
            }
        } else {

            throw new DataNotExistException("???????????????");

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

                // keyword?????????????????????????????????
                Set<String> keywordSet;
                if (!StringUtils.isEmpty(article.getKeywords())) {
                    keywordSet = new LinkedHashSet<>(Arrays.asList(article.getKeywords().split(Constants.SPLITTER)));
                } else {
                    keywordSet = new LinkedHashSet<>();
                }
                keywordSet.add(keyword);

                // ?????????
                String keywordListStr = StringUtils.join(keywordSet, Constants.JOINER);
                article.setKeywords(keywordListStr);
                articleDao.save(article);

                // ?????????VO?????????
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

        // keyword??????????????????
        Set<String> keywordSet;
        if (!StringUtils.isEmpty(article.getKeywords())) {
            keywordSet = new HashSet<>(Arrays.asList(article.getKeywords().split(Constants.SPLITTER)));
        } else {
            keywordSet = new HashSet<>();
        }
        keywordSet.remove(keyword);

        // ?????????
        String keywordListStr = StringUtils.join(keywordSet, Constants.JOINER);
        article.setKeywords(keywordListStr);
        articleDao.save(article);

        // ?????????VO?????????
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

        // ArticleDiseaseList
        // ????????????????????????ArticleDisease??????
        List<ArticleDisease> existADList = articleDiseaseDao.findAllByArticleId(articleUpdateForm.getArticleId());

        if (!CollectionUtils.isEmpty(articleUpdateForm.getDiseaseFormList())) {
            // ??????????????????????????????list
            List<ArticleDisease> diseaseList = new ArrayList<>();
            // ?????????????????????diseaseFormList????????????????????????????????????????????????ArticleDiseaseList
            List<ArticleDiseaseForm> diseaseFormList = articleUpdateForm.getDiseaseFormList();
            List<ArticleDisease> usedADList = new ArrayList<>();
            for (int i = 0; i < diseaseFormList.size(); i++) {
                ArticleDisease newAD = new ArticleDisease();
                BeanUtils.copyProperties(diseaseFormList.get(i), newAD);
                newAD.setArticleId(articleUpdateForm.getArticleId());

                // ????????????index??????existADList??????????????????????????????
                if (i + 1 <= existADList.size()) {
                    // ??????, ???frontAD?????????????????????oldAD
                    Diseases diseases = diseaseDao.getOne(existADList.get(i).getDiseaseId());
                    Country country = countryDao.getOne(existADList.get(i).getCountryId());
                    newAD.setId(existADList.get(i).getId());
                    newAD.setDiseaseCode(diseases.getNameCn());
                    newAD.setCountryCode(country.getNameCn());
                    usedADList.add(existADList.get(i));
                }
                diseaseList.add(newAD);
            }
            // ??????????????????ADList
            existADList.removeAll(usedADList);
            articleDiseaseDao.deleteAll(existADList);

            article.setDiseaseList(diseaseList);
        } else {
            // ?????????????????????????????????????????????????????????
            articleDiseaseDao.deleteAll(existADList);
            article.setDiseaseList(null);
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

    @Override
    public List<String> getKeywordsByFoDiseaseName(String  fDisName) {

        String diseaseAlias = "%|" + fDisName + "|%";

        Diseases disease = this.diseaseDao.findByNameCnOrAlias(fDisName,diseaseAlias);

        if(disease == null) {
            if (log.isDebugEnabled()) {
                log.debug("{0} not found disease", fDisName);
            }
            return null;
        }

        Pageable page = PageRequest.of(0,16);
        Page<Long> articleList = this.articleDiseaseDao.findByDisease(disease.getId(),page);

        if(articleList.isEmpty()) {
            if(log.isDebugEnabled()) {

                log.debug("{} focusDisease not found articel!",disease.getId());
            }
            return null;
        }

        List<String> keywords = this.articleDao.findKeywordByIds(articleList.getContent());

        List<String> results = new ArrayList<String>();
        for(String keyword : keywords) {

            String[] ks = keyword.split("\\|");

            for(String k : ks) {
                if((k != null && !k.trim().equals("")) && !results.contains(k)) {
                    results.add(k);
                }
            }
        }

        return results;
    }
}
