package com.dataint.service.datapack.service.impl;

import com.dataint.cloud.common.exception.DataAlreadyExistException;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.MD5Util;
import com.dataint.service.datapack.dao.*;
import com.dataint.service.datapack.dao.entity.*;
import com.dataint.service.datapack.model.ArticleBasicVO;
import com.dataint.service.datapack.model.ArticleEventVO;
import com.dataint.service.datapack.model.ArticleReportVO;
import com.dataint.service.datapack.model.ArticleVO;
import com.dataint.service.datapack.model.form.*;
import com.dataint.service.datapack.model.params.ArticleListQueryParam;
import com.dataint.service.datapack.service.IArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends AbstractBuild implements IArticleService {

    @Autowired
    private IArticleDao articleDao;

    @Autowired
    private ISiteDao siteDao;

    @Autowired
    private ICountryDao countryDao;

    @Autowired
    private IArticleDiseaseDao articleDiseaseDao;

    @Autowired
    private IDiseasesDao diseaseDao;

    @Autowired
    private IOutbreakLevelDao outbreakLevelDao;

    @Autowired
    private IEventDao eventDao;

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

        // 组装 Article 对象
        ArticleForm articleForm = storeDataForm.getArticleForm();
        Article article = buildArticle(articleForm, site);

        Article ifArticleExist = articleDao.findByArticleKey(articleForm.getArticleKey());
        if (ifArticleExist != null) {
            if (md5.equals(ifArticleExist.getInputMd5()))
                throw new DataAlreadyExistException("该数据已存在");
            else
                // TODO: 当同一条articleKey不同数据请求存储, 是update还是忽略??
                // article.setId(ifArticleExist.getId());
                throw new DataAlreadyExistException("该数据已存在");
        }
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

        // 查询默认舆情等级(若不存在则添加)
        OutbreakLevel outbreakLevel = outbreakLevelDao.findByLevelCode("00");  // 00(default): 未评价
        if (outbreakLevel == null) {
            outbreakLevelDao.save(new OutbreakLevel("未评价", "00"));
        }
        article.setOutbreakLevel(outbreakLevel);

        // 舆情信息保存
        articleDao.save(article);
    }

    @Override
    public Article buildArticle(ArticleForm articleForm, Site site) {
        Article article = articleForm.toPo(Article.class);
        // siteId
        article.setSite(site);
        // countryCode
        article.setCountryCode(convertCountryName(articleForm.getCountryNameList(), site.getLanguage()));
        // keywords
        if (!CollectionUtils.isEmpty(articleForm.getKeywordList()))
            article.setKeywords(StringUtils.join(articleForm.getKeywordList(), Constants.JOINER));
        // eventType => diseaseList
        if (!CollectionUtils.isEmpty(articleForm.getEventTypeList())) {
            List<ArticleDisease> diseaseList = new ArrayList<>();
            for (String eventType : articleForm.getEventTypeList()) {
                ArticleDisease articleDisease = new ArticleDisease();
                // check if disease exist
                Diseases disease = diseaseDao.findByNameCn(eventType);
                if (disease != null) {
                    articleDisease.setDiseaseId(disease.getId());
                    articleDisease.setDiseaseCode(disease.getNameCn());
                } else {
                    articleDisease.setDiseaseCode(eventType);
                }
                diseaseList.add(articleDisease);
            }
            article.setDiseaseList(diseaseList);
        }

        /*
        若需要, 字段长度截取
         */
        if (article.getSummary() != null && article.getSummary().length() > 1000)
            article.setSummary(getSubString(article.getSummary(), 1000));

        return article;
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
    public List<ArticleBasicVO> queryBasicList(PageParam pageParam) {
        Page<Article> pageResult = articleDao.findAllByDeleted("N", pageParam.toPageRequest("gmtRelease"));

        return pageResult.getContent().stream().map(ArticleBasicVO::new).collect(Collectors.toList());
    }

    @Override
    public ArticleBasicVO queryBasicById(Integer articleId) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (articleOpt.isPresent())
            return new ArticleBasicVO(articleOpt.get());

        return new ArticleBasicVO();
    }

    @Override
    public List<ArticleBasicVO> queryMapBasicList(Integer countryId, String diseaseName, PageParam pageParam) {
        Optional<Country> countryOpt = countryDao.findById(countryId);
        if (!countryOpt.isPresent())
            throw new DataNotExistException();
        Country country = countryOpt.get();
        Page<Article> pageResult = articleDao.findMapBasicListByDeleted(country.getCode(), diseaseName, "N",
                pageParam.toPageRequest());

        return pageResult.getContent().stream().map(ArticleBasicVO::new).collect(Collectors.toList());
    }


    @Override
    public Page<Object> getArticleList(ArticleListQueryParam articleListQueryParam) {
        Page<Article> pageResult = articleDao.findAllByDeletedAndArticleType("N", articleListQueryParam.getArticleType(), articleListQueryParam.toPageRequest("gmtRelease"));
        if (articleListQueryParam.getArticleType().equals("article")) {
            List<ArticleBasicVO> basicVOList = pageResult.getContent().stream().map(ArticleBasicVO::new).collect(Collectors.toList());
            return new PageImpl(basicVOList, pageResult.getPageable(), pageResult.getTotalElements());
        }else {
            List<ArticleEventVO> eventVOList = pageResult.getContent().stream().map(ArticleEventVO::new).collect(Collectors.toList());
            List<ArticleEventVO> basicVOList = new ArrayList<>();
            for (ArticleEventVO eventVO: eventVOList
                 ) {
                List<Event> eventList = eventDao.findAllByArticleId(eventVO.getId());
                eventVO.setEventList(eventList);
                basicVOList.add(eventVO);
            }
            return new PageImpl(basicVOList, pageResult.getPageable(), pageResult.getTotalElements());
        }
    }

    @Override
    public ArticleVO getArticleById(Integer articleId) {
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (articleOpt.isPresent())
            return new ArticleVO(articleOpt.get());

        return new ArticleVO();
    }

    @Transactional
    @Override
    public void delArticleById(Integer articleId) {
        // check if article exist
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Article article = articleOpt.get();
        article.setDeleted("Y");
        articleDao.save(article);
    }

    @Transactional
    @Override
    public List<ArticleBasicVO> addKeyword(List<Integer> idList, String keyword) {
        List<ArticleBasicVO> basicVOList = new ArrayList<>();

        for (Integer id : idList) {
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
    public ArticleBasicVO delKeyword(Integer id, String keyword) {
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
    public ArticleBasicVO updateLevel(Integer articleId, Integer levelId) {
        // check if article exist
        Optional<Article> articleOpt = articleDao.findById(articleId);
        if (!articleOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Article article = articleOpt.get();
        // check if outbreakLevel exist
        Optional<OutbreakLevel> levelOpt = outbreakLevelDao.findById(levelId);
        if (!levelOpt.isPresent()) {
            throw new DataNotExistException();
        }
        OutbreakLevel outbreakLevel = levelOpt.get();
        article.setOutbreakLevel(outbreakLevel);
        articleDao.save(article);

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
                // diseases
                Diseases diseases = diseaseDao.getOne(diseaseForm.getDiseaseId());
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
                // countries
                List<Integer> countryIdList = diseaseForm.getCountryIdList();
                if (countryIdList != null && countryIdList.size()>0) {
                    List<Country> countryList = countryDao.findAllById(countryIdList);
                    articleDisease.setCountryIds(StringUtils.join(
                            countryIdList, Constants.JOINER));
                    articleDisease.setCountryCodes(StringUtils.join(
                            countryList.stream().map(Country::getCode).collect(Collectors.toList()), Constants.JOINER));
                }

                // 新增病例
                if (diseaseForm.getNewCases() != null){
                    articleDisease.setNewCases(diseaseForm.getNewCases());
                }
                // 累计病例
                if (diseaseForm.getCumulativeCases() != null){
                    articleDisease.setCumulativeCases(diseaseForm.getCumulativeCases());
                }
                // 确诊病例
                if (diseaseForm.getConfirmedCases()!= null){
                    articleDisease.setConfirmedCases(diseaseForm.getConfirmedCases());
                }
                // 疑似病例
                if (diseaseForm.getSuspectedCases() != null){
                    articleDisease.setSuspectedCases(diseaseForm.getSuspectedCases());
                }
                // 死亡病例
                if (diseaseForm.getDeathToll() != null){
                    articleDisease.setDeathToll(diseaseForm.getDeathToll());
                }
                diseaseList.add(articleDisease);
            }
            article.setDiseaseList(diseaseList);
        }
        // outbreakLevel
        if (articleUpdateForm.getLevelId() != null) {
            OutbreakLevel outbreakLevel = outbreakLevelDao.getOne(articleUpdateForm.getLevelId());
            article.setOutbreakLevel(outbreakLevel);
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
    public Map<String, Object> queryReportContent(String startTime, String endTime, String type) {
        // 获取文章所有等级
        List<OutbreakLevel> levelList = outbreakLevelDao.findAll();

        Map<String, Object> articleMap = new HashMap<>();
        for (OutbreakLevel level : levelList) {
            // 00: 未评价
            if (!"00".equals(level.getLevelCode())) {
                String levelCode = level.getLevelCode();
                // 生成周报时不包含一般关注
                if ("02".equals(levelCode) && "weekly".equals(type)) {
                    continue;
                }
                List<Article> articleList = articleDao.queryAllByUpdatedTime(startTime, endTime, level.getId());

                /*
                构造返回值map
                 */
                // 重要, 一般
                if (!CollectionUtils.isEmpty(articleList)) {
                    articleMap.put(levelCode, articleList.stream().map(ArticleReportVO::new).collect(Collectors.toList()));
                }
            }
        }

        return articleMap;
    }

}
