package frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
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

    private BusinessHourData businessHourData;
    private Bundle bundle;
    private StaffData staffData;
    private HttpresponseUpd callback;

    private Sharedpreferences mPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_staff_working_time_screen, null);
        ButterKnife.bind(this, Mroot);
        bundle = getArguments();
        callback = this;
        mPref=Sharedpreferences.getUserDataObj(getActivity());
        staffData = LandingActivity.staff_data_array.get(bundle.getInt("pos"));
        newstaff_working_date_switch_btn.setOnCheckedChangeListener(this);

        newstaff_working_timemon_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timetue_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timewed_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timethr_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timefri_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timesat_switch_btn.setOnCheckedChangeListener(this);
        newstaff_working_timesun_switch_btn.setOnCheckedChangeListener(this);

       // businessHourData = LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos"));

        try {
            businessHourData = LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos"));

            Log.e("o", businessHourData.getDate_start() + "--");

            //check working day of address
            if (businessHourData.getMon_start_time().equals("") || businessHourData.getMon_end_time().equals("") ||
                    businessHourData.getMon_start_time().equals("null") || businessHourData.getMon_end_time().equals("null")) {
                newstaff_working_timemon_switch_btn.setChecked(false);
                newstaff_working_timemon_switch_btn.setEnabled(false);

            }

            if (businessHourData.getTue_start_time().equals("") || businessHourData.getTue_end_time().equals("") || businessHourData.getTue_start_time().equals("null") || businessHourData.getTue_end_time().equals("null")) {
                newstaff_working_timetue_switch_btn.setChecked(false);
                newstaff_working_timetue_switch_btn.setEnabled(false);

            }
            if (businessHourData.getWed_start_time().equals("") || businessHourData.getWed_end_time().equals("") || businessHourData.getWed_start_time().equals("null") || businessHourData.getWed_end_time().equals("null")) {
                newstaff_working_timewed_switch_btn.setChecked(false);

                newstaff_working_timewed_switch_btn.setEnabled(false);
            }
            if (businessHourData.getThr_start_time().equals("") || businessHourData.getThr_end_time().equals("") || businessHourData.getThr_start_time().equals("null") || businessHourData.getThr_end_time().equals("null")) {
                newstaff_working_timethr_switch_btn.setChecked(false);

                newstaff_working_timethr_switch_btn.setEnabled(false);
            }
            if (businessHourData.getFri_start_time().equals("") || businessHourData.getFri_end_time().equals("") || businessHourData.getFri_start_time().equals("null") || businessHourData.getFri_end_time().equals("null")) {
                newstaff_working_timefri_switch_btn.setChecked(false);

                newstaff_working_timefri_switch_btn.setEnabled(false);
            }
            if (businessHourData.getSat_start_time().equals("") || businessHourData.getSat_end_time().equals("") || businessHourData.getSat_start_time().equals("null") || businessHourData.getSat_end_time().equals("null")) {
                newstaff_working_timesat_switch_btn.setChecked(false);

                newstaff_working_timesat_switch_btn.setEnabled(false);
            }
            if (businessHourData.getSun_start_time().equals("") || businessHourData.getSun_end_time().equals("") || businessHourData.getSun_start_time().equals("null") || businessHourData.getSun_end_time().equals("null")) {
                newstaff_working_timesun_switch_btn.setChecked(false);
                newstaff_working_timesun_switch_btn.setEnabled(false);
            }
            if (LandingActivity.staff == false) {

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

                // set start and end date
                if (businessHourData.getDate_start().equals(businessHourData.getDate_end())) {
                    newstaff_working_date_start_btn.setText(businessHourData.getDate_start());
                    newstaff_working_date_end_btn.setText(businessHourData.getDate_end());
                    newstaff_working_date_switch_btn.setChecked(false);
                    newstaff_working_date_switch_btn.setEnabled(false);
                } else {
                    newstaff_working_date_start_btn.setText(businessHourData.getDate_start());
                    newstaff_working_date_end_btn.setText(businessHourData.getDate_end());
                    newstaff_working_date_switch_btn.setChecked(true);
                }


                // set Mon Start and End Time
                if (businessHourData.getMon_start_time().equals("") ||
                        businessHourData.getMon_end_time().equals("")) {
                    newstaff_working_timemon_switch_btn.setChecked(false);
                } else {


                    newstaff_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                    newstaff_working_timemon_end_btn.setText(businessHourData.getMon_end_time());
                }


                // set Tue Start and End Time
                if (businessHourData.getTue_start_time().equals("") ||
                        businessHourData.getTue_end_time().equals("")) {
                    newstaff_working_timetue_switch_btn.setChecked(false);
                } else {

                    newstaff_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                    newstaff_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


                }

                // set Wed Start and End Time
                if (businessHourData.getWed_start_time().equals("") || businessHourData.getWed_end_time().equals("")) {
                    newstaff_working_timewed_switch_btn.setChecked(false);
                } else {

                    newstaff_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                    newstaff_working_timewed_end_btn.setText(businessHourData.getWed_end_time());
                }


                // set Thru Start and End Time
                if (businessHourData.getThr_start_time().equals("") || businessHourData.getThr_end_time().equals("")) {
                    newstaff_working_timethr_switch_btn.setChecked(false);
                } else {

                    newstaff_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                    newstaff_working_timethr_end_btn.setText(businessHourData.getThr_end_time());
                }


                // set Fri Start and End Time
                if (businessHourData.getFri_start_time().equals("") ||
                        businessHourData.getFri_end_time().equals("")) {
                    newstaff_working_timefri_switch_btn.setChecked(false);
                } else {

                    newstaff_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                    newstaff_working_timefri_end_btn.setText(businessHourData.getFri_end_time());
                }


                // set Sat Start and End Time
                if (businessHourData.getSat_start_time().equals("") ||
                        businessHourData.getSat_end_time().equals("")) {
                    newstaff_working_timesat_switch_btn.setChecked(false);
                } else {

                    newstaff_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                    newstaff_working_timesat_end_btn.setText(businessHourData.getSat_end_time());
                }


                // set Sun Start and End Time
                if (businessHourData.getSun_start_time().equals("") ||
                        businessHourData.getSun_end_time().equals("")) {
                    newstaff_working_timesun_switch_btn.setChecked(false);
                } else {

                    newstaff_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                    newstaff_working_timesun_end_btn.setText(businessHourData.getSun_end_time());
                }


            } else {
                // set start and end date
                if (staffData.getStart_date().equals(staffData.getEnd_date())) {
                    newstaff_working_date_start_btn.setText(staffData.getStart_date());
                    newstaff_working_date_end_btn.setText(staffData.getEnd_date());
                    newstaff_working_date_switch_btn.setChecked(false);
                    newstaff_working_date_switch_btn.setEnabled(false);
                } else {
                    newstaff_working_date_start_btn.setText(staffData.getStart_date());
                    newstaff_working_date_end_btn.setText(staffData.getEnd_date());
                    newstaff_working_date_switch_btn.setChecked(true);
                }


                // set Mon Start and End Time
                if (staffData.getMon_from_time().equals("") ||
                        staffData.getMon_to_time().equals("")) {
                    newstaff_working_timemon_switch_btn.setChecked(false);
                } else {

                    if (staffData.getMon_from_time().equals("")) {

                    } else {
                        newstaff_working_timemon_start_btn.setText(staffData.getMon_from_time());
                        newstaff_working_timemon_end_btn.setText(staffData.getMon_to_time());
                    }
                }

                // set Tue Start and End Time
                if (staffData.getTue_from_time().equals("") ||
                        staffData.getTue_to_time().equals("")) {
                    newstaff_working_timetue_switch_btn.setChecked(false);
                } else {
                    if (staffData.getTue_from_time().equals("")) {

                    } else {
                        newstaff_working_timetue_start_btn.setText(staffData.getTue_from_time());
                        newstaff_working_timetue_end_btn.setText(staffData.getTue_to_time());
                    }

                }

                // set Wed Start and End Time
                if (staffData.getWed_from_time().equals("") || staffData.getWed_to_time().equals("")) {
                    newstaff_working_timewed_switch_btn.setChecked(false);
                } else {
                    if (staffData.getWed_from_time().equals("")) {

                    } else {
                        newstaff_working_timewed_start_btn.setText(staffData.getWed_from_time());
                        newstaff_working_timewed_end_btn.setText(staffData.getWed_to_time());
                    }
                }

                // set Thru Start and End Time
                if (staffData.getThru_from_time().equals("") || staffData.getThru_to_time().equals("")) {
                    newstaff_working_timethr_switch_btn.setChecked(false);
                } else {
                    if (staffData.getThru_from_time().equals("")) {

                    } else {
                        newstaff_working_timethr_start_btn.setText(staffData.getThru_from_time());
                        newstaff_working_timethr_end_btn.setText(staffData.getThru_to_time());
                    }
                }

                // set Fri Start and End Time
                if (staffData.getFri_from_time().equals("") ||
                        staffData.getFri_to_time().equals("")) {
                    newstaff_working_timefri_switch_btn.setChecked(false);
                } else {
                    if (staffData.getFri_from_time().equals("")) {

                    } else {
                        newstaff_working_timefri_start_btn.setText(staffData.getFri_from_time());
                        newstaff_working_timefri_end_btn.setText(staffData.getFri_to_time());
                    }
                }

                // set Sat Start and End Time
                if (staffData.getSat_from_time().equals("") ||
                        staffData.getSat_to_time().equals("")) {
                    newstaff_working_timesat_switch_btn.setChecked(false);
                } else {
                    if (staffData.getFri_from_time().equals("")) {

                    } else {
                        newstaff_working_timesat_start_btn.setText(staffData.getSat_from_time());
                        newstaff_working_timesat_end_btn.setText(staffData.getSat_to_time());
                    }
                }

                // set Sun Start and End Time
                if (staffData.getSun_from_time().equals("") ||
                        staffData.getSun_to_time().equals("")) {
                    newstaff_working_timesun_switch_btn.setChecked(false);
                } else {
                    if (staffData.getSun_from_time().equals("")) {

                    } else {
                        newstaff_working_timesun_start_btn.setText(staffData.getSun_from_time());
                        newstaff_working_timesun_end_btn.setText(staffData.getSun_to_time());
                    }
                }
            }
        } catch (Exception e) {
            businessHourData = LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos"));
        }

        return Mroot;

    }


    @OnClick(R.id.newstaff_working_next_btn)
    void goNext() {
        // save data in address array

        LandingActivity.staff_data_array.get(bundle.getInt("pos")).setStart_date(newstaff_working_date_start_btn.getText().toString());
        LandingActivity.staff_data_array.get(bundle.getInt("pos")).setEnd_date(newstaff_working_date_end_btn.getText().toString());

        // date start and End
        if (newstaff_working_date_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setStart_date(newstaff_working_date_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setEnd_date(newstaff_working_date_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setStart_date(newstaff_working_date_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setEnd_date(newstaff_working_date_start_btn.getText().toString());
        }

        // Mon start and End Time
        if (newstaff_working_timemon_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_from_time(newstaff_working_timemon_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_to_time(newstaff_working_timemon_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_to_time("");
        }

        // Tue start and End Time
        if (newstaff_working_timetue_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_from_time(newstaff_working_timetue_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_to_time(newstaff_working_timetue_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_to_time("");
        }

        // Wed start and End Time
        if (newstaff_working_timewed_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_from_time(newstaff_working_timewed_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_to_time(newstaff_working_timewed_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_to_time("");
        }

        // thr start and End Time
        if (newstaff_working_timethr_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_from_time(newstaff_working_timethr_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_to_time(newstaff_working_timethr_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_to_time("");
        }

        // fri start and End Time
        if (newstaff_working_timefri_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_from_time(newstaff_working_timefri_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_to_time(newstaff_working_timefri_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_to_time("");
        }

        // Sat start and End Time
        if (newstaff_working_timesat_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_from_time(newstaff_working_timesat_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_to_time(newstaff_working_timesat_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_to_time("");
        }

        // Sun start and End Time
        if (newstaff_working_timesun_switch_btn.isChecked()) {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_from_time(newstaff_working_timesun_start_btn.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_to_time(newstaff_working_timesun_end_btn.getText().toString());
        } else {
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_from_time("");
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_to_time("");
        }

        LandingActivity.staff = true;
        Bundle _bundle = new Bundle();
        _bundle.putString("src", bundle.getString("src"));
        _bundle.putInt("pos", bundle.getInt("pos"));

        replaceFrag(new NewStaffInfoFrag(), _bundle, BusinessHourFrag.class.getName());
    }


    @OnClick(R.id.newstaff_working_back_btn)
    void goBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.newstaff_working_date_start_btn)
    void getstartDate() {
        // set calender visible date in between business start and end date
        AppUtils.getDatewithCallback(newstaff_working_date_start_btn, getActivity(), businessHourData.getDate_start(),
                businessHourData.getDate_end(), callback);

    }

    @OnClick(R.id.newstaff_working_date_end_btn)
    void getendDate() {

        // set calender visible date in between business start and end date
        if (newstaff_working_date_switch_btn.isChecked()) {
            AppUtils.getDatewithCallback(newstaff_working_date_end_btn, getActivity(), businessHourData.getDate_start(), businessHourData.getDate_end(), callback);
        } else {

        }

    }

    // mon time click listener
    @OnClick(R.id.newstaff_working_timemon_start_btn)
    void getmonStartTime() {
        getTimestaff(newstaff_working_timemon_start_btn,
                businessHourData.getMon_start_time(), businessHourData.getMon_end_time());
    }

    @OnClick(R.id.newstaff_working_timemon_end_btn)
    void getmonEndTime() {


        getTimestaff(newstaff_working_timemon_end_btn,
                businessHourData.getMon_start_time(), businessHourData.getMon_end_time());

    }

    // tue time click listener
    @OnClick(R.id.newstaff_working_timetue_start_btn)
    void gettueStartTime() {

        getTimestaff(newstaff_working_timetue_start_btn,
                businessHourData.getTue_start_time(), businessHourData.getTue_end_time());


    }

    @OnClick(R.id.newstaff_working_timetue_end_btn)
    void gettueEndTime() {
        getTimestaff(newstaff_working_timetue_end_btn,
                businessHourData.getTue_start_time(), businessHourData.getTue_end_time());

    }

    // wed time click listener
    @OnClick(R.id.newstaff_working_timewed_start_btn)
    void getwedStartTime() {
        getTimestaff(newstaff_working_timewed_start_btn,
                businessHourData.getWed_start_time(), businessHourData.getWed_end_time());

    }

    @OnClick(R.id.newstaff_working_timewed_end_btn)
    void getwedEndTime() {
        getTimestaff(newstaff_working_timewed_end_btn,
                businessHourData.getWed_start_time(), businessHourData.getWed_end_time());

    }

    // thr time click listener
    @OnClick(R.id.newstaff_working_timethr_start_btn)
    void getthrStartTime() {
        getTimestaff(newstaff_working_timethr_start_btn,
                businessHourData.getThr_start_time(), businessHourData.getThr_end_time());

    }

    @OnClick(R.id.newstaff_working_timethr_end_btn)
    void getthrEndTime() {
        getTimestaff(newstaff_working_timethr_end_btn,
                businessHourData.getThr_start_time(), businessHourData.getThr_end_time());

    }

    // fri time click listener
    @OnClick(R.id.newstaff_working_timefri_start_btn)
    void getfriStartTime() {

        getTimestaff(newstaff_working_timefri_start_btn,
                businessHourData.getFri_start_time(), businessHourData.getFri_end_time());

    }

    @OnClick(R.id.newstaff_working_timefri_end_btn)
    void getfriEndTime() {

        getTimestaff(newstaff_working_timefri_end_btn,
                businessHourData.getFri_start_time(), businessHourData.getFri_end_time());


    }

    // sat time click listener
    @OnClick(R.id.newstaff_working_timesat_start_btn)
    void getsatStartTime() {
        getTimestaff(newstaff_working_timesat_start_btn,
                businessHourData.getSat_start_time(), businessHourData.getSat_end_time());


    }

    @OnClick(R.id.newstaff_working_timesat_end_btn)
    void getsatEndTime() {


        getTimestaff(newstaff_working_timesat_end_btn,
                businessHourData.getSat_start_time(), businessHourData.getSat_end_time());

    }

    // sun time click listener
    @OnClick(R.id.newstaff_working_timesun_start_btn)
    void getsunStartTime() {
        getTimestaff(newstaff_working_timesun_start_btn,
                businessHourData.getSun_start_time(), businessHourData.getSun_end_time());

    }

    @OnClick(R.id.newstaff_working_timesun_end_btn)
    void getsunEndTime() {
        getTimestaff(newstaff_working_timesun_end_btn,
                businessHourData.getSun_start_time(), businessHourData.getSun_end_time());

    }


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

            } else {
                newstaff_working_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timemon_switch_btn) {
            if (b) {
                newstaff_working_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                newstaff_working_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timetue_switch_btn) {
            if (b) {
                newstaff_working_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                newstaff_working_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timewed_switch_btn) {
            if (b) {
                newstaff_working_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                newstaff_working_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timethr_switch_btn) {
            if (b) {
                newstaff_working_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                newstaff_working_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timefri_switch_btn) {
            if (b) {
                newstaff_working_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {
                newstaff_working_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        } else if (compoundButton.getId() == R.id.newstaff_working_timesat_switch_btn) {
            if (b) {

                newstaff_working_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newstaff_working_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            } else {

                newstaff_working_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newstaff_working_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));

            }
        }


    }

    @Override
    public void getResponse(String response) {
        if (!newstaff_working_date_end_btn.getText().toString().equals("End date") && newstaff_working_date_switch_btn.isChecked()) {


            ArrayList<String> day_array = getListDate(newstaff_working_date_start_btn.getText().toString(), newstaff_working_date_end_btn.getText().toString());
            String day = "";
            boolean mon = false, tue = false, wed = false, thr = false, fri = false, sat = false, sun = false;

            if (day_array.size() == 0) {

            } else {

                newstaff_working_timemon_switch_btn.setEnabled(false);
                newstaff_working_timemon_switch_btn.setChecked(false);

                newstaff_working_timetue_switch_btn.setEnabled(false);
                newstaff_working_timemon_switch_btn.setChecked(false);

                newstaff_working_timewed_switch_btn.setEnabled(false);
                newstaff_working_timetue_switch_btn.setChecked(false);

                newstaff_working_timewed_switch_btn.setEnabled(false);
                newstaff_working_timewed_switch_btn.setChecked(false);

                newstaff_working_timethr_switch_btn.setEnabled(false);
                newstaff_working_timethr_switch_btn.setChecked(false);

                newstaff_working_timefri_switch_btn.setEnabled(false);
                newstaff_working_timefri_switch_btn.setChecked(false);

                newstaff_working_timesat_switch_btn.setEnabled(false);
                newstaff_working_timesat_switch_btn.setChecked(false);

                newstaff_working_timesun_switch_btn.setEnabled(false);
                newstaff_working_timesun_switch_btn.setChecked(false);
            }

            for (int i = 0; i < day_array.size(); i++) {

                day += day_array.get(i);

                Log.e("daytwo", day);


            }
            if (day.contains("Mon")) {


                newstaff_working_timemon_switch_btn.setEnabled(true);
                newstaff_working_timemon_switch_btn.setChecked(true);
                newstaff_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                newstaff_working_timemon_end_btn.setText(businessHourData.getMon_end_time());


            }

            if (day.contains("Tue")) {


                newstaff_working_timetue_switch_btn.setEnabled(true);
                newstaff_working_timetue_switch_btn.setChecked(true);
                newstaff_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                newstaff_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


            }
            if (day.contains("Wed")) {


                newstaff_working_timewed_switch_btn.setEnabled(true);
                newstaff_working_timewed_switch_btn.setChecked(true);
                newstaff_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                newstaff_working_timewed_end_btn.setText(businessHourData.getWed_end_time());


            }
            if (day.contains("Thu")) {


                newstaff_working_timethr_switch_btn.setEnabled(true);
                newstaff_working_timethr_switch_btn.setChecked(true);
                newstaff_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                newstaff_working_timethr_end_btn.setText(businessHourData.getThr_end_time());


            }

            if (day.contains("Fri")) {


                newstaff_working_timefri_switch_btn.setEnabled(true);
                newstaff_working_timefri_switch_btn.setChecked(true);
                newstaff_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                newstaff_working_timefri_end_btn.setText(businessHourData.getFri_end_time());


            }
            if (day.contains("Sat")) {


                newstaff_working_timesat_switch_btn.setEnabled(true);
                newstaff_working_timesat_switch_btn.setChecked(true);
                newstaff_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                newstaff_working_timesat_end_btn.setText(businessHourData.getSat_end_time());

            }
            if (day.contains("Sun")) {


                newstaff_working_timesun_switch_btn.setEnabled(true);
                newstaff_working_timesun_switch_btn.setChecked(true);
                newstaff_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                newstaff_working_timesun_end_btn.setText(businessHourData.getSun_end_time());


            }

        } else {

            newstaff_working_timemon_switch_btn.setEnabled(false);
            newstaff_working_timemon_switch_btn.setChecked(false);

            newstaff_working_timetue_switch_btn.setEnabled(false);
            newstaff_working_timemon_switch_btn.setChecked(false);

            newstaff_working_timewed_switch_btn.setEnabled(false);
            newstaff_working_timetue_switch_btn.setChecked(false);

            newstaff_working_timewed_switch_btn.setEnabled(false);
            newstaff_working_timewed_switch_btn.setChecked(false);

            newstaff_working_timethr_switch_btn.setEnabled(false);
            newstaff_working_timethr_switch_btn.setChecked(false);

            newstaff_working_timefri_switch_btn.setEnabled(false);
            newstaff_working_timefri_switch_btn.setChecked(false);

            newstaff_working_timesat_switch_btn.setEnabled(false);
            newstaff_working_timesat_switch_btn.setChecked(false);

            newstaff_working_timesun_switch_btn.setEnabled(false);
            newstaff_working_timesun_switch_btn.setChecked(false);


            Date date = new Date();
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = date_format.parse(newstaff_working_date_start_btn.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);// Note that Month value is 0-based. e.g., 0 for January.

            int reslut = calendar.get(Calendar.DAY_OF_WEEK);

            Log.e("day", reslut + "");


            switch (reslut) {

                case Calendar.MONDAY:
                    newstaff_working_timemon_switch_btn.setEnabled(true);
                    newstaff_working_timemon_switch_btn.setChecked(true);

                    newstaff_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                    newstaff_working_timemon_end_btn.setText(businessHourData.getMon_end_time());


                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timetue_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timewed_switch_btn.setChecked(false);

                    newstaff_working_timethr_switch_btn.setEnabled(false);
                    newstaff_working_timethr_switch_btn.setChecked(false);

                    newstaff_working_timefri_switch_btn.setEnabled(false);
                    newstaff_working_timefri_switch_btn.setChecked(false);

                    newstaff_working_timesat_switch_btn.setEnabled(false);
                    newstaff_working_timesat_switch_btn.setChecked(false);

                    newstaff_working_timesun_switch_btn.setEnabled(false);
                    newstaff_working_timesun_switch_btn.setChecked(false);
                    break;

                case Calendar.TUESDAY:
                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timemon_switch_btn.setChecked(false);

                    newstaff_working_timetue_switch_btn.setEnabled(true);
                    newstaff_working_timetue_switch_btn.setChecked(true);
                    newstaff_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                    newstaff_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timewed_switch_btn.setChecked(false);

                    newstaff_working_timethr_switch_btn.setEnabled(false);
                    newstaff_working_timethr_switch_btn.setChecked(false);

                    newstaff_working_timefri_switch_btn.setEnabled(false);
                    newstaff_working_timefri_switch_btn.setChecked(false);

                    newstaff_working_timesat_switch_btn.setEnabled(false);
                    newstaff_working_timesat_switch_btn.setChecked(false);

                    newstaff_working_timesun_switch_btn.setEnabled(false);
                    newstaff_working_timesun_switch_btn.setChecked(false);
                    break;

                case Calendar.WEDNESDAY:
                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timemon_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timetue_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(true);
                    newstaff_working_timewed_switch_btn.setChecked(true);
                    newstaff_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                    newstaff_working_timewed_end_btn.setText(businessHourData.getWed_end_time());


                    newstaff_working_timethr_switch_btn.setEnabled(false);
                    newstaff_working_timethr_switch_btn.setChecked(false);

                    newstaff_working_timefri_switch_btn.setEnabled(false);
                    newstaff_working_timefri_switch_btn.setChecked(false);

                    newstaff_working_timesat_switch_btn.setEnabled(false);
                    newstaff_working_timesat_switch_btn.setChecked(false);

                    newstaff_working_timesun_switch_btn.setEnabled(false);
                    newstaff_working_timesun_switch_btn.setChecked(false);
                    break;
                case Calendar.THURSDAY:
                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timemon_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timetue_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timewed_switch_btn.setChecked(false);

                    newstaff_working_timethr_switch_btn.setEnabled(true);
                    newstaff_working_timethr_switch_btn.setChecked(true);

                    newstaff_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                    newstaff_working_timethr_end_btn.setText(businessHourData.getThr_end_time());


                    newstaff_working_timefri_switch_btn.setEnabled(false);
                    newstaff_working_timefri_switch_btn.setChecked(false);

                    newstaff_working_timesat_switch_btn.setEnabled(false);
                    newstaff_working_timesat_switch_btn.setChecked(false);

                    newstaff_working_timesun_switch_btn.setEnabled(false);
                    newstaff_working_timesun_switch_btn.setChecked(false);
                    break;
                case Calendar.FRIDAY:
                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timemon_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timetue_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timewed_switch_btn.setChecked(false);

                    newstaff_working_timethr_switch_btn.setEnabled(false);
                    newstaff_working_timethr_switch_btn.setChecked(false);

                    newstaff_working_timefri_switch_btn.setEnabled(true);
                    newstaff_working_timefri_switch_btn.setChecked(true);
                    newstaff_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                    newstaff_working_timefri_end_btn.setText(businessHourData.getFri_end_time());

                    newstaff_working_timesat_switch_btn.setEnabled(false);
                    newstaff_working_timesat_switch_btn.setChecked(false);

                    newstaff_working_timesun_switch_btn.setEnabled(false);
                    newstaff_working_timesun_switch_btn.setChecked(false);
                    break;

                case Calendar.SATURDAY:
                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timemon_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timetue_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timewed_switch_btn.setChecked(false);

                    newstaff_working_timethr_switch_btn.setEnabled(false);
                    newstaff_working_timethr_switch_btn.setChecked(false);

                    newstaff_working_timefri_switch_btn.setEnabled(false);
                    newstaff_working_timefri_switch_btn.setChecked(false);

                    newstaff_working_timesat_switch_btn.setEnabled(true);
                    newstaff_working_timesat_switch_btn.setChecked(true);
                    newstaff_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                    newstaff_working_timesat_end_btn.setText(businessHourData.getSat_end_time());

                    newstaff_working_timesun_switch_btn.setEnabled(false);
                    newstaff_working_timesun_switch_btn.setChecked(false);
                    break;
                case Calendar.SUNDAY:
                    newstaff_working_timetue_switch_btn.setEnabled(false);
                    newstaff_working_timemon_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timetue_switch_btn.setChecked(false);

                    newstaff_working_timewed_switch_btn.setEnabled(false);
                    newstaff_working_timewed_switch_btn.setChecked(false);

                    newstaff_working_timethr_switch_btn.setEnabled(false);
                    newstaff_working_timethr_switch_btn.setChecked(false);

                    newstaff_working_timefri_switch_btn.setEnabled(false);
                    newstaff_working_timefri_switch_btn.setChecked(false);

                    newstaff_working_timesat_switch_btn.setEnabled(false);
                    newstaff_working_timesat_switch_btn.setChecked(false);

                    newstaff_working_timesun_switch_btn.setEnabled(true);
                    newstaff_working_timesun_switch_btn.setChecked(true);
                    newstaff_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                    newstaff_working_timesun_end_btn.setText(businessHourData.getSun_end_time());

                    break;


            }


        }
    }
}
