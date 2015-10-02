package samples.exoguru.materialtabs.common.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import samples.exoguru.materialtabs.R;


public class Tab_Settings extends AppCompatActivity implements View.OnTouchListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_settings);

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
        btnQuestion = (Button) findViewById(R.id.btnQuestion);
        btnQuestion.setOnTouchListener(this);
        btnCooperation = (Button) findViewById(R.id.btnCooperation);
        btnCooperation.setOnTouchListener(this);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnTouchListener(new View.OnTouchListener() {
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
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTo("分享", "Http://www.google.com.tw", "分享");

            }
        });
    }

    Button btnShare,btnQuestion,btnCooperation;

}