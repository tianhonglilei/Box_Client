package box.lilei.box_client.client.okhttp.listener;

/**
 * Created by lilei on 2017/10/13.
 */

public interface OkHttpDisposeListener {

    void onSuccess(Object responseObject);

    void onFail(Object errorObject);


}
