package box.lilei.box_client.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;

import org.greenrobot.greendao.DaoException;

/**
 * Created by lilei on 2017/11/5.
 * 货道数据库对象
 */
@Entity
public class RoadBean {

    /**
     * hid : 2
     * pid : 230
     * main : 0
     * max : 21
     * price : 250
     * weixin : 250
     * zhifubao : 250
     * huogui_num : 11
     */
    @Id
    private Long id;
    //货道id
    private Long hid;
    //商品id
    private Long pid;
    @ToOne(joinProperty = "pid")
    private GoodsBean goodsBean;
    //是否主页
    private int main;
    //最大数
    private int max;
    //价格
    private int price;
    //微信
    private int weixin;
    //支付宝
    private int zhifubao;
    //货柜类型
    private String huogui_num;
    //当前数量
    private int huodao_num;
    //温度状态
    private int tempState;
    //销售状态
    private int saleState;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 644143118)
    private transient RoadBeanDao myDao;

    @Generated(hash = 282021072)
    public RoadBean(Long id, Long hid, Long pid, int main, int max, int price,
            int weixin, int zhifubao, String huogui_num, int huodao_num,
            int tempState, int saleState) {
        this.id = id;
        this.hid = hid;
        this.pid = pid;
        this.main = main;
        this.max = max;
        this.price = price;
        this.weixin = weixin;
        this.zhifubao = zhifubao;
        this.huogui_num = huogui_num;
        this.huodao_num = huodao_num;
        this.tempState = tempState;
        this.saleState = saleState;
    }
    @Generated(hash = 2100317740)
    public RoadBean() {
    }
    @Generated(hash = 108351054)
    private transient Long goodsBean__resolvedKey;

    public Long getHid() {
        return this.hid;
    }
    public void setHid(Long hid) {
        this.hid = hid;
    }
    public Long getPid() {
        return this.pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
    public int getMain() {
        return this.main;
    }
    public void setMain(int main) {
        this.main = main;
    }
    public int getMax() {
        return this.max;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getWeixin() {
        return this.weixin;
    }
    public void setWeixin(int weixin) {
        this.weixin = weixin;
    }
    public int getZhifubao() {
        return this.zhifubao;
    }
    public void setZhifubao(int zhifubao) {
        this.zhifubao = zhifubao;
    }
    public String getHuogui_num() {
        return this.huogui_num;
    }
    public void setHuogui_num(String huogui_num) {
        this.huogui_num = huogui_num;
    }
    public int getHuodao_num() {
        return this.huodao_num;
    }
    public void setHuodao_num(int huodao_num) {
        this.huodao_num = huodao_num;
    }
    public int getTempState() {
        return this.tempState;
    }
    public void setTempState(int tempState) {
        this.tempState = tempState;
    }
    public int getSaleState() {
        return this.saleState;
    }
    public void setSaleState(int saleState) {
        this.saleState = saleState;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1201558024)
    public GoodsBean getGoodsBean() {
        Long __key = this.pid;
        if (goodsBean__resolvedKey == null
                || !goodsBean__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GoodsBeanDao targetDao = daoSession.getGoodsBeanDao();
            GoodsBean goodsBeanNew = targetDao.load(__key);
            synchronized (this) {
                goodsBean = goodsBeanNew;
                goodsBean__resolvedKey = __key;
            }
        }
        return goodsBean;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 958998985)
    public void setGoodsBean(GoodsBean goodsBean) {
        synchronized (this) {
            this.goodsBean = goodsBean;
            pid = goodsBean == null ? null : goodsBean.getId();
            goodsBean__resolvedKey = pid;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1792981372)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRoadBeanDao() : null;
    }




}
