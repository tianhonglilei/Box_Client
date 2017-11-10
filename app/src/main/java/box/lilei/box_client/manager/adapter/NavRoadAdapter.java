package box.lilei.box_client.manager.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.MyBaseAdapter;
import box.lilei.box_client.client.adapter.MyViewHolder;
import box.lilei.box_client.client.model.RoadGoods;

/**
 * Created by lilei on 2017/10/25.
 */

public class NavRoadAdapter extends MyBaseAdapter<RoadGoods> {
    public NavRoadAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        ((TextView)viewHolder.getView(R.id.nav_road_gv_item_road_num)).setText(""+ roadGoods.getRoadInfo().getRoadIndex());
        ((ImageView)viewHolder.getView(R.id.nav_road_gv_item_img)).setImageResource(R.mipmap.goods_xuebi);
    }
}