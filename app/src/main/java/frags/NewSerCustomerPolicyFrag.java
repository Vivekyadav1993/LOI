package frags;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import atw.lifeoninternet.LandingActivity;
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
 * Created by teknik on 10/6/2017.
 */

public class NewSerCustomerPolicyFrag extends HelperFrags implements HttpresponseUpd {

    @BindView(R.id.newser_cuspolicy_staffwise_booking_switch)
    public Switch newser_cuspolicy_staffwise_booking_switch;

    @BindView(R.id.newser_cuspolicy_advnoof_day_switch)
    public Switch newser_cuspolicy_advnoof_day_switch;

    @BindView(R.id.newser_cuspolicy_repeatbooking_packageconfig_switch)
    public Switch newser_cuspolicy_repeatbooking_packageconfig_switch;

    @BindView(R.id.newser_cuspolicy_operatorlive_status_switch)
    public Switch newser_cuspolicy_operatorlive_status_switch;

    @BindView(R.id.newser_cuspolicy_bookedpeople_ingroup_switch)
    public Switch newser_cuspolicy_bookedpeople_ingroup_switch;

    @BindView(R.id.newser_cuspolicy_calender_sync_switch)
    public Switch newser_cuspolicy_calender_sync_switch;

    @BindView(R.id.newser_cuspolicy_atpremise_all_switch)
    public Switch newser_cuspolicy_atpremise_all_switch;

    @BindView(R.id.newser_cuspolicy_skip_customer_switch)
    public Switch newser_cuspolicy_skip_customer_switch;

    @BindView(R.id.newser_cuspolicy_pre_advnoof_day_switch)
    public Switch newser_cuspolicy_pre_advnoof_day_switch;


    @BindView(R.id.newser_prepaid_switch)
    public Switch newser_prepaid_switch;

    @BindView(R.id.newser_cuspolicy_app_booking_switch)
    public Switch newser_cuspolicy_app_booking_switch;

    @BindView(R.id.prepaid_tag)
    public TextView prepaid_tag;

    @BindView(R.id.newser_cuspolicy_prepaid_book_radiogrp)
    public RadioGroup newser_cuspolicy_prepaid_book_radiogrp;

    @BindView(R.id.newser_cuspolicy_prepaid_book_ajust_radiobtn)
    public RadioButton newser_cuspolicy_prepaid_book_ajust_radiobtn;

    @BindView(R.id.newser_cuspolicy_prepaid_book_nonajust_radiobtn)
    public RadioButton newser_cuspolicy_prepaid_book_nonajust_radiobtn;

    @BindView(R.id.policy_time_picker)
    public TextView mTimePicker;

    public EditText mNoOfDays;

    private Dialog policyDialougeBox;
    private View Mroot;
    private Bundle bundle;
    private HttpresponseUpd callback;
    private Snackbar snackbar;

    public TextView mSaveTv, mCancleTv;
    private String days, pre_days;
    private Sharedpreferences mPref;
    private int hour, minute;
    private String post_tag;
    private String skip_status;
    @BindView(R.id.advance_booking_days_et)
    public EditText advance_booking_days_et;
    @BindView(R.id.Advance_booking_rl)
    public RelativeLayout Advance_booking_rl;
    @BindView(R.id.post_advance_booking_rl)
    public RelativeLayout post_advance_booking_rl;
    @BindView(R.id.post_advance_booking_day_et)
    public EditText post_advance_booking_day_et;
    private String atpremise_all;
    private String advance_no_of_days;
    private String app_booking="Yes";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_customerpolicy_screen, null);
        ButterKnife.bind(this, Mroot);

        callback = this;

        bundle = getArguments();

        mPref = Sharedpreferences.getUserDataObj(getActivity());

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getcustomerPolicy")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id", mPref.getAddressId())
        ;

        Log.e("url", builder.build().toString());

        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "get_policy_details";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


        newser_cuspolicy_pre_advnoof_day_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (newser_cuspolicy_pre_advnoof_day_switch.isChecked() == true) {
                    post_advance_booking_rl.setVisibility(View.VISIBLE);
                } else {

                    post_advance_booking_rl.setVisibility(View.GONE);


                }
            }
        });


        newser_cuspolicy_app_booking_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (b) {
                    app_booking = "No";
                } else {

                    app_booking = "Yes";

                }

            }
        });


        newser_cuspolicy_advnoof_day_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (newser_cuspolicy_advnoof_day_switch.isChecked() == true) {

                    Advance_booking_rl.setVisibility(View.VISIBLE);
                    advance_no_of_days = "Yes";
                } else {
                    advance_no_of_days = "No";

                    Advance_booking_rl.setVisibility(View.GONE);

                }
            }
        });

        newser_cuspolicy_atpremise_all_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    atpremise_all = "Yes";
                } else {
                    atpremise_all = "No";
                }
            }
        });


     /*   newser_prepaid_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    prepaid_tag.setVisibility(View.VISIBLE);
                    newser_cuspolicy_prepaid_book_radiogrp.setVisibility(View.VISIBLE);
                } else {
                    prepaid_tag.setVisibility(View.GONE);
                    newser_cuspolicy_prepaid_book_radiogrp.setVisibility(View.GONE);
                }
            }
        });*/
        newser_cuspolicy_skip_customer_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    skip_status = "Yes";
                } else {
                    skip_status = "No";

                }

            }
        });


        return Mroot;
    }


    @OnClick(R.id.newser_cuspolicy_back_btn)
    void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.policy_time_picker)
    void timepick() {
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePickerr;
        mTimePickerr = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                Log.e("time", hour + "" + minute);


                hour = selectedHour;
                minute = selectedMinute;

                String timeSet = "";

                if (hour > 12) {
                    hour -= 12;
                    timeSet = "PM";
                } else if (hour == 0) {
                    hour += 12;
                    timeSet = "AM";
                } else if (hour == 12) {
                    timeSet = "PM";
                } else {
                    timeSet = "AM";
                }

                String min = "";
                if (minute < 10)
                    min = "0" + minute;
                else
                    min = String.valueOf(minute);


                String hr = "";

                if (hour < 10)
                    hr = "0" + hour;
                else
                    hr = String.valueOf(hour);


                // Append in a StringBuilder
                String aTime = new StringBuilder().append(hr).append(':')
                        .append(min).append(" ").append(timeSet).toString();


                mTimePicker.setText(aTime);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePickerr.setTitle("Select Time");
        mTimePickerr.show();

    }


    @OnClick(R.id.newser_cuspolicy_next_btn)
    void create() {
     /*   if (newser_cuspolicy_staffwise_booking_switch.isChecked()) {
            LandingActivity.staffwise_booking_switch = "Yes";
        } else {
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()) {
            LandingActivity.advnoof_day_switch = "Yes";
        } else {
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()) {
            LandingActivity.packageconfig_switch = "Yes";
        } else {
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()) {
            LandingActivity.status_switch = "Yes";
        } else {
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()) {
            LandingActivity.ingroup_switch = "Yes";
        } else {
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()) {
            LandingActivity.sync_switch = "Yes";
        } else {
            LandingActivity.sync_switch = "No";
        }

        if (newser_prepaid_switch.isChecked()) {
            if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()) {
                LandingActivity.ajust_radiobtn_value = "Yes";
            } else {
                LandingActivity.ajust_radiobtn_value = "No";
            }
        } else {
            LandingActivity.ajust_radiobtn_value = "No";
        }*/

        //hit get api
        //hit api
        if (newser_cuspolicy_pre_advnoof_day_switch.isChecked()) {
            post_advance_booking_rl.setVisibility(View.VISIBLE);
            pre_days = post_advance_booking_day_et.getText().toString();

        } else {
            post_advance_booking_rl.setVisibility(View.VISIBLE);
            pre_days = "0";

        }


        if (newser_cuspolicy_advnoof_day_switch.isChecked()) {
            advance_no_of_days = "Yes";
            Advance_booking_rl.setVisibility(View.VISIBLE);
            days = advance_booking_days_et.getText().toString();

        } else {
            advance_no_of_days = "No";
            Advance_booking_rl.setVisibility(View.GONE);
            days = "0";
        }


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "createcustomerPolicy")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id", mPref.getAddressId())
                .appendQueryParameter("staffwise_booking", LandingActivity.staffwise_booking_switch)
                .appendQueryParameter("advance_no_of_days", advance_no_of_days /*LandingActivity.advnoof_day_switch*/)
                .appendQueryParameter("package_conf", LandingActivity.packageconfig_switch)
                .appendQueryParameter("operatorlive_status", LandingActivity.status_switch)
                .appendQueryParameter("in_group", LandingActivity.ingroup_switch)
                .appendQueryParameter("cal_sync", LandingActivity.sync_switch)
                .appendQueryParameter("amount_adjust", LandingActivity.ajust_radiobtn_value)
                .appendQueryParameter("no_of_days", days)
                .appendQueryParameter("no_of_hour", mTimePicker.getText().toString())
                .appendQueryParameter("no_of_days_advance", pre_days)
                .appendQueryParameter("skip_last", skip_status)
                .appendQueryParameter("sms_type", mPref.getIndustryType())
                .appendQueryParameter("atpremise_all", atpremise_all)
                .appendQueryParameter("app_booking", app_booking)
        ;

        Log.e("url", builder.build().toString());

        if (AppUtils.isNetworkAvailable(getActivity())) {

            post_tag = "create_policy";

            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


        //  replaceFrag(new AddServiceFrag(), new Bundle() ,NewSerCustomerPolicyFrag.class.getName());
    }


    @OnClick(R.id.newser_cuspolicy_cancellationpolicy_btn)
    void cancellation() {

        if (newser_cuspolicy_staffwise_booking_switch.isChecked()) {
            LandingActivity.staffwise_booking_switch = "Yes";
        } else {
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()) {
            LandingActivity.advnoof_day_switch = "Yes";
        } else {
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()) {
            LandingActivity.packageconfig_switch = "Yes";
        } else {
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()) {
            LandingActivity.status_switch = "Yes";
        } else {
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()) {
            LandingActivity.ingroup_switch = "Yes";
        } else {
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()) {
            LandingActivity.sync_switch = "Yes";
        } else {
            LandingActivity.sync_switch = "No";
        }
        if (newser_prepaid_switch.isChecked()) {
            if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()) {
                LandingActivity.ajust_radiobtn_value = "Yes";
            } else {
                LandingActivity.ajust_radiobtn_value = "No";
            }
        } else {
            LandingActivity.ajust_radiobtn_value = "";

        }


        Bundle _bundle = new Bundle();


        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));
        // go to next page
        replaceFrag(new NewSerCancellationPolicyFrag(), _bundle, NewSerCustomerPolicyFrag.class.getName());
    }

    @OnClick(R.id.newser_cuspolicy_discountpolicy_btn)
    void discount() {
        if (newser_cuspolicy_staffwise_booking_switch.isChecked()) {
            LandingActivity.staffwise_booking_switch = "Yes";
        } else {
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()) {
            LandingActivity.advnoof_day_switch = "Yes";
        } else {
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()) {
            LandingActivity.packageconfig_switch = "Yes";
        } else {
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()) {
            LandingActivity.status_switch = "Yes";
        } else {
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()) {
            LandingActivity.ingroup_switch = "Yes";
        } else {
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()) {
            LandingActivity.sync_switch = "Yes";
        } else {
            LandingActivity.sync_switch = "No";
        }
        if (newser_prepaid_switch.isChecked()) {
            if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()) {
                LandingActivity.ajust_radiobtn_value = "Yes";
            } else {
                LandingActivity.ajust_radiobtn_value = "No";
            }
        } else {
            LandingActivity.ajust_radiobtn_value = "";
        }
        Bundle _bundle = new Bundle();
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));
        // go to next page
        replaceFrag(new NewSerDiscountPolicyFrag(), _bundle, NewSerCustomerPolicyFrag.class.getName());

    }

    @OnClick(R.id.newser_cuspolicy_packageconfi_btn)
    void packageConfig() {
        if (newser_cuspolicy_staffwise_booking_switch.isChecked()) {
            LandingActivity.staffwise_booking_switch = "Yes";
        } else {
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()) {
            LandingActivity.advnoof_day_switch = "Yes";
        } else {
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()) {
            LandingActivity.packageconfig_switch = "Yes";
        } else {
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()) {
            LandingActivity.status_switch = "Yes";
        } else {
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()) {
            LandingActivity.ingroup_switch = "Yes";
        } else {
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()) {
            LandingActivity.sync_switch = "Yes";
        } else {
            LandingActivity.sync_switch = "No";
        }
        if (newser_prepaid_switch.isChecked()) {
            if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()) {
                LandingActivity.ajust_radiobtn_value = "Yes";
            } else {
                LandingActivity.ajust_radiobtn_value = "No";
            }
        } else {
            LandingActivity.ajust_radiobtn_value = "";
        }

        Bundle _bundle = new Bundle();
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));
        replaceFrag(new NewSerPackageConfigurationFrag(), _bundle, NewSerCustomerPolicyFrag.class.getName());

    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (post_tag.equalsIgnoreCase("get_policy_details")) {

            try {
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);
                String advance_no_of_days = obj.getString("advance_no_of_days");
                String no_of_days = obj.getString("no_of_days");
                String no_of_hour = obj.getString("no_of_hour");
                String no_of_days_advance = obj.getString("no_of_days_advance");
                String skip_last = obj.getString("skip_last");
                String atpremise = obj.getString("atpermise_all");
                String app_booking = obj.getString("app_booking");


                if (atpremise.equalsIgnoreCase("No") || atpremise.equalsIgnoreCase("")) {
                    newser_cuspolicy_atpremise_all_switch.setChecked(false);
                    atpremise_all = "No";
                } else {
                    newser_cuspolicy_atpremise_all_switch.setChecked(true);
                    atpremise_all = "Yes";
                }

                if (skip_last.equalsIgnoreCase("Yes")) {
                    newser_cuspolicy_skip_customer_switch.setChecked(true);
                } else {
                    newser_cuspolicy_skip_customer_switch.setChecked(false);
                }


                if(app_booking.equalsIgnoreCase("Yes")){
                    newser_cuspolicy_app_booking_switch.setChecked(false);

                }else {

                    newser_cuspolicy_app_booking_switch.setChecked(true);

                }

                if (advance_no_of_days.equalsIgnoreCase("Yes")) {
                    newser_cuspolicy_advnoof_day_switch.setChecked(true);
                    advance_booking_days_et.setText(no_of_days);
                    Advance_booking_rl.setVisibility(View.VISIBLE);
                } else {
                    newser_cuspolicy_advnoof_day_switch.setChecked(false);
                    advance_booking_days_et.setText("");
                    Advance_booking_rl.setVisibility(View.GONE);
                }
                if (no_of_days_advance == null || no_of_days_advance.equalsIgnoreCase("") || no_of_days_advance.equalsIgnoreCase("null")) {
                    newser_cuspolicy_pre_advnoof_day_switch.setChecked(false);
                    post_advance_booking_day_et.setText("");
                    post_advance_booking_rl.setVisibility(View.GONE);
                } else {

                    newser_cuspolicy_pre_advnoof_day_switch.setChecked(true);
                    post_advance_booking_day_et.setText(no_of_days_advance);
                    post_advance_booking_rl.setVisibility(View.VISIBLE);

                }

                if (no_of_hour.equals("0") || no_of_hour.equals("")) {
                    mTimePicker.setText("12:00 AM");
                } else {
                    mTimePicker.setText(no_of_hour);
                }
            /*    if (advance_no_of_days.equalsIgnoreCase("Yes")) {
                    newser_cuspolicy_advnoof_day_switch.setChecked(true);
                } else {
                    newser_cuspolicy_advnoof_day_switch.setChecked(false);
                }
                if (no_of_days_advance.equalsIgnoreCase(null)) {
                    newser_cuspolicy_pre_advnoof_day_switch.setChecked(false);
                } else {
                    newser_cuspolicy_pre_advnoof_day_switch.setChecked(true);
                }*/


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (post_tag.equalsIgnoreCase("create_policy")) {
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);
                String sucess_code = obj.getString("code");

                if (sucess_code.equals("200")) {
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Bundle _bundle = new Bundle();
                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));


                    replaceFrag(new AddServiceFrag(), _bundle, "");
                } else {
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

    }
}
