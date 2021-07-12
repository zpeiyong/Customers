package com.dataint.service.datapack.service;

import com.dataint.service.datapack.db.entity.DiseaseCountryCase;
import com.dataint.service.datapack.model.form.DiseaseCountryForm;
import com.dataint.service.datapack.model.param.DiseaseCountryParam;
import com.dataint.service.datapack.model.vo.CountryVO;
import com.dataint.service.datapack.model.vo.DiseaseCountryCaseVO;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface IDiseaseCountryCaseService {
    /**
     *
     * @param diseaseCountryParam
     * @return
     */
    Page<DiseaseCountryCaseVO> listDiseaseCountry(DiseaseCountryParam diseaseCountryParam);

    /**
     *
     * @param countryCase
     * @return
     */
    DiseaseCountryCase addDiseaseCountryCase(DiseaseCountryForm countryCase);

    /**
     *
     * @param diseaseId
     * @param showType
     * @param periodStart
     * @return
     * @throws ParseException
     */
    List<CountryVO> getCountriesByParam(Long diseaseId, String showType, String periodStart);

    /**
     *
     * @return
     */
    DiseaseCountryCaseVO getLatestCasesByParam(Long diseaseId, Long countryId);


    List<Map<String,Object>> getCountryDataTj();

    List<Map<String,Object>> getDiseaseDataTj();

    /**
     *
     * @param diseaseId
     * @param countryId
     * @param week
     * @return
     */
    public List<DiseaseCountryCaseVO> getForCountryRisk1(int diseaseId,int countryId,int week);

    /**
     *
     * @param diseaseId
     * @param countryId
     * @param day
     * @return
     */
    public List<DiseaseCountryCaseVO> getForCountryPreDay(int diseaseId,int countryId,int day);


    /**
     *
     * @param diseaseId
     * @param countryId
     * @return
     */
    public Object getDiseaseAnalysis(int diseaseId,int countryId);
}
