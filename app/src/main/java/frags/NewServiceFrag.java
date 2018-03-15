package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.HomeSerData;
import models.ResourceSpecification;
import models.ServiceData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/5/2017.
 */

public class NewServiceFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    private Bundle bundle;

    @BindView(R.id.new_serinfo_tag)
    TextView new_serinfo_tag;

    @BindView(R.id.new_serinfo_name_input)
    EditText new_serinfo_name_input;

    @BindView(R.id.new_serinfo_duration_input)
    EditText new_serinfo_duration_input;

    @BindView(R.id.new_serinfo_buffer_input)
    EditText new_serinfo_buffer_input;

    @BindView(R.id.new_serinfo_cost_input)
    EditText new_serinfo_cost_input;

    @BindView(R.id.new_serinfo_maxpeople_input)
    EditText new_serinfo_maxpeople_input;

    @BindView(R.id.new_serinfo_group_radio)
    RadioGroup new_serinfo_group_radio;

    @BindView(R.id.new_serinfo_oneonone_radiobtn)
    RadioButton new_serinfo_oneonone_radiobtn;


    @BindView(R.id.new_serinfo_group_radiobtn)
    RadioButton new_serinfo_group_radiobtn;

    @BindView(R.id.max_people_layout)
    RelativeLayout max_people_layout;

    private ServiceData serviceData;

    private HttpresponseUpd callback;

    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_service_screen, null);
        ButterKnife.bind(this, Mroot);

        callback = this;

        bundle = getArguments();

        serviceData = LandingActivity.service_data_array.get(bundle.getInt("pos"));

        if (bundle.getString("src").equals("def")){

        }
        else{

            new_serinfo_tag.setText(serviceData.getName());

            new_serinfo_name_input.setText(serviceData.getName());

            new_serinfo_duration_input.setText(serviceData.getDuration());

            new_serinfo_buffer_input.setText(serviceData.getBuffer_time());

            new_serinfo_cost_input.setText(serviceData.getCost());

            new_serinfo_maxpeople_input.setText(serviceData.getMax_people_in_group());


            if (serviceData.getService_type().equals("One")){
                new_serinfo_oneonone_radiobtn.setChecked(true);
            }
            else if (serviceData.getService_type().equals("Group")){
                new_serinfo_group_radiobtn.setChecked(true);
            }
            else{

            }
        }


        new_serinfo_group_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if (i==R.id.new_serinfo_oneonone_radiobtn){

                    if (new_serinfo_oneonone_radiobtn.isChecked()) {
                        LandingActivity.service_data_array.get(bundle.getInt("pos"))
                                .setService_type("One");
                        max_people_layout.setVisibility(View.GONE);
                    }
                    else{
                        LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("");
                    }
                }
                else{
                    if (new_serinfo_group_radiobtn.isChecked()) {
                        LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("Group");
                        max_people_layout.setVisibility(View.VISIBLE);
                    }
                    else{
                        LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("");
                    }
                }
            }
        });



        return Mroot;
    }

    @OnClick(R.id.new_serinfo_homeservice_btn)
    void homeService(){

        LandingActivity.service_data_array.get(bundle.getInt("pos"))
                .setName(new_serinfo_name_input.getText().toString());

        LandingActivity.service_data_array.get(bundle.getInt("pos"))
                .setDuration(new_serinfo_duration_input.getText().toString());

        LandingActivity.service_data_array.get(bundle.getInt("pos"))
                .setBuffer_time(new_serinfo_buffer_input.getText().toString());

        LandingActivity.service_data_array.get(bundle.getInt("pos"))
                .setCost(new_serinfo_cost_input.getText().toString());

        LandingActivity.service_data_array.get(bundle.getInt("pos"))
                .setMax_people_in_group(new_serinfo_maxpeople_input.getText().toString());

        if (new_serinfo_oneonone_radiobtn.isChecked()) {
            LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("One");
        }


        if (new_serinfo_group_radiobtn.isChecked()) {
            LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("Group");
        }

        LandingActivity.service_data_array.get(bundle.getInt("pos")).setHome_service("No");

        Bundle _bundle = new Bundle();

        _bundle.putString("src",bundle.getString("src"));
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));
        // go next page
        replaceFrag(new HomeServiceInfoFrag(), _bundle, NewServiceFrag.class.getName());
    }

    @OnClick(R.id.new_serinfo_back_btn)
    void back(){
        getActivity().onBackPressed();

    }

    @OnClick(R.id.new_serinfo_next_btn)
    void next(){

        if (new_serinfo_name_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Service name cannot blank!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (new_serinfo_duration_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Service duration cannot blank!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (new_serinfo_buffer_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Service buffer time cannot blank!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else {
            JSONArray arrForA = new JSONArray();
            if (serviceData.getData().size()==1&&serviceData.getData().get(0).getLocation().equals("")){

            }
            else{
                //Maint list object
                JSONObject objMainList = new JSONObject();

                try {


                    ArrayList<HomeSerData> array = new ArrayList<>();

                    array = serviceData.getData();

                    //prepare item array for "A"


                    for (int i = 0; i <array.size() ; i++) {
                        JSONObject itemA = new JSONObject();
                        itemA.put("homeservice_id", array.get(i).getId());
                        itemA.put("location", array.get(i).getLocation());
                        itemA.put("no_of_radius", array.get(i).getNo_of_radius());
                        itemA.put("timing", array.get(i).getTravel_time());
                        itemA.put("start_time", array.get(i).getStart_time());
                        itemA.put("end_time", array.get(i).getEnd_time());
                        itemA.put("select_staff", array.get(i).getSelect_staff());
                        itemA.put("lat", array.get(i).getLat());
                        itemA.put("longi", array.get(i).getLongi());

                        arrForA.put(itemA);
                    }





                    //Finally add item arrays for "A" and "B" to main list with key


                }
                catch (JSONException e){
                    snackbar = Snackbar.make(Mroot,e.getMessage(), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
            }

            LandingActivity.service_data_array.get(bundle.getInt("pos"))
                    .setName(new_serinfo_name_input.getText().toString());

            LandingActivity.service_data_array.get(bundle.getInt("pos"))
                    .setDuration(new_serinfo_duration_input.getText().toString());

            LandingActivity.service_data_array.get(bundle.getInt("pos"))
                    .setBuffer_time(new_serinfo_buffer_input.getText().toString());

            LandingActivity.service_data_array.get(bundle.getInt("pos"))
                    .setCost(new_serinfo_cost_input.getText().toString());

            LandingActivity.service_data_array.get(bundle.getInt("pos"))
                    .setMax_people_in_group(new_serinfo_maxpeople_input.getText().toString());

            if (new_serinfo_oneonone_radiobtn.isChecked()) {
                LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("One");
            }


            if (new_serinfo_group_radiobtn.isChecked()) {
                LandingActivity.service_data_array.get(bundle.getInt("pos")).setService_type("Group");
            }

            LandingActivity.service_data_array.get(bundle.getInt("pos")).setHome_service("No");


            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath("new_service")
                    .appendPath("api.php")
                    .appendQueryParameter("action", "createService")
                    .appendQueryParameter("service_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getSer_id())
                    .appendQueryParameter("address_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id())
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id",""))
                    .appendQueryParameter("name", LandingActivity.service_data_array.get(bundle.getInt("pos")).getName())
                    .appendQueryParameter("duration",LandingActivity.service_data_array.get(bundle.getInt("pos")).getDuration())
                    .appendQueryParameter("buffer_time", LandingActivity.service_data_array.get(bundle.getInt("pos")).getBuffer_time())
                    .appendQueryParameter("cost",LandingActivity.service_data_array.get(bundle.getInt("pos")).getCost())
                    .appendQueryParameter("service_type", LandingActivity.service_data_array.get(bundle.getInt("pos")).getService_type())
                    .appendQueryParameter("max_people_in_group", LandingActivity.service_data_array.get(bundle.getInt("pos")).getMax_people_in_group())
                    .appendQueryParameter("home_service",LandingActivity.service_data_array.get(bundle.getInt("pos")).getHome_service())
                    .appendQueryParameter("homeservice",arrForA.toString())

            ;

Log.e("url",builder.build().toString() );
            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

        }



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

                if (obj.getString("service_id").equals("0")){
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else{

                    // save data

                    LandingActivity.service_data_array.get(bundle.getInt("pos")).setSer_id(obj.getString("service_id"));




                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();

                    Bundle _bundle  = new Bundle();


                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));
                    _bundle.putString("ser_id", obj.getString("service_id"));

                    replaceFrag(new NewServiceStaffSelectionFrag(), _bundle , NewServiceFrag.class.getName());


                }





            }
            catch (JSONException e){
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }

    }
}
