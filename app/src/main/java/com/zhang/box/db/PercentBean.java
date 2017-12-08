package com.zhang.box.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by lilei on 2017/11/5.
 */
@Entity
public class PercentBean {


    /**
     * id : 105
     * material : 脂肪
     * hundred : 0g
     * percentage : 0%
     */
    @Id(autoincrement = true)
    private Long mainId;

    //商品id
    private Long id;
    //成分名称
    private String material;
    //成分含量
    private String hundred;
    //百分比
    private String percentage;
    @Generated(hash = 951030362)
    public PercentBean(Long mainId, Long id, String material, String hundred,
            String percentage) {
        this.mainId = mainId;
        this.id = id;
        this.material = material;
        this.hundred = hundred;
        this.percentage = percentage;
    }
    @Generated(hash = 318108856)
    public PercentBean() {
    }
    public Long getMainId() {
        return this.mainId;
    }
    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMaterial() {
        return this.material;
    }
    public void setMaterial(String material) {
        this.material = material;
    }
    public String getHundred() {
        return this.hundred;
    }
    public void setHundred(String hundred) {
        this.hundred = hundred;
    }
    public String getPercentage() {
        return this.percentage;
    }
    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }


}
