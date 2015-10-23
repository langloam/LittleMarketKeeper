package samples.exoguru.materialtabs.common.Tabs.SearchTabFragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMarketFragment extends Fragment {

    CDbManager db;
    Spinner MarketTypeSelector;
    CheckBox searchTitle, searchContent;
    Button btnStartSearch;
    EditText KeyWordBox;
    ListView MarketListView;
    List<Map<String, Object>> ResultSet = new ArrayList<>();
    SimpleAdapter rsltListAdapter;
    String SearchType;

    public SearchMarketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InitialComponent();
        final String All_Type = "所有分類";

        Cursor table = db.QueryBySql("SELECT DISTINCT fType FROM tMarket");
        final ArrayList<String> TypeList = new ArrayList<>();
        TypeList.add(All_Type);
        SearchType = All_Type;
        while (table.moveToNext()) {
            TypeList.add(table.getString(0));
        }
        table.close();
        ArrayAdapter<String> typeListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, TypeList.toArray(new String[TypeList.size()]));
        typeListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MarketTypeSelector.setAdapter(typeListAdapter);

        String sqlCmd = "SELECT fName, fSubmitdate, fType FROM tMarket";
        table = db.QueryBySql(sqlCmd);
        while (table.moveToNext()) {
            ResultFormat tmp = new ResultFormat();
            tmp.setTitle(table.getString(0));
            tmp.setDate(table.getString(1));
            tmp.setType(table.getString(2));
            ResultSet.add(tmp.getMapData());
        }
        table.close();
        rsltListAdapter = new SimpleAdapter(getContext(), ResultSet, R.layout.market_list_item, new String[]{"title", "info"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
        MarketListView.setAdapter(rsltListAdapter);

        MarketTypeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SearchType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            Cursor table;
            String sqlCmd, keyWords[];

            @Override
            public void onClick(View v) {
                if (KeyWordBox.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "請輸入關鍵字！！", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    keyWords = KeyWordBox.getText().toString().split(" ");
                    ResultSet.clear();
                }
                if (searchTitle.isChecked()) {
                    if (searchContent.isChecked()) {
                        sqlCmd = "SELECT fName, fSubmitdate, fType FROM tMarket WHERE ((";
                        for (int i = 0; i < keyWords.length; i++) {
                            sqlCmd += " fName LIKE '%" + keyWords[i] + "%'";
                            if (i != keyWords.length - 1) {
                                sqlCmd += " OR";
                            }
                        }
                        sqlCmd += ") OR (";
                        for (int i = 0; i < keyWords.length; i++) {
                            sqlCmd += " fInfo LIKE '%" + keyWords[i] + "%'";
                            if (i != keyWords.length - 1) {
                                sqlCmd += " OR";
                            }
                        }
                        sqlCmd += "))";

                        if (!SearchType.equals(All_Type)) {
                            sqlCmd += " AND (fType = '" + SearchType + "')";
                        }

                        table = db.QueryBySql(sqlCmd);
                        while (table.moveToNext()) {
                            ResultFormat tmp = new ResultFormat();
                            tmp.setTitle(table.getString(0));
                            tmp.setDate(table.getString(1));
                            tmp.setType(table.getString(2));
                            ResultSet.add(tmp.getMapData());
                        }
                        table.close();

                        rsltListAdapter = new SimpleAdapter(getContext(), ResultSet, R.layout.market_list_item, new String[]{"title", "info"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
                        MarketListView.setAdapter(rsltListAdapter);
                    } else {
                        sqlCmd = "SELECT fName, fSubmitdate, fType FROM tMarket WHERE (";
                        for (int i = 0; i < keyWords.length; i++) {
                            sqlCmd += " fName LIKE '%" + keyWords[i] + "%'";
                            if (i != keyWords.length - 1) {
                                sqlCmd += " OR";
                            }
                        }
                        sqlCmd += ")";

                        if (!SearchType.equals(All_Type)) {
                            sqlCmd += " AND (fType = '" + SearchType + "')";
                        }

                        table = db.QueryBySql(sqlCmd);
                        while (table.moveToNext()) {
                            ResultFormat tmp = new ResultFormat();
                            tmp.setTitle(table.getString(0));
                            tmp.setDate(table.getString(1));
                            tmp.setType(table.getString(2));
                            ResultSet.add(tmp.getMapData());
                        }
                        table.close();

                        rsltListAdapter = new SimpleAdapter(getContext(), ResultSet, R.layout.market_list_item, new String[]{"title", "info"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
                        MarketListView.setAdapter(rsltListAdapter);
                    }
                } else {
                    if (searchContent.isChecked()) {
                        sqlCmd = "SELECT fName, fSubmitdate, fType FROM tMarket WHERE (";
                        for (int i = 0; i < keyWords.length; i++) {
                            sqlCmd += " fInfo LIKE '%" + keyWords[i] + "%'";
                            if (i != keyWords.length - 1) {
                                sqlCmd += " OR";
                            }
                        }
                        sqlCmd += ")";

                        if (!SearchType.equals(All_Type)) {
                            sqlCmd += " AND (fType = '" + SearchType + "')";
                        }

                        table = db.QueryBySql(sqlCmd);
                        while (table.moveToNext()) {
                            ResultFormat tmp = new ResultFormat();
                            tmp.setTitle(table.getString(0));
                            tmp.setDate(table.getString(1));
                            tmp.setType(table.getString(2));
                            ResultSet.add(tmp.getMapData());
                        }
                        table.close();

                        rsltListAdapter = new SimpleAdapter(getContext(), ResultSet, R.layout.market_list_item, new String[]{"title", "info"}, new int[]{R.id.market_list_item_title, R.id.market_list_item_info});
                        MarketListView.setAdapter(rsltListAdapter);
                    } else {
                        Toast.makeText(getActivity(), "標題、內文請至少勾選一項", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_market, container, false);
    }

    private void InitialComponent() {
        db = new CDbManager(getActivity());
        MarketTypeSelector = (Spinner) getActivity().findViewById(R.id.MarketTypeSelector);
        searchTitle = (CheckBox) getActivity().findViewById(R.id.search_market_title);
        searchContent = (CheckBox) getActivity().findViewById(R.id.search_market_content);
        btnStartSearch = (Button) getActivity().findViewById(R.id.btn_market_start_search);
        KeyWordBox = (EditText) getActivity().findViewById(R.id.EditText_MarketKeyWord);
        MarketListView = (ListView) getActivity().findViewById(R.id.MarketList);
    }

    private class ResultFormat {
        private String title;
        private String type;
        private String date;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setDate(String date) {
            long MilliSec = Long.valueOf(date.replace("/Date(", "").replace(")/", ""));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(MilliSec);
            this.date = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
        }

        public Map<String, Object> getMapData() {
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", title);
            map.put("info", "建立日期：" + date + "\n" + "商圈分類：" + type);

            return map;
        }
    }
}
