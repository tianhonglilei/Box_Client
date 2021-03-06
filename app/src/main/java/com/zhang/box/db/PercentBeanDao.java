package com.zhang.box.db;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PERCENT_BEAN".
*/
public class PercentBeanDao extends AbstractDao<PercentBean, Long> {

    public static final String TABLENAME = "PERCENT_BEAN";

    /**
     * Properties of entity PercentBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property MainId = new Property(0, Long.class, "mainId", true, "_id");
        public final static Property Id = new Property(1, Long.class, "id", false, "ID");
        public final static Property Material = new Property(2, String.class, "material", false, "MATERIAL");
        public final static Property Hundred = new Property(3, String.class, "hundred", false, "HUNDRED");
        public final static Property Percentage = new Property(4, String.class, "percentage", false, "PERCENTAGE");
    }

    private Query<PercentBean> goodsBean_PercentBeanListQuery;

    public PercentBeanDao(DaoConfig config) {
        super(config);
    }
    
    public PercentBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PERCENT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: mainId
                "\"ID\" INTEGER," + // 1: id
                "\"MATERIAL\" TEXT," + // 2: material
                "\"HUNDRED\" TEXT," + // 3: hundred
                "\"PERCENTAGE\" TEXT);"); // 4: percentage
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PERCENT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PercentBean entity) {
        stmt.clearBindings();
 
        Long mainId = entity.getMainId();
        if (mainId != null) {
            stmt.bindLong(1, mainId);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String material = entity.getMaterial();
        if (material != null) {
            stmt.bindString(3, material);
        }
 
        String hundred = entity.getHundred();
        if (hundred != null) {
            stmt.bindString(4, hundred);
        }
 
        String percentage = entity.getPercentage();
        if (percentage != null) {
            stmt.bindString(5, percentage);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PercentBean entity) {
        stmt.clearBindings();
 
        Long mainId = entity.getMainId();
        if (mainId != null) {
            stmt.bindLong(1, mainId);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
 
        String material = entity.getMaterial();
        if (material != null) {
            stmt.bindString(3, material);
        }
 
        String hundred = entity.getHundred();
        if (hundred != null) {
            stmt.bindString(4, hundred);
        }
 
        String percentage = entity.getPercentage();
        if (percentage != null) {
            stmt.bindString(5, percentage);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public PercentBean readEntity(Cursor cursor, int offset) {
        PercentBean entity = new PercentBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // mainId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // material
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // hundred
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // percentage
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, PercentBean entity, int offset) {
        entity.setMainId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setMaterial(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHundred(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPercentage(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(PercentBean entity, long rowId) {
        entity.setMainId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(PercentBean entity) {
        if(entity != null) {
            return entity.getMainId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PercentBean entity) {
        return entity.getMainId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "percentBeanList" to-many relationship of GoodsBean. */
    public List<PercentBean> _queryGoodsBean_PercentBeanList(Long id) {
        synchronized (this) {
            if (goodsBean_PercentBeanListQuery == null) {
                QueryBuilder<PercentBean> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.Id.eq(null));
                goodsBean_PercentBeanListQuery = queryBuilder.build();
            }
        }
        Query<PercentBean> query = goodsBean_PercentBeanListQuery.forCurrentThread();
        query.setParameter(0, id);
        return query.list();
    }

}
