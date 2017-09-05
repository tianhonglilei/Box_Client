package box.lilei.box_client.client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import box.lilei.box_client.R;

/**
 * Created by lilei on 2017/9/4.
 * 右侧广告适配器
 */

public class RvRightAdapter extends RecyclerView.Adapter<RvRightAdapter.ViewHolder> {
    //数据集合
    private List<Integer> aDList;

    public RvRightAdapter(List<Integer> aDList) {
        this.aDList = aDList;
    }

    /**
     * 更新轮播中广告的数据
     *
     * @param aDList
     */
    public void updateData(List<Integer> aDList) {
        this.aDList = aDList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化展示View
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_adbanner_r_item, parent, false);
        //实例化ViewHolder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据
        holder.imageView.setImageResource(aDList.get(position));
    }


    @Override
    public int getItemCount() {
        return aDList == null ? 0 : aDList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.adbanner_r_item_img);
        }
    }

}
