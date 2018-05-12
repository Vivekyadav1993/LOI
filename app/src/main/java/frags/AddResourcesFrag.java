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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.AddResAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.ResourceData;
import models.ResourceSpecification;
import models.StaffData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Utils;

/**
 * Created by teknik on 10/4/2017.
 */

public class AddResourcesFrag extends HelperFrags implements HttpresponseUpd {


    private View Mroot;
    @BindView(R.id.add_res_list)
    ListView add_res_list;

    private Bundle bundle ;

    private int pos = 0;

    private ArrayList<ResourceSpecification> res_spec_array = new ArrayList<>();

    private Snackbar snackbar;

    private HttpresponseUpd callback;

    private AddResAdap adapter;

    private String res_spec_rawdata = "";

    private Boolean src_pos = false ;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_resource_screen,null);

        ButterKnife.bind(this, Mroot);

        bundle = getArguments();

        callback = this;




        res_spec_array.add(new ResourceSpecification("",""));

        LandingActivity.res_data_array = new ArrayList<>();

        LandingActivity.res_data_array.add(new ResourceData( LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(),"","","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
        ,"","","","","","","","","",""
               , "","","","","","","","",res_spec_array));


        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "resourceList")
                .appendQueryParameter("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());


        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


        add_res_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle _bundle =   new Bundle();
                _bundle.putString("src","createstaff");
                _bundle.putInt("pos",i);
                _bundle.putInt("create_pos",bundle.getInt("create_pos"));
                // go next page
                replaceFrag(new NewResourceInfoFrag(),_bundle, AddStaffFrag.class.getName());
            }
        });



        return  Mroot;
    }

    @OnClick(R.id.add_res_next_btn)
    void go(){
        Bundle _bundle =   new Bundle();

        _bundle.putInt("create_pos",bundle.getInt("create_pos"));
        _bundle.putInt("address_id",bundle.getInt("address_id"));
        _bundle.putInt("business_id",bundle.getInt("business_id"));

        replaceFrag(new AddServiceFrag(), _bundle , AddResourcesFrag.class.getName());

    }


   /* @OnClick(R.id.add_res_btn)
    void addRes(){

        Bundle _bundle =   new Bundle();
        _bundle.putString("src","def");
        if (src_pos){
            _bundle.putInt("pos",pos+1);

            ArrayList<ResourceSpecification> res_spec_array = new ArrayList<>();

            res_spec_array.add(new ResourceSpecification("",""));



            LandingActivity.res_data_array.add(pos+1 , new ResourceData(
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()
                    ,"","",""
                    ,LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_start() ,
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_end() ,


                    "","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    , "","","","","","","","",res_spec_array));
        }
        else{
            ArrayList<ResourceSpecification> res_spec_array = new ArrayList<>();

            res_spec_array.add(new ResourceSpecification("",""));



            LandingActivity.res_data_array.add(pos , new ResourceData(
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()
                    ,"","",""
                    ,LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_start() ,
                    LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_end() ,


                    "","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    ,"","","","","","","","","",""
                    , "","","","","","","","",res_spec_array));


            _bundle.putInt("pos",pos);}


        _bundle.putInt("create_pos",bundle.getInt("create_pos"));

            // go next page
            replaceFrag(new NewResourceInfoFrag(), _bundle, AddStaffFrag.class.getName());


    }
*/
    @OnClick(R.id.add_res_back_btn)
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


                if (arr.getJSONObject(0).getString("message").equals("No Record Found")) {
                    snackbar = Snackbar.make(Mroot, arr.getJSONObject(0).getString("message") , Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    LandingActivity.staff_data_array.clear();

                    ResourceData res_data;
                    JSONObject obj;

                    LandingActivity.res_data_array.clear();

                    for (int i = 0; i < arr.length(); i++) {
                        obj = arr.getJSONObject(i);

                        /*JSONArray spec_array = obj.getJSONArray("specification");

                        res_spec_rawdata = spec_array.toString();

                        res_spec_array.clear();

                        for (int j = 0; j <spec_array.length() ; j++) {
                            JSONObject obja = spec_array.getJSONObject(j);
                            res_spec_array. add( new ResourceSpecification(obja.getString("type"), obja.getString("total_count")));
                        }*/


                        res_data = new ResourceData(obj.getString("address_id"),
                                obj.getString("resource_id"),
                                obj.getString("name"),
                                obj.getString("details"),
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
                                obj.getString("sun_break5_to"),res_spec_array

                        );


                        LandingActivity.res_data_array.add(res_data);

                    }
                    // set staff list
                    adapter = new AddResAdap(getActivity() , LandingActivity.res_data_array);
                    add_res_list.setAdapter(adapter);

                    pos = LandingActivity.res_data_array.size()-1;
                    src_pos = true ;
                }




            } catch (JSONException e) {
                res_spec_array.add(new ResourceSpecification("",""));


                LandingActivity.res_data_array.add(new ResourceData( LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(),"","","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        ,"","","","","","","","","",""
                        , "","","","","","","","",res_spec_array));

                pos = 0;
                src_pos = false ;

                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }



    }
}
