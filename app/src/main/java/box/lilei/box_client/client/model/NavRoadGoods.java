package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/10/25.
 */

public class NavRoadGoods {

    private int roadGoodsId;
    private Goods goods;
    private RoadInfo roadInfo;

    public int getRoadGoodsId() {
        return roadGoodsId;
    }

    public void setRoadGoodsId(int roadGoodsId) {
        this.roadGoodsId = roadGoodsId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public RoadInfo getRoadInfo() {
        return roadInfo;
    }

    public void setRoadInfo(RoadInfo roadInfo) {
        this.roadInfo = roadInfo;
    }
}
