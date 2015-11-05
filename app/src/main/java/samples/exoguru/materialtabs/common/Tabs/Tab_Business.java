package samples.exoguru.materialtabs.common.Tabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Tabs.SearchTabFragments.SearchMarketFragment;
import samples.exoguru.materialtabs.common.Tabs.SearchTabFragments.SearchStoreFragment;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab_Business extends Fragment {

    DrawerLayout searchNavLayout;
    NavigationView searchNavList;
    Button btnSearchOpt;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_business,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchNavLayout = (DrawerLayout)this.getActivity().findViewById(R.id.SearchNavDrawerLayout);
        searchNavList = (NavigationView)this.getActivity().findViewById(R.id.SearchNavDrawer);
        btnSearchOpt = (Button)this.getActivity().findViewById(R.id.btnSearchOpt);
        searchNavList.setNavigationItemSelectedListener(new DrawerItemCheckListener());

        searchNavLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);



        btnSearchOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNavLayout.openDrawer(searchNavList);
            }
        });

        FragmentManager frgMgr = getFragmentManager();
        Fragment frg = new SearchMarketFragment();
        searchNavList.setCheckedItem(R.id.search_market);
        frgMgr.beginTransaction().replace(R.id.SearchContentFrame, frg).commit();
    }

    public void changePage(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.search_market:
                fragment = new SearchMarketFragment();
                break;
            case R.id.search_store:
                fragment = new SearchStoreFragment();
                break;
            default:
                return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.SearchContentFrame, fragment).commit();

        searchNavList.setCheckedItem(id);
        searchNavLayout.closeDrawer(searchNavList);
    }

    public class DrawerItemCheckListener implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            changePage(menuItem.getItemId());
            return true;
        }
    }
}