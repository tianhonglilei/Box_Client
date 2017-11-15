package box.lilei.box_client.client.okhttp.response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import box.lilei.box_client.client.okhttp.exception.OkHttpException;
import box.lilei.box_client.client.okhttp.handler.DisposeDataHandle;
import box.lilei.box_client.client.okhttp.listener.DisposeDownloadDataListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class CommonFileCallback implements Callback {
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
    private Handler mDeliveryHandler;
    private DisposeDownloadDataListener mListener;
    private String mFilePath;
    private int mProgress;

    public CommonFileCallback(DisposeDataHandle handle) {
        this.mListener = (DisposeDownloadDataListener) handle.mListener;
        this.mFilePath = handle.mSource;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PROGRESS_MESSAGE:
                        mListener.onProgress((int) msg.obj);
                        break;
                }
            }
        };
    }

    @Override
    public void onFailure(final Call call, final IOException ioexception) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFail(new OkHttpException(NETWORK_ERROR, ioexception));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final File file;
        if (response.isSuccessful()) {
            file = handleResponse(response);
        }else{
           file = null;
        }
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file != null) {
                    mListener.onSuccess(file);
                } else {
                    mListener.onFail(new OkHttpException(IO_ERROR, EMPTY_MSG));
                }
            }
        });
    }

    /**
     * 此时还在子线程中，不则调用回调接口
     *
     * @param response
     * @return
     */
    private File handleResponse(Response response) {
        if (response == null) {
            return null;
        }
        InputStream inputStream = null;
        File file = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[2048];
        int length = -1;
        int currentLength = 0;
        double sumLength = 0;
        try {
            file = new File(mFilePath);
            fos = new FileOutputStream(file);
            inputStream = response.body().byteStream();
            sumLength = (double) response.body().contentLength();

            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
                currentLength += length;
                mProgress = (int) (currentLength / sumLength * 100);
                mDeliveryHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
            }
            fos.flush();
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}