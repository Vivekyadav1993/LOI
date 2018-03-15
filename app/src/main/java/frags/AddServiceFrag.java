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
import adapters.AddSerAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.HomeSerData;
import models.ResourceData;
import models.ResourceSpecification;
import models.ServiceData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/5/2017.
 */

public class AddServiceFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.add_service_list)
    ListView add_service_list;

    private Bundle bundle ;

    private int pos = 0;

    private ArrayList<HomeSerData> homeser_data_array = new ArrayList<>();

    private HttpresponseUpd callback;

    private AddSerAdap adapter;

    private  Snackbar snackbar;

    private Boolean src_pos = false ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_service_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();

        callback = this;




        homeser_data_array.add(new HomeSerData("","","","","","","","",""));

        LandingActivity.service_data_array = new ArrayList<>();

        LandingActivity.service_data_array.add(new ServiceData( LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(),"",
                "","","","","","","",homeser_data_array));

        // list seton click listener
        add_service_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle _bundle =   new Bundle();
                _bundle.putString("src","createstaff");
                _bundle.putInt("pos",i);
                _bundle.putInt("create_pos",bundle.getInt("create_pos"));  // go next page
                replaceFrag(new NewServiceFrag(), _bundle, AddStaffFrag.class.getName());


            }
        });

        //hit get service api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id())

        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


        return Mroot;
    }

    @OnClick(R.id.add_service_next_btn)
    void go(){
        Bundle _bundle =   new Bundle();
        _bundle.putString("src","def");
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));

        replaceFrag(new AddCategoryFrag(), _bundle, AddStaffFrag.class.getName());

    }


    @OnClick(R.id.add_service_btn)
    void addSer(){

        Bundle _bundle =   new Bundle();
        _bundle.putString("src","def");

        if (src_pos){
            ArrayList<HomeSerData> homeser_data_array = new ArrayList<>();
            homeser_data_array.add(new HomeSerData("","","","","","","","",""));



            LandingActivity.service_data_array.add(pos+1,new ServiceData( LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(),"",
                    "","","","","","","",homeser_data_array));
            _bundle.putInt("pos", pos+1 );

        }

        else {

            _bundle.putInt("pos", pos );

        }

        _bundle.putInt("create_pos",bundle.getInt("create_pos"));
        // go next page
       replaceFrag(new NewServiceFrag(), _bundle, AddStaffFrag.class.getName());


    }

    @OnClick(R.id.add_service_back_btn)
    void backGo(){
        getActivity().onBackPressed();
    }


    @Override
    public void getResponse(String response) {
        // parse data

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
                }
                else {

                    LandingActivity.service_data_array.clear();

                    ServiceData ser_data;

                    JSONObject obj;



                    for (int i = 0; i < arr.length(); i++) {
                        obj = arr.getJSONObject(i);

                        JSONArray spec_array = obj.getJSONArray("homeservice");


                        homeser_data_array.clear();

                        for (int j = 0; j <spec_array.length() ; j++) {
                            JSONObject obja = spec_array.getJSONObject(j);
                            homeser_data_array. add( new HomeSerData(obja.getString("id"), obja.getString("location") , obja.getString("no_of_radius")
                            ,obja.getString("timing") ,obja.getString("start_time")
                                    ,obja.getString("end_time") , obja.getString("select_staff")
                                    , obja.getString("lat"), obja.getString("longi")));
                        }

                        ser_data = new ServiceData(obj.getString("business_address_id"),
                                obj.getString("id"),
                                obj.getString("name"),
                                obj.getString("duration"),
                                obj.getString("buffer_time"),
                                obj.getString("cost")
                                , obj.getString("service_type")
                                , obj.getString("max_people_in_group")
                                , obj.getString("home_service")
                                ,homeser_data_array

                        );

                        LandingActivity.service_data_array.add(ser_data);
                    }

                    // set staff list
                    adapter = new AddSerAdap(getActivity(),LandingActivity.service_data_array);
                    add_service_list.setAdapter(adapter);

                    if (LandingActivity.service_data_array.size()==0){

                    }
                    else{

                        pos = LandingActivity.service_data_array.size()-1;
                        src_pos = true;

                    }

                }

            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

    }
}
