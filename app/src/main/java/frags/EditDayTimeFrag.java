package frags;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 */
public class EditDayTimeFrag extends HelperFrags implements CompoundButton.OnCheckedChangeListener, HttpresponseUpd {

    private View Mroot;


    // select date widgets
    @BindView(R.id.frag_day_time_date_start_btn)
    TextView frag_day_time_date_start_btn;

    @BindView(R.id.frag_day_time_date_end_btn)
    TextView frag_day_time_date_end_btn;

    @BindView(R.id.frag_day_time_date_switch_btn)
    Switch frag_day_time_date_switch_btn;

    // monday time widgets
    @BindView(R.id.frag_day_time_timemon_start_btn)
    TextView frag_day_time_timemon_start_btn;

    @BindView(R.id.frag_day_time_timemon_end_btn)
    TextView frag_day_time_timemon_end_btn;

    @BindView(R.id.frag_day_time_timemon_switch_btn)
    Switch frag_day_time_timemon_switch_btn;

    // tuesday time widgets
    @BindView(R.id.frag_day_time_timetue_start_btn)
    TextView frag_day_time_timetue_start_btn;

    @BindView(R.id.frag_day_time_timetue_end_btn)
    TextView frag_day_time_timetue_end_btn;

    @BindView(R.id.frag_day_time_timetue_switch_btn)
    Switch frag_day_time_timetue_switch_btn;

    // wednesday time widgets
    @BindView(R.id.frag_day_time_timewed_start_btn)
    TextView frag_day_time_timewed_start_btn;

    @BindView(R.id.frag_day_time_timewed_end_btn)
    TextView frag_day_time_timewed_end_btn;

    @BindView(R.id.frag_day_time_timewed_switch_btn)
    Switch frag_day_time_timewed_switch_btn;

    // thursday time widgets
    @BindView(R.id.frag_day_time_timethr_start_btn)
    TextView frag_day_time_timethr_start_btn;

    @BindView(R.id.frag_day_time_timethr_end_btn)
    TextView frag_day_time_timethr_end_btn;

    @BindView(R.id.frag_day_time_timethr_switch_btn)
    Switch frag_day_time_timethr_switch_btn;

    // friday time widgets
    @BindView(R.id.frag_day_time_timefri_start_btn)
    TextView frag_day_time_timefri_start_btn;

    @BindView(R.id.frag_day_time_timefri_end_btn)
    TextView frag_day_time_timefri_end_btn;

    @BindView(R.id.frag_day_time_timefri_switch_btn)
    Switch frag_day_time_timefri_switch_btn;

    // saturday time widgets
    @BindView(R.id.frag_day_time_timesat_start_btn)
    TextView frag_day_time_timesat_start_btn;

    @BindView(R.id.frag_day_time_timesat_end_btn)
    TextView frag_day_time_timesat_end_btn;

    @BindView(R.id.frag_day_time_timesat_switch_btn)
    Switch frag_day_time_timesat_switch_btn;

    // sunday time widgets
    @BindView(R.id.frag_day_time_timesun_start_btn)
    TextView frag_day_time_timesun_start_btn;

    @BindView(R.id.frag_day_time_timesun_end_btn)
    TextView frag_day_time_timesun_end_btn;

    @BindView(R.id.frag_day_time_timesun_switch_btn)
    Switch frag_day_time_timesun_switch_btn;


    private HttpresponseUpd callback;

    private Snackbar snackbar;

    private Bundle bundle;

    private int pos = 0;

    private Sharedpreferences mPrefs;

    private String Mon, Tue, Wed, Thru, Fri, Sat, Sun, MonTo, TueTo, WedTo, ThruTo, FriTo, SatTo, SunTo, EndDate, StartDate,
            NewStartDate, NewEndDate, mMon, mMonTo, mTue, mTueTo, mWed, mWedTo,mThru,mThruTo,mFri,mFriTo,mSat,mSatTo,mSun,mSunTo;


    public EditDayTimeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Mroot = inflater.inflate(R.layout.fragment_edit_day_time, null);
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

        Mon = bundle.getString("Mon");
        MonTo = bundle.getString("MonTo");
        Tue = bundle.getString("Tue");
        TueTo = bundle.getString("TueTo");
        Wed = bundle.getString("Wed");
        WedTo = bundle.getString("WedTo");
        Thru = bundle.getString("Thru");
        ThruTo = bundle.getString("ThruTo");
        Fri = bundle.getString("Fri");
        FriTo = bundle.getString("FriTo");
        Sat = bundle.getString("Sat");
        SatTo = bundle.getString("SatTo");
        Sun = bundle.getString("Sun");
        SunTo = bundle.getString("SunTo");
        EndDate = bundle.getString("EndDate");
        StartDate = bundle.getString("StartDate");
        // get current date


        frag_day_time_date_start_btn.setText(StartDate);
        frag_day_time_date_end_btn.setText(EndDate);
        frag_day_time_timemon_start_btn.setText(Mon);
        frag_day_time_timemon_end_btn.setText(MonTo);
        frag_day_time_timetue_start_btn.setText(Tue);
        frag_day_time_timetue_end_btn.setText(TueTo);
        frag_day_time_timewed_start_btn.setText(Wed);
        frag_day_time_timewed_end_btn.setText(WedTo);
        frag_day_time_timethr_start_btn.setText(Thru);
        frag_day_time_timethr_end_btn.setText(ThruTo);
        frag_day_time_timefri_start_btn.setText(Fri);
        frag_day_time_timefri_end_btn.setText(FriTo);
        frag_day_time_timesat_start_btn.setText(Sat);
        frag_day_time_timesat_end_btn.setText(SatTo);
        frag_day_time_timesun_start_btn.setText(Sun);
        frag_day_time_timesun_end_btn.setText(SunTo);









        frag_day_time_date_start_btn.setText(getcurrentDate());
        Log.d("ADEF","MonTo"+MonTo+"TueTo"+TueTo+"WedTo"+WedTo+"ThruTo"+ThruTo+"FriTo"+FriTo+"SatTo"+SatTo+"SunTo"+SunTo);


        frag_day_time_date_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timemon_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timetue_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timewed_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timethr_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timefri_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timesat_switch_btn.setOnCheckedChangeListener(this);
        frag_day_time_timesun_switch_btn.setOnCheckedChangeListener(this);


        if (!Mon.equalsIgnoreCase("") && !Tue.equalsIgnoreCase("") && !Wed.equalsIgnoreCase("") && !Thru.equalsIgnoreCase("")
                && !Fri.equalsIgnoreCase("") && !Sat.equalsIgnoreCase("") && !Sun.equalsIgnoreCase("")) {

            frag_day_time_timemon_switch_btn.setEnabled(true);
            frag_day_time_timemon_switch_btn.setChecked(true);

            frag_day_time_timetue_switch_btn.setEnabled(true);
            frag_day_time_timetue_switch_btn.setChecked(true);

            frag_day_time_timewed_switch_btn.setEnabled(true);
            frag_day_time_timewed_switch_btn.setChecked(true);

            frag_day_time_timethr_switch_btn.setEnabled(true);
            frag_day_time_timethr_switch_btn.setChecked(true);

            frag_day_time_timefri_switch_btn.setEnabled(true);
            frag_day_time_timefri_switch_btn.setChecked(true);

            frag_day_time_timesat_switch_btn.setEnabled(true);
            frag_day_time_timesat_switch_btn.setChecked(true);

            frag_day_time_timesun_switch_btn.setEnabled(true);
            frag_day_time_timesun_switch_btn.setChecked(true);


        } else {

            // date start
            if (StartDate.equals("") || StartDate.equals("null")) {

            } else {
                frag_day_time_date_start_btn.setText(StartDate);
            }

            // date end
            if (EndDate.equals("") || EndDate.equals("null")) {

            } else {
                frag_day_time_date_end_btn.setText(EndDate);

            }

            // Mon  Time
            if (Mon.equals("") || Mon.equals("")) {
                frag_day_time_timemon_switch_btn.setChecked(false);
            } else {
                frag_day_time_timemon_start_btn.setText(Mon);
                frag_day_time_timemon_end_btn.setText(MonTo);

            }

            // Tue  Time
            if (Tue.equals("") || Tue.equals("null")) {
                frag_day_time_timetue_switch_btn.setChecked(false);

            } else {
                frag_day_time_timetue_start_btn.setText(Tue);
                frag_day_time_timetue_end_btn.setText(TueTo);

            }


            // Wed  Time
            if (Wed.equals("") || WedTo.equals("")) {
                frag_day_time_timewed_switch_btn.setChecked(false);

            } else {
                frag_day_time_timewed_start_btn.setText(Wed);
                frag_day_time_timewed_end_btn.setText(WedTo);

            }


            // Thr  Time
            if (Thru.equals("") || ThruTo.equals("")) {
                frag_day_time_timethr_switch_btn.setChecked(false);

            } else {
                frag_day_time_timethr_start_btn.setText(Thru);
                frag_day_time_timethr_end_btn.setText(ThruTo);

            }


            // Fri start Time
            if (Fri.equals("") || FriTo.equals("")) {
                frag_day_time_timefri_switch_btn.setChecked(false);

            } else {
                frag_day_time_timefri_start_btn.setText(Fri);
                frag_day_time_timefri_end_btn.setText(FriTo);

            }

            // Sat start Time
            if (Sat.equals("") || SatTo.equals("")) {
                frag_day_time_timesat_switch_btn.setChecked(false);
            } else {
                frag_day_time_timesat_start_btn.setText(Sat);
                frag_day_time_timesat_end_btn.setText(SatTo);

            }

            // Sun start Time
            if (Sun.equals("") || SunTo.equals("null")) {
                frag_day_time_timesun_switch_btn.setChecked(false);

            } else {
                frag_day_time_timesun_start_btn.setText(Sun);
                frag_day_time_timesun_end_btn.setText(SunTo);

            }


            if (!frag_day_time_date_end_btn.getText().toString().equals("End date") && frag_day_time_date_switch_btn.isChecked()) {


                ArrayList<String> day_array = getListDate(frag_day_time_date_start_btn.getText().toString(), frag_day_time_date_end_btn.getText().toString());
                String day = "";
                boolean mon = false, tue = false, wed = false, thr = false, fri = false, sat = false, sun = false;

                if (day_array.size() == 0) {

                } else {

                    frag_day_time_timemon_switch_btn.setEnabled(false);
                    frag_day_time_timemon_switch_btn.setChecked(false);

                    frag_day_time_timetue_switch_btn.setEnabled(false);
                    frag_day_time_timemon_switch_btn.setChecked(false);

                    frag_day_time_timewed_switch_btn.setEnabled(false);
                    frag_day_time_timetue_switch_btn.setChecked(false);

                    frag_day_time_timewed_switch_btn.setEnabled(false);
                    frag_day_time_timewed_switch_btn.setChecked(false);

                    frag_day_time_timethr_switch_btn.setEnabled(false);
                    frag_day_time_timethr_switch_btn.setChecked(false);

                    frag_day_time_timefri_switch_btn.setEnabled(false);
                    frag_day_time_timefri_switch_btn.setChecked(false);

                    frag_day_time_timesat_switch_btn.setEnabled(false);
                    frag_day_time_timesat_switch_btn.setChecked(false);

                    frag_day_time_timesun_switch_btn.setEnabled(false);
                    frag_day_time_timesun_switch_btn.setChecked(false);
                }

                for (int i = 0; i < day_array.size(); i++) {

                    day += day_array.get(i);

                    Log.e("daytwo", day);


                }
                if (day.contains("Mon")) {


                    frag_day_time_timemon_switch_btn.setEnabled(true);
                    frag_day_time_timemon_switch_btn.setChecked(true);


                }

                if (day.contains("Tue")) {


                    frag_day_time_timetue_switch_btn.setEnabled(true);
                    frag_day_time_timetue_switch_btn.setChecked(true);


                }
                if (day.contains("Wed")) {


                    frag_day_time_timewed_switch_btn.setEnabled(true);
                    frag_day_time_timewed_switch_btn.setChecked(true);


                }
                if (day.contains("Thu")) {


                    frag_day_time_timethr_switch_btn.setEnabled(true);
                    frag_day_time_timethr_switch_btn.setChecked(true);


                }

                if (day.contains("Fri")) {


                    frag_day_time_timefri_switch_btn.setEnabled(true);
                    frag_day_time_timefri_switch_btn.setChecked(true);


                }
                if (day.contains("Sat")) {


                    frag_day_time_timesat_switch_btn.setEnabled(true);
                    frag_day_time_timesat_switch_btn.setChecked(true);


                }
                if (day.contains("Sun")) {

                    frag_day_time_timesun_switch_btn.setEnabled(true);
                    frag_day_time_timesun_switch_btn.setChecked(true);

                }
            }
        }
        return Mroot;
    }


    @OnClick(R.id.frag_day_time_next_btn)
    void goNext() {
        // save data in address array

        // date start and End
        if (frag_day_time_date_switch_btn.isChecked()) {
            NewStartDate = frag_day_time_date_start_btn.getText().toString();
            // LandingActivity.business_data.getAdderess_data().get(pos).setDate_end(frag_day_time_date_end_btn.getText().toString());
        } else {
            NewEndDate = frag_day_time_date_start_btn.getText().toString();
            //  LandingActivity.business_data.getAdderess_data().get(pos).setDate_end("2019-12-11"/*bh_date_start_btn.getText().toString()*/);
        }

        // Mon start and End Time
        if (frag_day_time_timemon_switch_btn.isChecked()) {
            mMon = frag_day_time_timemon_start_btn.getText().toString();
            mMonTo = frag_day_time_timemon_end_btn.getText().toString();
        } else {
            mMon = "";
            mMonTo = "";
        }

        // Tue start and End Time
        if (frag_day_time_timetue_switch_btn.isChecked()) {
            mTue = frag_day_time_timetue_start_btn.getText().toString();
            mTueTo = frag_day_time_timetue_end_btn.getText().toString();
        } else {
            mTue = "";
            mTueTo = "";
        }

        // Wed start and End Time
        if (frag_day_time_timewed_switch_btn.isChecked()) {
            mWed = frag_day_time_timewed_start_btn.getText().toString();
            mWedTo = frag_day_time_timewed_end_btn.getText().toString();
        } else {
            mWed = "";
            mWedTo = "";
        }

        // thr start and End Time
        if (frag_day_time_timethr_switch_btn.isChecked()) {
            mThru =frag_day_time_timethr_start_btn.getText().toString();
            mThruTo= frag_day_time_timethr_end_btn.getText().toString();
        } else {
            mThru ="";
            mThruTo="";
        }

        // fri start and End Time
        if (frag_day_time_timefri_switch_btn.isChecked()) {
            mFri=frag_day_time_timefri_start_btn.getText().toString();
            mFriTo=frag_day_time_timefri_end_btn.getText().toString();
        } else {
            mFri="";
            mFriTo="";
        }

        // Sat start and End Time
        if (frag_day_time_timesat_switch_btn.isChecked()) {
            mSat=frag_day_time_timesat_start_btn.getText().toString();
            mSatTo=frag_day_time_timesat_end_btn.getText().toString();
        } else {
            mSat="";
            mSatTo="";
        }


        // Sun start and End Time
        if (frag_day_time_timesun_switch_btn.isChecked()) {
            mSun=frag_day_time_timesun_start_btn.getText().toString();
            mSunTo=frag_day_time_timesun_end_btn.getText().toString();
        } else {
            mSun="";
            mSunTo="";
        }


        // call api to create event id
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "editbusinessAddress")
                .appendQueryParameter("business_id", /*AppConstants.app_data.getString("user_id", "")*/mPrefs.getBusinessId())
                .appendQueryParameter("address_id", mPrefs.getAddressId())
                .appendQueryParameter("start_date",StartDate )
                .appendQueryParameter("end_date", EndDate)

                .appendQueryParameter("mon_from_time",mMon)
                .appendQueryParameter("mon_to_time", mMonTo)

                .appendQueryParameter("tue_from_time", mTue)
                .appendQueryParameter("tue_to_time", mTueTo)

                .appendQueryParameter("wed_from_time", mWed)
                .appendQueryParameter("wed_to_time", mWedTo)

                .appendQueryParameter("thru_from_time", mThru)
                .appendQueryParameter("thru_to_time", mThruTo)

                .appendQueryParameter("fri_from_time", mFri)
                .appendQueryParameter("fri_to_time", mFriTo)


                .appendQueryParameter("sat_from_time", mSat)
                .appendQueryParameter("sat_to_time", mSatTo)

                .appendQueryParameter("sun_from_time", mSun)
                .appendQueryParameter("sun_to_time", mSunTo);


        Log.e("url", builder.build().toString());


        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }


    @OnClick(R.id.frag_day_time_back_btn)
    void goBack() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.frag_day_time_date_start_btn)
    void getstartDate() {
        getDateWithcallback(frag_day_time_date_start_btn, callback);
    }

    @OnClick(R.id.frag_day_time_date_end_btn)
    void getendDate() {
        if (frag_day_time_date_switch_btn.isChecked()) {
            getDateWithcallback(frag_day_time_date_end_btn, callback);
        } else {

        }

    }

    // mon time click listener
    @OnClick(R.id.frag_day_time_timemon_start_btn)
    void getmonStartTime() {
        getTime(frag_day_time_timemon_start_btn, frag_day_time_timemon_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timemon_end_btn)
    void getmonEndTime() {
        getTimeback(frag_day_time_timemon_end_btn, frag_day_time_timemon_start_btn.getText().toString());
    }

    // tue time click listener
    @OnClick(R.id.frag_day_time_timetue_start_btn)
    void gettueStartTime() {
        getTime(frag_day_time_timetue_start_btn, frag_day_time_timetue_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timetue_end_btn)
    void gettueEndTime() {
        getTimeback(frag_day_time_timetue_end_btn, frag_day_time_timetue_start_btn.getText().toString());
    }

    // wed time click listener
    @OnClick(R.id.frag_day_time_timewed_start_btn)
    void getwedStartTime() {
        getTime(frag_day_time_timewed_start_btn, frag_day_time_timewed_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timewed_end_btn)
    void getwedEndTime() {
        getTimeback(frag_day_time_timewed_end_btn, frag_day_time_timewed_start_btn.getText().toString());
    }

    // thr time click listener
    @OnClick(R.id.frag_day_time_timethr_start_btn)
    void getthrStartTime() {
        getTime(frag_day_time_timethr_start_btn, frag_day_time_timethr_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timethr_end_btn)
    void getthrEndTime() {
        getTimeback(frag_day_time_timethr_end_btn, frag_day_time_timethr_start_btn.getText().toString());
    }

    // fri time click listener
    @OnClick(R.id.frag_day_time_timefri_start_btn)
    void getfriStartTime() {
        getTime(frag_day_time_timefri_start_btn, frag_day_time_timefri_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timefri_end_btn)
    void getfriEndTime() {
        getTimeback(frag_day_time_timefri_end_btn, frag_day_time_timefri_start_btn.getText().toString());
    }

    // sat time click listener
    @OnClick(R.id.frag_day_time_timesat_start_btn)
    void getsatStartTime() {
        getTime(frag_day_time_timesat_start_btn, frag_day_time_timesat_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timesat_end_btn)
    void getsatEndTime() {
        getTimeback(frag_day_time_timesat_end_btn, frag_day_time_timesat_start_btn.getText().toString());
    }

    // sun time click listener
    @OnClick(R.id.frag_day_time_timesun_start_btn)
    void getsunStartTime() {
        getTime(frag_day_time_timesun_start_btn, frag_day_time_timesun_end_btn.getText().toString());
    }

    @OnClick(R.id.frag_day_time_timesun_end_btn)
    void getsunEndTime() {
        getTimeback(frag_day_time_timesun_end_btn, frag_day_time_timesun_start_btn.getText().toString());
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (compoundButton.getId() == R.id.frag_day_time_date_switch_btn) {

            if (b) {
                frag_day_time_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

                callback.getResponse("Date");


            } else {
                frag_day_time_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                frag_day_time_timemon_switch_btn.setEnabled(true);
                frag_day_time_timemon_switch_btn.setChecked(true);

                frag_day_time_timetue_switch_btn.setEnabled(true);
                frag_day_time_timetue_switch_btn.setChecked(true);

                frag_day_time_timewed_switch_btn.setEnabled(true);
                frag_day_time_timewed_switch_btn.setChecked(true);

                frag_day_time_timethr_switch_btn.setEnabled(true);
                frag_day_time_timethr_switch_btn.setChecked(true);

                frag_day_time_timefri_switch_btn.setEnabled(true);
                frag_day_time_timefri_switch_btn.setChecked(true);

                frag_day_time_timesat_switch_btn.setEnabled(true);
                frag_day_time_timesat_switch_btn.setChecked(true);

                frag_day_time_timesun_switch_btn.setEnabled(true);
                frag_day_time_timesun_switch_btn.setChecked(true);
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timesun_switch_btn) {
            if (b) {
                frag_day_time_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                frag_day_time_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timemon_switch_btn) {
            if (b) {
                frag_day_time_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                frag_day_time_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timetue_switch_btn) {
            if (b) {
                frag_day_time_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                frag_day_time_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timewed_switch_btn) {
            if (b) {
                frag_day_time_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                frag_day_time_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timethr_switch_btn) {
            if (b) {
                frag_day_time_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                frag_day_time_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timefri_switch_btn) {
            if (b) {
                frag_day_time_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                frag_day_time_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.frag_day_time_timesat_switch_btn) {
            if (b) {

                frag_day_time_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                frag_day_time_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {

                frag_day_time_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                frag_day_time_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));

            }
        }

    }


    @Override
    public void getResponse(String response) {
        Log.e("res", response);


        Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

    }

}
