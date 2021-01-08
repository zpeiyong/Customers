package com.dataint.topic.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.exception.DataintBaseException;
import com.dataint.cloud.common.utils.GetPostUtil;
import com.dataint.topic.db.entity.CrawlSite;
import com.dataint.topic.model.po.SpiderProject;
import com.dataint.topic.service.ICrawlSiteService;
import com.dataint.topic.service.ISpiderService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SpiderServiceImpl implements ISpiderService {
    @Autowired
    private ICrawlSiteService crawlSiteService;

    @Value("${spider.spiderServer.serverUrl}")
    private String spiderBaseUrl = "";
    @Value("${spider.spiderServer.serverAuth}")
    private String spiderAuth;
    @Value("${spider.spiderServer.mongodb.host}")
    private String mongoHost;
    @Value("${spider.spiderServer.mongodb.port}")
    private String mongoPort;
    @Value("${spider.spiderServer.mongodb.database}")
    private String mongoDatabase;
    @Value("${spider.spiderServer.mongodb.user}")
    private String mongoUser;
    @Value("${spider.spiderServer.mongodb.password}")
    private String mongoPassword;
    @Value("${spider.spiderServer.mongodb.table_po}")
    private String mongoTablePo;  // 舆情表
    @Value("${spider.spiderServer.mongodb.table_cnt}")
    private String mongoTableCnt;  // 动态表

    private static int oneDay = 86400;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void pubDisposeProjects(List<String> keywordList) {
        List<CrawlSite> crawlList = crawlSiteService.getEnableCrawlSiteList();

        for (CrawlSite crawlSite : crawlList) {
            String projectName = crawlSite.getProjectName() + "_" + UUID.randomUUID().toString().replace("-", "");
            // check if script exist
            if (crawlSite.getScript() == null)
                continue;

            String script = genScript(projectName, keywordList, crawlSite.getScript());

            boolean ifSuccess = false;
            try {
                // 提交爬虫任务
                ifSuccess = saveScript(projectName, script);
                if (ifSuccess) {
                    Thread.sleep(500);

                    // 更改爬虫任务组别 "disposable"
                    ifSuccess = updateProject(projectName, "group", "disposable");
                }
                if (ifSuccess) {
                    String rateBurst = crawlSite.getRateBurst();
                    if (rateBurst != null && !rateBurst.isEmpty()) {
                        Thread.sleep(500);

                        // 更改爬虫速率
                        ifSuccess = updateProject(projectName, "rate", rateBurst);
                    }
                }
                if (ifSuccess) {
                    Thread.sleep(500);

                    // 更改爬虫任务状态 "RUNNING"
                    ifSuccess = updateProject(projectName, "status", "RUNNING");
                }
                if (ifSuccess) {
                    Thread.sleep(500);

                    // 执行爬虫任务
                    ifSuccess = runProject(projectName);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delDisposeProjects() {
        List<SpiderProject> projectList = getProjectList();

        for (SpiderProject project : projectList) {
            String groupName = project.getGroup();
            String projectName = project.getName();
            Long updateTime = project.getUpdateTime();
            Long currentTime = System.currentTimeMillis();
            if ("disposable".equals(groupName) && (currentTime-updateTime) >= oneDay) {
                // set to delete
                try {
                    // 更改爬虫任务状态 "STOP"
                    updateProject(projectName, "status", "STOP");
                    Thread.sleep(500);
                    // 更改爬虫任务组别 "delete"
                    updateProject(projectName, "group", "delete");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成爬虫脚本
     *
     * @param projectName
     * @param keywordList
     * @param defScript
     * @return
     */
    private String genScript(String projectName, List<String> keywordList, String defScript) {

        String generatedScript = defScript.replace("__PROJECT_NAME__", projectName)
                .replace("__CREATE_TIME__", dateFormat.format(new Date()))
                .replace("__MONGO_HOST__", mongoHost)
                .replace("__MONGO_PORT__", mongoPort)
                .replace("__MONGO_DATABASE__", mongoDatabase)
                .replace("__MONGO_USER__", mongoUser)
                .replace("__MONGO_PASSWORD__", mongoPassword)
                .replace("__MONGO_TABLE__", mongoTablePo)
                .replace("__MONGO_TABLE_2__", mongoTableCnt);

        StringBuilder sb = new StringBuilder();
        for (String keyword : keywordList) {
            sb.append("'").append(keyword).append("'");
            sb.append(", ");
        }
        if (sb.lastIndexOf(",") == sb.length()-2) {
            sb.delete(sb.length()-2, sb.length());
        }

        return generatedScript.replace("__SEARCH_KEYWORD__", sb.toString());
    }

    /**
     * 提交爬虫任务
     *
     * @param projectName
     * @param script
     * @return
     */
    private boolean saveScript(String projectName, String script) {
        String url = spiderBaseUrl + "debug/"+projectName+"/save";

        // header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", spiderAuth);

        // params
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("script", script));

        boolean ifSuccess = false;
        try {
            if ("ok".equals(GetPostUtil.sendPost(url, headerMap, nameValuePairList)))
                ifSuccess = true;
        } catch (DataintBaseException tbe) {
            tbe.printStackTrace();
        }

        return ifSuccess;
    }

    /**
     * 更改爬虫任务组名(group), 状态(status), 频率(rate)
     *
     * @param projectName
     * @param updateType
     * @param updateValue
     * @return
     */
    private boolean updateProject(String projectName, String updateType, String updateValue) {
        String url = spiderBaseUrl + "update";

        // header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", spiderAuth);

        // params
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("name", updateType));
        nameValuePairList.add(new BasicNameValuePair("value", updateValue));
        nameValuePairList.add(new BasicNameValuePair("pk", projectName));

        boolean ifSuccess = false;
        try {
            if ("ok".equals(GetPostUtil.sendPost(url, headerMap, nameValuePairList)))
                ifSuccess = true;
        } catch (DataintBaseException tbe) {
            tbe.printStackTrace();
        }

        return ifSuccess;
    }

    /**
     * 执行爬虫任务
     *
     * @param projectName
     * @return
     */
    private boolean runProject(String projectName) {
        String url = spiderBaseUrl + "run";

        // header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", spiderAuth);

        // params
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("project", projectName));

        boolean ifSuccess = false;
        try {
            String result = GetPostUtil.sendPost(url, headerMap, nameValuePairList);
            if (result != null && !result.isEmpty())
                ifSuccess = JSONObject.parseObject(result).getBoolean("result");
        } catch (DataintBaseException tbe) {
            tbe.printStackTrace();
        }

        return ifSuccess;
    }

    private List<SpiderProject> getProjectList() {
        String url = spiderBaseUrl + "getAll";

        // header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", spiderAuth);

        List<SpiderProject> projectList = new ArrayList<>();
        try {
            JSONArray jsonArray = GetPostUtil.sendGetWithHeader(url, headerMap).getJSONArray("result");
            for (Object object : jsonArray) {
                SpiderProject spiderProject = JSONObject.parseObject(((JSONObject) object).toJSONString(), SpiderProject.class);

                projectList.add(spiderProject);
            }
        } catch (DataintBaseException tbe) {
            tbe.printStackTrace();
        }

        return projectList;
    }
}
