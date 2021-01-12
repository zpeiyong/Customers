package com.dataint.monitor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.exception.DataNotExistException;
import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.model.param.PageParam;
import com.dataint.cloud.common.utils.DateUtil;
import com.dataint.monitor.adapt.IArticleAdapt;
import com.dataint.monitor.dao.IReportArticleDao;
import com.dataint.monitor.dao.IReportDao;
import com.dataint.monitor.dao.IReportLevelDao;
import com.dataint.monitor.dao.entity.Report;
import com.dataint.monitor.dao.entity.ReportArticle;
import com.dataint.monitor.dao.entity.ReportLevel;
import com.dataint.monitor.model.ArticleReport;
import com.dataint.monitor.model.ReportBaseModel;
import com.dataint.monitor.model.ReportVO;
import com.dataint.monitor.model.param.ReportQueryParam;
import com.dataint.monitor.service.IReportService;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
//      escapeCharMap.put("©", "&copy;");
//      escapeCharMap.put("®", "&reg;");
//      escapeCharMap.put("™", "&trade;");
//      escapeCharMap.put("×", "&times;");
//      escapeCharMap.put("÷", "&divide;");

        try {
            System.out.println(templatePath);
            TemplateLoader templateLoader = new FileTemplateLoader(new File(templatePath));
            configuration.setTemplateLoader(templateLoader);
        } catch (IOException ioe) {
            System.out.println("exception");
            ioe.printStackTrace();
        }

        // reportTypeMap
        reportTypeMap.put("daily", "日报");
        reportTypeMap.put("weekly", "周报");
        reportTypeMap.put("monthly", "月报");
        reportTypeMap.put("event", "事件报告");
    }

    @Autowired
    private IReportDao reportDao;
    @Autowired
    private IReportArticleDao reportArticleDao;
    @Autowired
    private IReportLevelDao reportLevelDao;
    @Autowired
    private IArticleAdapt articleAdapt;

    @Override
    public Page<ReportVO> queryReportList(ReportQueryParam reportQueryParam) {
        Page<Report> pageResult = null;
        //
        PageParam pageParam = new PageParam();
        pageParam.setCurrent(reportQueryParam.getCurrent());
        pageParam.setPageSize(reportQueryParam.getPageSize());

        // 日期条件存在时，不考虑keyword条件
        if (!StringUtils.isEmpty(reportQueryParam.getReportDate())) {
            Date dayStart, dayEnd;
            try {
                dayStart = Constants.DateTimeSDF.parse(reportQueryParam.getReportDate() + " 00:00:00");
                dayEnd = Constants.DateTimeSDF.parse(reportQueryParam.getReportDate() + " 23:59:59");
            } catch (ParseException pe) {
                pe.printStackTrace();
                return null;
            }
            pageResult = reportDao.findByGmtStartAndGmtEndAndReportType(dayStart, dayEnd,
                    reportQueryParam.getReportType(), pageParam.toPageRequest("gmtStart"));
        } else {
            // keyword条件不存在时, 可直接查询IReportDao
            if (StringUtils.isEmpty(reportQueryParam.getKeyword())) {
                pageResult = reportDao.findAllByReportType(reportQueryParam.getReportType(),
                        pageParam.toPageRequest("gmtStart"));
            }
            // keyword条件存在时, 通过数据库匹配并推算出对应简报标题
            else {
                // 返回结果为发布日期列表(List<String> gmtReleaseList)
//                ResultVO<List<String>> retVO = articleProvider.searchByKeyword(reportQueryParam.getKeyword());
//                if (!ObjectUtils.isEmpty(retVO)) {
//                    List<String> titleList = retVO.getData().stream().map(dateStr -> {
//                        String date = dateStr.replace("-", ".");
//                        // 日报标题为: YYYY.MM.DD-日报
//                        if ("daily".equals(reportQueryParam.getReportType())) {
//                            return date + "-" + reportTypeMap.get(reportQueryParam.getReportType());
//                        }
//                        // TODO: 周报标题为: YYYY第X周-周报
//                        else if ("weekly".equals(reportQueryParam.getReportType())) {
//                            return "weekly";
//                        }
//                        // TODO: 月报标题为: YYYY.MM-月报
//                        else if ("monthly".equals(reportQueryParam.getReportType())) {
//                            return "monthly";
//                        }
//                        // TODO:
//                        else {
//                            return "yearly";
//                        }
//                    }).collect(Collectors.toList());
//
//                    pageResult = reportDao.findAllByReportTypeAndTitleIn(reportQueryParam.getReportType(), titleList,
//                            pageParam.toPageRequest("gmtStart"));
//                }
            }
        }

        /* 封装VO返回 */
        if (!ObjectUtils.isEmpty(pageResult)) {
            List<ReportVO> reportVOList = pageResult.getContent().stream().map(ReportVO::new).collect(Collectors.toList());

            return new PageImpl(reportVOList, pageResult.getPageable(), pageResult.getTotalElements());
        }

        return null;
    }

    @Override
    public void generateDailyReport(String startTime, String endTime) {
        Map<String, Object> paramMap = generateDailyContent(startTime, endTime);

        // 检查获取的日报数据
        if (CollectionUtils.isEmpty(paramMap))
            return;
        //
        ReportBaseModel reportBaseModel = (ReportBaseModel) paramMap.get("reportBaseModel");
        if (reportBaseModel.getConcernList() == null && reportBaseModel.getMoreInfoList() == null) {
            log.info(reportBaseModel.getReportDate() + " 未对舆情数据进行处置!");
            return;
        }

        // 生成日报
        generateReport(paramMap);
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
    public Resource loadFileAsResource(Integer reportId) {
        // check if report exist
        Optional<Report> reportOpt = reportDao.findById(reportId);
        if (!reportOpt.isPresent()) {
            throw new DataNotExistException();
        }
        Report report = reportOpt.get();

        // 拼接文件实际所在path
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
     * 生成report
     *
     * @param paramMap
     */
    @Transactional(rollbackFor = Exception.class)
    public void generateReport(Map<String, Object> paramMap) {
        Report report = (Report) paramMap.get("report");

        //
        String docxTemplate = templatePath + "report_frame.docx";
        String docxXmtTemplate = "report_frame.ftl";  //
        String tempDocxXmlPath = tmpPath + "report_tmp.xml";  // 需要tmp的xml文件
        /* 生成目录方法有缺陷(非开源)，因此暂时不生成目录，直接输出到最终目录 */
//        String tmpDocxPath = tmpPath + "tmp_out.docx";  // 生成目录前的docx文档
        String docOutPath = report.getDocPath();  // 最终生成目录

        // 生成不含目录的tmp文档
        try {
            xmlToDocx(docxTemplate, docxXmtTemplate, tempDocxXmlPath, paramMap, docOutPath);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // 保存简报信息到数据库
        reportDao.save(report);

//        if (report != null) {
//            //  (需要重写)-生成含目录的最终docx文档
//            addDocxToc(tmpDocxPath, report.getDocPath());
//
//            reportRepository.save(report);
//        }
    }

    /**
     * 生成日报所需的内容
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
            log.info(existReportList.get(0).getTitle() + " 已存在!");
            return new HashMap<>();
        }

        // 生成标题，期数，日期
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
        reportBaseModel.setReportType("日");
        reportBaseModel.setSerialNo(currentNo);
        reportBaseModel.setTotalNo(totalNo);
        String[] dayNumbers = (dailyStartTime.split(" "))[0].split("-");
        reportBaseModel.setReportDate(dayNumbers[0] + "年" + dayNumbers[1] + "月" + dayNumbers[2] + "日");

        // get data from DataPack
        getData(reportBaseModel, dailyStartTime, dailyEndTime, type);

        // Report
        String title = dailyStartTime.split(" ")[0].replace("-", ".") + "-日报";
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
     * 从中台获取列表数据
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
        List<ReportLevel> reportLevelList = reportLevelDao.findAllByOrderBySort();
        for (ReportLevel reportLevel : reportLevelList) {
            // 查询报告舆情关系表，过滤出所有需要的舆情id()
            List<ReportArticle> reportArticleList = reportArticleDao.findAllByUpdatedTimeBetweenAndReportTypeAndReportLevelId(
                    startDateTime, endDateTime, type, reportLevel.getId());
            List<Long> articleIdList = reportArticleList.stream().map(ReportArticle::getArticleId).collect(Collectors.toList());

            // 请求service-datapack返回所有ID列表中的舆情数据
            JSONObject reportJO = articleAdapt.queryArticlesByIdList(articleIdList);
            System.out.println(reportJO);
        }



        return;
//        ResultVO<JSONObject> rstVO = articleProvider.queryDailyReport(startTime, endTime, type);
//        JSONObject reportJO = rstVO.getData();
//
//        if (!ObjectUtils.isEmpty(reportJO)) {
//            if (reportJO.containsKey("01")) {  // concernList - 重要
//                List<ArticleReport> concernList = JSONArray.parseArray(reportJO.getJSONArray("01").toJSONString(), ArticleReport.class);
//                // transform escape characters
//                for (ArticleReport reportArticle : concernList) {
//                    transArticleReport(reportArticle);
//                }
//                reportBaseModel.setConcernList(concernList);
//            }
//            if (reportJO.containsKey("02")) {  // moreInfoList - 一般
//                List<ArticleReport> concernList = JSONArray.parseArray(reportJO.getJSONArray("02").toJSONString(), ArticleReport.class);
//                // transform escape characters
//                for (ArticleReport reportArticle : concernList) {
//                    transArticleReport(reportArticle);
//                }
//                reportBaseModel.setMoreInfoList(concernList);
//            }
//        }
    }


    /**
     * ArticleReport 转换特殊字符
     * @param articleReport
     */
    private void transArticleReport(ArticleReport articleReport) {
        if (articleReport.getArticleUrl() != null) {
            String articleUlr = articleReport.getArticleUrl();
            articleReport.setArticleUrl(transEscapeChars(articleUlr));
        }
        if (articleReport.getSummary() != null) {
            String summary = articleReport.getSummary();
            articleReport.setSummary(transEscapeChars(summary));
        }
        if (articleReport.getTitle() != null) {
            String title = articleReport.getTitle();
            articleReport.setTitle(transEscapeChars(title));
        }
    }

    /**
     * 转换特殊字符
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
     * 根据模板生成docx文档
     *
     * @param docxTemplate    docx的模板docx文件路径
     * @param docxXmlTemplate docx的模板xml文件名称
     * @param tempDocxXmlPath docx的临时xml文件(docx的模板xml文件填充完数据生成的临时文件)
     * @param params          填充到docx的临时xml文件中的数据
     * @param tmpDocxPath     输出的临时docx文件路径
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
            // 把输入流的文件传到输出流中, 如果是word/document.xml则覆盖
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
