package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.NewSerPackConfigManageList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.PackageConfigData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/6/2017.
 */

public class NewSerCustomerPolicyFrag extends HelperFrags implements HttpresponseUpd{

    @BindView(R.id.newser_cuspolicy_staffwise_booking_switch)
    Switch newser_cuspolicy_staffwise_booking_switch;

    @BindView(R.id.newser_cuspolicy_advnoof_day_switch)
    Switch newser_cuspolicy_advnoof_day_switch;

    @BindView(R.id.newser_cuspolicy_repeatbooking_packageconfig_switch)
    Switch newser_cuspolicy_repeatbooking_packageconfig_switch;

    @BindView(R.id.newser_cuspolicy_operatorlive_status_switch)
    Switch newser_cuspolicy_operatorlive_status_switch;

    @BindView(R.id.newser_cuspolicy_bookedpeople_ingroup_switch)
    Switch newser_cuspolicy_bookedpeople_ingroup_switch;

    @BindView(R.id.newser_cuspolicy_calender_sync_switch)
    Switch newser_cuspolicy_calender_sync_switch;



    @BindView(R.id.newser_prepaid_switch)
    Switch newser_prepaid_switch;

    @BindView(R.id.prepaid_tag)
    TextView prepaid_tag;

    @BindView(R.id.newser_cuspolicy_prepaid_book_radiogrp)
    RadioGroup newser_cuspolicy_prepaid_book_radiogrp;

    @BindView(R.id.newser_cuspolicy_prepaid_book_ajust_radiobtn)
    RadioButton newser_cuspolicy_prepaid_book_ajust_radiobtn;

    @BindView(R.id.newser_cuspolicy_prepaid_book_nonajust_radiobtn)
    RadioButton newser_cuspolicy_prepaid_book_nonajust_radiobtn;




    private View Mroot;

    private  Bundle bundle;

    private HttpresponseUpd callback;

    private Snackbar snackbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_customerpolicy_screen, null);
        ButterKnife.bind(this , Mroot);

        callback = this;

        bundle = getArguments();



        if (LandingActivity.staffwise_booking_switch.equals("Yes")){
            newser_cuspolicy_staffwise_booking_switch.setChecked(true);

        }
        else{
            newser_cuspolicy_staffwise_booking_switch.setChecked(false);
        }

       if (LandingActivity.advnoof_day_switch.equals("Yes")){
            newser_cuspolicy_advnoof_day_switch.setChecked(true);

        }
        else{
            newser_cuspolicy_advnoof_day_switch.setChecked(false);
        }

        if (LandingActivity.packageconfig_switch.equals("Yes")){
            newser_cuspolicy_repeatbooking_packageconfig_switch.setChecked(true);

        }
        else{
            newser_cuspolicy_repeatbooking_packageconfig_switch.setChecked(false);
        }



        if (LandingActivity.status_switch.equals("Yes")){
            newser_cuspolicy_operatorlive_status_switch.setChecked(true);

        }
        else{
            newser_cuspolicy_operatorlive_status_switch.setChecked(false);
        }


        if (LandingActivity.ingroup_switch.equals("Yes")){
            newser_cuspolicy_bookedpeople_ingroup_switch.setChecked(true);

        }
        else{
            newser_cuspolicy_bookedpeople_ingroup_switch.setChecked(false);
        }

        if (LandingActivity.sync_switch.equals("Yes")){
            newser_cuspolicy_calender_sync_switch.setChecked(true);

        }
        else{
            newser_cuspolicy_calender_sync_switch.setChecked(false);
        }

        if (LandingActivity.ajust_radiobtn_value.equals("Yes")){
            newser_cuspolicy_prepaid_book_ajust_radiobtn.setChecked(true);
            newser_prepaid_switch.setChecked(true);


        }
        else if (LandingActivity.ajust_radiobtn_value.equals("")){
            newser_prepaid_switch.setChecked(false);
            prepaid_tag.setVisibility(View.GONE);
            newser_cuspolicy_prepaid_book_radiogrp.setVisibility(View.GONE);
        }
        else{
            newser_prepaid_switch.setChecked(true);

            newser_cuspolicy_prepaid_book_ajust_radiobtn.setChecked(false);
        }




        newser_prepaid_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    prepaid_tag.setVisibility(View.VISIBLE);
                    newser_cuspolicy_prepaid_book_radiogrp.setVisibility(View.VISIBLE);
                }

                else{
                    prepaid_tag.setVisibility(View.GONE);
                    newser_cuspolicy_prepaid_book_radiogrp.setVisibility(View.GONE);
                }
            }
        });




        return Mroot;
    }


    @OnClick(R.id.newser_cuspolicy_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.newser_cuspolicy_next_btn)
    void create(){

        if (newser_cuspolicy_staffwise_booking_switch.isChecked()){
            LandingActivity.staffwise_booking_switch = "Yes";
        }
        else{
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()){
            LandingActivity.advnoof_day_switch = "Yes";
        }
        else{
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()){
            LandingActivity.packageconfig_switch = "Yes";
        }
        else{
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()){
            LandingActivity.status_switch = "Yes";
        }
        else{
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()){
            LandingActivity.ingroup_switch = "Yes";
        }
        else{
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()){
            LandingActivity.sync_switch = "Yes";
        }
        else{
            LandingActivity.sync_switch = "No";
        }

        if (newser_prepaid_switch.isChecked()) {
            if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()) {
                LandingActivity.ajust_radiobtn_value = "Yes";
            } else {
                LandingActivity.ajust_radiobtn_value = "No";
            }
        }
        else{
            LandingActivity.ajust_radiobtn_value = "No";
        }

        //hit get api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "createcustomerPolicy")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id())

                .appendQueryParameter("staffwise_booking", LandingActivity.staffwise_booking_switch)
                .appendQueryParameter("advance_no_of_days", LandingActivity.advnoof_day_switch)
                .appendQueryParameter("package_conf", LandingActivity.packageconfig_switch)
                .appendQueryParameter("operatorlive_status", LandingActivity.status_switch)
                .appendQueryParameter("in_group", LandingActivity.ingroup_switch)
                .appendQueryParameter("cal_sync", LandingActivity.sync_switch)
                .appendQueryParameter("amount_adjust", LandingActivity.ajust_radiobtn_value)
        ;

        Log.e("url",builder.build().toString());

        if (AppUtils.isNetworkAvailable(getActivity())){



            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


       //  replaceFrag(new AddServiceFrag(), new Bundle() ,NewSerCustomerPolicyFrag.class.getName());
    }


    @OnClick(R.id.newser_cuspolicy_cancellationpolicy_btn)
    void cancellation(){

        if (newser_cuspolicy_staffwise_booking_switch.isChecked()){
            LandingActivity.staffwise_booking_switch = "Yes";
        }
        else{
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()){
            LandingActivity.advnoof_day_switch = "Yes";
        }
        else{
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()){
            LandingActivity.packageconfig_switch = "Yes";
        }
        else{
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()){
            LandingActivity.status_switch = "Yes";
        }
        else{
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()){
            LandingActivity.ingroup_switch = "Yes";
        }
        else{
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()){
            LandingActivity.sync_switch = "Yes";
        }
        else{
            LandingActivity.sync_switch = "No";
        }
        if (newser_prepaid_switch.isChecked()) {
            if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()) {
                LandingActivity.ajust_radiobtn_value = "Yes";
            } else {
                LandingActivity.ajust_radiobtn_value = "No";
            }
        }
        else{
            LandingActivity.ajust_radiobtn_value = "";

        }


        Bundle _bundle  = new Bundle();


        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));
        // go to next page
        replaceFrag(new NewSerCancellationPolicyFrag() , _bundle , NewSerCustomerPolicyFrag.class.getName());
    }

    @OnClick(R.id.newser_cuspolicy_discountpolicy_btn)
    void discount(){
        if (newser_cuspolicy_staffwise_booking_switch.isChecked()){
            LandingActivity. staffwise_booking_switch = "Yes";
        }
        else{
            LandingActivity.staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()){
            LandingActivity.advnoof_day_switch = "Yes";
        }
        else{
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()){
            LandingActivity.packageconfig_switch = "Yes";
        }
        else{
            LandingActivity.packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()){
            LandingActivity.status_switch = "Yes";
        }
        else{
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()){
            LandingActivity.ingroup_switch = "Yes";
        }
        else{
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()){
            LandingActivity. sync_switch = "Yes";
        }
        else{
            LandingActivity. sync_switch = "No";
        }
        if (newser_prepaid_switch.isChecked()) {
        if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()){
            LandingActivity. ajust_radiobtn_value = "Yes";
        }
        else{
            LandingActivity.ajust_radiobtn_value = "No";
        }
        }
        else{
            LandingActivity.ajust_radiobtn_value = "";
        }
        Bundle _bundle  = new Bundle();
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));
         // go to next page
        replaceFrag(new NewSerDiscountPolicyFrag() , _bundle , NewSerCustomerPolicyFrag.class.getName());

    }

    @OnClick(R.id.newser_cuspolicy_packageconfi_btn)
    void packageConfig(){
        if (newser_cuspolicy_staffwise_booking_switch.isChecked()){
            LandingActivity.staffwise_booking_switch = "Yes";
        }
        else{
            LandingActivity. staffwise_booking_switch = "No";
        }

        if (newser_cuspolicy_advnoof_day_switch.isChecked()){
            LandingActivity.advnoof_day_switch = "Yes";
        }
        else{
            LandingActivity.advnoof_day_switch = "No";
        }

        if (newser_cuspolicy_repeatbooking_packageconfig_switch.isChecked()){
            LandingActivity.packageconfig_switch = "Yes";
        }
        else{
            LandingActivity. packageconfig_switch = "No";
        }

        if (newser_cuspolicy_operatorlive_status_switch.isChecked()){
            LandingActivity.status_switch = "Yes";
        }
        else{
            LandingActivity.status_switch = "No";
        }

        if (newser_cuspolicy_bookedpeople_ingroup_switch.isChecked()){
            LandingActivity.ingroup_switch = "Yes";
        }
        else{
            LandingActivity.ingroup_switch = "No";
        }

        if (newser_cuspolicy_calender_sync_switch.isChecked()){
            LandingActivity.sync_switch = "Yes";
        }
        else{
            LandingActivity.sync_switch = "No";
        }
        if (newser_prepaid_switch.isChecked()) {
        if (newser_cuspolicy_prepaid_book_ajust_radiobtn.isChecked()){
            LandingActivity.ajust_radiobtn_value = "Yes";
        }
        else{
            LandingActivity. ajust_radiobtn_value = "No";
        }
        }
        else{
            LandingActivity. ajust_radiobtn_value = "";
        }

        Bundle _bundle  = new Bundle();
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));
        replaceFrag(new NewSerPackageConfigurationFrag() , _bundle , NewSerCustomerPolicyFrag.class.getName());

    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else{

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("message").equals("Customer policy created successfully")){
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();
                    Bundle _bundle = new Bundle();




                    _bundle.putInt("create_pos",bundle.getInt("create_pos"));

                    replaceFrag(new AddServiceFrag(), _bundle , "");

                }
                else {


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
