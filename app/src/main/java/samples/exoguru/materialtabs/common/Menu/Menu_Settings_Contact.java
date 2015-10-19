package samples.exoguru.materialtabs.common.Menu;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import samples.exoguru.materialtabs.R;

public class Menu_Settings_Contact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_settings_btn_contact);

        Init();

        //設定功能表項目陣列，使用createFromResource()
        ArrayAdapter adapter = ArrayAdapter.createFromResource(Menu_Settings_Contact.this, R.array.ui_spinner, android.R.layout.simple_spinner_item);
        //設定下拉選單的樣式

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    View.OnClickListener btnCancel_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    View.OnClickListener btnSend_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String contactSelect = spinner.getSelectedItem().toString();
            String contactContents = txbContactContents.getText().toString();

            StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(l_policy);

            try {
                String name = URLEncoder.encode("APP" + contactSelect, "UTF-8");
                String email = URLEncoder.encode("無","UTF-8");
                String Info = URLEncoder.encode(contactContents,"UTF-8");
                String urlstr = "http://mylittlemarket.azurewebsites.net/SubmitMessage.aspx?n=" + name + "&e=" + email + "&i=" + Info;

                Log.i("URLde", urlstr);
                URL url=new URL(urlstr);
                URLConnection conn=url.openConnection();
                InputStream streamIn=conn.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));

                StringBuilder html = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    html.append(line);
                }

                Log.i("InsetOK", html.toString());

            } catch (MalformedURLException e) {
                Log.d("DEBUG", e.getMessage());
                e.printStackTrace();
            }catch (IOException e) {
                Log.d("DEBUG",e.getMessage());
                e.printStackTrace();
            }catch (Exception e) {
                Log.d("DEBUG",e.getMessage());
                e.printStackTrace();
            }


            if(contactContents.length()>0){
                finish();
                Toast.makeText(Menu_Settings_Contact.this,"資料已送出",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(Menu_Settings_Contact.this,"請輸入內容",Toast.LENGTH_SHORT).show();
            }

        }
    };



    private void Init() {

        spinner = (Spinner)findViewById(R.id.UI_Spinner);


        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(btnCancel_Click);
        btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(btnSend_Click);

        txbContactContents = (EditText)findViewById(R.id.txb);

    }


    EditText txbContactContents;
    Spinner spinner;
    Button btnCancel,btnSend;
}
