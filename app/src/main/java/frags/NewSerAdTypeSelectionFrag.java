package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.StaffSelecData;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Utils;

/**
 * Created by teknik on 10/6/2017.
 */

public class NewSerAdTypeSelectionFrag extends HelperFrags implements  HttpresponseUpd{


    private View Mroot;

    @BindView(R.id.newser_adtype_appointment_layout)
    LinearLayout newser_adtype_appointment_layout;

    @BindView(R.id.newser_adtype_all_radiogrp)
    RadioGroup newser_adtype_all_radiogrp;

    @BindView(R.id.newser_adtype_que_btn)
    RadioButton newser_adtype_que_btn;

    @BindView(R.id.newser_adtype_group_btn)
    RadioButton newser_adtype_group_btn;

    @BindView(R.id.newser_adtype_appointment_btn)
    RadioButton newser_adtype_appointment_btn;


    @BindView(R.id.sub_type_radiogrp)
    RadioGroup sub_type_radiogrp;

    @BindView(R.id.sub_type_fixed)
    RadioButton sub_type_fixed;

    @BindView(R.id.sub_type_unfixed)
    RadioButton sub_type_unfixed;

    private String appointment_type ="", appointment_subtype = "";

    private HttpresponseUpd callback;

    private Bundle bundle;

    private Snackbar snackbar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_adtype_selection_screen, null);
        ButterKnife.bind(this , Mroot);

        callback = this;

        bundle = getArguments();

        newser_adtype_all_radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (newser_adtype_appointment_btn.isChecked()){
                    newser_adtype_appointment_layout.setVisibility(View.VISIBLE);
                    appointment_type = "Appointment";
                }
                else{
                    newser_adtype_appointment_layout.setVisibility(View.GONE);
                }

                if (newser_adtype_que_btn.isChecked()){
                    appointment_type = "Que";
                }

                if (newser_adtype_group_btn.isChecked()){
                    appointment_type = "Group";
                }
            }
        });


        sub_type_radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (sub_type_fixed.isChecked()){
                    appointment_subtype = "Fixed Service Duration";
                }

                if (sub_type_unfixed.isChecked()){
                    appointment_subtype = "Fixed Start or End Time";
                }
            }
        });

        return Mroot;
    }

    @OnClick(R.id.newser_adtype_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.newser_adtype_next_btn)
    void next(){

        if (appointment_type.equals("")){
            snackbar = Snackbar.make(Mroot, "Please choose any one option!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (appointment_type.equals("Appointment")){
            if (appointment_subtype.equals("")){
                snackbar = Snackbar.make(Mroot, "Please choose Appointment sub option!", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else{
                //hit api
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "updateserviceAppointmenttype")
                        .appendQueryParameter("service_id", bundle.getString("ser_id"))
                        .appendQueryParameter("appointment_type", appointment_type)
                        .appendQueryParameter("appointment_subtype", appointment_subtype)
                ;

                Log.e("url",builder.build().toString() );
                if (AppUtils.isNetworkAvailable(getActivity())){

                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
                else {
                    snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                    snackbar.show();

                }
            }
        }
        else{
            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "updateserviceAppointmenttype")
                    .appendQueryParameter("service_id", bundle.getString("ser_id"))
                    .appendQueryParameter("appointment_type", appointment_type)
                    .appendQueryParameter("appointment_subtype", appointment_subtype)
            ;

            Log.e("url",builder.build().toString() );
            if (AppUtils.isNetworkAvailable(getActivity())){

                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
            else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

        }


    }


    @Override
    public void getResponse(String response) {
        Log.e("res", response);


        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else{
            // parse data
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");


                if (arr.getJSONObject(0).getString("message").equals("Appointment updated successfully")){
                    Bundle _bundle  = new Bundle();


                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));
                    _bundle.putString("ser_id", bundle.getString("ser_id"));
                    // go next page
                    replaceFrag(new NewSerCustomerPolicyFrag(), _bundle , NewSerAdTypeSelectionFrag.class.getName());

                }
                snackbar = Snackbar.make(Mroot,arr.getJSONObject(0).getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();





            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
    }
}
