package frags;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import adapters.JoinAppointmentAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddManualCustomer extends HelperFrags implements HttpresponseUpd {

    private Bundle bundle;
    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private String[] industry_name_array, industry_id_array;

    @BindView(R.id.add_manual_spinner)
    public Spinner add_manual_spinner;

    @BindView(R.id.ctg_name_input)
    public EditText mName;

    @BindView(R.id.open_calenderdate_tv)
    public TextView mOpencalender;
    @BindView(R.id.add_manual_customer_walkin_tv)
    public TextView mWalkin;
    @BindView(R.id.add_manual_customer_oncall_tv)
    public TextView mOnCall;

    private JoinAppointmentAdapter joinAppointmentAdapter;

    @BindView(R.id.frag_add_manual_rv)
    public RecyclerView frag_add_manual_rv;

    @BindView(R.id.frag_add_manual_evening_rv)
    public RecyclerView frag_add_manual_evening_rv;


    @BindView(R.id.add_manual_customer_phone_et)
    public EditText mPhone;


    @BindView(R.id.frag_add_manual_sv)
    public ScrollView frag_add_manual_sv;


    private int mYear, mMonth, mDay;

    private String address_id, business_id;
    private String name, date, no_of_days_advance;
    private String spinner_service, business_name;
    private String post_tag = "";
    private boolean Clicked_status = false;
    private String status = "Walking", sub_date;
    private Sharedpreferences mPref;
    public boolean morning_selected = true, evening_selected = true;

    public AddManualCustomer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_manual_customer, container, false);

        ButterKnife.bind(this, view);
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bundle = getArguments();
        address_id = bundle.getString("address_id");
        business_id = bundle.getString("business_id");
        business_name = bundle.getString("business_name");
        sub_date = bundle.getString("sub_date");

        initView();
        return view;
    }

    private void initView() {
        callback = this;

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mOpencalender.setText(GetDateFormat(mYear, (mMonth + 1), mDay));
        hitUrl();

        frag_add_manual_rv.setHasFixedSize(true);
        frag_add_manual_rv.setLayoutManager(new GridLayoutManager(getContext(), 5));
        joinAppointmentAdapter = new JoinAppointmentAdapter(getActivity());
        frag_add_manual_rv.setAdapter(joinAppointmentAdapter);


        frag_add_manual_evening_rv.setHasFixedSize(true);
        frag_add_manual_evening_rv.setLayoutManager(new GridLayoutManager(getContext(), 5));
        joinAppointmentAdapter = new JoinAppointmentAdapter(getActivity());
        frag_add_manual_evening_rv.setAdapter(joinAppointmentAdapter);


      /*  mPhone.setFilters(new InputFilter[]{Utils.sepcialCharRemovalFilter});
        mPhone.setFilters(new InputFilter[]{Utils.sepcialCharRemovalFilter, new InputFilter.LengthFilter(10)});
*/
    }

    private void hitUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", address_id)
                .appendQueryParameter("bookingdate", mOpencalender.getText().toString())
                .appendQueryParameter("sub_date", sub_date)
                .appendQueryParameter("staff_id", mPref.getStaffId())


        ;
        Log.e("url", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "getCategory";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @OnClick({R.id.add_customer_done, R.id.open_calenderdate_tv, R.id.add_customer_back_btn, R.id.add_manual_customer_walkin_tv,
            R.id.add_manual_customer_oncall_tv, R.id.frag_add_manually_morning_tv, R.id.frag_add_manually_evening_tv, R.id.frag_add_manual_book_appoint_tv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_customer_back_btn:
                try {
                    getActivity().onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.frag_add_manual_book_appoint_tv:

                frag_add_manual_sv.setVisibility(View.VISIBLE);
                break;
            case R.id.frag_add_manually_morning_tv:

                if (morning_selected) {
                    frag_add_manual_rv.setVisibility(View.VISIBLE);
                    morning_selected = false;

                } else {
                    frag_add_manual_rv.setVisibility(View.GONE);
                    morning_selected = true;
                }

                break;
            case R.id.frag_add_manually_evening_tv:

                if (evening_selected) {
                    frag_add_manual_evening_rv.setVisibility(View.VISIBLE);
                    evening_selected = false;

                } else {
                    frag_add_manual_evening_rv.setVisibility(View.GONE);
                    evening_selected = true;
                }

                break;
            case R.id.open_calenderdate_tv:

                //  mOpencalender.setText("");
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                mOpencalender.setText(GetDateFormat(year, (monthOfYear + 1), dayOfMonth));
                            }
                        }, mYear, mMonth, mDay);

                try {
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (no_of_days_advance.equalsIgnoreCase("") || no_of_days_advance.equalsIgnoreCase(null)) {
                        c.add(Calendar.DATE, +15);

                    } else {
                        c.add(Calendar.DATE, +Integer.parseInt(no_of_days_advance));
                    }
                } catch (NumberFormatException e) {
                    c.add(Calendar.DATE, +15);

                }
                try {
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                datePickerDialog.show();


                break;

            case R.id.add_customer_done:

                bookingFun();
                //  hitUrl();

                break;

            case R.id.add_manual_customer_walkin_tv:

                mWalkin.setTextColor(Color.WHITE);
                mWalkin.setBackgroundColor(Color.BLUE);
                mOnCall.setTextColor(Color.BLUE);
                mOnCall.setBackgroundColor(Color.WHITE);
                Clicked_status = true;
                status = "Walking";
                break;
            case R.id.add_manual_customer_oncall_tv:
                mWalkin.setTextColor(Color.BLUE);
                mWalkin.setBackgroundColor(Color.WHITE);

                mOnCall.setTextColor(Color.WHITE);
                mOnCall.setBackgroundColor(Color.BLUE);
                Clicked_status = false;
                status = "Oncall";
                break;
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

    private void bookingFun() {
/*
        if (mName.getText().toString().equals("")) {
            mName.setError("Customer name can't be blank!!!");
*//*
            snackbar = Snackbar.make(getView(), "Customer name cannot be blank!!!", Snackbar.LENGTH_LONG);
*//*
         *//*   mName.setError("Customer name cannot be blank!!!");
            snackbar.show();*//*
        } else */
        if (mPhone.getText().toString().equals("")) {
            mPhone.setError("Customer phone number can't be blank!!!");
           /* snackbar = Snackbar.make(getView(), "Customer phone number cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } /*else if (add_manual_spinner.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(getView(), "Select Service!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }*/ else if (mOpencalender.getText().toString().equals("")) {
            mOpencalender.setError("Select Date!!!");
         /*   snackbar = Snackbar.make(getView(), "Select Date!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/

        } else {
            String final_name;
            name = mName.getText().toString();


            if (name.length() == 0) {
                final_name = mPhone.getText().toString();
            } else {
                final_name = name;
            }


            spinner_service = industry_id_array[add_manual_spinner.getSelectedItemPosition()];

            Log.d("AMC", "date" + date);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "get_token")
                    .appendQueryParameter("cust_name", final_name)
                    .appendQueryParameter("user_id", mPref.getUserId())
                    .appendQueryParameter("business_id", business_id)
                    .appendQueryParameter("business_address_id", address_id)
                    .appendQueryParameter("service_id", spinner_service)
                    .appendQueryParameter("appointment_date", mOpencalender.getText().toString())
                    .appendQueryParameter("lat", "")
                    .appendQueryParameter("longi", "")
                    .appendQueryParameter("mobile", mPhone.getText().toString())
                    .appendQueryParameter("manual_booking", status)
                    .appendQueryParameter("sub_date", sub_date)

            ;
            Log.e("url", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "addCustomer";
                Utils.showProgress(getContext());
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);

            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //  hitUrl();
    }

    @Override
    public void getResponse(String response) {

        //  mDate.setText("");
        Utils.stopProgress(getContext());
        add_manual_spinner.setSelection(0);
        mPhone.setText("");
        mName.setText("");

        Log.e("res", response);
        if (response.contains("Error :")) {
         /*   snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);
            snackbar.show();*/
            Toast.makeText(getActivity(), "Internet problem, Try again", Toast.LENGTH_SHORT).show();

        } else if (post_tag == "getCategory") {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);

                no_of_days_advance = main_obj.getString("no_of_days_advance");
                JSONArray arr = main_obj.getJSONArray("output");

                industry_name_array = new String[arr.length()];
                industry_id_array = new String[arr.length()];

             /*   industry_name_array[0] = "Select Service";
                industry_id_array[0] = "0";

              */
                JSONObject obj;
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);
                    industry_name_array[i] = obj.getString("name");
                    industry_id_array[i] = obj.getString("id");

                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item,
                                industry_name_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                add_manual_spinner.setAdapter(spinnerArrayAdapter);


            } catch (JSONException e) {
                snackbar = Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        } else if (post_tag.equals("addCustomer")) {
            try {
                //  hitUrl();
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                String obj = arr.getJSONObject(0).getString("message");

                Toast.makeText(getContext(), "" + obj, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
