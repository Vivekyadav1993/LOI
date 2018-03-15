package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.AddStaffAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.StaffData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 9/28/2017.
 */

public class AddStaffFrag extends HelperFrags implements HttpresponseUpd{

    private View Mroot;

    @BindView(R.id.add_staff_onlyme_btn)
    Switch add_staff_onlyme_btn;



    @BindView(R.id.add_staff_list)
    ListView add_staff_list;

    private  int pos , create_pos ;

    private Bundle bundle;

    private Snackbar snackbar;

    private HttpresponseUpd callback;

    private AddStaffAdap adapter;

    private Boolean src_pos = false ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_staff_screen,null);
        ButterKnife.bind(this , Mroot);

        bundle = getArguments();

        callback = this;

        create_pos = bundle.getInt("create_pos");


        //initialize static  staff array and add index 0
         LandingActivity.staff_data_array = new ArrayList<>();
         pos = 0;
         LandingActivity.staff_data_array.add(new StaffData( "No" , "No" , LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(),"", "", "", "","", "", "", "", "", "", "",
                "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "",
                 "", "","", "", "", "", "","", "", "", "", "", "","", "", "", "", "", "", "", "", "", "", "", "","", "", "",
                 "", "", "", "", "", "", "", "", "", "", "", "", "", "","","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")  );


        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "staffList")
                .appendQueryParameter("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());

            Log.e("stafflist" , builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


        add_staff_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle =   new Bundle();
                bundle.putString("src","createstaff");
                bundle.putInt("pos",i);
                bundle.putInt("create_pos",create_pos);
                // go next page
                replaceFrag(new NewStaffFrag(),bundle, AddStaffFrag.class.getName());
            }
        });


        return Mroot;

    }


    @OnClick(R.id.add_staff_next_btn)
    void go(){

        if (add_staff_onlyme_btn.isChecked()){
            LandingActivity.staff_data_array.get(pos).setOnly_me_flag("Yes");
        }

        Bundle _bundle =   new Bundle();
        _bundle.putString("src",bundle.getString("src"));

            _bundle.putInt("staff_pos", pos);

        _bundle.putInt("create_pos",create_pos);


        replaceFrag(new AddResourcesFrag(), _bundle, AddStaffFrag.class.getName());

    }


    @OnClick(R.id.add_staff_btn)
    void addStaff(){
        if (add_staff_onlyme_btn.isChecked()){

        }
        else{
            //save address id
            LandingActivity.staff_data_array.get(pos).setAddress_id(LandingActivity.business_data.getAdderess_data().get(create_pos).getAddress_id());
            Bundle _bundle =   new Bundle();
            _bundle.putString("src",bundle.getString("src"));
            if (src_pos){
            _bundle.putInt("pos",pos+1);

                LandingActivity.staff_data_array.add(pos+1 ,new StaffData( "No" , "No" ,
                        LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()
                        ,"", "", "", "","", "", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_start() ,
                        LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_end()
                        , "", "", "",
                        "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "",
                        "", "","", "", "", "", "","", "", "", "", "", "","", "", "", "", "", "", "", "", "", "", "", "","", "", "",
                        "", "", "", "", "", "", "", "", "", "", "", "", "", "","","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")  );



            }
            else{
                LandingActivity.staff_data_array.add(pos ,new StaffData( "No" , "No" ,
                        LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()
                        ,"", "", "", "","", "", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_start() ,
                        LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_end()
                        , "", "", "",
                        "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","", "",
                        "", "","", "", "", "", "","", "", "", "", "", "","", "", "", "", "", "", "", "", "", "", "", "","", "", "",
                        "", "", "", "", "", "", "", "", "", "", "", "", "", "","","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")  );


                _bundle.putInt("pos",pos);}

            _bundle.putInt("create_pos",create_pos);
            // go next page
            replaceFrag(new NewStaffFrag(),_bundle, AddStaffFrag.class.getName());

        }
    }

    @OnClick(R.id.add_staff_back_btn)
    void backGo(){
        getActivity().onBackPressed();
    }

    @Override
    public void getResponse(String response) {

        Log.e("res", response);


        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                String only_me_flag = "No";

                if (arr.getJSONObject(0).getString("message").equals("No Record Found")) {
                    snackbar = Snackbar.make(Mroot, arr.getJSONObject(0).getString("message") , Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    src_pos =  true;
                    LandingActivity.staff_data_array.clear();

                    StaffData staff_data;
                    JSONObject obj;

                    for (int i = 0; i < arr.length(); i++) {
                        obj = arr.getJSONObject(i);

                        only_me_flag = obj.getString("onlyme_status");

                        staff_data = new StaffData(obj.getString("admin_status"), obj.getString("onlyme_status"),
                                obj.getString("address_id"), obj.getString("staff_id"), obj.getString("first_name"),
                                obj.getString("last_name"), obj.getString("email"), obj.getString("phone"), obj.getString("address"),
                                obj.getString("start_date"), obj.getString("end_date")

                                , obj.getString("all_break1_from"), obj.getString("all_break1_to"), obj.getString("all_break2_from"),
                                obj.getString("all_break2_to"), obj.getString("all_break3_from"), obj.getString("all_break3_to"),
                                obj.getString("all_break4_from"), obj.getString("all_break4_to"), obj.getString("all_break5_from"),
                                obj.getString("all_break5_to"),


                                obj.getString("mon_from_time"), obj.getString("mon_to_time"), obj.getString("mon_break1_from"), obj.getString("mon_break1_to"), obj.getString("mon_break2_from"),
                                obj.getString("mon_break2_to"), obj.getString("mon_break3_from"), obj.getString("mon_break3_to"),
                                obj.getString("mon_break4_from"), obj.getString("mon_break4_to"), obj.getString("mon_break5_from"),
                                obj.getString("mon_break5_to"),


                                obj.getString("tue_from_time"), obj.getString("tue_to_time"), obj.getString("tue_break1_from"),
                                obj.getString("tue_break1_to"), obj.getString("tue_break2_from"),
                                obj.getString("tue_break2_to"), obj.getString("tue_break3_from"), obj.getString("tue_break3_to"),
                                obj.getString("tue_break4_from"), obj.getString("tue_break4_to"), obj.getString("tue_break5_from"),
                                obj.getString("tue_break5_to"),


                                obj.getString("wed_from_time"), obj.getString("wed_to_time"), obj.getString("wed_break1_from"),
                                obj.getString("wed_break1_to"), obj.getString("wed_break2_from"),
                                obj.getString("wed_break2_to"), obj.getString("wed_break3_from"), obj.getString("wed_break3_to"),
                                obj.getString("wed_break4_from"), obj.getString("wed_break4_to"), obj.getString("wed_break5_from"),
                                obj.getString("wed_break5_to"),


                                obj.getString("thru_from_time"), obj.getString("thru_to_time"), obj.getString("thru_break1_from"),
                                obj.getString("thru_break1_to"), obj.getString("thru_break2_from"),
                                obj.getString("thru_break2_to"), obj.getString("thru_break3_from"), obj.getString("thru_break3_to"),
                                obj.getString("thru_break4_from"), obj.getString("thru_break4_to"), obj.getString("thru_break5_from"),
                                obj.getString("thru_break5_to"),


                                obj.getString("fri_from_time"), obj.getString("fri_to_time"), obj.getString("fri_break1_from"),
                                obj.getString("fri_break1_to"), obj.getString("fri_break2_from"),
                                obj.getString("fri_break2_to"), obj.getString("fri_break3_from"), obj.getString("fri_break3_to"),
                                obj.getString("fri_break4_from"), obj.getString("fri_break4_to"), obj.getString("fri_break5_from"),
                                obj.getString("fri_break5_to"),


                                obj.getString("sat_from_time"), obj.getString("sat_to_time"), obj.getString("sat_break1_from"),
                                obj.getString("sat_break1_to"), obj.getString("sat_break2_from"),
                                obj.getString("sat_break2_to"), obj.getString("sat_break3_from"), obj.getString("sat_break3_to"),
                                obj.getString("sat_break4_from"), obj.getString("sat_break4_to"), obj.getString("sat_break5_from"),
                                obj.getString("sat_break5_to"),

                                obj.getString("sun_from_time"), obj.getString("sun_to_time"), obj.getString("sun_break1_from"),
                                obj.getString("sun_break1_to"), obj.getString("sun_break2_from"),
                                obj.getString("sun_break2_to"), obj.getString("sun_break3_from"), obj.getString("sun_break3_to"),
                                obj.getString("sun_break4_from"), obj.getString("sun_break4_to"), obj.getString("sun_break5_from"),
                                obj.getString("sun_break5_to")

                        );


                        LandingActivity.staff_data_array.add(staff_data);

                    }
                    // set staff list
                    adapter = new AddStaffAdap(getActivity() , LandingActivity.staff_data_array);
                    add_staff_list.setAdapter(adapter);
                    pos = LandingActivity.staff_data_array.size()-1;
                }


                if (only_me_flag.equals("No")) {
                    add_staff_onlyme_btn.setChecked(false);
                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }

    }
}
