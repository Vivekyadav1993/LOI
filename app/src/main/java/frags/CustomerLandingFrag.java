package frags;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.CustomerLandingRecyclerViewAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import helper.HelperFrags;
import models.businesslist.BusinessListModel;
import models.businesslist.Output;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.interfaces.customerbusiness.CustomerBusinessPresenterImpl;
import r2stech.lifeoninternet.interfaces.customerbusiness.ICustomerBusinessPresenter;
import r2stech.lifeoninternet.interfaces.customerbusiness.ICustomerBusinessView;
import r2stech.lifeoninternet.utils.Utils;

import static r2stech.lifeoninternet.utils.Utils.context;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerLandingFrag extends HelperFrags implements ICustomerBusinessView {
    private FragmentManager mFragmentManager;
    private LandingActivity activity;

    private CustomerLandingRecyclerViewAdapter mCustomerLandingRecyclerViewAdapter;

    private ICustomerBusinessPresenter mICustomerBusinessPresenter;
    private Context mContext;
    private LandingActivity mLandingActivity;
    private List<String> customerLandingData = new ArrayList<>();
    private ArrayList<Output> list = new ArrayList<>();

    @BindView(R.id.customer_landing_frag_recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.fragment_customer_no_data_tv)
    public TextView mNoDataTv;

    @BindView(R.id.fragment_customer_search_et)
    public EditText mSearch;

    private Bundle bundle;
    private String search_status;

    public CustomerLandingFrag() {

    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_landing, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {

        bundle = getArguments();
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        activity = (LandingActivity) getActivity();
        mFragmentManager = getActivity().getSupportFragmentManager();
        mICustomerBusinessPresenter = new CustomerBusinessPresenterImpl(this);
        mICustomerBusinessPresenter.getCustomerBusinessLsit();

        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });

      /*  search_status=bundle.getString("comming");

        if(search_status.equals("showsearch")){
            mRelativeLayout.setVisibility(View.VISIBLE);
        }else {
            mRelativeLayout.setVisibility(View.GONE);
        }
*/
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Output> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Output s : list) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mCustomerLandingRecyclerViewAdapter.filterList(filterdNames);
    }

    @Override
    public void onCustomerBusinessSuccess(int pid, BusinessListModel businessListModel) {
        Utils.stopProgress(getActivity());
        try {

            list.clear();

//            Log.d("CLF", "" + businessListModel.getOutput().get(0).getIndustry());
               /* mNoDataTv.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
*/
          /*  mNoDataTv.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
*/
            for (int i = 0; i < businessListModel.getOutput().size(); i++) {
                Output output = new Output();
                output.setBusinessId(businessListModel.getOutput().get(i).getBusinessId().toString());
                output.setBusinessAddressId(businessListModel.getOutput().get(i).getBusinessAddressId().toString());
                output.setName(businessListModel.getOutput().get(i).getName().toString());
                output.setIndustry(businessListModel.getOutput().get(i).getIndustry().toString());
                output.setAddress(businessListModel.getOutput().get(i).getAddress().toString());
                output.setMobile(businessListModel.getOutput().get(i).getMobile().toString());
                output.setOpenStatus(businessListModel.getOutput().get(i).getOpenStatus().toString());
                output.setOpenTime(businessListModel.getOutput().get(i).getOpenTime().toString());
                list.add(output);

            }

            mRecyclerView.setHasFixedSize(true);
            mCustomerLandingRecyclerViewAdapter = new CustomerLandingRecyclerViewAdapter(this, list, new CustomerLandingRecyclerViewAdapter.CustomerLandingButtonClick() {
                @Override
                public void onClick(int pos, String business_id, String addredd_id, String name) {
                    Bundle bundle = new Bundle();
                    bundle.putString("business_id", business_id);
                    bundle.putString("addredd_id", addredd_id);
                    bundle.putString("business_name", name);
                    replaceFrag(new JoinQueueFrag(), bundle, CustomerLandingFrag.class.getName());

                }
            });
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mCustomerLandingRecyclerViewAdapter);

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getView() != null) {
                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {

                            activity.finish();
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            Log.e("error", "" + e);
        }
    }


    @Override
    public void onRecruiterMasterdataError(int pid, String errorData) {


    }
}
