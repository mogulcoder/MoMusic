package com.mogul.network.core.main;

import com.mogul.network.core.app.HttpHelper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * 通用Request请求
 * Get、Post、文件上传
 */
public final class CommonRequest {

    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    /**
     * Get请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @return Get请求对象
     */
    public static Request getRequest(String url, ConcurrentHashMap<String, Object> params, ConcurrentHashMap<String, Object> headers) {
        String finalUrl = HttpHelper.jointParams(url, params);
        //添加请求头
        Headers.Builder header = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                header.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        CacheControl cacheControl = new CacheControl.Builder().maxAge(120, TimeUnit.SECONDS).build();
        return new Request.Builder().url(finalUrl).cacheControl(cacheControl).headers(header.build()).get().build();
    }

    /**
     * Post请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @return Post请求对象
     */
    public static Request postRequest(String url, ConcurrentHashMap<String, Object> params, ConcurrentHashMap<String, Object> headers) {
        //构造请求体
        FormBody.Builder formBody = new FormBody.Builder();
        //遍历请求参数
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formBody.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        //添加请求头
        Headers.Builder header = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                header.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return new Request.Builder().url(url).headers(header.build()).post(formBody.build()).build();
    }

    /**
     * 文件上传请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 文件上传Request对象
     */
    public static Request filePostRequest(String url, Map<String, Object> params) {
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof File) {
                    multipartBody.addPart(Headers.of("content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    multipartBody.addPart(Headers.of("content-Disposition", "form-data; name=\"" + entry.getKey() + "\""), RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder().url(url).post(multipartBody.build()).build();
    }
}