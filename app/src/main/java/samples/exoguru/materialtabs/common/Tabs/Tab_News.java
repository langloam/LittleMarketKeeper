package samples.exoguru.materialtabs.common.Tabs;


import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.ClassFactory.CNewsListItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_News extends Fragment {

    View layout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SimpleAdapter ItemAdapter = new SimpleAdapter(this.getContext(), CNewsListItem.getItem(),R.layout.news_list_item, new String[] {"lblnewsTitle", "lblnewsType"}, new int[] {R.id.lblnewsTitle, R.id.lblnewsType});

        ListView newsList = (ListView)this.getActivity().findViewById(R.id.newsListView);
        newsList.setAdapter(ItemAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.tab_news, container, false);
        return  layout;
    }


}
