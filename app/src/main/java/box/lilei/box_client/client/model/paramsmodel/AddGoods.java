package box.lilei.box_client.client.model.paramsmodel;

/**
 * Created by lilei on 2017/11/16.
 * 补货参数实体
 */

public class AddGoods {

//    machineid 机器号
//    hid       货道号
//    huodao_num 当前数量
//    hgid      货柜编号
//    pid       商品号
//    huodao_max 货道最大数

    private String machineid;
    private String hid;
    private String huodao_num;
    private String hgid;
    private String pid;
    private String huodao_max;

    public AddGoods(){

    }

    public AddGoods(String machineid, String hid, String huodao_num, String hgid, String pid, String huodao_max) {
        this.machineid = machineid;
        this.hid = hid;
        this.huodao_num = huodao_num;
        this.hgid = hgid;
        this.pid = pid;
        this.huodao_max = huodao_max;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getHuodao_num() {
        return huodao_num;
    }

    public void setHuodao_num(String huodao_num) {
        this.huodao_num = huodao_num;
    }

    public String getHgid() {
        return hgid;
    }

    public void setHgid(String hgid) {
        this.hgid = hgid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHuodao_max() {
        return huodao_max;
    }

    public void setHuodao_max(String huodao_max) {
        this.huodao_max = huodao_max;
    }
}
