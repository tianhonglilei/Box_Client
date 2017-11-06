package box.lilei.box_client.client.presenter.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import box.lilei.box_client.client.presenter.HomeDBPresenter;
import box.lilei.box_client.db.DaoMaster;
import box.lilei.box_client.db.DaoSession;

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
