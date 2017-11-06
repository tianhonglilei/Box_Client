package box.lilei.box_client.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by lilei on 2017/11/5.
 */
@Entity
public class GoodsBean {

    /**
     * id : 105
     * name : 王老吉
     * type : 1
     * small_img : img20170516171609_100.png
     * big_img : imgbig20170516171609_100.png
     * no_pro_img : imggray20170516171609_100.png
     */

    @Id
    private Long id;
    //名称
    private String name;
    //类型
    private int type;
    //小图
    private String small_img;
    //大图
    private String big_img;
    //售罄图
    private String no_pro_img;
    @ToMany(referencedJoinProperty = "id")
    private List<PercentBean> percentBeanList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 521530994)
    private transient GoodsBeanDao myDao;

    @Generated(hash = 331017321)
    public GoodsBean(Long id, String name, int type, String small_img,
            String big_img, String no_pro_img) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.small_img = small_img;
        this.big_img = big_img;
        this.no_pro_img = no_pro_img;
    }
    @Generated(hash = 1806305570)
    public GoodsBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getSmall_img() {
        return this.small_img;
    }
    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }
    public String getBig_img() {
        return this.big_img;
    }
    public void setBig_img(String big_img) {
        this.big_img = big_img;
    }
    public String getNo_pro_img() {
        return this.no_pro_img;
    }
    public void setNo_pro_img(String no_pro_img) {
        this.no_pro_img = no_pro_img;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1174371377)
    public List<PercentBean> getPercentBeanList() {
        if (percentBeanList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PercentBeanDao targetDao = daoSession.getPercentBeanDao();
            List<PercentBean> percentBeanListNew = targetDao
                    ._queryGoodsBean_PercentBeanList(id);
            synchronized (this) {
                if (percentBeanList == null) {
                    percentBeanList = percentBeanListNew;
                }
            }
        }
        return percentBeanList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1660621809)
    public synchronized void resetPercentBeanList() {
        percentBeanList = null;
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
    @Generated(hash = 2062802439)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGoodsBeanDao() : null;
    }


}
