package box.lilei.box_client.client.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeDataListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lilei on 2017/10/13.
 */

public class CommonJsonCallback implements Callback {

    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";


    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    private DisposeDataListener listener;
    private Class<?> mClass;
    private Handler mDisposeHandler;

    public CommonJsonCallback(DisposeDataHandle disposeHandler) {
        listener = disposeHandler.mListener;
        mClass = disposeHandler.mClass;
        mDisposeHandler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void onFailure(Call call, final IOException e) {
        mDisposeHandler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFail(e);
            }
        });

    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String result;
        if (response.isSuccessful()) {
            result = response.body().string();
        }else{
            result = "";
        }
        //在子线程操作
        mDisposeHandler.post(new Runnable() {
            @Override
            public void run() {
                handlerResponse(result);
            }
        });
    }

    private void handlerResponse(String result) {
        if (result == null || result.equals("")) {
            listener.onFail(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }else{
            listener.onSuccess(result);
        }

//        try {
//            JSONObject resultObj = new JSONObject(result);
//            if (resultObj.has(RESULT_CODE)) {
//                if (resultObj.optInt(RESULT_CODE) == RESULT_CODE_VALUE) {
//                    if (mClass == null) {
//                        listener.onSuccess(resultObj);
//                    } else {
//                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(resultObj, mClass);
//                        if (obj == null){
//                            listener.onFail(new OkHttpException(JSON_ERROR,EMPTY_MSG));
//                        }else{
//                            listener.onSuccess(obj);
//                        }
//                    }
//                } else {
//                    listener.onFail(new OkHttpException(JSON_ERROR, EMPTY_MSG));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            listener.onFail(new OkHttpException(OTHER_ERROR,e.getMessage()));
//
//        }

    }
}
