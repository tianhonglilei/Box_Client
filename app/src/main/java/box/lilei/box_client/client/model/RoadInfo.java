package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/10/25.
 */

public class RoadInfo {

    public static final int ROAD_STATE_NORMAL = 0;
    public static final int ROAD_STATE_NULL = 1;
    public static final int ROAD_STATE_ERROR = 9;

    private int roadIndex;
    private int roadState;

    public RoadInfo(){}

    public RoadInfo(int roadIndex, int roadState) {
        this.roadIndex = roadIndex;
        this.roadState = roadState;
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
