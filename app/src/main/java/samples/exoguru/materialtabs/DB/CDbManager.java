package samples.exoguru.materialtabs.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by iii on 2015/9/29.
 */
public class CDbManager extends SQLiteOpenHelper {
    public CDbManager(Context context) {
        super(context, "dbAPP.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE IF NOT EXISTS tCollectBusiness (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fFBID TEXT ,";
        sql+=" fBusinessID INTEGER ,";
        sql+=" fBusinessName TEXT)";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS tCollectStores (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fFBID TEXT ,";
        sql+=" fStoresID INTEGER ,";
        sql+=" fStoresName TEXT)";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS tMarket (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fName TEXT ,";
        sql+=" fType TEXT ,";
        sql+=" fArea TEXT ,";
        sql+=" fRange INTEGER ,";
        sql+=" fImg TEXT ,";
        sql+=" fInfo TEXT ,";
        sql+=" fBegindate TEXT ,";
        sql+=" fEnddate TEXT ,";
        sql+=" fSubmitdate TEXT)";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS tShop (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fName TEXT ,";
        sql+=" fAddress TEXT ,";
        sql+=" fInfo TEXT)";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS tDiscount (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fShopid TEXT ,";
        sql+=" fName TEXT ,";
        sql+=" fInfo TEXT ,";
        sql+=" fBegindate TEXT ,";
        sql+=" fEnddate TEXT ,";
        sql+=" fBuilddate TEXT)";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS tNews (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fType TEXT ,";
        sql+=" fTitle TEXT ,";
        sql+=" fContent TEXT ,";
        sql+=" fImgurl TEXT ,";
        sql+=" fBuilddate TEXT)";
        db.execSQL(sql);

        sql="CREATE TABLE IF NOT EXISTS tMarkeEvent (";
        sql+=" _id INTEGER PRIMARY KEY ,";
        sql+=" fId TEXT ,";
        sql+=" fMarkeid TEXT ,";
        sql+=" fName TEXT ,";
        sql+=" fInfo TEXT ,";
        sql+=" fBegindate TEXT ,";
        sql+=" fEnddate TEXT ,";
        sql+=" fBuilddate TEXT)";
        db.execSQL(sql);

    }

    public Cursor QueryBySql(String sql){
        return getReadableDatabase().rawQuery(sql,null);
    }

    public void Update(String tableName,ContentValues data,int pk){
        getWritableDatabase().update(tableName,data,"_id="+String.valueOf(pk),null);
    }

    public void Delete(String tableName,int pk){
        getWritableDatabase().delete(tableName, "_id=" + String.valueOf(pk), null);
    }

    public void Delete(String tableName){
        getWritableDatabase().delete(tableName,null,null);
    }
    public  void Insert(String tableName,ContentValues data){
        getWritableDatabase().insert(tableName,null,data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tMarket");
        onCreate(db);

        Log.i("SQLITE", "更新成功");
    }
}
