package frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import models.BusinessData;
import models.BusinessHourData;
import models.StaffData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/4/2017.
 */

public class NewStaffBreakTimeFrag extends HelperFrags {

    private View Mroot;


   // all day widgets
   @BindView(R.id.new_staff_breaktime_all_break_btn)
   TextView new_staff_breaktime_all_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_all_breaklayout1)
    RelativeLayout new_staff_breaktime_all_breaklayout1;

    @BindView(R.id.new_staff_breaktime_all_starttime_btn1)
    TextView new_staff_breaktime_all_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_all_endtime_btn1)
    TextView new_staff_breaktime_all_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_all_breaklayout2)
    RelativeLayout new_staff_breaktime_all_breaklayout2;

    @BindView(R.id.new_staff_breaktime_all_starttime_btn2)
    TextView new_staff_breaktime_all_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_all_endtime_btn2)
    TextView new_staff_breaktime_all_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_all_breaklayout3)
    RelativeLayout new_staff_breaktime_all_breaklayout3;

    @BindView(R.id.new_staff_breaktime_all_starttime_btn3)
    TextView new_staff_breaktime_all_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_all_endtime_btn3)
    TextView new_staff_breaktime_all_endtime_btn3;


    // 4th break
    @BindView(R.id.new_staff_breaktime_all_breaklayout4)
    RelativeLayout new_staff_breaktime_all_breaklayout4;

    @BindView(R.id.new_staff_breaktime_all_starttime_btn4)
    TextView new_staff_breaktime_all_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_all_endtime_btn4)
    TextView new_staff_breaktime_all_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_all_breaklayout5)
    RelativeLayout new_staff_breaktime_all_breaklayout5;

    @BindView(R.id.new_staff_breaktime_all_starttime_btn5)
    TextView new_staff_breaktime_all_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_all_endtime_btn5)
    TextView new_staff_breaktime_all_endtime_btn5;




    // Mon day widgets
    @BindView(R.id.new_staff_breaktime_mon_break_btn)
    TextView new_staff_breaktime_mon_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_mon_breaklayout1)
    RelativeLayout new_staff_breaktime_mon_breaklayout1;

    @BindView(R.id.new_staff_breaktime_mon_starttime_btn1)
    TextView new_staff_breaktime_mon_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_mon_endtime_btn1)
    TextView new_staff_breaktime_mon_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_mon_breaklayout2)
    RelativeLayout new_staff_breaktime_mon_breaklayout2;

    @BindView(R.id.new_staff_breaktime_mon_starttime_btn2)
    TextView new_staff_breaktime_mon_starttime_btn2;

    @BindView(R.id.new_staff_breaktimemon_endtime_btn2)
    TextView new_staff_breaktime_mon_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_mon_breaklayout3)
    RelativeLayout new_staff_breaktime_mon_breaklayout3;

    @BindView(R.id.new_staff_breaktime_mon_starttime_btn3)
    TextView new_staff_breaktime_mon_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_mon_endtime_btn3)
    TextView new_staff_breaktime_mon_endtime_btn3;



    // 4th break
    @BindView(R.id.new_staff_breaktime_mon_breaklayout4)
    RelativeLayout new_staff_breaktime_mon_breaklayout4;

    @BindView(R.id.new_staff_breaktime_mon_starttime_btn4)
    TextView new_staff_breaktime_mon_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_mon_endtime_btn4)
    TextView new_staff_breaktime_mon_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_mon_breaklayout5)
    RelativeLayout new_staff_breaktime_mon_breaklayout5;

    @BindView(R.id.new_staff_breaktime_mon_starttime_btn5)
    TextView new_staff_breaktime_mon_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_mon_endtime_btn5)
    TextView new_staff_breaktime_mon_endtime_btn5;




    // Tue day widgets
    @BindView(R.id.new_staff_breaktime_tue_break_btn)
    TextView new_staff_breaktime_tue_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_tue_breaklayout1)
    RelativeLayout new_staff_breaktime_tue_breaklayout1;

    @BindView(R.id.new_staff_breaktime_tue_starttime_btn1)
    TextView new_staff_breaktime_tue_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_tue_endtime_btn1)
    TextView new_staff_breaktime_tue_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_tue_breaklayout2)
    RelativeLayout new_staff_breaktime_tue_breaklayout2;

    @BindView(R.id.new_staff_breaktime_tue_starttime_btn2)
    TextView new_staff_breaktime_tue_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_tue_endtime_btn2)
    TextView new_staff_breaktime_tue_endtime_btn2;

    // 3rd break
    @BindView(R.id.new_staff_breaktime_tue_breaklayout3)
    RelativeLayout new_staff_breaktime_tue_breaklayout3;

    @BindView(R.id.new_staff_breaktime_tue_starttime_btn3)
    TextView new_staff_breaktime_tue_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_tue_endtime_btn3)
    TextView new_staff_breaktime_tue_endtime_btn3;



    // 4th break
    @BindView(R.id.new_staff_breaktime_tue_breaklayout4)
    RelativeLayout new_staff_breaktime_tue_breaklayout4;

    @BindView(R.id.new_staff_breaktime_tue_starttime_btn4)
    TextView new_staff_breaktime_tue_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_tue_endtime_btn4)
    TextView new_staff_breaktime_tue_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_tue_breaklayout5)
    RelativeLayout new_staff_breaktime_tue_breaklayout5;

    @BindView(R.id.new_staff_breaktime_tue_starttime_btn5)
    TextView new_staff_breaktime_tue_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_tue_endtime_btn5)
    TextView new_staff_breaktime_tue_endtime_btn5;





    // wed day widgets
    @BindView(R.id.new_staff_breaktime_wed_break_btn)
    TextView new_staff_breaktime_wed_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_wed_breaklayout1)
    RelativeLayout new_staff_breaktime_wed_breaklayout1;

    @BindView(R.id.new_staff_breaktime_wed_starttime_btn1)
    TextView new_staff_breaktime_wed_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_wed_endtime_btn1)
    TextView new_staff_breaktime_wed_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_wed_breaklayout2)
    RelativeLayout new_staff_breaktime_wed_breaklayout2;

    @BindView(R.id.new_staff_breaktime_wed_starttime_btn2)
    TextView new_staff_breaktime_wed_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_wed_endtime_btn2)
    TextView new_staff_breaktime_wed_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_wed_breaklayout3)
    RelativeLayout new_staff_breaktime_wed_breaklayout3;

    @BindView(R.id.new_staff_breaktime_wed_starttime_btn3)
    TextView new_staff_breaktime_wed_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_wed_endtime_btn3)
    TextView new_staff_breaktime_wed_endtime_btn3;



    // 4th break
    @BindView(R.id.new_staff_breaktime_wed_breaklayout4)
    RelativeLayout new_staff_breaktime_wed_breaklayout4;

    @BindView(R.id.new_staff_breaktime_wed_starttime_btn4)
    TextView new_staff_breaktime_wed_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_wed_endtime_btn4)
    TextView new_staff_breaktime_wed_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_wed_breaklayout5)
    RelativeLayout new_staff_breaktime_wed_breaklayout5;

    @BindView(R.id.new_staff_breaktime_wed_starttime_btn5)
    TextView new_staff_breaktime_wed_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_wed_endtime_btn5)
    TextView new_staff_breaktime_wed_endtime_btn5;




    // thr day widgets
    @BindView(R.id.new_staff_breaktime_Thr_break_btn)
    TextView new_staff_breaktime_thr_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_Thr_breaklayout1)
    RelativeLayout new_staff_breaktime_Thr_breaklayout1;

    @BindView(R.id.new_staff_breaktime_Thr_starttime_btn1)
    TextView new_staff_breaktime_Thr_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_Thr_endtime_btn1)
    TextView new_staff_breaktime_Thr_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_Thr_breaklayout2)
    RelativeLayout new_staff_breaktime_Thr_breaklayout2;

    @BindView(R.id.new_staff_breaktime_Thr_starttime_btn2)
    TextView new_staff_breaktime_Thr_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_Thr_endtime_btn2)
    TextView new_staff_breaktime_Thr_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_Thr_breaklayout3)
    RelativeLayout new_staff_breaktime_Thr_breaklayout3;

    @BindView(R.id.new_staff_breaktime_Thr_starttime_btn3)
    TextView new_staff_breaktime_Thr_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_Thr_endtime_btn3)
    TextView new_staff_breaktime_Thr_endtime_btn3;



    // 4th break
    @BindView(R.id.new_staff_breaktime_Thr_breaklayout4)
    RelativeLayout new_staff_breaktime_Thr_breaklayout4;

    @BindView(R.id.new_staff_breaktime_Thr_starttime_btn4)
    TextView new_staff_breaktime_Thr_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_Thr_endtime_btn4)
    TextView new_staff_breaktime_Thr_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_Thr_breaklayout5)
    RelativeLayout new_staff_breaktime_Thr_breaklayout5;

    @BindView(R.id.new_staff_breaktime_Thr_starttime_btn5)
    TextView new_staff_breaktime_Thr_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_Thr_endtime_btn5)
    TextView new_staff_breaktime_Thr_endtime_btn5;





    // fri day widgets
    @BindView(R.id.new_staff_breaktime_fri_break_btn)
    TextView new_staff_breaktime_fri_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_fri_breaklayout1)
    RelativeLayout new_staff_breaktime_fri_breaklayout1;

    @BindView(R.id.new_staff_breaktime_fri_starttime_btn1)
    TextView new_staff_breaktime_fri_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_fri_endtime_btn1)
    TextView new_staff_breaktime_fri_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_fri_breaklayout2)
    RelativeLayout new_staff_breaktime_fri_breaklayout2;

    @BindView(R.id.new_staff_breaktime_fri_starttime_btn2)
    TextView new_staff_breaktime_fri_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_fri_endtime_btn2)
    TextView new_staff_breaktime_fri_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_fri_breaklayout3)
    RelativeLayout new_staff_breaktime_fri_breaklayout3;

    @BindView(R.id.new_staff_breaktime_fri_starttime_btn3)
    TextView new_staff_breaktime_fri_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_fri_endtime_btn3)
    TextView new_staff_breaktime_fri_endtime_btn3;



    // 4th break
    @BindView(R.id.new_staff_breaktime_fri_breaklayout4)
    RelativeLayout new_staff_breaktime_fri_breaklayout4;

    @BindView(R.id.new_staff_breaktime_fri_starttime_btn4)
    TextView new_staff_breaktime_fri_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_fri_endtime_btn4)
    TextView new_staff_breaktime_fri_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_fri_breaklayout5)
    RelativeLayout new_staff_breaktime_fri_breaklayout5;

    @BindView(R.id.new_staff_breaktime_fri_starttime_btn5)
    TextView new_staff_breaktime_fri_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_fri_endtime_btn5)
    TextView new_staff_breaktime_fri_endtime_btn5;




    // sat day widgets
    @BindView(R.id.new_staff_breaktime_sat_break_btn)
    TextView new_staff_breaktime_sat_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_sat_breaklayout1)
    RelativeLayout new_staff_breaktime_sat_breaklayout1;

    @BindView(R.id.new_staff_breaktime_sat_starttime_btn1)
    TextView new_staff_breaktime_sat_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_sat_endtime_btn1)
    TextView new_staff_breaktime_sat_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_sat_breaklayout2)
    RelativeLayout new_staff_breaktime_sat_breaklayout2;

    @BindView(R.id.new_staff_breaktime_sat_starttime_btn2)
    TextView new_staff_breaktime_sat_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_sat_endtime_btn2)
    TextView new_staff_breaktime_sat_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_sat_breaklayout3)
    RelativeLayout new_staff_breaktime_sat_breaklayout3;

    @BindView(R.id.new_staff_breaktime_sat_starttime_btn3)
    TextView new_staff_breaktime_sat_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_sat_endtime_btn3)
    TextView new_staff_breaktime_sat_endtime_btn3;



    // 4th break
    @BindView(R.id.new_staff_breaktime_sat_breaklayout4)
    RelativeLayout new_staff_breaktime_sat_breaklayout4;

    @BindView(R.id.new_staff_breaktime_sat_starttime_btn4)
    TextView new_staff_breaktime_sat_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_sat_endtime_btn4)
    TextView new_staff_breaktime_sat_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_sat_breaklayout5)
    RelativeLayout new_staff_breaktime_sat_breaklayout5;

    @BindView(R.id.new_staff_breaktime_sat_starttime_btn5)
    TextView new_staff_breaktime_sat_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_sat_endtime_btn5)
    TextView new_staff_breaktime_sat_endtime_btn5;





    // sun day widgets
    @BindView(R.id.new_staff_breaktime_sun_break_btn)
    TextView new_staff_breaktime_sun_break_btn;
    // 1st break
    @BindView(R.id.new_staff_breaktime_sun_breaklayout1)
    RelativeLayout new_staff_breaktime_sun_breaklayout1;

    @BindView(R.id.new_staff_breaktime_sun_starttime_btn1)
    TextView new_staff_breaktime_sun_starttime_btn1;

    @BindView(R.id.new_staff_breaktime_sun_endtime_btn1)
    TextView new_staff_breaktime_sun_endtime_btn1;


    // 2nd break
    @BindView(R.id.new_staff_breaktime_sun_breaklayout2)
    RelativeLayout new_staff_breaktime_sun_breaklayout2;

    @BindView(R.id.new_staff_breaktime_sun_starttime_btn2)
    TextView new_staff_breaktime_sun_starttime_btn2;

    @BindView(R.id.new_staff_breaktime_sun_endtime_btn2)
    TextView new_staff_breaktime_sun_endtime_btn2;


    // 3rd break
    @BindView(R.id.new_staff_breaktime_sun_breaklayout3)
    RelativeLayout new_staff_breaktime_sun_breaklayout3;

    @BindView(R.id.new_staff_breaktime_sun_starttime_btn3)
    TextView new_staff_breaktime_sun_starttime_btn3;

    @BindView(R.id.new_staff_breaktime_sun_endtime_btn3)
    TextView new_staff_breaktime_sun_endtime_btn3;


    // 4th break
    @BindView(R.id.new_staff_breaktime_sun_breaklayout4)
    RelativeLayout new_staff_breaktime_sun_breaklayout4;

    @BindView(R.id.new_staff_breaktime_sun_starttime_btn4)
    TextView new_staff_breaktime_sun_starttime_btn4;

    @BindView(R.id.new_staff_breaktime_sun_endtime_btn4)
    TextView new_staff_breaktime_sun_endtime_btn4;



    // 5th break
    @BindView(R.id.new_staff_breaktime_sun_breaklayout5)
    RelativeLayout new_staff_breaktime_sun_breaklayout5;

    @BindView(R.id.new_staff_breaktime_sun_starttime_btn5)
    TextView new_staff_breaktime_sun_starttime_btn5;

    @BindView(R.id.new_staff_breaktime_sun_endtime_btn5)
    TextView new_staff_breaktime_sun_endtime_btn5;



      private Bundle bundle;

      private StaffData staffData ;

       private BusinessHourData businessHourData;






 @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newstaff_breaktime_screen, null);
        ButterKnife.bind(this, Mroot);


         bundle = getArguments();

         staffData = LandingActivity.staff_data_array.get(bundle.getInt("pos"));

          businessHourData = LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos"));


  new_staff_breaktime_all_break_btn.setTag(0);

/*
             if (bundle.getString("src").equals("def")) {



              // all days break button set tag
             new_staff_breaktime_all_break_btn.setTag(0);
              // mon days break button set tag
              new_staff_breaktime_mon_break_btn.setTag(0);
              // tue days break button set tag
              new_staff_breaktime_tue_break_btn.setTag(0);
              // wed days break button set tag
              new_staff_breaktime_wed_break_btn.setTag(0);
              // thr days break button set tag
              new_staff_breaktime_thr_break_btn.setTag(0);
              // fri days break button set tag
              new_staff_breaktime_fri_break_btn.setTag(0);
              // sat days break button set tag
              new_staff_breaktime_sat_break_btn.setTag(0);
              // sun days break button set tag
              new_staff_breaktime_sun_break_btn.setTag(0);

             }

     else
             {*/


              Log.e("id",staffData.getAll_break1_from());

              // check all day BREAK 1
              if (staffData.getAll_break1_from().equals("")||staffData.getAll_break1_to().equals("")){
               new_staff_breaktime_all_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_all_starttime_btn1.setText(staffData.getAll_break1_from());
               new_staff_breaktime_all_endtime_btn1.setText(staffData.getAll_break1_to());
               new_staff_breaktime_all_break_btn.setTag(1);
               new_staff_breaktime_all_breaklayout1.setVisibility(View.VISIBLE);
              }

              // check all day BREAK 2
              if (staffData.getAll_break2_from().equals("")
                      ||staffData.getAll_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_all_starttime_btn2.setText(staffData.getAll_break2_from());
               new_staff_breaktime_all_endtime_btn2.setText(staffData.getAll_break2_to());
               new_staff_breaktime_all_break_btn.setTag(2);
               new_staff_breaktime_all_breaklayout2.setVisibility(View.VISIBLE);
              }

              // check all day BREAK 3
              if (staffData.getAll_break3_from().equals("")||staffData.getAll_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_all_starttime_btn3.setText(staffData.getAll_break3_from());
               new_staff_breaktime_all_endtime_btn3.setText(staffData.getAll_break3_to());
               new_staff_breaktime_all_break_btn.setTag(3);
               new_staff_breaktime_all_breaklayout3.setVisibility(View.VISIBLE);
              }


              // check all day BREAK 4
              if (staffData.getAll_break4_from().equals("")||staffData.getAll_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_all_starttime_btn4.setText(staffData.getAll_break4_from());
               new_staff_breaktime_all_endtime_btn4.setText(staffData.getAll_break4_to());
               new_staff_breaktime_all_break_btn.setTag(4);
               new_staff_breaktime_all_breaklayout4.setVisibility(View.VISIBLE);
              }

              // check all day BREAK 5
              if (staffData.getAll_break5_from().equals("")||staffData.getAll_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_all_starttime_btn5.setText(staffData.getAll_break5_from());
               new_staff_breaktime_all_endtime_btn5.setText(staffData.getAll_break5_to());
               new_staff_breaktime_all_break_btn.setTag(5);
               new_staff_breaktime_all_breaklayout5.setVisibility(View.VISIBLE);
              }



              // check Mon day BREAK 1
              if (staffData.getMon_break1_from().equals("")||staffData.getMon_break1_to().equals("")){
               new_staff_breaktime_mon_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_mon_starttime_btn1.setText(staffData.getMon_break1_from());
               new_staff_breaktime_mon_endtime_btn1.setText(staffData.getMon_break1_to());
               new_staff_breaktime_mon_break_btn.setTag(1);
               new_staff_breaktime_mon_breaklayout1.setVisibility(View.VISIBLE);
              }

              // check Mon day BREAK 2
              if (staffData.getMon_break2_from().equals("")||staffData.getMon_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_mon_starttime_btn2.setText(staffData.getMon_break2_from());
               new_staff_breaktime_mon_endtime_btn2.setText(staffData.getMon_break2_to());
               new_staff_breaktime_mon_break_btn.setTag(2);
               new_staff_breaktime_mon_breaklayout2.setVisibility(View.VISIBLE);
              }

              // check Mon day BREAK 3
              if (staffData.getMon_break3_from().equals("")||staffData.getMon_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_mon_starttime_btn3.setText(staffData.getMon_break3_from());
               new_staff_breaktime_mon_endtime_btn3.setText(staffData.getMon_break3_to());
               new_staff_breaktime_mon_break_btn.setTag(3);
               new_staff_breaktime_mon_breaklayout3.setVisibility(View.VISIBLE);
              }


              // check Mon day BREAK 4
              if (staffData.getMon_break4_from().equals("")||staffData.getMon_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_mon_starttime_btn4.setText(staffData.getMon_break4_from());
               new_staff_breaktime_mon_endtime_btn4.setText(staffData.getMon_break4_to());
               new_staff_breaktime_mon_break_btn.setTag(4);
               new_staff_breaktime_mon_breaklayout4.setVisibility(View.VISIBLE);
              }

              // check Mon day BREAK 5
              if (staffData.getMon_break5_from().equals("")||staffData.getMon_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_mon_starttime_btn5.setText(staffData.getMon_break5_from());
               new_staff_breaktime_mon_endtime_btn5.setText(staffData.getMon_break5_to());
               new_staff_breaktime_mon_break_btn.setTag(5);
               new_staff_breaktime_mon_breaklayout5.setVisibility(View.VISIBLE);
              }




              // check Tue day BREAK 1
              if (staffData.getTue_break1_from().equals("")||staffData.getTue_break1_to().equals("")){
               new_staff_breaktime_tue_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_tue_starttime_btn1.setText(staffData.getTue_break1_from());
               new_staff_breaktime_tue_endtime_btn1.setText(staffData.getTue_break1_to());
               new_staff_breaktime_tue_break_btn.setTag(1);
               new_staff_breaktime_tue_breaklayout1.setVisibility(View.VISIBLE);
              }

              // check Tue day BREAK 2
              if (staffData.getTue_break2_from().equals("")||staffData.getTue_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_tue_starttime_btn2.setText(staffData.getTue_break2_from());
               new_staff_breaktime_tue_endtime_btn2.setText(staffData.getTue_break2_to());
               new_staff_breaktime_tue_break_btn.setTag(2);
               new_staff_breaktime_tue_breaklayout2.setVisibility(View.VISIBLE);
              }

              // check Tue day BREAK 3
              if (staffData.getTue_break3_from().equals("")||staffData.getTue_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_tue_starttime_btn3.setText(staffData.getTue_break3_from());
               new_staff_breaktime_tue_endtime_btn3.setText(staffData.getTue_break3_to());
               new_staff_breaktime_tue_break_btn.setTag(3);
               new_staff_breaktime_tue_breaklayout3.setVisibility(View.VISIBLE);
              }

              // check Tue day BREAK 4
              if (staffData.getTue_break4_from().equals("")||staffData.getTue_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_tue_starttime_btn4.setText(staffData.getTue_break4_from());
               new_staff_breaktime_tue_endtime_btn4.setText(staffData.getTue_break4_to());
               new_staff_breaktime_tue_break_btn.setTag(4);
               new_staff_breaktime_tue_breaklayout4.setVisibility(View.VISIBLE);
              }

              // check Tue day BREAK 5
              if (staffData.getTue_break5_from().equals("")||staffData.getTue_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_tue_starttime_btn5.setText(staffData.getTue_break5_from());
               new_staff_breaktime_tue_endtime_btn5.setText(staffData.getTue_break5_to());
               new_staff_breaktime_tue_break_btn.setTag(5);
               new_staff_breaktime_tue_breaklayout5.setVisibility(View.VISIBLE);
              }



              // check Wed day BREAK 1
              if (staffData.getWed_break1_from().equals("")||staffData.getWed_break1_to().equals("")){
                  new_staff_breaktime_wed_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_wed_starttime_btn1.setText(staffData.getWed_break1_from());
               new_staff_breaktime_wed_endtime_btn1.setText(staffData.getWed_break1_to());
               new_staff_breaktime_wed_break_btn.setTag(1);
               new_staff_breaktime_wed_breaklayout1.setVisibility(View.VISIBLE);
              }


              // check Wed day BREAK 2
              if (staffData.getWed_break2_from().equals("")||staffData.getWed_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_wed_starttime_btn2.setText(staffData.getWed_break2_from());
               new_staff_breaktime_wed_endtime_btn2.setText(staffData.getWed_break2_to());
               new_staff_breaktime_wed_break_btn.setTag(2);
                new_staff_breaktime_wed_breaklayout2.setVisibility(View.VISIBLE);
              }


              // check Wed day BREAK 3
              if (staffData.getWed_break3_from().equals("")||staffData.getWed_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_wed_starttime_btn3.setText(staffData.getWed_break3_from());
               new_staff_breaktime_wed_endtime_btn3.setText(staffData.getWed_break3_to());
               new_staff_breaktime_wed_break_btn.setTag(3);
               new_staff_breaktime_wed_breaklayout3.setVisibility(View.VISIBLE);
              }


              // check Wed day BREAK 4
              if (staffData.getWed_break4_from().equals("")||staffData.getWed_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_wed_starttime_btn4.setText(staffData.getWed_break4_from());
               new_staff_breaktime_wed_endtime_btn4.setText(staffData.getWed_break4_to());
               new_staff_breaktime_wed_break_btn.setTag(4);
               new_staff_breaktime_wed_breaklayout4.setVisibility(View.VISIBLE);
              }


              // check Wed day BREAK 5
              if (staffData.getWed_break5_from().equals("")||staffData.getWed_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_wed_starttime_btn5.setText(staffData.getWed_break5_from());
               new_staff_breaktime_wed_endtime_btn5.setText(staffData.getWed_break5_to());
               new_staff_breaktime_wed_break_btn.setTag(5);
               new_staff_breaktime_wed_breaklayout5.setVisibility(View.VISIBLE);
              }


              // check Thr day BREAK 1
              if (staffData.getThru_break1_from().equals("")||staffData.getThru_break1_to().equals("")){
               new_staff_breaktime_thr_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_Thr_starttime_btn1.setText(staffData.getThru_break1_from());
               new_staff_breaktime_Thr_endtime_btn1.setText(staffData.getThru_break1_to());
               new_staff_breaktime_thr_break_btn.setTag(1);
               new_staff_breaktime_Thr_breaklayout1.setVisibility(View.VISIBLE);
              }


              // check Thr day BREAK 2
              if (staffData.getThru_break2_from().equals("")||staffData.getThru_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_Thr_starttime_btn2.setText(staffData.getThru_break2_from());
               new_staff_breaktime_Thr_endtime_btn2.setText(staffData.getThru_break2_to());
               new_staff_breaktime_thr_break_btn.setTag(2);
               new_staff_breaktime_Thr_breaklayout2.setVisibility(View.VISIBLE);
              }

              // check Thr day BREAK 3
              if (staffData.getThru_break3_from().equals("")||staffData.getThru_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_Thr_starttime_btn3.setText(staffData.getThru_break3_from());
               new_staff_breaktime_Thr_endtime_btn3.setText(staffData.getThru_break3_to());
               new_staff_breaktime_thr_break_btn.setTag(3);
               new_staff_breaktime_Thr_breaklayout3.setVisibility(View.VISIBLE);
              }


              // check Thr day BREAK 4
              if (staffData.getThru_break4_from().equals("")||staffData.getThru_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_Thr_starttime_btn4.setText(staffData.getThru_break4_from());
               new_staff_breaktime_Thr_endtime_btn4.setText(staffData.getThru_break4_to());
               new_staff_breaktime_thr_break_btn.setTag(4);
               new_staff_breaktime_Thr_breaklayout4.setVisibility(View.VISIBLE);
              }

              // check Thr day BREAK 5
              if (staffData.getThru_break5_from().equals("")||staffData.getThru_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_Thr_starttime_btn5.setText(staffData.getThru_break5_from());
               new_staff_breaktime_Thr_endtime_btn5.setText(staffData.getThru_break5_to());
               new_staff_breaktime_thr_break_btn.setTag(5);
               new_staff_breaktime_Thr_breaklayout5.setVisibility(View.VISIBLE);
              }



              // check Fri day BREAK 1
              if (staffData.getFri_break1_from().equals("")||staffData.getFri_break1_to().equals("")){
                  new_staff_breaktime_fri_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_fri_starttime_btn1.setText(staffData.getFri_break1_from());
               new_staff_breaktime_fri_endtime_btn1.setText(staffData.getFri_break1_to());
               new_staff_breaktime_fri_break_btn.setTag(1);
               new_staff_breaktime_fri_breaklayout1.setVisibility(View.VISIBLE);
              }

              // check Fri day BREAK 2
              if (staffData.getFri_break2_from().equals("")||staffData.getFri_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_fri_starttime_btn2.setText(staffData.getFri_break2_from());
               new_staff_breaktime_fri_endtime_btn2.setText(staffData.getFri_break2_to());
               new_staff_breaktime_fri_break_btn.setTag(2);
               new_staff_breaktime_fri_breaklayout2.setVisibility(View.VISIBLE);
              }

              // check Fri day BREAK 3
              if (staffData.getFri_break3_from().equals("")||staffData.getFri_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_fri_starttime_btn3.setText(staffData.getFri_break3_from());
               new_staff_breaktime_fri_endtime_btn3.setText(staffData.getFri_break3_to());
               new_staff_breaktime_fri_break_btn.setTag(3);
               new_staff_breaktime_fri_breaklayout3.setVisibility(View.VISIBLE);
              }


              // check Fri day BREAK 4
              if (staffData.getFri_break4_from().equals("")||staffData.getFri_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_fri_starttime_btn4.setText(staffData.getFri_break4_from());
               new_staff_breaktime_fri_endtime_btn4.setText(staffData.getFri_break4_to());
               new_staff_breaktime_fri_break_btn.setTag(4);
               new_staff_breaktime_fri_breaklayout4.setVisibility(View.VISIBLE);
              }

              // check Fri day BREAK 5
              if (staffData.getFri_break5_from().equals("")||staffData.getFri_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_fri_starttime_btn5.setText(staffData.getFri_break5_from());
               new_staff_breaktime_fri_endtime_btn5.setText(staffData.getFri_break5_to());
               new_staff_breaktime_fri_break_btn.setTag(5);
               new_staff_breaktime_fri_breaklayout5.setVisibility(View.VISIBLE);
              }



              // check Sat day BREAK 1
              if (staffData.getSat_break1_from().equals("")||staffData.getSat_break1_to().equals("")){
               new_staff_breaktime_sat_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_sat_starttime_btn1.setText(staffData.getSat_break1_from());
               new_staff_breaktime_sat_endtime_btn1.setText(staffData.getSat_break1_to());
               new_staff_breaktime_sat_break_btn.setTag(1);
               new_staff_breaktime_sat_breaklayout1.setVisibility(View.VISIBLE);
              }

              // check Sat day BREAK 2
              if (staffData.getSat_break2_from().equals("")||staffData.getSat_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_sat_starttime_btn2.setText(staffData.getSat_break2_from());
               new_staff_breaktime_sat_endtime_btn2.setText(staffData.getSat_break2_to());
               new_staff_breaktime_sat_break_btn.setTag(2);
               new_staff_breaktime_sat_breaklayout2.setVisibility(View.VISIBLE);
              }

              // check Sat day BREAK 3
              if (staffData.getSat_break3_from().equals("")||staffData.getSat_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_sat_starttime_btn3.setText(staffData.getSat_break3_from());
               new_staff_breaktime_sat_endtime_btn3.setText(staffData.getSat_break3_to());
               new_staff_breaktime_sat_break_btn.setTag(3);
               new_staff_breaktime_sat_breaklayout3.setVisibility(View.VISIBLE);
              }

              // check Sat day BREAK 4
              if (staffData.getSat_break4_from().equals("")||staffData.getSat_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_sat_starttime_btn4.setText(staffData.getSat_break4_from());
               new_staff_breaktime_sat_endtime_btn4.setText(staffData.getSat_break4_to());
               new_staff_breaktime_sat_break_btn.setTag(4);
               new_staff_breaktime_sat_breaklayout4.setVisibility(View.VISIBLE);
              }

              // check Sat day BREAK 5
              if (staffData.getSat_break5_from().equals("")||staffData.getSat_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_sat_starttime_btn5.setText(staffData.getSat_break5_from());
               new_staff_breaktime_sat_endtime_btn5.setText(staffData.getSat_break5_to());
               new_staff_breaktime_sat_break_btn.setTag(5);
               new_staff_breaktime_sat_breaklayout5.setVisibility(View.VISIBLE);
              }


              // check Sun day BREAK 1
              if (staffData.getSun_break1_from().equals("")||staffData.getSun_break1_to().equals("")){
               new_staff_breaktime_sun_break_btn.setTag(0);
              }
              else{
               new_staff_breaktime_sun_starttime_btn1.setText(staffData.getSun_break1_from());
               new_staff_breaktime_sun_endtime_btn1.setText(staffData.getSun_break1_to());
               new_staff_breaktime_sun_break_btn.setTag(1);
               new_staff_breaktime_sun_breaklayout1.setVisibility(View.VISIBLE);
              }

              // check Sun day BREAK 2
              if (staffData.getSun_break2_from().equals("")||staffData.getSun_break2_to().equals("")){

              }
              else{
               new_staff_breaktime_sun_starttime_btn2.setText(staffData.getSun_break2_from());
               new_staff_breaktime_sun_endtime_btn2.setText(staffData.getSun_break2_to());
               new_staff_breaktime_sun_break_btn.setTag(2);
               new_staff_breaktime_sun_breaklayout2.setVisibility(View.VISIBLE);

              }

              // check Sun day BREAK 3
              if (staffData.getSun_break3_from().equals("")||staffData.getSun_break3_to().equals("")){

              }
              else{
               new_staff_breaktime_sun_starttime_btn3.setText(staffData.getSun_break3_from());
               new_staff_breaktime_sun_endtime_btn3.setText(staffData.getSun_break3_to());
               new_staff_breaktime_sun_break_btn.setTag(3);
               new_staff_breaktime_sun_breaklayout3.setVisibility(View.VISIBLE);

              }

              // check Sun day BREAK 4
              if (staffData.getSun_break4_from().equals("")||staffData.getSun_break4_to().equals("")){

              }
              else{
               new_staff_breaktime_sun_starttime_btn4.setText(staffData.getSun_break4_from());
               new_staff_breaktime_sun_endtime_btn4.setText(staffData.getSun_break4_to());
               new_staff_breaktime_sun_break_btn.setTag(4);
               new_staff_breaktime_sun_breaklayout4.setVisibility(View.VISIBLE);

              }

              // check Sun day BREAK 5
              if (staffData.getSun_break5_from().equals("")||staffData.getSun_break5_to().equals("")){

              }
              else{
               new_staff_breaktime_sun_starttime_btn5.setText(staffData.getSun_break5_from());
               new_staff_breaktime_sun_endtime_btn5.setText(staffData.getSun_break5_to());
               new_staff_breaktime_sun_break_btn.setTag(5);
               new_staff_breaktime_sun_breaklayout5.setVisibility(View.VISIBLE);

              }



             /*}*/








     return Mroot;
    }

    // back button
    @OnClick(R.id.new_staff_breaktime_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    // save button
    @OnClick(R.id.new_staff_breaktime_next_btn)
    void save(){

    /* // check all day option checked or not
     if (new_staff_breaktime_all_breaklayout1.isShown()||new_staff_breaktime_all_breaklayout2.isShown()
             ||new_staff_breaktime_all_breaklayout3.isShown()||new_staff_breaktime_all_breaklayout4.isShown()
             ||new_staff_breaktime_all_breaklayout5.isShown()){
*/

      // All day break time 1
      if (new_staff_breaktime_all_breaklayout1.isShown()){
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break1_from(new_staff_breaktime_all_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break1_to(new_staff_breaktime_all_endtime_btn1.getText().toString());
      }else{
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break1_to("");
      }

      // All day break time 2
      if (new_staff_breaktime_all_breaklayout2.isShown()){
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break2_from(new_staff_breaktime_all_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break2_to(new_staff_breaktime_all_endtime_btn2.getText().toString());
      }else{
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break2_to("");
      }

      // All day break time 3
      if (new_staff_breaktime_all_breaklayout3.isShown()){
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break3_from(new_staff_breaktime_all_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break3_to(new_staff_breaktime_all_endtime_btn3.getText().toString());
      }else{
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break3_to("");
      }

      // All day break time 4
      if (new_staff_breaktime_all_breaklayout4.isShown()){
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break4_from(new_staff_breaktime_all_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break4_to(new_staff_breaktime_all_endtime_btn4.getText().toString());
      }else{
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break4_to("");
      }

      // All day break time 5
      if (new_staff_breaktime_all_breaklayout5.isShown()){
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break5_from(new_staff_breaktime_all_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break5_to(new_staff_breaktime_all_endtime_btn5.getText().toString());
      }else{
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAll_break5_to("");
      }




     /*}
     else {*/


      // save data
      // Mon day break time 1
      if (new_staff_breaktime_mon_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break1_from(new_staff_breaktime_mon_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break1_to(new_staff_breaktime_mon_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break1_to("");
      }

      // Mon day break time 2
      if (new_staff_breaktime_mon_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break2_from(new_staff_breaktime_mon_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break2_to(new_staff_breaktime_mon_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break2_to("");
      }

      // Mon day break time 3
      if (new_staff_breaktime_mon_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break3_from(new_staff_breaktime_mon_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break3_to(new_staff_breaktime_mon_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break3_to("");
      }

      // Mon day break time 4
      if (new_staff_breaktime_mon_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break4_from(new_staff_breaktime_mon_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break4_to(new_staff_breaktime_mon_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break4_to("");
      }

      // Mon day break time 5
      if (new_staff_breaktime_mon_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break5_from(new_staff_breaktime_mon_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break5_to(new_staff_breaktime_mon_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setMon_break5_to("");
      }

      // Tue day break time 1
      if (new_staff_breaktime_tue_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break1_from(new_staff_breaktime_tue_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break1_to(new_staff_breaktime_tue_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break1_to("");
      }

      // Tue day break time 2
      if (new_staff_breaktime_tue_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break2_from(new_staff_breaktime_tue_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break2_to(new_staff_breaktime_tue_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break2_to("");
      }

      // Tue day break time 3
      if (new_staff_breaktime_tue_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break3_from(new_staff_breaktime_tue_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break3_to(new_staff_breaktime_tue_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break3_to("");
      }

      // Tue day break time 4
      if (new_staff_breaktime_tue_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break4_from(new_staff_breaktime_tue_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break4_to(new_staff_breaktime_tue_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break4_to("");
      }

      // Tue day break time 5
      if (new_staff_breaktime_tue_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break5_from(new_staff_breaktime_tue_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break5_to(new_staff_breaktime_tue_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setTue_break5_to("");
      }

      // Wed day break time 1
      if (new_staff_breaktime_wed_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break1_from(new_staff_breaktime_wed_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break1_to(new_staff_breaktime_wed_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break1_to("");
      }

      // Wed day break time 2
      if (new_staff_breaktime_wed_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break2_from(new_staff_breaktime_wed_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break2_to(new_staff_breaktime_wed_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break2_to("");
      }

      // Wed day break time 3
      if (new_staff_breaktime_wed_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break3_from(new_staff_breaktime_wed_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break3_to(new_staff_breaktime_wed_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break3_to("");
      }


      // Wed day break time 4
      if (new_staff_breaktime_wed_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break4_from(new_staff_breaktime_wed_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break4_to(new_staff_breaktime_wed_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break4_to("");
      }

      // Wed day break time 5
      if (new_staff_breaktime_wed_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break5_from(new_staff_breaktime_wed_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break5_to(new_staff_breaktime_wed_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setWed_break5_to("");
      }

      // Thr day break time 1
      if (new_staff_breaktime_Thr_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break1_from(new_staff_breaktime_Thr_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break1_to(new_staff_breaktime_Thr_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break1_to("");
      }

      // Thr day break time 2
      if (new_staff_breaktime_Thr_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break2_from(new_staff_breaktime_Thr_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break2_to(new_staff_breaktime_Thr_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break2_to("");
      }

      // Thr day break time 3
      if (new_staff_breaktime_Thr_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break3_from(new_staff_breaktime_Thr_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break3_to(new_staff_breaktime_Thr_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break3_to("");
      }

      // Thr day break time 3
      if (new_staff_breaktime_Thr_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break4_from(new_staff_breaktime_Thr_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break4_to(new_staff_breaktime_Thr_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break4_to("");
      }

      // Thr day break time 5
      if (new_staff_breaktime_Thr_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break5_from(new_staff_breaktime_Thr_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break5_to(new_staff_breaktime_Thr_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setThru_break5_to("");
      }


      // Fri day break time 1
      if (new_staff_breaktime_fri_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break1_from(new_staff_breaktime_fri_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break1_to(new_staff_breaktime_fri_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break1_to("");
      }

      // Fri day break time 2
      if (new_staff_breaktime_fri_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break2_from(new_staff_breaktime_fri_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break2_to(new_staff_breaktime_fri_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break2_to("");
      }

      // Fri day break time 3
      if (new_staff_breaktime_fri_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break3_from(new_staff_breaktime_fri_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break3_to(new_staff_breaktime_fri_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break3_to("");
      }

      // Fri day break time 4
      if (new_staff_breaktime_fri_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break4_from(new_staff_breaktime_fri_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break4_to(new_staff_breaktime_fri_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break4_to("");
      }

      // Fri day break time 5
      if (new_staff_breaktime_fri_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break5_from(new_staff_breaktime_fri_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break5_to(new_staff_breaktime_fri_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFri_break5_to("");
      }

      // Sat day break time 1
      if (new_staff_breaktime_sat_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break1_from(new_staff_breaktime_sat_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break1_to(new_staff_breaktime_sat_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break1_to("");
      }

      // Sat day break time 2
      if (new_staff_breaktime_sat_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break2_from(new_staff_breaktime_sat_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break2_to(new_staff_breaktime_sat_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break2_to("");
      }

      // Sat day break time 3
      if (new_staff_breaktime_sat_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break3_from(new_staff_breaktime_sat_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break3_to(new_staff_breaktime_sat_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break3_to("");
      }

      // Sat day break time 4
      if (new_staff_breaktime_sat_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break4_from(new_staff_breaktime_sat_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break4_to(new_staff_breaktime_sat_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break4_to("");
      }

      // Sat day break time 5
      if (new_staff_breaktime_sat_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break5_from(new_staff_breaktime_sat_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break5_to(new_staff_breaktime_sat_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSat_break5_to("");
      }

      // Sun day break time 1
      if (new_staff_breaktime_sun_breaklayout1.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break1_from(new_staff_breaktime_sun_starttime_btn1.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break1_to(new_staff_breaktime_sun_endtime_btn1.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break1_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break1_to("");
      }

      // Sun day break time 2
      if (new_staff_breaktime_sun_breaklayout2.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break2_from(new_staff_breaktime_sun_starttime_btn2.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break2_to(new_staff_breaktime_sun_endtime_btn2.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break2_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break2_to("");
      }

      // Sun day break time 3
      if (new_staff_breaktime_sun_breaklayout3.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break3_from(new_staff_breaktime_sun_starttime_btn3.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break3_to(new_staff_breaktime_sun_endtime_btn3.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break3_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break3_to("");
      }

      // Sun day break time 4
      if (new_staff_breaktime_sun_breaklayout4.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break4_from(new_staff_breaktime_sun_starttime_btn4.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break4_to(new_staff_breaktime_sun_endtime_btn4.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break4_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break4_to("");
      }

      // Sun day break time 5
      if (new_staff_breaktime_sun_breaklayout5.isShown()) {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break5_from(new_staff_breaktime_sun_starttime_btn5.getText().toString());
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break5_to(new_staff_breaktime_sun_endtime_btn5.getText().toString());
      } else {
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break5_from("");
       LandingActivity.staff_data_array.get(bundle.getInt("pos")).setSun_break5_to("");
      }


     /*}*/

     Bundle _bundle =   new Bundle();
     _bundle.putString("src",bundle.getString("src"));
     _bundle.putInt("pos",bundle.getInt("pos"));

     replaceFrag(new NewStaffInfoFrag(), _bundle , NewStaffBreakTimeFrag.class.getName());
    }




    // all days add break event
    @OnClick(R.id.new_staff_breaktime_all_break_btn)
    void all_add(){
        if (new_staff_breaktime_all_break_btn.getTag()==(Object)0){
            new_staff_breaktime_all_breaklayout1.setVisibility(View.VISIBLE);
            new_staff_breaktime_all_break_btn.setTag(1);
        }
        else if (new_staff_breaktime_all_break_btn.getTag()==(Object)1){
            new_staff_breaktime_all_breaklayout2.setVisibility(View.VISIBLE);
            new_staff_breaktime_all_break_btn.setTag(2);
        }
        else if (new_staff_breaktime_all_break_btn.getTag()==(Object)2){
            new_staff_breaktime_all_breaklayout3.setVisibility(View.VISIBLE);
            new_staff_breaktime_all_break_btn.setTag(3);
        }
        else if (new_staff_breaktime_all_break_btn.getTag()==(Object)3){
            new_staff_breaktime_all_breaklayout4.setVisibility(View.VISIBLE);
            new_staff_breaktime_all_break_btn.setTag(4);
        }
        else if (new_staff_breaktime_all_break_btn.getTag()==(Object)4){
            new_staff_breaktime_all_breaklayout5.setVisibility(View.VISIBLE);
         new_staff_breaktime_all_break_btn.setTag(5);
        }
        else if (new_staff_breaktime_all_break_btn.getTag()==(Object)5){
            Snackbar snackbar = Snackbar
                    .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
    }


    // all days delete break event1
    @OnClick(R.id.all_close_icn1)
      void close1(){
        new_staff_breaktime_all_breaklayout1.setVisibility(View.GONE);
        new_staff_breaktime_all_break_btn.setTag(0);
    }

    // all days delete break event2
    @OnClick(R.id.all_close_icn2)
    void close2(){
        new_staff_breaktime_all_breaklayout2.setVisibility(View.GONE);
        new_staff_breaktime_all_break_btn.setTag(1);
    }


    // all days delete break event3
    @OnClick(R.id.all_close_icn3)
    void close3(){
        new_staff_breaktime_all_breaklayout3.setVisibility(View.GONE);
        new_staff_breaktime_all_break_btn.setTag(2);
    }


    // all days delete break event4
    @OnClick(R.id.all_close_icn4)
    void close4(){
        new_staff_breaktime_all_breaklayout4.setVisibility(View.GONE);
        new_staff_breaktime_all_break_btn.setTag(3);
    }

    // all days delete break event5
    @OnClick(R.id.all_close_icn5)
    void close5(){
        new_staff_breaktime_all_breaklayout5.setVisibility(View.GONE);
        new_staff_breaktime_all_break_btn.setTag(4);
    }


 // Mon days add break event
 @OnClick(R.id.new_staff_breaktime_mon_break_btn)
 void all_mon() {

  if (businessHourData.getMon_start_time().equals("") ||
          businessHourData.getMon_start_time().equals("null")
          || businessHourData.getMon_end_time().equals("") ||
          businessHourData.getMon_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for monday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_mon_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_mon_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_mon_break_btn.setTag(1);
   } else if (new_staff_breaktime_mon_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_mon_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_mon_break_btn.setTag(2);
   } else if (new_staff_breaktime_mon_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_mon_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_mon_break_btn.setTag(3);
   } else if (new_staff_breaktime_mon_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_mon_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_mon_break_btn.setTag(4);
   } else if (new_staff_breaktime_mon_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_mon_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_mon_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }
  }

 }


 // Mon days delete break event1
 @OnClick(R.id.mon_close_icn1)
 void mon1(){
  new_staff_breaktime_mon_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_mon_break_btn.setTag(0);
 }

 // mon days delete break event2
 @OnClick(R.id.mon_close_icn2)
 void mon2(){
  new_staff_breaktime_mon_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_mon_break_btn.setTag(1);
 }


 // mon days delete break event3
 @OnClick(R.id.mon_close_icn3)
 void mon3(){
  new_staff_breaktime_mon_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_mon_break_btn.setTag(2);
 }


 // mon days delete break event4
 @OnClick(R.id.mon_close_icn4)
 void mon4(){
  new_staff_breaktime_mon_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_mon_break_btn.setTag(3);
 }

 // mon days delete break event5
 @OnClick(R.id.mon_close_icn5)
 void mon5(){
  new_staff_breaktime_mon_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_mon_break_btn.setTag(4);
 }





 // tue days add break event
 @OnClick(R.id.new_staff_breaktime_tue_break_btn)
 void all_tue() {

  if (businessHourData.getTue_start_time().equals("") ||
          businessHourData.getTue_start_time().equals("null") ||
          businessHourData.getTue_end_time().equals("") ||
          businessHourData.getTue_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for tuesday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_tue_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_tue_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_tue_break_btn.setTag(1);
   } else if (new_staff_breaktime_tue_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_tue_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_tue_break_btn.setTag(2);
   } else if (new_staff_breaktime_tue_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_tue_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_tue_break_btn.setTag(3);
   } else if (new_staff_breaktime_tue_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_tue_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_tue_break_btn.setTag(4);
   } else if (new_staff_breaktime_tue_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_tue_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_tue_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }
  }

 }

 // tue days delete break event1
 @OnClick(R.id.tue_close_icn1)
 void tue1(){
  new_staff_breaktime_tue_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_tue_break_btn.setTag(0);
 }

 // tue days delete break event2
 @OnClick(R.id.tue_close_icn2)
 void tue2(){
  new_staff_breaktime_tue_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_tue_break_btn.setTag(1);
 }


 // tue days delete break event3
 @OnClick(R.id.tue_close_icn3)
 void tue3(){
  new_staff_breaktime_tue_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_tue_break_btn.setTag(2);
 }


 // tue days delete break event4
 @OnClick(R.id.tue_close_icn4)
 void tue4(){
  new_staff_breaktime_tue_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_tue_break_btn.setTag(3);
 }

 // tue days delete break event5
 @OnClick(R.id.tue_close_icn5)
 void tue5(){
  new_staff_breaktime_tue_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_tue_break_btn.setTag(4);
 }




 // wed days add break event
 @OnClick(R.id.new_staff_breaktime_wed_break_btn)
 void wed_tue() {

  if (businessHourData.getWed_start_time().equals("")
          || businessHourData.getWed_start_time().equals("null") ||
          businessHourData.getWed_end_time().equals("") ||
          businessHourData.getWed_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for wednesday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_wed_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_wed_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_wed_break_btn.setTag(1);
   } else if (new_staff_breaktime_wed_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_wed_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_wed_break_btn.setTag(2);
   } else if (new_staff_breaktime_wed_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_wed_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_wed_break_btn.setTag(3);
   } else if (new_staff_breaktime_wed_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_wed_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_wed_break_btn.setTag(4);
   } else if (new_staff_breaktime_wed_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_wed_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_wed_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }
  }
 }

 // wed days delete break event1
 @OnClick(R.id.wed_close_icn1)
 void wed1(){
  new_staff_breaktime_wed_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_wed_break_btn.setTag(0);
 }

 // wed days delete break event2
 @OnClick(R.id.wed_close_icn2)
 void wed2(){
  new_staff_breaktime_wed_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_wed_break_btn.setTag(1);
 }


 // wed days delete break event3
 @OnClick(R.id.wed_close_icn3)
 void wed3(){
  new_staff_breaktime_wed_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_wed_break_btn.setTag(2);
 }


 // wed days delete break event4
 @OnClick(R.id.wed_close_icn4)
 void wed4(){
  new_staff_breaktime_wed_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_wed_break_btn.setTag(3);
 }

 // wed days delete break event5
 @OnClick(R.id.wed_close_icn5)
 void wed5(){
  new_staff_breaktime_wed_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_wed_break_btn.setTag(4);
 }


 // thr days add break event
 @OnClick(R.id.new_staff_breaktime_Thr_break_btn)
 void thr_tue() {


  if (businessHourData.getThr_start_time().equals("") ||
          businessHourData.getThr_start_time().equals("null") ||
          businessHourData.getThr_end_time().equals("") ||
          businessHourData.getThr_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for thursday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_thr_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_Thr_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_thr_break_btn.setTag(1);
   } else if (new_staff_breaktime_thr_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_Thr_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_thr_break_btn.setTag(2);
   } else if (new_staff_breaktime_thr_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_Thr_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_thr_break_btn.setTag(3);
   } else if (new_staff_breaktime_thr_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_Thr_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_thr_break_btn.setTag(4);
   } else if (new_staff_breaktime_thr_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_Thr_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_thr_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }
  }
 }

 // thr days delete break event1
 @OnClick(R.id.thr_close_icn1)
 void thr1(){
  new_staff_breaktime_Thr_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_thr_break_btn.setTag(0);
 }

 // thr days delete break event2
 @OnClick(R.id.thr_close_icn2)
 void thr2(){
  new_staff_breaktime_Thr_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_thr_break_btn.setTag(1);
 }


 // wed days delete break event3
 @OnClick(R.id.thr_close_icn3)
 void thr3(){
  new_staff_breaktime_Thr_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_thr_break_btn.setTag(2);
 }


 // thr days delete break event4
 @OnClick(R.id.thr_close_icn4)
 void thr4(){
  new_staff_breaktime_Thr_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_thr_break_btn.setTag(3);
 }

 // thr days delete break event5
 @OnClick(R.id.thr_close_icn5)
 void thr5(){
  new_staff_breaktime_Thr_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_thr_break_btn.setTag(4);
 }



 // fri days add break event
 @OnClick(R.id.new_staff_breaktime_fri_break_btn)
 void fri() {

  if (businessHourData.getFri_start_time().equals("") ||
          businessHourData.getFri_start_time().equals("null") ||
          businessHourData.getFri_end_time().equals("") ||
          businessHourData.getFri_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for friday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_fri_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_fri_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_fri_break_btn.setTag(1);
   } else if (new_staff_breaktime_fri_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_fri_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_fri_break_btn.setTag(2);
   } else if (new_staff_breaktime_fri_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_fri_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_fri_break_btn.setTag(3);
   } else if (new_staff_breaktime_fri_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_fri_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_fri_break_btn.setTag(4);
   } else if (new_staff_breaktime_fri_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_fri_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_fri_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }
  }

 }


 // fri days delete break event1
 @OnClick(R.id.fri_close_icn1)
 void fri1(){
  new_staff_breaktime_fri_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_fri_break_btn.setTag(0);
 }

 // fri days delete break event2
 @OnClick(R.id.fri_close_icn2)
 void fri2(){
  new_staff_breaktime_fri_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_fri_break_btn.setTag(1);
 }


 // fri days delete break event3
 @OnClick(R.id.fri_close_icn3)
 void fri3(){
  new_staff_breaktime_Thr_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_thr_break_btn.setTag(2);
 }


 // fri days delete break event4
 @OnClick(R.id.fri_close_icn4)
 void fri4(){
  new_staff_breaktime_fri_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_fri_break_btn.setTag(3);
 }

 // fri days delete break event5
 @OnClick(R.id.fri_close_icn5)
 void fri5(){
  new_staff_breaktime_fri_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_fri_break_btn.setTag(4);
 }





 // sat days add break event
 @OnClick(R.id.new_staff_breaktime_sat_break_btn)
 void sat() {


  if (businessHourData.getSat_start_time().equals("")
          || businessHourData.getSat_start_time().equals("null") ||
          businessHourData.getSat_end_time().equals("") ||
          businessHourData.getSat_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for saturday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_sat_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_sat_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_sat_break_btn.setTag(1);
   } else if (new_staff_breaktime_sat_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_sat_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_sat_break_btn.setTag(2);
   } else if (new_staff_breaktime_sat_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_sat_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_sat_break_btn.setTag(3);
   } else if (new_staff_breaktime_sat_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_sat_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_sat_break_btn.setTag(4);
   } else if (new_staff_breaktime_sat_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_sat_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_sat_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }
  }
 }

 // sat days delete break event1
 @OnClick(R.id.sat_close_icn1)
 void sat1(){
  new_staff_breaktime_sat_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_sat_break_btn.setTag(0);
 }

 // sat days delete break event2
 @OnClick(R.id.sat_close_icn2)
 void sat2(){
  new_staff_breaktime_sat_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_sat_break_btn.setTag(1);
 }


 // sat days delete break event3
 @OnClick(R.id.sat_close_icn3)
 void sat3(){
  new_staff_breaktime_sat_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_sat_break_btn.setTag(2);
 }


 // sat days delete break event4
 @OnClick(R.id.sat_close_icn4)
 void sat4(){
  new_staff_breaktime_sat_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_sat_break_btn.setTag(3);
 }

 // sat days delete break event5
 @OnClick(R.id.sat_close_icn5)
 void sat5(){
  new_staff_breaktime_sat_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_sat_break_btn.setTag(4);
 }



 // sun days add break event
 @OnClick(R.id.new_staff_breaktime_sun_break_btn)
 void sun() {


  if (businessHourData.getSun_start_time().equals("") || businessHourData.getSun_start_time().equals("null") ||
          businessHourData.getSun_end_time().equals("") || businessHourData.getSun_end_time().equals("null")) {
   Snackbar snackbar = Snackbar
           .make(Mroot, "Selected staff doesn't has any working time for sunday!!!", Snackbar.LENGTH_LONG);

   snackbar.show();
  } else {


   if (new_staff_breaktime_sun_break_btn.getTag() == (Object) 0) {
    new_staff_breaktime_sun_breaklayout1.setVisibility(View.VISIBLE);
    new_staff_breaktime_sun_break_btn.setTag(1);
   } else if (new_staff_breaktime_sun_break_btn.getTag() == (Object) 1) {
    new_staff_breaktime_sun_breaklayout2.setVisibility(View.VISIBLE);
    new_staff_breaktime_sun_break_btn.setTag(2);
   } else if (new_staff_breaktime_sun_break_btn.getTag() == (Object) 2) {
    new_staff_breaktime_sun_breaklayout3.setVisibility(View.VISIBLE);
    new_staff_breaktime_sun_break_btn.setTag(3);
   } else if (new_staff_breaktime_sun_break_btn.getTag() == (Object) 3) {
    new_staff_breaktime_sun_breaklayout4.setVisibility(View.VISIBLE);
    new_staff_breaktime_sun_break_btn.setTag(4);
   } else if (new_staff_breaktime_sun_break_btn.getTag() == (Object) 4) {
    new_staff_breaktime_sun_breaklayout5.setVisibility(View.VISIBLE);
   } else if (new_staff_breaktime_sun_break_btn.getTag() == (Object) 4) {
    Snackbar snackbar = Snackbar
            .make(Mroot, "You can add breaks up to 5 !!!", Snackbar.LENGTH_LONG);

    snackbar.show();
   }


  }
 }

 // sun days delete break event1
 @OnClick(R.id.sun_close_icn1)
 void sunc1(){
  new_staff_breaktime_sun_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(0);
 }

 // sun days delete break event2
 @OnClick(R.id.sun_close_icn2)
 void sunc2(){
  new_staff_breaktime_sun_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(1);
 }


 // sun days delete break event3
 @OnClick(R.id.sun_close_icn3)
 void sunc3(){
  new_staff_breaktime_sun_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(2);
 }


 // sun days delete break event4
 @OnClick(R.id.sun_close_icn4)
 void sunc4(){
  new_staff_breaktime_sun_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(3);
 }

 // sun days delete break event5
 @OnClick(R.id.sun_close_icn5)
 void sunc5(){
  new_staff_breaktime_sun_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(4);
 }



 // sun days delete break event1
 @OnClick(R.id.sun_close_icn1)
 void sun1(){
  new_staff_breaktime_sun_breaklayout1.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(0);
 }

 // sun days delete break event2
 @OnClick(R.id.sun_close_icn2)
 void sun2(){
  new_staff_breaktime_sun_breaklayout2.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(1);
 }


 // sun days delete break event3
 @OnClick(R.id.sun_close_icn3)
 void sun3(){
  new_staff_breaktime_sun_breaklayout3.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(2);
 }


 // sun days delete break event4
 @OnClick(R.id.sun_close_icn4)
 void sun4(){
  new_staff_breaktime_sun_breaklayout4.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(3);
 }

 // sun days delete break event5
 @OnClick(R.id.sun_close_icn5)
 void sun5(){
  new_staff_breaktime_sun_breaklayout5.setVisibility(View.GONE);
  new_staff_breaktime_sun_break_btn.setTag(4);
 }





 @OnClick(R.id.new_staff_breaktime_all_starttime_btn1)
 void allFrom1(){

  getTime(new_staff_breaktime_all_starttime_btn1 ,
          new_staff_breaktime_all_endtime_btn1.getText().toString());


 }

 @OnClick(R.id.new_staff_breaktime_all_starttime_btn2)
 void allFrom2(){

  getTime(new_staff_breaktime_all_starttime_btn2 ,
          new_staff_breaktime_all_endtime_btn2.getText().toString());

 }


 @OnClick(R.id.new_staff_breaktime_all_starttime_btn3)
 void allFrom3(){

  getTime(new_staff_breaktime_all_starttime_btn3 ,
          new_staff_breaktime_all_endtime_btn3.getText().toString());



 }


 @OnClick(R.id.new_staff_breaktime_all_starttime_btn4)
 void allFrom4(){

  getTime(new_staff_breaktime_all_starttime_btn4 ,
          new_staff_breaktime_all_endtime_btn4.getText().toString());
 }

 @OnClick(R.id.new_staff_breaktime_all_starttime_btn5)
 void allFrom5(){

  getTime(new_staff_breaktime_all_starttime_btn5 ,
          new_staff_breaktime_all_endtime_btn5.getText().toString());
 }


 @OnClick(R.id.new_staff_breaktime_all_endtime_btn1)
 void allEnd1(){

  getTimeback(new_staff_breaktime_all_endtime_btn1 ,
          new_staff_breaktime_all_starttime_btn1.getText().toString() );

 }

 @OnClick(R.id.new_staff_breaktime_all_endtime_btn2)
 void allEnd2(){

  getTimeback(new_staff_breaktime_all_endtime_btn2 ,
          new_staff_breaktime_all_starttime_btn2.getText().toString() );

 }


 @OnClick(R.id.new_staff_breaktime_all_endtime_btn3)
 void allEnd3(){

  getTimeback(new_staff_breaktime_all_endtime_btn3 ,
          new_staff_breaktime_all_starttime_btn3.getText().toString() );
 }


 @OnClick(R.id.new_staff_breaktime_all_endtime_btn4)
 void allEnd4(){

  getTimeback(new_staff_breaktime_all_endtime_btn4 ,
          new_staff_breaktime_all_starttime_btn4.getText().toString() );
 }

 @OnClick(R.id.new_staff_breaktime_all_endtime_btn5)
 void allEnd5(){

  getTimeback(new_staff_breaktime_all_endtime_btn5 ,
          new_staff_breaktime_all_starttime_btn5.getText().toString() );
 }








 @OnClick(R.id.new_staff_breaktime_mon_starttime_btn1)
 void monFrom1(){


  getTimestaff(new_staff_breaktime_mon_starttime_btn1,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_mon_starttime_btn2)
 void monFrom2(){
  getTimestaff(new_staff_breaktime_mon_starttime_btn2,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());


 }


 @OnClick(R.id.new_staff_breaktime_mon_starttime_btn3)
 void monFrom3(){

  getTimestaff(new_staff_breaktime_mon_starttime_btn3,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());


 }


 @OnClick(R.id.new_staff_breaktime_mon_starttime_btn4)
 void monFrom4(){

  getTimestaff(new_staff_breaktime_mon_starttime_btn4,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_mon_starttime_btn5)
 void monFrom5(){
  getTimestaff(new_staff_breaktime_mon_starttime_btn5,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());


 }


 @OnClick(R.id.new_staff_breaktime_mon_endtime_btn1)
 void monEnd1(){
  getTimestaff(new_staff_breaktime_mon_endtime_btn1,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());




 }

 @OnClick(R.id.new_staff_breaktimemon_endtime_btn2)
 void monEnd2(){

  getTimestaff(new_staff_breaktime_mon_endtime_btn2,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());

 }


 @OnClick(R.id.new_staff_breaktime_mon_endtime_btn3)
 void monEnd3(){

  getTimestaff(new_staff_breaktime_mon_endtime_btn3,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());
 }


 @OnClick(R.id.new_staff_breaktime_mon_endtime_btn4)
 void monEnd4(){

  getTimestaff(new_staff_breaktime_mon_endtime_btn4,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());
 }

 @OnClick(R.id.new_staff_breaktime_mon_endtime_btn5)
 void monEnd5(){

  getTimestaff(new_staff_breaktime_mon_endtime_btn5,
          businessHourData.getMon_start_time() ,businessHourData.getMon_end_time());
 }





 @OnClick(R.id.new_staff_breaktime_tue_starttime_btn1)
 void tueFrom1(){


  getTimestaff(new_staff_breaktime_tue_starttime_btn1,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());


 }
 @OnClick(R.id.new_staff_breaktime_tue_starttime_btn2)
 void tueFrom2(){

  getTimestaff(new_staff_breaktime_tue_starttime_btn2,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());
 }

 @OnClick(R.id.new_staff_breaktime_tue_starttime_btn3)
 void tueFrom3(){

  getTimestaff(new_staff_breaktime_tue_starttime_btn3,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());
 }
 @OnClick(R.id.new_staff_breaktime_tue_starttime_btn4)
 void tueFrom4(){

  getTimestaff(new_staff_breaktime_tue_starttime_btn4,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());
 }
 @OnClick(R.id.new_staff_breaktime_tue_starttime_btn5)
 void tueFrom5(){

  getTimestaff(new_staff_breaktime_tue_starttime_btn5,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());
 }


 @OnClick(R.id.new_staff_breaktime_tue_endtime_btn1)
 void tueEnd1(){
  getTimestaff(new_staff_breaktime_tue_endtime_btn1,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_tue_endtime_btn2)
 void tueEnd2(){

  getTimestaff(new_staff_breaktime_tue_endtime_btn2,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_tue_endtime_btn3)
 void tueEnd3(){

  getTimestaff(new_staff_breaktime_tue_endtime_btn3,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_tue_endtime_btn4)
 void tueEnd4(){

  getTimestaff(new_staff_breaktime_tue_endtime_btn4,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_tue_endtime_btn5)
 void tueEnd5(){

  getTimestaff(new_staff_breaktime_tue_endtime_btn5,
          businessHourData.getTue_start_time() ,businessHourData.getTue_end_time());

 }






 @OnClick(R.id.new_staff_breaktime_wed_starttime_btn1)
 void wedFrom1(){
  getTimestaff(new_staff_breaktime_wed_starttime_btn1,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_wed_starttime_btn2)
 void wedFrom2(){

  getTimestaff(new_staff_breaktime_wed_starttime_btn2,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_wed_starttime_btn3)
 void wedFrom3(){

  getTimestaff(new_staff_breaktime_wed_starttime_btn3,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_wed_starttime_btn4)
 void wedFrom4(){

  getTimestaff(new_staff_breaktime_wed_starttime_btn4,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_wed_starttime_btn5)
 void wedFrom5(){

  getTimestaff(new_staff_breaktime_wed_starttime_btn5,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());

 }



 @OnClick(R.id.new_staff_breaktime_wed_endtime_btn1)
 void wedEnd1(){
  getTimestaff(new_staff_breaktime_wed_endtime_btn1,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_wed_endtime_btn2)
 void wedEnd2(){

  getTimestaff(new_staff_breaktime_wed_endtime_btn2,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());
 }

 @OnClick(R.id.new_staff_breaktime_wed_endtime_btn3)
 void wedEnd3(){

  getTimestaff(new_staff_breaktime_wed_endtime_btn3,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());
 }

 @OnClick(R.id.new_staff_breaktime_wed_endtime_btn4)
 void wedEnd4(){

  getTimestaff(new_staff_breaktime_wed_endtime_btn4,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());
 }

 @OnClick(R.id.new_staff_breaktime_wed_endtime_btn5)
 void wedEnd5(){

  getTimestaff(new_staff_breaktime_wed_endtime_btn5,
          businessHourData.getWed_start_time() ,businessHourData.getWed_end_time());
 }






 @OnClick(R.id.new_staff_breaktime_Thr_starttime_btn1)
 void thrFrom1(){
  getTimestaff(new_staff_breaktime_Thr_starttime_btn1,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());


 }


 @OnClick(R.id.new_staff_breaktime_Thr_starttime_btn2)
 void thrFrom2(){

  getTimestaff(new_staff_breaktime_Thr_starttime_btn2,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_Thr_starttime_btn3)
 void thrFrom3(){

  getTimestaff(new_staff_breaktime_Thr_starttime_btn3,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_Thr_starttime_btn4)
 void thrFrom4(){

  getTimestaff(new_staff_breaktime_Thr_starttime_btn4,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }


 @OnClick(R.id.new_staff_breaktime_Thr_starttime_btn5)
 void thrFrom5(){

  getTimestaff(new_staff_breaktime_Thr_starttime_btn5,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }



 @OnClick(R.id.new_staff_breaktime_Thr_endtime_btn1)
 void thrEnd1(){


  getTimestaff(new_staff_breaktime_Thr_endtime_btn1,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());


 }
 @OnClick(R.id.new_staff_breaktime_Thr_endtime_btn2)
 void thrEnd2(){

  getTimestaff(new_staff_breaktime_Thr_endtime_btn2,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_Thr_endtime_btn3)
 void thrEnd3(){

  getTimestaff(new_staff_breaktime_Thr_endtime_btn3,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_Thr_endtime_btn4)
 void thrEnd4(){

  getTimestaff(new_staff_breaktime_Thr_endtime_btn4,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_Thr_endtime_btn5)
 void thrEnd5(){

  getTimestaff(new_staff_breaktime_Thr_endtime_btn5,
          businessHourData.getThr_start_time() ,businessHourData.getThr_end_time());

 }










 @OnClick(R.id.new_staff_breaktime_fri_starttime_btn1)
 void friFrom1(){

  getTimestaff(new_staff_breaktime_fri_starttime_btn1,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_fri_starttime_btn2)
 void friFrom2(){

  getTimestaff(new_staff_breaktime_fri_starttime_btn2,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_fri_starttime_btn3)
 void friFrom3(){

  getTimestaff(new_staff_breaktime_fri_starttime_btn3,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_fri_starttime_btn4)
 void friFrom4(){

  getTimestaff(new_staff_breaktime_fri_starttime_btn4,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_fri_starttime_btn5)
 void friFrom5(){

  getTimestaff(new_staff_breaktime_fri_starttime_btn5,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }




 @OnClick(R.id.new_staff_breaktime_fri_endtime_btn1)
 void friEnd1(){


  getTimestaff(new_staff_breaktime_fri_endtime_btn1,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_fri_endtime_btn2)
 void friEnd2(){

  getTimestaff(new_staff_breaktime_fri_endtime_btn2,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_fri_endtime_btn3)
 void friEnd3(){

  getTimestaff(new_staff_breaktime_fri_endtime_btn3,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_fri_endtime_btn4)
 void friEnd4(){

  getTimestaff(new_staff_breaktime_fri_endtime_btn4,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_fri_endtime_btn5)
 void friEnd5(){

  getTimestaff(new_staff_breaktime_fri_endtime_btn5,
          businessHourData.getFri_start_time() ,businessHourData.getFri_end_time());

 }

















 @OnClick(R.id.new_staff_breaktime_sat_starttime_btn1)
 void satFrom1(){


  getTimestaff(new_staff_breaktime_sat_starttime_btn1,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());


 }
 @OnClick(R.id.new_staff_breaktime_sat_starttime_btn2)
 void satFrom2(){


  getTimestaff(new_staff_breaktime_sat_starttime_btn2,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());



 }

 @OnClick(R.id.new_staff_breaktime_sat_starttime_btn3)
 void satFrom3(){

  getTimestaff(new_staff_breaktime_sat_starttime_btn3,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_sat_starttime_btn4)
 void satFrom4(){

  getTimestaff(new_staff_breaktime_sat_starttime_btn4,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());
 }


 @OnClick(R.id.new_staff_breaktime_sat_starttime_btn5)
 void satFrom5(){

  getTimestaff(new_staff_breaktime_sat_starttime_btn5,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());
 }



 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn1)
 void satEnd1(){


  getTimestaff(new_staff_breaktime_sat_endtime_btn1,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn2)
 void satEnd2(){

  getTimestaff(new_staff_breaktime_sat_endtime_btn2,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn3)
 void satEnd3(){

  getTimestaff(new_staff_breaktime_sat_endtime_btn3,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn4)
 void satEnd4(){

  getTimestaff(new_staff_breaktime_sat_endtime_btn4,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn5)
 void satEnd5(){

  getTimestaff(new_staff_breaktime_sat_endtime_btn5,
          businessHourData.getSat_start_time() ,businessHourData.getSat_end_time());

 }





 @OnClick(R.id.new_staff_breaktime_sun_starttime_btn1)
 void sunFrom1(){


  getTimestaff(new_staff_breaktime_sun_starttime_btn1,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_sun_starttime_btn2)
 void sunFrom2(){

  getTimestaff(new_staff_breaktime_sun_starttime_btn2,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_starttime_btn3)
 void sunFrom3(){

  getTimestaff(new_staff_breaktime_sun_starttime_btn3,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_starttime_btn4)
 void sunFrom4(){

  getTimestaff(new_staff_breaktime_sun_starttime_btn4,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }
 @OnClick(R.id.new_staff_breaktime_sun_starttime_btn5)
 void sunFrom5(){

  getTimestaff(new_staff_breaktime_sun_starttime_btn5,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }



 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn1)
 void sunEnd1(){
  getTimestaff(new_staff_breaktime_sun_endtime_btn1,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());


 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn2)
 void sunEnd2(){

  getTimestaff(new_staff_breaktime_sun_endtime_btn2,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn3)
 void sunEnd3(){

  getTimestaff(new_staff_breaktime_sun_endtime_btn3,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn4)
 void sunEnd4(){

  getTimestaff(new_staff_breaktime_sun_endtime_btn4,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }

 @OnClick(R.id.new_staff_breaktime_sun_endtime_btn5)
 void sunEnd5(){

  getTimestaff(new_staff_breaktime_sun_endtime_btn5,
          businessHourData.getSun_start_time() ,businessHourData.getSun_end_time());

 }



















}
