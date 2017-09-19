package box.lilei.box_client.client.thread;

import android.os.Bundle;
import android.os.Message;

import box.lilei.box_client.client.view.activity.ADBannerActivity;

/**
 * Created by lilei on 2017/9/18.
 */

public class HomeGoodsAutoScrollRunnable implements Runnable {

    private int total;
    private int now;
    private boolean goRight;

    public HomeGoodsAutoScrollRunnable(int total, int now, boolean goRight) {
        this.total = total;
        this.now = now;
        this.goRight = goRight;
    }

    @Override
    public void run() {
        if(goRight){
            for (int i = now; i < total;i++) {
                try {
                    this.wait(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i == total){
                    goRight = false;
                }
            }
        }else{
            for (int i = now; i >0 ; i--) {
                try {
                    this.wait(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(i == 0){
                    goRight = true;
                }
            }

        }
    }
    public void sendMsg(int now, boolean goRight){
        Bundle bundle = new Bundle();
        bundle.putInt("now", now);
        bundle.putBoolean("goRight", goRight);
        Message msg = new Message();
        msg.setData(bundle);
        msg.what = 1;

    }
}
