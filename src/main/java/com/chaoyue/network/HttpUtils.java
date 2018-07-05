package com.chaoyue.network;

import com.chaoyue.common.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 使用json格式发送 post 请求
     *
     * @param url  请求地址
     * @param json 请求参数
     * @return 请求结果
     */
    public static String postBasic(String url, String json) {
        log.info("General post request to " + url + " with parameters " + json);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity =
                new StringEntity(json, ContentType.APPLICATION_JSON.withCharset(DEFAULT_CHARSET));
        httpPost.setEntity(entity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost, HttpClientContext.create())) {
            if (null != response) {
                String responseResult = EntityUtils.toString(new BufferedHttpEntity(response.getEntity()), DEFAULT_CHARSET);
                response.close();
                log.info("Response:" + responseResult);
                return responseResult;
            }
        } catch (Exception e) {
            log.error(String.format("error to process request %s whit params %s ", url, json), e);
        }
        return null;
    }

    /**
     * 使用json格式发送 post 请求 并处理返回字符串为自定类型
     *
     * @param baseUrl    请求地址
     * @param parameters 请求参数
     * @param t          转换类型
     * @return 返回参数
     */
    public static <T> T post(String baseUrl, Map<String, Object> parameters, Class<T> t) {
        Map<String, Object> map = new HashMap<>(parameters);
        String responseStr = postBasic(baseUrl, JsonUtils.objectToJSON(map));
        return JsonUtils.toObject(responseStr, t);

    }

    /**
     * 发送get请求
     *
     * @param url        请求地址
     * @param parameters 请求参数
     * @return 返回参数
     */
    public static String get(String url, Map<String, String> parameters) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (null != parameters) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    builder.setParameter(entry.getKey(), entry.getValue());
                }
            }
            URI fullURI = builder.build();
            HttpGet httpGet = new HttpGet(fullURI);

            response = httpClient.execute(httpGet, HttpClientContext.create());
            if (null != response) {
                return EntityUtils.toString(new BufferedHttpEntity(response.getEntity()), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            log.error(String.format("error to process request %s whit params %s ", url, JsonUtils.objectToJSON(parameters)));
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException ignored) {

                }
            }
        }
        return null;
    }

}
