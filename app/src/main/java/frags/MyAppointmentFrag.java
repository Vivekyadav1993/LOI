package frags;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.MyAdsAddressAdapter;
import adapters.MyAppointmentAdapter;
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
import models.MyAppointment;
import models.businesslist.Output;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.MyAdsAddress;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAppointmentFrag extends HelperFrags implements HttpresponseUpd {


    @BindView(R.id.my_app_add_back_btn)
    public ImageView mBack;

    @BindView(R.id.my_appointment_frag_no_booking_tv)
    public TextView mNoBooking;

    @BindView(R.id.my_appointment_ll)
    public LinearLayout mLl;
    @BindView(R.id.my_app_frag_recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.frag_my_appointment_cancel_tv)
    public TextView mCancel;

    private List<MyAppointment> list;
    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private MyAppointmentAdapter mMyAppointmentAdapter;
    private Sharedpreferences mPref;

    public MyAppointmentFrag() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_appointment, container, false);
        ButterKnife.bind(this, view);
        initilizerView();

        return view;
    }


    @OnClick({R.id.my_app_add_back_btn, R.id.frag_my_appointment_cancel_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_app_add_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.frag_my_appointment_cancel_tv:
                cancelAppintmentFun();
                break;
        }
    }

    private void cancelAppintmentFun() {

    }


    private void initilizerView() {
        mPref = Sharedpreferences.getUserDataObj(getActivity());


        if(isNetworkConnectionAvailable()){
        try {
            list = new ArrayList<>();
            callback = this;
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "appointmentList")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                    .appendQueryParameter("cust_id", mPref.getCustId());

            String myUrl = builder.build().toString();
            Log.e("urlgetLsiAdd", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }else {
            checkNetworkConnection();
        }

    }

    @Override
    public void getResponse(String response) {
        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(getView(), "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            try {
                mNoBooking.setVisibility(View.GONE);
                mLl.setVisibility(View.VISIBLE);
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj;
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);
                    MyAppointment myAppointment = new MyAppointment();
                    myAppointment.setAddressid(obj.getString("business_address_id"));
                    myAppointment.setBusinessid(obj.getString("business_id"));
                    myAppointment.setBusinessname(obj.getString("business_name"));
                    myAppointment.setBookingid(obj.getString("booking_id"));
                    myAppointment.setServiceid(obj.getString("service_id"));
                    myAppointment.setAppointmentdate(obj.getString("appointment_date"));
                    myAppointment.setStatus(obj.getString("status"));

                    list.add(myAppointment);

                }

            } catch (JSONException e) {
                e.printStackTrace();
                mNoBooking.setVisibility(View.VISIBLE);
                mLl.setVisibility(View.GONE);
            }


            try {
                mRecyclerView.setHasFixedSize(true);
                mMyAppointmentAdapter = new MyAppointmentAdapter(getContext(), list, new MyAppointmentAdapter.MyApponClick() {
                    @Override
                    public void onClick(int pos, String adddress_id, String bookingid, String apontmentdate, String Serviceid, String businessid) {
                        Bundle _bundle = new Bundle();
                        _bundle.putString("addressid", adddress_id);
                        _bundle.putString("businessid", businessid);
                        _bundle.putString("apontmentdate", apontmentdate);
                        _bundle.putString("Serviceid", Serviceid);
                        _bundle.putString("bookingid", bookingid);
                        replaceFrag(new JoinedQueueFrag(), _bundle, MyAppointmentFrag.class.getName());

                     /*   Intent intent=new Intent(getContext(), AppointmentDashbord.class);
                        startActivity(intent);
*/
                    }
                });
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.setAdapter(mMyAppointmentAdapter);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }


        }

    }

    public  boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection(){

        Toast.makeText(getContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
      /*  android.support.v7.app.AlertDialog.Builder builder =new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();*/
    }

}
