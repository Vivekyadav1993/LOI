package frags;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.BusinessHourData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.SplashActivity;

/**
 * Created by teknik on 9/27/2017.
 */

public class BusinessDetailsFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;


    @BindView(R.id.bd_name_input)
    EditText bd_name_input;

    @BindView(R.id.bd_phone_input)
    EditText bd_phone_input;

    @BindView(R.id.bd_industry_name_picker)
    Spinner bd_industry_name_picker;


    //1st add
    @BindView(R.id.bd_add1_input)
    EditText bd_add1_input;


    //2nd add
    @BindView(R.id.bd_add2_layout)
    RelativeLayout bd_add2_layout;


    @BindView(R.id.bd_add2_input)
    EditText bd_add2_input;


    //3rd add
    @BindView(R.id.bd_add3_layout)
    RelativeLayout bd_add3_layout;

    @BindView(R.id.bd_add3_input)
    EditText bd_add3_input;


    //4th add
    @BindView(R.id.bd_add4_layout)
    RelativeLayout bd_add4_layout;

    @BindView(R.id.bd_add4_input)
    EditText bd_add4_input;


    //5th add
    @BindView(R.id.bd_add5_layout)
    RelativeLayout bd_add5_layout;

    @BindView(R.id.bd_add5_input)
    EditText bd_add5_input;

    /*@Bind(R.id.bd_scroll)
    ScrollView bd_scroll;*/


    private HttpresponseUpd callback;

    private String[] industry_name_array, industry_id_array;


    private Snackbar snackbar;

    private Bundle bundle;

    private Uri.Builder builder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.buiseness_details_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();


        callback = this;


        //hit api
        builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getIndustry");


        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }

        return Mroot;
    }

    private void saveData(String address, int pos) {

        Bundle bundle = new Bundle();

        if (this.bundle.getString("src").equals("def")) {

            bundle.putString("src", "def");


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
                LandingActivity.business_data.setBusiness_name(bd_name_input.getText().toString());
                LandingActivity.business_data.setBusiness_phone(bd_phone_input.getText().toString());
                LandingActivity.business_data.setBusiness_industry_id(industry_id_array[bd_industry_name_picker.getSelectedItemPosition()]);
                LandingActivity.business_data.setRegularevent_flag("");

                LandingActivity.business_data.getAdderess_data()
                        .add(new BusinessHourData(address, "", "", "",
                                "", ""
                                , "", "", "", "", "", ""
                                , "", "", "", "", "", ""));


                LandingActivity.business_array_pos = 0;

                bundle.putString("src", "def");
                bundle.putInt("pos", pos);


            }
        } else {

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
                LandingActivity.business_data.setBusiness_name(bd_name_input.getText().toString());
                LandingActivity.business_data.setBusiness_phone(bd_phone_input.getText().toString());
                LandingActivity.business_data.setBusiness_industry_id(industry_id_array[bd_industry_name_picker.getSelectedItemPosition()]);


                if (LandingActivity.business_data.getAdderess_data().size() > pos) {
                    bundle.putString("src", "create");
                } else {
                    LandingActivity.business_data.getAdderess_data()
                            .add(pos, new BusinessHourData(address, "", "", "",
                                    "", ""
                                    , "", "", "", "", "", ""
                                    , "", "", "", "", "", ""));
                    bundle.putString("src", "def");

                }
                bundle.putInt("pos", pos);
            }

        }

        LandingActivity.business_array_pos = pos;
        replaceFrag(new BusinessHourFrag(), bundle, BusinessDetailsFrag.class.getName());

    }


    // 1st next button
    @OnClick(R.id.bd_add_one_next_btn)
    void oneNext() {

        if (bd_add1_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            saveData(bd_add1_input.getText().toString(), 0);

        }
    }


    // 2nd next button
    @OnClick(R.id.bd_add_two_next_btn)
    void twoNext() {
        if (bd_add2_input.getText().toString().equals("")) {

            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            saveData(bd_add2_input.getText().toString(), 1);

        }

    }


    // 3rd next button
    @OnClick(R.id.bd_add_three_next_btn)
    void threeNext() {
        if (bd_add3_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            saveData(bd_add3_input.getText().toString(), 2);

        }
    }


    // 4th next button
    @OnClick(R.id.bd_add_four_next_btn)
    void fourNext() {
        if (bd_add4_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            saveData(bd_add4_input.getText().toString(), 3);

        }
    }


    // 5th next button
    @OnClick(R.id.bd_add_fifth_next_btn)
    void fifthNext() {
        if (bd_add5_input.getText().toString().equals("")) {
            snackbar = Snackbar.make(Mroot, "Industry address cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            saveData(bd_add5_input.getText().toString(), 4);

        }
    }


    // 2nd delete button
    @OnClick(R.id.bd_add_two_delete_btn)
    void deleteTwo() {

        Log.e("click on cancel", "okk");
        if (LandingActivity.business_data.getAdderess_data().size() >= 2) {
            LandingActivity.business_data.getAdderess_data()
                    .remove(1);
        }
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
        if (LandingActivity.business_data.getAdderess_data().size() >= 3) {
            LandingActivity.business_data.getAdderess_data()
                    .remove(2);
        }
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
        if (LandingActivity.business_data.getAdderess_data().size() >= 4) {
            LandingActivity.business_data.getAdderess_data()
                    .remove(3);
        }
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
        if (LandingActivity.business_data.getAdderess_data().size() >= 5) {
            LandingActivity.business_data.getAdderess_data()
                    .remove(4);
        }
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

        } else {

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
                        (getActivity(), android.R.layout.simple_spinner_item,
                                industry_name_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
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
                    } else if (LandingActivity.business_data.getAdderess_data().size() == 2) {
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


        }
    }
}
