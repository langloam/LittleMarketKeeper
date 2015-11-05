package samples.exoguru.materialtabs.common.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Tabs.Tab_Collect;

public class MarketInfoActivity extends AppCompatActivity {

    CDbManager db;
    Cursor table;
    MarketData marketData;
    List<Map<String, Object>> MappedStoreList = new ArrayList<>();
    Toolbar ActivityToolbar;
    TextView lblType, lblDate, lblAddr, lblContent;
    ImageView picMarket;
    ListView storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);
        Bundle args = this.getIntent().getExtras();
        InitialComponent();
        setSupportActionBar(ActivityToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        String sqlCmd = "SELECT fName, fType, fBegindate, fEnddate, fImg, fInfo, fArea, fRange FROM tMarket " +
                "WHERE fId='" + args.getCharSequence("MarketId") + "'";
        table = db.QueryBySql(sqlCmd);
        while (table.moveToNext()) {
            marketData.setId(args.getCharSequence("MarketId").toString());
            marketData.setName(table.getString(0));
            marketData.setType(table.getString(1));
            marketData.setBeginTime(table.getString(2));
            marketData.setEndTime(table.getString(3));
            marketData.setImgUrl(table.getString(4));
            marketData.setInfo(table.getString(5));
            marketData.setAddress(table.getString(6));
            marketData.setRange(table.getInt(7));
        }
        table.close();

        getSupportActionBar().setTitle(marketData.getName());
        lblType.setText("商圈分類：" + marketData.getType());
        lblDate.setText("起訖日期：" + marketData.getBeginTime() + " 至 " + marketData.getEndTime());
        lblAddr.setText("所在地址：" + marketData.getAddress());
        lblContent.setText(marketData.getInfo());

        try {
            URL ImgUrl = new URL("http://mylittlemarkethome.azurewebsites.net/" + marketData.getImgUrl());
            new LoadImage().execute(ImgUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //取得所屬店家的資料
        sqlCmd = "SELECT tShop.fId, tShop.fName, tShop.fAddress FROM tShopBelong INNER JOIN tShop ON tShop.fId = tShopBelong.fShopid WHERE tShopBelong.fMarketid = '" + marketData.getId() + "'";
        table = db.QueryBySql(sqlCmd);
        while (table.moveToNext()) {
            ShopDataFormat tmp = new ShopDataFormat();
            tmp.setId(table.getString(0));
            tmp.setName(table.getString(1));
            tmp.setAddress(table.getString(2));
            MappedStoreList.add(tmp.getMapData());
        }
        table.close();

        SimpleAdapter StoreListAdapter = new SimpleAdapter(this, MappedStoreList, R.layout.market_list_item, new String[]{"name", "address"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
        storeList.setAdapter(StoreListAdapter);

        //重設ListView的高度
        ListAdapter listAdapter = storeList.getAdapter();

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, storeList);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = storeList.getLayoutParams();
        params.height = totalHeight + (storeList.getDividerHeight() * (listAdapter.getCount() - 1));
        storeList.setLayoutParams(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_market_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_market_bookmark:
//                Log.d("FBTEST", AccessToken.getCurrentAccessToken().getUserId());
                Cursor table = (new CDbManager(this)).QueryBySql("SELECT * FROM tCollectBusiness where fBusinessID = '"+marketData.getId()+"'");
                if(table.getCount()==0){
                    ContentValues row = new ContentValues();
//                row.put("fId",marketData.getId().toString());

                    if(Tab_Collect.isFBloggedIn())
                        row.put("fFBID", AccessToken.getCurrentAccessToken().getUserId());
                    else
                        row.put("fFBID", "nobody");
                    row.put("fBusinessID", marketData.getId());
                    row.put("fBusinessName", marketData.getName());
                    db = new CDbManager(this);
                    db.Insert("tCollectBusiness", row);
                    Log.d("FBTEST", "收藏商圈成功");
                    Toast.makeText(this, "收藏商圈成功", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "此商圈已經收藏過了", Toast.LENGTH_SHORT).show();
                }

                return true;
            case R.id.menu_market_location:


                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("MarketAddress",marketData.getAddress());
                bundle.putCharSequence("MarketName",marketData.getName());
                bundle.putInt("MarketRange", marketData.getRange());
                intent.putExtras(bundle);
                intent.setClass(this,MapsActivity.class);

                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void InitialComponent() {
        db = new CDbManager(this.getApplicationContext());
        marketData = new MarketData();
        ActivityToolbar = (Toolbar) findViewById(R.id.MarketInfoTitleBar);
        lblType = (TextView) findViewById(R.id.MarketInfoType);
        lblDate = (TextView) findViewById(R.id.MarketInfoDate);
        lblAddr = (TextView) findViewById(R.id.MarketInfoAddress);
        lblContent = (TextView) findViewById(R.id.MarketInfoContent);
        picMarket = (ImageView) findViewById(R.id.MarketInfoImg);
        storeList = (ListView) findViewById(R.id.MarketInfoStoreList);

    }

    private class MarketData {
        private String id, name, info, type, imgUrl;
        private String beginTime, endTime;
        private String address;
        private int range;


        public int getRange() {
            return range;
        }

        public void setRange(int range) {
            this.range = range;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getBeginTime() {
            return ConvertDateFromJSONDate(beginTime);
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return ConvertDateFromJSONDate(endTime);
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        private String ConvertDateFromJSONDate(String JSONDate) {
            long milliSec = Long.valueOf(JSONDate.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSec);
            return new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        }
    }

    private class ShopDataFormat {
        private String id, name, address;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Map<String, Object> getMapData() {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("address", address);
            return map;
        }
    }

    //非同步 Task 載入圖片，繼承自 AsyncTask
    private class LoadImage extends AsyncTask<URL, Void, Bitmap> {
        //覆寫 doInBackground() *必須
        @Override
        protected Bitmap doInBackground(URL... ImgUrls) {
            int count = ImgUrls.length;
            Bitmap bitmap = null;
            try {
                for (int i = 0; i < count; i++) {
                    URLConnection imgConn = ImgUrls[i].openConnection();
                    HttpURLConnection httpConn = (HttpURLConnection) imgConn;
                    httpConn.setRequestMethod("GET");
                    httpConn.connect();
                    if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpConn.getInputStream();

                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                    }
                }
            } catch (Exception e) {
                Log.d("URLERROR", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        //覆寫 onPostExecute()，Task 完成後將 NewsImg 內容代換成已載入的圖片
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            picMarket.setImageBitmap(bitmap);
        }
    }
}
