package frags;

import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapters.AddResAdap;
import adapters.HomeSerAddAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import helper.LocationUpd;
import models.HomeSerData;
import models.ResourceData;
import models.ResourceSpecification;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/5/2017.
 */

public class HomeServiceInfoFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.homeser_btn)
    Switch homeser_btn;

    @BindView(R.id.homeser_name_input)
    AutoCompleteTextView place_name;

    @BindView(R.id.homeser_kmradius_input)
    EditText km_radius;

    @BindView(R.id.homeser_traveltime_input)
    EditText travel_time;

    @BindView(R.id.homeser_starttime_btn)
    TextView homeser_starttime_btn;

    @BindView(R.id.homeser_endtime_btn)
    TextView homeser_endtime_btn;

    @BindView(R.id.homeser_selectstaff_btn)
    TextView homeser_selectstaff_btn;




    @BindView(R.id.homeser_list)
    ListView homeser_list;


   private Snackbar snackbar;

    private HomeSerAddAdap adapter ;

    private  Bundle bundle;

    private  HttpresponseUpd callback;



    protected String[] receivers , receiver_id ;
    protected ArrayList<String> selectedReceivers = new ArrayList<>();

    private ArrayList<HomeSerData> home_ser_array = new ArrayList<>();

    private String select_staff = "" ;



    private ArrayList<String> address_list;
    private ArrayAdapter<String> autocomplete_adapter;

    private String post_tag = "";




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.home_service_info_screen, null);
        ButterKnife.bind(this, Mroot);

        callback = this;

        bundle = getArguments();

        if (bundle.getString("src").equals("def")){



        }
        else{

            home_ser_array = LandingActivity.service_data_array.get(bundle.getInt("pos")).getData();

            if (home_ser_array.size()==0){

            }
            else {


                    homeser_btn.setChecked(true);


            }
            adapter = new HomeSerAddAdap(getActivity(), home_ser_array);
            homeser_list.setAdapter(adapter);

        }

        LandingActivity.service_data_array.get(bundle.getInt("pos")).setHome_service("Yes");

        homeser_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    LandingActivity.service_data_array.get(bundle.getInt("pos")).setHome_service("Yes");
                }
                else{
                    LandingActivity.service_data_array.get(bundle.getInt("pos")).setHome_service("No");

                }
            }
        });


        place_name.setThreshold(0);

        address_list = new ArrayList<String>();

        place_name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Log.e("gfgj", s.toString());

                address_list = new ArrayList<String>();

                String input = "" , url;



                try {
                    input = "input=" + URLEncoder.encode(s.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(getActivity(), "UnsupportedEncodingException occurred!!Kindly try again.", Toast.LENGTH_SHORT).show();
                }

                String output = "json";
                String parameter = input + "&types=geocode&sensor=true&key="
                        + AppConstants.Google_place_APi_Key;

                url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
                        + output + "?" + parameter;

                post_tag = "getadd";
                hitApi(url,callback);


            }
        });




        // hit staff api

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getStaff")
                .appendQueryParameter("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());


        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        }
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }




        return Mroot;
    }

    @OnClick(R.id.homeser_next_btn)
    void next(){
        Bundle _bundle =   new Bundle();
        _bundle.putString("src","create");
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));
        // go next page
        replaceFrag(new NewServiceFrag(), _bundle, HomeServiceInfoFrag.class.getName());
    }

    @OnClick(R.id.homeser_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.homeser_selectstaff_btn)
    void selectStaff(){
        showSelectReceiversDialog();

    }

    @OnClick(R.id.homeser_starttime_btn)
        void getStartTime(){
        if (homeser_endtime_btn.getText().toString().equals("End Time")) {
            getTime( homeser_starttime_btn, "");
        }
        else{
            getTime( homeser_starttime_btn, homeser_endtime_btn.getText().toString());


        }

       // getTime( homeser_starttime_btn, homeser_endtime_btn.getText().toString());


    }

    @OnClick(R.id.homeser_endtime_btn)
    void getEndTime(){
       /* if (homeser_endtime_btn.getText().toString().equals("End Time")||homeser_starttime_btn.getText().toString().equals("Start Time")) {
            AppUtils.getTime(getActivity(), homeser_endtime_btn, "","");
        }
        else{
            AppUtils.getTime(getActivity(), homeser_endtime_btn, homeser_starttime_btn.getText().toString(),"");

        }  */
        getTimeback( homeser_endtime_btn, homeser_starttime_btn.getText().toString());

    }


    protected void onChangeSelectedReceivers() {
        StringBuilder stringBuilder = new StringBuilder();

        for(CharSequence receivers : selectedReceivers)
            stringBuilder.append(receivers + ",");

        select_staff = stringBuilder.toString();
    }

    protected void showSelectReceiversDialog() {
        boolean[] checkedReceivers = new boolean[receivers.length];
        int count = receivers.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceivers.contains(receiver_id[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                    selectedReceivers.add(receiver_id[which]);
                else
                    selectedReceivers.remove(receiver_id[which]);

                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Select Staff")
                .setMultiChoiceItems(receivers, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

}


    @OnClick(R.id.homeser_save_btn)
    void save(){

        if (place_name.getText().toString().equals("")){
            snackbar = Snackbar
                    .make(Mroot, "place_name cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (km_radius.getText().toString().equals("")){
            snackbar = Snackbar
                    .make(Mroot, "Km of radius cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (travel_time.getText().toString().equals("")){
            snackbar = Snackbar
                    .make(Mroot, "Travel Time cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (homeser_starttime_btn.getText().toString().equals("Start Time")){
            snackbar = Snackbar
                    .make(Mroot, "Start Time cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (homeser_endtime_btn.getText().toString().equals("End Time")){
            snackbar = Snackbar
                    .make(Mroot, "End Time cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else if (select_staff.equals("")){
            snackbar = Snackbar
                    .make(Mroot, "Please select staff!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else{

            // hit api to change address into  lat long
            calculateLatLongi("http://maps.google.com/maps/api/geocode/json?address=" + place_name.getText().toString().replaceAll(" ","%20") + "&sensor=false");
        }

    }

    @Override
    public void getResponse(String response) {

        Log.e("res", response);


        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else if (post_tag.equals("")){

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                receivers = new String[arr.length()];
                receiver_id = new String[arr.length()];


                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    receivers[i] = obj.getString("name");
                    receiver_id[i] = obj.getString("id");

                }










            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
        else{

            try {
                JSONObject obj = new JSONObject(response);

                JSONArray ja = obj.getJSONArray("predictions");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");

                    address_list.add(description);
                }

                autocomplete_adapter = new ArrayAdapter<String>(
                        getActivity(),
                        android.R.layout.simple_list_item_1, address_list) {
                    @Override
                    public View getView(int position,
                                        View convertView, ViewGroup parent) {
                        View view = super.getView(position,
                                convertView, parent);
                        TextView text = (TextView) view
                                .findViewById(android.R.id.text1);
                        text.setTextColor(Color.BLACK);
                        return view;
                    }
                };

                Log.e("array", address_list.size() + "---" + response);
                place_name.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }




        }

    }




    // Method for fetch address
    private void calculateLatLongi(final String url ) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response)throws IllegalArgumentException {


                        Log.e("response",url+"---"+response.toString());

                        try {

                            JSONArray results =       response.getJSONArray("results");

                            JSONObject obj = results.getJSONObject(0);

                            JSONObject geometry = obj.getJSONObject("geometry");

                            JSONObject location = geometry.getJSONObject("location");

                            Location loc = new Location("");
                            loc.setLatitude(Double.valueOf(location.getString("lat")));
                            loc.setLongitude(Double.valueOf(location.getString("lng")));


                            // save the value and populate list view.
                            home_ser_array.add(new HomeSerData("", place_name.getText().toString(),
                                    km_radius.getText().toString(),
                                    travel_time.getText().toString(),
                                    homeser_starttime_btn.getText().toString(),
                                    homeser_endtime_btn.getText().toString(),
                                    select_staff,
                                    location.getString("lat"),
                                    location.getString("lng")
                            ));

                            //save data
                            LandingActivity.service_data_array.get(bundle.getInt("pos")).setData(home_ser_array);

                            adapter = new HomeSerAddAdap(getActivity(), home_ser_array);
                            homeser_list.setAdapter(adapter);

                            place_name.setText("");
                            km_radius.setText("");
                            travel_time.setText("");
                            homeser_starttime_btn.setText("Start Time");
                            homeser_endtime_btn.setText("End Time");
                            selectedReceivers.clear();




                        } catch (JSONException e) {
                            snackbar = Snackbar
                                    .make(Mroot, "Error occurred! "+e.getMessage(), Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }





                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                snackbar = Snackbar
                        .make(Mroot, "Error occurred! "+error.getMessage(), Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        Volley.newRequestQueue(getActivity()).add(jsonObjReq);
    }


}
