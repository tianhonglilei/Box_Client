package com.zhang.box.client.okhttp.listener;

/**
 * Created by lilei on 2017/10/13.
 * 比较线上线下文件大小监听
 */

public interface DisposeSizeListener extends DisposeDataListener{

    void difference();

    void same();

}
