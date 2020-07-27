package com.mogul.network.core.main;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * 通用返回结果
 *
 * @param <T> 实体类
 */
public abstract class CommonCallback<T> implements Callback {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(final Call call, final IOException e) {
        if (e != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onError(call, e);
                }
            });
        }
    }

    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        if (response.isSuccessful() && response.body() != null) {
            final T t = (T) JSON.parseObject(response.body().string(), classInfo(this));
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(call, t);
                }
            });
        }
    }

    public abstract void onError(Call call, IOException e);

    public abstract void onSuccess(Call call, T t);

    /**
     * 解析泛型参数
     */
    private Class<?> classInfo(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        if (type == null) {
            return null;
        }
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}