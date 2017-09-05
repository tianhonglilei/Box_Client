package box.lilei.box_client.client.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import box.lilei.box_client.R;

/**
 * Created by lilei on 2017/9/4.
 */

public class RvBottomAdapter extends RecyclerView.Adapter<RvBottomAdapter.ViewHolder> {

    private List<Integer> goodsList;

    public RvBottomAdapter(List<Integer> goodsList) {
        this.goodsList = goodsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_adbanner_b_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imgGoods.setImageResource(R.mipmap.ic_launcher);
        holder.txtName.setText("第" + (position + 1) + "商品");
    }

    @Override
    public int getItemCount() {
        return goodsList == null ? 0 : goodsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGoods;
        TextView txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            imgGoods = (ImageView) itemView.findViewById(R.id.adbanner_b_item_img);
            txtName = (TextView) itemView.findViewById(R.id.adbanner_b_item_title);
        }
    }


}
