package frags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import helper.LocationUpd;
import models.BusinessHourData;
import models.myadsaddress.MyAdsAddress;


/**
 * Created by teknik on 9/27/2017.
 */

public class BusinessDetailsFrag extends HelperFrags implements HttpresponseUpd, LocationUpd {

    private View Mroot;


    @BindView(R.id.bd_name_input)
    EditText bd_name_input;

    @BindView(R.id.bd_phone_input)
    EditText bd_phone_input;

    @BindView(R.id.bd_industry_name_picker)
    Spinner bd_industry_name_picker;


    //1st add
    @BindView(R.id.bd_add1_input)
    AutoCompleteTextView bd_add1_input;


    //2nd add
    @BindView(R.id.bd_add2_layout)
    RelativeLayout bd_add2_layout;


    @BindView(R.id.bd_add2_input)
    AutoCompleteTextView bd_add2_input;


    //3rd add
    @BindView(R.id.bd_add3_layout)
    RelativeLayout bd_add3_layout;

    @BindView(R.id.bd_add3_input)
    AutoCompleteTextView bd_add3_input;


    //4th add
    @BindView(R.id.bd_add4_layout)
    RelativeLayout bd_add4_layout;

    @BindView(R.id.bd_add4_input)
    AutoCompleteTextView bd_add4_input;


    //5th add
    @BindView(R.id.bd_add5_layout)
    RelativeLayout bd_add5_layout;

    @BindView(R.id.bd_add5_input)
    AutoCompleteTextView bd_add5_input;

    /*@Bind(R.id.bd_scroll)
    ScrollView bd_scroll;*/


    private HttpresponseUpd callback;

    private String[] industry_name_array, industry_id_array;


    private Snackbar snackbar;

    private Bundle bundle;

    private Uri.Builder builder;
    private ArrayList<String> address_list;
    private HttpresponseUpd httpresponseUpd;
    private String postTag;
    private ArrayAdapter<String> autocomplete_adapter;
    private android.location.Geocoder geoCoder;

    private Sharedpreferences mPref;
    private String post_tag;
    String compareValue = "Doctor";

    String address_id, address, start_date, end_date, mon_from_time, mon_to_time, tue_from_time, tue_to_time, wed_from_time, wed_to_time,
            thru_from_time, thru_to_time, fri_from_time, fri_to_time, sat_from_time, sat_to_time, sun_from_time, sun_to_time;

    String address_id_1, address_id_2, address_id_3, address_id_4, address_id_5;
    private String industry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.buiseness_details_screen, null);
        ButterKnife.bind(this, Mroot);


        bundle = getArguments();
        bd_add1_input.setThreshold(1);
        bd_add2_input.setThreshold(1);
        bd_add3_input.setThreshold(1);
        bd_add4_input.setThreshold(1);
        bd_add5_input.setThreshold(1);
        String coming_from = bundle.getString("comming_from");
        httpresponseUpd = this;

        bd_add1_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                address_list = new ArrayList<String>();

                String input = "", url;
                try {
                    input = "input=" + URLEncoder.encode(editable.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(getActivity(), "UnsupportedEncodingException occurred!!Kindly try again.", Toast.LENGTH_SHORT).show();
                }

                String output = "json";
                String parameter = input + "&types=geocode&sensor=true&key="
                        + AppConstants.Google_place_APi_Key;

                url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
                        + output + "?" + parameter;
                postTag = "address_search";
                hitApi(url, httpresponseUpd);

            }
        });
        bd_add2_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                address_list = new ArrayList<String>();

                String input = "", url;


                try {
                    input = "input=" + URLEncoder.encode(editable.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(getActivity(), "UnsupportedEncodingException occurred!!Kindly try again.", Toast.LENGTH_SHORT).show();
                }

                String output = "json";
                String parameter = input + "&types=geocode&sensor=true&key="
                        + AppConstants.Google_place_APi_Key;

                url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
                        + output + "?" + parameter;
                postTag = "address_search_2";
                hitApi(url, httpresponseUpd);

            }
        });
        bd_add3_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                address_list = new ArrayList<String>();

                String input = "", url;


                try {
                    input = "input=" + URLEncoder.encode(editable.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(getActivity(), "UnsupportedEncodingException occurred!!Kindly try again.", Toast.LENGTH_SHORT).show();
                }

                String output = "json";
                String parameter = input + "&types=geocode&sensor=true&key="
                        + AppConstants.Google_place_APi_Key;

                url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
                        + output + "?" + parameter;
                postTag = "address_search_3";
                hitApi(url, httpresponseUpd);

            }
        });
        bd_add4_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                address_list = new ArrayList<String>();

                String input = "", url;


                try {
                    input = "input=" + URLEncoder.encode(editable.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(getActivity(), "UnsupportedEncodingException occurred!!Kindly try again.", Toast.LENGTH_SHORT).show();
                }

                String output = "json";
                String parameter = input + "&types=geocode&sensor=true&key="
                        + AppConstants.Google_place_APi_Key;

                url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
                        + output + "?" + parameter;
                postTag = "address_search_4";
                hitApi(url, httpresponseUpd);

            }
        });
        bd_add5_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                address_list = new ArrayList<String>();

                String input = "", url;


                try {
                    input = "input=" + URLEncoder.encode(editable.toString(), "utf-8");
                } catch (UnsupportedEncodingException e1) {
                    Toast.makeText(getActivity(), "UnsupportedEncodingException occurred!!Kindly try again.", Toast.LENGTH_SHORT).show();
                }

                String output = "json";
                String parameter = input + "&types=geocode&sensor=true&key="
                        + AppConstants.Google_place_APi_Key;

                url = "https://maps.googleapis.com/maps/api/place/autocomplete/"
                        + output + "?" + parameter;
                postTag = "address_search_5";
                hitApi(url, httpresponseUpd);

            }
        });
        bd_phone_input.setFilters(new InputFilter[]{Utils.sepcialCharRemovalFilter});
        bd_phone_input.setFilters(new InputFilter[]{Utils.sepcialCharRemovalFilter, new InputFilter.LengthFilter(10)});
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        callback = this;


        if (coming_from.equalsIgnoreCase("adpublishpage")) {

            //hit api
            builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "getIndustry");

            postTag = "getIndustry";
            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }

        } else if (coming_from.equalsIgnoreCase("edit_page")) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "getbusinessList")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""));

            String myUrl = builder.build().toString();
            Log.e("business_address", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity())) {

                postTag = "business_address";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }
        return Mroot;
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getView() != null) {
                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
                            Bundle bundle = new Bundle();
                            bundle.putString("src", "def");

                            replaceFrag(new AdPublishSelectionFrag(), bundle, BusinessDetailsFrag.class.getName());
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            Log.e("error", "" + e);
        }
    }

    private void saveData(String address, String address_id_) {

        Bundle bundle = new Bundle();

        if (bd_name_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry name cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (bd_phone_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry phone number cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (bd_industry_name_picker.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(Mroot, "Select Industry!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            //save business details
            Bundle bundle1 = new Bundle();

            bundle1.putString("bussiness_name", bd_name_input.getText().toString());
            bundle1.putString("bussiness_phone", bd_phone_input.getText().toString());
            bundle1.putString("bussiness_indusrty", bd_phone_input.getText().toString());
            bundle1.putString("address", address);
            bundle1.putString("indusrty_id", industry_id_array[bd_industry_name_picker.getSelectedItemPosition()]);
            bundle1.putString("indusrty_name", industry_name_array[bd_industry_name_picker.getSelectedItemPosition()]);
            bundle1.putString("address_id_", address_id_);

            replaceFrag(new BusinessHourFrag(), bundle1, BusinessDetailsFrag.class.getName());

        }


    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    // 1st next button
    @OnClick(R.id.bd_add_one_next_btn)
    void oneNext() {
        // convertAddress(bd_add1_input.getText().toString());

        String address = bd_add1_input.getText().toString();

        try {
            Geocoder gc = new Geocoder(getContext());
            if (gc.isPresent()) {
                List<Address> list = null;
                try {
                    list = gc.getFromLocationName(address, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address addres = list.get(0);
                double lat = addres.getLatitude();
                double lng = addres.getLongitude();

                mPref.setBusinessLat(String.valueOf(lat));
                mPref.setBusinessLong(String.valueOf(lng));

                Log.d("address", "lat" + lat + "lng" + lng);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
      /*  if (bd_add1_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        if (bd_name_input.getText().toString().equals("")) {

            bd_name_input.setError("Industry name cannot be blank!!!");
         /*   snackbar = Snackbar.make(Mroot, "Industry name cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else if (bd_phone_input.getText().toString().equals("")) {

            bd_phone_input.setError("Industry phone number cannot be blank!!!");
          /*  snackbar = Snackbar.make(Mroot, "Industry phone number cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_industry_name_picker.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(Mroot, "Select Industry!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (bd_add1_input.getText().toString().equals("")) {
            bd_add1_input.setError("Industry address cannot be blank!!!");
           /* snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/

        } else {

            saveData(bd_add1_input.getText().toString(), address_id_1);
        }
    }

    @OnClick({R.id.bd_add1_get_current_location_iv, R.id.bd_add2_get_current_location_iv, R.id.bd_add3_get_current_location_iv, R.id.bd_add4_get_current_location_iv,
            R.id.bd_add5_get_current_location_iv})
    void onClick(View view) {
        switch (view.getId()) {

            case R.id.bd_add1_get_current_location_iv:
                getCurrentLocation();
                break;
            case R.id.bd_add2_get_current_location_iv:
                bd_add2_input.setText("" + AppConstants.app_data.getString("address", "Life On Internet"));
                break;
            case R.id.bd_add3_get_current_location_iv:
                bd_add3_input.setText("" + AppConstants.app_data.getString("address", "Life On Internet"));
                break;
            case R.id.bd_add4_get_current_location_iv:
                bd_add4_input.setText("" + AppConstants.app_data.getString("address", "Life On Internet"));
                break;
            case R.id.bd_add5_get_current_location_iv:
                bd_add5_input.setText("" + AppConstants.app_data.getString("address", "Life On Internet"));
                break;

        }
    }

    private void getCurrentLocation() {

        bd_add1_input.setText("" + AppConstants.app_data.getString("address", "Life On Internet"));

    }

    // 2nd next button
    @OnClick(R.id.bd_add_two_next_btn)
    void twoNext() {

        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

          /*  if (bd_add1_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        if (bd_name_input.getText().toString().equals("")) {

            bd_name_input.setError("Industry name cannot be blank!!!");
         /*   snackbar = Snackbar.make(Mroot, "Industry name cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_phone_input.getText().toString().equals("")) {

            bd_phone_input.setError("Industry phone number cannot be blank!!!");
          /*  snackbar = Snackbar.make(Mroot, "Industry phone number cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_industry_name_picker.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(Mroot, "Select Industry!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (bd_add2_input.getText().toString().equals("")) {

            bd_add2_input.setError("Industry address cannot be blank!!!");
/*
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/

        } else {

            saveData(bd_add2_input.getText().toString(), address_id_2);

        }

    }


    // 3rd next button
    @OnClick(R.id.bd_add_three_next_btn)
    void threeNext() {

        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
          /*  if (bd_add1_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        if (bd_name_input.getText().toString().equals("")) {

            bd_name_input.setError("Industry name cannot be blank!!!");
         /*   snackbar = Snackbar.make(Mroot, "Industry name cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_phone_input.getText().toString().equals("")) {

            bd_phone_input.setError("Industry phone number cannot be blank!!!");
          /*  snackbar = Snackbar.make(Mroot, "Industry phone number cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_industry_name_picker.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(Mroot, "Select Industry!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (bd_add3_input.getText().toString().equals("")) {

            bd_add3_input.setError("Industry address cannot be blank!!!");
           /* snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else {

            saveData(bd_add3_input.getText().toString(), address_id_3);

        }
    }


    // 4th next button
    @OnClick(R.id.bd_add_four_next_btn)
    void fourNext() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
          /*  if (bd_add1_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        if (bd_name_input.getText().toString().equals("")) {

            bd_name_input.setError("Industry name cannot be blank!!!");
         /*   snackbar = Snackbar.make(Mroot, "Industry name cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_phone_input.getText().toString().equals("")) {

            bd_phone_input.setError("Industry phone number cannot be blank!!!");
          /*  snackbar = Snackbar.make(Mroot, "Industry phone number cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else if (bd_industry_name_picker.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(Mroot, "Select Industry!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (bd_add4_input.getText().toString().equals("")) {
            bd_add4_input.setError("Industry address cannot be blank!!!");
          /*  snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else {
            saveData(bd_add4_input.getText().toString(), address_id_4);
        }
    }

    // 5th next button
    @OnClick(R.id.bd_add_fifth_next_btn)
    void fifthNext() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
          /*  if (bd_add1_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        if (bd_name_input.getText().toString().equals("")) {

            bd_name_input.setError("Industry name cannot be blank!!!");
         /*   snackbar = Snackbar.make(Mroot, "Industry name cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_phone_input.getText().toString().equals("")) {

            bd_phone_input.setError("Industry phone number cannot be blank!!!");
          /*  snackbar = Snackbar.make(Mroot, "Industry phone number cannot be blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();*/
        } else if (bd_industry_name_picker.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(Mroot, "Select Industry!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (bd_add5_input.getText().toString().equals("")) {

            bd_add5_input.setError("Industry address cannot be blank!!!");
           /* snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else {

            saveData(bd_add5_input.getText().toString(), address_id_5);

        }
    }


    // 2nd delete button
    @OnClick(R.id.bd_add_two_delete_btn)
    void deleteTwo() {

        Log.e("click on cancel", "okk");

        bd_add2_input.setText("");
        bd_add2_layout.setVisibility(View.GONE);


       /* if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }*/


    }

    // 3rd delete button
    @OnClick(R.id.bd_add_three_delete_btn)
    void deleteThree() {

        bd_add3_input.setText("");
        bd_add3_layout.setVisibility(View.GONE);

       /* if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }*/
    }

    // 4th delete button
    @OnClick(R.id.bd_add_four_delete_btn)
    void deleteFour() {

        bd_add4_input.setText("");
        bd_add4_layout.setVisibility(View.GONE);
       /* if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }*/
    }

    // 5th delete button
    @OnClick(R.id.bd_add_five_delete_btn)
    void deleteFive() {

        bd_add5_input.setText("");
        bd_add5_layout.setVisibility(View.GONE);
       /* if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }*/
    }


    @OnClick(R.id.bd_add_more_btn)
    void next() {
        if (bd_add2_layout.getVisibility() == View.VISIBLE) {

            if (bd_add3_layout.getVisibility() == View.VISIBLE) {

                if (bd_add4_layout.getVisibility() == View.VISIBLE) {

                    if (bd_add5_layout.getVisibility() == View.VISIBLE) {

                        Snackbar snackbar = Snackbar

                                .make(Mroot, "Max number of address reached!!!", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    } else {

                        bd_add5_layout.setVisibility(View.VISIBLE);

                    }

                } else {

                    bd_add4_layout.setVisibility(View.VISIBLE);

                }

            } else {
                bd_add3_layout.setVisibility(View.VISIBLE);
            }
        } else {
            bd_add2_layout.setVisibility(View.VISIBLE);
        }

    }


    @OnClick(R.id.bd_back_btn)
    void go_back() {
        // getActivity().onBackPressed();
        Intent intent = new Intent(getActivity(), LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("src", "def");
        startActivity(intent);
    }


    @Override
    public void getResponse(String response) {

        Log.e("res", response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else if (postTag.equalsIgnoreCase("getIndustry")) {

            try {

                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");


                industry_name_array = new String[arr.length() + 1];
                industry_id_array = new String[arr.length() + 1];

                industry_name_array[0] = "Select";
                industry_id_array[0] = "0";

                JSONObject obj;
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);
                    industry_name_array[i + 1] = obj.getString("title");
                    industry_id_array[i + 1] = obj.getString("id");

                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                         (getActivity(), android.R.layout.simple_spinner_item, industry_name_array); //selected item will look like a spinner set from XML
              //  spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bd_industry_name_picker.setAdapter(spinnerArrayAdapter);



                if (bundle.getString("src").equals("def")) {

                } else {
                    // put all previous data in UI widgets
                    bd_name_input.setText(LandingActivity.business_data.getBusiness_name());

                    bd_phone_input.setText(LandingActivity.business_data.getBusiness_phone());

                    for (int i = 0; i < industry_id_array.length; i++) {

                        if (LandingActivity.business_data.getBusiness_industry_id().equals(industry_id_array[i])) {
                            bd_industry_name_picker.setSelection(i);
                        }

                    }

                    if (LandingActivity.business_data.getAdderess_data().size() == 1) {
                        bd_add1_input.setText(LandingActivity.business_data.getAdderess_data().get(0).getFull_address());
                        bd_add2_layout.setVisibility(View.GONE);
                    } else if (LandingActivity.business_data.getAdderess_data().size() == 2) {
                        bd_add2_layout.setVisibility(View.VISIBLE);
                        bd_add1_input.setText(LandingActivity.business_data.getAdderess_data().get(0).getFull_address());
                        bd_add2_input.setText(LandingActivity.business_data.getAdderess_data().get(1).getFull_address());

                    } else if (LandingActivity.business_data.getAdderess_data().size() == 3) {
                        bd_add1_input.setText(LandingActivity.business_data.getAdderess_data().get(0).getFull_address());
                        bd_add2_input.setText(LandingActivity.business_data.getAdderess_data().get(1).getFull_address());
                        bd_add3_input.setText(LandingActivity.business_data.getAdderess_data().get(2).getFull_address());
                    } else if (LandingActivity.business_data.getAdderess_data().size() == 4) {
                        bd_add1_input.setText(LandingActivity.business_data.getAdderess_data().get(0).getFull_address());
                        bd_add2_input.setText(LandingActivity.business_data.getAdderess_data().get(1).getFull_address());
                        bd_add3_input.setText(LandingActivity.business_data.getAdderess_data().get(2).getFull_address());
                        bd_add4_input.setText(LandingActivity.business_data.getAdderess_data().get(3).getFull_address());
                    } else if (LandingActivity.business_data.getAdderess_data().size() == 5) {
                        bd_add1_input.setText(LandingActivity.business_data.getAdderess_data().get(0).getFull_address());
                        bd_add2_input.setText(LandingActivity.business_data.getAdderess_data().get(1).getFull_address());
                        bd_add3_input.setText(LandingActivity.business_data.getAdderess_data().get(2).getFull_address());
                        bd_add4_input.setText(LandingActivity.business_data.getAdderess_data().get(3).getFull_address());
                        bd_add5_input.setText(LandingActivity.business_data.getAdderess_data().get(4).getFull_address());
                    }


                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        } else if (postTag.equalsIgnoreCase("address_search")) {
            Log.e("res_address", response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("predictions");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");


                    address_list.add(description);
                }
                autocomplete_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, address_list) {
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
                bd_add1_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }
        } else if (postTag.equalsIgnoreCase("address_search_2")) {
            Log.e("res_address", response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("predictions");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");


                    address_list.add(description);
                }
                autocomplete_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, address_list) {
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

                bd_add2_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }

        } else if (postTag.equalsIgnoreCase("address_search_3")) {
            Log.e("res_address", response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("predictions");
                Log.d("BDF", "ja" + ja.length());

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");

                    Log.d("BDF", "description" + description);

                    address_list.add(description);
                }
                Log.d("BDF", "address_list" + address_list.size());
                autocomplete_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, address_list) {
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
                bd_add3_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }

        } else if (postTag.equalsIgnoreCase("address_search_4")) {
            Log.e("res_address", response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("predictions");
                Log.d("BDF", "ja" + ja.length());

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");

                    Log.d("BDF", "description" + description);

                    address_list.add(description);
                }
                Log.d("BDF", "address_list" + address_list.size());
                autocomplete_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, address_list) {
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
                bd_add4_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }

        } else if (postTag.equalsIgnoreCase("address_search_5")) {
            Log.e("res_address", response);
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("predictions");
                Log.d("BDF", "ja" + ja.length());

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");

                    Log.d("BDF", "description" + description);

                    address_list.add(description);
                }
                Log.d("BDF", "address_list" + address_list.size());
                autocomplete_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, address_list) {
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
                bd_add5_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }

        } else if (postTag.equalsIgnoreCase("business_address")) {
            Log.e("res_address", response);
            try {

                JSONObject obj = new JSONObject(response);
                JSONArray ja = obj.getJSONArray("output");
                industry=ja.getJSONObject(0).getString("industry_name");

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String buss_name = c.getString("business_name");
                    String buss_phone = c.getString("business_phone");

                    bd_name_input.setText(buss_name);
                    bd_phone_input.setText(buss_phone);

                    /*bd_add1_input*/

                    JSONArray array_industry = c.getJSONArray("industry");
                    industry_name_array = new String[array_industry.length() + 1];
                    industry_id_array = new String[array_industry.length() + 1];

                    industry_name_array[0] = "Select";
                    industry_id_array[0] = "0";

                    for (int j = 0; j < array_industry.length(); j++) {
                        JSONObject industry_object = array_industry.getJSONObject(j);
                        String industry_name = industry_object.getString("industry_name");
                        String industry_id = industry_object.getString("industry_id");

                        industry_name_array[j + 1] = industry_name;
                        industry_id_array[j + 1] = industry_id;
                    }
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_spinner_item,
                                    industry_name_array); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bd_industry_name_picker.setAdapter(spinnerArrayAdapter);

                    Log.d("BDF","industry"+industry);

                    if (industry != null) {
                        int spinnerPosition = spinnerArrayAdapter.getPosition(industry);
                        bd_industry_name_picker.setSelection(spinnerPosition);
                    }

                    JSONArray array_business_address = c.getJSONArray("businessaddress");
                    Log.d("array_business_address", "array_business_address" + array_business_address);
                    for (int k = 0; k < array_business_address.length(); k++) {
                        JSONObject address_object = array_business_address.getJSONObject(k);
                        address_id = address_object.getString("address_id");
                        address = address_object.getString("address");
                        start_date = address_object.getString("start_date");
                        end_date = address_object.getString("end_date");
                        mon_from_time = address_object.getString("mon_from_time");
                        mon_to_time = address_object.getString("mon_to_time");
                        tue_from_time = address_object.getString("tue_from_time");
                        tue_to_time = address_object.getString("tue_to_time");
                        wed_from_time = address_object.getString("wed_from_time");
                        wed_to_time = address_object.getString("wed_to_time");
                        thru_from_time = address_object.getString("thru_from_time");
                        thru_to_time = address_object.getString("thru_to_time");
                        fri_from_time = address_object.getString("fri_from_time");
                        fri_to_time = address_object.getString("fri_to_time");
                        sat_from_time = address_object.getString("sat_from_time");
                        sat_to_time = address_object.getString("sat_to_time");
                        sun_from_time = address_object.getString("sun_from_time");
                        sun_to_time = address_object.getString("sun_to_time");


                    }
                    try {
                        if (array_business_address.length() == 1) {
                            address_id_1 = array_business_address.getJSONObject(0).getString("address_id");
                            bd_add1_input.setText(array_business_address.getJSONObject(0).getString("address"));
                        }
                        if (array_business_address.length() == 2) {
                            bd_add2_layout.setVisibility(View.VISIBLE);
                            address_id_1 = array_business_address.getJSONObject(0).getString("address_id");
                            bd_add1_input.setText(array_business_address.getJSONObject(0).getString("address"));
                            address_id_2 = array_business_address.getJSONObject(1).getString("address_id");
                            bd_add2_input.setText(array_business_address.getJSONObject(1).getString("address"));
                        }
                        if (array_business_address.length() == 3) {
                            address_id_1 = array_business_address.getJSONObject(0).getString("address_id");
                            bd_add1_input.setText(array_business_address.getJSONObject(0).getString("address"));
                            address_id_2 = array_business_address.getJSONObject(1).getString("address_id");
                            bd_add2_input.setText(array_business_address.getJSONObject(1).getString("address"));

                            address_id_3 = array_business_address.getJSONObject(2).getString("address_id");
                            bd_add3_input.setText(array_business_address.getJSONObject(2).getString("address"));
                        }
                        if (array_business_address.length() == 4) {
                            address_id_1 = array_business_address.getJSONObject(0).getString("address_id");
                            bd_add1_input.setText(array_business_address.getJSONObject(0).getString("address"));
                            address_id_2 = array_business_address.getJSONObject(1).getString("address_id");
                            bd_add2_input.setText(array_business_address.getJSONObject(1).getString("address"));

                            address_id_3 = array_business_address.getJSONObject(2).getString("address_id");
                            bd_add3_input.setText(array_business_address.getJSONObject(2).getString("address"));

                            address_id_4 = array_business_address.getJSONObject(3).getString("address_id");
                            bd_add4_input.setText(array_business_address.getJSONObject(3).getString("address"));
                        }
                        if (array_business_address.length() == 5) {
                            address_id_1 = array_business_address.getJSONObject(0).getString("address_id");
                            bd_add1_input.setText(array_business_address.getJSONObject(0).getString("address"));
                            address_id_2 = array_business_address.getJSONObject(1).getString("address_id");
                            bd_add2_input.setText(array_business_address.getJSONObject(1).getString("address"));

                            address_id_3 = array_business_address.getJSONObject(2).getString("address_id");
                            bd_add3_input.setText(array_business_address.getJSONObject(2).getString("address"));

                            address_id_4 = array_business_address.getJSONObject(3).getString("address_id");
                            bd_add4_input.setText(array_business_address.getJSONObject(3).getString("address"));

                            address_id_5 = array_business_address.getJSONObject(4).getString("address_id");
                            bd_add5_input.setText(array_business_address.getJSONObject(4).getString("address"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

             /*   JSONArray ja = obj.getJSONArray("predictions");
                Log.d("BDF", "ja" + ja.length());

                for (int i = 0; i < ja.length(); i++) {
                    JSONObject c = ja.getJSONObject(i);
                    String description = c.getString("description");

                    Log.d("BDF", "description" + description);

                    address_list.add(description);
                }
                Log.d("BDF", "address_list" + address_list.size());
                autocomplete_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, address_list) {
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
                bd_add5_input.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();*/
            } catch (Exception e) {

                Log.e("error", e.toString());
            }

        }


    }

    @Override
    public void getLoc(Location loc) {

    }
}
