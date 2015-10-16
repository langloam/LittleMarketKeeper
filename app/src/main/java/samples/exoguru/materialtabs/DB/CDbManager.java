package samples.exoguru.materialtabs.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by iii on 2015/9/29.
 */
public class CDbManager extends SQLiteOpenHelper {
    public CDbManager(Context context) {
        super(context, "dbAPP.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE tCollect (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fFBID TEXT ,";
        sql+=" fBusiness TEXT ,";
        sql+=" fStores TEXT)";
        db.execSQL(sql);
    }

    public Cursor QueryBySql(String sql){
        return getReadableDatabase().rawQuery(sql,null);
    }
    public void Update(String tableName,ContentValues data,int pk){
        getWritableDatabase().update(tableName,data,"_id="+String.valueOf(pk),null);
    }

    public void Delete(String tableName,int pk){
        getWritableDatabase().delete(tableName,"_id="+String.valueOf(pk),null);
    }
    public  void Insert(String tableName,ContentValues data){
        getWritableDatabase().insert(tableName,null,data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
