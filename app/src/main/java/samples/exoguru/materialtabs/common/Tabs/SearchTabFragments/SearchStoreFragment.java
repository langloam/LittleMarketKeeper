package samples.exoguru.materialtabs.common.Tabs.SearchTabFragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Activities.StoreInfoActivity;

public class SearchStoreFragment extends Fragment {

    CDbManager db;
    Cursor table;
    List<Map<String, Object>> MarketList = new ArrayList<>();
    List<Map<String, Object>> StoreList = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitialComponent();

        final String all_market = "所有商圈";
        String sqlCmd;

        db = new CDbManager(getActivity().getApplicationContext());
        //取得商圈清單
        sqlCmd = "SELECT fId, fName FROM tMarket";
        table = db.QueryBySql(sqlCmd);
        MarketList.clear();
        //將第一項填入「所有商圈」
        MarketData firstItem = new MarketData();
        firstItem.setName(all_market);
        firstItem.setId(null);
        MarketList.add(firstItem.getMapData());
        while (table.moveToNext()) {
            MarketData tmp = new MarketData();
            tmp.setId(table.getString(0));
            tmp.setName(table.getString(1));
            MarketList.add(tmp.getMapData());
        }
        table.close();

        //填充Spinner的內容，為了能夠紀錄tMarket.fId所以採用SimpleAdapter
        SimpleAdapter cbMarketAdapter = new SimpleAdapter(getActivity(), MarketList, android.R.layout.simple_spinner_dropdown_item, new String[]{"name"}, new int[]{android.R.id.text1});
        cbMarketAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbMarket.setAdapter(cbMarketAdapter);

        //取得所有商家
        sqlCmd = "SELECT tShop.fId, tShop.fName, tMarket.fName " +
                "FROM tShop " +
                "INNER JOIN tShopBelong ON tShop.fId = tShopBelong.fShopid " +
                "INNER JOIN tMarket ON tShopBelong.fMarketid = tMarket.fId";
        table = db.QueryBySql(sqlCmd);
        StoreList.clear();
        while (table.moveToNext()) {
            StoreData tmp = new StoreData();
            tmp.setId(table.getString(0));
            tmp.setName(table.getString(1));
            tmp.setMarket(table.getString(2));
            StoreList.add(tmp.getMapData());
        }
        table.close();

        //產生SimpleAdapter以將資料填入ListView
        SimpleAdapter listStoreAdapter = new SimpleAdapter(getActivity(), StoreList, R.layout.market_list_item, new String[]{"name", "market"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
        listStore.setAdapter(listStoreAdapter);
    }

    AdapterView.OnItemClickListener StoreListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> storedata = ((Map<String, Object>)parent.getItemAtPosition(position));

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("StoreId", storedata.get("id").toString());
            intent.setClass(getActivity(), StoreInfoActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_store, container, false);
    }

    private class MarketData {
        private String id, name;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Object> getMapData() {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            return map;
        }
    }

    private class StoreData {
        private String id, name, market;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public Map<String, Object> getMapData() {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("market", "所屬商圈：" + market);
            return map;
        }
    }

    private void InitialComponent() {
        tbKeyword = (EditText) getActivity().findViewById(R.id.SearchStoreKeyword);
        btnSearch = (Button) getActivity().findViewById(R.id.SearchStoreSearchButton);
        listStore = (ListView) getActivity().findViewById(R.id.SearchStoreList);
        listStore.setOnItemClickListener(StoreListItemClickListener);
        cbMarket = (Spinner) getActivity().findViewById(R.id.SearchStoreMarketSpinner);
    }

    EditText tbKeyword;
    Button btnSearch;
    ListView listStore;
    Spinner cbMarket;
}
