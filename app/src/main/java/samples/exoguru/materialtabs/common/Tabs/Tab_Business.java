package samples.exoguru.materialtabs.common.Tabs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import samples.exoguru.materialtabs.R;
import samples.exoguru.materialtabs.common.Tabs.SearchTabFragments.SearchMarketFragment;

/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab_Business extends Fragment {

    DrawerLayout searchNavLayout;
    NavigationView searchNavList;

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
        searchNavList.setNavigationItemSelectedListener(new DrawerItemCheckListener());

        FragmentManager frgMgr = getFragmentManager();
        Fragment frg = new SearchMarketFragment();
        frgMgr.beginTransaction().replace(R.id.SearchContentFrame, frg).commit();
    }

    public void changePage(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.search_market:
                fragment = new SearchMarketFragment();
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