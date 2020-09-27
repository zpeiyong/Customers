package com.dataint.topic.utils;

import com.dataint.cloud.common.model.Constants;
import com.dataint.topic.model.po.SendModelEvent;
import com.dataint.topic.model.po.SendModelStatistic;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.apache.poi.hssf.usermodel.HSSFDateUtil;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SendDataUtil {
    private static DecimalFormat df = new DecimalFormat("#.00");
    private static SimpleDateFormat parseSDF = new SimpleDateFormat("yyyy/MM/dd");

//    private static String dataFolderPath = ServiceConfig.getServiceConfig(Constants.apiBasicService, Constants.shhgBDCenterDataPath);
    private static String dataFolderPath = "";

    public static List<SendModelStatistic> getSendModelStatistic() throws Exception {
        List<SendModelStatistic> statisticList = new ArrayList<>();

        List<String> filePathList = getFilesFromFolder(dataFolderPath);

        for (String path : filePathList) {
            List<List<String>> xlsxList = readXlsx(path);

            SendModelStatistic statistic = new SendModelStatistic();
            List<SendModelEvent> eventList = new ArrayList<>();

            boolean ifStat = false;
            boolean ifDetail = false;
            for(List<String> sheetList : xlsxList) {
                if (sheetList == null || sheetList.get(0) == null || "".equals(sheetList.get(0))) {
                    continue;
                }
                if ("keyword_id".equals(sheetList.get(0))) {
                    ifStat = true;
                    continue;
                }
                if ("nodetime".equals(sheetList.get(0))) {
                    ifDetail = true;
                    continue;
                }

                //
                if (ifStat) {
                    statistic.setKeywordId(sheetList.get(0));
                    statistic.setKeywords(sheetList.get(1));
                    statistic.setStatisticTime(Constants.DateTimeSDF.format(new Date()));
                    if (sheetList.get(3) != null && !sheetList.get(3).isEmpty())
                        statistic.setScore(df.format(Double.valueOf(sheetList.get(3))));
                    else
                        statistic.setScore("0");
                    if (sheetList.get(4) != null && !sheetList.get(4).isEmpty())
                        statistic.setAreaScore(df.format(Double.valueOf(sheetList.get(4))));
                    else
                        statistic.setAreaScore("0");
                    if (sheetList.get(5) != null && !sheetList.get(5).isEmpty())
                        statistic.setTopicScore(df.format(Double.valueOf(sheetList.get(5))));
                    else
                        statistic.setTopicScore("0");
                    if (sheetList.get(6) != null && !sheetList.get(6).isEmpty())
                        statistic.setCommentScore(df.format(Double.valueOf(sheetList.get(6))));
                    else
                        statistic.setCommentScore("0");
                    if (sheetList.get(7) != null && !sheetList.get(7).isEmpty())
                        statistic.setLikeScore(df.format(Double.valueOf(sheetList.get(7))));
                    else
                        statistic.setLikeScore("0");
                    if (sheetList.get(8) != null && !sheetList.get(8).isEmpty())
                        statistic.setForwardScore(df.format(Double.valueOf(sheetList.get(8))));
                    else
                        statistic.setForwardScore("0");
                    if (sheetList.get(9) != null && !sheetList.get(9).isEmpty())
                        statistic.setConsignee(sheetList.get(9));
                    else
                        statistic.setConsignee("");
                    if (sheetList.get(10) != null && !sheetList.get(10).isEmpty())
                        statistic.setConsignor(sheetList.get(10));
                    else
                        statistic.setConsignor("");
                    if (sheetList.get(11) != null && !sheetList.get(11).isEmpty())
                        statistic.setWebsiteCnt(Integer.valueOf(sheetList.get(11)));
                    else
                        statistic.setWebsiteCnt(0);
                    if (sheetList.get(12) != null && !sheetList.get(12).isEmpty())
                        statistic.setAllLikeCnt(Integer.valueOf(sheetList.get(12)));
                    else
                        statistic.setAllLikeCnt(0);
                    if (sheetList.get(13) != null && !sheetList.get(13).isEmpty())
                        statistic.setAllCommentCnt(Integer.valueOf(sheetList.get(13)));
                    else
                        statistic.setAllCommentCnt(0);
                    if (sheetList.get(14) != null && !sheetList.get(14).isEmpty())
                        statistic.setAllForwardCnt(Integer.valueOf(sheetList.get(14)));
                    else
                        statistic.setAllForwardCnt(0);
                    if (sheetList.get(15) != null && !sheetList.get(15).isEmpty())
                        statistic.setHotTimeCnt(Integer.valueOf(sheetList.get(15)));
                    else
                        statistic.setHotTimeCnt(0);
                    if (sheetList.get(16) != null && !sheetList.get(16).isEmpty())
                        statistic.setGovMediaCnt(Integer.valueOf(sheetList.get(16)));
                    else
                        statistic.setGovMediaCnt(0);
                    if (sheetList.get(17) != null && !sheetList.get(17).isEmpty())
                        statistic.setSelfMediaCnt(Integer.valueOf(sheetList.get(17)));
                    else
                        statistic.setSelfMediaCnt(0);
                    if (sheetList.get(18) != null && !sheetList.get(18).isEmpty())
                        statistic.setWbwxMediaCnt(Integer.valueOf(sheetList.get(18)));
                    else
                        statistic.setWbwxMediaCnt(0);

                    ifStat = false;
                }

                if (ifDetail) {
                    SendModelEvent sendModelEvent = new SendModelEvent();
                    sendModelEvent.setNodeTime(Constants.DateSDF.format(parseSDF.parse(sheetList.get(0))));
                    if (sheetList.get(1) != null && !sheetList.get(1).isEmpty())
                        sendModelEvent.setArticleCnt(Integer.valueOf(sheetList.get(1)));
                    else
                        sendModelEvent.setArticleCnt(0);
                    if (sheetList.get(2) != null && !sheetList.get(2).isEmpty())
                        sendModelEvent.setHotValue(sheetList.get(2));
                    else
                        sendModelEvent.setHotValue("0");
                    if (sheetList.get(3) != null && !sheetList.get(3).isEmpty())
                        sendModelEvent.setWebsiteCnt(Integer.valueOf(sheetList.get(3)));
                    else
                        sendModelEvent.setWebsiteCnt(0);
                    if (sheetList.size() > 4 && sheetList.get(4) != null && !sheetList.get(4).isEmpty())
                        sendModelEvent.setKeynode(sheetList.get(4));
                    else
                        sendModelEvent.setKeynode("否");
                    if (sheetList.size() > 5 && sheetList.get(5) != null && !sheetList.get(5).isEmpty())
                        sendModelEvent.setKeynodeType(sheetList.get(5));
                    else
                        sendModelEvent.setKeynodeType("");
                    if (sheetList.size() > 6 && sheetList.get(6) != null && !sheetList.get(6).isEmpty())
                        sendModelEvent.setKeynodeMark(sheetList.get(6));
                    else
                        sendModelEvent.setKeynodeMark("");

                    eventList.add(sendModelEvent);
                }
            }
            statistic.setEventList(eventList);

            //
            statisticList.add(statistic);
        }

        return statisticList;
    }

    private static List<List<String>> readXlsx(String path) throws Exception {
//        InputStream is = new FileInputStream(path);
//        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<List<String>> result =new ArrayList<>();
//        //循环每一页，并处理当前循环页
//        //for(XSSFSheet xssfSheet : xssfWorkbook){
//        for(int numSheet=0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++){
//            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
//            if (xssfSheet==null)
//                continue;
//            //处理当前页，循环读取每一行
//            for (Row row : xssfSheet) {
////            for(int rowNum=0; rowNum<=xssfSheet.getLastRowNum();rowNum++){
////                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
//                XSSFRow xssfRow = (XSSFRow) row;
//
//                if (xssfRow == null)
//                    continue;
//                int minColIx = xssfRow.getFirstCellNum();
//                int maxColIx = xssfRow.getLastCellNum();
//                List<String> rowList = new ArrayList<>();
//                //遍历该行获取处理每个cell元素
//                for(int colIx=minColIx;colIx<maxColIx; colIx++){
//                    XSSFCell cell= xssfRow.getCell(colIx);
//                    if (cell==null) {
//                        rowList.add("");
//                    } else {
//                        rowList.add(getStringVal(cell));
//                    }
//                }
//                result.add(rowList);
//            }
//        }
//
//        //
//        is.close();
////        is = null;

        return result;
    }
//
//    private static String getStringVal (XSSFCell cell){
//        switch (cell.getCellType()) {
//            case BOOLEAN:
//                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
//            case FORMULA://公式格式
//                return cell.getCellFormula();
//            case NUMERIC://数字格式
//                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                    Date date = cell.getDateCellValue();
//                    return DateFormatUtils.format(date, "yyyy/MM/dd");
//                } else {
//                    cell.setCellType(CellType.STRING);
//
////                    DecimalFormat df = new DecimalFormat("#.00");
////                    return df.format(cell.getNumericCellValue());
//                }
//            case STRING:
//                return cell.getStringCellValue();
//
//            default:
//                return "";
//        }
//    }

    /**
     * get all files' absolute path under the folder
     * @param folderPath
     * @return
     */
    private static List<String> getFilesFromFolder(String folderPath) {
        List<String> filePathList = new ArrayList<>();

        File file = new File(folderPath);
        File[] fs = file.listFiles();
        if (fs != null) {
            for (File f : fs) {
                if (!f.isDirectory())
                    filePathList.add(f.getAbsolutePath());
            }
        }

        return filePathList;
    }
}
