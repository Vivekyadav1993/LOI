package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import helper.HttpresponseUpd;
import r2stech.lifeoninternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyDetailsFrag extends Fragment implements HttpresponseUpd  {


    private HttpresponseUpd callback;
    public CompanyDetailsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_details, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        callback = this;

    }

    @Override
    public void getResponse(String response) {

    }
}
