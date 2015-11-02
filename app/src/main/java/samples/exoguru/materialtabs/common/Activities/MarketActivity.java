package samples.exoguru.materialtabs.common.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.ClassFactory.CNewsListItem;

public class MarketActivity extends AppCompatActivity {
    Toolbar TitleToolBar;
    String MarketID;
    TextView Title, Content, Date, Type, Address;
    CDbManager db;
    ImageView Img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        InitialComponent();
        TitleToolBar = (Toolbar) findViewById(R.id.MarketTitleBar);
        setSupportActionBar(TitleToolBar);
        ActionBar ActBar = getSupportActionBar();
        ActBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        Bundle args = this.getIntent().getExtras();

        MarketID = (String) args.get("MarketID");

        String sqlCmd = "SELECT * FROM tMarket where fId =" + MarketID;
        Cursor table = db.QueryBySql(sqlCmd);

        ResultFormat tmp = new ResultFormat();
        while (table.moveToNext()) {
            tmp.set_id(table.getString(0));
            tmp.setId(table.getString(1));
            tmp.setName(table.getString(2));
            tmp.setType(table.getString(3));
            tmp.setArea(table.getString(4));
            tmp.setRange(table.getString(5));
            tmp.setImg(table.getString(6));
            tmp.setInfo(table.getString(7));
            tmp.setBegin(table.getString(8));
            tmp.setEnd(table.getString(9));
            tmp.setDate(table.getString(10));
        }
        table.close();

        URL imgURL = null;
        try {
            imgURL = new URL("http://mylittlemarkethome.azurewebsites.net/" + tmp.img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        new LoadImage().execute(imgURL);    //調用非同步 Task 來載入圖片
        Title.setText("商圈名稱： " + tmp.name);
        Type.setText("商圈分類： " + tmp.type);
        Address.setText("商圈位置： " + tmp.area);
        Date.setText("商圈期限： " + tmp.begin + "～" + tmp.end);
        Content.setText("商圈介紹： " + tmp.info);



        sqlCmd = "SELECT fShopid FROM tShopBelong where fMarketid =" + MarketID;
        table = db.QueryBySql(sqlCmd);

        List<Map<String, Object>> item = new ArrayList<>();
        item.clear();
        Map<String, Object> shopsdata;

        while (table.moveToNext()) {
            shopsdata = new HashMap<>();
            shopsdata.put("lblShopTitle", table.getString(0));
            Log.d("Shops_test", table.getString(0));
            item.add(shopsdata);
        }
        table.close();

        SimpleAdapter ItemAdapter = new SimpleAdapter(this, item, R.layout.shop_list_item, new String[]{"lblShopTitle"}, new int[]{R.id.lblShopTitle});

        ListView shopList = (ListView) this.findViewById(R.id.ShopListView);
        shopList.setAdapter(ItemAdapter);

//        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ListView list = (ListView) parent;
//                HashMap<String, Object> map = (HashMap<String, Object>) list.getItemAtPosition(position);
//                CharSequence NewsID = (CharSequence) map.get("lblnewsID");
//
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//
//                intent.setClass(Tab_News.this.getContext(), NewsPageActivity.class);
//                bundle.putCharSequence("NewsID", NewsID);
//                intent.putExtras(bundle);
//
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //元件初始化
    private void InitialComponent() {
        db = new CDbManager(this);

        Title = (TextView) findViewById(R.id.MarketTitle);
        Date = (TextView) findViewById(R.id.MarketDate);
        Address = (TextView) findViewById(R.id.MarketAddress);
        Type = (TextView) findViewById(R.id.MarketType);
        Content = (TextView) findViewById(R.id.MarketContent);
        Img = (ImageView) findViewById(R.id.MarketImg);
    }

    private class ResultFormat {
        private String _id;
        private String id;
        private String name;
        private String type;
        private String area;
        private String range;
        private String img;
        private String info;
        private String begin;
        private String end;
        private String date;


        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setDate(String date) {
            long MilliSec = Long.valueOf(date.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(MilliSec);
            this.date = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setBegin(String begin) {
            long MilliSec = Long.valueOf(begin.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(MilliSec);
            this.begin = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        }

        public void setEnd(String end) {
            long MilliSec = Long.valueOf(end.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(MilliSec);
            this.end = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
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
            Img.setImageBitmap(bitmap);
        }
    }
}


