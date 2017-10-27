package box.lilei.box_client.manager.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.adapter.MyBaseAdapter;
import box.lilei.box_client.client.adapter.MyViewHolder;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.NavRoadGoods;

/**
 * Created by lilei on 2017/10/25.
 */

public class NavGoodsAdapter extends MyBaseAdapter<NavRoadGoods> {
    public NavGoodsAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(NavRoadGoods navRoadGoods, MyViewHolder viewHolder, int position) {
        ((TextView)viewHolder.getView(R.id.nav_goods_gv_item_road_num)).setText(""+navRoadGoods.getRoadInfo().getRoadIndex());
        ((TextView)viewHolder.getView(R.id.nav_goods_gv_item_max_num)).setText(""+navRoadGoods.getRoadInfo().getRoadMaxNum());
        ((TextView)viewHolder.getView(R.id.nav_goods_gv_item_now_num)).setText(""+navRoadGoods.getRoadInfo().getRoadNowNum());
        ((TextView)viewHolder.getView(R.id.nav_goods_gv_item_name)).setText(""+navRoadGoods.getGoods().getGoodsName()+"("+navRoadGoods.getGoods().getGoodsTaste()+")");
        ((ImageView)viewHolder.getView(R.id.nav_goods_gv_item_img)).setImageResource(R.mipmap.goods_xuebi);
    }
}
