package com.zhang.box.client.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import box.lilei.box_client.R;
import com.zhang.box.client.model.ADInfo;
import com.zhang.box.contants.Constants;

/**
 * Created by lilei on 2017/9/14.
 */

public class LvAdImgAdapter extends MyBaseAdapter<ADInfo> {


    public LvAdImgAdapter(Context context, List<ADInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }


    @Override
    protected void convert(ADInfo adInfo, MyViewHolder viewHolder, int position) {
        ImageView img = viewHolder.getView(R.id.adbanner_rv_img);
        File file = new File(Constants.DEMO_FILE_PATH + "/" + adInfo.getImgFileName());
//        if (adInfo.getAdType() == ADInfo.ADTYPE_IMG) {
//
//        } else if (adInfo.getAdType() == ADInfo.ADTYPE_VIDEO) {
//
//        }
        Glide.with(mContext)
                .load(file)
                .into(img);
    }


}
