package samples.exoguru.materialtabs.common.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import samples.exoguru.materialtabs.R;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab2 extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_business,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SearchView searchView = (SearchView) getView().findViewById(R.id.UI_search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("請輸入欲搜尋的商圈");

    }
}