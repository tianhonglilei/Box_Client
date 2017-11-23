package box.lilei.box_client.box;

import com.avm.serialport_142.MainHandler;

import box.lilei.box_client.client.model.RoadInfo;

/**
 * Created by lilei on 2017/11/22.
 */

public class BoxAction {

    /**
     * 出货
     * @param boxType   货柜类型
     * @param roadIndex 货道编号
     * @return
     */
    public static void outGoods(String boxType,String roadIndex){
        MainHandler.noticeAvmOutGoods("","");
    }

    /**
     * 货道状态
     * @param boxType
     * @param roadIndex
     * @return
     */
    public static int getRoadState(String boxType,String roadIndex){
        String result = MainHandler.getGoodsInfo(Integer.parseInt(boxType),Integer.parseInt(roadIndex));
        int str = Integer.parseInt(result.substring(0,1));
        return str;
    }

   public static String getBoxId(){
        return MainHandler.getMachNo();
   }

}
