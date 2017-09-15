package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/9/4.
 */

public class Goods {

    //商品ID
    private int goodsId;

    //商品名称
    private String goodsName;

    //商品原价格
    private double goodsPrice;

    //商品温度
    private int goodsWd;

    //商品销售状态
    private int goodsSaleState;

    //商品打折价格
    private double goodsDiscountPrice;

    //商品图片路径
    private String goodsImgUrl;

    public Goods(){}

    public Goods(int goodsId, String goodsName, double goodsPrice, int goodsWd, int goodsSaleState, double goodsDiscountPrice, String goodsImgUrl) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsWd = goodsWd;
        this.goodsSaleState = goodsSaleState;
        this.goodsDiscountPrice = goodsDiscountPrice;
        this.goodsImgUrl = goodsImgUrl;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getGoodsWd() {
        return goodsWd;
    }

    public void setGoodsWd(int goodsWd) {
        this.goodsWd = goodsWd;
    }

    public int getGoodsSaleState() {
        return goodsSaleState;
    }

    public void setGoodsSaleState(int goodsSaleState) {
        this.goodsSaleState = goodsSaleState;
    }

    public double getGoodsDiscountPrice() {
        return goodsDiscountPrice;
    }

    public void setGoodsDiscountPrice(double goodsDiscountPrice) {
        this.goodsDiscountPrice = goodsDiscountPrice;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }
}
