package samples.exoguru.materialtabs.common.Tabs;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;

import java.util.Timer;
import java.util.TimerTask;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Adapter.ImageAdapter;

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

    }

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
}
