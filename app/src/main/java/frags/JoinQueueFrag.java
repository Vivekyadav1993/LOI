package frags;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.google.gson.JsonArray;
import com.wefika.calendar.CollapseCalendarView;
import com.wefika.calendar.manager.CalendarManager;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import adapters.JoinAppointmentAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.UserAuthACtivity;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.ServiceList;
import models.ServiceTime;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinQueueFrag extends HelperFrags implements HttpresponseUpd {


    @BindView(R.id.frag_toolbar_title_tv)
    public TextView mHeaderTitle;

    @BindView(R.id.frag_join_queue_calendar_view)
    public DatePickerTimeline mCalendarView;

    @BindView(R.id.frag_join_queue_waiting_rl)
    public RelativeLayout mWaitingRl;

    @BindView(R.id.frag_join_queue_waiting_time_tv)
    public TextView mJoinWaitingTime;

    @BindView(R.id.frag_join_queue_pre_book_amount_tv)
    public TextView mPreBookAmount;

    @BindView(R.id.frag_join_queue_float_btn)
    public FloatingActionButton mFloatBtn;

    @BindView(R.id.waiting_time_text_tv)
    public TextView mWaitingTv;

    @BindView(R.id.morning_time_tv)
    public TextView morning_time_tv;

    @BindView(R.id.evening_time_tv)
    public TextView evening_time_tv;

    @BindView(R.id.frag_join_queue_morning_tv)
    public TextView frag_join_queue_morning_tv;

    @BindView(R.id.frag_join_queue_button)
    public Button mfragJoinQueueButton;

    @BindView(R.id.frag_join_queue_mor_eve_button)
    public Button mfragJoinQueueMorEveButton;

    @BindView(R.id.frag_join_queue_rv)
    public RecyclerView frag_join_queue_rv;

    @BindView(R.id.frag_join_queue_evening_rv)
    public RecyclerView frag_join_queue_evening_rv;

    private JoinAppointmentAdapter joinAppointmentAdapter;

    @BindView(R.id.frag_join_queue_service_spinner)
    public Spinner mSpinner;
    @BindView(R.id.frag_join_queue_staff_spinner)
    public Spinner mStaffSpinner;
    @BindView(R.id.frag_join_queue_address_spinner)
    public Spinner mAddressSpinner;

    @BindView(R.id.mor_eve_ll)
    public LinearLayout mLl;

    @BindView(R.id.mor_ll)
    public LinearLayout morLl;
    @BindView(R.id.eve_ll)
    public LinearLayout eveLl;

    boolean shift_check = true;

    private HttpresponseUpd callback;

    private Bundle bundle;
    private String business_id, addredd_id;
    private Snackbar snackbar;
    private String message, status, statuscode, advance_booking_status, no_of_days;
    private String date, business_name, staffwise_booking;
    private String post_tag = "";
    private String[] service_name_array, service_id_array, service_estimate_time_array, service_staff_array, service_staff_id_array;
    private String[] service_address_array, service_address_id_array;
    private List<ServiceList> serviceListt;
    public String spinner_service_id, spinner_service_address_id, swift_time;
    public boolean morning_selected = false, evening_selected = true;
    private Sharedpreferences mPref;

    public JoinQueueFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_queue, container, false);

        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        callback = this;
        bundle = getArguments();
        business_id = bundle.getString("business_id");
        addredd_id = bundle.getString("addredd_id");
        business_name = bundle.getString("business_name");
        mHeaderTitle.setText(business_name);
        Log.d("JQF", "date" + date);

        date = GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay());

        try {
            Uri.Builder builder = new Uri.Builder();
            Log.d("JQF", "urll" + Utils.stringBuilder());
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "startBooking")
                    .appendQueryParameter("business_id", business_id)
                   /* .appendQueryParameter("address_id", addredd_id)*/
                    .appendQueryParameter("bookingdate", GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay())/*mCalendarView.getSelectedDate()*/);

            Log.e("stafflist", builder.build().toString());
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


        mCalendarView.setOnDateSelectedListener(new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {


                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "startBooking")
                        .appendQueryParameter("business_id", business_id)
                        //   .appendQueryParameter("address_id", addredd_id)
                        .appendQueryParameter("bookingdate", GetDateFormat(year, (month + 1), day))
                     /*   .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                        .appendQueryParameter("cust_id", mPref.getCustId())*/;

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "selectmanualdate";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        mCalendarView.setFirstVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), mCalendarView.getSelectedDay());

        mAddressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinner_service_address_id = service_address_id_array[mAddressSpinner.getSelectedItemPosition()];

                try {
                    Uri.Builder builder = new Uri.Builder();
                    Log.d("JQF", "urll" + Utils.stringBuilder());
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "startBooking")
                            .appendQueryParameter("business_id", business_id)
                       /* .appendQueryParameter("address_id", addredd_id)*/
                            .appendQueryParameter("bookingdate", GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay())/*mCalendarView.getSelectedDate()*/)
                            .appendQueryParameter("address_id", spinner_service_address_id)
                    /*    .appendQueryParameter("address_id", mPref.getUserId())
                        .appendQueryParameter("cust_id", mPref.getCustId())*/;

                    Log.e("servicelist", builder.build().toString());
                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "get_service";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_service_address_id = service_address_id_array[mAddressSpinner.getSelectedItemPosition()];
                spinner_service_id = service_id_array[mSpinner.getSelectedItemPosition()];
                try {
                    Uri.Builder builder = new Uri.Builder();
                    Log.d("JQF", "urll" + Utils.stringBuilder());
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "startBooking")
                            .appendQueryParameter("business_id", business_id)
                       /* .appendQueryParameter("address_id", addredd_id)*/
                            .appendQueryParameter("bookingdate", GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay())/*mCalendarView.getSelectedDate()*/)
                            .appendQueryParameter("address_id", spinner_service_address_id)
                            .appendQueryParameter("service_id", spinner_service_id)
                           /* .appendQueryParameter("service_id", spinner_service_id)*/
                        /*.appendQueryParameter("address_id", mPref.getUserId())*/
                            .appendQueryParameter("cust_id", mPref.getCustId());

                    Log.e("serviceDetails", builder.build().toString());
                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        Utils.showProgress(getContext());
                        post_tag = "detail_service";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        frag_join_queue_rv.setHasFixedSize(true);
        frag_join_queue_rv.setLayoutManager(new GridLayoutManager(getContext(), 5));
        joinAppointmentAdapter = new JoinAppointmentAdapter(getActivity());
        frag_join_queue_rv.setAdapter(joinAppointmentAdapter);

        frag_join_queue_evening_rv.setHasFixedSize(true);
        frag_join_queue_evening_rv.setLayoutManager(new GridLayoutManager(getContext(), 5));
        joinAppointmentAdapter = new JoinAppointmentAdapter(getActivity());
        frag_join_queue_evening_rv.setAdapter(joinAppointmentAdapter);

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

    @SuppressLint("ResourceType")
    @OnClick({R.id.frag_join_queue_button, R.id.join_queue_back_btn,
            R.id.frag_join_queue_float_btn, R.id.frag_join_queue_mor_eve_button, R.id.mor_ll, R.id.eve_ll, R.id.frag_join_queue_morning_tv,
            R.id.frag_join_queue_evening_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_join_queue_button:

                joinQueueFun();
                break;

            case R.id.frag_join_queue_morning_tv:

                if (morning_selected) {
                    frag_join_queue_rv.setVisibility(View.VISIBLE);
                    morning_selected = false;

                } else {
                    frag_join_queue_rv.setVisibility(View.GONE);
                    morning_selected = true;
                }


                break;
            case R.id.frag_join_queue_evening_tv:

                if (evening_selected) {
                    frag_join_queue_evening_rv.setVisibility(View.VISIBLE);
                    evening_selected = false;

                } else {
                    frag_join_queue_evening_rv.setVisibility(View.GONE);
                    evening_selected = true;
                }


                break;

            case R.id.join_queue_back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.frag_join_queue_float_btn:
                showDetailsFloatFun();
                break;

            case R.id.frag_join_queue_mor_eve_button:
                joinQueueMorEveFun();
                break;
            case R.id.mor_ll:
                morLl.setBackgroundResource(R.color.blue);
                eveLl.setBackgroundResource(R.color.white);
                evening_time_tv.setTextColor(R.color.black);
                morning_time_tv.setTextColor(R.color.white);
                shift_check = true;
                break;
            case R.id.eve_ll:
                morLl.setBackgroundResource(R.color.white);
                eveLl.setBackgroundResource(R.color.blue);
                evening_time_tv.setTextColor(R.color.white);
                morning_time_tv.setTextColor(R.color.black);

                shift_check = false;
                break;
        }
    }

    private void joinQueueMorEveFun() {

        date = GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay());

        Log.d("JQF", "date" + date);
        if (shift_check) {
            swift_time = "morning";
        } else {
            swift_time = "evening";
        }
        Log.d("JQF", "swift_time" + swift_time);

        Bundle bundle = new Bundle();
        bundle.putString("business_id", business_id);
        bundle.putString("addredd_id", spinner_service_address_id);
        bundle.putString("service_id", spinner_service_id);
        bundle.putString("appointment_date", date);
        bundle.putString("sub_date", swift_time);
        bundle.putString("staff_id", "");
        if (mPref.getUserId().equals(null) || mPref.getUserId().equals("")) {
            Intent intent = new Intent(getContext(), UserAuthACtivity.class);
            startActivity(intent);

        } else {
            replaceFrag(new JoinedQueueFrag(), bundle, JoinQueueFrag.class.getName());
        }


    }

    private void joinQueueFun() {
        date = GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay());
        Log.d("JQF", "date2" + date);

        if (status.equals("0")) {
            Toast.makeText(getActivity(), "Booking not started yet !!! ", Toast.LENGTH_LONG).show();

        } else if (status.equals("8")) {
            Toast.makeText(getActivity(), "Already booked !!! ", Toast.LENGTH_LONG).show();

        } else if (status.equals("2")) {
            Toast.makeText(getActivity(), "All slots are booked you are in waiting !!! ", Toast.LENGTH_LONG).show();

        } else {

            Bundle bundle = new Bundle();
            bundle.putString("business_id", business_id);
            bundle.putString("addredd_id", spinner_service_address_id);
            bundle.putString("service_id", spinner_service_id);
            bundle.putString("appointment_date", date);
            bundle.putString("sub_date", swift_time);
            bundle.putString("staff_id", "");
            if (mPref.getUserId().equals(null) || mPref.getUserId().equals("")) {
                Intent intent = new Intent(getContext(), UserAuthACtivity.class);
                startActivity(intent);

            } else {
                replaceFrag(new JoinedQueueFrag(), bundle, JoinQueueFrag.class.getName());
            }
        }

    }


    private void showDetailsFloatFun() {


        Bundle bundle1 = new Bundle();
        replaceFrag(new CompanyDetailsFrag(), bundle1, JoinQueueFrag.class.getName());
    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);
        Utils.stopProgress(getContext());
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (post_tag.equalsIgnoreCase("selecteddate")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray json_array = obj.getJSONArray("address");
                message = obj.getString("message");
                //    no_of_days = obj.getString("no_of_days");
                status = obj.getString("status");
                //  staffwise_booking = obj.getString("staffwise_booking");
                statuscode = obj.getString("statuscode");

                mWaitingTv.setText(message);

                service_address_array = new String[json_array.length()];
                service_address_id_array = new String[json_array.length()];

                for (int i = 0; i < json_array.length(); i++) {
                    JSONObject address_obj = json_array.getJSONObject(i);
                    service_address_array[i] = address_obj.getString("title");
                    service_address_id_array[i] = address_obj.getString("id");
                    no_of_days = address_obj.getString("no_of_days_advance");
                    JSONArray arr = address_obj.getJSONArray("service");
                    service_name_array = new String[arr.length()];
                    service_id_array = new String[arr.length()];
                    serviceListt = new ArrayList<>();

                }

                if (!no_of_days.equalsIgnoreCase(null) || no_of_days != null) {

                    mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + Integer.parseInt(no_of_days)));

                } else {
                    mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + 15));

                }
                Log.d("JQF", "service_name_array" + service_name_array.length + "service_address_array" + service_address_array);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, service_address_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mAddressSpinner.setAdapter(spinnerArrayAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("selectmanualdate")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray json_array = obj.getJSONArray("address");
                message = obj.getString("message");
                //    no_of_days = obj.getString("no_of_days");
                status = obj.getString("status");
                //  staffwise_booking = obj.getString("staffwise_booking");
                statuscode = obj.getString("statuscode");

                mWaitingTv.setText(message);

                service_address_array = new String[json_array.length()];
                service_address_id_array = new String[json_array.length()];

                for (int i = 0; i < json_array.length(); i++) {
                    JSONObject address_obj = json_array.getJSONObject(i);
                    service_address_array[i] = address_obj.getString("title");
                    service_address_id_array[i] = address_obj.getString("id");
                    no_of_days = address_obj.getString("no_of_days_advance");
                    JSONArray arr = address_obj.getJSONArray("service");
                    service_name_array = new String[arr.length()];
                    service_id_array = new String[arr.length()];
                    serviceListt = new ArrayList<>();

                }
                Calendar cCal = Calendar.getInstance();

                int cYear = cCal.get(Calendar.YEAR);
                int cMonth = cCal.get(Calendar.MONTH);
                int cDay = cCal.get(Calendar.DAY_OF_MONTH);
                getCountOfDays(cDay + "/" + (cMonth + 1) + "/" + cYear, (mCalendarView.getSelectedDay() + "/" + (mCalendarView.getSelectedMonth() + 1) + "/" + mCalendarView.getSelectedYear()));

                Log.d("selecte date", "" + cDay + "/" + (cMonth + 1) + "/" + cYear + "datte" + (mCalendarView.getSelectedDay() + "/" + (mCalendarView.getSelectedMonth() + 1) + "/" + mCalendarView.getSelectedYear()));


                mWaitingTv.setText(message);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, service_address_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mAddressSpinner.setAdapter(spinnerArrayAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("get_service")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray json_array = obj.getJSONArray("address");
                message = obj.getString("message");
                status = obj.getString("status");
                statuscode = obj.getString("statuscode");

                JSONObject address_obj = json_array.getJSONObject(0);

                JSONArray arr = address_obj.getJSONArray("service");
                service_name_array = new String[arr.length()];
                service_id_array = new String[arr.length()];
                serviceListt = new ArrayList<>();
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject service_obj = arr.getJSONObject(j);
                    service_name_array[j] = service_obj.getString("service_name");
                    service_id_array[j] = service_obj.getString("service_id");


                }


                ArrayAdapter<String> spinnerServiceArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, service_name_array); //selected item will look like a spinner set from XML
                spinnerServiceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(spinnerServiceArrayAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("detail_service")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray json_array = obj.getJSONArray("address");
                message = obj.getString("message");
                status = obj.getString("status");
                statuscode = obj.getString("statuscode");

                mWaitingTv.setText(message);


                JSONObject address_obj = json_array.getJSONObject(0);

                JSONArray arr = address_obj.getJSONArray("service");
                JSONObject service_obj = arr.getJSONObject(0);
                //  JSONArray staff_array = service_obj.getJSONArray("staff");

                String serv_morning = service_obj.getString("serv_mor_est_time");
                String serv_evening = service_obj.getString("serv_eve_est_time");


                if (!serv_morning.equals("") && !serv_evening.equals("")) {
                    mLl.setVisibility(View.VISIBLE);
                    mJoinWaitingTime.setVisibility(View.GONE);
                    mfragJoinQueueMorEveButton.setVisibility(View.VISIBLE);
                    mfragJoinQueueButton.setVisibility(View.GONE);
                    evening_time_tv.setText(serv_evening);
                    morning_time_tv.setText(serv_morning);
                } else {
                    mLl.setVisibility(View.GONE);
                    mJoinWaitingTime.setVisibility(View.VISIBLE);
                    mfragJoinQueueMorEveButton.setVisibility(View.GONE);
                    mfragJoinQueueButton.setVisibility(View.VISIBLE);

                    String show_time = "";
                    if (serv_morning.equals("")) {
                        show_time = serv_evening;
                        swift_time = "evening";
                    } else {
                        show_time = serv_morning;
                        swift_time = "morning";
                    }
                    mJoinWaitingTime.setText(show_time);

                }

                //  Log.d("JQF","staff_array"+staff_array.length()+"time");

            /*    List<ServiceTime> serviceTimes = new ArrayList<>();
                for(int i=0;i< staff_array.length();i++){
                    JSONObject staff_obj = staff_array.getJSONObject(i);

                    ServiceTime serviceTime= new ServiceTime();
                    serviceTime.setService_morning_time(staff_obj.getString("morning_est_time"));
                    serviceTime.setService_evening_time(staff_obj.getString("evening_est_time"));

                    serviceTimes.add(serviceTime);
                }
                mJoinWaitingTime.setText(serviceTimes.get(0).getService_morning_time());

                Log.d("JQF","serviceTimes"+serviceTimes.size()+"time"+serviceTimes.get(0).getService_morning_time());
*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public String getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireCovertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireCovertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(todayWithZeroTime);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar todayCal = Calendar.getInstance();
    int todayYear = todayCal.get(Calendar.YEAR);
    int today = todayCal.get(Calendar.MONTH);
    int todayDay = todayCal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireCovertedDate);

        int eYear = eCal.get(Calendar.YEAR);
        int eMonth = eCal.get(Calendar.MONTH);
        int eDay = eCal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = 0;
        int dayCount = 0;
        try {
            diff = date2.getTimeInMillis() - date1.getTimeInMillis();

            dayCount = (int) diff / (24 * 60 * 60 * 1000);

            int dayDiff = Integer.parseInt(no_of_days) - dayCount;

            if (!no_of_days.equalsIgnoreCase(null) || no_of_days != null) {

                mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + dayDiff));

            } else {
                mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + 15));

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Log.d("JQF", "diff" + diff + "dayCount" + dayCount);
        return ("" + (int) dayCount + " Days");
    }
}
