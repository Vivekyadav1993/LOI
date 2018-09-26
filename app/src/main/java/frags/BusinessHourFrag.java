package frags;

import android.location.Geocoder;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

/**
 * Created by vivek on 10/01/2018.
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

    String start_date, end_date, mon_from_time, mon_to_time, tue_from_time, tue_to_time, wed_from_time, wed_to_time,
            thru_from_time, thru_to_time, fri_from_time, fri_to_time, sat_from_time, sat_to_time, sun_from_time, sun_to_time;


    Geocoder geocoder = null;
    private String industry_id, address_id_;
    private Uri.Builder builder;
    private String post_tag, business_name, phone, address,business_id,address_id,industry_name ;


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
        geocoder = new Geocoder(getActivity());
        bundle = getArguments();
        industry_id = bundle.getString("indusrty_id");
        address_id = bundle.getString("address_id_");
        industry_name= bundle.getString("indusrty_name");
        mPrefs.setIndustryType(industry_name);
        if(mPrefs.getBusnessId().equalsIgnoreCase("null")){
            business_id="";
        }else {
            business_id= mPrefs.getBusnessId();
        }
        Log.d("BHF", "address_id" + address_id);
        try {
            if (address_id==null) {
                address_id_="";
            } else {
                address_id_=address_id;
                hitBusinessHourApi(business_id, address_id);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        Log.d("BHF","business_id"+mPrefs.getBusnessId());


        address = bundle.getString("address");
        phone = bundle.getString("bussiness_phone");
        business_name = bundle.getString("bussiness_name");
        Log.d("BHF", "address_id_1" + address_id_);
        // get current date
        bh_date_start_btn.setText(getcurrentDate());

        //  bh_date_switch_btn.setOnCheckedChangeListener(this);
        bh_timemon_switch_btn.setOnCheckedChangeListener(this);
        bh_timetue_switch_btn.setOnCheckedChangeListener(this);
        bh_timewed_switch_btn.setOnCheckedChangeListener(this);
        bh_timethr_switch_btn.setOnCheckedChangeListener(this);
        bh_timefri_switch_btn.setOnCheckedChangeListener(this);
        bh_timesat_switch_btn.setOnCheckedChangeListener(this);
        bh_timesun_switch_btn.setOnCheckedChangeListener(this);

        pos = bundle.getInt("pos");


    /*    if (bundle.getString("src").equals("def")) {*/

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




       /* } else {

            // date start
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start().equals("")
                    || LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start().equals("null")) {

            } else {
                bh_date_start_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start());
            }

            // date end
            if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end().equals("")
                    || LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end().equals("null")) {

            } *//*else {
                if (LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_start().equals(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end())) {
                    bh_date_switch_btn.setChecked(false);
                } else {
                    bh_date_end_btn.setText(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("pos")).getDate_end());

                }

            }*//*

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


            if (bh_date_end_btn.getText().toString().equals("")*//* && bh_date_switch_btn.isChecked()*//*) {


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
        }*/
        return Mroot;
    }

    private void hitBusinessHourApi(String busnessId, String address_id_1) {

        builder = new Uri.Builder();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "bookingDetails")
                .appendQueryParameter("business_id", busnessId)
                .appendQueryParameter("address_id", address_id_1);

        Log.d("url","hour_url"+builder);
        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "businessdetails";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            try {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @OnClick(R.id.bh_next_btn)
    void goNext() {
        // save data in address array
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String date = year + "-" + (month + 1) + "-" + day;

      //  Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
        // date start and End
      /*  if (bh_date_switch_btn.isChecked()) {
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_start(bh_date_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_end(date);
        } else {
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_start(bh_date_start_btn.getText().toString());
            LandingActivity.business_data.getAdderess_data().get(pos).setDate_end("2019-12-11"*//*bh_date_start_btn.getText().toString()*//*);
        }*/

        String mon_start_time, mon_end_time, tue_start_time, tue_end_time, wed_start_time, wed_end_time, thru_start_time, thru_end_time, fri_start_time, fri_end_time;
        String sat_start_time, sat_end_time, sun_start_time, sun_to_time;
        // Mon start and End Time
        if (bh_timemon_switch_btn.isChecked()) {
            mon_start_time = bh_timemon_start_btn.getText().toString();
            mon_end_time = bh_timemon_end_btn.getText().toString();
        } else {
            mon_start_time = "";
            mon_end_time = "";
        }

        // Tue start and End Time
        if (bh_timetue_switch_btn.isChecked()) {
            tue_start_time = bh_timetue_start_btn.getText().toString();
            tue_end_time = bh_timetue_end_btn.getText().toString();
        } else {
            tue_start_time = "";
            tue_end_time = "";
        }

        // Wed start and End Time
        if (bh_timewed_switch_btn.isChecked()) {
            wed_start_time = bh_timewed_start_btn.getText().toString();
            wed_end_time = bh_timewed_end_btn.getText().toString();
        } else {
            wed_start_time = "";
            wed_end_time = "";
        }

        // thr start and End Time
        if (bh_timethr_switch_btn.isChecked()) {
            thru_start_time = bh_timethr_start_btn.getText().toString();
            thru_end_time = bh_timethr_end_btn.getText().toString();
        } else {
            thru_start_time = "";
            thru_end_time = "";
        }

        // fri start and End Time
        if (bh_timefri_switch_btn.isChecked()) {
            fri_start_time = bh_timefri_start_btn.getText().toString();
            fri_end_time = bh_timefri_end_btn.getText().toString();
        } else {
            fri_start_time = "";
            fri_end_time = "";
        }

        // Sat start and End Time
        if (bh_timesat_switch_btn.isChecked()) {
            sat_start_time = bh_timesat_start_btn.getText().toString();
            sat_end_time = bh_timesat_end_btn.getText().toString();
        } else {
            sat_start_time = "";
            sat_end_time = "";
        }


        // Sun start and End Time
        if (bh_timesun_switch_btn.isChecked()) {
            sun_start_time = bh_timesun_start_btn.getText().toString();
            sun_to_time = bh_timesun_end_btn.getText().toString();
        } else {
            sun_start_time = "";
            sun_to_time = "";
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
                .appendQueryParameter("industry_id", industry_id)
                .appendQueryParameter("publish_id", "0")
                .appendQueryParameter("publish_type", "Public")
                .appendQueryParameter("business_name", business_name)
                .appendQueryParameter("business_phone", phone)
                .appendQueryParameter("regular_flag", "")

                .appendQueryParameter("address", address)
                .appendQueryParameter("address_id", address_id_)
                .appendQueryParameter("start_date", bh_date_start_btn.getText().toString())
                .appendQueryParameter("end_date", bh_date_end_btn.getText().toString())
                .appendQueryParameter("mon_from_time", mon_start_time)
                .appendQueryParameter("mon_to_time", mon_end_time)

                .appendQueryParameter("tue_from_time", tue_start_time)
                .appendQueryParameter("tue_to_time", tue_end_time)

                .appendQueryParameter("wed_from_time", wed_start_time)
                .appendQueryParameter("wed_to_time", wed_end_time)

                .appendQueryParameter("thru_from_time", thru_start_time)
                .appendQueryParameter("thru_to_time", thru_end_time)

                .appendQueryParameter("fri_from_time", fri_start_time)
                .appendQueryParameter("fri_to_time", fri_end_time)


                .appendQueryParameter("sat_from_time", sat_start_time)
                .appendQueryParameter("sat_to_time", sat_end_time)

                .appendQueryParameter("sun_from_time", sun_start_time)
                .appendQueryParameter("sun_to_time", sun_to_time)
                .appendQueryParameter("lat", mPrefs.getBusinessLat())
                .appendQueryParameter("longi", mPrefs.getBusinessLong())
                .appendQueryParameter("business_id", business_id)
        ;


        Log.e("url", builder.build().toString());


        if (AppUtils.isNetworkAvailable(getActivity())) {

            post_tag = "create_business";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
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
        /*if (bh_date_switch_btn.isChecked()) {*/
        getDateWithcallback(bh_date_end_btn, callback);
        /*} else {

        }*/

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

            if (!bh_date_end_btn.getText().toString().equals("") /*&& bh_date_switch_btn.isChecked()*/) {

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

        } else if (post_tag.equalsIgnoreCase("businessdetails")) {

            Log.e("res1", response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("business");
                JSONObject address_object = ja.getJSONObject(0);

                start_date = address_object.getString("start_date");
                end_date = address_object.getString("end_date");
                mon_from_time = address_object.getString("mon_from_time");
                mon_to_time = address_object.getString("mon_to_time");
                tue_from_time = address_object.getString("tue_from_time");
                tue_to_time = address_object.getString("tue_to_time");
                wed_from_time = address_object.getString("wed_from_time");
                wed_to_time = address_object.getString("wed_to_time");
                thru_from_time = address_object.getString("thru_from_time");
                thru_to_time = address_object.getString("thru_to_time");
                fri_from_time = address_object.getString("fri_from_time");
                fri_to_time = address_object.getString("fri_to_time");
                sat_from_time = address_object.getString("sat_from_time");
                sat_to_time = address_object.getString("sat_to_time");
                sun_from_time = address_object.getString("sun_from_time");
                sun_to_time = address_object.getString("sun_to_time");


                bh_date_start_btn.setText(start_date);
                bh_date_end_btn.setText(end_date);
                if (mon_from_time.equalsIgnoreCase("null")) {
                    bh_timemon_switch_btn.setEnabled(false);
                    bh_timemon_switch_btn.setChecked(false);
                    bh_timemon_start_btn.setText("08:00 AM");
                    bh_timemon_end_btn.setText("05:00 PM");
                } else {
                    bh_timemon_switch_btn.setEnabled(true);
                    bh_timemon_switch_btn.setChecked(true);
                    bh_timemon_start_btn.setText(mon_from_time);
                    bh_timemon_end_btn.setText(mon_to_time);
                }
                if (tue_from_time.equalsIgnoreCase("null")) {
                    bh_timetue_switch_btn.setEnabled(false);
                    bh_timetue_switch_btn.setChecked(false);
                    bh_timetue_start_btn.setText("08:00 AM");
                    bh_timetue_end_btn.setText("05:00 PM");
                } else {
                    bh_timetue_switch_btn.setEnabled(true);
                    bh_timetue_switch_btn.setChecked(true);
                    bh_timetue_start_btn.setText(tue_from_time);
                    bh_timetue_end_btn.setText(tue_to_time);

                }
                if (wed_from_time.equalsIgnoreCase("null")) {
                    bh_timewed_switch_btn.setEnabled(false);
                    bh_timewed_switch_btn.setChecked(false);
                    bh_timewed_start_btn.setText("08:00 AM");
                    bh_timewed_end_btn.setText("05:00 PM");

                } else {
                    bh_timewed_switch_btn.setEnabled(true);
                    bh_timewed_switch_btn.setChecked(true);
                    bh_timewed_start_btn.setText(wed_from_time);
                    bh_timewed_end_btn.setText(wed_to_time);

                }
                if (thru_from_time.equalsIgnoreCase("null")) {
                    bh_timethr_switch_btn.setEnabled(false);
                    bh_timethr_switch_btn.setChecked(false);
                    bh_timethr_start_btn.setText("08:00 AM");
                    bh_timethr_end_btn.setText("05:00 PM");

                } else {
                    bh_timethr_switch_btn.setEnabled(true);
                    bh_timethr_switch_btn.setChecked(true);
                    bh_timethr_start_btn.setText(thru_from_time);
                    bh_timethr_end_btn.setText(thru_to_time);

                }
                if (fri_from_time.equalsIgnoreCase("null")) {
                    bh_timefri_switch_btn.setEnabled(false);
                    bh_timefri_switch_btn.setChecked(false);
                    bh_timefri_start_btn.setText("08:00 AM");
                    bh_timefri_end_btn.setText("05:00 PM");

                } else {
                    bh_timefri_switch_btn.setEnabled(true);
                    bh_timefri_switch_btn.setChecked(true);
                    bh_timefri_start_btn.setText(fri_from_time);
                    bh_timefri_end_btn.setText(fri_to_time);

                }
                if (sat_from_time.equalsIgnoreCase("null")) {
                    bh_timesat_switch_btn.setEnabled(false);
                    bh_timesat_switch_btn.setChecked(false);
                    bh_timesat_start_btn.setText("08:00 AM");
                    bh_timesat_end_btn.setText("05:00 PM");

                } else {
                    bh_timesat_switch_btn.setEnabled(true);
                    bh_timesat_switch_btn.setChecked(true);
                    bh_timesat_start_btn.setText(sat_from_time);
                    bh_timesat_end_btn.setText(sat_to_time);

                }
                if (sun_from_time.equalsIgnoreCase("null")) {

                    bh_timesun_start_btn.setText("08:00 AM");
                    bh_timesun_end_btn.setText("05:00 PM");
                    bh_timesun_switch_btn.setEnabled(false);
                    bh_timesun_switch_btn.setChecked(false);

                } else {
                    bh_timesun_start_btn.setText(sun_from_time);
                    bh_timesun_end_btn.setText(sun_to_time);
                    bh_timesun_switch_btn.setEnabled(true);
                    bh_timesun_switch_btn.setChecked(true);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (post_tag.equalsIgnoreCase("create_business")) {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                // save data

                mPrefs.setAddressId(obj.getString("address_id"));
                mPrefs.setBusnessId(obj.getString("business_id"));
                mPrefs.setBusnessName(obj.getString("business_name"));

                /*    LandingActivity.business_data.setBusiness_id(obj.getString("business_id"));
                    Log.d("BusinessHourFrag", "business_id" + obj.getString("business_id"));

                    LandingActivity.business_data.getAdderess_data().get(pos).setAddress_id(obj.getString("address_id"));
*/
                Bundle _bundle = new Bundle();
                _bundle.putInt("create_pos", pos);
                _bundle.putString("business_id", obj.getString("business_id"));
                _bundle.putString("address_id", obj.getString("address_id"));

                replaceFrag(new AddStaffFrag(), _bundle, BusinessDetailsFrag.class.getName());

                snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();


             /*       LandingActivity.staff = false;
                    LandingActivity.res = false;
            */

                /*    if (bundle.getString("src").equals("def")) {
                        _bundle.putString("src", "def");
                    } else {
                        _bundle.putString("src", "create");
                    }
*/


            } catch (JSONException e) {
                // snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                //     snackbar.show();
            }


        }
    }
}
