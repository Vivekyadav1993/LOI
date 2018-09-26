package frags;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import models.BusinessHourData;
import models.StaffData;


/**
 * Created by teknik on 10/3/2017.
 */

public class NewStaffWorkingHourFrag extends HelperFrags implements CompoundButton.OnCheckedChangeListener, HttpresponseUpd {

    private View Mroot;

    // select date widgets
    @BindView(R.id.newstaff_working_date_start_btn)
    TextView newstaff_working_date_start_btn;

    @BindView(R.id.newstaff_working_date_end_btn)
    TextView newstaff_working_date_end_btn;

    @BindView(R.id.newstaff_working_date_switch_btn)
    Switch newstaff_working_date_switch_btn;

    // monday time widgets
    @BindView(R.id.newstaff_working_timemon_start_btn)
    TextView newstaff_working_timemon_start_btn;

    @BindView(R.id.newstaff_working_timemon_end_btn)
    TextView newstaff_working_timemon_end_btn;

    @BindView(R.id.newstaff_working_timemon_switch_btn)
    Switch newstaff_working_timemon_switch_btn;

    // tuesday time widgets
    @BindView(R.id.newstaff_working_timetue_start_btn)
    TextView newstaff_working_timetue_start_btn;

    @BindView(R.id.newstaff_working_timetue_end_btn)
    TextView newstaff_working_timetue_end_btn;

    @BindView(R.id.newstaff_working_timetue_switch_btn)
    Switch newstaff_working_timetue_switch_btn;

    // wednesday time widgets
    @BindView(R.id.newstaff_working_timewed_start_btn)
    TextView newstaff_working_timewed_start_btn;

    @BindView(R.id.newstaff_working_timewed_end_btn)
    TextView newstaff_working_timewed_end_btn;

    @BindView(R.id.newstaff_working_timewed_switch_btn)
    Switch newstaff_working_timewed_switch_btn;

    // thursday time widgets
    @BindView(R.id.newstaff_working_timethr_start_btn)
    TextView newstaff_working_timethr_start_btn;

    @BindView(R.id.newstaff_working_timethr_end_btn)
    TextView newstaff_working_timethr_end_btn;

    @BindView(R.id.newstaff_working_timethr_switch_btn)
    Switch newstaff_working_timethr_switch_btn;

    // friday time widgets
    @BindView(R.id.newstaff_working_timefri_start_btn)
    TextView newstaff_working_timefri_start_btn;

    @BindView(R.id.newstaff_working_timefri_end_btn)
    TextView newstaff_working_timefri_end_btn;

    @BindView(R.id.newstaff_working_timefri_switch_btn)
    Switch newstaff_working_timefri_switch_btn;

    // saturday time widgets
    @BindView(R.id.newstaff_working_timesat_start_btn)
    TextView newstaff_working_timesat_start_btn;

    @BindView(R.id.newstaff_working_timesat_end_btn)
    TextView newstaff_working_timesat_end_btn;

    @BindView(R.id.newstaff_working_timesat_switch_btn)
    Switch newstaff_working_timesat_switch_btn;

    // sunday time widgets
    @BindView(R.id.newstaff_working_timesun_start_btn)
    TextView newstaff_working_timesun_start_btn;

    @BindView(R.id.newstaff_working_timesun_end_btn)
    TextView newstaff_working_timesun_end_btn;

    @BindView(R.id.newstaff_working_timesun_switch_btn)
    Switch newstaff_working_timesun_switch_btn;


    @BindView(R.id.mon_mor_tv)
    public TextView mon_mor_tv;

    @BindView(R.id.mon_eve_tv)
    public TextView mon_eve_tv;
    @BindView(R.id.bh_timemon_start_eve_btn)
    public TextView bh_timemon_start_eve_btn;
    @BindView(R.id.bh_timemon_end_eve_btn)
    public TextView bh_timemon_end_eve_btn;
    @BindView(R.id.tue_mor_tv)
    public TextView tue_mor_tv;
    @BindView(R.id.tue_eve_tv)
    public TextView tue_eve_tv;
    @BindView(R.id.bh_timetue_start_eve_btn)
    public TextView bh_timetue_start_eve_btn;
    @BindView(R.id.bh_timetue_end_eve_btn)
    public TextView bh_timetue_end_eve_btn;
    @BindView(R.id.wed_mor_tv)
    public TextView wed_mor_tv;
    @BindView(R.id.wed_eve_tv)
    public TextView wed_eve_tv;
    @BindView(R.id.bh_timewed_start_eve_btn)
    public TextView bh_timewed_start_eve_btn;
    @BindView(R.id.bh_timewed_end_eve_btn)
    public TextView bh_timewed_end_eve_btn;
    @BindView(R.id.thr_mor_tv)
    public TextView thr_mor_tv;
    @BindView(R.id.thr_eve_tv)
    public TextView thr_eve_tv;
    @BindView(R.id.bh_timethr_start_eve_btn)
    public TextView bh_timethr_start_eve_btn;
    @BindView(R.id.bh_timethr_end_eve_btn)
    public TextView bh_timethr_end_eve_btn;
    @BindView(R.id.fri_mor_tv)
    public TextView fri_mor_tv;
    @BindView(R.id.fri_eve_tv)
    public TextView fri_eve_tv;
    @BindView(R.id.bh_timefri_start_eve_btn)
    public TextView bh_timefri_start_eve_btn;
    @BindView(R.id.bh_timefri_end_eve_btn)
    public TextView bh_timefri_end_eve_btn;
    @BindView(R.id.sat_mor_tv)
    public TextView sat_mor_tv;
    @BindView(R.id.sat_eve_tv)
    public TextView sat_eve_tv;
    @BindView(R.id.bh_timesat_start_eve_btn)
    public TextView bh_timesat_start_eve_btn;
    @BindView(R.id.bh_timesat_end_eve_btn)
    public TextView bh_timesat_end_eve_btn;
    @BindView(R.id.sun_mor_tv)
    public TextView sun_mor_tv;
    @BindView(R.id.sun_eve_tv)
    public TextView sun_eve_tv;
    @BindView(R.id.bh_timesun_start_eve_btn)
    public TextView bh_timesun_start_eve_btn;
    @BindView(R.id.bh_timesun_end_eve_btn)
    public TextView bh_timesun_end_eve_btn;

    boolean mon_mor_clicked = true, mon_eve_clicked = true, tue_mor_clicked = true, tue_eve_clicked = true, wed_mor_clicked = true, wed_eve_clicked = true,
            thru_mor_clicked = true, thru_eve_clicked = true, fri_mor_clicked = true, fri_eve_clicked = true, sat_mor_clicked = true, sat_eve_clicked = true,
            sun_mor_clicked = true, sun_eve_clicked = true;


    private BusinessHourData businessHourData;
    private Bundle bundle;
    private StaffData staffData;
    private HttpresponseUpd callback;

    private Sharedpreferences mPref;
    private String staff_id;
    private String post_tag;
    private Snackbar snackbar;

    public String emon_from_time, emon_to_time, etue_from_time, etue_to_time, ewed_from_time, ewed_to_time, ethru_from_time, ethru_to_time,
            efri_from_time, efri_to_time, esat_from_time, esat_to_time, esun_from_time, esun_to_time;
    ;

    public String start_date, business_start_date, end_date, business_end_date, mon_from_time, business_mon_from_time, mon_to_time, business_mon_to_time, tue_from_time, business_tue_from_time,
            tue_to_time, business_tue_to_time, wed_from_time, business_wed_from_time, wed_to_time, business_wed_to_time, thru_from_time, business_thru_from_time,
            thru_to_time, business_thru_to_time;
    public String fri_from_time, business_fri_from_time, fri_to_time, business_fri_to_time, sat_from_time, business_sat_from_time,
            sat_to_time, business_sat_to_time, sun_from_time, business_sun_from_time, sun_to_time, business_sun_to_time;

    public String mon_eve_from_time, mon_eve_to_time, tue_eve_from_time, tue_eve_to_time, wed_eve_from_time, wed_eve_to_time, thru_eve_from_time, thru_eve_to_time,
            fri_eve_from_time, fri_eve_to_time, sat_eve_from_time, sat_eve_to_time, sun_eve_from_time, sun_eve_to_time;

    public String sun_to_time_final, sun_from_time_final, start_date_final, end_date_final, mon_from_time_final, mon_to_time_final, tue_from_time_final, tue_to_time_final,
            wed_from_time_final, wed_to_time_final, thru_from_time_final, thru_to_time_final, fri_from_time_final, fri_to_time_final, sat_from_time_final, sat_to_time_final;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_staff_working_time_screen, null);
        ButterKnife.bind(this, Mroot);
        bundle = getArguments();
        callback = this;
        staff_id = bundle.getString("staff_id");
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        //  staffData = LandingActivity.staff_data_array.get(bundle.getInt("pos"));
        newstaff_working_date_switch_btn.setOnCheckedChangeListener(this);

        newstaff_working_timemon_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timetue_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timewed_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timethr_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timefri_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timesat_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timesun_switch_btn.setOnCheckedChangeListener(this);


        try {
            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "showstaffWorkingTime")
                    .appendQueryParameter("staff_id", staff_id);

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "staffHourInfo";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Mroot;

    }

    private void showRelatedFields(String business_industry) {

    }


    @OnClick(R.id.newstaff_working_next_btn)
    void goNext() {
        // save data in address array

       /* start_date_final = newstaff_working_date_start_btn.getText().toString();
        end_date_final = newstaff_working_date_end_btn.getText().toString();
*/
        // date start and End

        start_date_final = newstaff_working_date_start_btn.getText().toString();
        end_date_final = newstaff_working_date_end_btn.getText().toString();


        // Mon start and End Time
        if (newstaff_working_timemon_switch_btn.isChecked()) {
            if (!mon_mor_clicked) {
                mon_from_time_final = "";
                mon_to_time_final = "";
            } else {

                mon_from_time_final = newstaff_working_timemon_start_btn.getText().toString();
                mon_to_time_final = newstaff_working_timemon_end_btn.getText().toString();

            }

            if (!mon_eve_clicked) {
                mon_eve_from_time = "";
                mon_eve_to_time = "";

            } else {
                mon_eve_from_time = bh_timemon_start_eve_btn.getText().toString();
                mon_eve_to_time = bh_timemon_end_eve_btn.getText().toString();

            }


        } else {
            mon_from_time_final = "";
            mon_to_time_final = "";
            mon_eve_from_time = "";
            mon_eve_to_time = "";
        }

        // Tue start and End Time
        if (newstaff_working_timetue_switch_btn.isChecked()) {

            if (!tue_mor_clicked) {
                tue_from_time_final = "";
                tue_to_time_final = "";

            } else {

                tue_from_time_final = newstaff_working_timetue_start_btn.getText().toString();
                tue_to_time_final = newstaff_working_timetue_end_btn.getText().toString();

            }

            if (!tue_eve_clicked) {
                tue_eve_from_time = "";
                tue_eve_to_time = "";

            } else {
                tue_eve_from_time = bh_timetue_start_eve_btn.getText().toString();
                tue_eve_to_time = bh_timetue_end_eve_btn.getText().toString();

            }

        } else {
            tue_from_time_final = "";
            tue_to_time_final = "";
            tue_eve_from_time = "";
            tue_eve_to_time = "";
        }

        // Wed start and End Time
        if (newstaff_working_timewed_switch_btn.isChecked()) {

            if (!wed_mor_clicked) {
                wed_from_time_final = "";
                wed_to_time_final = "";

            } else {

                wed_from_time_final = newstaff_working_timewed_start_btn.getText().toString();
                wed_to_time_final = newstaff_working_timewed_end_btn.getText().toString();

            }

            if (!wed_eve_clicked) {
                wed_eve_from_time = "";
                wed_eve_to_time = "";

            } else {
                wed_eve_from_time = bh_timewed_start_eve_btn.getText().toString();
                wed_eve_to_time = bh_timewed_end_eve_btn.getText().toString();

            }


        } else {
            wed_from_time_final = "";
            wed_to_time_final = "";
            wed_eve_from_time = "";
            wed_eve_to_time = "";
        }

        // thr start and End Time
        if (newstaff_working_timethr_switch_btn.isChecked()) {

            if (!thru_mor_clicked) {
                thru_from_time_final = "";
                thru_to_time_final = "";

            } else {

                thru_from_time_final = newstaff_working_timethr_start_btn.getText().toString();
                thru_to_time_final = newstaff_working_timethr_end_btn.getText().toString();

            }

            if (!thru_eve_clicked) {
                thru_eve_from_time = "";
                thru_eve_to_time = "";

            } else {
                thru_eve_from_time = bh_timethr_start_eve_btn.getText().toString();
                thru_eve_to_time = bh_timethr_end_eve_btn.getText().toString();

            }


        } else {
            thru_from_time_final = "";
            thru_to_time_final = "";
            thru_eve_from_time = "";
            thru_eve_to_time = "";
        }

        // fri start and End Time
        if (newstaff_working_timefri_switch_btn.isChecked()) {

            if (!fri_mor_clicked) {
                fri_from_time_final = "";
                fri_to_time_final = "";

            } else {

                fri_from_time_final = newstaff_working_timefri_start_btn.getText().toString();
                fri_to_time_final = newstaff_working_timefri_end_btn.getText().toString();

            }

            if (!thru_eve_clicked) {
                fri_eve_from_time = "";
                fri_eve_to_time = "";

            } else {
                fri_eve_from_time = bh_timefri_start_eve_btn.getText().toString();
                fri_eve_to_time = bh_timefri_end_eve_btn.getText().toString();

            }

        } else {
            fri_from_time_final = "";
            fri_to_time_final = "";
            fri_eve_from_time = "";
            fri_eve_to_time = "";
        }

        // Sat start and End Time
        if (newstaff_working_timesat_switch_btn.isChecked()) {

            if (!sat_mor_clicked) {
                sat_from_time_final = "";
                sat_to_time_final = "";

            } else {

                sat_from_time_final = newstaff_working_timesat_start_btn.getText().toString();
                sat_to_time_final = newstaff_working_timesat_end_btn.getText().toString();

            }

            if (!sat_eve_clicked) {
                sat_eve_from_time = "";
                sat_eve_to_time = "";

            } else {
                sat_eve_from_time = bh_timesat_start_eve_btn.getText().toString();
                sat_eve_to_time = bh_timesat_end_eve_btn.getText().toString();

            }


        } else {
            sat_from_time_final = "";
            sat_to_time_final = "";
            sat_eve_from_time = "";
            sat_eve_to_time = "";
        }

        // Sun start and End Time
        if (newstaff_working_timesun_switch_btn.isChecked()) {

            if (!sun_mor_clicked) {
                sun_from_time_final = "";
                sun_to_time_final = "";

            } else {

                sun_from_time_final = newstaff_working_timesun_start_btn.getText().toString();
                sun_to_time_final = newstaff_working_timesun_end_btn.getText().toString();

            }

            if (!sun_eve_clicked) {
                sun_eve_from_time = "";
                sun_eve_to_time = "";

            } else {
                sun_eve_from_time = bh_timesat_start_eve_btn.getText().toString();
                sun_eve_to_time = bh_timesat_end_eve_btn.getText().toString();

            }


        } else {
            sun_from_time_final = "";
            sun_to_time_final = "";
            sun_eve_from_time = "";
            sun_eve_to_time = "";
        }

        try {
            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "editstaffWorkingTime")
                    .appendQueryParameter("staff_id", staff_id)
                    .appendQueryParameter("start_date", start_date_final)
                    .appendQueryParameter("end_date", end_date_final)
                    .appendQueryParameter("mon_from_time", mon_from_time_final)
                    .appendQueryParameter("mon_to_time", mon_to_time_final)
                    .appendQueryParameter("tue_from_time", tue_from_time_final)
                    .appendQueryParameter("tue_to_time", tue_to_time_final)
                    .appendQueryParameter("wed_from_time", wed_from_time_final)
                    .appendQueryParameter("wed_to_time", wed_to_time_final)
                    .appendQueryParameter("thru_from_time", thru_from_time_final)
                    .appendQueryParameter("thru_to_time", thru_to_time_final)
                    .appendQueryParameter("fri_from_time", fri_from_time_final)
                    .appendQueryParameter("fri_to_time", fri_to_time_final)
                    .appendQueryParameter("sat_from_time", sat_from_time_final)
                    .appendQueryParameter("sat_to_time", sat_to_time_final)
                    .appendQueryParameter("sun_from_time", sun_from_time_final)
                    .appendQueryParameter("sun_to_time", sun_to_time_final)
                    .appendQueryParameter("mon_eve_from_time", mon_eve_from_time)
                    .appendQueryParameter("mon_eve_to_time", mon_eve_to_time)
                    .appendQueryParameter("tue_eve_from_time", tue_eve_from_time)
                    .appendQueryParameter("tue_eve_to_time", tue_eve_to_time)
                    .appendQueryParameter("wed_eve_from_time", wed_eve_from_time)
                    .appendQueryParameter("wed_eve_to_time", wed_eve_to_time)
                    .appendQueryParameter("thru_eve_from_time", thru_eve_from_time)
                    .appendQueryParameter("thru_eve_to_time", thru_eve_to_time)
                    .appendQueryParameter("fri_eve_from_time", fri_eve_from_time)
                    .appendQueryParameter("fri_eve_to_time", fri_eve_to_time)
                    .appendQueryParameter("sat_eve_from_time", sat_eve_from_time)
                    .appendQueryParameter("sat_eve_to_time", sat_eve_to_time)
                    .appendQueryParameter("sun_eve_from_time", sun_eve_from_time)
                    .appendQueryParameter("sun_eve_to_time", sun_eve_to_time)

            ;

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "staffFinalHourInfo";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  LandingActivity.staff = true;
        Bundle _bundle = new Bundle();
        _bundle.putString("src", bundle.getString("src"));
        _bundle.putInt("pos", bundle.getInt("pos"));

        replaceFrag(new NewStaffInfoFrag(), _bundle, BusinessHourFrag.class.getName());*/


    }


    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.mon_mor_tv, R.id.mon_eve_tv, R.id.tue_mor_tv, R.id.tue_eve_tv, R.id.wed_mor_tv, R.id.wed_eve_tv, R.id.thr_mor_tv, R.id.thr_eve_tv,
            R.id.fri_mor_tv, R.id.fri_eve_tv, R.id.sat_mor_tv, R.id.sat_eve_tv, R.id.sun_mor_tv, R.id.sun_eve_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mon_mor_tv:

                if (mon_mor_clicked) {

                    mon_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timemon_start_btn.setClickable(false);
                    newstaff_working_timemon_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timemon_end_btn.setClickable(false);
                    newstaff_working_timemon_end_btn.setTextColor(Color.GRAY);

                    mon_mor_clicked = false;
                } else {
                    mon_mor_tv.setBackgroundResource(R.color.blue);
                    newstaff_working_timemon_start_btn.setClickable(true);
                    newstaff_working_timemon_end_btn.setClickable(true);
                    newstaff_working_timemon_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timemon_end_btn.setTextColor(Color.BLACK);

                    mon_mor_clicked = true;
                }
                break;

            case R.id.mon_eve_tv:
                if (mon_eve_clicked) {

                    mon_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timemon_start_eve_btn.setClickable(false);
                    bh_timemon_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timemon_end_eve_btn.setClickable(false);
                    bh_timemon_end_eve_btn.setTextColor(Color.GRAY);

                    mon_eve_clicked = false;
                } else {
                    mon_eve_tv.setBackgroundResource(R.color.blue);
                    bh_timemon_start_eve_btn.setClickable(true);
                    bh_timemon_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timemon_end_eve_btn.setClickable(true);
                    bh_timemon_end_eve_btn.setTextColor(Color.BLACK);

                    mon_eve_clicked = true;
                }
                break;
            case R.id.tue_mor_tv:
                if (tue_mor_clicked) {

                    tue_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timetue_start_btn.setClickable(false);
                    newstaff_working_timetue_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timetue_end_btn.setClickable(false);
                    newstaff_working_timetue_end_btn.setTextColor(Color.GRAY);

                    tue_mor_clicked = false;
                } else {
                    tue_mor_tv.setBackgroundResource(R.color.blue);
                    newstaff_working_timetue_start_btn.setClickable(true);
                    newstaff_working_timetue_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timetue_end_btn.setClickable(true);
                    newstaff_working_timetue_end_btn.setTextColor(Color.BLACK);

                    tue_mor_clicked = true;
                }
                break;
            case R.id.tue_eve_tv:
                if (tue_eve_clicked) {

                    tue_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timetue_start_eve_btn.setClickable(false);
                    bh_timetue_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timetue_end_eve_btn.setClickable(false);
                    bh_timetue_end_eve_btn.setTextColor(Color.GRAY);

                    tue_eve_clicked = false;
                } else {
                    tue_eve_tv.setBackgroundResource(R.color.blue);
                    bh_timetue_start_eve_btn.setClickable(true);
                    bh_timetue_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timetue_end_eve_btn.setClickable(true);
                    bh_timetue_end_eve_btn.setTextColor(Color.BLACK);
                    tue_eve_clicked = true;
                }
                break;
            case R.id.wed_mor_tv:
                if (wed_mor_clicked) {

                    wed_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timewed_start_btn.setClickable(false);
                    newstaff_working_timewed_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timewed_end_btn.setClickable(false);
                    newstaff_working_timewed_end_btn.setTextColor(Color.GRAY);

                    wed_mor_clicked = false;
                } else {
                    wed_mor_tv.setBackgroundResource(R.color.blue);
                    newstaff_working_timewed_start_btn.setClickable(true);
                    newstaff_working_timewed_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timewed_end_btn.setClickable(true);
                    newstaff_working_timewed_end_btn.setTextColor(Color.BLACK);
                    wed_mor_clicked = true;
                }
                break;
            case R.id.wed_eve_tv:
                if (wed_eve_clicked) {

                    wed_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timewed_start_eve_btn.setClickable(false);
                    bh_timewed_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timewed_end_eve_btn.setClickable(false);
                    bh_timewed_end_eve_btn.setTextColor(Color.GRAY);
                    wed_eve_clicked = false;
                } else {
                    wed_eve_tv.setBackgroundResource(R.color.blue);

                    bh_timewed_start_eve_btn.setClickable(true);
                    bh_timewed_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timewed_end_eve_btn.setClickable(true);
                    bh_timewed_end_eve_btn.setTextColor(Color.BLACK);
                    wed_eve_clicked = true;
                }
                break;
            case R.id.thr_mor_tv:
                if (thru_mor_clicked) {

                    thr_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timethr_start_btn.setClickable(false);
                    newstaff_working_timethr_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timethr_end_btn.setClickable(false);
                    newstaff_working_timethr_end_btn.setTextColor(Color.GRAY);
                    thru_mor_clicked = false;
                } else {
                    thr_mor_tv.setBackgroundResource(R.color.blue);
                    newstaff_working_timethr_start_btn.setClickable(true);
                    newstaff_working_timethr_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timethr_end_btn.setClickable(true);
                    newstaff_working_timethr_end_btn.setTextColor(Color.BLACK);
                    thru_mor_clicked = true;
                }
                break;
            case R.id.thr_eve_tv:
                if (thru_eve_clicked) {

                    thr_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timethr_start_eve_btn.setClickable(false);
                    bh_timethr_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timethr_end_eve_btn.setClickable(false);
                    bh_timethr_end_eve_btn.setTextColor(Color.GRAY);
                    thru_eve_clicked = false;
                } else {
                    thr_eve_tv.setBackgroundResource(R.color.blue);

                    bh_timethr_start_eve_btn.setClickable(true);
                    bh_timethr_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timethr_end_eve_btn.setClickable(true);
                    bh_timethr_end_eve_btn.setTextColor(Color.BLACK);
                    thru_eve_clicked = true;
                }
                break;

            case R.id.fri_mor_tv:
                if (fri_mor_clicked) {

                    fri_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timefri_start_btn.setClickable(false);
                    newstaff_working_timefri_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timefri_end_btn.setClickable(false);
                    newstaff_working_timefri_end_btn.setTextColor(Color.GRAY);
                    fri_mor_clicked = false;
                } else {
                    fri_mor_tv.setBackgroundResource(R.color.blue);
                    newstaff_working_timefri_start_btn.setClickable(true);
                    newstaff_working_timefri_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timefri_end_btn.setTextColor(Color.BLACK);
                    newstaff_working_timefri_end_btn.setClickable(true);
                    fri_mor_clicked = true;
                }
                break;
            case R.id.fri_eve_tv:
                if (fri_eve_clicked) {

                    fri_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timefri_start_eve_btn.setClickable(false);
                    bh_timefri_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timefri_end_eve_btn.setClickable(false);
                    bh_timefri_end_eve_btn.setTextColor(Color.GRAY);
                    fri_eve_clicked = false;
                } else {
                    fri_eve_tv.setBackgroundResource(R.color.blue);
                    bh_timefri_start_eve_btn.setClickable(true);
                    bh_timefri_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timefri_end_eve_btn.setClickable(true);
                    bh_timefri_end_eve_btn.setTextColor(Color.BLACK);
                    fri_eve_clicked = true;
                }
                break;
            case R.id.sat_mor_tv:
                if (sat_mor_clicked) {

                    sat_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timesat_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timesat_start_btn.setClickable(false);
                    newstaff_working_timesat_end_btn.setClickable(false);
                    newstaff_working_timesat_end_btn.setTextColor(Color.GRAY);
                    sat_mor_clicked = false;
                } else {
                    sat_mor_tv.setBackgroundResource(R.color.blue);

                    newstaff_working_timesat_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timesat_start_btn.setClickable(true);
                    newstaff_working_timesat_end_btn.setClickable(true);
                    newstaff_working_timesat_end_btn.setTextColor(Color.BLACK);
                    sat_mor_clicked = true;
                }
                break;
            case R.id.sat_eve_tv:
                if (sat_eve_clicked) {

                    sat_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timesat_start_eve_btn.setClickable(false);
                    bh_timesat_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timesat_end_eve_btn.setClickable(false);
                    bh_timesat_end_eve_btn.setTextColor(Color.GRAY);
                    sat_eve_clicked = false;
                } else {
                    sat_eve_tv.setBackgroundResource(R.color.blue);

                    bh_timesat_start_eve_btn.setClickable(true);
                    bh_timesat_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timesat_end_eve_btn.setClickable(true);
                    bh_timesat_end_eve_btn.setTextColor(Color.BLACK);
                    sat_eve_clicked = true;
                }
                break;

            case R.id.sun_eve_tv:
                if (sun_eve_clicked) {

                    sun_eve_tv.setBackgroundResource(R.color.gray);
                    bh_timesun_start_eve_btn.setClickable(false);
                    bh_timesun_start_eve_btn.setTextColor(Color.GRAY);
                    bh_timesun_end_eve_btn.setClickable(false);
                    bh_timesun_end_eve_btn.setTextColor(Color.GRAY);
                    sun_eve_clicked = false;
                } else {
                    sun_eve_tv.setBackgroundResource(R.color.blue);
                    bh_timesun_start_eve_btn.setClickable(false);
                    bh_timesun_start_eve_btn.setTextColor(Color.BLACK);
                    bh_timesun_end_eve_btn.setClickable(false);
                    bh_timesun_end_eve_btn.setTextColor(Color.BLACK);
                    sun_eve_clicked = true;
                }
                break;
            case R.id.sun_mor_tv:
                if (sun_mor_clicked) {

                    sun_mor_tv.setBackgroundResource(R.color.gray);
                    newstaff_working_timesun_start_btn.setClickable(false);
                    newstaff_working_timesun_start_btn.setTextColor(Color.GRAY);
                    newstaff_working_timesun_end_btn.setClickable(false);
                    newstaff_working_timesun_end_btn.setTextColor(Color.GRAY);
                    sun_mor_clicked = false;
                } else {
                    sun_mor_tv.setBackgroundResource(R.color.blue);
                    newstaff_working_timesun_start_btn.setClickable(true);
                    newstaff_working_timesun_start_btn.setTextColor(Color.BLACK);
                    newstaff_working_timesun_end_btn.setClickable(true);
                    newstaff_working_timesun_end_btn.setTextColor(Color.BLACK);
                    sun_mor_clicked = true;
                }
                break;


        }
    }

    @OnClick(R.id.newstaff_working_back_btn)
    void goBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.newstaff_working_date_start_btn)
    void getstartDate() {
        // set calender visible date in between business start and end date

        AppUtils.getDatewithCallback(newstaff_working_date_start_btn, getActivity(), business_start_date,
                business_end_date, callback);


/*        getDateWithcallback(newstaff_working_date_start_btn, callback);*/

    }

   /* @OnClick(R.id.newstaff_working_date_end_btn)
    void getendDate() {
        // set calender visible date in between business start and end date
        if (newstaff_working_date_switch_btn.isChecked()) {
        AppUtils.getDatewithCallback(newstaff_working_date_end_btn, getActivity(), start_date, end_date, callback);

       // getDateWithcallback(newstaff_working_date_end_btn, callback);
        } else {

        }

    }*/

    // mon time click listener
    @OnClick(R.id.newstaff_working_timemon_start_btn)
    void getmonStartTime() {
     /*   getTimeback(newstaff_working_timemon_start_btn,
                newstaff_working_timemon_start_btn.getText().toString());
     */
        getTimestaff(newstaff_working_timemon_start_btn,
                business_mon_from_time, business_mon_to_time);
    }

    @OnClick(R.id.bh_timemon_start_eve_btn)
    void getmonEveStartTime() {
     /*   getTimeback(newstaff_working_timemon_start_btn,
                newstaff_working_timemon_start_btn.getText().toString());
     */
        getTimestaff(bh_timemon_start_eve_btn,
                business_mon_from_time, business_mon_to_time);
    }

    @OnClick(R.id.newstaff_working_timemon_end_btn)
    void getmonEndTime() {

        getTimestaff(newstaff_working_timemon_end_btn,
                newstaff_working_timemon_start_btn.getText().toString(), business_mon_to_time);


/*  getTimestaff(newstaff_working_timemon_end_btn,
                businessHourData.getMon_start_time(), businessHourData.getMon_end_time());


    }*/
    }

    @OnClick(R.id.bh_timemon_end_eve_btn)
    void getmonEveEndTime() {

        getTimestaff(bh_timemon_end_eve_btn,
                newstaff_working_timemon_end_btn.getText().toString(), business_mon_to_time);


/*  getTimestaff(newstaff_working_timemon_end_btn,
                businessHourData.getMon_start_time(), businessHourData.getMon_end_time());


    }*/
    }

    // tue time click listener
    @OnClick(R.id.newstaff_working_timetue_start_btn)
    void gettueStartTime() {

        getTimestaff(newstaff_working_timetue_start_btn,
                business_tue_from_time, business_tue_to_time);


    }

    @OnClick(R.id.bh_timetue_start_eve_btn)
    void gettueEveStartTime() {

        getTimestaff(bh_timetue_start_eve_btn,
                business_tue_from_time, business_tue_to_time);


    }

    @OnClick(R.id.newstaff_working_timetue_end_btn)
    void gettueEndTime() {
        getTimestaff(newstaff_working_timetue_end_btn,
                newstaff_working_timetue_start_btn.getText().toString(), business_tue_to_time);

    }

    @OnClick(R.id.bh_timetue_end_eve_btn)
    void gettueEveEndTime() {
        getTimestaff(bh_timetue_end_eve_btn,
                bh_timetue_start_eve_btn.getText().toString(), business_tue_to_time);

    }


    // wed time click listener
    @OnClick(R.id.newstaff_working_timewed_start_btn)
    void getwedStartTime() {
        getTimestaff(newstaff_working_timewed_start_btn,
                business_wed_from_time, business_wed_to_time);

    }

    @OnClick(R.id.bh_timewed_start_eve_btn)
    void getwedEveStartTime() {
        getTimestaff(bh_timewed_start_eve_btn,
                newstaff_working_timewed_start_btn.getText().toString(), business_wed_to_time);

    }

    @OnClick(R.id.newstaff_working_timewed_end_btn)
    void getwedEndTime() {
        getTimestaff(newstaff_working_timewed_end_btn,
                business_wed_from_time, business_wed_to_time);

    }

    @OnClick(R.id.bh_timewed_end_eve_btn)
    void getwedEveEndTime() {
        getTimestaff(bh_timewed_end_eve_btn,
                newstaff_working_timewed_end_btn.getText().toString(), business_wed_to_time);

    }

    // thr time click listener
    @OnClick(R.id.newstaff_working_timethr_start_btn)
    void getthrStartTime() {
        getTimestaff(newstaff_working_timethr_start_btn,
                business_thru_from_time, business_thru_to_time);

    }

    @OnClick(R.id.bh_timethr_start_eve_btn)
    void getthrEveStartTime() {
        getTimestaff(bh_timethr_start_eve_btn,
                newstaff_working_timethr_start_btn.getText().toString(), business_thru_to_time);

    }

    @OnClick(R.id.newstaff_working_timethr_end_btn)
    void getthrEndTime() {
        getTimestaff(newstaff_working_timethr_end_btn,
                business_thru_from_time, business_thru_to_time);

    }

    @OnClick(R.id.bh_timethr_end_eve_btn)
    void getthrEveEndTime() {
        getTimestaff(bh_timethr_end_eve_btn,
                newstaff_working_timethr_end_btn.getText().toString(), business_thru_to_time);

    }

    // fri time click listener
    @OnClick(R.id.newstaff_working_timefri_start_btn)
    void getfriStartTime() {

        getTimestaff(newstaff_working_timefri_start_btn,
                business_fri_from_time, business_fri_to_time);

    }

    @OnClick(R.id.bh_timefri_start_eve_btn)
    void getfriEveStartTime() {

        getTimestaff(bh_timefri_start_eve_btn,
                newstaff_working_timefri_start_btn.getText().toString(), business_fri_to_time);

    }

    @OnClick(R.id.newstaff_working_timefri_end_btn)
    void getfriEndTime() {

        getTimestaff(newstaff_working_timefri_end_btn,
                business_fri_from_time, business_fri_to_time);


    }

    @OnClick(R.id.bh_timefri_end_eve_btn)
    void getfriEveEndTime() {

        getTimestaff(bh_timefri_end_eve_btn,
                newstaff_working_timefri_end_btn.getText().toString(), business_fri_to_time);


    }

    // sat time click listener
    @OnClick(R.id.newstaff_working_timesat_start_btn)
    void getsatStartTime() {
        getTimestaff(newstaff_working_timesat_start_btn,
                business_sat_from_time, business_sat_to_time);


    }

    @OnClick(R.id.bh_timesat_start_eve_btn)
    void getsatEveStartTime() {
        getTimestaff(bh_timesat_start_eve_btn,
                business_sat_from_time, business_sat_to_time);


    }

    @OnClick(R.id.newstaff_working_timesat_end_btn)
    void getsatEndTime() {


        getTimestaff(newstaff_working_timesat_end_btn,
                business_sat_from_time, business_sat_to_time);

    }

    @OnClick(R.id.bh_timesat_end_eve_btn)
    void getsatEveEndTime() {


        getTimestaff(bh_timesat_end_eve_btn,
                business_sat_from_time, business_sat_to_time);

    }

    // sun time click listener
    @OnClick(R.id.newstaff_working_timesun_start_btn)
    void getsunStartTime() {
        getTimestaff(newstaff_working_timesun_start_btn,
                business_sun_from_time, business_sun_to_time);

    }

    @OnClick(R.id.bh_timesun_start_eve_btn)
    void getsunEveStartTime() {
        getTimestaff(bh_timesun_start_eve_btn,
                business_sun_from_time, business_sun_to_time);

    }

    @OnClick(R.id.newstaff_working_timesun_end_btn)
    void getsunEndTime() {
        getTimestaff(newstaff_working_timesun_end_btn,
                newstaff_working_timesun_start_btn.getText().toString(), business_sun_to_time);

    }

    @OnClick(R.id.bh_timesun_end_eve_btn)
    void getsunEveEndTime() {
        getTimestaff(bh_timesun_end_eve_btn,
                bh_timesun_start_eve_btn.getText().toString(), business_sun_to_time);

    }


    @SuppressLint("ResourceType")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.newstaff_working_date_switch_btn) {

            if (b) {
                newstaff_working_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                callback.getResponse("Date");

            } else {
                newstaff_working_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                newstaff_working_timemon_switch_btn.setEnabled(true);
                newstaff_working_timemon_switch_btn.setChecked(true);

                newstaff_working_timetue_switch_btn.setEnabled(true);
                newstaff_working_timetue_switch_btn.setChecked(true);

                newstaff_working_timewed_switch_btn.setEnabled(true);
                newstaff_working_timewed_switch_btn.setChecked(true);

                newstaff_working_timethr_switch_btn.setEnabled(true);
                newstaff_working_timethr_switch_btn.setChecked(true);

                newstaff_working_timefri_switch_btn.setEnabled(true);
                newstaff_working_timefri_switch_btn.setChecked(true);

                newstaff_working_timesat_switch_btn.setEnabled(true);
                newstaff_working_timesat_switch_btn.setChecked(true);

                newstaff_working_timesun_switch_btn.setEnabled(true);
                newstaff_working_timesun_switch_btn.setChecked(true);
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timesun_switch_btn) {
            if (b) {
                newstaff_working_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timesun_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timesun_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                sun_mor_tv.setBackgroundResource(R.color.blue);
                sun_eve_tv.setBackgroundResource(R.color.blue);


            } else {
                newstaff_working_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timesun_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                bh_timesun_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                sun_mor_tv.setBackgroundResource(R.color.gray);
                sun_eve_tv.setBackgroundResource(R.color.gray);

            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timemon_switch_btn) {
            if (b) {
                newstaff_working_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timemon_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timemon_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mon_mor_tv.setBackgroundResource(R.color.blue);
                mon_eve_tv.setBackgroundResource(R.color.blue);


            } else {
                newstaff_working_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timemon_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timemon_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                mon_mor_tv.setBackgroundResource(R.color.gray);
                mon_eve_tv.setBackgroundResource(R.color.gray);


            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timetue_switch_btn) {
            if (b) {
                newstaff_working_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timetue_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timetue_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                tue_mor_tv.setBackgroundResource(R.color.blue);
                tue_eve_tv.setBackgroundResource(R.color.blue);


            } else {
                newstaff_working_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timetue_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                bh_timetue_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                tue_mor_tv.setBackgroundResource(R.color.gray);
                tue_eve_tv.setBackgroundResource(R.color.gray);


            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timewed_switch_btn) {
            if (b) {
                newstaff_working_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timewed_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timewed_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                wed_mor_tv.setBackgroundResource(R.color.blue);
                wed_eve_tv.setBackgroundResource(R.color.blue);


            } else {
                newstaff_working_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timewed_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                bh_timewed_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                wed_mor_tv.setBackgroundResource(R.color.gray);
                wed_eve_tv.setBackgroundResource(R.color.gray);

            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timethr_switch_btn) {
            if (b) {
                newstaff_working_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timethr_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timethr_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                thr_mor_tv.setBackgroundResource(R.color.blue);
                thr_eve_tv.setBackgroundResource(R.color.blue);


            } else {
                newstaff_working_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timethr_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                bh_timethr_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                thr_mor_tv.setBackgroundResource(R.color.gray);
                thr_eve_tv.setBackgroundResource(R.color.gray);

            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timefri_switch_btn) {
            if (b) {
                newstaff_working_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timefri_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timefri_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                fri_mor_tv.setBackgroundResource(R.color.blue);
                fri_eve_tv.setBackgroundResource(R.color.blue);


            } else {
                newstaff_working_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timefri_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                bh_timefri_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                fri_mor_tv.setBackgroundResource(R.color.gray);
                fri_eve_tv.setBackgroundResource(R.color.gray);

            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timesat_switch_btn) {
            if (b) {

                newstaff_working_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timesat_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timesat_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                sat_mor_tv.setBackgroundResource(R.color.blue);
                sat_eve_tv.setBackgroundResource(R.color.blue);


            } else {

                newstaff_working_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timesat_start_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                bh_timesat_end_eve_btn.setTextColor(getActivity().getResources().getColor(R.color.gray));
                sat_mor_tv.setBackgroundResource(R.color.gray);
                sat_eve_tv.setBackgroundResource(R.color.gray);


            }
        }


    }

    @Override
    public void getResponse(String response) {

        Log.e("res", response);
        if (response.contains("Error :")) {

            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (post_tag.equalsIgnoreCase("staffFinalHourInfo")) {
            try {
                JSONObject main_obj = new JSONObject(response);
                Log.d("Response", "comming" + response);

                Bundle _bundle = new Bundle();
                _bundle.putString("src", bundle.getString("src"));
                _bundle.putInt("pos", bundle.getInt("pos"));
                _bundle.putString("coming_from", "staff_hour");

                replaceFrag(new AddStaffFrag(), _bundle, BusinessHourFrag.class.getName());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (post_tag.equalsIgnoreCase("staffHourInfo")) {
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                end_date = main_obj.getString("end_date");
                business_end_date = main_obj.getString("business_end_date");
                start_date = main_obj.getString("start_date");
                business_start_date = main_obj.getString("business_start_date");


                mon_from_time = main_obj.getString("mon_from_time");
                mon_to_time = main_obj.getString("mon_to_time");
                tue_from_time = main_obj.getString("tue_from_time");
                tue_to_time = main_obj.getString("tue_to_time");
                wed_from_time = main_obj.getString("wed_from_time");
                wed_to_time = main_obj.getString("wed_to_time");
                thru_from_time = main_obj.getString("thru_from_time");
                thru_to_time = main_obj.getString("thru_to_time");
                fri_from_time = main_obj.getString("fri_from_time");
                fri_to_time = main_obj.getString("fri_to_time");
                sat_from_time = main_obj.getString("sat_from_time");
                sat_to_time = main_obj.getString("sat_to_time");
                sun_from_time = main_obj.getString("sun_from_time");
                sun_to_time = main_obj.getString("sun_to_time");


                emon_from_time = main_obj.getString("mon_eve_from_time");
                emon_to_time = main_obj.getString("mon_eve_to_time");
                etue_from_time = main_obj.getString("tue_eve_from_time");
                etue_to_time = main_obj.getString("tue_eve_to_time");
                ewed_from_time = main_obj.getString("wed_eve_from_time");
                ewed_to_time = main_obj.getString("wed_eve_to_time");
                ethru_from_time = main_obj.getString("thru_eve_from_time");
                ethru_to_time = main_obj.getString("thru_eve_to_time");
                efri_from_time = main_obj.getString("fri_eve_from_time");
                efri_to_time = main_obj.getString("fri_eve_to_time");
                esat_from_time = main_obj.getString("sat_eve_from_time");
                esat_to_time = main_obj.getString("sat_eve_to_time");
                esun_from_time = main_obj.getString("sun_eve_from_time");
                esun_to_time = main_obj.getString("sun_eve_to_time");


                // address_id = main_obj.getString("address_id");

                business_mon_from_time = main_obj.getString("business_mon_from_time");
                business_mon_to_time = main_obj.getString("business_mon_to_time");
                business_tue_from_time = main_obj.getString("business_tue_from_time");
                business_tue_to_time = main_obj.getString("business_tue_to_time");
                business_wed_from_time = main_obj.getString("business_wed_from_time");
                business_wed_to_time = main_obj.getString("business_wed_to_time");
                business_thru_from_time = main_obj.getString("business_thru_from_time");
                business_thru_to_time = main_obj.getString("business_thru_to_time");
                business_fri_from_time = main_obj.getString("business_fri_from_time");
                business_fri_to_time = main_obj.getString("business_fri_to_time");
                business_sat_from_time = main_obj.getString("business_sat_from_time");
                business_sat_to_time = main_obj.getString("business_sat_to_time");
                business_sun_from_time = main_obj.getString("business_sun_from_time");
                business_sun_to_time = main_obj.getString("business_sun_to_time");
                String business_industry = main_obj.getString("business_industry");
                String message = main_obj.getString("message");

                Log.d("NSWHF", "business_industry" + business_industry);
                setAllDataFun(business_industry);
                //   showRelatedFields(business_industry);

             /*   newstaff_working_date_start_btn.setText(start_date);
                newstaff_working_date_end_btn.setText(end_date);
*/

            } catch (Exception e) {

            }

            //  setManualData();
        }

    }

    private void setAllDataFun(String business_industry) {

        if (business_industry.equalsIgnoreCase("Doctor")) {
            mon_mor_tv.setVisibility(View.VISIBLE);
            mon_eve_tv.setVisibility(View.VISIBLE);
            bh_timemon_start_eve_btn.setVisibility(View.VISIBLE);
            //   bh_timemon_start_eve_btn.setText("05:00 PM");
            //  bh_timemon_start_btn.setText("10:00 AM");
            bh_timemon_end_eve_btn.setVisibility(View.VISIBLE);
            //   bh_timemon_end_eve_btn.setText("09:00 PM");
            // bh_timemon_end_btn.setText("02:00 PM");
            tue_mor_tv.setVisibility(View.VISIBLE);
            tue_eve_tv.setVisibility(View.VISIBLE);
            bh_timetue_start_eve_btn.setVisibility(View.VISIBLE);
            //  bh_timetue_start_btn.setText("10:00 AM");
            //    bh_timetue_start_eve_btn.setText("05:00 PM");
            bh_timetue_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timetue_end_btn.setText("02:00 PM");
            //   bh_timetue_end_eve_btn.setText("09:00 PM");
            wed_mor_tv.setVisibility(View.VISIBLE);
            wed_eve_tv.setVisibility(View.VISIBLE);
            bh_timewed_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timewed_start_btn.setText("10:00 AM");
            //   bh_timewed_start_eve_btn.setText("05:00 PM");
            bh_timewed_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timewed_end_btn.setText("02:00 PM");
            //    bh_timewed_end_eve_btn.setText("09:00 PM");
            thr_mor_tv.setVisibility(View.VISIBLE);
            thr_eve_tv.setVisibility(View.VISIBLE);
            bh_timethr_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timethr_start_btn.setText("10:00 AM");
            //   bh_timethr_start_eve_btn.setText("05:00 PM");
            bh_timethr_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timethr_end_btn.setText("02:00 PM");
            //  bh_timethr_end_eve_btn.setText("09:00 PM");
            fri_mor_tv.setVisibility(View.VISIBLE);
            fri_eve_tv.setVisibility(View.VISIBLE);
            bh_timefri_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timefri_start_btn.setText("10:00 AM");
            //   bh_timefri_start_eve_btn.setText("05:00 PM");
            bh_timefri_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timefri_end_btn.setText("02:00 PM");
            //  bh_timefri_end_eve_btn.setText("09:00 PM");
            sat_mor_tv.setVisibility(View.VISIBLE);
            sat_eve_tv.setVisibility(View.VISIBLE);
            bh_timesat_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesat_start_btn.setText("10:00 AM");
            //   bh_timesat_start_eve_btn.setText("05:00 PM");
            bh_timesat_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesat_end_btn.setText("02:00 PM");
            //   bh_timesat_end_eve_btn.setText("09:00 PM");
            sun_mor_tv.setVisibility(View.VISIBLE);
            sun_eve_tv.setVisibility(View.VISIBLE);
            bh_timesun_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesun_start_btn.setText("10:00 AM");
            //    bh_timesun_start_eve_btn.setText("05:00 PM");
            bh_timesun_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesun_end_btn.setText("02:00 PM");
            //    bh_timesun_end_eve_btn.setText("09:00 PM");


        }else if (business_industry.equalsIgnoreCase("Hospital")||business_industry.equalsIgnoreCase("Clinic")) {
            mon_mor_tv.setVisibility(View.VISIBLE);
            mon_eve_tv.setVisibility(View.VISIBLE);
            bh_timemon_start_eve_btn.setVisibility(View.VISIBLE);
            //   bh_timemon_start_eve_btn.setText("05:00 PM");
            //  bh_timemon_start_btn.setText("10:00 AM");
            bh_timemon_end_eve_btn.setVisibility(View.VISIBLE);
            //   bh_timemon_end_eve_btn.setText("09:00 PM");
            // bh_timemon_end_btn.setText("02:00 PM");
            tue_mor_tv.setVisibility(View.VISIBLE);
            tue_eve_tv.setVisibility(View.VISIBLE);
            bh_timetue_start_eve_btn.setVisibility(View.VISIBLE);
            //  bh_timetue_start_btn.setText("10:00 AM");
            //    bh_timetue_start_eve_btn.setText("05:00 PM");
            bh_timetue_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timetue_end_btn.setText("02:00 PM");
            //   bh_timetue_end_eve_btn.setText("09:00 PM");
            wed_mor_tv.setVisibility(View.VISIBLE);
            wed_eve_tv.setVisibility(View.VISIBLE);
            bh_timewed_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timewed_start_btn.setText("10:00 AM");
            //   bh_timewed_start_eve_btn.setText("05:00 PM");
            bh_timewed_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timewed_end_btn.setText("02:00 PM");
            //    bh_timewed_end_eve_btn.setText("09:00 PM");
            thr_mor_tv.setVisibility(View.VISIBLE);
            thr_eve_tv.setVisibility(View.VISIBLE);
            bh_timethr_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timethr_start_btn.setText("10:00 AM");
            //   bh_timethr_start_eve_btn.setText("05:00 PM");
            bh_timethr_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timethr_end_btn.setText("02:00 PM");
            //  bh_timethr_end_eve_btn.setText("09:00 PM");
            fri_mor_tv.setVisibility(View.VISIBLE);
            fri_eve_tv.setVisibility(View.VISIBLE);
            bh_timefri_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timefri_start_btn.setText("10:00 AM");
            //   bh_timefri_start_eve_btn.setText("05:00 PM");
            bh_timefri_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timefri_end_btn.setText("02:00 PM");
            //  bh_timefri_end_eve_btn.setText("09:00 PM");
            sat_mor_tv.setVisibility(View.VISIBLE);
            sat_eve_tv.setVisibility(View.VISIBLE);
            bh_timesat_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesat_start_btn.setText("10:00 AM");
            //   bh_timesat_start_eve_btn.setText("05:00 PM");
            bh_timesat_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesat_end_btn.setText("02:00 PM");
            //   bh_timesat_end_eve_btn.setText("09:00 PM");
            sun_mor_tv.setVisibility(View.VISIBLE);
            sun_eve_tv.setVisibility(View.VISIBLE);
            bh_timesun_start_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesun_start_btn.setText("10:00 AM");
            //    bh_timesun_start_eve_btn.setText("05:00 PM");
            bh_timesun_end_eve_btn.setVisibility(View.VISIBLE);
            // bh_timesun_end_btn.setText("02:00 PM");
            //    bh_timesun_end_eve_btn.setText("09:00 PM");


        } else {

            sat_eve_clicked=false;
            mon_eve_clicked=false;
            tue_eve_clicked=false;
            wed_eve_clicked=false;
            thru_eve_clicked=false;
            fri_eve_clicked=false;
            sun_eve_clicked=false;
        /*    mon_eve_from_time = "";
            mon_eve_to_time = "";

            tue_eve_from_time = "";
            tue_eve_to_time = "";

            wed_eve_from_time = "";
            wed_eve_to_time = "";

            thru_eve_from_time = "";
            thru_eve_to_time = "";

            fri_eve_from_time = "";
            fri_eve_to_time = "";

            sat_eve_from_time = "";
            sat_eve_to_time = "";

            sun_eve_from_time = "";
            sun_eve_to_time = "";
*/
          /*  bh_timemon_start_eve_btn.setText("");
            bh_timemon_end_eve_btn.setText("");

            bh_timetue_start_eve_btn.setText("");
            bh_timetue_end_eve_btn.setText("");

            bh_timewed_start_eve_btn.setText("");
            bh_timewed_end_eve_btn.setText("");

            bh_timethr_start_eve_btn.setText("");
            bh_timethr_end_eve_btn.setText("");

            bh_timefri_start_eve_btn.setText("");
            bh_timefri_end_eve_btn.setText("");

            bh_timesat_start_eve_btn.setText("");
            bh_timesat_end_eve_btn.setText("");

            // Sun start and End Time
            bh_timesun_start_eve_btn.setText("");
            bh_timesun_end_eve_btn.setText("");

*/
        }

        Log.d("NSWHF", "start_date" + start_date);
        if (start_date.equals("") || start_date.equals("null")) {
            newstaff_working_date_start_btn.setText(business_start_date);
            newstaff_working_date_end_btn.setText(business_end_date);
            Log.d("NSWHF", "mon_from_time" + mon_from_time + "sun_from_time" + sun_from_time);

          /*  newstaff_working_timemon_start_btn.setText(business_mon_from_time);
            newstaff_working_timemon_end_btn.setText(business_mon_to_time);
          */
            if (business_mon_from_time.equals("")) {

                newstaff_working_timemon_switch_btn.setChecked(false);
                newstaff_working_timemon_start_btn.setClickable(false);
                newstaff_working_timemon_end_btn.setClickable(false);
                bh_timemon_start_eve_btn.setClickable(false);
                bh_timemon_end_eve_btn.setClickable(false);
                mon_eve_tv.setBackgroundResource(R.color.gray);
                mon_mor_tv.setBackgroundResource(R.color.gray);


            } else {
                newstaff_working_timemon_switch_btn.setEnabled(true);
                newstaff_working_timemon_switch_btn.setChecked(true);
                newstaff_working_timemon_start_btn.setText(business_mon_from_time);
                newstaff_working_timemon_end_btn.setText(business_mon_to_time);
                newstaff_working_timemon_start_btn.setClickable(true);
                newstaff_working_timemon_end_btn.setClickable(true);
                bh_timemon_start_eve_btn.setClickable(true);
                bh_timemon_end_eve_btn.setClickable(true);

            }

            if (business_tue_from_time.equals("")) {

                newstaff_working_timetue_switch_btn.setChecked(false);
                newstaff_working_timetue_start_btn.setClickable(false);
                newstaff_working_timetue_end_btn.setClickable(false);
                bh_timetue_start_eve_btn.setClickable(false);
                bh_timetue_end_eve_btn.setClickable(false);
                tue_mor_tv.setBackgroundResource(R.color.gray);
                tue_eve_tv.setBackgroundResource(R.color.gray);
            } else {

                newstaff_working_timetue_start_btn.setText(business_tue_from_time);
                newstaff_working_timetue_end_btn.setText(business_tue_to_time);
                newstaff_working_timetue_switch_btn.setChecked(true);
                newstaff_working_timetue_start_btn.setClickable(true);
                newstaff_working_timetue_end_btn.setClickable(true);
                bh_timetue_start_eve_btn.setClickable(true);
                bh_timetue_end_eve_btn.setClickable(true);


            }
            if (business_wed_from_time.equals("")) {
                newstaff_working_timewed_switch_btn.setChecked(false);
                newstaff_working_timewed_start_btn.setClickable(false);
                newstaff_working_timewed_end_btn.setClickable(false);
                bh_timewed_start_eve_btn.setClickable(false);
                bh_timewed_end_eve_btn.setClickable(false);
                wed_mor_tv.setBackgroundResource(R.color.gray);
                wed_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timewed_switch_btn.setChecked(true);
                newstaff_working_timewed_start_btn.setText(business_wed_from_time);
                newstaff_working_timewed_end_btn.setText(business_wed_to_time);
                newstaff_working_timewed_start_btn.setClickable(true);
                newstaff_working_timewed_end_btn.setClickable(true);
                bh_timewed_start_eve_btn.setClickable(true);
                bh_timewed_end_eve_btn.setClickable(true);


            }
            if (business_thru_from_time.equals("")) {
                newstaff_working_timethr_switch_btn.setChecked(false);
                newstaff_working_timethr_start_btn.setClickable(false);
                newstaff_working_timethr_end_btn.setClickable(false);
                bh_timethr_start_eve_btn.setClickable(false);
                bh_timethr_end_eve_btn.setClickable(false);
                thr_mor_tv.setBackgroundResource(R.color.gray);
                thr_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timethr_switch_btn.setChecked(true);
                newstaff_working_timethr_start_btn.setText(business_thru_from_time);
                newstaff_working_timethr_end_btn.setText(business_thru_to_time);
                newstaff_working_timethr_start_btn.setClickable(true);
                newstaff_working_timethr_end_btn.setClickable(true);
                bh_timethr_start_eve_btn.setClickable(true);
                bh_timethr_end_eve_btn.setClickable(true);


            }
            if (business_fri_from_time.equals("")) {
                newstaff_working_timefri_switch_btn.setChecked(false);
                newstaff_working_timefri_start_btn.setClickable(false);
                newstaff_working_timefri_end_btn.setClickable(false);
                bh_timefri_start_eve_btn.setClickable(false);
                bh_timefri_end_eve_btn.setClickable(false);
                fri_mor_tv.setBackgroundResource(R.color.gray);
                fri_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timefri_switch_btn.setChecked(true);
                newstaff_working_timefri_start_btn.setText(business_fri_from_time);
                newstaff_working_timefri_end_btn.setText(business_fri_to_time);
                newstaff_working_timefri_start_btn.setClickable(true);
                newstaff_working_timefri_end_btn.setClickable(true);
                bh_timefri_start_eve_btn.setClickable(true);
                bh_timefri_end_eve_btn.setClickable(true);


            }
            if (business_sat_from_time.equals("")) {
                newstaff_working_timesat_switch_btn.setChecked(false);
                newstaff_working_timesat_start_btn.setClickable(false);
                newstaff_working_timesat_end_btn.setClickable(false);
                bh_timesat_start_eve_btn.setClickable(false);
                bh_timesat_end_eve_btn.setClickable(false);
                sat_mor_tv.setBackgroundResource(R.color.gray);
                sat_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timesat_switch_btn.setChecked(true);
                newstaff_working_timesat_start_btn.setText(business_sat_from_time);
                newstaff_working_timesat_end_btn.setText(business_sat_to_time);
                newstaff_working_timesat_start_btn.setClickable(true);
                newstaff_working_timesat_end_btn.setClickable(true);
                bh_timesat_start_eve_btn.setClickable(true);
                bh_timesat_end_eve_btn.setClickable(true);


            }
            if (business_sun_from_time.equals("")) {
                newstaff_working_timesun_switch_btn.setChecked(false);
                newstaff_working_timesun_start_btn.setClickable(false);
                newstaff_working_timesun_end_btn.setClickable(false);
                bh_timesun_start_eve_btn.setClickable(false);
                bh_timesun_end_eve_btn.setClickable(false);
                sun_mor_tv.setBackgroundResource(R.color.gray);
                sun_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timesun_switch_btn.setChecked(true);
                newstaff_working_timesun_start_btn.setText(business_sun_from_time);
                newstaff_working_timesun_end_btn.setText(business_sun_to_time);
                newstaff_working_timesun_start_btn.setClickable(true);
                newstaff_working_timesun_end_btn.setClickable(true);
                bh_timesun_start_eve_btn.setClickable(true);
                bh_timesun_end_eve_btn.setClickable(true);


            }


        } else {
            newstaff_working_date_start_btn.setText(start_date);
            newstaff_working_date_end_btn.setText(end_date);

            if (mon_from_time.equals("") && emon_from_time.equals("")) {

                Log.d("NSWHF", "mon_from_time" + mon_from_time + "emon_from_time" + emon_from_time);
                newstaff_working_timemon_end_btn.setTextColor(Color.GRAY);
                newstaff_working_timemon_start_btn.setTextColor(Color.GRAY);
                bh_timemon_start_eve_btn.setTextColor(Color.GRAY);
                bh_timemon_end_eve_btn.setTextColor(Color.GRAY);
                mon_eve_tv.setBackgroundResource(R.color.gray);
                mon_mor_tv.setBackgroundResource(R.color.gray);
                newstaff_working_timemon_end_btn.setClickable(false);
                bh_timemon_start_eve_btn.setClickable(false);
                bh_timemon_end_eve_btn.setClickable(false);
                newstaff_working_timemon_switch_btn.setChecked(false);
                newstaff_working_timemon_start_btn.setClickable(false);


            } else if (mon_from_time.equals("")) {
                mon_eve_clicked = true;
                mon_mor_clicked = false;
                newstaff_working_timemon_switch_btn.setEnabled(true);
                newstaff_working_timemon_switch_btn.setChecked(true);
                newstaff_working_timemon_start_btn.setText("08:00 AM");
                newstaff_working_timemon_end_btn.setText("04:00 PM");
                mon_mor_tv.setBackgroundResource(R.color.gray);
                bh_timemon_start_eve_btn.setText(emon_from_time);
                bh_timemon_end_eve_btn.setText(emon_to_time);

            } else if (emon_from_time.equals("")) {
                mon_eve_clicked = false;
                mon_mor_clicked = true;
                newstaff_working_timemon_switch_btn.setEnabled(true);
                newstaff_working_timemon_switch_btn.setChecked(true);
                newstaff_working_timemon_start_btn.setText(mon_from_time);
                newstaff_working_timemon_end_btn.setText(mon_to_time);
                mon_eve_tv.setBackgroundResource(R.color.gray);
                bh_timemon_start_eve_btn.setText("05:00 PM");
                bh_timemon_end_eve_btn.setText("08:00 PM");

            } else {
                newstaff_working_timemon_switch_btn.setEnabled(true);
                newstaff_working_timemon_switch_btn.setChecked(true);
                newstaff_working_timemon_start_btn.setText(mon_from_time);
                newstaff_working_timemon_end_btn.setText(mon_to_time);
                bh_timemon_start_eve_btn.setText(emon_from_time);
                bh_timemon_end_eve_btn.setText(emon_to_time);

            }

            if (tue_from_time.equals("") && etue_from_time.equals("")) {
                newstaff_working_timetue_switch_btn.setChecked(false);
                newstaff_working_timetue_start_btn.setTextColor(Color.GRAY);
                newstaff_working_timetue_end_btn.setTextColor(Color.GRAY);
                bh_timetue_start_eve_btn.setTextColor(Color.GRAY);
                bh_timetue_end_eve_btn.setTextColor(Color.GRAY);
                tue_mor_tv.setBackgroundResource(R.color.gray);
                tue_eve_tv.setBackgroundResource(R.color.gray);


            } else if (tue_from_time.equals("")) {
                tue_mor_clicked = false;
                tue_eve_clicked = true;
                newstaff_working_timetue_start_btn.setText("10:00 AM");
                newstaff_working_timetue_end_btn.setText("04:00 PM");
                newstaff_working_timetue_switch_btn.setChecked(true);
                bh_timetue_start_eve_btn.setText(etue_from_time);
                tue_mor_tv.setBackgroundResource(R.color.gray);
                bh_timetue_end_eve_btn.setText(etue_to_time);
            } else if (etue_from_time.equals("")) {
                tue_mor_clicked = true;
                tue_eve_clicked = false;
                newstaff_working_timetue_start_btn.setText(tue_from_time);
                newstaff_working_timetue_end_btn.setText(tue_to_time);
                newstaff_working_timetue_switch_btn.setChecked(true);
                bh_timetue_start_eve_btn.setText("05:00 PM");
                tue_eve_tv.setBackgroundResource(R.color.gray);
                bh_timetue_end_eve_btn.setText("08:00 PM");
            } else {
                newstaff_working_timetue_start_btn.setText(tue_from_time);
                newstaff_working_timetue_end_btn.setText(tue_to_time);
                newstaff_working_timetue_switch_btn.setChecked(true);
                bh_timetue_start_eve_btn.setText(etue_from_time);
                bh_timetue_end_eve_btn.setText(etue_to_time);

            }
            if (wed_from_time.equals("") && ewed_from_time.equals("")) {
                newstaff_working_timewed_switch_btn.setChecked(false);
                newstaff_working_timewed_start_btn.setTextColor(Color.GRAY);
                newstaff_working_timewed_end_btn.setTextColor(Color.GRAY);
                bh_timewed_start_eve_btn.setTextColor(Color.GRAY);
                bh_timewed_end_eve_btn.setTextColor(Color.GRAY);
                wed_mor_tv.setBackgroundResource(R.color.gray);
                wed_mor_tv.setBackgroundResource(R.color.gray);

            } else if (wed_from_time.equals("")) {
                wed_eve_clicked = true;
                wed_mor_clicked = false;
                newstaff_working_timewed_switch_btn.setChecked(true);
                newstaff_working_timewed_switch_btn.setEnabled(true);
                newstaff_working_timewed_start_btn.setText("08:00 AM");
                newstaff_working_timewed_end_btn.setText("04:00 PM");
                bh_timewed_start_eve_btn.setText(ewed_from_time);
                bh_timewed_end_eve_btn.setText(ewed_to_time);
                wed_mor_tv.setBackgroundResource(R.color.gray);


            } else if (ewed_from_time.equals("")) {
                wed_eve_clicked = false;
                wed_mor_clicked = true;
                newstaff_working_timewed_switch_btn.setChecked(true);
                newstaff_working_timewed_switch_btn.setEnabled(true);
                newstaff_working_timewed_start_btn.setText(wed_from_time);
                newstaff_working_timewed_end_btn.setText(wed_to_time);
                bh_timewed_start_eve_btn.setText("05:00 PM");
                bh_timewed_end_eve_btn.setText("08:00 PM");
                wed_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timewed_switch_btn.setChecked(true);
                newstaff_working_timewed_switch_btn.setEnabled(true);
                newstaff_working_timewed_start_btn.setText(wed_from_time);
                newstaff_working_timewed_end_btn.setText(wed_to_time);
                bh_timewed_start_eve_btn.setText(ewed_from_time);
                bh_timewed_end_eve_btn.setText(ewed_to_time);
            }
            if (thru_from_time.equals("") && ethru_from_time.equals("")) {
                newstaff_working_timethr_switch_btn.setChecked(false);
                newstaff_working_timethr_start_btn.setTextColor(Color.GRAY);
                newstaff_working_timethr_end_btn.setTextColor(Color.GRAY);
                bh_timethr_start_eve_btn.setTextColor(Color.GRAY);
                bh_timethr_end_eve_btn.setTextColor(Color.GRAY);
                thr_mor_tv.setBackgroundResource(R.color.gray);
                thr_eve_tv.setBackgroundResource(R.color.gray);

            } else if (thru_from_time.equals("")) {
                thru_mor_clicked = false;
                thru_eve_clicked = true;
                newstaff_working_timethr_switch_btn.setChecked(true);
                newstaff_working_timethr_start_btn.setText("08:00 AM");
                newstaff_working_timethr_end_btn.setText("04:00 PM");
                bh_timethr_start_eve_btn.setText(ethru_from_time);
                bh_timethr_end_eve_btn.setText(ethru_to_time);
                thr_mor_tv.setBackgroundResource(R.color.gray);
            } else if (ethru_from_time.equals("")) {
                thru_mor_clicked = true;
                thru_eve_clicked = false;
                newstaff_working_timethr_switch_btn.setChecked(true);
                newstaff_working_timethr_start_btn.setText(thru_from_time);
                newstaff_working_timethr_end_btn.setText(thru_to_time);
                bh_timethr_start_eve_btn.setText("05:00 PM");
                bh_timethr_end_eve_btn.setText("08:00 PM");
                thr_eve_tv.setBackgroundResource(R.color.gray);
            } else {
                newstaff_working_timethr_switch_btn.setChecked(true);
                newstaff_working_timethr_start_btn.setText(thru_from_time);
                newstaff_working_timethr_end_btn.setText(thru_to_time);
                bh_timethr_start_eve_btn.setText(ethru_from_time);
                bh_timethr_end_eve_btn.setText(ethru_to_time);


            }
            if (fri_from_time.equals("") && efri_from_time.equals("")) {
                newstaff_working_timefri_switch_btn.setChecked(false);
                newstaff_working_timefri_start_btn.setTextColor(Color.GRAY);
                newstaff_working_timefri_end_btn.setTextColor(Color.GRAY);
                bh_timefri_start_eve_btn.setTextColor(Color.GRAY);
                bh_timefri_end_eve_btn.setTextColor(Color.GRAY);
                fri_mor_tv.setBackgroundResource(R.color.gray);
                fri_eve_tv.setBackgroundResource(R.color.gray);

            } else if (fri_from_time.equals("")) {
                fri_mor_clicked = false;
                fri_eve_clicked = true;
                newstaff_working_timefri_switch_btn.setChecked(true);
                newstaff_working_timefri_start_btn.setText("08:00 AM");
                newstaff_working_timefri_end_btn.setText("04:00 PM");
                bh_timefri_start_eve_btn.setText(efri_from_time);
                bh_timefri_end_eve_btn.setText(efri_to_time);
                fri_mor_tv.setBackgroundResource(R.color.gray);
            } else if (efri_from_time.equals("")) {
                fri_mor_clicked = true;
                fri_eve_clicked = false;
                newstaff_working_timefri_switch_btn.setChecked(true);
                newstaff_working_timefri_start_btn.setText(fri_from_time);
                newstaff_working_timefri_end_btn.setText(fri_to_time);
                bh_timefri_start_eve_btn.setText("05:00 PM");
                bh_timefri_end_eve_btn.setText("08:00 PM");
                fri_eve_tv.setBackgroundResource(R.color.gray);
            } else {
                newstaff_working_timefri_switch_btn.setChecked(true);
                newstaff_working_timefri_start_btn.setText(fri_from_time);
                newstaff_working_timefri_end_btn.setText(fri_to_time);
                bh_timefri_start_eve_btn.setText(efri_from_time);
                bh_timefri_end_eve_btn.setText(efri_to_time);

            }
            if (sat_from_time.equals("") && esat_from_time.equals("")) {
                newstaff_working_timesat_switch_btn.setChecked(false);
                newstaff_working_timesat_start_btn.setTextColor(Color.GRAY);
                newstaff_working_timesat_end_btn.setTextColor(Color.GRAY);
                bh_timesat_start_eve_btn.setTextColor(Color.GRAY);
                bh_timesat_end_eve_btn.setTextColor(Color.GRAY);
                sat_mor_tv.setBackgroundResource(R.color.gray);
                sat_eve_tv.setBackgroundResource(R.color.gray);


            } else if (sat_from_time.equals("")) {
                sat_mor_clicked = false;
                sat_eve_clicked = true;
                newstaff_working_timesat_switch_btn.setChecked(true);
                newstaff_working_timesat_start_btn.setText("08:00 AM");
                newstaff_working_timesat_end_btn.setText("04:00 PM");
                bh_timesat_start_eve_btn.setText(esat_from_time);
                bh_timesat_end_eve_btn.setText(esat_to_time);
                sat_mor_tv.setBackgroundResource(R.color.gray);

            } else if (esat_from_time.equals("")) {
                sat_mor_clicked = true;
                sat_eve_clicked = false;
                newstaff_working_timesat_switch_btn.setChecked(true);
                newstaff_working_timesat_start_btn.setText(sat_from_time);
                newstaff_working_timesat_end_btn.setText(sat_to_time);
                bh_timesat_start_eve_btn.setText("05:00 PM");
                bh_timesat_end_eve_btn.setText("08:00 PM");
                sat_eve_tv.setBackgroundResource(R.color.gray);

            } else {
                newstaff_working_timesat_switch_btn.setChecked(true);
                newstaff_working_timesat_start_btn.setText(sat_from_time);
                newstaff_working_timesat_end_btn.setText(sat_to_time);
                bh_timesat_start_eve_btn.setText(esat_from_time);
                bh_timesat_end_eve_btn.setText(esat_to_time);

            }
            if (sun_from_time.equals("") && esun_from_time.equals("")) {
                newstaff_working_timesun_switch_btn.setChecked(false);
                newstaff_working_timesun_start_btn.setTextColor(Color.GRAY);
                newstaff_working_timesun_end_btn.setTextColor(Color.GRAY);
                bh_timesun_start_eve_btn.setTextColor(Color.GRAY);
                bh_timesun_end_eve_btn.setTextColor(Color.GRAY);
                sun_mor_tv.setBackgroundResource(R.color.gray);
                sun_eve_tv.setBackgroundResource(R.color.gray);

            } else if (sun_from_time.equals("")) {
                sun_mor_clicked = false;
                sun_eve_clicked = true;
                newstaff_working_timesun_switch_btn.setChecked(true);
                newstaff_working_timesun_switch_btn.setEnabled(true);
                newstaff_working_timesun_start_btn.setText("08:00 AM");
                newstaff_working_timesun_end_btn.setText("04:00 PM");
                bh_timesun_start_eve_btn.setText(esun_from_time);
                bh_timesun_end_eve_btn.setText(esun_to_time);
                sun_mor_tv.setBackgroundResource(R.color.gray);


            } else if (esun_from_time.equals("")) {
                sun_mor_clicked = true;
                sun_eve_clicked = false;
                newstaff_working_timesun_switch_btn.setChecked(true);
                newstaff_working_timesun_switch_btn.setEnabled(true);
                newstaff_working_timesun_start_btn.setText(sun_from_time);
                newstaff_working_timesun_end_btn.setText(sun_to_time);
                bh_timesun_start_eve_btn.setText("05:00 PM");
                bh_timesun_end_eve_btn.setText("08:00 PM");
                sun_eve_tv.setBackgroundResource(R.color.gray);


            } else {
                newstaff_working_timesun_switch_btn.setChecked(true);
                newstaff_working_timesun_switch_btn.setEnabled(true);
                newstaff_working_timesun_start_btn.setText(sun_from_time);
                newstaff_working_timesun_end_btn.setText(sun_to_time);
                bh_timesun_start_eve_btn.setText(esun_from_time);
                bh_timesun_end_eve_btn.setText(esun_to_time);

            }

        }
    }
}
