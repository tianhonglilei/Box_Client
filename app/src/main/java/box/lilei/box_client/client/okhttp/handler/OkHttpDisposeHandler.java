package box.lilei.box_client.client.okhttp.handler;

import box.lilei.box_client.client.okhttp.listener.OkHttpDisposeListener;

/**
 * Created by lilei on 2017/10/13.
 */

public class OkHttpDisposeHandler {
    public OkHttpDisposeListener mListener;
    public Class<?> mClass;
    public String mSource;

    public OkHttpDisposeHandler(OkHttpDisposeListener mListener) {
        this.mListener = mListener;
    }

    public OkHttpDisposeHandler(OkHttpDisposeListener mListener, Class<?> mClass) {
        this.mListener = mListener;
        this.mClass = mClass;
    }

    public OkHttpDisposeHandler(OkHttpDisposeListener mListener, String mSource) {
        this.mListener = mListener;
        this.mSource = mSource;
    }
}
