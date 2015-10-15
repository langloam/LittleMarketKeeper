package samples.exoguru.materialtabs.common.Menu;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Menu_QRcode extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("la.droid.qr.scan");    //使用QRDroid的掃描功能
        intent.putExtra( "la.droid.qr.complete" , true);   //完整回傳，不截掉scheme

        try {
            //開啟 QRDroid App 的掃描功能，等待 call back onActivityResult()
            startActivityForResult(intent, 0);
        } catch(ActivityNotFoundException ex) {
            //若沒安裝 QRDroid，則開啟 Google Play商店，並顯示 QRDroid App
            startActivity((new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=la.droid.qr"))));
            this.finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( 0==requestCode && null!=data && data.getExtras()!=null ) {
            //掃描結果存放在 key 為 la.droid.qr.result 的值中
            String result = data.getExtras().getString("la.droid.qr.result");

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
            startActivity(intent);
            this.finish();
        }
        else
            this.finish();
    }



}
