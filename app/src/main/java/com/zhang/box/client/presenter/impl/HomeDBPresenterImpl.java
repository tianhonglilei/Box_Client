package com.zhang.box.client.presenter.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zhang.box.client.presenter.HomeDBPresenter;
import com.zhang.box.db.DaoMaster;
import com.zhang.box.db.DaoSession;

/**
 * Created by lilei on 2017/10/30.
 */

public class HomeDBPresenterImpl implements HomeDBPresenter {


    public DaoMaster daoMaster;
    public DaoSession daoSession;
    public SQLiteDatabase db;


    @Override
    public void openDb(Context context) {

    }
}
