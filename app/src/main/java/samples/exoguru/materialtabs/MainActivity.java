package samples.exoguru.materialtabs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.common.Adapter.ViewPagerAdapter;
import samples.exoguru.materialtabs.common.Menu.Menu_QRcode;
import samples.exoguru.materialtabs.common.Menu.Menu_Settings;
import samples.exoguru.materialtabs.common.view.SlidingTabLayout;

/**
 * Created by Edwin on 15/02/2015.
 */
public class MainActivity extends AppCompatActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = new CharSequence[4];
    int Numboftabs =Titles.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //檢查網路狀態
        checkNetwork();
        
        // Set The Tabs Titles
        setTabsTitles();


        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);



        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    public void  checkNetwork(){
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);//先取得此service

        NetworkInfo networInfo = conManager.getActiveNetworkInfo();       //在取得相關資訊

        if (networInfo == null || !networInfo.isAvailable()){ //判斷是否有網路

            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("偵測不到網路,請檢查您的網路狀態")
                    .show();

        }
        else{
            //商圈資料初始化
            MarketDataInit();
            //店家資料初始化
            ShopDataInit();
            //Log.d("appCREATE", "資料庫check");
        }
    }



    private void MarketDataInit() {
        CDbManager db = new CDbManager(MainActivity.this);
        db.Delete("tMarket");
        Log.d("appCREATE", "資料庫del");

        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(l_policy);

        try {

            URL url=new URL("http://mylittlemarket.azurewebsites.net/FindMarkets.aspx");
            URLConnection conn=url.openConnection();
            InputStream streamIn=conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
            JSONArray jsonArray= new JSONArray(r.readLine());
            for(int i=0; i<jsonArray.length(); i++) {

                ContentValues row =new ContentValues();
                row.put("fId",Integer.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
                row.put("fName",jsonArray.getJSONObject(i).get("name").toString());
                row.put("fType",jsonArray.getJSONObject(i).get("marketType").toString());
                row.put("fArea",jsonArray.getJSONObject(i).get("area").toString());
                row.put("fRange",Integer.valueOf(jsonArray.getJSONObject(i).get("range").toString()));
                row.put("fImg",jsonArray.getJSONObject(i).get("img").toString());
                row.put("fInfo",jsonArray.getJSONObject(i).get("info").toString());
                row.put("fBegindate",jsonArray.getJSONObject(i).get("begindate").toString());
                row.put("fEnddate",jsonArray.getJSONObject(i).get("enddate").toString());
                row.put("fSubmitdate", jsonArray.getJSONObject(i).get("submitdate").toString());
                db = new CDbManager(MainActivity.this);
                db.Insert("tMarket", row);

            }
//            Toast.makeText(MainActivity.this, "新增商圈資料成功", Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            Log.d("URLERROR", e.getMessage());
            e.printStackTrace();
        }catch (IOException e) {
            Log.d("URLERROR",e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {
            Log.d("URLERROR",e.getMessage());
            e.printStackTrace();
        }

        Log.d("appCREATE", "資料庫ins");

//        Cursor table = db.QueryBySql("SELECT * FROM tMarket");
//        String[] datas=new String[table.getCount()];
//        table.moveToFirst();
//        for(int i=0;i<datas.length;i++){
//            datas[i]=table.getString(1)+"\r\n"+table.getString(2);
//            table.moveToNext();
//        }
//
//        AlertDialog.Builder build=new AlertDialog.Builder(MainActivity.this);
//        build.setTitle("")
//                .setItems(datas, null)
//                .create().show();
//
//        Toast.makeText(MainActivity.this, "查詢商圈資料成功", Toast.LENGTH_SHORT).show();

        Log.d("appCREATE", "資料庫sel");
    }

    private void ShopDataInit() {
        CDbManager db = new CDbManager(MainActivity.this);
        db.Delete("tShop");
        Log.d("appCREATE", "Shop_del");

        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(l_policy);

        try {

            URL url=new URL("http://mylittlemarket.azurewebsites.net/FindShops.aspx?t=al");
            URLConnection conn=url.openConnection();
            InputStream streamIn=conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
            JSONArray jsonArray= new JSONArray(r.readLine());
            for(int i=0; i<jsonArray.length(); i++) {

                ContentValues row =new ContentValues();
                row.put("fId",Integer.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
                row.put("fName",jsonArray.getJSONObject(i).get("name").toString());
                row.put("fAddress",jsonArray.getJSONObject(i).get("address").toString());
                row.put("fInfo",jsonArray.getJSONObject(i).get("info").toString());
                db = new CDbManager(MainActivity.this);
                db.Insert("tShop", row);

            }
//            Toast.makeText(MainActivity.this, "新增店家資料成功", Toast.LENGTH_SHORT).show();
        } catch (MalformedURLException e) {
            Log.d("URLERROR", e.getMessage());
            e.printStackTrace();
        }catch (IOException e) {
            Log.d("URLERROR",e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {
            Log.d("URLERROR",e.getMessage());
            e.printStackTrace();
        }

        Log.d("appCREATE", "Shop_ins");

        Cursor table = db.QueryBySql("SELECT * FROM tShop");
        String[] datas=new String[table.getCount()];
        table.moveToFirst();
        for(int i=0;i<datas.length;i++){
            datas[i]=table.getString(1)+"\r\n"+table.getString(2);
            table.moveToNext();
        }

        AlertDialog.Builder build=new AlertDialog.Builder(MainActivity.this);
        build.setTitle("")
                .setItems(datas, null)
                .create().show();

        Toast.makeText(MainActivity.this, "查詢商圈資料成功", Toast.LENGTH_SHORT).show();

        Log.d("appCREATE", "Shop_sel");
    }

    private void setTabsTitles() {
        Titles[0] = "優惠";
        Titles[1] = getString(R.string.news_tab_title);
        Titles[2] = "商圈";
        Titles[3] = "收藏";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_settings:
                Intent intent = new Intent(MainActivity.this, Menu_Settings.class);
                startActivity(intent);
                return true;
            case R.id.menu_QRCode:
                startActivity((new Intent(MainActivity.this, Menu_QRcode.class)));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private List<ResolveInfo> getShareTargets(){
        Intent intent=new Intent(Intent.ACTION_SEND,null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");

        PackageManager pm=this.getPackageManager();

        return pm.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

}