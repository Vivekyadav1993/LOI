package frags;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daasuu.bl.BubbleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import adapters.ConsumerListAdap;
import adapters.LiveAdap;
import atw.lifeoninternet.R;
import atw.lifeoninternet.interfaces.UpdateListData;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import models.ConsumerListData;
import models.StaffListData;
import models.businesslist.Output;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsFrag extends HelperFrags implements UpdateListData {

    private View Mroot;

    @BindView(R.id.staff_list)
    public ListView staff_list;

    @BindView(R.id.customer_list)
    public ListView customer_list;

    @BindView(R.id.expand_up_iv)
    public ImageView mUp;

    @BindView(R.id.expand_down_iv)
    public ImageView mDown;

    private String post_tag = "";
    private Bundle bundle;
    private ArrayList<StaffListData> raw_data;
    private ArrayList<ConsumerListData> consumer_array;
    private LiveAdap liveAdap;
    private ConsumerListAdap consumerListAdap;
    private UpdateListData updateListData;
    // BottomSheetBehavior variable
    // private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.bottom_sheet)
    public View mBottomSheet;

    @BindView(R.id.my_add_back_btn)
    public ImageView mBack;

    @BindView(R.id.my_ads_bubble_layout)
    public BubbleLayout mBubbleLayout;

    @BindView(R.id.add_cuctomers)
    public ImageView mAddCust;

    @BindView(R.id.my_ads_business_tv)
    public TextView mBusinessTv;

    @BindView(R.id.my_ads_frag_total_customer_tv)
    public TextView mTotalCust;

    @BindView(R.id.my_ads_frag_left_customer_tv)
    public TextView mLeftCust;


    @BindView(R.id.fragment_customer_name_search_et)
    public TextView mSearchNameEt;

    private final int FIVE_SECONDS = 30000;
    private Handler handler;
    private Runnable runnable;

    private boolean info = true;

    private BottomSheetBehavior<View> behavior;

    public static String business_id = "", address_id = "";
    public static String staff_id = "";
    public String staff_service_started, business_name, no_of_days;

    private Sharedpreferences mPref;

    //   private DetailListModel detailListModel;
    public MyAdsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mroot = inflater.inflate(R.layout.fragment_my_ads, container, false);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();
        raw_data = new ArrayList<>();
        consumer_array = new ArrayList<>();
        updateListData = this;
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        handler = new Handler();
        address_id = bundle.getString("address_id");
        business_id = bundle.getString("business_id");
        //  initView();
        post_tag = "getDATA";


      /*  hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                business_id + "&address_id=" +
                address_id);
*/
        bottomsheetFun();
        openBottomSheetFunc();

        mSearchNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());

            }
        });


        return Mroot;

    }

    private void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<ConsumerListData> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ConsumerListData s : consumer_array) {
            //if the existing elements contains the search input
            if (s.getCustomer_name().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        consumerListAdap.filterList(filterdNames);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("MAF", "Staff----id" + mPref.getStaffId());
        if (mPref.getStaffId().equalsIgnoreCase("")) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, FIVE_SECONDS);
                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                            business_id + "&address_id=" + address_id);
                    Utils.stopProgress(getActivity());


                }
            };
            runnable.run();
        } else {
            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, FIVE_SECONDS);
                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                            business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId());
                    Utils.stopProgress(getActivity());


                }
            };
            runnable.run();

        }
    }

    private void openBottomSheetFunc() {

        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
            //   mOnbordingTransRl.setVisibility(View.VISIBLE);
        } else {
            behavior.setState(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

            //  mOnbordingTransRl.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.my_add_back_btn, R.id.add_cuctomers, R.id.my_ads_info_iv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.my_add_back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.add_cuctomers:

                Bundle _bundle = new Bundle();

                _bundle.putString("address_id", address_id);
                _bundle.putString("business_id", business_id);
                _bundle.putString("business_name", business_name);
                replaceFrag(new AddManualCustomer(), _bundle, AddStaffFrag.class.getName());
                break;

            case R.id.my_ads_info_iv:

                if (info == true) {
                    mBubbleLayout.setVisibility(View.VISIBLE);
                    info = false;
                } else {
                    mBubbleLayout.setVisibility(View.GONE);
                    info = true;
                }
                break;
        }

    }

    private void hitStartServiceFun(final String url) {

        Utils.showProgress(getActivity());
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("url", url);
                Log.e("urlres", response);
                Utils.stopProgress(getActivity());
                try {
                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                            business_id + "&address_id=" +
                            address_id);


                    //parse data and put all to list
                    JSONObject main_obj = new JSONObject(response);
                    JSONArray output_array = main_obj.getJSONArray("output");
                    String message = output_array.getJSONObject(0).getString("message");
                    // mStartService.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Utils.stopProgress(getActivity());
            }
        });

        Volley.newRequestQueue(getActivity()).add(strReq);


    }

    private void bottomsheetFun() {


        behavior = BottomSheetBehavior.from(mBottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mDown.setVisibility(View.VISIBLE);
                        mUp.setVisibility(View.GONE);

                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mUp.setVisibility(View.VISIBLE);
                        mDown.setVisibility(View.GONE);


                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        //   behavior.setState(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });
    }

    private void initView() {

    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);

    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

    }

    public void hitAPI(final String url) {

        Utils.showProgress(getActivity());
        final StringRequest strReq = new StringRequest(Request.Method.GET,
                url.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("url", url);
                Log.e("urlres", response);
                try {
                    //parse data and put all to list
                    JSONObject main_obj = new JSONObject(response);
                    business_name = main_obj.getString("business_name");
                    no_of_days = main_obj.getString("no_of_days");
                    mPref.setNoOfDays(no_of_days);
                    mBusinessTv.setText(business_name);
                    JSONArray output_array = main_obj.getJSONArray("output");


                    raw_data.clear();
                    consumer_array.clear();
                    for (int i = 0; i < output_array.length(); i++) {
                        JSONObject obj = output_array.getJSONObject(i);
                        staff_id = obj.getString("staff_id");


                      /*  staff_service_started = obj.getString("staff_service_started");

                        Log.d("staff_service_started ", "staff_service_started " + staff_service_started);
                        if (staff_service_started.equalsIgnoreCase("Yes")) {
                            mStartService.setVisibility(View.GONE);
                        } else {
                            mStartService.setVisibility(View.VISIBLE);
                        }*/

                        try {

                            if (obj.getJSONArray("customer").length() == 0) {
                                raw_data.add(new StaffListData(obj.getString("staff_id"),
                                        obj.getString("staff_name")
                                        , obj.getString("hold_id")
                                        , obj.getString("staff_status")
                                        , ""
                                        , ""
                                        , ""
                                        , ""
                                        , ""
                                        , ""
                                        , obj.getString("staff_service_started")
                                ));

                            } else if (obj.getJSONArray("customer").length() == 1) {
                                try {
                                    raw_data.add(new StaffListData(obj.getString("staff_id")
                                            , obj.getString("staff_name")
                                            , obj.getString("hold_id")
                                            , obj.getString("staff_status")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("service_name")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("customer_name")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("estimate_time")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("status")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("appointment_date")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("id")
                                            , obj.getString("staff_service_started")
                                    ));

                                } catch (JSONException e) {
                                    raw_data.add(new StaffListData(obj.getString("staff_id"),
                                            obj.getString("staff_name")
                                            , obj.getString("hold_id")
                                            , obj.getString("staff_status")
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , obj.getString("staff_service_started")
                                    ));

                                }
                            } else {
                                raw_data.add(new StaffListData(obj.getString("staff_id")
                                        , obj.getString("staff_name")
                                        , obj.getString("hold_id")
                                        , obj.getString("staff_status")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("service_name")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("customer_name")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("estimate_time")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("status")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("appointment_date")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("id")
                                        , obj.getString("staff_service_started")
                                ));


                            }
                            JSONArray consumer_Jsonarray = obj.getJSONArray("customer");
                            for (int j = 0; j < consumer_Jsonarray.length(); j++) {
                                JSONObject _obj = consumer_Jsonarray.getJSONObject(j);
                                consumer_array.add(new ConsumerListData(_obj.getString("id")
                                        , _obj.getString("service_name")
                                        , _obj.getString("customer_name")
                                        , _obj.getString("estimate_time")
                                        , _obj.getString("status")
                                        , _obj.getString("appointment_date")
                                        , _obj.getString("token_no")
                                        , ""));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    try {
                        Log.d("MAF", "" + consumer_array.size());
                        mLeftCust.setText(consumer_array.size());
                      //  mTotalCust.setText(consumer_array.get(consumer_array.size()-1).getToken_id());
                    } catch (Exception e) {
                        mTotalCust.setText("0");

                    }
                    liveAdap = new LiveAdap(getActivity(), raw_data, updateListData);
                    staff_list.setAdapter(liveAdap);
                    consumerListAdap = new ConsumerListAdap(getActivity(), consumer_array, updateListData);
                    customer_list.setAdapter(consumerListAdap);
                    Utils.stopProgress(getActivity());

                    // Toast.makeText(getActivity(), "list updated successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    //  Utils.stopProgress(getActivity());
                    Log.e("error", e.getMessage());

                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Utils.stopProgress(getActivity());
            }
        });

        Volley.newRequestQueue(getActivity()).add(strReq);
    }

    @Override
    public void doUpdate(String url) {
        Log.e("url", url);
        post_tag = "getDATA";
        hitAPI(url);
    }
}
