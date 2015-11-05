package samples.exoguru.materialtabs.common.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import samples.exoguru.materialtabs.MainActivity;
import samples.exoguru.materialtabs.R;

public class LodingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

//        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.fade_in);
        handler.sendMessageDelayed(new Message(), 2500);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            intent.setClass(LodingActivity.this, MainActivity.class);
            LodingActivity.this.startActivity(intent);
            LodingActivity.this.finish();



            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            /**

             overridePendingTransition 畫面轉場效果

             (實現淡入淡出的效果)android.R.anim.fade_in, android.R.anim.fade_out;
             (由左向右滑入的效果)android.R.anim.slide_in_left, android.R.anim.slide_out_right
             (由上向下滑入的效果)android.R.anim.slide_in_up, android.R.anim.slide_out_down

             **/
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loding, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }
}
