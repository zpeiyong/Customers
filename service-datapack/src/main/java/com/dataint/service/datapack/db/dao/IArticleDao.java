package com.dataint.service.datapack.db.dao;

import com.dataint.service.datapack.db.IArticleEvent;
import com.dataint.service.datapack.db.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface IArticleDao extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

//    @Query(value = "SELECT * FROM article WHERE updated_time >= ?1 AND updated_time <= ?2 AND outbreak_level_id = ?3", nativeQuery = true)
//    List<Article> queryAllByUpdatedTime(String startDate, String endDate, Long levelId);

    Article findByArticleKey(String articleKey);

    Page<Article> findAllByIfDeleted(Boolean ifDeleted,  Pageable pageable);

    Page<Article> findAllByIfDeletedAndArticleType(Boolean ifDeleted, String articleType, Pageable pageable);

//    @Query(value = "SELECT DISTINCT a.* " +
//            "FROM article a " +
//            "LEFT JOIN article_disease ad ON ad.article_id = a.id " +
//            "WHERE (locate(?1, a.country_code) OR locate(?1, ad.country_codes)) AND ad.disease_code = ?2 AND a.if_deleted = ?3 " +
//            "ORDER BY gmt_release DESC",
//            countQuery = "SELECT COUNT(DISTINCT a.id) " +
//                    "FROM article a " +
//                    "LEFT JOIN article_disease ad ON ad.article_id = a.id " +
//                    "WHERE (locate(?1, a.country_code) OR locate(?1, ad.country_codes)) AND ad.disease_code = ?2 AND a.if_deleted = ?3", nativeQuery = true)
//    Page<Article> findMapBasicListByIfDeleted(String countryCode, String diseaseName, Boolean ifDeleted, Pageable pageable);

    /* Statistic */
    int countByGmtCrawlBetween(Date startTime, Date endTime);

    @Query(value = "SELECT ad.disease_code AS diseaseCode, COUNT(*) AS diseaseCnt " +
            "FROM article_disease ad " +
            "LEFT JOIN article a ON ad.article_id = a.id " +
            "WHERE a.gmt_crawl >= ?1 AND a.gmt_crawl <= ?2 " +
            "GROUP BY ad.disease_code " +
            "ORDER BY diseaseCnt DESC", nativeQuery = true)
    List<Object[]> queryDiseaseCnt(Date startTime, Date endTime);

//    @Query(value="SELECT DISTINCT a.countryCode " +
//            "FROM Article a " +
//            "WHERE a.gmtCrawl >= ?1 AND a.gmtCrawl <= ?2 AND a.countryCode IS NOT NULL")
//    Set<String> findCountryCodeFromA(Date startTime, Date endTime);

    @Query(value = "SELECT DISTINCT ad.country_codes " +
            "FROM article_disease ad " +
            "LEFT JOIN article a ON ad.article_id = a.id " +
            "WHERE a.gmt_crawl >= ?1 AND a.gmt_crawl <= ?2 AND ad.country_codes IS NOT NULL", nativeQuery = true)
    Set<String> findCountryCodesFromAD(Date startTime, Date endTime);

//    @Query(value = "SELECT DISTINCT a.id " +
//            "FROM article a " +
//            "LEFT JOIN article_disease ad ON ad.article_id = a.id " +
//            "WHERE a.gmt_crawl >= ?1 AND a.gmt_crawl <= ?2 AND " +
//            "   (locate(?3, a.country_code) OR (locate(?3, ad.country_codes) AND ad.article_id IS NOT NULL))", nativeQuery = true)
//    Set<Integer> queryArticleIdsByCountry(Date startTime, Date endTime, String countryCode);

    @Query(value = "SELECT DISTINCT DATE_FORMAT(a.updated_time, '%Y-%m-%d') FROM article a " +
            "WHERE a.outbreak_level_id != 1 AND a.updated_time IS NOT NULL AND a.if_deleted = false " +
            "   AND (locate(?1, a.title) OR locate(?1, a.summary))", nativeQuery = true)
    List<String> searchByKeyword(String keyword);

    List<Article> findAllByIdInOrderByGmtRelease(Iterable<Long> iterable);

    /**
     * 统计当前文章除自身以外的相似度文章数量
     * @param similarArticleId
     * @return
     */
    Integer countBySimilarArticleId(Long similarArticleId);

    @Query(nativeQuery =true,value = "SELECT a.id as id,a.gmt_release as releaseTime, s.name_cn as nameCn,a.title as title, a.summary as summary, a.content as content, " +
            "a.article_url as articleUrl, a.keywords as keywords " +
            "from article a LEFT JOIN  article_disease  ad ON a.id=ad.article_id " +
            "left join site s on a.site_id=s.id " +
            "where ad.disease_id=?1  and a.similar_article_id=0  and a.gmt_release LIKE ?2 " +
            "GROUP BY ad.article_id ORDER BY  a.gmt_release",
            countQuery = "SELECT count(*) " +
                    "from article a LEFT JOIN  article_disease  ad ON a.id=ad.article_id " +
                    "left join site s on a.site_id=s.id " +
                    "where ad.disease_id=?1  and a.similar_article_id=0  and a.gmt_release LIKE ?2 " +
                    "GROUP BY ad.article_id ORDER BY  a.gmt_release ")
    Page<IArticleEvent> findGmtTime(long diseaseId, String releaseTime, Pageable pageable);

}
