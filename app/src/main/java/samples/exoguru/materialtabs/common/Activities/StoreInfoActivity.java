package samples.exoguru.materialtabs.common.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Demo.DemoCoupon2;
import samples.exoguru.materialtabs.common.Tabs.Tab_Collect;

public class StoreInfoActivity extends AppCompatActivity {

    CDbManager db;
    Cursor table;
    StoreData storeData;
    List<Map<String, Object>> DiscountList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        InitialComponent();
        db = new CDbManager(getApplicationContext());
        String sqlCmd;
        String StoreID;
        Bundle args;

        //取得傳入Activity的Store ID
        args = getIntent().getExtras();
        StoreID = args.getCharSequence("StoreId").toString();

        //取得店家資料
        sqlCmd = "SELECT tShop.fName, tShop.fAddress, tShop.fInfo, tMarket.fName " +
                "FROM tShop " +
                "LEFT JOIN tShopBelong ON tShop.fId = tShopBelong.fShopid " +
                "LEFT JOIN tMarket ON tShopBelong.fMarketid = tMarket.fId " +
                "WHERE tShop.fId = '" + StoreID + "'";
        table = db.QueryBySql(sqlCmd);
        storeData = new StoreData();
        while (table.moveToNext()) {
            storeData.setId(StoreID);
            storeData.setName(table.getString(0));
            storeData.setAddress(table.getString(1));
            storeData.setInfo(table.getString(2));
            if (table.getString(3) == null) {
                storeData.setMarket("無");
            } else {
                storeData.setMarket(table.getString(3));
            }
        }
        table.close();

        //設定Activity標題
        getSupportActionBar().setTitle(storeData.getMapData().get("name").toString());
        //顯示資訊內容
        lblBelongTo.setText("所屬商圈：" + storeData.getMapData().get("market").toString());
        lblAddress.setText("店家地址：" + storeData.getMapData().get("address").toString());
        lblContent.setText(storeData.getMapData().get("info").toString());

        //取得優惠資訊
        sqlCmd = "SELECT fId, fName, fBegindate, fEnddate, fInfo " +
                "FROM tDiscount " +
                "WHERE fShopid = '" + StoreID + "'";
        table = db.QueryBySql(sqlCmd);
        DiscountList.clear();
        while (table.moveToNext()) {
            DiscountData tmp = new DiscountData();
            tmp.setId(table.getString(0));
            tmp.setName(table.getString(1));
            tmp.setBeginDate(table.getString(2));
            tmp.setEndDate(table.getString(3));
            tmp.setInfo(table.getString(4));
            SimpleDateFormat dateForm = new SimpleDateFormat("yyyy/MM/dd");

            Log.d("Date Debug", tmp.getEndDate());
            Log.d("Date Debug", dateForm.format(Calendar.getInstance().getTime()));
            //如果活動已結束便不列出
            try {
                if(dateForm.parse(tmp.getEndDate()).before(dateForm.parse(dateForm.format(Calendar.getInstance().getTime())))){
                    break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DiscountList.add(tmp.getMapData());
        }
        table.close();

        //使用SimpleAdapter將資料與ListView連動
        if (DiscountList.size() == 0) {
            lblNoDiscount.setVisibility(View.VISIBLE);
        } else {
            SimpleAdapter listDiscountAdapter = new SimpleAdapter(getApplication(), DiscountList, R.layout.market_list_item, new String[]{"name", "text2"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
            listDiscount.setAdapter(listDiscountAdapter);
        }

        //重設ListView的高度
        ListAdapter listAdapter = listDiscount.getAdapter();
        if(listAdapter!=null) {

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listDiscount);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listDiscount.getLayoutParams();
            params.height = totalHeight + (listDiscount.getDividerHeight() * (listAdapter.getCount() - 1));
            listDiscount.setLayoutParams(params);
        }
    }

    AdapterView.OnItemClickListener DiscountListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> data = (Map<String, Object>)parent.getItemAtPosition(position);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("Name", data.get("name").toString());
            bundle.putString("Info", data.get("info").toString());
            bundle.putString("Shopname", storeData.getMapData().get("name").toString());
            bundle.putString("Bengindate", data.get("JSONBeginDate").toString());
            bundle.putString("Enddate", data.get("JSONEndDate").toString());
            bundle.putString("Address", storeData.getMapData().get("address").toString());
            intent.putExtras(bundle);
            intent.setClass(getApplication(), DemoCoupon2.class);

            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_store_bookmark:
                Cursor table = (new CDbManager(this)).QueryBySql("SELECT * FROM tCollectStores where fStoresID = '"+storeData.getMapData().get("id").toString()+"'");
                if(table.getCount()==0){
                    ContentValues row = new ContentValues();
                row.put("fId", storeData.getMapData().get("id").toString());

                    if(Tab_Collect.isFBloggedIn())
                        row.put("fFBID", AccessToken.getCurrentAccessToken().getUserId());
                    else
                        row.put("fFBID", "nobody");
                    row.put("fStoresID", storeData.getMapData().get("id").toString());
                    row.put("fStoresName", storeData.getMapData().get("name").toString());
                    db = new CDbManager(this);
                    db.Insert("tCollectStores", row);
                    Log.d("FBTEST", "收藏店家成功");
                    Toast.makeText(this, "收藏店家成功", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, "此店家已經收藏過了", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.menu_store_location:

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putCharSequence("Name", storeData.getMapData().get("name").toString());
                bundle.putCharSequence("Address", storeData.getMapData().get("address").toString());
                bundle.putInt("Range", 0);
                intent.putExtras(bundle);
                intent.setClass(this, MapsActivity.class);

                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StoreData {
        private String id, name, address, market, info;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Map<String, Object> getMapData() {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("address", address);
            map.put("market", market);
            map.put("info", info);
            return map;
        }
    }

    private class DiscountData {
        private String id, name, beginDate, endDate, info;
        private String JSONBeginDate, JSONEndDate;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBeginDate(String beginDate) {
            this.JSONBeginDate = beginDate;
            this.beginDate = convertDate(beginDate);
        }

        public void setEndDate(String endDate) {
            this.JSONEndDate = endDate;
            this.endDate = convertDate(endDate);
        }

        public String getEndDate(){
            return  endDate;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Map<String, Object> getMapData() {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("beginDate", beginDate);
            map.put("JSONBeginDate", JSONBeginDate);
            map.put("endDate", endDate);
            map.put("JSONEndDate", JSONEndDate);
            map.put("info", info);
            map.put("text2", "有效期間：" + beginDate + " 至 " + endDate);
            return map;
        }

        private String convertDate(String JSONDate) {
            long milliSec = Long.valueOf(JSONDate.replace("/Date(", "").replace(")/", ""));
            Calendar calender = Calendar.getInstance();
            calender.setTimeInMillis(milliSec);
            return new SimpleDateFormat("yyyy/MM/dd").format(calender.getTime());
        }
    }

    private void InitialComponent() {
        ActivityToolbar = (Toolbar) findViewById(R.id.StoreInfoTitleBar);
        setSupportActionBar(ActivityToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        lblAddress = (TextView) findViewById(R.id.StoreInfoAddress);
        lblBelongTo = (TextView) findViewById(R.id.StoreInfoBelongTo);
        lblContent = (TextView) findViewById(R.id.StoreInfoContent);
        lblNoDiscount = (TextView) findViewById(R.id.StoreInfoDiscountInfo);
        listDiscount = (ListView) findViewById(R.id.StoreInfoDiscountList);
        listDiscount.setOnItemClickListener(DiscountListClickListener);
    }

    Toolbar ActivityToolbar;
    TextView lblAddress, lblBelongTo, lblContent, lblNoDiscount;
    ListView listDiscount;
}
