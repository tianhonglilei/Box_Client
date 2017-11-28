package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/11/12.
 */

public class OrderInfo {

    private String orderId;
    private boolean payState = false;
    private int outGoodsNum;
    private boolean cancel;

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public int getOutGoodsNum() {
        return outGoodsNum;
    }

    public void setOutGoodsNum(int outGoodsNum) {
        this.outGoodsNum = outGoodsNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public boolean isPayState() {
        return payState;
    }

    public void setPayState(boolean payState) {
        this.payState = payState;
    }
}
