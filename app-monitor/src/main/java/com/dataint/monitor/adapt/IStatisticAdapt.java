package com.dataint.monitor.adapt;

public interface IStatisticAdapt {
    Object getStatisticBasic(Long diseaseId);

    Object getStatisticBasicBI(Long diseaseId);

    Object getCountryAddTimeLine(Long diseaseId, String dateStr);

    Object getCountryRiskRank(Long diseaseId, String dateStr);

    Object getEventAddTimeLine(Long diseaseId, String dateStr);

    Object getEventTotalCntRank(Long diseaseId, String dateStr);

    Object getArticleAddTimeLine(Long diseaseId, String dateStr);

    Object getArticleTotalCntRank(Long diseaseId, String dateStr);

    Object getMapCountryList();

    Object getGlobalRiskTimeLine(String dateStr);
}
