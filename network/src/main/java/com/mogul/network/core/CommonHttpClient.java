package com.mogul.network.core;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * 请求客户端
 */
public class CommonHttpClient {

    private static OkHttpClient instance;
    private static final int TIME_OUT = 30;

    private CommonHttpClient() {
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (CommonHttpClient.class) {
                if (instance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
                    builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
                    builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
                    instance = builder.build();
                }
            }
        }
        return instance;
    }
}