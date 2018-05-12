package frags;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import adapters.MyAdsAddressAdapter;
import adapters.MyCustomerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.businesslist.Output;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.MyAdsAddress;
import models.mycustomer.Booking;
import models.mycustomer.MyCustomer;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Utils;

import static frags.MyAdsFrag.address_id;
import static frags.MyAdsFrag.business_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCustomerFrag extends HelperFrags implements HttpresponseUpd {

    @BindView(R.id.my_customer_frag_recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.frag_my_customer_calendar_view)
    public DatePickerTimeline mDatePicker;

    @BindView(R.id.my_customer_frag_no_tv)
    public TextView mNoCustomerTv;

    @BindView(R.id.frag_my_customer_service_spinner)
    public Spinner mServiceSpinner;

    @BindView(R.id.frag_my_customer_staff_spinner)
    public Spinner mStaffSpinner;
    @BindView(R.id.frag_my_customer_all_cancel_cb)
    public CheckBox mCheckBox;


    private ArrayList<Booking> bookingList;
    private MyCustomerAdapter mMyCustomerAdapter;
    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private String date;
    private String businessid, addressid;
    private String post_tag = "";

    private Dialog mDialougeBox;

    private Bundle bundle;


    private Boolean checked_status=true;

    public MyCustomerFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_customer, container, false);
        ButterKnife.bind(this, view);
        bundle = this.getArguments();
        businessid = bundle.getString("businessId");
        addressid = bundle.getString("addressId");

        initilizerView();
        return view;
    }

    @OnClick({R.id.frag_my_customer_cancel_fb})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.frag_my_customer_cancel_fb:

                openPopUpFun();
                break;


        }

    }

    private void openPopUpFun() {

        mDialougeBox = new Dialog(getContext());
        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialougeBox.setContentView(R.layout.item_history_dialouge);
        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
        mDialougeBox.show();

    }

    private void initilizerView() {



        bookingList = new ArrayList<>();
        callback = this;
        date = mDatePicker.getSelectedYear() + "-" + (mDatePicker.getSelectedMonth() + 1) + "-" + mDatePicker.getSelectedDay();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "mycustomerList")
                .appendQueryParameter("business_id", businessid)
                .appendQueryParameter("address_id", addressid)
                .appendQueryParameter("apt_date", date);

        String myUrl = builder.build().toString();
        Log.e("urlgetLsiAdd", myUrl);

        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "defaultdate";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

               mMyCustomerAdapter.selectAll(b);
            }
        });

        mDatePicker.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {
                bookingList.clear();
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "mycustomerList")
                        .appendQueryParameter("business_id", businessid)
                        .appendQueryParameter("address_id", addressid)
                        .appendQueryParameter("apt_date", year + "-" + (month + 1) + "-" + day);

                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "selectmanualdate";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        mDatePicker.setFirstVisibleDate(mDatePicker.getSelectedYear(), mDatePicker.getSelectedMonth(), mDatePicker.getSelectedDay());
        // mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + 5));


    }

    @Override
    public void getResponse(String response) {

        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(getView(), "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (post_tag.equalsIgnoreCase("defaultdate")) {
            try {
                JSONObject main_obj = new JSONObject(response);
                Log.d("MCF", "" + main_obj.getString("message"));

                if (main_obj.getString("message").equalsIgnoreCase("No Record Found")) {
                    mNoCustomerTv.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);

                } else {
                    Gson gson = new Gson();
                    mNoCustomerTv.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    try {
                        bookingList.clear();
                        MyCustomer myCustomer = gson.fromJson(response, MyCustomer.class);
                        for (int j = 0; j < myCustomer.getOutput().getBooking().size(); j++) {
                            Booking booking = new Booking();
                            booking.setUserName(myCustomer.getOutput().getBooking().get(j).getUserName());
                            booking.setServiceName(myCustomer.getOutput().getBooking().get(j).getServiceName());
                            booking.setBookingId(myCustomer.getOutput().getBooking().get(j).getBookingId());
                            booking.setEstimateTime(myCustomer.getOutput().getBooking().get(j).getEstimateTime());
                            bookingList.add(booking);
                        }
                        mDatePicker.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
                            @Override
                            public CharSequence getLabel(Calendar calendar, int index) {
                                return Integer.toString(bookingList.size());
                            }
                        });
                        mRecyclerView.setHasFixedSize(true);
                        mMyCustomerAdapter = new MyCustomerAdapter(this, bookingList);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mRecyclerView.setAdapter(mMyCustomerAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("selectmanualdate")) {
            try {
                JSONObject main_obj = new JSONObject(response);
                Log.d("MCF", "" + main_obj.getString("message"));

                if (main_obj.getString("message").equalsIgnoreCase("No Record Found")) {
                    mNoCustomerTv.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    Gson gson = new Gson();
                    mNoCustomerTv.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    try {
                        bookingList.clear();
                        MyCustomer myCustomer = gson.fromJson(response, MyCustomer.class);
                        for (int j = 0; j < myCustomer.getOutput().getBooking().size(); j++) {
                            Booking booking = new Booking();
                            booking.setUserName(myCustomer.getOutput().getBooking().get(j).getUserName());
                            booking.setBookingId(myCustomer.getOutput().getBooking().get(j).getBookingId());
                            booking.setServiceName(myCustomer.getOutput().getBooking().get(j).getServiceName());
                            booking.setEstimateTime(myCustomer.getOutput().getBooking().get(j).getEstimateTime());
                            bookingList.add(booking);

                        }
                        mRecyclerView.setHasFixedSize(true);
                        mMyCustomerAdapter = new MyCustomerAdapter(this, bookingList);

                        mDatePicker.setDateLabelAdapter(new MonthView.DateLabelAdapter() {
                            @Override
                            public CharSequence getLabel(Calendar calendar, int index) {
                                //  return Integer.toString(calendar.get(Calendar.MONTH) + 1) + "/" + (calendar.get(Calendar.YEAR) % 2000);
                                return Integer.toString(bookingList.size());
                            }
                        });
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mRecyclerView.setAdapter(mMyCustomerAdapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
