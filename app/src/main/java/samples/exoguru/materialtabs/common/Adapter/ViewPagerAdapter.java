package samples.exoguru.materialtabs.common.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import samples.exoguru.materialtabs.R;
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }




//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//            View view = null;
//            view = mInflater.inflate(R.layout.record_list_layout, null);           
//            TextView tvRecord = (TextView) view.findViewById(R.id.tv_record);
//            String key = "tvRecord" + position;
//            // 關鍵點，針對要更新的View來設定Tag，主要是在後續使用ViewPager.findViewWithTag()時，可以找到要更新的View
//            tvRecord.setTag(key);
//            container.addView(view);       
//            return view;
//    }

}