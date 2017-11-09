package box.lilei.box_client.client.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.Goods;
import box.lilei.box_client.client.model.RoadGoods;
import box.lilei.box_client.contants.Constants;

/**
 * Created by lilei on 2017/9/14.
 */

public class GvHomeGoodsAdapter extends MyBaseAdapter<RoadGoods> {

    Context mContext;

    public GvHomeGoodsAdapter(Context context, List datas, int layoutId) {
        super(context, datas, layoutId);
        mContext = context;
    }

    public View saleStateView;


    @Override
    protected void convert(RoadGoods roadGoods, MyViewHolder viewHolder, int position) {
        Goods goods = roadGoods.getGoods();
        ImageView goodsImg = viewHolder.getView(R.id.adbanner_b_item_img);
        ImageView wdImg = viewHolder.getView(R.id.adbanner_b_item_img_wd);
        saleStateView = viewHolder.getView(R.id.adbanner_b_item_rl_sale_state);
        TextView price = viewHolder.getView(R.id.adbanner_b_item_price);
        price.setText(""+goods.getGoodsPrice());
        int state = goods.getGoodsSaleState();
        int wd = goods.getGoodsWd();
        if(state == Goods.SALE_STATE_DISCOUNT) {
            //删除线
            ((TextView)viewHolder.getView(R.id.adbanner_b_item_price)).getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_sale_state_discount);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale_out).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_discount_rmb).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_discount_price).setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(new File(Constants.DEMO_FILE_PATH+"/"+goods.getGoodsBImgName()))
                    .into(goodsImg);
        }else if(state == Goods.SALE_STATE_OUT){
            saleStateView.setVisibility(View.VISIBLE);
            saleStateView.setBackgroundResource(R.drawable.shape_sale_state_off);
            ((TextView)viewHolder.getView(R.id.adbanner_b_item_price)).getPaint().setFlags(0);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale_out).setVisibility(View.VISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_sale).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_discount_rmb).setVisibility(View.INVISIBLE);
            viewHolder.getView(R.id.adbanner_b_item_txt_discount_price).setVisibility(View.INVISIBLE);
            Glide.with(mContext)
                    .load(new File(Constants.DEMO_FILE_PATH+"/"+goods.getGoodsOutImgName()))
                    .into(goodsImg);
        }else if (state == Goods.SALE_STATE_NORMAL){
            saleStateView.setVisibility(View.INVISIBLE);
            Glide.with(mContext)
                    .load(new File(Constants.DEMO_FILE_PATH+"/"+goods.getGoodsBImgName()))
                    .into(goodsImg);
        }
        switch (wd){
            case Goods.GOODS_WD_COLD:
                viewHolder.getView(R.id.adbanner_b_item_img_wd).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.mipmap.logo_cold).into(wdImg);
                break;
            case Goods.GOODS_WD_HOT:
                viewHolder.getView(R.id.adbanner_b_item_img_wd).setVisibility(View.VISIBLE);
                Glide.with(mContext).load(R.mipmap.logo_hot).into(wdImg);
                break;
            case Goods.GOODS_WD_NORMAL:
                viewHolder.getView(R.id.adbanner_b_item_img_wd).setVisibility(View.INVISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) price.getLayoutParams();
                params.removeRule(RelativeLayout.ALIGN_RIGHT);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                price.setLayoutParams(params);
                break;
            default:
                break;
        }

        viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorHomeGoodsBg));
        viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(Color.WHITE);
        viewHolder.getView(R.id.adbanner_b_item_bg_rl).setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorHomeGoodsBgWhite));



    }
}
