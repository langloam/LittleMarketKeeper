package samples.exoguru.materialtabs.common.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import samples.exoguru.materialtabs.R;

public class NewsPageActivity extends AppCompatActivity {

    Toolbar TitleToolBar;
    String NewsID;
    TextView NewsTitle, NewsContent, NewsBuildDate, NewsType;
    ImageView NewsImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        InitialComponent();
        TitleToolBar = (Toolbar) findViewById(R.id.newsTitleBar);
        setSupportActionBar(TitleToolBar);
        ActionBar ActBar = getSupportActionBar();
        ActBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        Bundle args = this.getIntent().getExtras();

        NewsID = (String) args.get("NewsID");

        try {
            URL url = new URL("http://mylittlemarket.azurewebsites.net/FindNews.aspx");
            URLConnection conn = url.openConnection();
            InputStream streamIn = conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
            JSONArray jsonArray = new JSONArray(r.readLine());

            for (int i = 0; i < jsonArray.length(); i++) {
                if (String.valueOf(jsonArray.getJSONObject(i).getInt("id")).equals(NewsID)) {
                    NewsTitle.setText(jsonArray.getJSONObject(i).getString("newsTitle"));
                    Log.d("Debug_News", NewsTitle.getText().toString());

                    //將 JSON DateTime 轉換成時間
                    String dateData = jsonArray.getJSONObject(i).getString("builddate");
                    long MillionSecDate = Long.valueOf(dateData.replace("/Date(", "").replace(")/", ""));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(MillionSecDate);

                    NewsBuildDate.setText("發布日期：" + new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime()));
                    NewsType.setText("新聞分類：" + jsonArray.getJSONObject(i).getString("newsType"));

                    URL imgURL = new URL("http://mylittlemarkethome.azurewebsites.net/" + jsonArray.getJSONObject(i).getString("imgurl"));
                    new LoadImage().execute(imgURL);    //調用非同步 Task 來載入圖片

                    NewsContent.setText(jsonArray.getJSONObject(i).getString("newsContent"));
                }
            }
        } catch (Exception e) {
            Log.d("URLERROR", e.getMessage());
            e.printStackTrace();
        }
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
        NewsTitle = (TextView) findViewById(R.id.NewsTitle);
        NewsBuildDate = (TextView) findViewById(R.id.NewsBuildDate);
        NewsType = (TextView) findViewById(R.id.NewsType);
        NewsContent = (TextView) findViewById(R.id.NewsContent);
        NewsImg = (ImageView) findViewById(R.id.NewsImg);
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
            NewsImg.setImageBitmap(bitmap);
        }
    }
}
