package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import helper.HelperFrags;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.interfaces.UpdateListData;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffPageFrag extends HelperFrags implements UpdateListData {


    public StaffPageFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_staff_page, container, false);
        ButterKnife.bind(this,view);
        initializeView();

        return view;
    }

    private void initializeView() {

    }

    @Override
    public void doUpdate(String url) {

    }
}
