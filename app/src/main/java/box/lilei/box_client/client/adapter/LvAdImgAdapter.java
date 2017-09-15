package box.lilei.box_client.client.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.VideoView;

import java.util.List;

import box.lilei.box_client.R;
import box.lilei.box_client.client.model.ADInfo;

/**
 * Created by lilei on 2017/9/14.
 */

public class LvAdImgAdapter extends MyBaseAdapter<ADInfo> {


    public LvAdImgAdapter(Context context, List<ADInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(ADInfo adInfo, MyViewHolder viewHolder, int position) {
        if (adInfo.getAdType() == ADInfo.ADTYPE_IMG) {
            ((ImageView) viewHolder.getView(R.id.adbanner_rv_img)).setImageResource(R.drawable.ad_test_img1_s);

        } else {
            ((ImageView) viewHolder.getView(R.id.adbanner_rv_img)).setImageResource(R.drawable.ad_test_video1_s);
        }
    }


}
