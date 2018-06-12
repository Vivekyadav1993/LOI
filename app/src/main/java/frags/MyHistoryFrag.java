package frags;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import adapters.CustomerLandingRecyclerViewAdapter;
import adapters.MyHsitoryAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import atw.lifeoninternet.views.AppointmentDashbord;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.History;

import static helper.AppUtils.dialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyHistoryFrag extends HelperFrags implements HttpresponseUpd {

    private MyHsitoryAdapter mMyHsitoryAdapter;

    @BindView(R.id.my_history_frag_recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.frag_calendar_view)
    public DatePickerTimeline mDatePickerTimeline;

  @BindView(R.id.no_history_tv)
    public TextView mNoHistory;

    private HttpresponseUpd callback;
    private ArrayList<History> list;
    private Snackbar snackbar;
    private String date;
    private Sharedpreferences mPref;
    private String post_tag,businessid,addressid,cust_name;
    private String businessname, servicename, appointmentdate, status, est_time;
    private FragmentManager mFragmentManager;
    private AppointmentDashbord activity;
    private Bundle bundle;

    public MyHistoryFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_history, container, false);
        ButterKnife.bind(this, view);
        activity = (AppointmentDashbord) getActivity();
        mFragmentManager = activity.getSupportFragmentManager();
       /* bundle = this.getArguments();
        businessid = bundle.getString("businessId");
        addressid = bundle.getString("addressId");
*/
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initilizerView();


    }


    private void initilizerView() {

        mPref = Sharedpreferences.getUserDataObj(getActivity());
        callback = MyHistoryFrag.this;
        list = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        date = mDatePickerTimeline.getSelectedYear() + "-" + (mDatePickerTimeline.getSelectedMonth() + 1) + "-" + mDatePickerTimeline.getSelectedDay();

        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "appointmentList")
                    .appendQueryParameter("type", "history")
                    .appendQueryParameter("business_id", mPref.getSelecttBusinessId())
                    .appendQueryParameter("business_address_id",mPref.getSelectAddressId())
                    .appendQueryParameter("bookingdate", GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)));
            String myUrl = builder.build().toString();
            Log.e("selected", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "selecteddate";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mDatePickerTimeline.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                //  bookingList.clear();
                try {
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "appointmentList")
                            .appendQueryParameter("business_id", mPref.getSelecttBusinessId())
                            .appendQueryParameter("business_address_id", mPref.getSelectAddressId())
                            .appendQueryParameter("bookingdate", GetDateFormat(year, (month + 1), day));

                    String myUrl = builder.build().toString();
                    Log.e("select", myUrl);

                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "selectmanualdate";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);

                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mDatePickerTimeline.setLastVisibleDate(mDatePickerTimeline.getSelectedYear() , (mDatePickerTimeline.getSelectedMonth() + 1) , mDatePickerTimeline.getSelectedDay());

    }

    @Override
    public void getResponse(String response) {
        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(getView(), "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (post_tag.equals("selecteddate")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                list.clear();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject objt = arr.getJSONObject(i);
                    businessname = objt.getString("business_name");
                    servicename = objt.getString("service_name");
                    appointmentdate = objt.getString("appointment_date");
                    status = objt.getString("status");
                    est_time = objt.getString("estimate_time");
                    cust_name= objt.getString("customer_name");
                    History history = new History();
                    history.setBusinessname(cust_name);
                    history.setServicename(servicename);
                    history.setAppointmentdate(appointmentdate);
                    history.setStatus(status);
                    history.setEstimatetime(est_time);
                    list.add(history);

                }
                mNoHistory.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                Log.d("MHF","history list size"+list.size());
                mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setHasFixedSize(true);
                    mMyHsitoryAdapter = new MyHsitoryAdapter(getActivity(), list);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mMyHsitoryAdapter);

            } catch (JSONException e) {

                mNoHistory.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                }


        } else if (post_tag.equals("selectmanualdate")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                list.clear();

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject objt = arr.getJSONObject(i);
                    businessname = objt.getString("business_name");
                    servicename = objt.getString("service_name");
                    appointmentdate = objt.getString("appointment_date");
                    status = objt.getString("status");
                    est_time = objt.getString("estimate_time");
                    cust_name= objt.getString("customer_name");
                    History history = new History();
                    history.setBusinessname(cust_name);
                    history.setServicename(servicename);
                    history.setAppointmentdate(appointmentdate);
                    history.setStatus(status);
                    history.setEstimatetime(est_time);
                    list.add(history);

                }

                mNoHistory.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                Log.d("MHF", "listSize1" + list.size());
                    mRecyclerView.setHasFixedSize(true);
                    mMyHsitoryAdapter = new MyHsitoryAdapter(getActivity(), list);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mMyHsitoryAdapter);


            } catch (JSONException e) {
                mNoHistory.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);

            }

        }
    }

    public static String GetDateFormat(int Year, int Month, int Date) {
        try {
            return (new StringBuilder()
                    .append(Year).append("-")
                    .append((Month <= 9 ? "0" : "")).append(Month + "-")
                    .append((Date <= 9 ? "0" : "")).append(Date)).toString();
        } catch (Exception e) {
            return "";
        }
    }
   /* @OnClick({R.id.my_history_back_btn})
    public void onClick(View v) {
        switch (v.getId()) {
              case R.id.my_history_back_btn:
                getActivity().onBackPressed();
                break;

        }
    }*/
}
