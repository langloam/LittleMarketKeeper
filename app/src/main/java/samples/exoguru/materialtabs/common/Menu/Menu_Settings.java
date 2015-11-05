package samples.exoguru.materialtabs.common.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import samples.exoguru.materialtabs.R;


public class Menu_Settings extends AppCompatActivity implements View.OnTouchListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        Init();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            v.setBackgroundColor(0XFFCFD8DC);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundColor(0X00000000);
        }
        return false;
    }

    private void shareTo(String subject, String body, String chooserTitle) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);

        startActivity(Intent.createChooser(sharingIntent, chooserTitle));
    }


    private void Init() {
        btnVersion = (Button) findViewById(R.id.btnVersion);
        btnVersion.setOnTouchListener(this);

        btnContact = (Button) findViewById(R.id.btnContact);
        btnContact.setOnTouchListener(this);



        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu_Settings.this,Menu_Settings_Contact.class));
            }
        });

        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnTouchListener(this);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTo("分享", "http://mylittlemarkethome.azurewebsites.net/", "分享");
            }
        });
    }

    Button btnShare, btnContact,btnVersion;

}