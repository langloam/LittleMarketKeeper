package samples.exoguru.materialtabs.common.ClassFactory;

import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Louis on 2015/10/13.
 */
public class CNewsListItem {
    static private List<Map<String, Object>> item = new ArrayList<>();

    public static List<Map<String, Object>> getItem() {

        item.clear();

        Map<String, Object> tmp;

        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(l_policy);

        try {

            URL url=new URL("http://mylittlemarket.azurewebsites.net/FindNews.aspx");
            URLConnection conn=url.openConnection();
            InputStream streamIn=conn.getInputStream();


            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
            JSONArray jsonArray= new JSONArray(r.readLine());
            for(int i=0; i<jsonArray.length(); i++) {
                tmp = new HashMap<>();
                tmp.put("lblnewsTitle", jsonArray.getJSONObject(i).get("newsTitle").toString());
                tmp.put("lblnewsType", jsonArray.getJSONObject(i).get("newsContent").toString());
                tmp.put("lblnewsID", jsonArray.getJSONObject(i).get("id").toString());
                Log.d("NewsLD_test", jsonArray.getJSONObject(i).get("id").toString());
                item.add(tmp);
            }
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
        return item;
    }
}
