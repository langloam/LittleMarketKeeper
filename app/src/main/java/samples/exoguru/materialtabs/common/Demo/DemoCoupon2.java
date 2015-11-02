package samples.exoguru.materialtabs.common.Demo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import samples.exoguru.materialtabs.R;

public class DemoCoupon2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_coupon2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.DemoCoupon2_ToolBar);
        setSupportActionBar(toolbar);
        ActionBar ActBar = getSupportActionBar();
        ActBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        TextView DemoCoupon1_txb2 = (TextView) findViewById(R.id.DemoCoupon1_txb2);

        DemoCoupon1_txb2.setText("熱情邀約您一起 看見新北 ● 探索無限可能\n" +
                "「2014新北市商圈嘉年華」活動開跑! 期間推出「新北商圈遊程行程」\n規劃出9條不同遊逛行程，教你怎樣玩最好玩~\n活動期間將舉辦一系列商圈行銷活動及消費好康優惠!" +
                "矽谷溫泉會館為「2014新北市商圈嘉年華」的特約店家，推出限定好康優惠! \n" +
                "\n凡貴賓出示 \"2014新北市商圈嘉年華活動手冊\"，即可享 \n" +
                "\n好康優惠1. 住宿優惠享定價5.5折，並贈送限量獨家設計款人字拖兩雙。 (數量有限，送完為止) \n" +
                "\n好康優惠2. 泡湯兩小時享優惠價 $999 / 2人。" +
                "\n* 注意事項：住宿泡湯請事先預約並告知使用好康優惠，服務專線 (02)2218-0101。\n" +
                "\n" +
                "● 活動期間：2014/9/28(日) ~ 2014/10/31(五) \n" +
                "● 活動手冊索取地點：至新北市各商圈、觀光工廠、區公所、旅客服務中心及市府洽公處等，約100處提供索取。\n" +
                "● 更多詳情請至2014新北市商圈嘉年華活動官網：http://www.newtaipeishopping.tw/");

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
