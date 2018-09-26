package frags;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.util.ArrayList;
import java.util.List;

import adapters.CustomerLandingRecyclerViewAdapter;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.UserAuthACtivity;
import atw.lifeoninternet.interfaces.customerbusiness.CustomerBusinessPresenterImpl;
import atw.lifeoninternet.interfaces.customerbusiness.ICustomerBusinessPresenter;
import atw.lifeoninternet.interfaces.customerbusiness.ICustomerBusinessView;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import helper.HelperFrags;
import models.businesslist.BusinessListModel;
import models.businesslist.Output;

import static atw.lifeoninternet.utils.Utils.context;


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
    private Sharedpreferences mPref;

  //  private Dialog mDialougeBox;

    public CustomerLandingFrag() {

    }

   /* @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_landing, container, false);
        ButterKnife.bind(this, view);
        //  isNetworkConnectionAvailable();
        initilizerView();

        return view;
    }

    private void initilizerView() {
       // showForceUpdateDialog();
        mPref = Sharedpreferences.getUserDataObj(getActivity());
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

        if (isNetworkConnectionAvailable()) {
            mICustomerBusinessPresenter = new CustomerBusinessPresenterImpl(this);
            mICustomerBusinessPresenter.getCustomerBusinessLsit();


        } else {
            checkNetworkConnection();
        }


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

    /*public void showForceUpdateDialog() {


        mDialougeBox = new Dialog(getContext());
        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // mDialougeBox.setContentView(R.layout.rate_application_items);
        mDialougeBox.setContentView(R.layout.rate_user_application_items);
        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
        mDialougeBox.show();

     *//*   final LinearLayout bad_ll = (LinearLayout) mDialougeBox.findViewById(R.id.bad_smile_ll);
        final LinearLayout okay_ll = (LinearLayout) mDialougeBox.findViewById(R.id.okay_smile_ll);
        final LinearLayout great_ll = (LinearLayout) mDialougeBox.findViewById(R.id.great_smile_ll);
        ImageView bad_iv = (ImageView) mDialougeBox.findViewById(R.id.bad_iv);
        final ImageView okay_iv = (ImageView) mDialougeBox.findViewById(R.id.okay_iv);
        ImageView great_iv = (ImageView) mDialougeBox.findViewById(R.id.great_iv);
*//*
       *//* bad_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad_ll.setBackgroundResource(R.color.gray);
            }
        });*//*
       *//* bad_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad_ll.setBackgroundResource(R.color.gray);
                okay_ll.setBackgroundResource(R.color.background_blue_color);
                great_ll.setBackgroundResource(R.color.background_blue_color);
            }
        });
        okay_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bad_ll.setBackgroundResource(R.color.background_blue_color);
                okay_ll.setBackgroundResource(R.color.gray);
                great_ll.setBackgroundResource(R.color.background_blue_color);

            }
        });
        great_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                okay_ll.setBackgroundResource(R.color.background_blue_color);
                great_ll.setBackgroundResource(R.color.gray);
                bad_ll.setBackgroundResource(R.color.background_blue_color);

            }
        });
*//*
        //  mDialougeBox.setCancelable(false);


    }*/

    @Override
    public void onCustomerBusinessSuccess(int pid, BusinessListModel businessListModel) {
        Utils.stopProgress(getActivity());
        try {

            list.clear();


            for (int i = 0; i < businessListModel.getOutput().size(); i++) {
                Output output = new Output();
                output.setBusinessId(businessListModel.getOutput().get(i).getBusinessId().toString());
//                output.setBusinessAddressId(businessListModel.getOutput().get(i).getBusinessAddressId().toString());
                output.setName(businessListModel.getOutput().get(i).getName().toString());
                output.setIndustry(businessListModel.getOutput().get(i).getIndustry().toString());
                output.setAddress(businessListModel.getOutput().get(i).getAddress().toString());
                output.setMobile(businessListModel.getOutput().get(i).getMobile().toString());
                output.setOpenStatus(businessListModel.getOutput().get(i).getOpenStatus().toString());
                output.setOpenTime(businessListModel.getOutput().get(i).getOpenTime().toString());
                output.setPic(businessListModel.getOutput().get(i).getPic().toString());
                output.setAppBooking(businessListModel.getOutput().get(i).getAppBooking());
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

        } catch (Exception e) {
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

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {

        Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
      /*  android.support.v7.app.AlertDialog.Builder builder =new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();*/
    }
}
