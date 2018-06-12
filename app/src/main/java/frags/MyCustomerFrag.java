package frags;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.badoualy.datepicker.DatePickerTimeline;
import com.github.badoualy.datepicker.MonthView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapters.BusinessTimeAdapter;
import adapters.FaqChildAdapter;
import adapters.MyAdsAddressAdapter;
import adapters.MyCustomerAdapter;
import adapters.ServiceStaffAdapter;
import adapters.ShowStaffAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import atw.lifeoninternet.views.AppointmentDashbord;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.CancelCustomerSelection;
import models.StaffSelecData;
import models.addetailsEdit.DetailsEditModel;
import models.addetailsEdit.Staff;
import models.businesslist.Output;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.MyAdsAddress;
import models.mycustomer.Booking;
import models.mycustomer.MyCustomer;

import static frags.MyAdsFrag.address_id;
import static frags.MyAdsFrag.business_id;
import static helper.AppUtils.dialog;

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
    private String[] service_name_array, service_id_array;
    private String[] service_staff_array, service_staff_id_array;

    private Sharedpreferences mPref;
    private Boolean checked_status = true;
    private FragmentManager mFragmentManager;
    private AppointmentDashbord activity;
    private String[] industry_name_array, industry_id_array;
    private String spinner_service_id, spinner_staff_service_id;

    public static ArrayList<CancelCustomerSelection> customerCancelArray;
    private String radioReason;

    private JSONArray arrForA;

    public MyCustomerFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_customer, container, false);
        ButterKnife.bind(this, view);
        bundle = this.getArguments();
        mPref = Sharedpreferences.getUserDataObj(getActivity());
      /*  businessid = bundle.getString("businessId");
        addressid = bundle.getString("addressId");
*/
        arrForA = new JSONArray();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initilizerView();
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


        Log.d("MCF", "array_size" + customerCancelArray.size());
        //  Log.d("MCF", "status11" + customerCancelArray.get(1).getStatus());

      /*  if (customerCancelArray.size() == 0) {
        } else {
            try {
                for (int i = 0; i < customerCancelArray.size(); i++) {
                    Log.d("MCF", "status" + customerCancelArray.get(i).getStatus());

                    JSONObject itemA = new JSONObject();
                    itemA.put("service_id", customerCancelArray.get(i).getBooking_id());
                    Log.d("MCF", "status1" + customerCancelArray.get(i).getStatus());

                    if (customerCancelArray.get(i).getStatus().equals("Yes")) {
                        arrForA.put(itemA);
                    } else {

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("MCF", "arrForA--->" + arrForA.toString());
        }
*/
        TextView submitBtn = (TextView) mDialougeBox.findViewById(R.id.booking_cancel_submit_tv);
        TextView cancelBtn = (TextView) mDialougeBox.findViewById(R.id.booking_cancel_cancel_tv);
        final EditText reasonInput = (EditText) mDialougeBox.findViewById(R.id.cancel_booking_et);

        RadioGroup radioGroup = (RadioGroup) mDialougeBox.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = mDialougeBox.findViewById(R.id.reason_1);
        RadioButton radioButton2 = mDialougeBox.findViewById(R.id.reason_2);

        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        RadioButton radioSelectButton = (RadioButton) mDialougeBox.findViewById(selectedId);

        radioReason = radioSelectButton.getText().toString();


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioReason.equalsIgnoreCase(null) && reasonInput.getText().toString().equalsIgnoreCase(null)) {

                    Toast.makeText(activity, "Select any reason", Toast.LENGTH_SHORT).show();
                }
                if (customerCancelArray.size() == 0) {

                    Toast.makeText(activity, "Heee", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        for (int i = 0; i < customerCancelArray.size(); i++) {
                            JSONObject itemA = new JSONObject();
                            itemA.put("service_id", customerCancelArray.get(i).getBooking_id());
                            Log.d("MCF", "status1" + customerCancelArray.get(i).getStatus());

                            if (customerCancelArray.get(i).getStatus().equals("Yes")) {
                                arrForA.put(itemA);

                            } else {

                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("MCF", "arrForA" + arrForA);

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "cancelBooking")
                            .appendQueryParameter("booking_id", arrForA.toString())
                            .appendQueryParameter("reason", reasonInput.getText().toString());

                    String myUrl = builder.build().toString();
                    Log.e("cancelUrl", myUrl);

                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "bookingcancel";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);

                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialougeBox.dismiss();
            }
        });
    }

    private void initilizerView() {
        arrForA = new JSONArray();
        customerCancelArray = new ArrayList<>();
        bookingList = new ArrayList<>();
        callback = MyCustomerFrag.this;

        hitMainApi();

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
                        .appendQueryParameter("business_id", mPref.getSelecttBusinessId())
                        .appendQueryParameter("address_id", mPref.getSelectAddressId())
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

        mServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinner_service_id = service_id_array[mServiceSpinner.getSelectedItemPosition()];
                //  String spinner_est_time = service_estimate_time_array[mSpinner.getSelectedItemPosition()];

                if (mServiceSpinner.getSelectedItemPosition() == 0) {
                    mStaffSpinner.setVisibility(View.INVISIBLE);
                } else {
                    mStaffSpinner.setVisibility(View.VISIBLE);
                }
                if (i == 0) {
                    //  hitMainApi();
                } else {
                    date = mDatePicker.getSelectedYear() + "-" + (mDatePicker.getSelectedMonth() + 1) + "-" + mDatePicker.getSelectedDay();

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "mycustomerList")
                            .appendQueryParameter("business_id", mPref.getSelecttBusinessId())
                            .appendQueryParameter("address_id", mPref.getSelectAddressId())
                            .appendQueryParameter("service_id", spinner_service_id)
                            .appendQueryParameter("apt_date", date);

                    String myUrl = builder.build().toString();
                    Log.e("service_select", myUrl);

                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "service_select";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mStaffSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spinner_staff_service_id = service_staff_id_array[mStaffSpinner.getSelectedItemPosition()];
                //  String spinner_est_time = service_estimate_time_array[mSpinner.getSelectedItemPosition()];
                if (i == 0) {
                    //  hitMainApi();
                } else {
                    date = mDatePicker.getSelectedYear() + "-" + (mDatePicker.getSelectedMonth() + 1) + "-" + mDatePicker.getSelectedDay();

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "mycustomerList")
                            .appendQueryParameter("business_id", mPref.getSelecttBusinessId())
                            .appendQueryParameter("address_id", mPref.getSelectAddressId())
                            .appendQueryParameter("service_id", service_id_array[mServiceSpinner.getSelectedItemPosition()])
                            .appendQueryParameter("staff_id", spinner_staff_service_id)
                            .appendQueryParameter("apt_date", date);

                    String myUrl = builder.build().toString();
                    Log.e("staff_select", myUrl);

                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "staff_select";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void hitMainApi() {
        date = mDatePicker.getSelectedYear() + "-" + (mDatePicker.getSelectedMonth() + 1) + "-" + mDatePicker.getSelectedDay();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "mycustomerList")
                .appendQueryParameter("business_id", mPref.getSelecttBusinessId())
                .appendQueryParameter("address_id", mPref.getSelectAddressId())
                .appendQueryParameter("apt_date", date);

        String myUrl = builder.build().toString();
        Log.e("urlgetLsiAdd", myUrl);

        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "defaultdate";
            dialog.dismiss();
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            dialog.dismiss();
        } else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

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
             /*   JSONObject main_obj = new JSONObject(response);
                Log.d("MCF", "" + main_obj.getString("message"));
                JSONArray arr = main_obj.getJSONArray("service");
                JSONArray service_array = main_obj.getJSONArray("service");
                service_name_array = new String[service_array.length()];

                for (int i = 0; i < service_array.length(); i++) {
                    JSONObject service_obj = service_array.getJSONObject(i);

                    service_name_array[i] = service_obj.getString("service_name");
                    service_id_array[i] = service_obj.getString("service_id");
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, service_name_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mServiceSpinner.setAdapter(spinnerArrayAdapter);
          */      //   mCalendarView.setTitle(mCalendarView.getSelectedDate().toString());
                //  mCalendarView.getChildAt();
                Gson gson = new Gson();


                mServiceSpinner.setSelection(0);
                mStaffSpinner.setSelection(0);
                MyCustomer myCustomer1 = gson.fromJson(response, MyCustomer.class);
                service_name_array = new String[myCustomer1.getService().size() + 1];
                service_id_array = new String[myCustomer1.getService().size() + 1];
                service_name_array[0] = "Service";
                service_id_array[0] = "0";


                for (int i = 0; i < myCustomer1.getService().size(); i++) {

                    service_name_array[i + 1] = myCustomer1.getService().get(i).getName();
                    service_id_array[i + 1] = myCustomer1.getService().get(i).getId();

                    service_staff_array = new String[myCustomer1.getService().get(i).getStaff().size() + 1];
                    service_staff_id_array = new String[myCustomer1.getService().get(i).getStaff().size() + 1];

                    service_staff_array[0] = "Staff";
                    service_staff_id_array[0] = "0";


                    for (int j = 0; j < myCustomer1.getService().get(i).getStaff().size(); j++) {

                        Log.d("MCF", "staff" + myCustomer1.getService().size() + "staffname" + myCustomer1.getService().get(i).getStaff().get(j).getStaffName());

                        service_staff_array[j + 1] = myCustomer1.getService().get(i).getStaff().get(j).getStaffName();
                        service_staff_id_array[j + 1] = myCustomer1.getService().get(i).getStaff().get(j).getId();
                    }

                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, service_name_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mServiceSpinner.setAdapter(spinnerArrayAdapter);

                ArrayAdapter<String> spinnerStaffArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_dropdown_item_1line
                                , service_staff_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mStaffSpinner.setAdapter(spinnerStaffArrayAdapter);

                if (main_obj.getString("message").equalsIgnoreCase("No Record Found")) {
                    mNoCustomerTv.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);

                } else {

                    mNoCustomerTv.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);

                    try {
                        bookingList.clear();
                        customerCancelArray.clear();
                        MyCustomer myCustomer = gson.fromJson(response, MyCustomer.class);
                        for (int j = 0; j < myCustomer.getOutput().getBooking().size(); j++) {
                            Booking booking = new Booking();
                            booking.setUserName(myCustomer.getOutput().getBooking().get(j).getUserName());
                            booking.setServiceName(myCustomer.getOutput().getBooking().get(j).getServiceName());
                            booking.setBookingId(myCustomer.getOutput().getBooking().get(j).getBookingId());
                            booking.setEstimateTime(myCustomer.getOutput().getBooking().get(j).getEstimateTime());
                            booking.setChkStatus(myCustomer.getOutput().getBooking().get(j).getChkStatus());
                            bookingList.add(booking);
                            CancelCustomerSelection cancelCustomerSelection = new CancelCustomerSelection();
                            cancelCustomerSelection.setBooking_id(myCustomer.getOutput().getBooking().get(j).getBookingId());

                            customerCancelArray.add(cancelCustomerSelection);

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
                            booking.setChkStatus(myCustomer.getOutput().getBooking().get(j).getChkStatus());
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
        } else if (post_tag.equalsIgnoreCase("service_select")) {
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
                            booking.setChkStatus(myCustomer.getOutput().getBooking().get(j).getChkStatus());
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
        } else if (post_tag.equalsIgnoreCase("staff_select")) {
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
                            booking.setChkStatus(myCustomer.getOutput().getBooking().get(j).getChkStatus());
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
        } else if (post_tag.equalsIgnoreCase("bookingcancel")) {
            try {
                JSONObject main_obj = new JSONObject(response);
                Log.d("MCF", "" + main_obj.getString("message"));
                mDialougeBox.dismiss();
                hitMainApi();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }/* else if (post_tag.equals("businessdetails")) {

            try {
                JSONObject main_obj = new JSONObject(response);

                Log.d("ADEF", "" + main_obj);
                JSONArray service_array = main_obj.getJSONArray("service");
                service_name_array = new String[service_array.length()];

                for (int i = 0; i < service_array.length(); i++) {
                    JSONObject service_obj = service_array.getJSONObject(i);

                    service_name_array[i] = service_obj.getString("service_name");
                 //   service_id_array[i] = service_obj.getString("service_id");

                      *//*  serviceList.setService_id(service_obj.getString("service_id"));
                        serviceList.setService_name(service_obj.getString("service_name"));
                        serviceListt.add(serviceList);*//*
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item, service_name_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mServiceSpinner.setAdapter(spinnerArrayAdapter);
                //   mCalendarView.setTitle(mCalendarView.getSelectedDate().toString());
                //  mCalendarView.getChildAt();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/

    }

}


