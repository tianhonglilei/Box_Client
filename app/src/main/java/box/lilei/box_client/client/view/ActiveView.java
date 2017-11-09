package box.lilei.box_client.client.view;

import android.content.Context;

/**
 * Created by lilei on 2017/11/7.
 */

public interface ActiveView {

    void changeDownloadProgress(int maxNum, int successNum, int failNum);

    void showDialog(String text);

    void hideDialog();

    void skipToADBannerActivity();
}
