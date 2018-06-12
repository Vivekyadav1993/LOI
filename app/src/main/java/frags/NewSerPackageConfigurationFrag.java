package frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.PackageConfigData;

/**
 * Created by teknik on 10/10/2017.
 */

public class NewSerPackageConfigurationFrag extends HelperFrags implements HttpresponseUpd {

    @BindView(R.id.pack_confi_day_input)
    EditText pack_confi_day_input;


    @BindView(R.id.pack_confi_duration_input)
    EditText pack_confi_duration_input;

    @BindView(R.id.pack_confi_classno_input)
    EditText pack_confi_classno_input;

    @BindView(R.id.pack_confi_cost_input)
    EditText pack_confi_cost_input;


    @BindView(R.id.pack_confi_byvendor_refundradiogrp)
    RadioGroup pack_confi_byvendor_refundradiogrp;

    @BindView(R.id.pack_confi_byvendor_refund_btn)
    RadioButton pack_confi_byvendor_refund_btn;

    @BindView(R.id.pack_confi_byvendor_nextperiod_btn)
    RadioButton pack_confi_byvendor_nextperiod_btn;

    @BindView(R.id.pack_confi_bycus_refundradiogrp)
    RadioGroup pack_confi_bycus_refundradiogrp;

    @BindView(R.id.pack_confi_bycus_refund_btn)
    RadioButton pack_confi_bycus_refund_btn;

    @BindView(R.id.pack_confi_bycus_nextperiod_btn)
    RadioButton pack_confi_bycus_nextperiod_btn;


    private View Mroot;

    private Bundle bundle;

    private HttpresponseUpd callback;

    private Snackbar snackbar ;

    private PackageConfigData data ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_package_confirmation_screen , null);

        ButterKnife.bind(this , Mroot);

        callback = this;

        bundle =  getArguments();

        LandingActivity.ser_package_array = new ArrayList<>();

        data = new PackageConfigData("","","","","","","","");




        pack_confi_byvendor_refundradiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.pack_confi_byvendor_refund_btn){

                    if (pack_confi_byvendor_refund_btn.isChecked()){
                        //save data
                        data.setCancelled_by_vendor_refund("Yes");
                    }
                    else{
                        //save data
                        data.setCancelled_by_vendor_refund("No");
                    }

                }
                else{
                    if (pack_confi_byvendor_nextperiod_btn.isChecked()){
                        //save data
                        data.setCancelled_by_vendor_class_in_next_period("Yes");
                    }
                    else{
                        //save data
                        data.setCancelled_by_vendor_class_in_next_period("No");
                    }

                }
            }
        });

        pack_confi_bycus_refundradiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.pack_confi_bycus_refund_btn){

                    if (pack_confi_bycus_refund_btn.isChecked()){
                        //save data
                        data.setCancelled_by_customer_refund("Yes");
                    }
                    else{
                        //save data
                        data.setCancelled_by_customer_refund("No");
                    }

                }
                else{
                    if (pack_confi_bycus_nextperiod_btn.isChecked()){
                        //save data
                        data.setCancelled_by_customer_class_in_next_period("Yes");
                    }
                    else{
                        //save data
                        data.setCancelled_by_customer_class_in_next_period("No");
                    }

                }
            }
        });


        return Mroot ;
    }

    @OnClick(R.id.pack_confi_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.pack_confi_next_btn)
    void next(){

        Bundle _bundle  = new Bundle();
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        _bundle.putString("ser_id", bundle.getString("ser_id"));

       replaceFrag(new NewSerPackageConfigManageFrag() , _bundle , NewSerPackageConfigurationFrag.class.getName() );

    }

    @OnClick(R.id.pack_confi_addbtn)
    void addPackage(){
        // save data
        if (pack_confi_day_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot,"No of day cannot blank!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (pack_confi_duration_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot,"duration cannot blank!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (pack_confi_classno_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot,"Class no cannot blank!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (pack_confi_cost_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot,"Cost cannot blank!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (!pack_confi_byvendor_refund_btn.isChecked()&&!pack_confi_byvendor_nextperiod_btn.isChecked()){
            snackbar = Snackbar.make(Mroot,"Select refund option!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (!pack_confi_bycus_refund_btn.isChecked()&&!pack_confi_bycus_nextperiod_btn.isChecked()){
            snackbar = Snackbar.make(Mroot,"Select refund option!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{
            //save data

            data.setNo_of_days(pack_confi_day_input.getText().toString()+" Day");
            data.setDuration_per_class(pack_confi_duration_input.getText().toString()+" Duration per class");
            data.setNo_of_class(pack_confi_classno_input.getText().toString()+" Class");
            data.setPackage_cost(pack_confi_cost_input.getText().toString()+ " Cost");

            LandingActivity.ser_package_array.add(data);

            Bundle _bundle  = new Bundle();
            _bundle.putInt("create_pos", bundle.getInt("create_pos"));
            _bundle.putString("ser_id", bundle.getString("ser_id"));

            replaceFrag(new NewSerPackageConfigManageFrag() , _bundle , NewSerPackageConfigurationFrag.class.getName() );


        }


    }

    @Override
    public void getResponse(String response) {

    }
}
