package box.lilei.box_client.service.presenter;

/**
 * Created by lilei on 2017/12/4.
 */

public interface HeartPresenter {

    /**
     * 发送心跳
     */
    void sendHeartInfo();

    /**
     * 从机器获取心跳信息
     */
    void getHeartInfo();

}
