package com.mogul.network.core.app;

import android.content.Context;

import com.mogul.network.core.main.CommonCall;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;


/**
 * 对外唯一请求辅助类
 */
public class HttpHelper {

    private Context mContext; //上下文

    private String url; //请求地址
    private ConcurrentHashMap<String, Object> mParams; //请求参数
    private ConcurrentHashMap<String, Object> mHeaders; //请求头

    public static final String CALLBACK_ERROR = "The callback must be instantiated";

    private boolean isGet = true; //默认get请求

    private HttpHelper(Context context) {
        this.mContext = context;
    }

    public static HttpHelper with(Context context) {
        return new HttpHelper(context);
    }

    //请求地址
    public HttpHelper url(String url) {
        this.url = url;
        return this;
    }

    //Get请求
    public HttpHelper get() {
        isGet = true;
        return this;
    }

    //Post请求
    public HttpHelper post() {
        isGet = false;
        return this;
    }

    //请求参数
    public HttpHelper addParams(ConcurrentHashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }

    //请求参数
    public HttpHelper addHeaders(ConcurrentHashMap<String, Object> headers) {
        this.mHeaders = headers;
        return this;
    }

    //执行方法
    public void execute(Callback callback) {
        if (callback == null) {
            throw new RuntimeException(CALLBACK_ERROR);
        }
        if (isGet) {
            Call call = new CommonCall(url, mParams, mHeaders).getCall();
            call.enqueue(callback);
        } else {
            Call call = new CommonCall(url, mParams, mHeaders).postCall();
            call.enqueue(callback);
        }
    }

    /**
     * 拼接URL参数
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        if (!url.contains("?")) {
            stringBuilder.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuilder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}