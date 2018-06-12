package frags;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import atw.lifeoninternet.R;
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

    @BindView(R.id.frag_join_queue_button)
    public Button mfragJoinQueueButton;

    @BindView(R.id.frag_join_queue_service_spinner)
    public Spinner mSpinner;
    @BindView(R.id.frag_join_queue_staff_spinner)
    public Spinner mStaffSpinner;

    private HttpresponseUpd callback;

    private Bundle bundle;
    private String business_id, addredd_id;
    private Snackbar snackbar;
    private String message, status, statuscode, advance_booking_status, no_of_days;
    private String date, business_name, staffwise_booking;
    private String post_tag = "";
    private String[] service_name_array, service_id_array, service_estimate_time_array;
    private List<ServiceList> serviceListt;
    public String spinner_service_id;

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
        date = mCalendarView.getSelectedYear() + "-" + (mCalendarView.getSelectedMonth() + 1) + "-" + mCalendarView.getSelectedDay();
        mHeaderTitle.setText(business_name);
        Log.d("JQF", "date" + date);

        date = GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay());

        try {
            Uri.Builder builder = new Uri.Builder();
            Log.d("JQF","urll"+ Utils.stringBuilder());
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "startBooking")
                    .appendQueryParameter("business_id", business_id)
                    .appendQueryParameter("address_id", addredd_id)
                    .appendQueryParameter("bookingdate", GetDateFormat(mCalendarView.getSelectedYear(), (mCalendarView.getSelectedMonth() + 1), mCalendarView.getSelectedDay())/*mCalendarView.getSelectedDate()*/)
                    .appendQueryParameter("user_id", mPref.getUserId())
                    .appendQueryParameter("cust_id", mPref.getCustId());

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
                        .appendQueryParameter("address_id", addredd_id)
                        .appendQueryParameter("bookingdate", GetDateFormat(year, (month + 1), day))
                        .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                        .appendQueryParameter("cust_id", mPref.getCustId());

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

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinner_service_id = service_id_array[mSpinner.getSelectedItemPosition()];
                String spinner_est_time = service_estimate_time_array[mSpinner.getSelectedItemPosition()];


                if (spinner_est_time.equalsIgnoreCase(null)) {
                    mJoinWaitingTime.setText("");

                } else {
                    mJoinWaitingTime.setText(spinner_est_time);
                }

                Log.d("JQF", "" + spinner_est_time);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static String GetDateFormat(int Year, int Month, int Date) {
        try {
            return (new StringBuilder()
                    .append(Year).append("-")
                    .append((Month)).append("-")
                    .append((Date <= 9 ? "0" : "")).append(Date)).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @OnClick({R.id.frag_join_queue_button, R.id.join_queue_back_btn,
            R.id.frag_join_queue_float_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frag_join_queue_button:
                Bundle bundle = new Bundle();
                bundle.putString("business_id", business_id);
                bundle.putString("addredd_id", addredd_id);
                bundle.putString("service_id", spinner_service_id);
                bundle.putString("appointment_date", date);
                bundle.putString("staff_id", "");

                replaceFrag(new JoinedQueueFrag(), bundle, JoinQueueFrag.class.getName());
                break;

            case R.id.join_queue_back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.frag_join_queue_float_btn:
                showDetailsFloatFun();
                break;
        }
    }


    private void showDetailsFloatFun() {


        Bundle bundle1 = new Bundle();
        replaceFrag(new CompanyDetailsFrag(), bundle1, JoinQueueFrag.class.getName());
    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);

        if (response.contains("Error :")) {
            snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (post_tag.equalsIgnoreCase("selecteddate")) {

            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                message = obj.getString("message");
                no_of_days = obj.getString("no_of_days");
                status = obj.getString("status");
                staffwise_booking = obj.getString("staffwise_booking");
                statuscode = obj.getString("statuscode");

           /*     if (staffwise_booking.equalsIgnoreCase("Yes")) {

                } else {*/
                try {
                    if(!no_of_days.equalsIgnoreCase(null) || no_of_days!=null){

                        mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + Integer.parseInt(no_of_days)));

                    }else {
                        mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + 15));

                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Log.d("JQF", "status" + status + "message" + message);
           /*     if (status.equals("1")) {*/
               /* } else if (status.equals("2")) {
                    mWaitingTv.setText(message);

                } else if (status.equals("3")) {
                    mWaitingTv.setText(message);

                } else*/
                if (status.equals("0")) {
                    mWaitingTv.setText(message);
                    mfragJoinQueueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "Today's booking over try on next booking date !!! ", Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (status.equals("8")) {
                    mWaitingTv.setText(message);
                    mJoinWaitingTime.setVisibility(View.INVISIBLE);
                    mfragJoinQueueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "Already booked !!! ", Toast.LENGTH_LONG).show();
                        }
                    });


                } else {

                    mWaitingTv.setText(message);

                    /* else if (status.equals("5")) {
                    mWaitingTv.setText(message);
                } else if (status.equals("0")) {
                    mWaitingTv.setText(message);
                }*/
                }


                if (staffwise_booking.equalsIgnoreCase("Yes")) {


                } else {
                    service_name_array = new String[arr.length()];
                    service_id_array = new String[arr.length()];
                    service_estimate_time_array = new String[arr.length()];
                    serviceListt = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject service_obj = arr.getJSONObject(i);
                        service_name_array[i] = service_obj.getString("service_name");
                        service_id_array[i] = service_obj.getString("service_id");
                        service_estimate_time_array[i] = service_obj.getString("estimate_time");

                  /*  serviceList.setService_id(service_obj.getString("service_id"));
                    serviceList.setService_name(service_obj.getString("service_name"));
                    serviceListt.add(serviceList);*/
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_spinner_item, service_name_array); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(spinnerArrayAdapter);
                    //   mCalendarView.setTitle(mCalendarView.getSelectedDate().toString());
                    //  mCalendarView.getChildAt();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("selectmanualdate")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                message = obj.getString("message");
                no_of_days = obj.getString("no_of_days");
                status = obj.getString("status");
                statuscode = obj.getString("statuscode");
              /*  if (status.equals("1")) {
                */
                Log.d("JQF1", "status" + status + "message" + message);
              /*  } else if (status.equals("2")) {
                    mWaitingTv.setText(message);
                } else if (status.equals("3")) {
                    mWaitingTv.setText(message);
                } else*/
                Calendar cCal = Calendar.getInstance();

               int cYear = cCal.get(Calendar.YEAR);
                int cMonth = cCal.get(Calendar.MONTH);
                int cDay = cCal.get(Calendar.DAY_OF_MONTH);
                getCountOfDays(cDay+"/"+(cMonth+1)+"/"+cYear,  (mCalendarView.getSelectedDay()+"/"+(mCalendarView.getSelectedMonth()+1)+"/"+mCalendarView.getSelectedYear()));

                Log.d("selecte date",""+cDay+"/"+(cMonth+1)+"/"+cYear+"datte"+ (mCalendarView.getSelectedDay()+"/"+(mCalendarView.getSelectedMonth()+1)+"/"+mCalendarView.getSelectedYear()));


                if (status.equals("0")) {
                    mWaitingTv.setText(message);
                    mfragJoinQueueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "Today's booking over try on next booking date !!! ", Toast.LENGTH_LONG).show();
                        }
                    });


                } else if (status.equals("8")) {
                    mWaitingTv.setText(message);
                    mJoinWaitingTime.setVisibility(View.INVISIBLE);
                    mfragJoinQueueButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), "Already booked !!! ", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    mWaitingTv.setText(message);
                    /* else if (status.equals("5")) {
                    mWaitingTv.setText(message);
                } else if (status.equals("0")) {
                    mWaitingTv.setText(message);
                }
*/
                }
                if (staffwise_booking.equalsIgnoreCase("Yes")) {

                    JSONArray jsonArray = obj.getJSONArray("staff");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject service_obj = arr.getJSONObject(i);
                     /*   staff_name_array[i] = service_obj.getString("staff_name");
                        staff_id_array[i] = service_obj.getString("staff_id");
*/
                  /*  serviceList.setService_id(service_obj.getString("service_id"));
                    serviceList.setService_name(service_obj.getString("service_name"));
                    serviceListt.add(serviceList);*/
                    }

                } else {
                    service_name_array = new String[arr.length()];
                    service_id_array = new String[arr.length()];
                    service_estimate_time_array = new String[arr.length()];
                    serviceListt = new ArrayList<>();
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject service_obj = arr.getJSONObject(i);
                        service_name_array[i] = service_obj.getString("service_name");
                        service_id_array[i] = service_obj.getString("service_id");
                        service_estimate_time_array[i] = service_obj.getString("estimate_time");

                  /*  serviceList.setService_id(service_obj.getString("service_id"));
                    serviceList.setService_name(service_obj.getString("service_name"));
                    serviceListt.add(serviceList);*/
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_spinner_item, service_name_array); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinner.setAdapter(spinnerArrayAdapter);
                }
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

            int dayDiff= Integer.parseInt(no_of_days)-dayCount;

            if(!no_of_days.equalsIgnoreCase(null) || no_of_days!=null){

                mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + dayDiff));

            }else {
                mCalendarView.setLastVisibleDate(mCalendarView.getSelectedYear(), mCalendarView.getSelectedMonth(), (mCalendarView.getSelectedDay() + 15));

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Log.d("JQF","diff"+diff+"dayCount"+dayCount);
        return ("" + (int) dayCount + " Days");
    }
}
