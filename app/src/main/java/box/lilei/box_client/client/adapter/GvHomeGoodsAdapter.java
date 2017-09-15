package box.lilei.box_client.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.Goods;

/**
 * Created by lilei on 2017/9/14.
 */

public class GvHomeGoodsAdapter extends MyBaseAdapter<Goods> {

    Context mContext;

    public GvHomeGoodsAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    @Override
    protected void convert(Goods goods, MyViewHolder viewHolder,int position) {
        ((ImageView) viewHolder.getView(R.id.adbanner_b_item_img)).setImageResource(R.drawable.goods_glc_dft);
        if(position%2==0){
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorHomeGoodsBg));
        }else{
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(Color.WHITE);
        }
//        ((TextView)viewHolder.getView(R.id.adbanner_b_item_price)).setText(goods.getGoodsName());
    }
}
