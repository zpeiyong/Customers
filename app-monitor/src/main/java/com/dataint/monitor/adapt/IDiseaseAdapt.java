package com.dataint.monitor.adapt;

import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.monitor.model.param.DiseaseQueryParam;

public interface IDiseaseAdapt {


    Object queryFocusDiseases(DiseaseQueryParam diseaseQueryParam);

    Object getCasesByCountryId(Long countryId, String dateStr, PageParam pageParam);

    Object getCasesByCidAndDid(Long countryId, Long diseaseId, String dateStr);
}
