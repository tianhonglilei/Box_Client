package box.lilei.box_client.client.okhttp.listener;

/**
 * @author vision
 * @function 监听下载进度
 */
public interface DisposeDownloadListener extends OkHttpDisposeListener {
	void onProgress(int progrss);
}
