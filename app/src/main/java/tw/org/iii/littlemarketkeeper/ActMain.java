package tw.org.iii.littlemarketkeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class ActMain extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main);

        Init();




    }


    View.OnClickListener imgBtnNews_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    /*
        ImageButton.OnTouchListener imgBtnNews_Touch = new ImageButton.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.button_touch));
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.button));
                }
                return false;
            }
        };
    */
    private void Init() {
    /*
        imgBtnNews = (ImageButton) findViewById(R.id.imgBtnNews);
        imgBtnNews.setOnClickListener(imgBtnNews_Click);
        */








        /***************ToolBar功能****************/
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_act_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.test2);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    ImageButton imgBtnNews;
}
