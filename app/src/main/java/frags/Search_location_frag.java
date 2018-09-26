package frags;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.HelperFrags;
import helper.HttpresponseUpd;


/**
 * Created by teknik on 9/26/2017.
 */

public class Search_location_frag extends HelperFrags implements HttpresponseUpd, LocationListener {

    private View Mroot;

    @BindView(R.id.locd_autocomplte_input)
    AutoCompleteTextView locd_autocomplte_input;

    @BindView(R.id.locd_main_tag)
    TextView locd_main_tag;

    @BindView(R.id.locd_current_loc_btn)
    TextView locd_current_loc_btn;

    @BindView(R.id.locd_saved_add_list)
    ListView locd_saved_add_list;

    private SharedPreferences.Editor editor;

    private ArrayList<String> address_list;
    private ArrayAdapter<String> autocomplete_adapter;

    private HttpresponseUpd httpresponseUpd;

    LocationManager locationManager;
    String provider;

    private Sharedpreferences mPref;

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.change_location_dialog_screen, null);
        ButterKnife.bind(this, Mroot);
        httpresponseUpd = this;
        mPref= Sharedpreferences.getUserDataObj(getActivity());
        locd_autocomplte_input.setThreshold(0);
        address_list = new ArrayList<String>();
        locd_autocomplte_input.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Log.e("gfgj", s.toString());

                address_list = new ArrayList<String>();

                String input = "", url;


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

                hitApi(url, httpresponseUpd);


            }
        });


        return Mroot;
    }

    @Override
    public void onLocationChanged(Location location) {
        // Getting reference to TextView tv_longitude
     /*   TextView tvLongitude = (TextView)findViewById(R.id.tv_longitude);

        // Getting reference to TextView tv_latitude
        TextView tvLatitude = (TextView)findViewById(R.id.tv_latitude);
*/
        // Setting Current Longitude
    /*    tvLongitude.setText("Longitude:" + location.getLongitude());

        // Setting Current Latitude
        tvLatitude.setText("Latitude:" + location.getLatitude() );
      */

        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(getContext(), Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            String current_address = address + "," + city + "," + state + "," + country;
            Log.d("SLF", "address" + address + "ss" + city + "statr" + state + "country" + country);
            String[] add_array = address.split(",");

            // save current location
            editor = AppConstants.app_data.edit();
            editor.putString("address", current_address);
            editor.putString("short_add", add_array[0]);
            editor.putString("lat", location.getLatitude() + "");
            editor.putString("long", location.getLongitude() + "");
            editor.commit();

            Intent intent = new Intent(getActivity(), LandingActivity.class);
            intent.putExtra("src", "search_current");
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  Intent intent = new Intent(getActivity() , LandingActivity.class);
        intent.putExtra("src","search_current");
        startActivity(intent);*/
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @OnClick(R.id.locd_current_loc_btn)
    void goLoc() {

       /* SharedPreferences.Editor editor = AppConstants.app_data.edit();
        editor.putString("address", "");
        editor.putString("short_add","Life On Internet");
        editor.commit();
        Intent intent = new Intent(getActivity() , LandingActivity.class);
        intent.putExtra("src","search_current");
        startActivity(intent);
*/
        currentLocationFun();

    }

    @SuppressLint("MissingPermission")
    private void currentLocationFun() {
        // Getting LocationManager object
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {

            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(provider);

            locationManager.requestLocationUpdates(provider, /*200000*/20000000, 1, (LocationListener) this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "No Provider Found", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.locd_back_button)
    void goBack() {
        Intent intent = new Intent(getActivity(), LandingActivity.class);
        intent.putExtra("src", "deff");
        startActivity(intent);
    }


    @OnClick(R.id.locd_done_button)
    void goDone() {

        if (locd_autocomplte_input.getText().toString().equals("")) {

        } else {
            String[] add_array = locd_autocomplte_input.getText().toString().split(",");

            mPref.setMainAddress(locd_autocomplte_input.getText().toString());
            // save current address
            SharedPreferences.Editor editor = AppConstants.app_data.edit();
            editor.putString("address", locd_autocomplte_input.getText().toString());
            editor.putString("short_add", add_array[0]);
            editor.putString("lat", "");
            editor.putString("long", "");
            editor.commit();
        }

        Intent intent = new Intent(getActivity(), LandingActivity.class);
        intent.putExtra("src", "search_save");
        startActivity(intent);

    }

    @Override
    public void getResponse(String response) {

        if (response.equals("error")) {

        } else {

            Log.e("res", response);
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
                locd_autocomplte_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }

        }


    }


}
