package com.mogul.network.core.main;

import com.mogul.network.core.CommonHttpClient;

import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;


/**
 * 通用请求对象
 */
public class CommonCall {

    private String url; //请求地址
    private ConcurrentHashMap<String, Object> mParams; //请求参数
    private ConcurrentHashMap<String, Object> mHeaders; //请求头

    public CommonCall(String url, ConcurrentHashMap<String, Object> params, ConcurrentHashMap<String, Object> headers) {
        this.url = url;
        mParams = params;
        mHeaders = headers;
    }

    /**
     * get call
     *
     * @return call
     */
    public Call getCall() {
        return CommonHttpClient.getInstance().newCall(CommonRequest.getRequest(url, mParams, mHeaders));
    }

    /**
     * post call
     *
     * @return call
     */
    public Call postCall() {
        return CommonHttpClient.getInstance().newCall(CommonRequest.postRequest(url, mParams, mHeaders));
    }
}