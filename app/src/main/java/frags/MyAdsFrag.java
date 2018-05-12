package frags;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import adapters.ConsumerListAdap;
import adapters.LiveAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import models.ConsumerListData;
import models.StaffListData;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.interfaces.UpdateListData;
import r2stech.lifeoninternet.utils.Utils;

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

    @BindView(R.id.add_cuctomers)
    public TextView mAddCust;


    private BottomSheetBehavior<View> behavior;

    public static String business_id = "", address_id = "";
    public static String staff_id = "";
    public String staff_service_started;

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
        address_id = bundle.getString("address_id");
        business_id = bundle.getString("business_id");
        //  initView();
        post_tag = "getDATA";
        hitAPI("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=bookingList&business_id=" +
                business_id + "&address_id=" +
                address_id);
        bottomsheetFun();
        openBottomSheetFunc();
        return Mroot;

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

    @OnClick({R.id.my_add_back_btn, R.id.add_cuctomers/*, R.id.frag_my_ads_start_service_tv*/})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.my_add_back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.add_cuctomers:

                Bundle _bundle = new Bundle();

                _bundle.putString("address_id", address_id);
                _bundle.putString("business_id", business_id);
                replaceFrag(new AddManualCustomer(), _bundle, AddStaffFrag.class.getName());
                break;

          /*  case R.id.frag_my_ads_start_service_tv:

                Log.d("MAF", "Staff_id" + staff_id);
                  break;*/
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
                    hitAPI("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=bookingList&business_id=" +
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
                                        ,obj.getString("staff_service_started")
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
                                            ,obj.getString("staff_service_started")
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
                                            ,obj.getString("staff_service_started")
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
                                        ,obj.getString("staff_service_started")
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


                    Log.d("MAF", "" + raw_data.size());

                    liveAdap = new LiveAdap(getActivity(), raw_data, updateListData);
                    staff_list.setAdapter(liveAdap);
                    consumerListAdap = new ConsumerListAdap(getActivity(), consumer_array, updateListData);
                    customer_list.setAdapter(consumerListAdap);
                    Utils.stopProgress(getActivity());
                    Toast.makeText(getActivity(), "list updated successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Utils.stopProgress(getActivity());
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
