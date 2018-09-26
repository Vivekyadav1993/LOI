package frags;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.AddStaffAdap;
import adapters.BusinessTimeAdapter;
import adapters.CustomerLandingRecyclerViewAdapter;
import adapters.ExpandableListAdapter;
import adapters.FaqChildAdapter;
import adapters.ServiceStaffAdapter;
import adapters.ShowStaffAdapter;
import atw.lifeoninternet.R;
import atw.lifeoninternet.interfaces.IAddDetailsPresenter;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.BusinessDayTime;
import models.StaffData;
import models.addetailsEdit.Business;
import models.addetailsEdit.DetailsEditModel;
import models.addetailsEdit.Resource;
import models.addetailsEdit.Service;
import models.addetailsEdit.Servicestaff;
import models.addetailsEdit.Staff;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdDetailsEditFrag extends HelperFrags implements HttpresponseUpd {

    @BindView(R.id.frag_details_edit_lvExp)
    public ExpandableListView expListView;

    @BindView(R.id.ad_details_edit_category_tv)
    public TextView mCategory;


    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private List<Staff> staffList;
    private List<Resource> resourcesList;
    private ExpandableListAdapter listAdapter;

    private IAddDetailsPresenter mIAddDetailsPresenter;
    //Parent Context
    private Bundle bundle;
    private Snackbar snackbar;
    private HttpresponseUpd callback;

    //Fragment Manager Instance
    private FragmentManager fragmentManager;
    private DetailsEditModel mDetailsEditModel;

    @BindView(R.id.ad_details_edit_business_tv)
    public TextView mBusinessNametv;

    @BindView(R.id.ad_details_edit_address_tv)
    public TextView mBusinessAdd;

    @BindView(R.id.ad_details_edit_phone_tv)
    public TextView mPhone;

    @BindView(R.id.frag_add_edit_details_staff_rv)
    public RecyclerView mStaffRv;

    @BindView(R.id.frag_details_edit_rv)
    public RecyclerView mRecyclerView;

    @BindView(R.id.frag_details_edit_chield_staff_rv)
    public RecyclerView mChildRv;


    @BindView(R.id.frag_add_edit_details_business_date_rv)
    public RecyclerView mBusinessTimeRv;

    @BindView(R.id.frag_ad_details_edit_Sv)
    public ScrollView mScrollView;

    @BindView(R.id.frag_ad_details_edit_tv)
    public TextView mNoDataTv;

    private FaqChildAdapter mFaqChildAdapter;


    private ShowStaffAdapter mShowStaffAdapter;

    private String service_name;

    private List<String> staff_name_service;
    private String staff_name;

    private LinearLayoutManager linearLayoutManager;

    private ServiceStaffAdapter mAdapter;
    private BusinessTimeAdapter mBusinessAdapter;

    private Sharedpreferences mPrefs;

    public String business_id, address_id;

    private HttpresponseUpd httpresponseUpd;
    private List<BusinessDayTime> businessDayTimesList;
    private Dialog mDialougeBox;
    private String post_tag;
    private ArrayList<String> dayDay, dayTo, day;
    private String Mon, Tue, Wed, Thru, Fri, Sat, Sun, MonTo, TueTo, WedTo, ThruTo, FriTo, SatTo, SunTo, StartDate, EndDate;
    private ArrayList<String> address_list;
    private String postTag = "", comming ;
    private ArrayAdapter<String> autocomplete_adapter;
    private AutoCompleteTextView address;

    public AdDetailsEditFrag() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad_details_edit, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        mPrefs = Sharedpreferences.getUserDataObj(getContext());
        staffList = new ArrayList<>();
        resourcesList = new ArrayList<>();
        listDataHeader = new ArrayList<String>();
        businessDayTimesList = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();
        staff_name_service = new ArrayList<>();
        bundle = getArguments();
        callback = this;

        business_id = /*bundle.getString("business_id")*/mPrefs.getBusnessId();

         comming = bundle.getString("comingfrom");
        Log.d("ADEF", "status" +  comming );
        if (comming.equalsIgnoreCase("service")) {
            address_id = bundle.getString("add_id");
        } else if(comming.equalsIgnoreCase("address_page")) {
            address_id = bundle.getString("address_id");
        }
        //  mPrefs.setBusnessId(business_id);
        Log.d("AdEF", "bus_id" + business_id + "add" + address_id);

        if (isNetworkConnectionAvailable()) {
            if (business_id.equalsIgnoreCase("null") && address_id.equalsIgnoreCase("null")) {
                mScrollView.setVisibility(View.GONE);
                mNoDataTv.setVisibility(View.VISIBLE);
            } else {
                //hit api

                mScrollView.setVisibility(View.VISIBLE);
                mNoDataTv.setVisibility(View.GONE);

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "bookingDetails")
                        .appendQueryParameter("business_id", business_id)
                        .appendQueryParameter("address_id", address_id);

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "businessdetails";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    try {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        } else {
            checkNetworkConnection();
        }

        // preparing list data
        //prepareListData();

        //  mIAddDetailsPresenter.showDetails("bookingDetails", "361", "119");

    }

    @OnClick({R.id.ad_details_edit_back_btn, R.id.ad_details_edit_submit_btn, R.id.ad_details_edit_business_iv, R.id.ad_details_edit_staff_iv,
            R.id.ad_details_edit_resource_iv, R.id.ad_details_edit_category_iv, R.id.ad_details_edit_service_iv, R.id.ad_details_edit_staff_business_date_iv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ad_details_edit_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.ad_details_edit_submit_btn:

                bundle.putString("src", "value");
                replaceFrag(new CustomerLandingFrag(), bundle, "");
                break;

            case R.id.ad_details_edit_service_iv:
                bundle.putString("src", "value");
                mPrefs.setAddressId(address_id);
                replaceFrag(new AddServiceFrag(), bundle, AdDetailsEditFrag.class.getName());
                break;

            case R.id.ad_details_edit_business_iv:

                // openEditBusinessDialougeBox(mBusinessNametv.getText().toString(),mBusinessAdd.getText().toString(),mPhone.getText().toString());
                // bundle.putString("src", "value");
                Bundle _bundle = new Bundle();
                _bundle.putString("comming_from", "edit_page");
                /*mPrefs.setAddressId(address_id);*/
                replaceFrag(new BusinessDetailsFrag(), _bundle, BusinessDetailsFrag.class.getName());
               /* Fragment fragment = new BusinessDetailsFrag();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.parentcontainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                break;

            case R.id.ad_details_edit_staff_iv:
                bundle.putString("address_id_staff", address_id);
                bundle.putString("coming_from", "edit_staff");
                mPrefs.setAddressId(address_id);
                replaceFrag(new AddStaffFrag(), bundle, AdDetailsEditFrag.class.getName());
                break;

            case R.id.ad_details_edit_resource_iv:

                break;

            case R.id.ad_details_edit_category_iv:

                bundle.putString("src", "value");
                replaceFrag(new AddCategoryFrag(), bundle, AdDetailsEditFrag.class.getName());

                break;

            case R.id.ad_details_edit_staff_business_date_iv:

                editDayTimeFun();

                break;
        }
    }

    private void editDayTimeFun() {


        Bundle bundle = new Bundle();
        bundle.putString("Mon", Mon);
        bundle.putString("MonTo", MonTo);
        bundle.putString("Tue", Tue);
        bundle.putString("TueTo", TueTo);
        bundle.putString("Wed", Wed);
        bundle.putString("WedTo", WedTo);
        bundle.putString("Thru", Thru);
        bundle.putString("ThruTo", ThruTo);
        bundle.putString("Fri", Fri);
        bundle.putString("FriTo", FriTo);
        bundle.putString("Sat", Sat);
        bundle.putString("SatTo", SatTo);
        bundle.putString("Sun", Sun);
        bundle.putString("SunTo", SunTo);
        bundle.putString("StartDate", StartDate);
        bundle.putString("EndDate", EndDate);
        bundle.putString("address_id", address_id);

        // mfragment.setArguments(bundle); //data b
        replaceFrag(new EditDayTimeFrag(), bundle, AdDetailsEditFrag.class.getName());
    }

    private void openEditBusinessDialougeBox(String bus_name, String bus_add, String bus_phone) {
        mDialougeBox = new Dialog(getContext());
        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialougeBox.setContentView(R.layout.item_edit_business);
        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
        mDialougeBox.show();
        final EditText name = (EditText) mDialougeBox.findViewById(R.id.edit_business_name);
        address = (AutoCompleteTextView) mDialougeBox.findViewById(R.id.edit_business_address);
        final EditText phone = (EditText) mDialougeBox.findViewById(R.id.edit_business_phone);
        final ImageView current_location = (ImageView) mDialougeBox.findViewById(R.id.edit_business_current_address_tv);
        name.setText(bus_name);
        address.setText(bus_add);
        phone.setText(bus_phone);
        TextView done = (TextView) mDialougeBox.findViewById(R.id.business_done_tv);
        TextView cancel = (TextView) mDialougeBox.findViewById(R.id.business_cancel_tv);
        address.setThreshold(1);
        httpresponseUpd = this;


        address.addTextChangedListener(new TextWatcher() {
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


        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address.setText(AppConstants.app_data.getString("address", "Life On Internet"));
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String bus_address = address.getText().toString();
                Geocoder gc = new Geocoder(getContext());
                if (gc.isPresent()) {
                    List<Address> list = null;
                    try {
                        list = gc.getFromLocationName(bus_address, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address addres = list.get(0);
                    double lat = addres.getLatitude();
                    double lng = addres.getLongitude();

                  /*  mPref.setBusinessLat(String.valueOf(lat));
                    mPref.setBusinessLong(String.valueOf(lng));
*/
                    Log.d("address", "lat" + lat + "lng" + lng);
                }

                if (name.getText().length() == 0) {
                    name.setError("Name can't be blank");
                } else if (address.getText().length() == 0) {
                    address.setError("Address can't be blank");
                } else if (phone.getText().length() == 0) {
                    phone.setError("Phone can't be blank");

                } else {
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "editBusiness")
                            .appendQueryParameter("business_id", business_id)
                            .appendQueryParameter("address_id", address_id)
                            .appendQueryParameter("business_name", name.getText().toString())
                            .appendQueryParameter("business_phone", phone.getText().toString())
                            .appendQueryParameter("address", address.getText().toString());

                    Log.e("stafflist", builder.build().toString());
                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "editbusiness";
                        AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialougeBox.dismiss();
            }
        });


    }


    @Override
    public void getResponse(String response) {

        Log.e("res", response);

        if (response.contains("Error :")) {
            snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (post_tag.equalsIgnoreCase("editbusiness")) {
            mDialougeBox.dismiss();
            try {
                JSONObject main_obj = new JSONObject(response);
                String business_name = main_obj.getString("business_name");
                String business_phone = main_obj.getString("business_phone");
                String address = main_obj.getString("address");
                mBusinessNametv.setText(business_name);
                mBusinessAdd.setText(address);
                mPhone.setText(business_phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(getActivity(), "Update successfully", Toast.LENGTH_SHORT).show();
        } else if (postTag.equalsIgnoreCase("address_search")) {
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
                address.setAdapter(autocomplete_adapter);
                autocomplete_adapter.notifyDataSetChanged();
            } catch (Exception e) {

                Log.e("error", e.toString());
            }
        } else if (post_tag.equals("businessdetails")) {

            try {
                //parse data
                final JSONObject main_obj = new JSONObject(response);

                Log.d("ADEF", "" + main_obj);
                Gson gson = new Gson();
                DetailsEditModel detailsEditModel = gson.fromJson(response, DetailsEditModel.class);

                if (detailsEditModel.getBusiness().get(0).getBusinessName() == null) {
                    mScrollView.setVisibility(View.GONE);
                    mNoDataTv.setVisibility(View.VISIBLE);

                } else {

                    mScrollView.setVisibility(View.VISIBLE);
                    mNoDataTv.setVisibility(View.GONE);

                    mBusinessNametv.setText(detailsEditModel.getBusiness().get(0).getBusinessName());
                    mBusinessAdd.setText(detailsEditModel.getBusiness().get(0).getBusinessAddress());
                    mPhone.setText(detailsEditModel.getBusiness().get(0).getBusinessPhone());
                    //  mCategory.setText(detailsEditModel.getBusiness().get(0));

                    JSONArray staff_array = main_obj.getJSONArray("staff");

                    // Staff staff = new Staff();
                    for (int i = 0; i < staff_array.length(); i++) {
                        JSONObject obj = staff_array.getJSONObject(i);

                        staffList.add(new Staff(obj.getString("staff_id"),
                                obj.getString("staff_name")

                        ));

                    }
                    mStaffRv.setHasFixedSize(true);
                    mShowStaffAdapter = new ShowStaffAdapter(this, staffList);
                    mStaffRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mStaffRv.setAdapter(mShowStaffAdapter);


                /* service with staff list*/

                    final JSONArray service_array = main_obj.getJSONArray("service");

                    for (int i = 0; i < service_array.length(); i++) {

                        JSONObject servic_staff_obj = service_array.getJSONObject(i);
                        listDataHeader.add(servic_staff_obj.getString("service_name"));

                        //  listDataChild.put(listDataHeader.get(i), staff_name_service);
                    }

                    mRecyclerView.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mAdapter = new ServiceStaffAdapter(getActivity(), listDataHeader/*, staff_name_service*/, new ServiceStaffAdapter.EditDetailsViewAdapter() {
                        @Override
                        public void onClick(int pos) {
                            try {
                                staff_name_service.clear();
                                JSONArray service_array = main_obj.getJSONArray("service");
                                JSONObject servic_staff_obj = service_array.getJSONObject(pos);
                                JSONArray staff_Jsonarray = servic_staff_obj.getJSONArray("servicestaff");
                                for (int j = 0; j < staff_Jsonarray.length(); j++) {
                                    JSONObject _obj = staff_Jsonarray.getJSONObject(j);
                                    staff_name_service.add(_obj.getString("staff_name"));
                                }
                                mChildRv.setHasFixedSize(true);
                                linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mChildRv.setLayoutManager(linearLayoutManager);
                                mFaqChildAdapter = new FaqChildAdapter(getActivity(), staff_name_service);
                                mChildRv.setAdapter(mFaqChildAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);

                }
                JSONArray arr = main_obj.getJSONArray("business");
                JSONObject jo = arr.getJSONObject(0);

                mBusinessNametv.setText(jo.getString("business_name"));
                mBusinessAdd.setText(jo.getString("business_address"));
                mPhone.setText(jo.getString("business_phone"));
                StartDate = jo.getString("start_date");
                EndDate = jo.getString("end_date");

                Mon = jo.getString("mon_from_time");
                Tue = jo.getString("tue_from_time");
                Wed = jo.getString("wed_from_time");
                Thru = jo.getString("thru_from_time");
                Fri = jo.getString("fri_from_time");
                Sat = jo.getString("sat_from_time");
                Sun = jo.getString("sun_from_time");
                MonTo = jo.getString("mon_to_time");
                TueTo = jo.getString("tue_to_time");
                WedTo = jo.getString("wed_to_time");
                ThruTo = jo.getString("thru_to_time");
                FriTo = jo.getString("fri_to_time");
                SatTo = jo.getString("sat_to_time");
                SunTo = jo.getString("sun_to_time");
                day = new ArrayList<>();
                day.add(Mon);
                day.add(Tue);
                day.add(Wed);
                day.add(Thru);
                day.add(Fri);
                day.add(Sat);
                day.add(Sun);

                dayTo = new ArrayList<>();
                dayTo.add(MonTo);
                dayTo.add(TueTo);
                dayTo.add(WedTo);
                dayTo.add(ThruTo);
                dayTo.add(FriTo);
                dayTo.add(SatTo);
                dayTo.add(SunTo);

                dayDay = new ArrayList<>();
                {
                    dayDay.add("Mon");
                    dayDay.add("Tue");
                    dayDay.add("Wed");
                    dayDay.add("Thru");
                    dayDay.add("Fri");
                    dayDay.add("Sat");
                    dayDay.add("Sun");
                }

                mBusinessTimeRv.setHasFixedSize(true);
                mBusinessAdapter = new BusinessTimeAdapter(this, day, dayTo, dayDay);
                mBusinessTimeRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                mBusinessTimeRv.setAdapter(mBusinessAdapter);


            } catch (JSONException e) {
                snackbar = Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
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

        Toast.makeText(getActivity(), "No internet Connection", Toast.LENGTH_SHORT).show();
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
