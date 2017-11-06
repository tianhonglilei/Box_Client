package box.lilei.box_client.client.presenter;

import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.VideoView;

/**
 * Created by lilei on 2017/9/5.
 * 处理广告页面业务
 */

public interface ADBannerPresenter {

    /**
     * 初始化广告数据
     */
    void initAdData(ListView adbannerAdLv);

    /**
     * 初始化商品数据
     */
    void initGoodsData(GridView adbannerGoodsGv);

    /**
     * 保存广告数据
     */
    void saveAdData();

    /**
     * 保存商品数据
     */
    void saveGoodsData();

    /**
     * 开始滚动
     */
//    void startAutoGoodsScroll();

    //通过网络获取广告信息
    void getAdInfoFromUrl(String imei);

}
