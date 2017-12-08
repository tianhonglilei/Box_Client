package com.zhang.box.client.okhttp.listener;

/**
 * @author vision
 * @function 监听下载进度
 */
public interface DisposeDownloadDataListener extends DisposeDataListener {
	void onProgress(int progrss);
}
