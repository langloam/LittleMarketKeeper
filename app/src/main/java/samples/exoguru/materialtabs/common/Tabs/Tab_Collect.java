package samples.exoguru.materialtabs.common.Tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import samples.exoguru.materialtabs.R;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab_Collect extends Fragment {
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_collect,container,false);
        return v;
    }
}