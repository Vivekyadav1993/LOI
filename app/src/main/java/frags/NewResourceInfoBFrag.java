package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.ResourceSpecification;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/4/2017.
 */

public class NewResourceInfoBFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    private  Bundle bundle;

    private HttpresponseUpd callback;

    private  Snackbar snackbar;

    @BindView(R.id.new_resinfob_tag)
    TextView new_resinfob_tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_resources_info_b_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();

        callback = this;

        new_resinfob_tag.setText(LandingActivity.res_data_array.get(bundle.getInt("pos")).getName());


        return Mroot;
    }

    @OnClick(R.id.new_resinfob_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.new_resinfob_next_btn)
    void next(){

        //Maint list object
        JSONObject objMainList = new JSONObject();
        JSONArray arrForA = new JSONArray();
          try {


              ArrayList<ResourceSpecification> array = new ArrayList<>();

              array = LandingActivity.res_data_array.get(bundle.getInt("pos")).getRes_spec_array();

              //prepare item array for "A"


              for (int i = 0; i <array.size() ; i++) {
                  JSONObject itemA = new JSONObject();
                  itemA.put("type", array.get(i).getRes_name());
                  itemA.put("total_count", array.get(i).getRes_qty());
                  arrForA.put(itemA);
              }





              //Finally add item arrays for "A" and "B" to main list with key


          }
          catch (JSONException e){
              snackbar = Snackbar.make(Mroot,e.getMessage(), Snackbar.LENGTH_LONG);

              snackbar.show();
          }


        // call api to create event id
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "createResource")
                .appendQueryParameter("address_id", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAddress_id())
                .appendQueryParameter("resource_id", LandingActivity.res_data_array.get(bundle.getInt("pos")).getRes_id())
                .appendQueryParameter("name",  LandingActivity.res_data_array.get(bundle.getInt("pos")).getName())
                .appendQueryParameter("details", LandingActivity.res_data_array.get(bundle.getInt("pos")).getDetails())




                .appendQueryParameter("all_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break1_from())
                .appendQueryParameter("all_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break1_to())
                .appendQueryParameter("all_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break2_from())
                .appendQueryParameter("all_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break2_to())
                .appendQueryParameter("all_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break3_from())
                .appendQueryParameter("all_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break3_to())
                .appendQueryParameter("all_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break4_from())
                .appendQueryParameter("all_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break4_to())
                .appendQueryParameter("all_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break5_from())
                .appendQueryParameter("all_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getAll_break5_to())

                .appendQueryParameter("start_date", LandingActivity.res_data_array.get(bundle.getInt("pos")).getStart_date())
                .appendQueryParameter("end_date", LandingActivity.res_data_array.get(bundle.getInt("pos")).getEnd_date())


                .appendQueryParameter("mon_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_from_time())
                .appendQueryParameter("mon_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_to_time())

                .appendQueryParameter("mon_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break1_from())
                .appendQueryParameter("mon_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break1_to())
                .appendQueryParameter("mon_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break2_from())
                .appendQueryParameter("mon_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break2_to())
                .appendQueryParameter("mon_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break3_from())
                .appendQueryParameter("mon_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break3_to())
                .appendQueryParameter("mon_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break4_from())
                .appendQueryParameter("mon_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break4_to())
                .appendQueryParameter("mon_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break5_from())
                .appendQueryParameter("mon_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getMon_break5_to())


                .appendQueryParameter("tue_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_from_time())
                .appendQueryParameter("tue_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_to_time())

                .appendQueryParameter("tue_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break1_from())
                .appendQueryParameter("tue_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break1_to())
                .appendQueryParameter("tue_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break2_from())
                .appendQueryParameter("tue_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break2_to())
                .appendQueryParameter("tue_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break3_from())
                .appendQueryParameter("tue_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break3_to())
                .appendQueryParameter("tue_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break4_from())
                .appendQueryParameter("tue_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break4_to())
                .appendQueryParameter("tue_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break5_from())
                .appendQueryParameter("tue_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getTue_break5_to())

                .appendQueryParameter("wed_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_from_time())
                .appendQueryParameter("wed_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_to_time())

                .appendQueryParameter("wed_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break1_from())
                .appendQueryParameter("wed_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break1_to())
                .appendQueryParameter("wed_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break2_from())
                .appendQueryParameter("wed_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break2_to())
                .appendQueryParameter("wed_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break3_from())
                .appendQueryParameter("wed_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break3_to())
                .appendQueryParameter("wed_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break4_from())
                .appendQueryParameter("wed_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break4_to())
                .appendQueryParameter("wed_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break5_from())
                .appendQueryParameter("wed_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getWed_break5_to())

                .appendQueryParameter("thru_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_from_time())
                .appendQueryParameter("thru_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_to_time())

                .appendQueryParameter("thru_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break1_from())
                .appendQueryParameter("thru_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break1_to())
                .appendQueryParameter("thru_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break2_from())
                .appendQueryParameter("thru_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break2_to())
                .appendQueryParameter("thru_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break3_from())
                .appendQueryParameter("thru_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break3_to())
                .appendQueryParameter("thru_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break4_from())
                .appendQueryParameter("thru_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break4_to())
                .appendQueryParameter("thru_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break5_from())
                .appendQueryParameter("thru_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getThru_break5_to())

                .appendQueryParameter("fri_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_from_time())
                .appendQueryParameter("fri_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_to_time())

                .appendQueryParameter("fri_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break1_from())
                .appendQueryParameter("fri_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break1_to())
                .appendQueryParameter("fri_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break2_from())
                .appendQueryParameter("fri_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break2_to())
                .appendQueryParameter("fri_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break3_from())
                .appendQueryParameter("fri_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break3_to())
                .appendQueryParameter("fri_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break4_from())
                .appendQueryParameter("fri_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break4_to())
                .appendQueryParameter("fri_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break5_from())
                .appendQueryParameter("fri_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getFri_break5_to())


                .appendQueryParameter("sat_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_from_time())
                .appendQueryParameter("sat_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_to_time())

                .appendQueryParameter("sat_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break1_from())
                .appendQueryParameter("sat_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break1_to())
                .appendQueryParameter("sat_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break2_from())
                .appendQueryParameter("sat_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break2_to())
                .appendQueryParameter("sat_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break3_from())
                .appendQueryParameter("sat_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break3_to())
                .appendQueryParameter("sat_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break4_from())
                .appendQueryParameter("sat_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break4_to())
                .appendQueryParameter("sat_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break5_from())
                .appendQueryParameter("sat_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSat_break5_to())

                .appendQueryParameter("sun_from_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_from_time())
                .appendQueryParameter("sun_to_time", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_to_time())

                .appendQueryParameter("sun_break1_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break1_from())
                .appendQueryParameter("sun_break1_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break1_to())
                .appendQueryParameter("sun_break2_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break2_from())
                .appendQueryParameter("sun_break2_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break2_to())
                .appendQueryParameter("sun_break3_from",LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break3_from())
                .appendQueryParameter("sun_break3_to",LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break3_to())
                .appendQueryParameter("sun_break4_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break4_from())
                .appendQueryParameter("sun_break4_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break4_to())
                .appendQueryParameter("sun_break5_from", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break5_from())
                .appendQueryParameter("sun_break5_to", LandingActivity.res_data_array.get(bundle.getInt("pos")).getSun_break5_to())
        .appendQueryParameter("specification",arrForA.toString())

        ;


        Log.e("url" , builder.build().toString());


        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


        //replaceFrag(new AddResourcesFrag(), new Bundle() , NewResourceInfoBFrag.class.getName());
    }

    @OnClick(R.id.new_resinfob_workingdays_btn)
    void godate(){

        Bundle _bundle = new Bundle();


        _bundle.putString("src",bundle.getString("src"));
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));
        replaceFrag(new NewResourcesWorkingHourFrag(), _bundle , NewResourceInfoBFrag.class.getName());
    }

    @OnClick(R.id.new_resinfob_breaks_btn)
    void gobreak(){
        Bundle _bundle = new Bundle();


        _bundle.putString("src",bundle.getString("src"));
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));
        replaceFrag(new NewResoucesBreakTimeFrag(), _bundle , NewResourceInfoBFrag.class.getName());
    }

    @OnClick(R.id.new_resinfob_specification_btn)
    void specification(){
        Bundle _bundle = new Bundle();


        _bundle.putString("src",bundle.getString("src"));
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));

        replaceFrag(new AddResSpecificationFrag() , new Bundle() ,NewResourceInfoBFrag.class.getName() );
    }

    @Override
    public void getResponse(String response) {

        Log.e("res", response);

        if (response.contains("Error :")){
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else{

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("resource_id").equals("0")){
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else{

                    // save data

                    LandingActivity.res_data_array.get(bundle.getInt("pos")).setRes_id(obj.getString("resource_id"));




                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();




                    Bundle _bundle  = new Bundle();


                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));


                    replaceFrag(new AddResourcesFrag(),_bundle , BusinessDetailsFrag.class.getName());


                }





            }
            catch (JSONException e){
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }

    }
}
