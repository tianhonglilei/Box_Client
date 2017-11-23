package box.lilei.box_client.client.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lilei on 2017/10/25.
 * 货道信息
 */

public class RoadInfo implements Parcelable {

    //从机器接口获取货道状态
    public static final int ROAD_STATE_NORMAL = 0;
    public static final int ROAD_STATE_NULL = 1;
    public static final int ROAD_STATE_ERROR = 2;
    public static final int ROAD_STATE_DATA_ERROR = 9;

    //打开关闭货道
    public static final int ROAD_OPEN = 0;
    public static final int ROAD_CLOSE = 1;

    public static final int BOX_TYPE_DRINK = 11;
    public static final int BOX_TYPE_FOOD = 9;


    private Long roadIndex;
    private int roadState;
    private int roadOpen;
    private int roadMaxNum;
    private int roadNowNum;
    //货柜类型
    private String roadBoxType;


    public RoadInfo(){}

    public RoadInfo(Long roadIndex, int roadState, int roadOpen, int roadMaxNum, int roadNowNum, String roadBoxType) {
        this.roadIndex = roadIndex;
        this.roadState = roadState;
        this.roadOpen = roadOpen;
        this.roadMaxNum = roadMaxNum;
        this.roadNowNum = roadNowNum;
        this.roadBoxType = roadBoxType;
    }

    public String getRoadBoxType() {
        return roadBoxType;
    }

    public void setRoadBoxType(String roadBoxType) {
        this.roadBoxType = roadBoxType;
    }

    public Long getRoadIndex() {
        return roadIndex;
    }

    public void setRoadIndex(Long roadIndex) {
        this.roadIndex = roadIndex;
    }

    public int getRoadState() {
        return roadState;
    }

    public void setRoadState(int roadState) {
        this.roadState = roadState;
    }

    public int getRoadOpen() {
        return roadOpen;
    }

    public void setRoadOpen(int roadOpen) {
        this.roadOpen = roadOpen;
    }

    public int getRoadMaxNum() {
        return roadMaxNum;
    }

    public void setRoadMaxNum(int roadMaxNum) {
        this.roadMaxNum = roadMaxNum;
    }

    public int getRoadNowNum() {
        return roadNowNum;
    }

    public void setRoadNowNum(int roadNowNum) {
        this.roadNowNum = roadNowNum;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.roadIndex);
        dest.writeInt(this.roadState);
        dest.writeInt(this.roadOpen);
        dest.writeInt(this.roadMaxNum);
        dest.writeInt(this.roadNowNum);
        dest.writeString(this.roadBoxType);
    }

    protected RoadInfo(Parcel in) {
        this.roadIndex = (Long) in.readValue(Long.class.getClassLoader());
        this.roadState = in.readInt();
        this.roadOpen = in.readInt();
        this.roadMaxNum = in.readInt();
        this.roadNowNum = in.readInt();
        this.roadBoxType = in.readString();
    }

    public static final Creator<RoadInfo> CREATOR = new Creator<RoadInfo>() {
        @Override
        public RoadInfo createFromParcel(Parcel source) {
            return new RoadInfo(source);
        }

        @Override
        public RoadInfo[] newArray(int size) {
            return new RoadInfo[size];
        }
    };
}
