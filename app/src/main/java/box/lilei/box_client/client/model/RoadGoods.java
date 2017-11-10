package box.lilei.box_client.client.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lilei on 2017/10/25.
 */

public class RoadGoods implements Parcelable {

    private int roadGoodsId;
    private Goods goods;
    private RoadInfo roadInfo;

    public RoadGoods(int roadGoodsId, Goods goods, RoadInfo roadInfo) {
        this.roadGoodsId = roadGoodsId;
        this.goods = goods;
        this.roadInfo = roadInfo;
    }
    public RoadGoods(){}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.roadGoodsId);
        dest.writeParcelable(this.goods, flags);
        dest.writeParcelable(this.roadInfo, flags);
    }

    protected RoadGoods(Parcel in) {
        this.roadGoodsId = in.readInt();
        this.goods = in.readParcelable(Goods.class.getClassLoader());
        this.roadInfo = in.readParcelable(RoadInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<RoadGoods> CREATOR = new Parcelable.Creator<RoadGoods>() {
        @Override
        public RoadGoods createFromParcel(Parcel source) {
            return new RoadGoods(source);
        }

        @Override
        public RoadGoods[] newArray(int size) {
            return new RoadGoods[size];
        }
    };
}
