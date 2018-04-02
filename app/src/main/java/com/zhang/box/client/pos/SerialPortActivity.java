package com.zhang.box.client.pos;

import android.app.Activity;

/**
 * Created by lilei on 2018/3/29.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zhang.box.R;
import com.zhang.box.contants.Constants;

import android_serialport_api.SerialPort;

public abstract class SerialPortActivity extends Activity {

    protected SerialTool tool;
    protected SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;


    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();

            byte[] test = null;
            try {
                while(true) {
                    if(mSerialPort==null){
                        mSerialPort = SerialTool.getSerialPort();
                    }
                    Log.e("aaaaaaaaaaaaaa","aaaaaaaaaaaaaa");
                    Thread.currentThread().sleep(500);
                    test = SerialTool.readFromPort(mSerialPort);
                    if(test!=null){
                        PosRequest posRequest = PosRequest.decRequest(Demo.printHexString(test));
                        String srt3 = posRequest.toString();

                        Log.e("ddddddddddddddddddddd", srt3);
                        onDataReceived(test,test.length);
                        test = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            int bufflenth = 0; //获取buffer里的数据长度
//            try {
//                bufflenth = mInputStream.available();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            byte[] buffer = new byte[bufflenth];
//            while (!isInterrupted()) {
//                int size;
//                try {
//                    if (mInputStream == null) {
//                        return;
//                    }
//                    size = mInputStream.read(buffer);
//                    if (size > 0) {
//                        onDataReceived(buffer, size);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return;
//                }
//            }
        }
    }

    private void DisplayError(int resourceId) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Error");
        b.setMessage(resourceId);
        b.setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SerialPortActivity.this.finish();
            }
        });
        b.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tool = SerialTool.getSerialTool();
        try {
            mSerialPort = SerialTool.getSerialPort();
            mOutputStream = mSerialPort.b();
            mInputStream = mSerialPort.a();

            /* Create a receiving thread */
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            DisplayError(R.string.error_security);
        } catch (IOException e) {
            DisplayError(R.string.error_unknown);
        } catch (InvalidParameterException e) {
            DisplayError(R.string.error_configuration);
        }
    }

    protected abstract void onDataReceived(final byte[] buffer, final int size);

    /**
     * 断开连接
     */
    protected abstract void disConnect();

    @Override
    protected void onDestroy() {
//        if (mOutputStream!=null){
//            try {
//                mOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (mInputStream !=null){
//            try {
//                mInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (mReadThread != null)
        Log.e("ssssssssssssssssssss","DDDDDDDDDDDDDDDDDDDDDDDDDDD");
        disConnect();
//        tool.closeSerialPort(mSerialPort);
        mSerialPort = null;
        super.onDestroy();

    }


}
