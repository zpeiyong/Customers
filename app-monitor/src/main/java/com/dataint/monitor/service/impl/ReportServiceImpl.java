package com.dataint.monitor.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.Pagination;
import com.dataint.cloud.common.model.ResultVO;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.dao.IArticleReportDao;
import com.dataint.monitor.dao.IReportArticleDao;
import com.dataint.monitor.dao.IReportDao;
import com.dataint.monitor.dao.IReportLevelDao;
import com.dataint.monitor.dao.entity.ArticleReport;
import com.dataint.monitor.dao.entity.Report;
import com.dataint.monitor.dao.entity.ReportArticle;
import com.dataint.monitor.dao.entity.ReportLevel;
import com.dataint.monitor.model.ArticleReportVO;
import com.dataint.monitor.model.ReportBaseModel;
import com.dataint.monitor.model.ReportVO;
import com.dataint.monitor.model.param.ReportQueryParam;
import com.dataint.monitor.service.IReportService;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class ReportServiceImpl implements IReportService {

    @Value("${dataint.report.templatePath}")
    private String templatePath;
    @Value("${dataint.report.tmpPath}")
    private String tmpPath;
    @Value("${dataint.report.generatePath}")
    private String generatePath;
    private static Map<String, String> escapeCharMap = new HashMap<>();
    private static Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    private static Map<String, String> reportTypeMap = new HashMap<>();


    @PostConstruct
    public void initStatic() {
        // escape characters
//      escapeCharMap.put("\"", "&quot;");
        escapeCharMap.put("&", "&amp;");
        escapeCharMap.put("<", "&lt;");
        escapeCharMap.put(">", "&gt;");
//      escapeCharMap.put("??", "&copy;");
//      escapeCharMap.put("??", "&reg;");
//      escapeCharMap.put("???", "&trade;");
//      escapeCharMap.put("??", "&times;");
//      escapeCharMap.put("??", "&divide;");

        try {
            System.out.println(templatePath);
            TemplateLoader templateLoader = new FileTemplateLoader(new File(templatePath));
            configuration.setTemplateLoader(templateLoader);
        } catch (IOException ioe) {
            System.out.println("exception");
            ioe.printStackTrace();
        }

        // reportTypeMap
        reportTypeMap.put("daily", "??????");
        reportTypeMap.put("weekly", "??????");
        reportTypeMap.put("monthly", "??????");
        reportTypeMap.put("event", "????????????");
    }

    @Autowired
    private IReportDao reportDao;
    @Autowired
    private IReportArticleDao reportArticleDao;
    @Autowired
    private IReportLevelDao reportLevelDao;
    @Autowired
    private IArticleAdapt articleAdapt;
    @Autowired
    private IArticleReportDao articleReportDao;

    @Override
    public ResultVO queryReportList(ReportQueryParam reportQueryParam) {
        Page<Report> pageResult = null;
        //
        PageParam pageParam = new PageParam();
        pageParam.setCurrent(reportQueryParam.getCurrent());
        pageParam.setPageSize(reportQueryParam.getPageSize());

        // ?????????????????????????????????keyword??????
        if (!StringUtils.isEmpty(reportQueryParam.getReportDate())) {
            String reportDate = reportQueryParam.getReportDate() + " 00:00:00";
            Date dayStart, dayEnd;
            try {
                // ???????????????????????????7???, ???????????????????????????30???
                String nDaysTimeStart = "";
                if ("daily".equals(reportQueryParam.getReportType())) {
                    nDaysTimeStart = DateUtil.getNDaysThanTimeStart(reportDate, -7);
                } else if ("weekly".equals(reportQueryParam.getReportType())) {
                    nDaysTimeStart = DateUtil.getNDaysThanTimeStart(reportDate, -30);
                } else {
                    // ???????????????monthly???yearly, ?????????event??????
                    nDaysTimeStart = DateUtil.getNDaysThanTimeStart(reportDate, -30);
                }

                dayStart = Constants.DateTimeSDF.parse(nDaysTimeStart);
                dayEnd = Constants.DateTimeSDF.parse(DateUtil.getDayEnd(reportDate));
            } catch (ParseException pe) {
                pe.printStackTrace();
                throw new DataintBaseException(BaseExceptionEnum.DATE_PARSE_ERROR);
            }

            pageResult = reportDao.findByGmtStartAndGmtEndAndReportType(dayStart, dayEnd,
                    reportQueryParam.getReportType(), pageParam.toPageRequest("gmtStart"));
        } else {
            // keyword??????????????????, ???????????????IReportDao
            if (StringUtils.isEmpty(reportQueryParam.getKeyword())) {
                pageResult = reportDao.findAllByReportType(reportQueryParam.getReportType(),
                        pageParam.toPageRequest("gmtStart"));
            }
            // keyword???????????????, ???????????????????????????????????????????????????
            else {
                String keyword = "%" + reportQueryParam.getKeyword() + "%";
                List<ArticleReport> arList = articleReportDao.findAllByTitleLikeOrSummaryLikeOrderByReportTitleDesc(keyword, keyword);

                List<String> titleList = arList.stream().map(ArticleReport::getReportTitle).collect(Collectors.toList());

                pageResult = reportDao.findAllByReportTypeAndTitleIn(
                        reportQueryParam.getReportType(),
                        titleList,
                        pageParam.toPageRequest("gmtStart"));
            }
        }

        /* ??????VO?????? */
        List<ReportVO> reportVOList = pageResult.getContent().stream().map(ReportVO::new).collect(Collectors.toList());
        Pagination pagination = new Pagination(pageParam.getPageSize(), pageResult.getTotalElements(), pageParam.getCurrent());

        return ResultVO.success(reportVOList, pagination);
    }

    @Override
    public void generateDailyReport(String startTime, String endTime) {
        Map<String, Object> paramMap = generateDailyContent(startTime, endTime);

        // ???????????????????????????
        if (CollectionUtils.isEmpty(paramMap))
            return;
        //
        ReportBaseModel reportBaseModel = (ReportBaseModel) paramMap.get("reportBaseModel");
        if (CollectionUtils.isEmpty(reportBaseModel.getListMap())) {
            log.info(reportBaseModel.getReportDate() + " ??????????????????????????????!");
            return;
        }

        // ????????????
        generateReport(paramMap);

        // ????????????????????????????????????
        String reportTitle = ((Report) paramMap.get("report")).getTitle();
        List<ReportLevel> reportLevelList = reportLevelDao.findAllByOrderBySort();
        for (ReportLevel level : reportLevelList) {
            List<ArticleReportVO> articleReportVOList = reportBaseModel.getListMap().get(level.getLevelName());
            List<ArticleReport> arList = articleReportVOList.stream().map(vo -> {
                ArticleReport articleReport = new ArticleReport();
                articleReport.setArticleId(vo.getId());
                articleReport.setReportLevelId(level.getId());
                articleReport.setTitle(vo.getTitle());
                articleReport.setSummary(vo.getSummary());
                articleReport.setArticleUrl(vo.getArticleUrl());
                articleReport.setReportTitle(reportTitle);

                return articleReport;
            }).collect(Collectors.toList());

            articleReportDao.saveAll(arList);
        }
    }

    @Override
    public void generateWeeklyReport(String weekStartTime, String weekEndTime) {

    }

    @Override
    public void genPeriodReport(String beginTime, String overTime) {
        // dailyStartTime, dailyEndTime
        List<Map<String, String>> list = DateUtil.getDailyBetweenIntervals(beginTime, overTime);

        for (Map<String, String> map : list) {
            String startTime = map.get("startTime");
            String endTime = map.get("endTime");

            // generate report
            generateDailyReport(startTime, endTime);
        }
    }

    @Override
    public Resource loadFileAsResource(Long reportId) {
        // check if report exist
        Optional<Report> reportOpt = reportDao.findById(reportId);
        if (!reportOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Report report = reportOpt.get();

        // ????????????????????????path
        String[] strs = report.getDocPath().split("/");
        String fileStr = generatePath + strs[strs.length-1];
        try {
            Path filePath = Paths.get(fileStr);
            org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        return null;
    }


    /**
     * ??????report
     *
     * @param paramMap
     */
    @Transactional(rollbackFor = Exception.class)
    public void generateReport(Map<String, Object> paramMap) {
        Report report = (Report) paramMap.get("report");

        //
        String docxTemplate = templatePath + "report_frame.docx";
        String docxXmtTemplate = "report_frame.ftl";  //
        String tempDocxXmlPath = tmpPath + "report_tmp.xml";  // ??????tmp???xml??????
        /* ???????????????????????????(?????????)???????????????????????????????????????????????????????????? */
//        String tmpDocxPath = tmpPath + "tmp_out.docx";  // ??????????????????docx??????
        String docOutPath = report.getDocPath();  // ??????????????????

        // ?????????????????????tmp??????
        try {
            xmlToDocx(docxTemplate, docxXmtTemplate, tempDocxXmlPath, paramMap, docOutPath);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // ??????????????????????????????
        reportDao.save(report);

//        if (report != null) {
//            //  (????????????)-????????????????????????docx??????
//            addDocxToc(tmpDocxPath, report.getDocPath());
//
//            reportRepository.save(report);
//        }
    }

    /**
     * ???????????????????????????
     *
     * @return
     */
    private Map<String, Object> generateDailyContent(String dailyStartTime, String dailyEndTime) {
        String type = "daily";
        Date todayStart, todayEnd = null;
        try {
            todayStart = Constants.DateTimeSDF.parse(dailyStartTime);
            todayEnd = Constants.DateTimeSDF.parse(dailyEndTime);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return new HashMap<>();
        }

        // check if exist
        List<Report> existReportList = reportDao.findByGmtStartAndGmtEndAndReportType(todayStart, todayEnd, type);
        if (!CollectionUtils.isEmpty(existReportList)) {
            log.info(existReportList.get(0).getTitle() + " ?????????!");
            return new HashMap<>();
        }

        // ??????????????????????????????
        Integer maxSerialNum = 1;
        Integer maxTotalNum = 1;
        int currentNo;
        int totalNo;
        if (null == maxSerialNum) {
            currentNo = 1;
            totalNo = 1;
        } else {
            currentNo = maxSerialNum + 1;
            totalNo = maxTotalNum + 1;
        }

        // ReportBaseModel
        ReportBaseModel reportBaseModel = new ReportBaseModel();
        reportBaseModel.setReportType("???");
        reportBaseModel.setSerialNo(currentNo);
        reportBaseModel.setTotalNo(totalNo);
        String[] dayNumbers = (dailyStartTime.split(" "))[0].split("-");
        reportBaseModel.setReportDate(dayNumbers[0] + "???" + dayNumbers[1] + "???" + dayNumbers[2] + "???");

        // get data from DataPack
        getData(reportBaseModel, dailyStartTime, dailyEndTime, type);

        // Report
        String title = dailyStartTime.split(" ")[0].replace("-", ".") + "-??????";
        Report reportDb = new Report();
        reportDb.setCreatedTime(new Date());
        reportDb.setGmtStart(todayStart);
        reportDb.setGmtEnd(todayEnd);
        reportDb.setSerialNumber(currentNo);
        reportDb.setTotalNumber(totalNo);
        reportDb.setReportType(type);
        reportDb.setTitle(title);
        reportDb.setDocPath(generatePath + title + ".docx");

        // build result model
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("reportBaseModel", reportBaseModel);
        paramMap.put("report", reportDb);

        return paramMap;
    }

    /**
     * ???????????????????????????
     *
     * @param reportBaseModel
     * @param startTime
     * @param endTime
     */
    private void getData(ReportBaseModel reportBaseModel, String startTime, String endTime, String type) {
        Date startDateTime, endDateTime;

        try {
            startDateTime = Constants.getDateTimeFormat().parse(startTime);
            endDateTime = Constants.getDateTimeFormat().parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        //
        LinkedHashMap<String, List<ArticleReportVO>> listMap = new LinkedHashMap<>();
        List<ReportLevel> reportLevelList = reportLevelDao.findAllByOrderBySort();
        for (ReportLevel reportLevel : reportLevelList) {
            // ????????????????????????????????????????????????????????????id()
            List<ReportArticle> reportArticleList = reportArticleDao.findAllByUpdatedTimeBetweenAndReportTypeAndReportLevelId(
                    startDateTime, endDateTime, type, reportLevel.getId());
            List<Long> articleIdList = reportArticleList.stream().map(ReportArticle::getArticleId).collect(Collectors.toList());

            // ??????service-datapack????????????ID????????????????????????
            JSONObject rstVO = articleAdapt.queryArticlesByIdList(articleIdList);
            if (rstVO != null && rstVO.containsKey("data")) {
                List<ArticleReportVO> concernList = JSONArray.parseArray(rstVO.getJSONArray("data").toJSONString(), ArticleReportVO.class);
                for (ArticleReportVO reportArticle : concernList) {
                    transArticleReport(reportArticle);
                }

                listMap.put(reportLevel.getLevelName(), concernList);
            }
        }
        reportBaseModel.setListMap(listMap);


//        ResultVO<JSONObject> rstVO = articleProvider.queryDailyReport(startTime, endTime, type);
//        JSONObject reportJO = rstVO.getData();
//
//        if (!ObjectUtils.isEmpty(reportJO)) {
//            if (reportJO.containsKey("01")) {  // concernList - ??????
//                List<ArticleReportVO> concernList = JSONArray.parseArray(reportJO.getJSONArray("01").toJSONString(), ArticleReportVO.class);
//                // transform escape characters
//                for (ArticleReportVO reportArticle : concernList) {
//                    transArticleReport(reportArticle);
//                }
//                reportBaseModel.setConcernList(concernList);
//            }
//            if (reportJO.containsKey("02")) {  // moreInfoList - ??????
//                List<ArticleReportVO> concernList = JSONArray.parseArray(reportJO.getJSONArray("02").toJSONString(), ArticleReportVO.class);
//                // transform escape characters
//                for (ArticleReportVO reportArticle : concernList) {
//                    transArticleReport(reportArticle);
//                }
//                reportBaseModel.setMoreInfoList(concernList);
//            }
//        }
    }


    /**
     * ArticleReportVO ??????????????????
     * @param articleReportVO
     */
    private void transArticleReport(ArticleReportVO articleReportVO) {
        if (articleReportVO.getArticleUrl() != null) {
            String articleUlr = articleReportVO.getArticleUrl();
            articleReportVO.setArticleUrl(transEscapeChars(articleUlr));
        }
        if (articleReportVO.getSummary() != null) {
            String summary = articleReportVO.getSummary();
            articleReportVO.setSummary(transEscapeChars(summary));
        }
        if (articleReportVO.getTitle() != null) {
            String title = articleReportVO.getTitle();
            articleReportVO.setTitle(transEscapeChars(title));
        }
    }

    /**
     * ??????????????????
     * @param str
     * @return
     */
    private String transEscapeChars(String str) {
        String rstStr = str;
        for (Map.Entry<String, String> entry : escapeCharMap.entrySet()) {
            rstStr = rstStr.replace(entry.getKey(), entry.getValue());
        }

        return rstStr;
    }

    /**
     * ??????????????????docx??????
     *
     * @param docxTemplate    docx?????????docx????????????
     * @param docxXmlTemplate docx?????????xml????????????
     * @param tempDocxXmlPath docx?????????xml??????(docx?????????xml??????????????????????????????????????????)
     * @param params          ?????????docx?????????xml??????????????????
     * @param tmpDocxPath     ???????????????docx????????????
     */
    private static void xmlToDocx(String docxTemplate, String docxXmlTemplate, String tempDocxXmlPath, Map params, String tmpDocxPath) throws Exception {
        Template template = configuration.getTemplate(docxXmlTemplate);

        Writer fileWriter = new FileWriter(new File(tempDocxXmlPath));
        template.process(params, fileWriter);
        fileWriter.close();

        File docxFile = new File(docxTemplate);
        ZipFile zipFile = new ZipFile(docxFile);
        Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(tmpDocxPath));
        int len = -1;
        byte[] buffer = new byte[1024];
        while (zipEntrys.hasMoreElements()) {
            ZipEntry next = zipEntrys.nextElement();
            InputStream is = zipFile.getInputStream(next);
            // ???????????????????????????????????????, ?????????word/document.xml?????????
            zipout.putNextEntry(new ZipEntry(next.toString()));
            if ("word/document.xml".equals(next.toString())) {
                //InputStream in = new FileInputStream(new File(XmlToDocx.class.getClassLoader().getResource("").toURI().getPath()+"template/test.xml"));
                InputStream in = new FileInputStream(tempDocxXmlPath);
                while ((len = in.read(buffer)) != -1) {
                    zipout.write(buffer, 0, len);
                }
                in.close();
            } else {
                while ((len = is.read(buffer)) != -1) {
                    zipout.write(buffer, 0, len);
                }
                is.close();
            }
        }
        zipout.close();
    }
}
