package box.lilei.box_client.client.model;

/**
 * Created by lilei on 2017/9/5.
 * 广告信息类
 */

public class ADInfo {
    //图片广告
    public static final int ADTYPE_IMG = 1;
    //视频广告
    public static final int ADTYPE_VIDEO = 2;

    private int adId;
    private int adType = ADTYPE_IMG;
    private String adTitle;
    private String adUrl;
    private String adMemo;

    public ADInfo(){}

    public ADInfo(int adId, int adType, String adTitle, String adUrl, String adMemo) {
        this.adId = adId;
        this.adType = adType;
        this.adTitle = adTitle;
        this.adUrl = adUrl;
        this.adMemo = adMemo;
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdMemo() {
        return adMemo;
    }

    public void setAdMemo(String adMemo) {
        this.adMemo = adMemo;
    }




}
