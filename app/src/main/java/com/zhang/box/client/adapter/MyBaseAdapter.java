package com.zhang.box.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lilei on 2017/9/14.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> mDatas = null;
    protected Context mContext = null;
    protected int layoutId;

    public MyBaseAdapter(Context context, List<T> datas, int layoutId) {
        mDatas = datas;
        mContext = context;
        this.layoutId = layoutId;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder = MyViewHolder.get(mContext, parent, layoutId, position, convertView);
        convert(getItem(position), viewHolder, position);
        return viewHolder.getConvertView();
    }

    protected abstract void convert(T t, MyViewHolder viewHolder, int position);

}
