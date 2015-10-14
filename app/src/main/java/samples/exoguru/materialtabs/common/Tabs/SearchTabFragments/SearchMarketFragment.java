package samples.exoguru.materialtabs.common.Tabs.SearchTabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import samples.exoguru.materialtabs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMarketFragment extends Fragment {


    public SearchMarketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_market, container, false);
    }


}
