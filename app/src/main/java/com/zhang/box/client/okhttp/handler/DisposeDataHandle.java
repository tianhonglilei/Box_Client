package com.zhang.box.client.okhttp.handler;

import com.zhang.box.client.okhttp.listener.DisposeDataListener;

/**
 * Created by lilei on 2017/10/13.
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener;
    public Class<?> mClass;
    public String mSource;
    public Long size;

    public DisposeDataHandle(DisposeDataListener mListener) {
        this.mListener = mListener;
    }

    public DisposeDataHandle(DisposeDataListener mListener, Class<?> mClass) {
        this.mListener = mListener;
        this.mClass = mClass;
    }

    public DisposeDataHandle(DisposeDataListener mListener, String mSource) {
        this.mListener = mListener;
        this.mSource = mSource;
    }

    public DisposeDataHandle(DisposeDataListener mListener, Long size) {
        this.mListener = mListener;
        this.size = size;
    }


}
