package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/9/4.
 */

public class Goods {

    //销售状态0正常1打折2售罄
    public static final int SALE_STATE_NORMAL = 0;
    public static final int SALE_STATE_DISCOUNT = 1;
    public static final int SALE_STATE_OUT = 2;

    //商品类型0饮料1零食
    public static final int GOODS_TYPE_DRINK = 1;
    public static final int GOODS_TYPE_FOOD = 2;

    //商品温度0正常1制冷2制热
    public static final int GOODS_WD_NORMAL = 0;
    public static final int GOODS_WD_COLD = 1;
    public static final int GOODS_WD_HOT = 2;


    //商品ID
    private Long goodsId;

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

    //商品小图名称
    private String goodsSImgName;

    //商品大图展示
    private String goodsBImgName;

    //商品售罄图
    private String goodsOutImgName;


    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
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

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsWd() {
        return goodsWd;
    }

    public void setGoodsWd(int goodsWd) {
        this.goodsWd = goodsWd;
    }

    public String getGoodsTaste() {
        return goodsTaste;
    }

    public void setGoodsTaste(String goodsTaste) {
        this.goodsTaste = goodsTaste;
    }

    public int getGoodsSaleState() {
        return goodsSaleState;
    }

    public void setGoodsSaleState(int goodsSaleState) {
        this.goodsSaleState = goodsSaleState;
    }

    public String getGoodsMemo() {
        return goodsMemo;
    }

    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo;
    }

    public double getGoodsDiscountPrice() {
        return goodsDiscountPrice;
    }

    public void setGoodsDiscountPrice(double goodsDiscountPrice) {
        this.goodsDiscountPrice = goodsDiscountPrice;
    }

    public String getGoodsSImgName() {
        return goodsSImgName;
    }

    public void setGoodsSImgName(String goodsSImgName) {
        this.goodsSImgName = goodsSImgName;
    }

    public String getGoodsBImgName() {
        return goodsBImgName;
    }

    public void setGoodsBImgName(String goodsBImgName) {
        this.goodsBImgName = goodsBImgName;
    }

    public String getGoodsOutImgName() {
        return goodsOutImgName;
    }

    public void setGoodsOutImgName(String goodsOutImgName) {
        this.goodsOutImgName = goodsOutImgName;
    }
}
