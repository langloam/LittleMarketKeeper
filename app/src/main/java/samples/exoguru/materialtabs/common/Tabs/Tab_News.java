package samples.exoguru.materialtabs.common.Tabs;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import samples.exoguru.materialtabs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_News extends Fragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(l_policy);

        try {
            URL url=new URL("http://192.168.1.58:8081/myWebSite/Default.aspx");
            URLConnection conn=url.openConnection();
            InputStream streamIn=conn.getInputStream();

            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
            JSONArray jsonArray= new JSONArray(r.readLine());
            TextView lblnewsTitle = (TextView)getView().findViewById(R.id.lblnewsTitle);
            lblnewsTitle.setText(jsonArray.getJSONObject(0).get("newsTitle").toString());
        } catch (MalformedURLException e) {
            Log.d("URLERROR", e.getMessage());
            e.printStackTrace();
        }catch (IOException e) {
            Log.d("URLERROR",e.getMessage());
            e.printStackTrace();
        }catch (Exception e) {
            Log.d("URLERROR",e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_news, container, false);

    }


}
