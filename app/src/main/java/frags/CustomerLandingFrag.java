package frags;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import adapters.CustomerLandingRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import helper.HelperFrags;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerLandingFrag extends HelperFrags{

    private FragmentManager mFragmentManager;
    private LandingActivity activity;

    private CustomerLandingRecyclerViewAdapter mCustomerLandingRecyclerViewAdapter;

    private ArrayList<String> customerLandingData= new ArrayList<>();
    {
        customerLandingData.add("Hotel");
        customerLandingData.add("Mall");
        customerLandingData.add("Hostel");
        customerLandingData.add("School");
        customerLandingData.add("Business");

    }

    @BindView(R.id.customer_landing_frag_recycler_view)
    public RecyclerView mRecyclerView;


    public CustomerLandingFrag() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_customer_landing, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {

        activity = (LandingActivity) getActivity();
        mFragmentManager = getActivity().getSupportFragmentManager();

        mRecyclerView.setHasFixedSize(true);
        mCustomerLandingRecyclerViewAdapter = new CustomerLandingRecyclerViewAdapter(this, customerLandingData, new CustomerLandingRecyclerViewAdapter.CustomerLandingButtonClick() {
            @Override
            public void buttonOptionClick(int pos) {
                Bundle bundle = new Bundle();
                Toast.makeText(getContext(), "" +pos, Toast.LENGTH_SHORT).show();
                replaceFrag(new JoinQueueFrag(), bundle, CustomerLandingFrag.class.getName());

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCustomerLandingRecyclerViewAdapter);

    }

}
