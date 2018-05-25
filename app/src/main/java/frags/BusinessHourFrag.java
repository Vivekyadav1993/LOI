package frags;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Sharedpreferences;
import r2stech.lifeoninternet.utils.Utils;

/**
 * Created by teknik on 9/28/2017.
 */

public class BusinessHourFrag extends HelperFrags implements CompoundButton.OnCheckedChangeListener, HttpresponseUpd {

    private View Mroot;


    // select date widgets
    @BindView(R.id.bh_date_start_btn)
    TextView bh_date_start_btn;

    @BindView(R.id.bh_date_end_btn)
    TextView bh_date_end_btn;

    @BindView(R.id.bh_date_switch_btn)
    Switch bh_date_switch_btn;

    // monday time widgets
    @BindView(R.id.bh_timemon_start_btn)
    TextView bh_timemon_start_btn;

    @BindView(R.id.bh_timemon_end_btn)
    TextView bh_timemon_end_btn;

    @BindView(R.id.bh_timemon_switch_btn)
    Switch bh_timemon_switch_btn;

    // tuesday time widgets
    @BindView(R.id.bh_timetue_start_btn)
    TextView bh_timetue_start_btn;

    @BindView(R.id.bh_timetue_end_btn)
    TextView bh_timetue_end_btn;

    @BindView(R.id.bh_timetue_switch_btn)
    Switch bh_timetue_switch_btn;

    // wednesday time widgets
    @BindView(R.id.bh_timewed_start_btn)
    TextView bh_timewed_start_btn;

    @BindView(R.id.bh_timewed_end_btn)
    TextView bh_timewed_end_btn;

    @BindView(R.id.bh_timewed_switch_btn)
    Switch bh_timewed_switch_btn;

    // thursday time widgets
    @BindView(R.id.bh_timethr_start_btn)
    TextView bh_timethr_start_btn;

    @BindView(R.id.bh_timethr_end_btn)
    TextView bh_timethr_end_btn;

    @BindView(R.id.bh_timethr_switch_btn)
    Switch bh_timethr_switch_btn;

    // friday time widgets
    @BindView(R.id.bh_timefri_start_btn)
    TextView bh_timefri_start_btn;

    @BindView(R.id.bh_timefri_end_btn)
    TextView bh_timefri_end_btn;

    @BindView(R.id.bh_timefri_switch_btn)
    Switch bh_timefri_switch_btn;

    // saturday time widgets
    @BindView(R.id.bh_timesat_start_btn)
    TextView bh_timesat_start_btn;

    @BindView(R.id.bh_timesat_end_btn)
    TextView bh_timesat_end_btn;

    @BindView(R.id.bh_timesat_switch_btn)
    Switch bh_timesat_switch_btn;

    // sunday time widgets
    @BindView(R.id.bh_timesun_start_btn)
    TextView bh_timesun_start_btn;

    @BindView(R.id.bh_timesun_end_btn)
    TextView bh_timesun_end_btn;

    @BindView(R.id.bh_timesun_switch_btn)
    Switch bh_timesun_switch_btn;


    private HttpresponseUpd callback;

    private Snackbar snackbar;

    private Bundle bundle;

    private int pos = 0;

    private Sharedpreferences mPrefs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.business_hour_screen, null);
        ButterKnife.bind(this, Mroot);

        initializeSharedData();
        mPrefs = Sharedpreferences.getUserDataObj(getContext());

        // Mroot = getActivity().getCurrentFocus();
     /*   if (Mroot != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Mroot.getWindowToken(), 0);
        }
*/
        callback = this;

        bundle = getArguments();

        // get current date
        bh_date_start_btn.setText(getcurrentDate());


        bh_date_switch_btn.setOnCheckedChangeListener(this);
        bh_timemon_switch_btn.setOnCheckedChangeListener(this);
        bh_timetue_switch_btn.setOnCheckedChangeListener(this);
        bh_timewed_switch_btn.setOnCheckedChangeListener(this);
        bh_timethr_switch_btn.setOnCheckedChangeListener(this);
        bh_timefri_switch_btn.setOnCheckedChangeListener(this);
        bh_timesat_switch_btn.setOnCheckedChangeListener(this);
        bh_timesun_switch_btn.setOnCheckedChangeListener(this);

        pos = bundle.getInt("pos");

        if (bundle.getString("src").equals("def")) {

            bh_timemon_switch_btn.setEnabled(true);
            bh_timemon_switch_btn.setChecked(true);

            bh_timetue_switch_btn.setEnabled(true);
            bh_timetue_switch_btn.setChecked(true);

            bh_timewed_switch_btn.setEnabled(true);
            bh_timewed_switch_btn.setChecked(true);

            bh_timethr_switch_btn.setEnabled(true);
            bh_timethr_switch_btn.setChecked(true);

            bh_timefri_switch_btn.setEnabled(true);
            bh_timefri_switch_btn.setChecked(true);

            bh_timesat_switch_btn.setEnabled(true);
            bh_timesat_switch_btn.setChecked(true);

            bh_timesun_switch_btn.setEnabled(true);
            bh_timesun_switch_btn.setChecked(true);


        } else {

            // date start
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start().equals("")
                    || LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start().equals("null")) {

            } else {
                bh_date_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start());
            }

            // date end
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end().equals("")
                    || LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end().equals("null")) {

            } else {
                if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start().equals(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end())) {
                    bh_date_switch_btn.setChecked(false);
                } else {
                    bh_date_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end());

                }

            }

            // Mon  Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getMon_start_time().equals("null") || LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getMon_end_time().equals("null")) {
                bh_timemon_switch_btn.setChecked(false);
            } else {
                bh_timemon_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getMon_start_time());
                bh_timemon_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getMon_end_time());

            }

            // Tue  Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getTue_start_time().equals("null") ||
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getTue_end_time().equals("null")) {
                bh_timetue_switch_btn.setChecked(false);

            } else {
                bh_timetue_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getTue_start_time());
                bh_timetue_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getTue_end_time());

            }


            // Wed  Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getWed_start_time().equals("null") ||
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getWed_end_time().equals("null")) {
                bh_timewed_switch_btn.setChecked(false);

            } else {
                bh_timewed_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getWed_start_time());
                bh_timewed_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getWed_end_time());

            }


            // Thr  Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getThr_start_time().equals("null") ||
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getThr_end_time().equals("null")) {
                bh_timethr_switch_btn.setChecked(false);

            } else {
                bh_timethr_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getThr_start_time());
                bh_timethr_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getThr_end_time());

            }


            // Fri start Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getFri_start_time().equals("null") ||
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getFri_end_time().equals("null")) {
                bh_timefri_switch_btn.setChecked(false);

            } else {
                bh_timefri_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getFri_start_time());
                bh_timefri_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getFri_end_time());

            }

            // Sat start Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSat_start_time().equals("null") ||
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSat_end_time().equals("null")) {
                bh_timesat_switch_btn.setChecked(false);
            } else {
                bh_timesat_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSat_start_time());
                bh_timesat_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSat_end_time());

            }

            // Sun start Time
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSun_start_time().equals("null") ||
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSun_end_time().equals("null")) {
                bh_timesun_switch_btn.setChecked(false);

            } else {
                bh_timesun_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSun_start_time());
                bh_timesun_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getSun_end_time());

            }


            if (!bh_date_end_btn.getText().toString().equals("End date") && bh_date_switch_btn.isChecked()) {


                ArrayList<String> day_array = getListDate(bh_date_start_btn.getText().toString(), bh_date_end_btn.getText().toString());
                String day = "";
                boolean mon = false, tue = false, wed = false, thr = false, fri = false, sat = false, sun = false;

                if (day_array.size() == 0) {

                } else {

                    bh_timemon_switch_btn.setEnabled(false);
                    bh_timemon_switch_btn.setChecked(false);

                    bh_timetue_switch_btn.setEnabled(false);
                    bh_timemon_switch_btn.setChecked(false);

                    bh_timewed_switch_btn.setEnabled(false);
                    bh_timetue_switch_btn.setChecked(false);

                    bh_timewed_switch_btn.setEnabled(false);
                    bh_timewed_switch_btn.setChecked(false);

                    bh_timethr_switch_btn.setEnabled(false);
                    bh_timethr_switch_btn.setChecked(false);

                    bh_timefri_switch_btn.setEnabled(false);
                    bh_timefri_switch_btn.setChecked(false);

                    bh_timesat_switch_btn.setEnabled(false);
                    bh_timesat_switch_btn.setChecked(false);

                    bh_timesun_switch_btn.setEnabled(false);
                    bh_timesun_switch_btn.setChecked(false);
                }

                for (int i = 0; i < day_array.size(); i++) {

                    day += day_array.get(i);

                    Log.e("daytwo", day);


                }
                if (day.contains("Mon")) {


                    bh_timemon_switch_btn.setEnabled(true);
                    bh_timemon_switch_btn.setChecked(true);


                }

                if (day.contains("Tue")) {


                    bh_timetue_switch_btn.setEnabled(true);
                    bh_timetue_switch_btn.setChecked(true);


                }
                if (day.contains("Wed")) {


                    bh_timewed_switch_btn.setEnabled(true);
                    bh_timewed_switch_btn.setChecked(true);


                }
                if (day.contains("Thu")) {


                    bh_timethr_switch_btn.setEnabled(true);
                    bh_timethr_switch_btn.setChecked(true);


                }

                if (day.contains("Fri")) {


                    bh_timefri_switch_btn.setEnabled(true);
                    bh_timefri_switch_btn.setChecked(true);


                }
                if (day.contains("Sat")) {


                    bh_timesat_switch_btn.setEnabled(true);
                    bh_timesat_switch_btn.setChecked(true);


                }
                if (day.contains("Sun")) {

                    bh_timesun_switch_btn.setEnabled(true);
                    bh_timesun_switch_btn.setChecked(true);

                }
            }
        }
        return Mroot;
    }


    @OnClick(R.id.bh_next_btn)
    void goNext() {
        // save data in address array

        // date start and End
        if (bh_date_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_start(bh_date_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_end(bh_date_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_start(bh_date_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_end("2019-12-11"/*bh_date_start_btn.getText().toString()*/);
        }

        // Mon start and End Time
        if (bh_timemon_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setMon_start_time(bh_timemon_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setMon_end_time(bh_timemon_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setMon_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setMon_end_time("");
        }

        // Tue start and End Time
        if (bh_timetue_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setTue_start_time(bh_timetue_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setTue_end_time(bh_timetue_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setTue_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setTue_end_time("");
        }

        // Wed start and End Time
        if (bh_timewed_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setWed_start_time(bh_timewed_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setWed_end_time(bh_timewed_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setWed_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setWed_end_time("");
        }

        // thr start and End Time
        if (bh_timethr_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setThr_start_time(bh_timethr_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setThr_end_time(bh_timethr_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setThr_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setThr_end_time("");
        }

        // fri start and End Time
        if (bh_timefri_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setFri_start_time(bh_timefri_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setFri_end_time(bh_timefri_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setFri_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setFri_end_time("");
        }

        // Sat start and End Time
        if (bh_timesat_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setSat_start_time(bh_timesat_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setSat_end_time(bh_timesat_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setSat_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setSat_end_time("");
        }


        // Sun start and End Time
        if (bh_timesun_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setSun_start_time(bh_timesun_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setSun_end_time(bh_timesun_end_btn.getText().toString());
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setSun_start_time("");
            LandingActivity.business_data.getAdderess_data().get(pos).setSun_end_time("");
        }


        // call api to create event id
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "createEvent")
                .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                .appendQueryParameter("industry_id", LandingActivity.business_data.getBusiness_industry_id())
                .appendQueryParameter("publish_id", LandingActivity.business_data.getPublish_id())
                .appendQueryParameter("publish_type", LandingActivity.business_data.getPublish_type())
                .appendQueryParameter("business_name", LandingActivity.business_data.getBusiness_name())
                .appendQueryParameter("business_phone", LandingActivity.business_data.getBusiness_phone())
                .appendQueryParameter("regular_flag", LandingActivity.business_data.getBusiness_phone())

                .appendQueryParameter("address", LandingActivity.business_data.getAdderess_data().get(pos).getFull_address())
                .appendQueryParameter("address_id", LandingActivity.business_data.getAdderess_data().get(pos).getAddress_id())

                .appendQueryParameter("start_date", LandingActivity.business_data.getAdderess_data().get(pos).getDate_start())
                .appendQueryParameter("end_date", LandingActivity.business_data.getAdderess_data().get(pos).getDate_end())

                .appendQueryParameter("mon_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getMon_start_time())
                .appendQueryParameter("mon_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getMon_end_time())

                .appendQueryParameter("tue_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getTue_start_time())
                .appendQueryParameter("tue_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getTue_end_time())

                .appendQueryParameter("wed_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getWed_start_time())
                .appendQueryParameter("wed_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getWed_end_time())

                .appendQueryParameter("thru_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getThr_start_time())
                .appendQueryParameter("thru_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getThr_end_time())

                .appendQueryParameter("fri_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getFri_start_time())
                .appendQueryParameter("fri_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getFri_end_time())


                .appendQueryParameter("sat_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getSat_start_time())
                .appendQueryParameter("sat_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getSat_end_time())

                .appendQueryParameter("sun_from_time", LandingActivity.business_data.getAdderess_data().get(pos).getSun_start_time())
                .appendQueryParameter("sun_to_time", LandingActivity.business_data.getAdderess_data().get(pos).getSun_end_time());


        Log.e("url", builder.build().toString());


        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    @OnClick(R.id.bh_back_btn)
    void goBack() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.bh_date_start_btn)
    void getstartDate() {
        getDateWithcallback(bh_date_start_btn, callback);
    }

    @OnClick(R.id.bh_date_end_btn)
    void getendDate() {
        if (bh_date_switch_btn.isChecked()) {
            getDateWithcallback(bh_date_end_btn, callback);
        } else {

        }

    }

    // mon time click listener
    @OnClick(R.id.bh_timemon_start_btn)
    void getmonStartTime() {
        getTime(bh_timemon_start_btn, bh_timemon_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timemon_end_btn)
    void getmonEndTime() {
        getTimeback(bh_timemon_end_btn, bh_timemon_start_btn.getText().toString());
    }

    // tue time click listener
    @OnClick(R.id.bh_timetue_start_btn)
    void gettueStartTime() {
        getTime(bh_timetue_start_btn, bh_timetue_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timetue_end_btn)
    void gettueEndTime() {
        getTimeback(bh_timetue_end_btn, bh_timetue_start_btn.getText().toString());
    }

    // wed time click listener
    @OnClick(R.id.bh_timewed_start_btn)
    void getwedStartTime() {
        getTime(bh_timewed_start_btn, bh_timewed_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timewed_end_btn)
    void getwedEndTime() {
        getTimeback(bh_timewed_end_btn, bh_timewed_start_btn.getText().toString());
    }

    // thr time click listener
    @OnClick(R.id.bh_timethr_start_btn)
    void getthrStartTime() {
        getTime(bh_timethr_start_btn, bh_timethr_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timethr_end_btn)
    void getthrEndTime() {
        getTimeback(bh_timethr_end_btn, bh_timethr_start_btn.getText().toString());
    }

    // fri time click listener
    @OnClick(R.id.bh_timefri_start_btn)
    void getfriStartTime() {
        getTime(bh_timefri_start_btn, bh_timefri_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timefri_end_btn)
    void getfriEndTime() {
        getTimeback(bh_timefri_end_btn, bh_timefri_start_btn.getText().toString());
    }

    // sat time click listener
    @OnClick(R.id.bh_timesat_start_btn)
    void getsatStartTime() {
        getTime(bh_timesat_start_btn, bh_timesat_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timesat_end_btn)
    void getsatEndTime() {
        getTimeback(bh_timesat_end_btn, bh_timesat_start_btn.getText().toString());
    }

    // sun time click listener
    @OnClick(R.id.bh_timesun_start_btn)
    void getsunStartTime() {
        getTime(bh_timesun_start_btn, bh_timesun_end_btn.getText().toString());
    }

    @OnClick(R.id.bh_timesun_end_btn)
    void getsunEndTime() {
        getTimeback(bh_timesun_end_btn, bh_timesun_start_btn.getText().toString());
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (compoundButton.getId() == R.id.bh_date_switch_btn) {

            if (b) {
                bh_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

                callback.getResponse("Date");


            } else {
                bh_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                bh_timemon_switch_btn.setEnabled(true);
                bh_timemon_switch_btn.setChecked(true);

                bh_timetue_switch_btn.setEnabled(true);
                bh_timetue_switch_btn.setChecked(true);

                bh_timewed_switch_btn.setEnabled(true);
                bh_timewed_switch_btn.setChecked(true);

                bh_timethr_switch_btn.setEnabled(true);
                bh_timethr_switch_btn.setChecked(true);

                bh_timefri_switch_btn.setEnabled(true);
                bh_timefri_switch_btn.setChecked(true);

                bh_timesat_switch_btn.setEnabled(true);
                bh_timesat_switch_btn.setChecked(true);

                bh_timesun_switch_btn.setEnabled(true);
                bh_timesun_switch_btn.setChecked(true);
            }
        } else if (compoundButton.getId() == R.id.bh_timesun_switch_btn) {
            if (b) {
                bh_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                bh_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.bh_timemon_switch_btn) {
            if (b) {
                bh_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                bh_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.bh_timetue_switch_btn) {
            if (b) {
                bh_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                bh_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.bh_timewed_switch_btn) {
            if (b) {
                bh_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                bh_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.bh_timethr_switch_btn) {
            if (b) {
                bh_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                bh_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.bh_timefri_switch_btn) {
            if (b) {
                bh_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                bh_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.bh_timesat_switch_btn) {
            if (b) {

                bh_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                bh_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {

                bh_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                bh_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));

            }
        }

    }


    @Override
    public void getResponse(String response) {
        Log.e("res", response);

        if (response.equals("Date")) {

            if (!bh_date_end_btn.getText().toString().equals("End date") && bh_date_switch_btn.isChecked()) {

                ArrayList<String> day_array = getListDate(bh_date_start_btn.getText().toString(), bh_date_end_btn.getText().toString());
                String day = "";
                boolean mon = false, tue = false, wed = false, thr = false, fri = false, sat = false, sun = false;

                if (day_array.size() == 0) {

                } else {

                    bh_timemon_switch_btn.setEnabled(false);
                    bh_timemon_switch_btn.setChecked(false);

                    bh_timetue_switch_btn.setEnabled(false);
                    bh_timemon_switch_btn.setChecked(false);

                    bh_timewed_switch_btn.setEnabled(false);
                    bh_timetue_switch_btn.setChecked(false);

                    bh_timewed_switch_btn.setEnabled(false);
                    bh_timewed_switch_btn.setChecked(false);

                    bh_timethr_switch_btn.setEnabled(false);
                    bh_timethr_switch_btn.setChecked(false);

                    bh_timefri_switch_btn.setEnabled(false);
                    bh_timefri_switch_btn.setChecked(false);

                    bh_timesat_switch_btn.setEnabled(false);
                    bh_timesat_switch_btn.setChecked(false);

                    bh_timesun_switch_btn.setEnabled(false);
                    bh_timesun_switch_btn.setChecked(false);
                }

                for (int i = 0; i < day_array.size(); i++) {

                    day += day_array.get(i);

                    Log.e("daytwo", day);


                }
                if (day.contains("Mon")) {


                    bh_timemon_switch_btn.setEnabled(true);
                    bh_timemon_switch_btn.setChecked(true);


                }

                if (day.contains("Tue")) {


                    bh_timetue_switch_btn.setEnabled(true);
                    bh_timetue_switch_btn.setChecked(true);


                }
                if (day.contains("Wed")) {


                    bh_timewed_switch_btn.setEnabled(true);
                    bh_timewed_switch_btn.setChecked(true);


                }
                if (day.contains("Thu")) {


                    bh_timethr_switch_btn.setEnabled(true);
                    bh_timethr_switch_btn.setChecked(true);


                }

                if (day.contains("Fri")) {


                    bh_timefri_switch_btn.setEnabled(true);
                    bh_timefri_switch_btn.setChecked(true);


                }
                if (day.contains("Sat")) {


                    bh_timesat_switch_btn.setEnabled(true);
                    bh_timesat_switch_btn.setChecked(true);


                }
                if (day.contains("Sun")) {


                    bh_timesun_switch_btn.setEnabled(true);
                    bh_timesun_switch_btn.setChecked(true);


                }

            } else {

                bh_timemon_switch_btn.setEnabled(false);
                bh_timemon_switch_btn.setChecked(false);

                bh_timetue_switch_btn.setEnabled(false);
                bh_timemon_switch_btn.setChecked(false);

                bh_timewed_switch_btn.setEnabled(false);
                bh_timetue_switch_btn.setChecked(false);

                bh_timewed_switch_btn.setEnabled(false);
                bh_timewed_switch_btn.setChecked(false);

                bh_timethr_switch_btn.setEnabled(false);
                bh_timethr_switch_btn.setChecked(false);

                bh_timefri_switch_btn.setEnabled(false);
                bh_timefri_switch_btn.setChecked(false);

                bh_timesat_switch_btn.setEnabled(false);
                bh_timesat_switch_btn.setChecked(false);

                bh_timesun_switch_btn.setEnabled(false);
                bh_timesun_switch_btn.setChecked(false);


                Date date = new Date();
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = date_format.parse(bh_date_start_btn.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);// Note that Month value is 0-based. e.g., 0 for January.

                int reslut = calendar.get(Calendar.DAY_OF_WEEK);

                Log.e("day", reslut + "");


                switch (reslut) {

                    case Calendar.MONDAY:
                        bh_timemon_switch_btn.setEnabled(true);
                        bh_timemon_switch_btn.setChecked(true);

                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timetue_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timewed_switch_btn.setChecked(false);

                        bh_timethr_switch_btn.setEnabled(false);
                        bh_timethr_switch_btn.setChecked(false);

                        bh_timefri_switch_btn.setEnabled(false);
                        bh_timefri_switch_btn.setChecked(false);

                        bh_timesat_switch_btn.setEnabled(false);
                        bh_timesat_switch_btn.setChecked(false);

                        bh_timesun_switch_btn.setEnabled(false);
                        bh_timesun_switch_btn.setChecked(false);
                        break;

                    case Calendar.TUESDAY:
                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timemon_switch_btn.setChecked(false);

                        bh_timetue_switch_btn.setEnabled(true);
                        bh_timetue_switch_btn.setChecked(true);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timewed_switch_btn.setChecked(false);

                        bh_timethr_switch_btn.setEnabled(false);
                        bh_timethr_switch_btn.setChecked(false);

                        bh_timefri_switch_btn.setEnabled(false);
                        bh_timefri_switch_btn.setChecked(false);

                        bh_timesat_switch_btn.setEnabled(false);
                        bh_timesat_switch_btn.setChecked(false);

                        bh_timesun_switch_btn.setEnabled(false);
                        bh_timesun_switch_btn.setChecked(false);
                        break;

                    case Calendar.WEDNESDAY:
                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timemon_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timetue_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(true);
                        bh_timewed_switch_btn.setChecked(true);

                        bh_timethr_switch_btn.setEnabled(false);
                        bh_timethr_switch_btn.setChecked(false);

                        bh_timefri_switch_btn.setEnabled(false);
                        bh_timefri_switch_btn.setChecked(false);

                        bh_timesat_switch_btn.setEnabled(false);
                        bh_timesat_switch_btn.setChecked(false);

                        bh_timesun_switch_btn.setEnabled(false);
                        bh_timesun_switch_btn.setChecked(false);
                        break;
                    case Calendar.THURSDAY:
                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timemon_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timetue_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timewed_switch_btn.setChecked(false);

                        bh_timethr_switch_btn.setEnabled(true);
                        bh_timethr_switch_btn.setChecked(true);

                        bh_timefri_switch_btn.setEnabled(false);
                        bh_timefri_switch_btn.setChecked(false);

                        bh_timesat_switch_btn.setEnabled(false);
                        bh_timesat_switch_btn.setChecked(false);

                        bh_timesun_switch_btn.setEnabled(false);
                        bh_timesun_switch_btn.setChecked(false);
                        break;
                    case Calendar.FRIDAY:
                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timemon_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timetue_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timewed_switch_btn.setChecked(false);

                        bh_timethr_switch_btn.setEnabled(false);
                        bh_timethr_switch_btn.setChecked(false);

                        bh_timefri_switch_btn.setEnabled(true);
                        bh_timefri_switch_btn.setChecked(true);

                        bh_timesat_switch_btn.setEnabled(false);
                        bh_timesat_switch_btn.setChecked(false);

                        bh_timesun_switch_btn.setEnabled(false);
                        bh_timesun_switch_btn.setChecked(false);
                        break;

                    case Calendar.SATURDAY:
                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timemon_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timetue_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timewed_switch_btn.setChecked(false);

                        bh_timethr_switch_btn.setEnabled(false);
                        bh_timethr_switch_btn.setChecked(false);

                        bh_timefri_switch_btn.setEnabled(false);
                        bh_timefri_switch_btn.setChecked(false);

                        bh_timesat_switch_btn.setEnabled(true);
                        bh_timesat_switch_btn.setChecked(true);

                        bh_timesun_switch_btn.setEnabled(false);
                        bh_timesun_switch_btn.setChecked(false);
                        break;
                    case Calendar.SUNDAY:
                        bh_timetue_switch_btn.setEnabled(false);
                        bh_timemon_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timetue_switch_btn.setChecked(false);

                        bh_timewed_switch_btn.setEnabled(false);
                        bh_timewed_switch_btn.setChecked(false);

                        bh_timethr_switch_btn.setEnabled(false);
                        bh_timethr_switch_btn.setChecked(false);

                        bh_timefri_switch_btn.setEnabled(false);
                        bh_timefri_switch_btn.setChecked(false);

                        bh_timesat_switch_btn.setEnabled(false);
                        bh_timesat_switch_btn.setChecked(false);

                        bh_timesun_switch_btn.setEnabled(true);
                        bh_timesun_switch_btn.setChecked(true);
                        break;
                }
            }
        } else if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("business_id").equals("0")) {
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    // save data

                    mPrefs.setAddressId(obj.getString("address_id"));
                    mPrefs.setBusnessId(obj.getString("business_id"));
                    LandingActivity.business_data.setBusiness_id(obj.getString("business_id"));
                    Log.d("BusinessHourFrag", "business_id" + obj.getString("business_id"));

                    LandingActivity.business_data.getAdderess_data().get(pos).setAddress_id(obj.getString("address_id"));

                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();

                    LandingActivity.staff = false;
                    LandingActivity.res = false;
                    Bundle _bundle = new Bundle();

                    if (bundle.getString("src").equals("def")) {
                        _bundle.putString("src", "def");
                    } else {
                        _bundle.putString("src", "create");
                    }

                    _bundle.putInt("create_pos", pos);
                    _bundle.putInt("business_id", Integer.parseInt(obj.getString("business_id")));
                    _bundle.putInt("address_id", Integer.parseInt(obj.getString("address_id")));

                    replaceFrag(new AddStaffFrag(), _bundle, BusinessDetailsFrag.class.getName());
                }

            } catch (JSONException e) {
                // snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                //     snackbar.show();
            }


        }
    }
}
