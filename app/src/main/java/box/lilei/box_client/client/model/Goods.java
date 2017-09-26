package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/9/4.
 */

public class Goods {

    //销售状态0正常1打折2售罄
    public static final int SALESTATE_NORMAL = 0;
    public static final int SALESTATE_DISCOUNT = 1;
    public static final int SALESTATE_OUT = 2;

    //商品类型0饮料1零食
    public static final int GOODS_TYPE_DRINK = 0;
    public static final int GOODS_TYPE_FOOD = 1;

    //商品温度0正常1制冷2制热
    public static final int GOODS_WD_NORMAL = 0;
    public static final int GOODS_WD_COLD = 1;
    public static final int GOODS_WD_HOT = 2;


    //商品ID
    private int goodsId;

    //商品名称
    private String goodsName;

    //商品价格
    private double goodsPrice;

    //商品类型
    private int goodsType;

    //商品温度
    private int goodsWd;

    //商品口味
    private String goodsTaste;

    //商品销售状态
    private int goodsSaleState;

    //商品描述
    private String goodsMemo;

    //商品打折价格
    private double goodsDiscountPrice;

    //商品图片路径
    private String goodsImgUrl;

    public Goods(){}

    public Goods(int goodsId, String goodsName, double goodsPrice, int goodsType, int goodsWd, int goodsSaleState, double goodsDiscountPrice, String goodsImgUrl) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsType = goodsType;
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

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
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

    public String getGoodsTaste() {
        return goodsTaste;
    }

    public void setGoodsTaste(String goodsTaste) {
        this.goodsTaste = goodsTaste;
    }

    public String getGoodsMemo() {
        return goodsMemo;
    }

    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo;
    }
}
