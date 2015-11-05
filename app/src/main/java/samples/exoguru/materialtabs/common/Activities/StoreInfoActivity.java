package samples.exoguru.materialtabs.common.Activities;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;

public class StoreInfoActivity extends AppCompatActivity {

    CDbManager db;
    Cursor table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        InitialComponent();
        db = new CDbManager(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void InitialComponent(){
        ActivityToolbar = (Toolbar) findViewById(R.id.StoreInfoTitleBar);
        setSupportActionBar(ActivityToolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        lblAddress = (TextView) findViewById(R.id.StoreInfoAddress);
        lblBelongTo = (TextView) findViewById(R.id.StoreInfoBelongTo);
    }

    Toolbar ActivityToolbar;
    TextView lblAddress, lblBelongTo;
}
