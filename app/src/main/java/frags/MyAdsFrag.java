package frags;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daasuu.bl.BubbleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import adapters.ConsumerListAdap;
import adapters.CustomerListAdapter;
import adapters.FaqChildAdapter;
import adapters.LiveAdap;
import adapters.ServiceStaffAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.interfaces.UpdateListData;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import handler.MyUncaughtExceptionHandler;
import helper.HelperFrags;
import models.ConsumerListData;
import models.StaffListData;
import models.businesslist.Output;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsFrag extends HelperFrags implements UpdateListData {

    private View Mroot;

    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.staff_list)
    public ListView staff_list;

    @BindView(R.id.recyclerview_list)
    public RecyclerView recyclerview_list;

    @BindView(R.id.expand_up_iv)
    public ImageView mUp;

    @BindView(R.id.expand_down_iv)
    public ImageView mDown;

    @BindView(R.id.my_ads_info_iv)
    public ImageView my_ads_info_iv;

    @BindView(R.id.served_tv)
    public TextView mServed;

    @BindView(R.id.customer_text)
    public TextView customer_text;

    @BindView(R.id.myads_staff_hold_spinner)
    public Spinner myads_staff_hold_spinner;

    @BindView(R.id.left_tv)
    public TextView mLeft;
    @BindView(R.id.cancel_tv)
    public TextView mCancel;
    @BindView(R.id.total_tv)
    public TextView mTotal;
    public Timer timer;

    @BindView(R.id.myads_unhold_btn)
    public Button myads_unhold_btn;

    @BindView(R.id.myads_btns_ll)
    public LinearLayout myads_btns_ll;

    private String post_tag = "";
    private Bundle bundle;
    private ArrayList<StaffListData> raw_data;
    private ArrayList<ConsumerListData> consumer_array;
    private LiveAdap liveAdap;
    private ConsumerListAdap consumerListAdap;

    private CustomerListAdapter customerListAdapter;
    private UpdateListData updateListData;

    private boolean Selected = true;
    private int selected_position;
    // BottomSheetBehavior variable
    // private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.bottom_sheet)
    public RelativeLayout mBottomSheet;

    @BindView(R.id.staff_layout_ll)
    public LinearLayout staff_layout_ll;
    @BindView(R.id.admin_layout_ll)
    public LinearLayout admin_layout_ll;

    @BindView(R.id.my_add_back_btn)
    public ImageView mBack;

    @BindView(R.id.my_ads_bubble_layout)
    public BubbleLayout mBubbleLayout;

    @BindView(R.id.add_cuctomers)
    public ImageView mAddCust;

    @BindView(R.id.my_ads_business_tv)
    public TextView mBusinessTv;

    @BindView(R.id.unselect_all_tv)
    public TextView unselect_all_tv;

    @BindView(R.id.not_attend_all_tv)
    public ImageView not_attend_all_tv;

    @BindView(R.id.muads_next_btn)
    public Button muads_next_btn;


    @BindView(R.id.fragment_customer_name_search_et)
    public TextView mSearchNameEt;
    @BindView(R.id.myads_staff_customer_name_tv)
    public TextView myads_staff_customer_name_tv;

    private final int FIVE_SECONDS = 40000;
    private Handler handler;
    private Runnable runnable;

    private boolean info = true;
    private boolean search_button_status = true;

    private BottomSheetBehavior<View> behavior;

    public static String business_id = "", address_id = "";
    public static String staff_id = "";
    public String staff_service_started, business_name, no_of_days, sub_date, service_id, customer_id, skip_last, appointment_date;

    private String selected_id = "";
    private Sharedpreferences mPref;
    private Dialog mDialougeBox;
    private String hold_id, selected_service_id;

    private String row_index;
    private int not_attended;
    private boolean selection_status = true;
    private String atpermise_all;
    @BindView(R.id.myads_absent_btn)
    public Button mAbsentBtn;

    @BindView(R.id.myads_skip_btn)
    public Button mSkipBtn;

    int delay = 0; // delay for 1 sec.
    int period = 40000; // repeat every 10 sec.
    private String inputDate;
    private String unattended = "";

    //   private DetailListModel detailListModel;
    public MyAdsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Mroot = inflater.inflate(R.layout.fragment_my_ads, container, false);
        ButterKnife.bind(this, Mroot);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Thread.currentThread().setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        bundle = getArguments();
        raw_data = new ArrayList<>();
        consumer_array = new ArrayList<>();
        updateListData = this;

        mPref = Sharedpreferences.getUserDataObj(getActivity());
        handler = new Handler();
        address_id = bundle.getString("address_id");
        business_id = bundle.getString("business_id");
        sub_date = bundle.getString("sub_date");


        post_tag = "getDATA";

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        inputDate = GetDateFormat(mYear, (mMonth + 1), mDay);

        if (mPref.getStaffAdmin().equalsIgnoreCase("no")) {
            admin_layout_ll.setVisibility(View.GONE);
            staff_layout_ll.setVisibility(View.VISIBLE);
        } else {
            admin_layout_ll.setVisibility(View.VISIBLE);
            staff_layout_ll.setVisibility(View.GONE);
            unselect_all_tv.setVisibility(View.GONE);
        }

        mSearchNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    filter(editable.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        customer_text.setText("Customers List");


        final Calendar calendar = Calendar.getInstance();
        //  myads_staff_hold_spinner.setTag(0);
        myads_staff_hold_spinner.setSelection(0);
        myads_staff_hold_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:

                        break;

                    case 1:
                        Log.d("Ta", "5 min" + mPref.getStaffId());
                        // 5 min

                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId()
                                    + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=5&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        } else {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=5&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        }
                        break;

                    case 2:
                        //10 min
                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=10&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        } else {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=10&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);


                        }
                        break;

                    case 3:
                        //15 min
                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=15&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        } else {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=15&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        }
                        break;

                    case 4:
                        //20 min
                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=20&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        } else {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=20&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);


                        }
                        break;

                    case 5:
                        //25 min
                        if (mPref.getStaffId().equalsIgnoreCase("")) {

                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=25&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        } else {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=25&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        }
                        break;

                    case 6:
                        //30 min
                        if (mPref.getStaffId().equalsIgnoreCase("")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=30&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);
                        } else {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusHold&id=" +
                                    customer_id + "&business_id=" + business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&appointment_date=" +
                                    GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                                    "&hold_time_interval=30&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                        }
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return Mroot;

    }

    private void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<ConsumerListData> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (ConsumerListData s : consumer_array) {
            //if the existing elements contains the search input
            try {
                if (s.getCustomer_name().toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //calling a method of the adapter class and passing the filtered list
        try {
//            consumerListAdap.filterList(filterdNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetDateFormat(int Year, int Month, int Date) {
        try {
            return (new StringBuilder()
                    .append(Year).append("-")
                    .append((Month <= 9 ? "0" : "")).append(Month + "-")
                    .append((Date <= 9 ? "0" : "")).append(Date)).toString();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        hitMainApiFun();
    }

    private void hitMainApiFun() {
        myads_staff_hold_spinner.setSelection(0);

        if (mPref.getStaffId().equalsIgnoreCase("")) {


            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                            business_id + "&address_id=" + address_id + "&sub_date=" + sub_date + "&appointment_date=" + inputDate + "&adminlogin=Yes");
                   /* Utils.showProgress(getActivity());
                    Utils.stopProgress(getActivity());*/

                }
            }, delay, period);

      /*      ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);

// This schedule a runnable task every 40 seconds.
            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {

                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                            business_id + "&address_id=" + address_id + "&sub_date=" + sub_date);
                    Utils.showProgress(getActivity());
                    Utils.stopProgress(getActivity());

                }
            }, 0, 1, TimeUnit.MINUTES);*/
          /*  runnable = new Runnable() {
                @Override
                public void run() {

                    try {
                        handler.postDelayed(this, FIVE_SECONDS);
                        hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                                business_id + "&address_id=" + address_id + "&sub_date=" + sub_date);
                        Utils.stopProgress(getActivity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            runnable.run();*/
        } else {

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=bookingList&business_id=" +
                            business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&adminlogin=" + mPref.getStaffAdmin()
                            + "&sub_date=" + sub_date + "&appointment_date=" + inputDate);
                 /*   Utils.showProgress(getActivity());
                    Utils.stopProgress(getActivity());
*/
                }
            }, delay, period);

        }
    }

    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.my_add_back_btn, R.id.add_cuctomers, R.id.my_ads_info_iv, R.id.expand_up_iv, R.id.expand_down_iv,
            R.id.search_show_hide_button, R.id.muads_next_btn, R.id.myads_skip_btn, R.id.myads_absent_btn, R.id.myads_unhold_btn,
            R.id.unselect_all_tv, R.id.not_attend_all_tv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.my_add_back_btn:
                try {
                    getActivity().onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.not_attend_all_tv:

                unattended = "checked";
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                inputDate = GetDateFormat(mYear, (mMonth + 1), mDay);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(inputDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                c.setTime(date);
                c.add(Calendar.DATE, -1);
                inputDate = format.format(c.getTime());
                Log.d("asd", "selected date : " + inputDate);
                customer_text.setText("Unattended List");
                customer_text.setTextColor(getResources().getColor(R.color.yellow_900));
                hitMainApiFun();
                not_attend_all_tv.setBackgroundColor(getResources().getColor(R.color.blue));


                break;
            case R.id.muads_next_btn:

                try {
                    if (staff_service_started.equalsIgnoreCase("Yes")) {
                        if (consumer_array.get(0).getStatus().equalsIgnoreCase("Active")) {
                            muads_next_btn.setClickable(false);
                        } else {
                            muads_next_btn.setClickable(true);
                            if (selected_id.equalsIgnoreCase("") || customer_id.equalsIgnoreCase(null)) {
                                hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=nextCustomer&business_id=" +
                                        business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() +
                                        "&sub_date=" + sub_date + "&appointment_date=" + appointment_date + "&service_id=" + service_id + "&id=" + customer_id
                                        + "&selbook_id=" + "" + "&adminlogin=" + mPref.getStaffAdmin());
                                selected_id = "";
                            } else {
                                hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=nextCustomer&business_id=" +
                                        business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() +
                                        "&sub_date=" + sub_date + "&appointment_date=" + appointment_date + "&service_id=" + service_id + "&id=" + customer_id
                                        + "&selbook_id=" + selected_id + "&adminlogin=" + mPref.getStaffAdmin());
                                selected_id = "";
                            }
                        }
                    } else {
                        muads_next_btn.setClickable(true);
                        mDialougeBox = new Dialog(getActivity());
                        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialougeBox.setContentView(R.layout.item_start_service);
                        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                        mDialougeBox.show();

                        TextView mStartService = (TextView) mDialougeBox.findViewById(R.id.start_service_tv);
                        TextView mCancleServiceTv = (TextView) mDialougeBox.findViewById(R.id.cancel_start_service_tv);

                        mStartService.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Log.d("MyAdsFragment", "selected_id" + selected_id + "customer_id" + customer_id);

                                hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=staffserviceStarted&business_id=" +
                                        business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&sub_date=" + sub_date +
                                        "&appointment_date=&service_id=&selbook_id=&adminlogin=" + mPref.getStaffAdmin());


                               /* if (selected_id.equalsIgnoreCase("") || customer_id.equalsIgnoreCase(null)) {
                                       selected_id = "";
                                } else {
                                    hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=staffserviceStarted&business_id=" +
                                            business_id + "&address_id=" + address_id + "&staff_id=" + mPref.getStaffId() + "&sub_date=" + sub_date +
                                            "&appointment_date=" + appointment_date + "&service_id=" + service_id + "&selbook_id=" + selected_id + "&adminlogin=" + mPref.getStaffAdmin());
                                    selected_id = "";
                                }*/
                                mDialougeBox.hide();
                            }
                        });

                        mCancleServiceTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mDialougeBox.hide();

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.myads_skip_btn:


                Log.d("MAF", "mPref.getStaffAdmin()" + skip_last);
                if (skip_last.equalsIgnoreCase("Yes")) {
                    Log.d("MAF", "yes_click" + skip_last);

                    if (selected_id.equalsIgnoreCase("") || customer_id.equalsIgnoreCase(null)) {
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=pushmyselfBack&business_id=" + business_id + "&address_id="
                                + MyAdsFrag.address_id + "&id=" + customer_id + "&staff_id=" + mPref.getStaffId() +
                                "&appointment_date=" + appointment_date + "&adminlogin=" + mPref.getStaffAdmin() + "&type=skiplast&sub_date=" +
                                sub_date + "&service_id=" + service_id + "&selbook_id=" + "");
                        selected_id = "";
                    } else {
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=pushmyselfBack&business_id=" + business_id + "&address_id="
                                + MyAdsFrag.address_id + "&id=" + customer_id + "&staff_id=" + mPref.getStaffId() +
                                "&appointment_date=" + appointment_date + "&adminlogin=" + mPref.getStaffAdmin() + "&type=skiplast&sub_date=" +
                                sub_date + "&service_id=" + service_id + "&selbook_id=" + selected_id);
                        selected_id = "";
                    }
                } else {
                    Log.d("MAF", "no_click" + skip_last);

                    if (selected_id.equalsIgnoreCase("") || customer_id.equalsIgnoreCase(null)) {
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusSkip&business_id=" + business_id + "&address_id="
                                + MyAdsFrag.address_id + "&id=" + customer_id + "&staff_id=" + mPref.getStaffId() +
                                "&appointment_date=" + appointment_date + "&adminlogin=" + mPref.getStaffAdmin() + "&sub_date=" +
                                sub_date + "&service_id=" + service_id + "&selbook_id=" + "" + "&type=");
                        selected_id = "";
                    } else {
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusSkip&business_id=" + business_id + "&address_id="
                                + MyAdsFrag.address_id + "&id=" + customer_id + "&staff_id=" + mPref.getStaffId() +
                                "&appointment_date=" + appointment_date + "&adminlogin=" + mPref.getStaffAdmin() + "&sub_date=" +
                                sub_date + "&service_id=" + service_id + "&selbook_id=" + selected_id + "&type=");
                        selected_id = "";
                    }
                }
                break;

            case R.id.myads_absent_btn:

                mDialougeBox = new Dialog(getActivity());
                mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialougeBox.setContentView(R.layout.item_absent_customer);
                mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                mDialougeBox.show();

                TextView mDelete = (TextView) mDialougeBox.findViewById(R.id.absent_customer_tv);
                TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_customer_tv);

                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mPref.getStaffId().equalsIgnoreCase("")) {
                            if (selected_id.equalsIgnoreCase("") || customer_id.equalsIgnoreCase(null)) {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                        + customer_id + "&business_id=" + business_id + "&address_id="
                                        + MyAdsFrag.address_id + "&appointment_date=" + appointment_date + "&sub_date=" +
                                        sub_date + "&service_id=" + service_id + "&selbook_id=" + "" + "&adminlogin=" + mPref.getStaffAdmin()
                                        + "&absent_type=top");

                                selected_id = "";

                            } else {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                        + customer_id + "&business_id=" + business_id + "&address_id="
                                        + MyAdsFrag.address_id + "&appointment_date=" + appointment_date + "&sub_date=" +
                                        sub_date + "&service_id=" + service_id + "&selbook_id=" + selected_id + "" + "&adminlogin=" + mPref.getStaffAdmin()
                                        + "&absent_type=top");
                                selected_id = "";
                            }

                        } else {
                            if (selected_id.equalsIgnoreCase("") || customer_id.equalsIgnoreCase(null)) {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                        + customer_id + "&business_id=" + business_id + "&address_id="
                                        + MyAdsFrag.address_id + "&appointment_date=" + appointment_date + "&staff_id=" + mPref.getStaffId()
                                        + "&sub_date=" + sub_date + "&service_id=" + service_id + "&selbook_id=" + "" + "&adminlogin=" +
                                        mPref.getStaffAdmin() + "&absent_type=top");
                                selected_id = "";
                            } else {
                                updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                        + customer_id + "&business_id=" + business_id + "&address_id="
                                        + MyAdsFrag.address_id + "&appointment_date=" + appointment_date + "&staff_id=" + mPref.getStaffId()
                                        + "&sub_date=" + sub_date + "&service_id=" + service_id + "&selbook_id=" + selected_id + "&adminlogin=" + mPref.getStaffAdmin()
                                        + "&absent_type=top");
                                selected_id = "";

                            }

                        }
                        mDialougeBox.hide();
                    }
                });

                mCancleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mDialougeBox.hide();

                    }
                });

                break;


            case R.id.myads_unhold_btn:
                final Calendar calendar = Calendar.getInstance();
                if (mPref.getStaffId().equalsIgnoreCase("")) {

                    updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusUnHold&staff_id=" +
                            mPref.getStaffId() + "&hold_id=" + hold_id + "&business_id=" +
                            business_id + "&address_id=" + address_id + "&appointment_date=" +
                            GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE))
                            + "&adminlogin=yes" + "&sub_date=" + sub_date + "&service_id=" + service_id);

                    myads_staff_hold_spinner.setSelection(0);

                } else {

                    updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateStaffStatusUnHold&staff_id=" +
                            mPref.getStaffId() + "&hold_id=" + hold_id + "&business_id=" +
                            business_id + "&address_id=" + address_id + "&appointment_date=" +
                            GetDateFormat(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE)) +
                            "&adminlogin=no" + "&sub_date=" + sub_date + "&service_id=" + service_id);
                    myads_staff_hold_spinner.setSelection(0);

                }


                break;
            case R.id.expand_up_iv:
            /*    mUp.setVisibility(View.GONE);
                mDown.setVisibility(View.VISIBLE);
                behavior = BottomSheetBehavior.from(mBottomSheet);
                behavior.setPeekHeight(700);
                behavior.setM*/

                //  behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                mUp.setVisibility(View.GONE);
                mDown.setVisibility(View.VISIBLE);

                mBottomSheet.getLayoutParams().height = 750;
                //btnBottomSheet.setText("Close sheet");


                // btnBottomSheet.setText("Expand sheet");

                break;

            case R.id.unselect_all_tv:
                selected_id = "";
                try {
                    recyclerview_list.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerview_list.setLayoutManager(linearLayoutManager);
                    customerListAdapter = new CustomerListAdapter(getActivity(), consumer_array/*, staff_name_service*//*, atpermise_all*/, updateListData, new CustomerListAdapter.CustomerSelectedClick() {
                        @Override
                        public void onClick(String id, int position, String service_id) {
                            selected_id = String.valueOf(id);
                            Log.d("MAF", "selected_id" + id);
                        }
                    });
                    recyclerview_list.setAdapter(customerListAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.expand_down_iv:

                mUp.setVisibility(View.VISIBLE);
                mDown.setVisibility(View.GONE);
                mBottomSheet.getLayoutParams().height = 250;

                break;
            case R.id.add_cuctomers:

                Bundle _bundle = new Bundle();

                _bundle.putString("address_id", address_id);
                _bundle.putString("business_id", business_id);
                _bundle.putString("business_name", business_name);
                _bundle.putString("sub_date", sub_date);
                replaceFrag(new AddManualCustomer(), _bundle, AddStaffFrag.class.getName());
                break;

            case R.id.my_ads_info_iv:

                if (info == true) {
                    mBubbleLayout.setVisibility(View.VISIBLE);
                    my_ads_info_iv.setBackgroundColor(getResources().getColor(R.color.blue));
                    info = false;
                } else {
                    mBubbleLayout.setVisibility(View.GONE);
                    my_ads_info_iv.setBackgroundColor(getResources().getColor(R.color.hesder_bg));
                    info = true;
                }
                break;

            case R.id.search_show_hide_button:


                if (search_button_status == false) {
                    mSearchNameEt.setVisibility(View.GONE);
                    search_button_status = true;
                } else {
                    mSearchNameEt.setVisibility(View.VISIBLE);
                    search_button_status = false;

                }
                break;
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
        handler.removeCallbacks(runnable);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        handler.removeCallbacks(runnable);

    }

    public void hitAPI(final String url) {

        Utils.showProgress(getActivity());

        final StringRequest strReq = new StringRequest(Request.Method.GET,
                url.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("url", url);
                Log.e("urlres", response);


                try {
                    selected_id = "";
                    //parse data and put all to list
                    JSONObject main_obj = new JSONObject(response);
                    business_name = main_obj.getString("business_name");
                    // no_of_days = main_obj.getString("no_of_days");
                    String total_booking = main_obj.getString("total_booking");
                    String total_served = main_obj.getString("total_served");
                    String total_left = main_obj.getString("total_left");
                    String total_cancel = main_obj.getString("total_cancel");
                    not_attended = main_obj.getInt("not_attended");
                    //   skip_last = main_obj.getString("skip_last");
                    //    Log.d("MAF","skip_last"+skip_last);
                    //   atpermise_all = main_obj.getString("atpermise_all");
                    mServed.setText(total_served);
                    mTotal.setText(total_booking);
                    mCancel.setText(total_cancel);
                    mLeft.setText(total_left);
                    //    mPref.setNoOfDays(no_of_days);
                    mBusinessTv.setText(business_name);
                    JSONArray output_array = main_obj.getJSONArray("output");

                    Log.d("MyAdsffragment", "not_attended" + not_attended);

                    String staff_status = output_array.getJSONObject(0).getString("staff_status");
                    hold_id = output_array.getJSONObject(0).getString("hold_id");

                    if (staff_status.equalsIgnoreCase("Hold")) {
                        myads_btns_ll.setVisibility(View.GONE);
                        myads_unhold_btn.setVisibility(View.VISIBLE);
                        muads_next_btn.setClickable(false);
                        muads_next_btn.setEnabled(false);


                    } else {
                        myads_btns_ll.setVisibility(View.VISIBLE);
                        myads_unhold_btn.setVisibility(View.GONE);
                        muads_next_btn.setClickable(true);
                        muads_next_btn.setEnabled(true);
                    }

                    raw_data.clear();
                    consumer_array.clear();
                    for (int i = 0; i < output_array.length(); i++) {
                        JSONObject obj = output_array.getJSONObject(i);
                        staff_id = obj.getString("staff_id");

                        staff_service_started = obj.getString("staff_service_started");
                        skip_last = obj.getString("skip_last");
                        if (staff_service_started.equalsIgnoreCase("Yes")) {
                            muads_next_btn.setText("NEXT");
                        } else {
                            muads_next_btn.setText("Start service");
                        }

                        //   Log.d("MyAdsFrag","atpermise_all"+atpermise_all);
                        try {

                            if (obj.getJSONArray("customer").length() == 0) {
                                raw_data.add(new StaffListData(obj.getString("staff_id"),
                                        obj.getString("staff_name")
                                        , obj.getString("hold_id")
                                        , obj.getString("staff_status")
                                        , ""
                                        , ""
                                        , ""
                                        , ""
                                        , ""
                                        , ""
                                        , obj.getString("staff_service_started")
                                        , sub_date
                                        , ""
                                        , obj.getString("skip_last")
                                ));

                            } else if (obj.getJSONArray("customer").length() == 1) {
                                try {
                                    raw_data.add(new StaffListData(obj.getString("staff_id")
                                            , obj.getString("staff_name")
                                            , obj.getString("hold_id")
                                            , obj.getString("staff_status")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("service_name")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("customer_name")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("estimate_time")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("status")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("appointment_date")
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("id")
                                            , obj.getString("staff_service_started")
                                            , sub_date
                                            , obj.getJSONArray("customer").getJSONObject(0).getString("service_id")
                                            , obj.getString("skip_last")
                                    ));

                                } catch (JSONException e) {
                                    raw_data.add(new StaffListData(obj.getString("staff_id"),
                                            obj.getString("staff_name")
                                            , obj.getString("hold_id")
                                            , obj.getString("staff_status")
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , ""
                                            , obj.getString("staff_service_started")
                                            , sub_date
                                            , ""
                                            , obj.getString("skip_last")
                                    ));

                                }
                            } else {
                                raw_data.add(new StaffListData(obj.getString("staff_id")
                                        , obj.getString("staff_name")
                                        , obj.getString("hold_id")
                                        , obj.getString("staff_status")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("service_name")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("customer_name")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("estimate_time")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("status")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("appointment_date")
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("id")
                                        , obj.getString("staff_service_started")
                                        , sub_date
                                        , obj.getJSONArray("customer").getJSONObject(0).getString("service_id")
                                        , obj.getString("skip_last")
                                ));


                            }
                            JSONArray consumer_Jsonarray = obj.getJSONArray("customer");
                            for (int j = 0; j < consumer_Jsonarray.length(); j++) {
                                JSONObject _obj = consumer_Jsonarray.getJSONObject(j);
                                consumer_array.add(new ConsumerListData(_obj.getString("id")
                                        , _obj.getString("service_name")
                                        , _obj.getString("customer_name")
                                        , _obj.getString("estimate_time")
                                        , _obj.getString("status")
                                        , _obj.getString("appointment_date")
                                        , _obj.getString("token_no")
                                        , ""
                                        , _obj.getString("book_position")
                                        , sub_date
                                        , _obj.getString("service_id")
                                        , obj.getString("atpermise_all")
                                ));
                            }

                        } catch (JSONException e) {
                            Utils.stopProgress(getActivity());
                        }
                    }
                    liveAdap = new LiveAdap(getActivity()/*, skip_last*/, raw_data, updateListData);
                    staff_list.setAdapter(liveAdap);

                    recyclerview_list.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerview_list.setLayoutManager(linearLayoutManager);
                    customerListAdapter = new CustomerListAdapter(getActivity(), consumer_array/*, staff_name_service*//*, atpermise_all*/, updateListData, new CustomerListAdapter.CustomerSelectedClick() {
                        @Override
                        public void onClick(String id, int position, String service_id) {
                            selected_id = String.valueOf(id);
                            Log.d("MAF", "selected_id" + id);
                        }
                    });
                    recyclerview_list.setAdapter(customerListAdapter);
                    try {
                        if (staff_service_started.equalsIgnoreCase("Yes")) {
                            mSkipBtn.setVisibility(View.VISIBLE);
                            mAbsentBtn.setVisibility(View.VISIBLE);
                            myads_staff_hold_spinner.setVisibility(View.VISIBLE);
                        } else {

                            mSkipBtn.setVisibility(View.INVISIBLE);
                            mAbsentBtn.setVisibility(View.INVISIBLE);
                            myads_staff_hold_spinner.setVisibility(View.INVISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String cus_name = null;
                    try {
                        service_id = output_array.getJSONObject(0).getJSONArray("customer").getJSONObject(0).getString("service_id");
                        customer_id = output_array.getJSONObject(0).getJSONArray("customer").getJSONObject(0).getString("id");
                        appointment_date = output_array.getJSONObject(0).getJSONArray("customer").getJSONObject(0).getString("appointment_date");
                        cus_name = output_array.getJSONObject(0).getJSONArray("customer").getJSONObject(0).getString("customer_name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (cus_name.equalsIgnoreCase("") || cus_name.equalsIgnoreCase(null)) {
                            myads_staff_customer_name_tv.setText("No Customer");
                         /*   muads_next_btn.setClickable(false);
                            muads_next_btn.setEnabled(false);
*/
                        } else {
                            myads_staff_customer_name_tv.setText(cus_name);
                            //  Toast.makeText(getActivity(), "Next"+cus_name, Toast.LENGTH_SHORT).show();
                         /*   muads_next_btn.setClickable(true);
                            muads_next_btn.setEnabled(true);
*/
                        }
                    } catch (Exception e) {

                        myads_staff_customer_name_tv.setText("No Customer");

                    }
                    Utils.stopProgress(getActivity());

                } catch (JSONException e) {
                    //  Utils.stopProgress(getActivity());
                    Log.e("error", e.getMessage());
                    consumer_array.clear();
                    myads_staff_customer_name_tv.setText("No Customer");
                    /*recyclerview_list.setAdapter(customerListAdapter);*/
                    customerListAdapter.notifyDataSetChanged();
                    Utils.stopProgress(getActivity());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
                Utils.stopProgress(getActivity());
                try {
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                    } else if (error instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                    } else if (error instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                    } else if (error instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
            }
        });

        try {
            Volley.newRequestQueue(getActivity()).add(strReq);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doUpdate(String url) {
        //       Log.e("url", url);
        post_tag = "getDATA";
        hitAPI(url);
    }
}

