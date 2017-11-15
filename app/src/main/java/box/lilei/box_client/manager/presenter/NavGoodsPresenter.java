package box.lilei.box_client.manager.presenter;

import android.widget.GridView;

/**
 * Created by lilei on 2017/11/14.
 */

public interface NavGoodsPresenter {

    void initGoodsGridView(GridView gridView);

    void checkGoodsData(GridView gridView,String boxType);

}
