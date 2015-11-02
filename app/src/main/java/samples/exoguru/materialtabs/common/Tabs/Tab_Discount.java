package samples.exoguru.materialtabs.common.Tabs;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.MainActivity;
import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Adapter.ImageAdapter;
import samples.exoguru.materialtabs.common.Demo.DemoCoupon1;
import samples.exoguru.materialtabs.common.Demo.DemoCoupon2;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab_Discount extends Fragment {

    private Gallery gallery;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private Handler mHandler = new Handler();
    private int[] Pics = {R.drawable.img1, R.drawable.img2};


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_discount, container, false);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        couponText1 = (TextView) getActivity().findViewById(R.id.couponText1);
        couponText2 = (TextView) getActivity().findViewById(R.id.couponText2);
        couponInit();

        gallery = (Gallery) getView().findViewById(R.id.UI_Gallery);
        gallery.setAdapter(new ImageAdapter(getActivity(), Pics));


        //圖片不透明
        gallery.setUnselectedAlpha( 255 );
        //圖片不漸層，漸層長度為0
        gallery.setFadingEdgeLength( 0 );
        //圖片不重疊，圖片間距為0
        gallery.setSpacing(0);
        //圖片一開始顯示在第幾張設定在Integer.MAX_VALUE/2的位置(Integer.MAX_VALUE為int的最高值)
        gallery.setSelection(Integer.MAX_VALUE / 2);
        //圖片在切換圖片的速度
        gallery.setAnimationDuration(1000);
        //設定點擊圖片時觸發
        //mGallery.setOnItemClickListener(click);

        imgbtn1 = (View) getView().findViewById(R.id.imgbtn1);
        imgbtn1.setOnClickListener(imgbtn1_Click);
        imgbtn2 = (View) getView().findViewById(R.id.imgbtn2);
        imgbtn2.setOnClickListener(imgbtn2_Click);




    }

    private void couponInit() {
        Log.d("FB","couponInit");

        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(l_policy);

        try {
            URL url=new URL("http://mylittlemarket.azurewebsites.net/FindDiscontRandom.aspx");
            URLConnection conn = url.openConnection();
            InputStream streamIn = conn.getInputStream();
            Log.d("FB","連線成功");
            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));



            String JsonString = r.readLine();
            Log.d("FB","JsonString:"+JsonString);

            Gson gson = new Gson();
            Type type = new TypeToken<List<couponInfo>>(){}.getType();
            List<couponInfo> objList = gson.fromJson(JsonString, type);

            Log.d("FB","Id:"+objList.get(0).getId());
            Log.d("FB","Name:"+objList.get(0).getName());
            Log.d("FB","Info:"+objList.get(0).getInfo());
            Log.d("FB","Bengindate:"+objList.get(0).getBengindate());
            Log.d("FB","Enddate:"+objList.get(0).getEnddate());
            Log.d("FB","Buliddate:"+objList.get(0).getBuliddate());
            Log.d("FB","Shopid:"+objList.get(0).getShopid());
            Log.d("FB","Shopid:"+objList.get(0).getShopname());
            Log.d("FB","Address:"+objList.get(0).getAddress());


            Log.d("FB", "Id:" + objList.get(1).getId());
            Log.d("FB","Name:"+objList.get(1).getName());
            Log.d("FB","Info:"+objList.get(1).getInfo());
            Log.d("FB","Bengindate:"+objList.get(1).getBengindate());
            Log.d("FB","Enddate:"+objList.get(1).getEnddate());
            Log.d("FB","Buliddate:"+objList.get(1).getBuliddate());
            Log.d("FB","Shopid:"+ objList.get(1).getShopid());
            Log.d("FB","Shopid:"+objList.get(1).getShopname());
            Log.d("FB", "Address:" + objList.get(1).getAddress());

            couponText1.setText(objList.get(0).getShopname() + "\n" + objList.get(0).getName());
            couponText2.setText(objList.get(1).getShopname() + "\n" + objList.get(1).getName());


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

    }



    View.OnClickListener imgbtn1_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity((new Intent(getActivity(), DemoCoupon1.class)));
        }
    };

    View.OnClickListener imgbtn2_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity((new Intent(getActivity(), DemoCoupon2.class)));
        }
    };

    @Override
//程式暫停時將自動輪播功能的時間計時清除.
    public void onPause() {
        super.onPause();

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    @Override
//程式回復時建立自動輪播的時間計時
    public void onResume() {
        super.onResume();

        //因下方會重新new Timer，避免重複佔據系統不必要的資源，在此確認mTimer是否為null
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        //因下方會重新new TimerTask，避免重複佔據系統不必要的資源，在此確認mTimerTask是否為null
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        //建立Timer
        mTimer = new Timer();
        //建立TimerTask
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //每3秒觸發時要做的事
                        //scroll 3
                        gallery.onScroll(null, null, 3, 0);
                        //向右滾動
                        gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    }
                });
            }
        };

        //從3秒開始，每3秒觸發一次，每三秒自動滾動
        mTimer.schedule(mTimerTask, 3000, 3000);
    }



    /*
    private OnItemClickListener click = new OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
        {
            Toast.makeText(MainActivity.this, "您點擊第" + ((arg2 % Pics.length) + 1) + "張圖片", Toast.LENGTH_SHORT).show();
        }
    };
    */

    View imgbtn1,imgbtn2;
    TextView couponText1,couponText2;



    private class couponInfo{
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBengindate() {
            return bengindate;
        }

        public void setBengindate(String bengindate) {
            this.bengindate = bengindate;
        }

        public String getBuliddate() {
            return buliddate;
        }

        public void setBuliddate(String buliddate) {
            this.buliddate = buliddate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }
        private String id;
        private String name;
        private String info;
        private String bengindate;
        private String enddate;
        private String buliddate;
        private String shopid;
        private String shopname;

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        private String address;
    }
}
