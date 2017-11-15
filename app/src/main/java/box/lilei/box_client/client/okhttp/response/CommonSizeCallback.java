package box.lilei.box_client.client.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeSizeListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonSizeCallback implements Callback {
    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int IO_ERROR = -2; // the JSON relative error
    protected final String EMPTY_MSG = "";
    /**
     * 将其它线程的数据转发到UI线程
     */
    private static final int PROGRESS_MESSAGE = 0x01;

    private DisposeSizeListener listener;
    private Long size;
    private Handler mDisposeHandler;

    public CommonSizeCallback(DisposeDataHandle disposeHandler) {
        listener = (DisposeSizeListener) disposeHandler.mListener;
        size = disposeHandler.size;
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
        final long contentLength;
        if (response.isSuccessful()) {
            contentLength = response.body().contentLength();
        }else{
            contentLength = 0;
        }
        //在子线程操作
        mDisposeHandler.post(new Runnable() {
            @Override
            public void run() {
                handlerResponse(contentLength);
            }
        });
    }

    private void handlerResponse(Long contentLength) {
        if (contentLength == 0) {
            listener.onFail(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }else{
            if (contentLength == size){
                listener.difference();
            }else{
                listener.same();
            }
        }
    }
}