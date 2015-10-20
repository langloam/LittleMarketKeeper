package samples.exoguru.materialtabs.common.Tabs;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Activities.NewsPageActivity;
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

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView list = (ListView)parent;
                HashMap<String , Object> map = (HashMap<String, Object>)list.getItemAtPosition(position);
                CharSequence NewsID = (CharSequence)map.get("lblnewsID");

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                intent.setClass(Tab_News.this.getContext(), NewsPageActivity.class);
                bundle.putCharSequence("NewsID", NewsID);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.tab_news, container, false);
        return  layout;
    }


}
