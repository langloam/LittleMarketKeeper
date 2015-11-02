package samples.exoguru.materialtabs.common.Demo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Tabs.Tab_Discount;

public class DemoCoupon1 extends AppCompatActivity {

    ArrayList<couponInfo> objList=null;
    Type type = new TypeToken<ArrayList<couponInfo>>(){}.getType();
    ActionBar ActBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_coupon1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.DemoCoupon1_ToolBar);
        setSupportActionBar(toolbar);
        ActBar = getSupportActionBar();
        ActBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        TextView DemoCoupon1_txb1 = (TextView) findViewById(R.id.DemoCoupon1_txb1);





        String data = getIntent().getStringExtra("Bengindate");

        long MillionSecDate = Long.valueOf(data.replace("/Date(", "").replace(")/", ""));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(MillionSecDate);
        String Bengindate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());


        data =getIntent().getStringExtra("Enddate");

        MillionSecDate = Long.valueOf(data.replace("/Date(", "").replace(")/", ""));
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(MillionSecDate);

        String Enddate = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());



        DemoCoupon1_txb1.setText("優惠名稱:" + getIntent().getStringExtra("Name") +
                "\n店家:" + getIntent().getStringExtra("Shopname") +
                "\n起始日期:" + Bengindate +
                "\n結束日期:" + Enddate +
                                "\n店家地址:"+ getIntent().getStringExtra("Address"));

        ActBar.setTitle(getIntent().getStringExtra("Shopname"));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
