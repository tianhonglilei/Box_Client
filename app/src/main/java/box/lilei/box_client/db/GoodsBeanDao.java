package box.lilei.box_client.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GOODS_BEAN".
*/
public class GoodsBeanDao extends AbstractDao<GoodsBean, Long> {

    public static final String TABLENAME = "GOODS_BEAN";

    /**
     * Properties of entity GoodsBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Type = new Property(2, int.class, "type", false, "TYPE");
        public final static Property Small_img = new Property(3, String.class, "small_img", false, "SMALL_IMG");
        public final static Property Big_img = new Property(4, String.class, "big_img", false, "BIG_IMG");
        public final static Property No_pro_img = new Property(5, String.class, "no_pro_img", false, "NO_PRO_IMG");
    }

    private DaoSession daoSession;


    public GoodsBeanDao(DaoConfig config) {
        super(config);
    }
    
    public GoodsBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GOODS_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"TYPE\" INTEGER NOT NULL ," + // 2: type
                "\"SMALL_IMG\" TEXT," + // 3: small_img
                "\"BIG_IMG\" TEXT," + // 4: big_img
                "\"NO_PRO_IMG\" TEXT);"); // 5: no_pro_img
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GOODS_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GoodsBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getType());
 
        String small_img = entity.getSmall_img();
        if (small_img != null) {
            stmt.bindString(4, small_img);
        }
 
        String big_img = entity.getBig_img();
        if (big_img != null) {
            stmt.bindString(5, big_img);
        }
 
        String no_pro_img = entity.getNo_pro_img();
        if (no_pro_img != null) {
            stmt.bindString(6, no_pro_img);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GoodsBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getType());
 
        String small_img = entity.getSmall_img();
        if (small_img != null) {
            stmt.bindString(4, small_img);
        }
 
        String big_img = entity.getBig_img();
        if (big_img != null) {
            stmt.bindString(5, big_img);
        }
 
        String no_pro_img = entity.getNo_pro_img();
        if (no_pro_img != null) {
            stmt.bindString(6, no_pro_img);
        }
    }

    @Override
    protected final void attachEntity(GoodsBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GoodsBean readEntity(Cursor cursor, int offset) {
        GoodsBean entity = new GoodsBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getInt(offset + 2), // type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // small_img
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // big_img
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // no_pro_img
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GoodsBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setType(cursor.getInt(offset + 2));
        entity.setSmall_img(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBig_img(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNo_pro_img(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GoodsBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GoodsBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GoodsBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}