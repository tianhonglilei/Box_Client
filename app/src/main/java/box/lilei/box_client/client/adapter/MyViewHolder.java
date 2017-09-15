package box.lilei.box_client.client.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lilei on 2017/9/14.
 */

public class MyViewHolder {

    private  int mPosition = 0;
    private View mConvertView = null;
    private SparseArray<View> mViews;



    private MyViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mPosition = position;
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }
    public static MyViewHolder get(Context context, ViewGroup parent, int layoutId, int position, View convertView)
    {
        if(convertView == null)
        {
            return new MyViewHolder(context, parent, layoutId, position);
        }
        else
        {
            MyViewHolder holder = (MyViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }
    public <T extends  View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if(view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    public View getConvertView()
    {
        return mConvertView;
    }

}
