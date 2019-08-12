package com.taotao.common;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author hmt
 * @date 2019/8/1 15:18
 * 网络请求工具类
 */
public class HttpUtil {
    private static HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            return false;
        }
    };
    private static CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36")
            .setRetryHandler(retryHandler)
            .build();

    //Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.80 Safari/537.36

    public static String doGet(String url) {
        return doGet(url, "UTF-8");
    }

    public static String doGet(String url, String charset) {
        HttpGet getMethod = new HttpGet(url);
        try {
            HttpResponse resp = httpClient.execute(getMethod);
            return EntityUtils.toString(resp.getEntity(), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, "UTF-8");
    }

    public static String doGet(String url, Map<String, String> params, String charset){
       try {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.setCharset(Charset.forName(charset));
        if(params != null && params.size() > 0){
            for (String key:params.keySet()){
                uriBuilder.addParameter(key,params.get(key));
                }
            }
           HttpGet getMethod = new HttpGet(url);
           HttpResponse resp = httpClient.execute(getMethod);
           return EntityUtils.toString(resp.getEntity(), charset);
        } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
    }
    public static String doPost(String url) {
        return doPost(url, null, "UTF-8");
    }

    public static String doPost(String url, Map<String, String> params) {
        return doPost(url, params, "UTF-8");
    }

    public static String doPost(String url, Map<String, String> params, String charset) {

        HttpPost postMethod = new HttpPost(url);
        try {
            if(params != null && params.size() > 0) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for(String key : params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.get(key)));
                }
                postMethod.setEntity(new UrlEncodedFormEntity(nvps,charset));
            }
            HttpResponse resp = httpClient.execute(postMethod);
            return EntityUtils.toString(resp.getEntity(), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //从请求中获取完整的url
    public static String getFullUrl(HttpServletRequest request){
        StringBuilder sb = new StringBuilder();
        //获取baseurl
        String port = request.getLocalPort() + "";
        String url = request.getRequestURL().toString();
        int portIdx = url.indexOf(port);
        url = url.substring(0,portIdx+port.length()+1);
        sb.append(url);
        //获取pathurl
        sb.append(request.getRequestURI());
        //获取参数
        String params = parseReqParameters(request.getParameterMap());
        sb.append(params);
        return sb.toString();
    }

    /**
     * 将GET请求中的参数，拼接成String串(?key=val&key2=val2...)
     * @param parameters
     * @return
     */
    public static String parseReqParameters(Map<String, String[]> parameters) {
        StringBuilder sb = new StringBuilder();
        if(parameters.size() > 0) {
            sb.append("?");
        } else {
            return "";
        }
        // 单值的情况：username=zhangsan
        // 多值得情况：hobby=reading,sleeping,swimming
        for(String key : parameters.keySet()) {
            sb.append(key);
            sb.append("=");
            String[] valArr = parameters.get(key);
            StringBuilder sbVal = new StringBuilder("");
            if(valArr != null && valArr.length > 0) {

                for(String val : valArr) {
                    sbVal.append(val);
                    sbVal.append(",");
                }

                sbVal.deleteCharAt(sbVal.length() - 1);
            }
            sb.append(sbVal.toString());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
