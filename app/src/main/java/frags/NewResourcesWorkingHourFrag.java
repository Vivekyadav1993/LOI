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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.BusinessHourData;
import models.ResourceData;

/**
 * Created by teknik on 10/5/2017.
 */

public class NewResourcesWorkingHourFrag extends HelperFrags implements CompoundButton.OnCheckedChangeListener , HttpresponseUpd {

    private View Mroot;

    // select date widgets
    @BindView(R.id.newres_working_date_start_btn)
    TextView newres_working_date_start_btn;

    @BindView(R.id.newres_working_date_end_btn)
    TextView newres_working_date_end_btn;

    @BindView(R.id.newres_working_date_switch_btn)
    Switch newres_working_date_switch_btn;

    // monday time widgets
    @BindView(R.id.newres_working_timemon_start_btn)
    TextView newres_working_timemon_start_btn;

    @BindView(R.id.newres_working_timemon_end_btn)
    TextView newres_working_timemon_end_btn;

    @BindView(R.id.newres_working_timemon_switch_btn)
    Switch newres_working_timemon_switch_btn;

    // tuesday time widgets
    @BindView(R.id.newres_working_timetue_start_btn)
    TextView newres_working_timetue_start_btn;

    @BindView(R.id.newres_working_timetue_end_btn)
    TextView newres_working_timetue_end_btn;

    @BindView(R.id.newres_working_timetue_switch_btn)
    Switch newres_working_timetue_switch_btn;

    // wednesday time widgets
    @BindView(R.id.newres_working_timewed_start_btn)
    TextView newres_working_timewed_start_btn;

    @BindView(R.id.newres_working_timewed_end_btn)
    TextView newres_working_timewed_end_btn;

    @BindView(R.id.newres_working_timewed_switch_btn)
    Switch newres_working_timewed_switch_btn;

    // thursday time widgets
    @BindView(R.id.newres_working_timethr_start_btn)
    TextView newres_working_timethr_start_btn;

    @BindView(R.id.newres_working_timethr_end_btn)
    TextView newres_working_timethr_end_btn;

    @BindView(R.id.newres_working_timethr_switch_btn)
    Switch newres_working_timethr_switch_btn;

    // friday time widgets
    @BindView(R.id.newres_working_timefri_start_btn)
    TextView newres_working_timefri_start_btn;

    @BindView(R.id.newres_working_timefri_end_btn)
    TextView newres_working_timefri_end_btn;

    @BindView(R.id.newres_working_timefri_switch_btn)
    Switch newres_working_timefri_switch_btn;

    // saturday time widgets
    @BindView(R.id.newres_working_timesat_start_btn)
    TextView newres_working_timesat_start_btn;

    @BindView(R.id.newres_working_timesat_end_btn)
    TextView newres_working_timesat_end_btn;

    @BindView(R.id.newres_working_timesat_switch_btn)
    Switch newres_working_timesat_switch_btn;

    // sunday time widgets
    @BindView(R.id.newres_working_timesun_start_btn)
    TextView newres_working_timesun_start_btn;

    @BindView(R.id.newres_working_timesun_end_btn)
    TextView newres_working_timesun_end_btn;

    @BindView(R.id.newres_working_timesun_switch_btn)
    Switch newres_working_timesun_switch_btn;

    private Bundle bundle;

    private BusinessHourData businessHourData;

    private ResourceData res_data;

    private HttpresponseUpd callback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_resources_working_hour_screen, null);
        ButterKnife.bind(this , Mroot);

        bundle = getArguments();



        callback = this;






        newres_working_date_switch_btn.setOnCheckedChangeListener(this);

        newres_working_timemon_switch_btn.setOnCheckedChangeListener(this);
        newres_working_timetue_switch_btn.setOnCheckedChangeListener(this);
        newres_working_timewed_switch_btn.setOnCheckedChangeListener(this);
        newres_working_timethr_switch_btn.setOnCheckedChangeListener(this);
        newres_working_timefri_switch_btn.setOnCheckedChangeListener(this);
        newres_working_timesat_switch_btn.setOnCheckedChangeListener(this);
        newres_working_timesun_switch_btn.setOnCheckedChangeListener(this);


        businessHourData = LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos"));

        res_data = LandingActivity.res_data_array.get(bundle.getInt("pos"));

        Log.e("o",businessHourData.getWed_start_time()+"--");







        //check working day of address
        if (businessHourData.getMon_start_time().equals("")||businessHourData.getMon_end_time().equals("")||businessHourData.getMon_start_time().equals("null")||businessHourData.getMon_end_time().equals("null")){
            newres_working_timemon_switch_btn.setChecked(false);
            newres_working_timemon_switch_btn.setEnabled(false);

        }

        if (businessHourData.getTue_start_time().equals("")||businessHourData.getTue_end_time().equals("")||businessHourData.getTue_start_time().equals("null")||businessHourData.getTue_end_time().equals("null")){
            newres_working_timetue_switch_btn.setChecked(false);
            newres_working_timetue_switch_btn.setEnabled(false);

        }
        if (businessHourData.getWed_start_time().equals("")||businessHourData.getWed_end_time().equals("")||businessHourData.getWed_start_time().equals("null")||businessHourData.getWed_end_time().equals("null")){
            newres_working_timewed_switch_btn.setChecked(false);

            newres_working_timewed_switch_btn.setEnabled(false);
        }
        if (businessHourData.getThr_start_time().equals("")||businessHourData.getThr_end_time().equals("")||businessHourData.getThr_start_time().equals("null")||businessHourData.getThr_end_time().equals("null")){
            newres_working_timethr_switch_btn.setChecked(false);

            newres_working_timethr_switch_btn.setEnabled(false);
        }
        if (businessHourData.getFri_start_time().equals("")||businessHourData.getFri_end_time().equals("")||businessHourData.getFri_start_time().equals("null")||businessHourData.getFri_end_time().equals("null")){
            newres_working_timefri_switch_btn.setChecked(false);

            newres_working_timefri_switch_btn.setEnabled(false);
        }
        if (businessHourData.getSat_start_time().equals("")||businessHourData.getSat_end_time().equals("")||businessHourData.getSat_start_time().equals("null")||businessHourData.getSat_end_time().equals("null")){
            newres_working_timesat_switch_btn.setChecked(false);

            newres_working_timesat_switch_btn.setEnabled(false);
        }
        if (businessHourData.getSun_start_time().equals("")||businessHourData.getSun_end_time().equals("")||businessHourData.getSun_start_time().equals("null")||businessHourData.getSun_end_time().equals("null")){
            newres_working_timesun_switch_btn.setChecked(false);

            newres_working_timesun_switch_btn.setEnabled(false);
        }


        if (LandingActivity.res==false) {
            // get current date
          /*  newres_working_date_start_btn.setText(getcurrentDate());*/


           /* // set start and end date
            if (businessHourData.getDate_start().equals(businessHourData.getDate_end())){
                newres_working_date_start_btn.setText(businessHourData.getDate_start());
                newres_working_date_end_btn.setText(businessHourData.getDate_end());
                newres_working_date_switch_btn.setChecked(false);
                newres_working_date_switch_btn.setEnabled(false                                                                                                                                                                                                                          );
            }
            else{
                newres_working_date_start_btn.setText(businessHourData.getDate_start());
                newres_working_date_end_btn.setText(businessHourData.getDate_end());
                newres_working_date_switch_btn.setChecked(true);
            }



            if (!newres_working_date_end_btn.getText().toString().equals("End date")&&newres_working_date_switch_btn.isChecked()) {


                ArrayList<String> day_array = getListDate(newres_working_date_start_btn.getText().toString(), newres_working_date_end_btn.getText().toString());
                String day = "";
                boolean mon = false, tue = false, wed = false, thr = false, fri = false, sat = false, sun = false;

                if (day_array.size() == 0) {

                } else {

                    newres_working_timemon_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                }

                for (int i = 0; i < day_array.size(); i++) {

                    day += day_array.get(i);

                    Log.e("daytwo", day);


                }
                if (day.contains("Mon")) {


                    newres_working_timemon_switch_btn.setEnabled(true);
                    newres_working_timemon_switch_btn.setChecked(true);
                    newres_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                    newres_working_timemon_end_btn.setText(businessHourData.getMon_end_time());


                }

                if (day.contains("Tue")) {


                    newres_working_timetue_switch_btn.setEnabled(true);
                    newres_working_timetue_switch_btn.setChecked(true);
                    newres_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                    newres_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


                }
                if (day.contains("Wed")) {


                    newres_working_timewed_switch_btn.setEnabled(true);
                    newres_working_timewed_switch_btn.setChecked(true);
                    newres_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                    newres_working_timewed_end_btn.setText(businessHourData.getWed_end_time());


                }
                if (day.contains("Thu")) {


                    newres_working_timethr_switch_btn.setEnabled(true);
                    newres_working_timethr_switch_btn.setChecked(true);
                    newres_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                    newres_working_timethr_end_btn.setText(businessHourData.getThr_end_time());


                }

                if (day.contains("Fri")) {


                    newres_working_timefri_switch_btn.setEnabled(true);
                    newres_working_timefri_switch_btn.setChecked(true);
                    newres_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                    newres_working_timefri_end_btn.setText(businessHourData.getFri_end_time());


                }
                if (day.contains("Sat")) {


                    newres_working_timesat_switch_btn.setEnabled(true);
                    newres_working_timesat_switch_btn.setChecked(true);
                    newres_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                    newres_working_timesat_end_btn.setText(businessHourData.getSat_end_time());

                }
                if (day.contains("Sun")) {


                    newres_working_timesun_switch_btn.setEnabled(true);
                    newres_working_timesun_switch_btn.setChecked(true);
                    newres_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                    newres_working_timesun_end_btn.setText(businessHourData.getSun_end_time());


                }

            }
            else{

                newres_working_timemon_switch_btn.setEnabled(false);
                newres_working_timemon_switch_btn.setChecked(false);

                newres_working_timetue_switch_btn.setEnabled(false);
                newres_working_timemon_switch_btn.setChecked(false);

                newres_working_timewed_switch_btn.setEnabled(false);
                newres_working_timetue_switch_btn.setChecked(false);

                newres_working_timewed_switch_btn.setEnabled(false);
                newres_working_timewed_switch_btn.setChecked(false);

                newres_working_timethr_switch_btn.setEnabled(false);
                newres_working_timethr_switch_btn.setChecked(false);

                newres_working_timefri_switch_btn.setEnabled(false);
                newres_working_timefri_switch_btn.setChecked(false);

                newres_working_timesat_switch_btn.setEnabled(false);
                newres_working_timesat_switch_btn.setChecked(false);

                newres_working_timesun_switch_btn.setEnabled(false);
                newres_working_timesun_switch_btn.setChecked(false);


                Date date = new Date();
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = date_format.parse(newres_working_date_start_btn.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);// Note that Month value is 0-based. e.g., 0 for January.

                int reslut = calendar.get(Calendar.DAY_OF_WEEK);

                Log.e("day" , reslut+"") ;


                switch (reslut) {

                    case Calendar.MONDAY:
                        newres_working_timemon_switch_btn.setEnabled(true);
                        newres_working_timemon_switch_btn.setChecked(true);

                        newres_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                        newres_working_timemon_end_btn.setText(businessHourData.getMon_end_time());



                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timetue_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timewed_switch_btn.setChecked(false);

                        newres_working_timethr_switch_btn.setEnabled(false);
                        newres_working_timethr_switch_btn.setChecked(false);

                        newres_working_timefri_switch_btn.setEnabled(false);
                        newres_working_timefri_switch_btn.setChecked(false);

                        newres_working_timesat_switch_btn.setEnabled(false);
                        newres_working_timesat_switch_btn.setChecked(false);

                        newres_working_timesun_switch_btn.setEnabled(false);
                        newres_working_timesun_switch_btn.setChecked(false);
                        break;

                    case Calendar.TUESDAY:
                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timemon_switch_btn.setChecked(false);

                        newres_working_timetue_switch_btn.setEnabled(true);
                        newres_working_timetue_switch_btn.setChecked(true);
                        newres_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                        newres_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timewed_switch_btn.setChecked(false);

                        newres_working_timethr_switch_btn.setEnabled(false);
                        newres_working_timethr_switch_btn.setChecked(false);

                        newres_working_timefri_switch_btn.setEnabled(false);
                        newres_working_timefri_switch_btn.setChecked(false);

                        newres_working_timesat_switch_btn.setEnabled(false);
                        newres_working_timesat_switch_btn.setChecked(false);

                        newres_working_timesun_switch_btn.setEnabled(false);
                        newres_working_timesun_switch_btn.setChecked(false);
                        break;

                    case Calendar.WEDNESDAY:
                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timemon_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timetue_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(true);
                        newres_working_timewed_switch_btn.setChecked(true);
                        newres_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                        newres_working_timewed_end_btn.setText(businessHourData.getWed_end_time());



                        newres_working_timethr_switch_btn.setEnabled(false);
                        newres_working_timethr_switch_btn.setChecked(false);

                        newres_working_timefri_switch_btn.setEnabled(false);
                        newres_working_timefri_switch_btn.setChecked(false);

                        newres_working_timesat_switch_btn.setEnabled(false);
                        newres_working_timesat_switch_btn.setChecked(false);

                        newres_working_timesun_switch_btn.setEnabled(false);
                        newres_working_timesun_switch_btn.setChecked(false);
                        break;
                    case Calendar.THURSDAY:
                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timemon_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timetue_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timewed_switch_btn.setChecked(false);

                        newres_working_timethr_switch_btn.setEnabled(true);
                        newres_working_timethr_switch_btn.setChecked(true);

                        newres_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                        newres_working_timethr_end_btn.setText(businessHourData.getThr_end_time());


                        newres_working_timefri_switch_btn.setEnabled(false);
                        newres_working_timefri_switch_btn.setChecked(false);

                        newres_working_timesat_switch_btn.setEnabled(false);
                        newres_working_timesat_switch_btn.setChecked(false);

                        newres_working_timesun_switch_btn.setEnabled(false);
                        newres_working_timesun_switch_btn.setChecked(false);
                        break;
                    case Calendar.FRIDAY:
                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timemon_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timetue_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timewed_switch_btn.setChecked(false);

                        newres_working_timethr_switch_btn.setEnabled(false);
                        newres_working_timethr_switch_btn.setChecked(false);

                        newres_working_timefri_switch_btn.setEnabled(true);
                        newres_working_timefri_switch_btn.setChecked(true);
                        newres_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                        newres_working_timefri_end_btn.setText(businessHourData.getFri_end_time());

                        newres_working_timesat_switch_btn.setEnabled(false);
                        newres_working_timesat_switch_btn.setChecked(false);

                        newres_working_timesun_switch_btn.setEnabled(false);
                        newres_working_timesun_switch_btn.setChecked(false);
                        break;

                    case Calendar.SATURDAY:
                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timemon_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timetue_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timewed_switch_btn.setChecked(false);

                        newres_working_timethr_switch_btn.setEnabled(false);
                        newres_working_timethr_switch_btn.setChecked(false);

                        newres_working_timefri_switch_btn.setEnabled(false);
                        newres_working_timefri_switch_btn.setChecked(false);

                        newres_working_timesat_switch_btn.setEnabled(true);
                        newres_working_timesat_switch_btn.setChecked(true);
                        newres_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                        newres_working_timesat_end_btn.setText(businessHourData.getSat_end_time());

                        newres_working_timesun_switch_btn.setEnabled(false);
                        newres_working_timesun_switch_btn.setChecked(false);
                        break;
                    case Calendar.SUNDAY:
                        newres_working_timetue_switch_btn.setEnabled(false);
                        newres_working_timemon_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timetue_switch_btn.setChecked(false);

                        newres_working_timewed_switch_btn.setEnabled(false);
                        newres_working_timewed_switch_btn.setChecked(false);

                        newres_working_timethr_switch_btn.setEnabled(false);
                        newres_working_timethr_switch_btn.setChecked(false);

                        newres_working_timefri_switch_btn.setEnabled(false);
                        newres_working_timefri_switch_btn.setChecked(false);

                        newres_working_timesat_switch_btn.setEnabled(false);
                        newres_working_timesat_switch_btn.setChecked(false);

                        newres_working_timesun_switch_btn.setEnabled(true);
                        newres_working_timesun_switch_btn.setChecked(true);
                        newres_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                        newres_working_timesun_end_btn.setText(businessHourData.getSun_end_time());

                        break;


                }


            }
        }
        else{*/
            newres_working_timemon_switch_btn.setEnabled(true);
            newres_working_timemon_switch_btn.setChecked(true);

            newres_working_timetue_switch_btn.setEnabled(true);
            newres_working_timetue_switch_btn.setChecked(true);

            newres_working_timewed_switch_btn.setEnabled(true);
            newres_working_timewed_switch_btn.setChecked(true);

            newres_working_timethr_switch_btn.setEnabled(true);
            newres_working_timethr_switch_btn.setChecked(true);

            newres_working_timefri_switch_btn.setEnabled(true);
            newres_working_timefri_switch_btn.setChecked(true);

            newres_working_timesat_switch_btn.setEnabled(true);
            newres_working_timesat_switch_btn.setChecked(true);

            newres_working_timesun_switch_btn.setEnabled(true);
            newres_working_timesun_switch_btn.setChecked(true);

            // set start and end date
            if (businessHourData.getDate_start().equals(businessHourData.getDate_end())){
                newres_working_date_start_btn.setText(businessHourData.getDate_start());
                newres_working_date_end_btn.setText(businessHourData.getDate_end());
                newres_working_date_switch_btn.setChecked(false);
                newres_working_date_switch_btn.setEnabled(false                                                                                                                                                                                                                          );
            }
            else{
                newres_working_date_start_btn.setText(businessHourData.getDate_start());
                newres_working_date_end_btn.setText(businessHourData.getDate_end());
                newres_working_date_switch_btn.setChecked(true);
            }


            // set Mon Start and End Time
            if (businessHourData.getMon_start_time().equals("")||
                    businessHourData.getMon_end_time().equals("")){
                newres_working_timemon_switch_btn.setChecked(false);
            }
            else{


                newres_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                newres_working_timemon_end_btn.setText(businessHourData.getMon_end_time());}


            // set Tue Start and End Time
            if (businessHourData.getTue_start_time().equals("")||
                    businessHourData.getTue_end_time().equals("")){
                newres_working_timetue_switch_btn.setChecked(false);
            }
            else{

                newres_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                newres_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


            }

            // set Wed Start and End Time
            if (businessHourData.getWed_start_time().equals("")||businessHourData.getWed_end_time().equals("")){
                newres_working_timewed_switch_btn.setChecked(false);
            }
            else{

                newres_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                newres_working_timewed_end_btn.setText(businessHourData.getWed_end_time());}


            // set Thru Start and End Time
            if (businessHourData.getThr_start_time().equals("")||businessHourData.getThr_end_time().equals("")){
                newres_working_timethr_switch_btn.setChecked(false);
            }
            else{

                newres_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                newres_working_timethr_end_btn.setText(businessHourData.getThr_end_time());}


            // set Fri Start and End Time
            if (businessHourData.getFri_start_time().equals("")||
                    businessHourData.getFri_end_time().equals("")){
                newres_working_timefri_switch_btn.setChecked(false);
            }
            else{

                newres_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                newres_working_timefri_end_btn.setText(businessHourData.getFri_end_time());}


            // set Sat Start and End Time
            if (businessHourData.getSat_start_time().equals("")||
                    businessHourData.getSat_end_time().equals("")){
                newres_working_timesat_switch_btn.setChecked(false);
            }
            else{

                newres_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                newres_working_timesat_end_btn.setText(businessHourData.getSat_end_time());}


            // set Sun Start and End Time
            if (businessHourData.getSun_start_time().equals("")||
                    businessHourData.getSun_end_time().equals("")){
                newres_working_timesun_switch_btn.setChecked(false);
            }
            else{

                newres_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                newres_working_timesun_end_btn.setText(businessHourData.getSun_end_time());}


        }



        else{
            newres_working_timemon_switch_btn.setEnabled(true);
            newres_working_timemon_switch_btn.setChecked(true);

            newres_working_timetue_switch_btn.setEnabled(true);
            newres_working_timetue_switch_btn.setChecked(true);


            newres_working_timewed_switch_btn.setEnabled(true);
            newres_working_timewed_switch_btn.setChecked(true);

            newres_working_timethr_switch_btn.setEnabled(true);
            newres_working_timethr_switch_btn.setChecked(true);

            newres_working_timefri_switch_btn.setEnabled(true);
            newres_working_timefri_switch_btn.setChecked(true);

            newres_working_timesat_switch_btn.setEnabled(true);
            newres_working_timesat_switch_btn.setChecked(true);

            newres_working_timesun_switch_btn.setEnabled(true);
            newres_working_timesun_switch_btn.setChecked(true);

            // set start and end date
            if (res_data.getStart_date().equals(res_data.getEnd_date())){
                newres_working_date_start_btn.setText(res_data.getStart_date());
                newres_working_date_end_btn.setText(res_data.getEnd_date());
                newres_working_date_switch_btn.setChecked(false);
                newres_working_date_switch_btn.setEnabled(false);
            }
            else{
                newres_working_date_start_btn.setText(res_data.getStart_date());
                newres_working_date_end_btn.setText(res_data.getEnd_date());
                newres_working_date_switch_btn.setChecked(true);
            }


            // set Mon Start and End Time
            if (res_data.getMon_from_time().equals("")||res_data.getMon_to_time().equals("")){
                newres_working_timemon_switch_btn.setChecked(false);

                if (businessHourData.getMon_start_time().equals("")||businessHourData.getMon_end_time().equals("")||businessHourData.getMon_start_time().equals("null")||businessHourData.getMon_end_time().equals("null")){
                    newres_working_timemon_switch_btn.setChecked(false);
                    newres_working_timemon_switch_btn.setEnabled(false);

                }
            }
            else{

                newres_working_timemon_start_btn.setText(res_data.getMon_from_time());
                newres_working_timemon_end_btn.setText(res_data.getMon_to_time());
            }

            // set Tue Start and End Time
            if (res_data.getTue_from_time().equals("")||res_data.getTue_to_time().equals("")){
                newres_working_timetue_switch_btn.setChecked(false);
            }
            else{
                newres_working_timetue_start_btn.setText(res_data.getTue_from_time());
                newres_working_timetue_end_btn.setText(res_data.getTue_to_time());
            }

            // set Wed Start and End Time
            if (res_data.getWed_from_time().equals("")||res_data.getWed_to_time().equals("")){
                newres_working_timewed_switch_btn.setChecked(false);
            }
            else{
                newres_working_timewed_start_btn.setText(res_data.getWed_from_time());
                newres_working_timewed_end_btn.setText(res_data.getWed_to_time());
            }

            // set Thru Start and End Time
            if (res_data.getThru_from_time().equals("")||res_data.getThru_to_time().equals("")){
                newres_working_timethr_switch_btn.setChecked(false);
            }
            else{
                newres_working_timethr_start_btn.setText(res_data.getThru_from_time());
                newres_working_timethr_end_btn.setText(res_data.getThru_to_time());
            }

            // set Fri Start and End Time
            if (res_data.getFri_from_time().equals("")||res_data.getFri_to_time().equals("")){
                newres_working_timefri_switch_btn.setChecked(false);
            }
            else{
                newres_working_timefri_start_btn.setText(res_data.getFri_from_time());
                newres_working_timefri_end_btn.setText(res_data.getFri_to_time());
            }

            // set Sat Start and End Time
            if (res_data.getSat_from_time().equals("")||res_data.getSat_to_time().equals("")){
                newres_working_timesat_switch_btn.setChecked(false);
            }
            else{
                newres_working_timesat_start_btn.setText(res_data.getSat_from_time());
                newres_working_timesat_end_btn.setText(res_data.getSat_to_time());
            }

            // set Sun Start and End Time
            if (res_data.getSun_from_time().equals("")||res_data.getSun_to_time().equals("")){
                newres_working_timesun_switch_btn.setChecked(false);
            }
            else{
                newres_working_timesun_start_btn.setText(res_data.getSun_from_time());
                newres_working_timesun_end_btn.setText(res_data.getSun_to_time());
            }


        }






        return  Mroot;

    }


    @OnClick(R.id.newres_working_next_btn)
    void goNext(){
        // save data in res array

        // date start and End
        if (newres_working_date_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setStart_date(newres_working_date_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setEnd_date(newres_working_date_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setStart_date(newres_working_date_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setEnd_date(newres_working_date_start_btn.getText().toString());
        }

        // Mon start and End Time
        if (newres_working_timemon_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setMon_from_time(newres_working_timemon_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setMon_to_time(newres_working_timemon_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setMon_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setMon_to_time("");
        }

        // Tue start and End Time
        if (newres_working_timetue_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setTue_from_time(newres_working_timetue_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setTue_to_time(newres_working_timetue_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setTue_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setTue_to_time("");
        }

        // Wed start and End Time
        if (newres_working_timewed_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setWed_from_time(newres_working_timewed_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setWed_to_time(newres_working_timewed_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setWed_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setWed_to_time("");
        }

        // thr start and End Time
        if (newres_working_timethr_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setThru_from_time(newres_working_timethr_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setThru_to_time(newres_working_timethr_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setThru_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setThru_to_time("");
        }

        // fri start and End Time
        if (newres_working_timefri_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setFri_from_time(newres_working_timefri_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setFri_to_time(newres_working_timefri_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setFri_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setFri_to_time("");
        }

        // Sat start and End Time
        if (newres_working_timesat_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSat_from_time(newres_working_timesat_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSat_to_time(newres_working_timesat_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSat_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSat_to_time("");
        }


        // Sun start and End Time
        if (newres_working_timesun_switch_btn.isChecked()){
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSun_from_time(newres_working_timesun_start_btn.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSun_to_time(newres_working_timesun_end_btn.getText().toString());
        }else{
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSun_from_time("");
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setSun_to_time("");
        }

        LandingActivity.res = true;
        Bundle _bundle =   new Bundle();
        _bundle.putString("src",bundle.getString("src"));
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));

        replaceFrag(new NewResourceInfoBFrag(),_bundle , BusinessHourFrag.class.getName());
    }









    @OnClick(R.id.newres_working_back_btn)

    void goBack(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.newres_working_date_start_btn)
    void getstartDate(){
        // set calender visible date in between business start and end date
        AppUtils.getDatewithCallback(newres_working_date_start_btn , getActivity(),businessHourData.getDate_start(), businessHourData.getDate_end() , callback );


    }

    @OnClick(R.id.newres_working_date_end_btn)
    void getendDate(){
        // set calender visible date in between business start and end date
        if (newres_working_date_switch_btn.isChecked()){
            AppUtils.getDatewithCallback(newres_working_date_end_btn , getActivity(),businessHourData.getDate_start(), businessHourData.getDate_end() , callback);
        }
        else{

        }

    }

    // mon time click listener
    @OnClick(R.id.newres_working_timemon_start_btn)
    void getmonStartTime(){
        getTimestaff(newres_working_timemon_start_btn,
                businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());

    }
    @OnClick(R.id.newres_working_timemon_end_btn)
    void getmonEndTime(){
        getTimestaff(newres_working_timemon_end_btn,
                businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());


    }

    // tue time click listener
    @OnClick(R.id.newres_working_timetue_start_btn)
    void gettueStartTime(){
        getTimestaff(newres_working_timetue_start_btn,
                businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());

    }
    @OnClick(R.id.newres_working_timetue_end_btn)
    void gettueEndTime(){
        getTimestaff(newres_working_timetue_end_btn,
                businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());


    }

    // wed time click listener
    @OnClick(R.id.newres_working_timewed_start_btn)
    void getwedStartTime(){
        getTimestaff(newres_working_timewed_start_btn,
                businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());



    }
    @OnClick(R.id.newres_working_timewed_end_btn)
    void getwedEndTime(){
        getTimestaff(newres_working_timewed_end_btn,
                businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());



    }

    // thr time click listener
    @OnClick(R.id.newres_working_timethr_start_btn)
    void getthrStartTime(){
        getTimestaff(newres_working_timethr_start_btn,
                businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());



    }
    @OnClick(R.id.newres_working_timethr_end_btn)
    void getthrEndTime(){
        getTimestaff(newres_working_timethr_end_btn,
                businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());


    }

    // fri time click listener
    @OnClick(R.id.newres_working_timefri_start_btn)
    void getfriStartTime(){
        getTimestaff(newres_working_timefri_start_btn,
                businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());


    }
    @OnClick(R.id.newres_working_timefri_end_btn)
    void getfriEndTime(){
        getTimestaff(newres_working_timefri_end_btn,
                businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());


    }

    // sat time click listener
    @OnClick(R.id.newres_working_timesat_start_btn)
    void getsatStartTime(){

        getTimestaff(newres_working_timesat_start_btn,
                businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());


    }
    @OnClick(R.id.newres_working_timesat_end_btn)
    void getsatEndTime(){
        getTimestaff(newres_working_timesat_end_btn,
                businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());


    }

    // sun time click listener
    @OnClick(R.id.newres_working_timesun_start_btn)
    void getsunStartTime(){
        getTimestaff(newres_working_timesun_start_btn,
                businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());


    }
    @OnClick(R.id.newres_working_timesun_end_btn)
    void getsunEndTime(){


        getTimestaff(newres_working_timesun_end_btn,
                businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());


    }




    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (compoundButton.getId()== R.id.newres_working_date_switch_btn){

            if (b){
                newres_working_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                callback.getResponse("Date");

            }
            else{
                newres_working_date_end_btn.setTextColor(getActivity().getResources().getColor(R.color.dark_grey));
                newres_working_timemon_switch_btn.setEnabled(true);
                newres_working_timemon_switch_btn.setChecked(true);

                newres_working_timetue_switch_btn.setEnabled(true);
                newres_working_timetue_switch_btn.setChecked(true);

                newres_working_timewed_switch_btn.setEnabled(true);
                newres_working_timewed_switch_btn.setChecked(true);


                newres_working_timethr_switch_btn.setEnabled(true);
                newres_working_timethr_switch_btn.setChecked(true);

                newres_working_timefri_switch_btn.setEnabled(true);
                newres_working_timefri_switch_btn.setChecked(true);

                newres_working_timesat_switch_btn.setEnabled(true);
                newres_working_timesat_switch_btn.setChecked(true);

                newres_working_timesun_switch_btn.setEnabled(true);
                newres_working_timesun_switch_btn.setChecked(true);
            }

        }

        else if (compoundButton.getId()== R.id.newres_working_timesun_switch_btn){
            if (b){
                newres_working_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{
                newres_working_timesun_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timesun_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        }

        else if (compoundButton.getId()== R.id.newres_working_timemon_switch_btn){
            if (b){
                newres_working_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{
                newres_working_timemon_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timemon_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        }

        else if (compoundButton.getId()== R.id.newres_working_timetue_switch_btn){
            if (b){
                newres_working_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{
                newres_working_timetue_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timetue_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        }

        else if (compoundButton.getId()== R.id.newres_working_timewed_switch_btn){
            if (b){
                newres_working_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{
                newres_working_timewed_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timewed_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        }

        else if (compoundButton.getId()== R.id.newres_working_timethr_switch_btn){
            if (b){
                newres_working_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{
                newres_working_timethr_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timethr_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        }

        else if (compoundButton.getId()== R.id.newres_working_timefri_switch_btn){
            if (b){
                newres_working_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{
                newres_working_timefri_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timefri_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
            }
        }

        else if (compoundButton.getId()== R.id.newres_working_timesat_switch_btn){
            if (b){

                newres_working_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.black));
                newres_working_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
            else{

                newres_working_timesat_start_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));
                newres_working_timesat_end_btn.setTextColor(getActivity().getResources().getColor(R.color.grey));

            }
        }

    }

    @Override
    public void getResponse(String response) {
        if (!newres_working_date_end_btn.getText().toString().equals("End date")&&newres_working_date_switch_btn.isChecked()) {


            ArrayList<String> day_array = getListDate(newres_working_date_start_btn.getText().toString(), newres_working_date_end_btn.getText().toString());
            String day = "";
            boolean mon = false, tue = false, wed = false, thr = false, fri = false, sat = false, sun = false;

            if (day_array.size() == 0) {

            } else {

                newres_working_timemon_switch_btn.setEnabled(false);
                newres_working_timemon_switch_btn.setChecked(false);

                newres_working_timetue_switch_btn.setEnabled(false);
                newres_working_timemon_switch_btn.setChecked(false);

                newres_working_timewed_switch_btn.setEnabled(false);
                newres_working_timetue_switch_btn.setChecked(false);

                newres_working_timewed_switch_btn.setEnabled(false);
                newres_working_timewed_switch_btn.setChecked(false);

                newres_working_timethr_switch_btn.setEnabled(false);
                newres_working_timethr_switch_btn.setChecked(false);

                newres_working_timefri_switch_btn.setEnabled(false);
                newres_working_timefri_switch_btn.setChecked(false);

                newres_working_timesat_switch_btn.setEnabled(false);
                newres_working_timesat_switch_btn.setChecked(false);

                newres_working_timesun_switch_btn.setEnabled(false);
                newres_working_timesun_switch_btn.setChecked(false);
            }

            for (int i = 0; i < day_array.size(); i++) {

                day += day_array.get(i);

                Log.e("daytwo", day);


            }
            if (day.contains("Mon")) {


                newres_working_timemon_switch_btn.setEnabled(true);
                newres_working_timemon_switch_btn.setChecked(true);
                newres_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                newres_working_timemon_end_btn.setText(businessHourData.getMon_end_time());


            }

            if (day.contains("Tue")) {


                newres_working_timetue_switch_btn.setEnabled(true);
                newres_working_timetue_switch_btn.setChecked(true);
                newres_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                newres_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


            }
            if (day.contains("Wed")) {


                newres_working_timewed_switch_btn.setEnabled(true);
                newres_working_timewed_switch_btn.setChecked(true);
                newres_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                newres_working_timewed_end_btn.setText(businessHourData.getWed_end_time());


            }
            if (day.contains("Thu")) {


                newres_working_timethr_switch_btn.setEnabled(true);
                newres_working_timethr_switch_btn.setChecked(true);
                newres_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                newres_working_timethr_end_btn.setText(businessHourData.getThr_end_time());


            }

            if (day.contains("Fri")) {


                newres_working_timefri_switch_btn.setEnabled(true);
                newres_working_timefri_switch_btn.setChecked(true);
                newres_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                newres_working_timefri_end_btn.setText(businessHourData.getFri_end_time());


            }
            if (day.contains("Sat")) {


                newres_working_timesat_switch_btn.setEnabled(true);
                newres_working_timesat_switch_btn.setChecked(true);
                newres_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                newres_working_timesat_end_btn.setText(businessHourData.getSat_end_time());

            }
            if (day.contains("Sun")) {


                newres_working_timesun_switch_btn.setEnabled(true);
                newres_working_timesun_switch_btn.setChecked(true);
                newres_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                newres_working_timesun_end_btn.setText(businessHourData.getSun_end_time());


            }

        }
        else{

            newres_working_timemon_switch_btn.setEnabled(false);
            newres_working_timemon_switch_btn.setChecked(false);

            newres_working_timetue_switch_btn.setEnabled(false);
            newres_working_timemon_switch_btn.setChecked(false);

            newres_working_timewed_switch_btn.setEnabled(false);
            newres_working_timetue_switch_btn.setChecked(false);

            newres_working_timewed_switch_btn.setEnabled(false);
            newres_working_timewed_switch_btn.setChecked(false);

            newres_working_timethr_switch_btn.setEnabled(false);
            newres_working_timethr_switch_btn.setChecked(false);

            newres_working_timefri_switch_btn.setEnabled(false);
            newres_working_timefri_switch_btn.setChecked(false);

            newres_working_timesat_switch_btn.setEnabled(false);
            newres_working_timesat_switch_btn.setChecked(false);

            newres_working_timesun_switch_btn.setEnabled(false);
            newres_working_timesun_switch_btn.setChecked(false);


            Date date = new Date();
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = date_format.parse(newres_working_date_start_btn.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);// Note that Month value is 0-based. e.g., 0 for January.

            int reslut = calendar.get(Calendar.DAY_OF_WEEK);

            Log.e("day" , reslut+"") ;


            switch (reslut) {

                case Calendar.MONDAY:
                    newres_working_timemon_switch_btn.setEnabled(true);
                    newres_working_timemon_switch_btn.setChecked(true);

                    newres_working_timemon_start_btn.setText(businessHourData.getMon_start_time());
                    newres_working_timemon_end_btn.setText(businessHourData.getMon_end_time());



                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                    break;

                case Calendar.TUESDAY:
                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timetue_switch_btn.setEnabled(true);
                    newres_working_timetue_switch_btn.setChecked(true);
                    newres_working_timetue_start_btn.setText(businessHourData.getTue_start_time());
                    newres_working_timetue_end_btn.setText(businessHourData.getTue_end_time());


                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                    break;

                case Calendar.WEDNESDAY:
                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(true);
                    newres_working_timewed_switch_btn.setChecked(true);
                    newres_working_timewed_start_btn.setText(businessHourData.getWed_start_time());
                    newres_working_timewed_end_btn.setText(businessHourData.getWed_end_time());



                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                    break;
                case Calendar.THURSDAY:
                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(true);
                    newres_working_timethr_switch_btn.setChecked(true);

                    newres_working_timethr_start_btn.setText(businessHourData.getThr_start_time());
                    newres_working_timethr_end_btn.setText(businessHourData.getThr_end_time());


                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                    break;
                case Calendar.FRIDAY:
                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(true);
                    newres_working_timefri_switch_btn.setChecked(true);
                    newres_working_timefri_start_btn.setText(businessHourData.getFri_start_time());
                    newres_working_timefri_end_btn.setText(businessHourData.getFri_end_time());

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                    break;

                case Calendar.SATURDAY:
                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(true);
                    newres_working_timesat_switch_btn.setChecked(true);
                    newres_working_timesat_start_btn.setText(businessHourData.getSat_start_time());
                    newres_working_timesat_end_btn.setText(businessHourData.getSat_end_time());

                    newres_working_timesun_switch_btn.setEnabled(false);
                    newres_working_timesun_switch_btn.setChecked(false);
                    break;
                case Calendar.SUNDAY:
                    newres_working_timetue_switch_btn.setEnabled(false);
                    newres_working_timemon_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timetue_switch_btn.setChecked(false);

                    newres_working_timewed_switch_btn.setEnabled(false);
                    newres_working_timewed_switch_btn.setChecked(false);

                    newres_working_timethr_switch_btn.setEnabled(false);
                    newres_working_timethr_switch_btn.setChecked(false);

                    newres_working_timefri_switch_btn.setEnabled(false);
                    newres_working_timefri_switch_btn.setChecked(false);

                    newres_working_timesat_switch_btn.setEnabled(false);
                    newres_working_timesat_switch_btn.setChecked(false);

                    newres_working_timesun_switch_btn.setEnabled(true);
                    newres_working_timesun_switch_btn.setChecked(true);
                    newres_working_timesun_start_btn.setText(businessHourData.getSun_start_time());
                    newres_working_timesun_end_btn.setText(businessHourData.getSun_end_time());

                    break;


            }




        }
    }
}
