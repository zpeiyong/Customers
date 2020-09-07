package com.dataint.cloud.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dataint.cloud.common.exception.DataintBaseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetPostUtil {

    /**
     * @param baseUrl
     * @param params k,v
     * @return
     */
    public static JSONObject sendGet(String baseUrl, Map<String, String> params) throws DataintBaseException {
        if (params == null || params.size() == 0) {
            return sendGet(baseUrl);
        }
        StringBuilder url = new StringBuilder();
        url.append(baseUrl);
        url.append("?");
        params.forEach((k, v) -> {
            url.append(k);
            url.append("=");
            url.append(v);
            url.append("&");
        });
        url.deleteCharAt(url.length() - 1);
        return sendGet(url.toString());
    }

    /** 
     * 向指定URL发送GET方法的请求 
     *  
     * @param url 
     *            发送请求的URL 
     * @return URL所代表远程资源的响应
     */
    public static JSONObject sendGet(String url) throws DataintBaseException {
        url = url.replaceAll(" ", "%20");
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;
        HttpResponse resp;
        try {
            resp = client.execute(httpGet);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
            } else {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
                throw new DataintBaseException(result, statusCode);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        result = formatDateString(result);
        return JSONObject.parseObject(result);
    }

    /**  
     * 向指定URL发送POST方法的请求  
     *   
     * @param url  
     *            发送请求的URL  
     * @param jsonObject
     *            请求参数，请求参数应该是JSON的形式。  
     * @return URL所代表远程资源的响应  
     */
    public static JSONObject sendPost(String url, String jsonObject) throws DataintBaseException {
        return sendPost(url, jsonObject, 6000);
    }

    public static JSONObject sendPost(String url, String jsonObject, int timeout) throws DataintBaseException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
            .setConnectTimeout(timeout).build();//设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;

        StringEntity entity = new StringEntity(jsonObject, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        HttpResponse resp;
        try {
            resp = client.execute(httpPost);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
            } else {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
                throw new DataintBaseException(result, statusCode);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        result = formatDateString(result);
        return JSONObject.parseObject(result);
    }

    /**
     * 发送Post请求
     * @param url
     * @param params
     * @return
     * @throws DataintBaseException
     */
    public static String sendPost(String url, NameValuePair... params) throws DataintBaseException {
        return sendPost(url, Arrays.asList(params));
    }

    /**
     * 向指定URL发送POST方法的请求 , NameValuePair 提供对 请求参数为list的支持
     * @param url
     * @param list
     * @return
     * @throws DataintBaseException
     */
    public static String sendPost(String url, List list) throws DataintBaseException {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;

        HttpResponse resp;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
            } else {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
                throw new DataintBaseException(result, resp.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        result = formatDateString(result);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String uploadFile(String url, String relativePath, File file)
                                                                               throws DataintBaseException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        HttpPost httppost = new HttpPost(url);
        HttpEntity req = MultipartEntityBuilder.create()
            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            .addPart("part", new StringBody(relativePath, ContentType.MULTIPART_FORM_DATA))
            .addPart("file", new FileBody(file)).build();

        String result = null;
        httppost.setEntity(req);
        try {
            response = httpclient.execute(httppost);
            HttpEntity he = response.getEntity();
            result = EntityUtils.toString(he, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String uploadFile(String url, File file) throws DataintBaseException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;

        HttpPost httppost = new HttpPost(url);
        HttpEntity req = MultipartEntityBuilder.create()
            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("file", new FileBody(file))
            .build();

        String result = null;
        httppost.setEntity(req);
        try {
            response = httpclient.execute(httppost);
            HttpEntity he = response.getEntity();
            result = EntityUtils.toString(he, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 向指定的url发送delete请求
     * @param url
     * @return
     * @throws DataintBaseException
     */
    public static  JSONObject sendDelete(String url) throws DataintBaseException {
        url = url.replaceAll(" ", "%20");
        HttpDelete httpDelete = new HttpDelete(url);
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;
        HttpResponse resp;
        try {
            resp = client.execute(httpDelete);
            int statusCode = resp.getStatusLine().getStatusCode();
            if(statusCode==200) {
                HttpEntity he=resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
            }else {
                HttpEntity he = resp.getEntity();
                result = EntityUtils.toString(he,"UTF-8");
                throw  new DataintBaseException(result, statusCode);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(result);
    }

    /**
     * 向指定的url发送put请求
     * @param url
     * @param jsonObject
     * @param timeout
     * @return
     * @throws DataintBaseException
     */
    public static  JSONObject sendPut(String url, String jsonObject, int timeout) throws DataintBaseException{
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                .setConnectTimeout(timeout).build();//设置请求和传输超时时间
        httpPut.setConfig(requestConfig);
        CloseableHttpClient client = HttpClients.createDefault();
        String result = null;

        StringEntity entity = new StringEntity(jsonObject, "utf-8");//解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPut.setEntity(entity);

        HttpResponse resp;
        try {
            resp = client.execute(httpPut);
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
            } else {
                HttpEntity he = (HttpEntity) resp.getEntity();
                result = EntityUtils.toString(he, "UTF-8");
                throw new DataintBaseException(result, statusCode);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(result);
    }

//    /**
//     * 时间格式转化
//     * 从yyyy-MM-ddTHH:mm:ssZ变为yyyy-MM-dd HH:mm:ss
//     * */
//    private static String formatDateString(String str) {
//
//        String regex = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";
//        Pattern r = Pattern.compile(regex);
//        Matcher m = r.matcher(str);
//        String findStr = "";
//        while (m.find()) {
//            findStr = m.group(0);
//            String replaceStr = findStr.replaceAll("T", " ");
//            replaceStr = replaceStr.replaceAll("Z", "");
//            replaceStr = DateUtils.addMin(replaceStr, 8 * 60, "yyyy-MM-dd HH:mm:ss");
//            str = str.replaceAll(findStr, replaceStr);
//        }
//        //    	//将时间+8小时
//        //    	String regex = "(?<=T)\\d{2}(?=:)";
//        //    	Pattern r = Pattern.compile(regex);
//        //        Matcher m = r.matcher(str);
//        //        String num = "";
//        //        while (m.find()) {
//        //        	num = m.group(0);
//        //        }
//        //    	str = str.replaceAll(regex, num);
//        //    	//去掉时间中的T
//        //        regex = "(?<=\\d{4}-\\d{2}-\\d{2})T";
//        //    	r = Pattern.compile(regex);
//        //        m = r.matcher(str);
//        //        str = m.replaceAll(" ");
//        //        //去掉时间中的Z
//        //        regex = "(?<=\\d{2}:\\d{2}:\\d{2})Z";
//        //        r = Pattern.compile(regex);
//        //        m = r.matcher(str);
//        //        str = m.replaceAll("");
//        return str;
//    }

    // 提供主方法，测试发送GET请求和POST请求  
    //    public static void main(String args[]) {
    //        try {
    //            String s1 = GetPostUtil
    //                .sendGet("http://localhost:8086/sys/user/getUserInfo?userId=xiaoyuId");
    //            System.out.println(s1);
    //
    //            try {
    //                String url = "http://127.0.0.1:8085/file/upload";
    //                String filePath = "";
    //                File file = new File(filePath);
    //                GetPostUtil.uploadFile(url, file);
    //            } catch (ThinventBaseException e) {
    //                System.out.println(e);
    //            }
    //        } catch (ThinventBaseException e) {
    //            // TODO Auto-generated catch block
    //            e.printStackTrace();
    //        }

    /*String param="{\"name\":\"李刚\",\"age\":\"13\"}";
    System.out.println(param);
    JSONObject json = new JSONObject();
    json.put("name", "李刚");
    json.put("age", "13");
    System.out.println(json.toString());
    //发送POST请求  
    JSONObject s1 = GetPostUtil.sendPost("http://192.168.10.124:8080/tongbao/card/v1.0/applyCard" ,"userId=1&token=4&merchId=1");  
    String s1 = TestGetPost.sendGet("http://192.168.10.124:8080/tongbao/card/v1.0/pay", "userId=1&token=4&merchId=201508111544260856&cardNo=1&payAmount=1093");
    System.out.println(s1); */
    //	    String getUrl = "http://localhost:8083/test/test1";
    //	    String getObject;
    //	    System.out.println(EduEnum.BENKE.getIndex());
    /*try {
    	getObject = GetPostUtil.sendGet(getUrl);
    	JSONObject json = JSON.parseObject(getObject);
    	boolean flag = json.getBoolean("");
    } catch (Exception e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    }*/
    //    	String str = "11dsfsfs";
    //    	System.out.println(str.toString());
    //        num = Integer.valueOf(num)+8+"";
    //        System.out.println(str.replaceAll(regex, num));
    //        System.out.println(str);
    //    	String num = m.group(0);
    //    	System.out.println(num);

    //        System.out.println(str.replaceAll(regex, "$0+8"));

    //    }
}