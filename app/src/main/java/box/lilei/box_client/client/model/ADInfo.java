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

    private Long adId;
    private int adType = ADTYPE_IMG;
    private String imgFileName;
    private String videoFileName;

    public ADInfo(){}

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getVideoFileName() {
        return videoFileName;
    }

    public void setVideoFileName(String videoFileName) {
        this.videoFileName = videoFileName;
    }
}
