package com.zhang.box.client.okhttp;

import com.zhang.box.client.okhttp.response.CommonSizeCallback;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import com.zhang.box.client.okhttp.handler.DisposeDataHandle;
import com.zhang.box.client.okhttp.response.CommonFileCallback;
import com.zhang.box.client.okhttp.response.CommonJsonCallback;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lilei on 2017/10/16.
 */

public class CommonOkHttpClient {
    private static final int TIME_OUT = 90;
    private static OkHttpClient mOkHttpClient;

    static {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
        {
            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }
        });

        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        mOkHttpClient = okHttpClientBuilder.build();
    }


    /**
     * 通过构造好的Request,Callback去发送请求
     *
     * @param request
     * @param
     */
    public static Call get(Request request, DisposeDataHandle handle)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call post(Request request, DisposeDataHandle handle)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call downloadFile(Request request, DisposeDataHandle handle)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }

    public static Call compareSize(Request request, DisposeDataHandle handle)
    {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonSizeCallback(handle));
        return call;
    }





























}
