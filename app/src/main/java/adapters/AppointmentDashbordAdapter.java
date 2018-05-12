package adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import frags.MyCustomerFrag;
import frags.MyHistoryFrag;

/**
 * Created by Vivek yadav on 07-05-2018.
 */

public class AppointmentDashbordAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    String businessId,addressId;

    //Constructor to the class
    public AppointmentDashbordAdapter(FragmentManager fm, int tabCount, String businessId, String addressId) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
        this.addressId=addressId;
        this.businessId=businessId;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Bundle bundle =new Bundle();
                bundle.putString("businessId",businessId);
                bundle.putString("addressId",addressId);
                MyCustomerFrag mMyCustomerFrag = new MyCustomerFrag();
                mMyCustomerFrag.setArguments(bundle);
                return mMyCustomerFrag;
            case 1:
                MyHistoryFrag mMyHistoryFrag = new MyHistoryFrag();
                return mMyHistoryFrag;
        }
        return null;
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}

