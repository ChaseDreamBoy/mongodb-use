package com.xh.mongodb.utils;

import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * PostRequest
 *
 * @author xiaohe
 */
@Slf4j
public class PostRequest {

    /**
     * http client
     */
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();

    /**
     * 调用http接口
     *
     * @param url    请求 url
     * @param json   请求json
     * @param header http 参数头
     * @return 返回数据
     * @throws Exception http 错误
     */
    public static synchronized String doPost(String url, String json, Map<String, String> header) throws Exception {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder().headers(Headers.of(header)).url(url).post(body).build();
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new Exception(response.toString());
        }
    }

    /**
     * 调用http接口
     *
     * @param url    请求 url
     * @param header http 参数头
     * @return 返回数据
     * @throws Exception http 错误
     */
    public static synchronized String doGet(String url, Map<String, String> header) throws Exception {
        Request request = new Request.Builder().headers(Headers.of(header)).url(url).get().build();
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new Exception(response.toString());
        }
    }

}
