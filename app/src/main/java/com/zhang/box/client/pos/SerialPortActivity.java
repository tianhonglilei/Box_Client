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
            while (!isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int size;
                try {
                    if (mInputStream == null) {
                        return;
                    }
                    int bufflenth = mInputStream.available(); //获取buffer里的数据长度
                    byte[] buffer = new byte[bufflenth];
                    while (bufflenth != 0) {
                        buffer = new byte[bufflenth]; //初始化byte数组为buffer中数据的长度
                        mInputStream.read(buffer);
                        bufflenth = mInputStream.available();
                    }
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
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
            mOutputStream = Constants.serialPort.b();
            mInputStream = Constants.serialPort.a();

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

    @Override
    protected void onDestroy() {
        if (mReadThread != null)
            mReadThread.interrupt();
        tool.closeSerialPort();
        mSerialPort = null;
        super.onDestroy();
    }
}
