package box.lilei.box_client.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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

    public View saleStateView;

    @Override
    protected void convert(Goods goods, MyViewHolder viewHolder,int position) {
        ((ImageView) viewHolder.getView(R.id.adbanner_b_item_img)).setImageResource(R.drawable.goods_glc_dft);
        if(position%2==0){
            //促销
            goodsSaleState(viewHolder,1);

            ((ImageView)viewHolder.getView(R.id.adbanner_b_item_img_wd)).setImageResource(R.mipmap.logo_cold);
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorHomeGoodsBg));

        }else{
            //售罄
            goodsSaleState(viewHolder,2);
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(Color.WHITE);
            ((ImageView)viewHolder.getView(R.id.adbanner_b_item_img_wd)).setImageResource(R.mipmap.logo_hot);
            viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorHomeGoodsBgWhite));
        }
//        ((TextView)viewHolder.getView(R.id.adbanner_b_item_price)).setText(goods.getGoodsName());
    }

    public void goodsSaleState(MyViewHolder viewHolder, int state){
        saleStateView = viewHolder.getView(R.id.adbanner_b_item_rl_sale_state);
        if(state == 1) {
            //删除线
            ((TextView)viewHolder.getView(R.id.adbanner_b_item_price)).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_sale_state_discount);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale_out).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_discount_rmb).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_discount_price).setVisibility(View.VISIBLE);
        }else if(state == 2){
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_sale_state_off);
            ((TextView)viewHolder.getView(R.id.adbanner_b_item_price)).getPaint().setFlags(0);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale_out).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_discount_rmb).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_discount_price).setVisibility(View.INVISIBLE);
        }else{
            saleStateView.setVisibility(View.INVISIBLE);
        }
    }

}
