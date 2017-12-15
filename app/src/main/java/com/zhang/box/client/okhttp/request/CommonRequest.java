package com.zhang.box.client.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lilei on 2017/10/13.
 * 创建各种类型的请求对象
 * get,post,文件上传,文件下载
 */

public class CommonRequest {

    /**
     * post请求
     *
     * @param url
     * @param params 参数列表
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params) {

        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }

        }
        FormBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url);
        if (params != null) {
            if (url.contains("?")){
                urlBuilder.append("&");
            }else{
                urlBuilder.append("?");
            }
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            url = urlBuilder.toString();
        }
        return new Request.Builder().url(url).get().build();
    }


    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");

    public static Request createMultiPostRequest(String url, RequestParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    builder.addPart(MultipartBody.Part.createFormData(entry.getKey(), null,
                            RequestBody.create(FILE_TYPE, (File) entry.getValue())));
                } else {
                    builder.addFormDataPart(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
        }

        return new Request.Builder().url(url).post(builder.build()).build();
    }

}
