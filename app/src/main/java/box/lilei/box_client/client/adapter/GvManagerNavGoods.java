package box.lilei.box_client.client.adapter;

import android.content.Context;

import java.util.List;

import box.lilei.box_client.client.model.NavRoadGoods;

/**
 * Created by lilei on 2017/10/25.
 */

public class GvManagerNavGoods extends MyBaseAdapter<NavRoadGoods> {
    public GvManagerNavGoods(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(NavRoadGoods navRoadGoods, MyViewHolder viewHolder, int position) {

    }
}
