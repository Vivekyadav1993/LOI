package frags;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.AddResAdap;
import adapters.AddSerAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.HomeSerData;
import models.ResourceData;
import models.ResourceSpecification;
import models.ServiceData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Sharedpreferences;
import r2stech.lifeoninternet.utils.Utils;

import static frags.MyAdsFrag.address_id;
import static frags.MyAdsFrag.business_id;

/**
 * Created by teknik on 10/5/2017.
 */

public class AddServiceFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.add_service_list)
    ListView add_service_list;

    @BindView(R.id.add_service_no_service_tv)
    public TextView mNoServiceTv;

    private Bundle bundle;

    private int pos = 0;

    private ArrayList<HomeSerData> homeser_data_array = new ArrayList<>();

    private HttpresponseUpd callback;

    private AddSerAdap adapter;

    private Snackbar snackbar;

    private Boolean src_pos = false;

    private Dialog mDialougeBox;
    private TextView mDelete, mCancleTv;
    private String addressid;
    private String businessid;
    private Sharedpreferences mPrefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_service_screen, null);
        ButterKnife.bind(this, Mroot);
        mPrefs = Sharedpreferences.getUserDataObj(getActivity());
        bundle = getArguments();
        callback = this;

        // businessid=bundle.getInt("business_id");
        addressid = mPrefs.getAddressId();
        // businessid = LandingActivity.business_data.getBusiness_id();
        businessid = mPrefs.getBusnessId();


        Log.d("Business", "b" + businessid + "add" + addressid);
        homeser_data_array.add(new HomeSerData("", "", "", "", "", "", "", "", ""));

        LandingActivity.service_data_array = new ArrayList<>();

        LandingActivity.service_data_array.add(new ServiceData(/*LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()*/mPrefs.getAddressId(), "",
                "", "", "", "", "", "", "", homeser_data_array));

        // list seton click listener
        add_service_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle _bundle = new Bundle();
                _bundle.putString("src", "createstaff");
                _bundle.putInt("pos", i);
                _bundle.putInt("create_pos", bundle.getInt("create_pos"));  // go next page
                replaceFrag(new NewServiceFrag(), _bundle, AddStaffFrag.class.getName());


            }
        });

        //hit get service api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id())
        ;
        Log.e("url", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        return Mroot;
    }

    @OnClick(R.id.service_refress_page_iv)
    void refresh() {

        //  src_pos = false;
        LandingActivity.service_data_array.clear();

        LandingActivity.service_data_array = new ArrayList<>();

        LandingActivity.service_data_array.add(new ServiceData(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(), "",
                "", "", "", "", "", "", "", homeser_data_array));

        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", String.valueOf(addressid))
        ;
        Log.e("url", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }

    }

    @OnClick(R.id.add_service_next_btn)
    void go() {
        Log.d("ASF", "Size" + LandingActivity.service_data_array.size());

        if (LandingActivity.service_data_array.size() > 1) {
            Bundle _bundle = new Bundle();
      /*  _bundle.putString("src", "def");
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
*/
            _bundle.putString("add_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id());

            replaceFrag(new /*AddCategoryFrag()*/AdDetailsEditFrag(), _bundle, AddStaffFrag.class.getName());

        } else if (LandingActivity.service_data_array.size() == 1) {

            if (!LandingActivity.service_data_array.get(pos).getName().equalsIgnoreCase("")) {
                Bundle _bundle = new Bundle();
      /*  _bundle.putString("src", "def");
        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
*/
                _bundle.putString("add_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id());

                replaceFrag(new /*AddCategoryFrag()*/AdDetailsEditFrag(), _bundle, AddStaffFrag.class.getName());

            } else {
                Toast.makeText(getActivity(), "Add Service First", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.add_service_btn)
    void addSer() {

        Bundle _bundle = new Bundle();
        _bundle.putString("src", "def");

        if (src_pos) {
            ArrayList<HomeSerData> homeser_data_array = new ArrayList<>();
            homeser_data_array.add(new HomeSerData("", "", "", "", "", "", "", "", ""));

            LandingActivity.service_data_array.add(pos + 1, new ServiceData(LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(), "",
                    "", "", "", "", "", "", "", homeser_data_array));
            _bundle.putInt("pos", pos + 1);

        } else {
            _bundle.putInt("pos", pos);
        }

        _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        // go next page
        replaceFrag(new NewServiceFrag(), _bundle, AddStaffFrag.class.getName());


    }

    @OnClick(R.id.add_service_back_btn)
    void backGo() {
    /*    Bundle bundle = new Bundle();
        bundle.putString("src", "def");

        replaceFrag(new AddResourcesFrag(), bundle, AddServiceFrag.class.getName());
*/
        getActivity().onBackPressed();

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

                            replaceFrag(new AddResourcesFrag(), bundle, AddServiceFrag.class.getName());
                        }
                        return true;
                    }
                });
            }
        } catch (Exception e) {
            Log.e("error", "" + e);
        }
    }

    @Override
    public void getResponse(String response) {
        // parse data
        Log.e("res", response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                if (arr.getJSONObject(0).getString("message").equalsIgnoreCase("No service")) {
                    add_service_list.setVisibility(View.GONE);
                    mNoServiceTv.setVisibility(View.VISIBLE);
                   /* snackbar = Snackbar.make(Mroot, arr.getJSONObject(0).getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();*/
                } else {
                    try {
                        add_service_list.setVisibility(View.VISIBLE);
                        mNoServiceTv.setVisibility(View.GONE);
                        LandingActivity.service_data_array.clear();
                        ServiceData ser_data;
                        JSONObject obj;
                        for (int i = 0; i < arr.length(); i++) {
                            obj = arr.getJSONObject(i);

                            JSONArray spec_array = obj.getJSONArray("homeservice");


                            homeser_data_array.clear();

                            for (int j = 0; j < spec_array.length(); j++) {
                                JSONObject obja = spec_array.getJSONObject(j);
                                homeser_data_array.add(new HomeSerData(obja.getString("id"), obja.getString("location"), obja.getString("no_of_radius")
                                        , obja.getString("timing"), obja.getString("start_time")
                                        , obja.getString("end_time"), obja.getString("select_staff")
                                        , obja.getString("lat"), obja.getString("longi")));
                            }

                            ser_data = new ServiceData(obj.getString("business_address_id"),
                                    obj.getString("id"),
                                    obj.getString("name"),
                                    obj.getString("duration"),
                                    obj.getString("buffer_time"),
                                    obj.getString("cost")
                                    , obj.getString("service_type")
                                    , obj.getString("max_people_in_group")
                                    , obj.getString("home_service")
                                    , homeser_data_array

                            );

                            LandingActivity.service_data_array.add(ser_data);
                        }

                        // set staff list
                        adapter = new AddSerAdap(getActivity(), LandingActivity.service_data_array);
                        add_service_list.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    add_service_list.setLongClickable(true);
                    for (int i = 0; i < LandingActivity.service_data_array.size(); i++) {

                        add_service_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                                mDialougeBox = new Dialog(getContext());
                                mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                mDialougeBox.setContentView(R.layout.item_delete_staff);
                                mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                                mDialougeBox.show();

                                mDelete = (TextView) mDialougeBox.findViewById(R.id.delete_staff_tv);
                                mCancleTv = (TextView) mDialougeBox.findViewById(R.id.staff_cancel_tv);

                                mDelete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        hitAPI("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=deleteService&business_id=" +
                                                businessid + "&address_id=" + addressid + "&service_id=" +
                                                LandingActivity.service_data_array.get(i).getSer_id());


                                        mDialougeBox.hide();
                                    }
                                });

                                mCancleTv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        mDialougeBox.hide();

                                    }
                                });


                                return true;
                            }

                            private void hitAPI(final String url) {

                                final Dialog dialog = new Dialog(getActivity());
                                dialog.setTitle("Processing...");

                                dialog.show();
                                StringRequest strReq = new StringRequest(Request.Method.GET,
                                        url.replaceAll(" ", "%20"), new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("url", url);
                                        Log.e("urlres", response);
                                        try {
                                            JSONObject main_obj = new JSONObject(response);
                                            //   JSONArray output_array = main_obj.getJSONArray("output");
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "" + main_obj.getString("message") + "\n Press the refresh button", Toast.LENGTH_SHORT).show();

                                        } catch (JSONException e) {
                                            dialog.dismiss();
                                            if (response.equals("null")) {
                                                Toast.makeText(getActivity(), "no response from server!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        }


                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String message = "";
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
                                        dialog.dismiss();
                                    }
                                });
                                Volley.newRequestQueue(getActivity()).add(strReq);

                            }
                        });
                    }


                    if (LandingActivity.service_data_array.size() == 0) {

                    } else {

                        pos = LandingActivity.service_data_array.size() - 1;
                        src_pos = true;

                    }

                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

    }
}
