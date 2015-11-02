package samples.exoguru.materialtabs.common.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;

public class ShopActivity extends AppCompatActivity {
    Toolbar TitleToolBar;
    TextView Title, Content, Address;
    String ShopId,ShopName,ShopAddress,ShopInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        InitialComponent();
        TitleToolBar = (Toolbar) findViewById(R.id.ShopTitleBar);
        setSupportActionBar(TitleToolBar);
        ActionBar ActBar = getSupportActionBar();
        ActBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);

        Bundle args = this.getIntent().getExtras();

        ShopId = (String) args.get("ShopID");
        ShopName = (String) args.get("ShopName");
        ShopAddress = (String) args.get("ShopAddress");
        ShopInfo = (String) args.get("ShopInfo");

        Title.setText(ShopName);
        Address.setText(ShopAddress);
        Content.setText(ShopInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop, menu);
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
    //元件初始化

    private void InitialComponent() {
        Title = (TextView) findViewById(R.id.ShopTitle);
        Address = (TextView) findViewById(R.id.ShopAddress);
        Content = (TextView) findViewById(R.id.ShopContent);
    }
}
