package frags;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Sharedpreferences;
import r2stech.lifeoninternet.utils.Utils;

import static frags.MyAdsFrag.business_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedQueueFrag extends HelperFrags implements HttpresponseUpd {


    @BindView(R.id.frag_joined_queue_position_in_queue_tv)
    public TextView mPositionInQueue;
    @BindView(R.id.est_time)
    public TextView mEst_time;
    @BindView(R.id.position_in_queue)
    public TextView mPosition_in_queue;

    @BindView(R.id.frag_joined_queue_service_status_tv)
    public TextView mServiceStatus;

    @BindView(R.id.frag_joined_queue_cancel_btn)
    public Button mCancel;
    @BindView(R.id.frag_joined_queue_push_back_btn)
    public Button mPushBack;

    @BindView(R.id.frag_joined_queue_waiting_time_tv)
    public TextView mWaitingTime;
    @BindView(R.id.appoitment_date_in_queue)
    public TextView mDate;

    @BindView(R.id.frag_toolbar_title_tv)
    public TextView mHeaderTitle;
    private HttpresponseUpd callback;

    private Bundle bundle;
    private String businessid, addreddid, serviceid, appointment_date, staff_id;
    private Snackbar snackbar;
    private String token_number, your_place, estimate_time, a_date, message, booking_id, cust_id;
    private String post_tag, busines_sid, addred_did, service_id, appointmen_tdate, staf_fid, bookin_gid, service_status, setvices_tatus;

    public Boolean Comming = false;

    private Sharedpreferences mPref;
    private final int FIVE_SECONDS = 15000;
    private Handler handler;
    private Runnable runnable;

    public JoinedQueueFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joined_queue, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        bundle = getArguments();
        callback = this;
        businessid = bundle.getString("business_id");
        addreddid = bundle.getString("addredd_id");
        serviceid = bundle.getString("service_id");
        appointment_date = bundle.getString("appointment_date");
        staff_id = bundle.getString("staff_id");

        busines_sid = bundle.getString("businessid");
        addred_did = bundle.getString("addressid");
        service_id = bundle.getString("Serviceid");
        appointmen_tdate = bundle.getString("apontmentdate");
        staf_fid = bundle.getString("staff_id");
        bookin_gid = bundle.getString("bookingid");


        handler = new Handler();

        Log.d("JQ", "before hiting api");

         /*  handler.postDelayed(new Runnable() {
            public void run() {
                hitBookingApi();          // this method will contain your almost-finished HTTP calls
                handler.postDelayed(this, FIVE_SECONDS);
            }
        }, FIVE_SECONDS);

*/
       /* ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

// This schedule a runnable task every 5 seconds
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                hitBookingApi();
                Toast.makeText(getActivity(), "Hello", Toast.LENGTH_SHORT).show();
            }
        }, 0, 5, TimeUnit.SECONDS);
*/
        if (bookin_gid == null || bookin_gid.equalsIgnoreCase("")) {
            Comming = true;
            Log.d("Api hiting", "1");
            hitApi();


        } else {
            Comming = false;
            //  hitBookingApi();

            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, FIVE_SECONDS);
                    Log.d("Api hiting", "2");
                    hitBookingApi();          // this method will contain your almost-finished HTTP calls

                }
            };
            runnable.run();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (booking_id != null) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, FIVE_SECONDS);
                    Log.d("Api hiting", "5");
                    hitBookingApi();          // this method will contain your almost-finished HTTP calls

                }
            };
            runnable.run();
        } else {
            Log.d("booking_id", "null value comming");
        }
        Log.d("Api hiting", "3");

    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);

    }

    @Override
    public void onPause() {
        super.onPause();

        // handler.removeCallbacks(runnable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

    }

    private void hitBookingApi() {

        try {
            if (booking_id == null) {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "showcustomerEstimateTime")
                        .appendQueryParameter("booking_id", bookin_gid);

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "booking_id";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            } else {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "showcustomerEstimateTime")
                        .appendQueryParameter("booking_id", booking_id);

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "booking_id";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hitApi() {


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "showcustomerEstimateTime")
                .appendQueryParameter("business_id", businessid)
                .appendQueryParameter("business_address_id", addreddid)
                .appendQueryParameter("staff_id", "")
                .appendQueryParameter("service_id", serviceid)
                .appendQueryParameter("appointment_date", appointment_date)
                .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""));

        Log.e("stafflist", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "joinqueue";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @OnClick({R.id.frag_joined_queue_back_btn, R.id.frag_joined_queue_cancel_btn, R.id.frag_joined_queue_push_back_btn, R.id.frag_joined_queue_show_details_btn})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.frag_joined_queue_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.frag_joined_queue_cancel_btn:
                appointmentCancleFun();
                break;
            case R.id.frag_joined_queue_push_back_btn:
                pushMySelfBackFunction();
                break;

            case R.id.frag_joined_queue_show_details_btn:
                showDetailsFun();
                break;
        }

    }

    private void showDetailsFun() {

    }

    private void pushMySelfBackFunction() {


        Uri.Builder builder = new Uri.Builder();

        if (Comming == true) {

            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "pushmyselfBack")
                    .appendQueryParameter("id", booking_id)
                    .appendQueryParameter("business_id", businessid)
                    .appendQueryParameter("address_id", addreddid)
                    .appendQueryParameter("appointment_date", appointment_date);

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "pushmyselfback";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {

            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "pushmyselfBack")
                    .appendQueryParameter("id", bookin_gid)
                    .appendQueryParameter("business_id", busines_sid)
                    .appendQueryParameter("address_id", addred_did)
                    .appendQueryParameter("appointment_date", appointmen_tdate);

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "pushmyselfback";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    private void appointmentCancleFun() {

        if (Comming == true) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "updateUserStatusCancel")
                    .appendQueryParameter("id", booking_id)
                    .appendQueryParameter("business_id", businessid)
                    .appendQueryParameter("address_id", addreddid)
                    .appendQueryParameter("appointment_date", appointment_date);

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "cancel";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "updateUserStatusCancel")
                    .appendQueryParameter("id", bookin_gid)
                    .appendQueryParameter("business_id", busines_sid)
                    .appendQueryParameter("address_id", addred_did)
                    .appendQueryParameter("appointment_date", appointmen_tdate);

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "cancel";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }


    }

    @Override
    public void getResponse(String response) {

        Log.d("JQF", "" + response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (post_tag.equalsIgnoreCase("joinqueue")) {

            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                JSONObject objt = arr.getJSONObject(0);
                token_number = objt.getString("token_no");
                estimate_time = objt.getString("estimate_time");
                your_place = objt.getString("your_place");
                a_date = objt.getString("appointment_date");
                message = objt.getString("message");
                booking_id = objt.getString("booking_id");
                service_status = objt.getString("staff_service_started");
                cust_id = objt.getString("cust_id");


                mPref.setCustId(cust_id);

                if (service_status.equalsIgnoreCase("yes")) {

                    mServiceStatus.setText("Service started");
                    mServiceStatus.setTextColor(Color.GREEN);
                } else {
                    mServiceStatus.setText("Service not started yet");
                    mServiceStatus.setTextColor(Color.RED);

                }

                //  Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                mPositionInQueue.setText(your_place);
                mEst_time.setText(estimate_time);
                mPosition_in_queue.setText("Token No." + token_number);
                mDate.setText(a_date);


             /*   runnable = new Runnable() {
                    @Override
                    public void run() {

                        handler.postDelayed(this, FIVE_SECONDS);
                        Log.d("Api hiting", "hiting api");
                        hitBookingApi();          // this method will contain your almost-finished HTTP calls

                    }
                };
                runnable.run();
*/
                hitRefreshApi(booking_id);
                Log.d("Api hiting", "4");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("pushmyselfback")) {
            try {
                hitBookingApi();
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                JSONObject objt = arr.getJSONObject(0);
                message = objt.getString("message");
                Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                // mPushBack.setVisibility(View.INVISIBLE);
                // hitApi();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("cancel")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                JSONObject objt = arr.getJSONObject(0);
                message = objt.getString("message");

                if (message.equalsIgnoreCase("Your booking has been canceled")) {

                    Bundle bundle = new Bundle();
                    replaceFrag(new CustomerLandingFrag(), bundle, JoinedQueueFrag.class.getName());

                } else {
                    Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                }
                //  hitApi();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (post_tag.equalsIgnoreCase("booking_id")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                JSONObject objt = arr.getJSONObject(0);
                setvices_tatus = objt.getString("staff_service_started");
                token_number = objt.getString("token_no");
                estimate_time = objt.getString("estimate_time");
                your_place = objt.getString("your_place");
                a_date = objt.getString("appointment_date");
                message = objt.getString("message");
                booking_id = objt.getString("booking_id");
                //  Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                mPositionInQueue.setText(your_place);
                mEst_time.setText(estimate_time);

                if (setvices_tatus.equalsIgnoreCase("yes")) {

                    mServiceStatus.setText("Service started");
                    mServiceStatus.setTextColor(Color.GREEN);
                } else {
                    mServiceStatus.setText("Service not started yet");
                    mServiceStatus.setTextColor(Color.RED);
                }
                //  mServiceStatus.setText(setvices_tatus);
                mPosition_in_queue.setText("Token No." + token_number);
                mDate.setText(a_date);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void hitRefreshApi(String booking_id) {

        if (booking_id != null) {
            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, FIVE_SECONDS);
                    Log.d("Api hiting", "5");
                    hitBookingApi();          // this method will contain your almost-finished HTTP calls

                }
            };
            runnable.run();
        } else {
            Log.d("booking_id", "null value comming");
        }
    }
}
