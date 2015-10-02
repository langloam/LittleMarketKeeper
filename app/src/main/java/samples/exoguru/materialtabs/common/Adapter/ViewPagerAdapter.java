package samples.exoguru.materialtabs.common.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import samples.exoguru.materialtabs.common.Tabs.Tab_Discount;
import samples.exoguru.materialtabs.common.Tabs.Tab_Business;
import samples.exoguru.materialtabs.common.Tabs.Tab_Collect;
import samples.exoguru.materialtabs.common.Tabs.Tab_News;

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
                Tab_Discount tabDiscount = new Tab_Discount();
                return tabDiscount;
            case 1:
                Tab_News tabNews = new Tab_News();
                return tabNews;
            case 2:
                Tab_Business tab2 = new Tab_Business();
                return tab2;
            case 3:
                Tab_Collect tabBusiness = new Tab_Collect();
                return tabBusiness;
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