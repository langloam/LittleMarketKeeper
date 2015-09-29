package samples.exoguru.materialtabs.common;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import samples.exoguru.materialtabs.R;

/**
 * Created by iii on 2015/9/29.
 */
public class Init extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnShare = (Button) findViewById(R.id.btnShare);
    }



    Button btnShare;
}
