package samples.exoguru.materialtabs.common.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import samples.exoguru.materialtabs.common.Tabs.Tab1;
import samples.exoguru.materialtabs.common.Tabs.Tab2;
import samples.exoguru.materialtabs.common.Tabs.Tab3;
import samples.exoguru.materialtabs.common.Tabs.TabMore;
import samples.exoguru.materialtabs.common.Tabs.TabNews;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                TabNews tabNews = new TabNews();
                return tabNews;
            case 2:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 3:
                Tab3 tab3 = new Tab3();
                return tab3;
            default:
                return null;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}