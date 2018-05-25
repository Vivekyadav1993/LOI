package frags;


import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddManualCustomer extends HelperFrags implements HttpresponseUpd {

    private Bundle bundle;
    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private String[] industry_name_array, industry_id_array;

    @BindView(R.id.add_manual_spinner)
    public Spinner add_manual_spinner;

    @BindView(R.id.ctg_name_input)
    public EditText mName;

    @BindView(R.id.date_tv)
    public TextView mDate;
    @BindView(R.id.open_calenderdate_tv)
    public TextView mOpencalender;

    @BindView(R.id.add_manual_customer_phone_et)
    public EditText mPhone;


    private int mYear, mMonth, mDay;

    private String address_id, business_id;
    private String name, date;
    private String spinner_service,business_name;
    private String post_tag = "";

    public AddManualCustomer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_manual_customer, container, false);

        ButterKnife.bind(this, view);
        bundle = getArguments();
        address_id = bundle.getString("address_id");
        business_id = bundle.getString("business_id");
        business_name = bundle.getString("business_name");

        initView();
        return view;
    }

    private void initView() {
        callback = this;

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
        hitUrl();

        mPhone.setFilters(new InputFilter[]{Utils.sepcialCharRemovalFilter});
        mPhone.setFilters(new InputFilter[]{Utils.sepcialCharRemovalFilter, new InputFilter.LengthFilter(10)});

    }

    private void hitUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", address_id)


        ;
        Log.e("url", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "getCategory";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @OnClick({R.id.add_customer_done, R.id.open_calenderdate_tv, R.id.add_customer_back_btn})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_customer_back_btn:
                getActivity().onBackPressed();
                break;
            case R.id.open_calenderdate_tv:

                mDate.setText("");
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                mDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

                break;

            case R.id.add_customer_done:

                bookingFun();
                //  hitUrl();

                break;
        }
    }

    private void bookingFun() {

        if (mName.getText().toString().equals("")) {
            snackbar = Snackbar.make(getView(), "Customer name cannot be blank!!!", Snackbar.LENGTH_LONG);
         /*   mName.setError("Customer name cannot be blank!!!");
            snackbar.show();*/
        } else if (mPhone.getText().toString().equals("")) {
            mPhone.setError("Customer phone number cannot be blank!!!");
          /*  snackbar = Snackbar.make(getView(), "Customer phone number cannot be blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/
        } else if (add_manual_spinner.getSelectedItemPosition() == 0) {
            snackbar = Snackbar.make(getView(), "Select Service!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (mDate.getText().toString().equals("")) {
            mDate.setError("Select Date!!!");
         /*   snackbar = Snackbar.make(getView(), "Select Date!!!", Snackbar.LENGTH_LONG);
            snackbar.show();*/

        } else {
            name = mName.getText().toString();
            spinner_service = industry_id_array[add_manual_spinner.getSelectedItemPosition()];

            Log.d("AMC", "date" + date);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "get_token")
                    .appendQueryParameter("cust_name", name)
                    .appendQueryParameter("business_id", business_id)
                    .appendQueryParameter("business_address_id", address_id)
                    .appendQueryParameter("service_id", spinner_service)
                    .appendQueryParameter("appointment_date", mDate.getText().toString())
                    .appendQueryParameter("lat", "")
                    .appendQueryParameter("longi", "")
                    .appendQueryParameter("mobile", mPhone.getText().toString())

            ;
            Log.e("url", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "addCustomer";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);

            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //  hitUrl();
    }

    @Override
    public void getResponse(String response) {

        //  mDate.setText("");

        add_manual_spinner.setSelection(0);
        mPhone.setText("");
        mName.setText("");

        Log.e("res", response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else if (post_tag == "getCategory") {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                industry_name_array = new String[arr.length() + 1];
                industry_id_array = new String[arr.length() + 1];

                industry_name_array[0] = "Select Service";
                industry_id_array[0] = "0";

                JSONObject obj;
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);
                    industry_name_array[i + 1] = obj.getString("name");
                    industry_id_array[i + 1] = obj.getString("id");

                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (getActivity(), android.R.layout.simple_spinner_item,
                                industry_name_array); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                add_manual_spinner.setAdapter(spinnerArrayAdapter);


            } catch (JSONException e) {
                snackbar = Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        } else if (post_tag.equals("addCustomer")) {
            try {
                //  hitUrl();
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                String obj = arr.getJSONObject(0).getString("message");

                Toast.makeText(getContext(), "" + obj, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
