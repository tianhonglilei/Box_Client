package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/10/25.
 * 货道信息
 */

public class RoadInfo {

    //从机器接口获取货道状态
    public static final int ROAD_STATE_NORMAL = 0;
    public static final int ROAD_STATE_NULL = 1;
    public static final int ROAD_STATE_ERROR = 9;

    //打开关闭货道
    public static final int ROAD_OPEN = 0;
    public static final int ROAD_CLOSE = 1;

    private int roadIndex;
    private int roadState;
    private int roadOpen;
    private int roadMaxNum;
    private int roadNowNum;

    public RoadInfo(){}

    public RoadInfo(int roadIndex, int roadState, int roadOpen, int roadMaxNum, int roadNowNum) {
        this.roadIndex = roadIndex;
        this.roadState = roadState;
        this.roadOpen = roadOpen;
        this.roadMaxNum = roadMaxNum;
        this.roadNowNum = roadNowNum;
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

    public int getRoadOpen() {
        return roadOpen;
    }

    public void setRoadOpen(int roadOpen) {
        this.roadOpen = roadOpen;
    }

    public int getRoadIndex() {
        return roadIndex;
    }

    public void setRoadIndex(int roadIndex) {
        this.roadIndex = roadIndex;
    }

    public int getRoadState() {
        return roadState;
    }

    public void setRoadState(int roadState) {
        this.roadState = roadState;
    }
}
