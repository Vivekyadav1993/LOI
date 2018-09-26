package frags;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.MyAdsAddressAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.VenderDetailsActivity;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import atw.lifeoninternet.views.AppointmentDashbord;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.businesslist.Output;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.MyAdsAddress;

import static frags.MyAdsFrag.address_id;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsAddressFrag extends HelperFrags implements HttpresponseUpd {


    private Snackbar snackbar;
    @BindView(R.id.my_ads_address_frag_recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.my_ads_add_back_btn)
    public ImageView mBack;

    @BindView(R.id.frag_my_ads_address_main_ll)
    public LinearLayout mDataLinearLayout;
    @BindView(R.id.frag_my_ads_address_no_address_ll)
    public LinearLayout mNoDataLinearLayout;

    private MyAdsAddressAdapter mMyAdsAddressAdapter;
    private ArrayList<Output> list;
    private ArrayList<Businessaddress> businessaddressesList;
    private HttpresponseUpd callback;
    public String status;
    public String business_id;
    private Sharedpreferences mPref;
    private Dialog mDialougeBox;


    public MyAdsAddressFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_ads_address, container, false);
        ButterKnife.bind(this, view);
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        initilizerView();
        return view;
    }

    private void initilizerView() {
        list = new ArrayList<>();
        businessaddressesList = new ArrayList<>();
        callback = this;

        status = getArguments().getString("commingfrom");
        if (isNetworkConnectionAvailable()) {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "getbusinessList")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""));

            String myUrl = builder.build().toString();
            Log.e("urlgetLsiAdd", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        } else {
            checkNetworkConnection();
        }
    }

    @OnClick({R.id.my_ads_add_back_btn, R.id.my_ads_address_frag_float_button})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.my_ads_add_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.my_ads_address_frag_float_button:

                Intent intent = new Intent(getContext(), VenderDetailsActivity.class);
                startActivity(intent);

             /*   Bundle _bundle = new Bundle();
                //  _bundle.putString("address_id", address_id);
                  _bundle.putString("business_id", business_id);
                replaceFrag(new UpLoadCompanyDetailsFrag(), _bundle, AddStaffFrag.class.getName());

*/
                break;

        }

    }


    @Override
    public void getResponse(String response) {
        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(getView(), "Network error occurred!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            try {
                Gson gson = new Gson();

                MyAdsAddress myAdsAddress = gson.fromJson(response, MyAdsAddress.class);

                if (myAdsAddress.getOutput().get(0).getBusinessaddress() == null) {

                    mDataLinearLayout.setVisibility(View.GONE);
                    mNoDataLinearLayout.setVisibility(View.VISIBLE);
                }

                //   JSONArray main_array = main_obj.getJSONArray("output");
                else {
                    mDataLinearLayout.setVisibility(View.VISIBLE);
                    mNoDataLinearLayout.setVisibility(View.GONE);

                    business_id = myAdsAddress.getOutput().get(0).getBusinessId();

                    for (int j = 0; j < myAdsAddress.getOutput().get(0).getBusinessaddress().size(); j++) {

                        Businessaddress businessaddress = new Businessaddress();
                        businessaddress.setAddressId(myAdsAddress.getOutput().get(0).getBusinessaddress().get(j).getAddressId());
                        businessaddress.setAddress(myAdsAddress.getOutput().get(0).getBusinessaddress().get(j).getAddress());
                        businessaddress.setAllow(myAdsAddress.getOutput().get(0).getBusinessaddress().get(j).getAllow());

                        businessaddressesList.add(businessaddress);
                    }

                    if(businessaddressesList.size()==1){

                        final String address_id1=businessaddressesList.get(0).getAddressId();
                        String allow1=businessaddressesList.get(0).getAllow();
                        final Bundle _bundle = new Bundle();
                        _bundle.putString("address_id", address_id1);
                        _bundle.putString("business_id", business_id);

                        if(address_id1.equalsIgnoreCase(mPref.getAddressId1())){
                            mPref.setStaffId(mPref.getStaffId1());
                        }else if(address_id1.equalsIgnoreCase(mPref.getAddressId2())){
                            mPref.setStaffId(mPref.getStaffId2());
                        }else {
                            Log.d("MAAF","staff_id_ayads_page"+mPref.getStaffId());
                        }

                        if (status.equals("myads")) {

                            if (mPref.getIndustryType().equalsIgnoreCase("Doctor")) {

                                if (allow1.equalsIgnoreCase("close")) {
                                    Toast.makeText(getContext(), "Today's business is closed !!!", Toast.LENGTH_SHORT).show();

                                } else {

                                    mDialougeBox = new Dialog(getContext());
                                    mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    mDialougeBox.setContentView(R.layout.item_select_shift);
                                    mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                                    mDialougeBox.show();

                                    TextView mMorning = (TextView) mDialougeBox.findViewById(R.id.morning_tv);
                                    TextView mEvening = (TextView) mDialougeBox.findViewById(R.id.evening_tv);
                                    TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_tv);


                                    if (allow1.equalsIgnoreCase("both")) {
                                        mMorning.setClickable(true);
                                        mEvening.setClickable(true);
                                        mMorning.setTextColor(Color.BLACK);
                                        mEvening.setTextColor(Color.BLACK);


                                    } else if (allow1.equalsIgnoreCase("morning")) {
                                        mMorning.setClickable(true);
                                        mEvening.setTextColor(Color.GRAY);
                                        mEvening.setClickable(false);
                                        mEvening.setEnabled(true);
                                        mEvening.setVisibility(View.INVISIBLE);


                                    } else if (allow1.equalsIgnoreCase("evening")) {
                                        mMorning.setClickable(false);
                                        mMorning.setEnabled(true);
                                        mMorning.setVisibility(View.INVISIBLE);
                                        mMorning.setTextColor(Color.GRAY);
                                        mEvening.setClickable(true);

                                    }
                                    mMorning.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("address_id", address_id1);
                                            bundle.putString("business_id", business_id);
                                          //  bundle.putString("business_id", business_id);
                                            bundle.putString("sub_date", "morning");
                                            replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

                                            mDialougeBox.hide();
                                        }
                                    });
                                    mEvening.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("address_id", address_id1);
                                            bundle.putString("business_id", business_id);
                                         //   bundle.putString("business_id", business_id);
                                            bundle.putString("sub_date", "evening");
                                            replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

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

                            }else if (mPref.getIndustryType().equalsIgnoreCase("Hospital")) {

                                if (allow1.equalsIgnoreCase("close")) {
                                    Toast.makeText(getContext(), "Today's business is closed !!!", Toast.LENGTH_SHORT).show();

                                } else {

                                    mDialougeBox = new Dialog(getContext());
                                    mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    mDialougeBox.setContentView(R.layout.item_select_shift);
                                    mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                                    mDialougeBox.show();

                                    TextView mMorning = (TextView) mDialougeBox.findViewById(R.id.morning_tv);
                                    TextView mEvening = (TextView) mDialougeBox.findViewById(R.id.evening_tv);
                                    TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_tv);


                                    if (allow1.equalsIgnoreCase("both")) {
                                        mMorning.setClickable(true);
                                        mEvening.setClickable(true);
                                        mMorning.setTextColor(Color.BLACK);
                                        mEvening.setTextColor(Color.BLACK);


                                    } else if (allow1.equalsIgnoreCase("morning")) {
                                        mMorning.setClickable(true);
                                        mEvening.setTextColor(Color.GRAY);
                                        mEvening.setClickable(false);
                                        mEvening.setEnabled(true);
                                        mEvening.setVisibility(View.INVISIBLE);


                                    } else if (allow1.equalsIgnoreCase("evening")) {
                                        mMorning.setClickable(false);
                                        mMorning.setEnabled(true);
                                        mMorning.setVisibility(View.INVISIBLE);
                                        mMorning.setTextColor(Color.GRAY);
                                        mEvening.setClickable(true);

                                    }
                                    mMorning.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("address_id", address_id1);
                                            bundle.putString("business_id", business_id);
                                          //  bundle.putString("business_id", business_id);
                                            bundle.putString("sub_date", "morning");
                                            replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

                                            mDialougeBox.hide();
                                        }
                                    });
                                    mEvening.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Bundle bundle = new Bundle();
                                            bundle.putString("address_id", address_id1);
                                            bundle.putString("business_id", business_id);
                                         //   bundle.putString("business_id", business_id);
                                            bundle.putString("sub_date", "evening");
                                            replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

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

                            } else {
                                _bundle.putString("sub_date", "morning");

                                replaceFrag(new MyAdsFrag(), _bundle, AddStaffFrag.class.getName());
                            }

                        } else if (status.equals("editads")) {

                            Intent intent = new Intent(getActivity(), AppointmentDashbord.class);
                            intent.putExtra("business_id", business_id);
                            intent.putExtra("address_id", address_id1);
                            startActivity(intent);

                            // replaceFrag(new MyCustomerFrag(), _bundle, AddStaffFrag.class.getName());
                        } else if (status.equals("landing_edit_button")) {

                           /*     Bundle bundle = new Bundle();
                                bundle.putString("business_id", business_id);
                                bundle.putString("address_id", address_id);*/
                            _bundle.putString("comingfrom", "address_page");
                            replaceFrag(new AdDetailsEditFrag(), _bundle, AddStaffFrag.class.getName());
                        }



                }else {


                    mRecyclerView.setHasFixedSize(true);
                    mMyAdsAddressAdapter = new MyAdsAddressAdapter(this, businessaddressesList, new MyAdsAddressAdapter.MyAdsAddressonClick() {


                        @Override
                        public void onClick(int pos, final String address_id, String allow) {

                            final Bundle _bundle = new Bundle();
                            _bundle.putString("address_id", address_id);
                            _bundle.putString("business_id", business_id);

                            if(address_id.equalsIgnoreCase(mPref.getAddressId1())){
                                mPref.setStaffId(mPref.getStaffId1());
                            }else if(address_id.equalsIgnoreCase(mPref.getAddressId2())){
                                mPref.setStaffId(mPref.getStaffId2());
                            }else {
                                Log.d("MAAF","staff_id_ayads_page"+mPref.getStaffId());
                            }

                            Log.d("MAAF", "allow" + allow + "mPref.getAdType()" + mPref.getIndustryType());
                            if (status.equals("myads")) {

                                if (mPref.getIndustryType().equalsIgnoreCase("Doctor")) {

                                    if (allow.equalsIgnoreCase("close")) {
                                        Toast.makeText(getContext(), "Today's business is closed !!!", Toast.LENGTH_SHORT).show();

                                    } else {

                                        mDialougeBox = new Dialog(getContext());
                                        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        mDialougeBox.setContentView(R.layout.item_select_shift);
                                        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                                        mDialougeBox.show();

                                        TextView mMorning = (TextView) mDialougeBox.findViewById(R.id.morning_tv);
                                        TextView mEvening = (TextView) mDialougeBox.findViewById(R.id.evening_tv);
                                        TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_tv);


                                        if (allow.equalsIgnoreCase("both")) {
                                            mMorning.setClickable(true);
                                            mEvening.setClickable(true);
                                            mMorning.setTextColor(Color.BLACK);
                                            mEvening.setTextColor(Color.BLACK);


                                        } else if (allow.equalsIgnoreCase("morning")) {
                                            mMorning.setClickable(true);
                                            mEvening.setTextColor(Color.GRAY);
                                            mEvening.setClickable(false);
                                            mEvening.setEnabled(true);
                                            mEvening.setVisibility(View.INVISIBLE);


                                        } else if (allow.equalsIgnoreCase("evening")) {
                                            mMorning.setClickable(false);
                                            mMorning.setEnabled(true);
                                            mMorning.setVisibility(View.INVISIBLE);
                                            mMorning.setTextColor(Color.GRAY);
                                            mEvening.setClickable(true);

                                        }
                                        mMorning.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("address_id", address_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("sub_date", "morning");
                                                replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

                                                mDialougeBox.hide();
                                            }
                                        });
                                        mEvening.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("address_id", address_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("sub_date", "evening");
                                                replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

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

                                }else if (mPref.getIndustryType().equalsIgnoreCase("Hospital")||mPref.getIndustryType().equalsIgnoreCase("Clinic")) {

                                    if (allow.equalsIgnoreCase("close")) {
                                        Toast.makeText(getContext(), "Today's business is closed !!!", Toast.LENGTH_SHORT).show();

                                    } else {

                                        mDialougeBox = new Dialog(getContext());
                                        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        mDialougeBox.setContentView(R.layout.item_select_shift);
                                        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                                        mDialougeBox.show();

                                        TextView mMorning = (TextView) mDialougeBox.findViewById(R.id.morning_tv);
                                        TextView mEvening = (TextView) mDialougeBox.findViewById(R.id.evening_tv);
                                        TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_tv);


                                        if (allow.equalsIgnoreCase("both")) {
                                            mMorning.setClickable(true);
                                            mEvening.setClickable(true);
                                            mMorning.setTextColor(Color.BLACK);
                                            mEvening.setTextColor(Color.BLACK);


                                        } else if (allow.equalsIgnoreCase("morning")) {
                                            mMorning.setClickable(true);
                                            mEvening.setTextColor(Color.GRAY);
                                            mEvening.setClickable(false);
                                            mEvening.setEnabled(true);
                                            mEvening.setVisibility(View.INVISIBLE);


                                        } else if (allow.equalsIgnoreCase("evening")) {
                                            mMorning.setClickable(false);
                                            mMorning.setEnabled(true);
                                            mMorning.setVisibility(View.INVISIBLE);
                                            mMorning.setTextColor(Color.GRAY);
                                            mEvening.setClickable(true);

                                        }
                                        mMorning.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("address_id", address_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("sub_date", "morning");
                                                replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

                                                mDialougeBox.hide();
                                            }
                                        });
                                        mEvening.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Bundle bundle = new Bundle();
                                                bundle.putString("address_id", address_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("business_id", business_id);
                                                bundle.putString("sub_date", "evening");
                                                replaceFrag(new MyAdsFrag(), bundle, AddStaffFrag.class.getName());

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

                                } else {
                                    _bundle.putString("sub_date", "morning");

                                    replaceFrag(new MyAdsFrag(), _bundle, AddStaffFrag.class.getName());
                                }

                            } else if (status.equals("editads")) {

                                Intent intent = new Intent(getActivity(), AppointmentDashbord.class);
                                intent.putExtra("business_id", business_id);
                                intent.putExtra("address_id", address_id);
                                startActivity(intent);

                                // replaceFrag(new MyCustomerFrag(), _bundle, AddStaffFrag.class.getName());
                            } else if (status.equals("landing_edit_button")) {

                           /*     Bundle bundle = new Bundle();
                                bundle.putString("business_id", business_id);
                                bundle.putString("address_id", address_id);*/
                                _bundle.putString("comingfrom", "address_page");
                                replaceFrag(new AdDetailsEditFrag(), _bundle, AddStaffFrag.class.getName());
                            }
                        }
                    });
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mMyAdsAddressAdapter);
                    }
                }

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
              //  Toast.makeText(getContext(), "Slow internet connection !!!" , Toast.LENGTH_SHORT).show();
            }


        }

    }

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {

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


