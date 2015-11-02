package samples.exoguru.materialtabs.common.Activities;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;

public class MarketInfoActivity extends AppCompatActivity {

    CDbManager db;
    Cursor table;
    MarketData marketData;
    Toolbar ActivityToolbar;
    TextView lblType, lblDate, lblContent;
    ImageView picMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);
        Bundle args = this.getIntent().getExtras();
        InitialComponent();
        setSupportActionBar(ActivityToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        String sqlCmd = "SELECT fName, fType, fBegindate, fEnddate, fImg, fInfo, fId FROM tMarket " +
                "WHERE fId='" + args.getCharSequence("MarketId")+"'";
        table = db.QueryBySql(sqlCmd);
        while (table.moveToNext()) {
            marketData.setId(args.getCharSequence("MarketId").toString());
            marketData.setName(table.getString(0));
            marketData.setType(table.getString(1));
            marketData.setBeginTime(table.getString(2));
            marketData.setEndTime(table.getString(3));
            marketData.setImgUrl(table.getString(4));
            marketData.setInfo(table.getString(5));
        }

        getSupportActionBar().setTitle(marketData.getName());
        lblType.setText("商圈分類：" + marketData.getType());
        lblDate.setText("起訖日期：" + marketData.getBeginTime() + " 至 " + marketData.getEndTime());
        lblContent.setText(marketData.getInfo());

        try {
            URL ImgUrl = new URL("http://mylittlemarkethome.azurewebsites.net/" + marketData.getImgUrl());
            new LoadImage().execute(ImgUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_market_info, menu);
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

    private void InitialComponent() {
        db = new CDbManager(this.getApplicationContext());
        marketData = new MarketData();
        ActivityToolbar = (Toolbar) findViewById(R.id.MarketInfoTitleBar);
        lblType = (TextView) findViewById(R.id.MarketInfoType);
        lblDate = (TextView) findViewById(R.id.MarketInfoDate);
        lblContent = (TextView) findViewById(R.id.MarketInfoContent);
        picMarket = (ImageView)findViewById(R.id.MarketInfoImg);
    }

    private class MarketData {
        private String id, name, info, type, imgUrl;
        private String beginTime, endTime;

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

        private String ConvertDateFromJSONDate(String JSONDate) {
            long milliSec = Long.valueOf(JSONDate.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSec);
            return new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        }
        /*
        private static String ConvertDateFromJSONDate(String JSONDate, String formatString){
            long milliSec = Long.valueOf(JSONDate.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSec);
            return new SimpleDateFormat(formatString).format(calendar);
        }*/
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
