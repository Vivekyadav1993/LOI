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
import android.widget.ImageView;
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

import static frags.MyAdsFrag.business_id;
import static helper.AppUtils.dialog;

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

    @BindView(R.id.frag_joined_queue_service_status_hide_tv)
    public TextView mServiceStatusHide;

    @BindView(R.id.frag_joined_queue_service_absent_hide_tv)
    public TextView mServiceAbsentHide;

    @BindView(R.id.frag_joined_queue_cancel_btn)
    public Button mCancel;
    @BindView(R.id.frag_joined_queue_push_back_btn)
    public Button mPushBack;

    @BindView(R.id.frag_joined_queue_waiting_time_tv)
    public TextView mWaitingTime;
    @BindView(R.id.appoitment_date_in_queue)
    public TextView mDate;

    @BindView(R.id.est_dummy)
    public TextView mEstDummy;

    @BindView(R.id.iv1)
    public ImageView mStatusIv;

    @BindView(R.id.frag_toolbar_title_tv)
    public TextView mHeaderTitle;
    private HttpresponseUpd callback;

    private Bundle bundle;
    private String businessid, addreddid, serviceid, appointment_date, staff_id, staff_status;
    private Snackbar snackbar;
    private String token_number, your_place, estimate_time, a_date, message, booking_id, cust_id, ssub_date;
    private String post_tag, busines_sid, addred_did, service_id, appointmen_tdate, staf_fid, bookin_gid, service_status, setvices_tatus, sub_date;

    private Dialog mDialougeBox;
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
        sub_date = bundle.getString("sub_date");
        busines_sid = bundle.getString("businessid");
        addred_did = bundle.getString("addressid");
        service_id = bundle.getString("Serviceid");
        appointmen_tdate = bundle.getString("apontmentdate");
        staf_fid = bundle.getString("staff_id");
        bookin_gid = bundle.getString("bookingid");


        handler = new Handler();

        hitDistanceApi();

        Log.d("JQ", "appointment_date" + appointment_date);

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
                    dialog.dismiss();

                }
            };
            runnable.run();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (booking_id != null) {

            hitDistanceApi();

            runnable = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(this, FIVE_SECONDS);
                    Log.d("Api hiting", "5");
                    hitBookingApi();
                    dialog.dismiss();// this method will contain your almost-finished HTTP calls

                }
            };
            runnable.run();
        } else {
            Log.d("booking_id", "null value comming");
        }


        Log.d("Api hiting", "3");

    }

    private void hitDistanceApi() {

        Log.d("JQF", "lat" + mPref.getLat() + "long" + mPref.getLong());
        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "gpsAtPermise")
                    .appendQueryParameter("booking_id", mPref.getBookingId())
                    .appendQueryParameter("lat", mPref.getLat())
                    .appendQueryParameter("longi", mPref.getLong());

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "sendLatLong";
                dialog.dismiss();
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                dialog.dismiss();

            } else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

                Toast.makeText(getActivity(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                .appendQueryParameter("lat", mPref.getLat())
                .appendQueryParameter("longi", mPref.getLong())
                .appendQueryParameter("sub_date", sub_date)
        ;

        Log.e("stafflist", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity())) {
            post_tag = "joinqueue";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        } else {

            Toast.makeText(getActivity(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Toast.LENGTH_SHORT).show();

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
                    .appendQueryParameter("appointment_date", appointment_date)
                    .appendQueryParameter("sub_date", ssub_date)
                    .appendQueryParameter("service_id", service_id);

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
                    .appendQueryParameter("appointment_date", appointmen_tdate)
                    .appendQueryParameter("sub_date", ssub_date)
                    .appendQueryParameter("service_id", service_id);

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


        mDialougeBox = new Dialog(getContext());
        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialougeBox.setContentView(R.layout.item_cancel_appointment);
        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
        mDialougeBox.show();

        TextView mDelete = (TextView) mDialougeBox.findViewById(R.id.cancel_appointment_tv);
        TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_tv);

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                            .appendQueryParameter("appointment_date", appointment_date)
                            .appendQueryParameter("sub_date ", ssub_date)
                            .appendQueryParameter("service_id", service_id);

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
                            .appendQueryParameter("appointment_date", appointmen_tdate)
                            .appendQueryParameter("sub_date", ssub_date)
                            .appendQueryParameter("service_id", service_id);

                    Log.e("stafflist", builder.build().toString());
                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "cancel";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                }
                //    Toast.makeText(getContext(), "" + LandingActivity.staff_data_array.get(i).getStaff_id() + "bid" + business_id + "add" + address_id, Toast.LENGTH_SHORT).show();
                mDialougeBox.hide();
            }
        });

        mCancleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialougeBox.hide();

            }
        });
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
                } else if (service_status.equalsIgnoreCase("no")) {
                    mServiceStatus.setText("Service not started yet");
                    mServiceStatus.setTextColor(Color.RED);

                }
                mServiceStatus.setText("Service on hold");
                mServiceStatus.setTextColor(Color.BLACK);


                //  Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
                mPositionInQueue.setText(your_place);
                mEst_time.setText(estimate_time);
                mPosition_in_queue.setText("Token No." + token_number);
                mDate.setText(a_date);

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
        }/* else if (post_tag.equalsIgnoreCase("sendLatLong")) {
            try {
                JSONObject obj = new JSONObject(response);
                String message = obj.getString("message");
                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/ else if (post_tag.equalsIgnoreCase("booking_id")) {
            try {
                JSONObject obj = new JSONObject(response);
                JSONArray arr = obj.getJSONArray("output");
                JSONObject objt = arr.getJSONObject(0);
                setvices_tatus = objt.getString("staff_service_started");
                String servuce_status = objt.getString("staff_service");
                token_number = objt.getString("token_no");
                estimate_time = objt.getString("estimate_time");
                your_place = objt.getString("your_place");
                a_date = objt.getString("appointment_date");
                message = objt.getString("message");
                booking_id = objt.getString("booking_id");
                staff_status = objt.getString("staff_status");
                String cancel_reson = objt.getString("cancel_reason");
                String status = objt.getString("status");
                String business_name = objt.getString("business_name");
                String staff_name = objt.getString("staff_name");
                String queue_status = objt.getString("queue_status");
                ssub_date = objt.getString("sub_date");
                service_id = objt.getString("service_id");
                //  Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();

                mPref.setBooingId(booking_id);
                mHeaderTitle.setText(business_name);


                if (status.equalsIgnoreCase("In")) {
                    mPositionInQueue.setText("IN");
                    mEst_time.setText("");
                    mStatusIv.setBackgroundResource(R.drawable.welcome);
                    mCancel.setVisibility(View.INVISIBLE);
                    mPushBack.setVisibility(View.INVISIBLE);
                    mServiceStatusHide.setVisibility(View.GONE);
                    mServiceAbsentHide.setVisibility(View.VISIBLE);
                    mEst_time.setText(staff_name);
                    mEstDummy.setText("");
                    mServiceAbsentHide.setText("Come in. Your turn has come ");
                    mServiceAbsentHide.setTextColor(Color.RED);

                } else if (status.equalsIgnoreCase("Out")) {
                    mPositionInQueue.setText("");
                    mEst_time.setText("");
                    mStatusIv.setBackgroundResource(R.drawable.visitagain);
                    mServiceStatusHide.setVisibility(View.VISIBLE);
                    mServiceAbsentHide.setVisibility(View.GONE);
                    mCancel.setVisibility(View.INVISIBLE);
                    mPushBack.setVisibility(View.INVISIBLE);


                } else if (status.equalsIgnoreCase("Absent")) {
                    mPositionInQueue.setText("");
                    mEst_time.setText("");
                    mStatusIv.setBackgroundResource(R.drawable.absent);
                    mServiceStatusHide.setVisibility(View.GONE);
                    mServiceAbsentHide.setVisibility(View.VISIBLE);

                } else if (status.equalsIgnoreCase("Cancel")) {
                    //  mPositionInQueue.setText("IN");
                    mEst_time.setText("");
                    mStatusIv.setBackgroundResource(R.drawable.canceled);
                    mCancel.setVisibility(View.INVISIBLE);
                    mPushBack.setVisibility(View.INVISIBLE);
                    mServiceAbsentHide.setVisibility(View.VISIBLE);
                    mServiceAbsentHide.setText(cancel_reson);
                } else {
                    mPositionInQueue.setText(your_place);
                    mEst_time.setText(estimate_time);
                    mStatusIv.setBackgroundResource(R.drawable.group_queue_icon);
                    mServiceStatusHide.setVisibility(View.GONE);
                    mServiceAbsentHide.setVisibility(View.GONE);
                }

              /*  if (queue_status.equalsIgnoreCase("second")) {
                    mPositionInQueue.setText(your_place);
                    mEst_time.setText(estimate_time);
                    mCancel.setVisibility(View.VISIBLE);
                    mPushBack.setVisibility(View.VISIBLE);
                    mServiceAbsentHide.setVisibility(View.VISIBLE);
                    mServiceAbsentHide.setText("Be ready.  You are 2nd in queue");
                } else if (queue_status.equalsIgnoreCase("one")) {
                    mPositionInQueue.setText(your_place);
                    mEst_time.setText(estimate_time);
                    mCancel.setVisibility(View.VISIBLE);
                    mPushBack.setVisibility(View.VISIBLE);
                    mServiceAbsentHide.setVisibility(View.VISIBLE);
                    mServiceAbsentHide.setText("Be ready.  You are next in queue");
                }
*/
                if (servuce_status.equalsIgnoreCase("yes")) {

                    mServiceStatus.setText("Service started");
                    mServiceStatus.setTextColor(Color.BLUE);
                } else if (servuce_status.equalsIgnoreCase("no")) {
                    mServiceStatus.setText("Service Not Started Yet");
                    mServiceStatus.setTextColor(Color.RED);
                }
                if (staff_status.equalsIgnoreCase("Hold")) {
                    mServiceStatus.setText("Service On Hold");
                    mServiceStatus.setTextColor(Color.DKGRAY);
                }

                Log.d("JQF", "staff_status" + staff_status + "setvices_tatus" + setvices_tatus);
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
                    hitBookingApi();
                    dialog.dismiss();

                }
            };
            runnable.run();
        } else {
            Log.d("booking_id", "null value comming");
        }
    }
}
