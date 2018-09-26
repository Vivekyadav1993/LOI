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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
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

import adapters.AddStaffAdap;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.StaffData;


/**
 * Created by teknik on 9/28/2017.
 */

public class AddStaffFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.add_staff_onlyme_btn)
    Switch add_staff_onlyme_btn;


    @BindView(R.id.add_staff_list)
    ListView add_staff_list;

    @BindView(R.id.add_staff_no_staff_tv)
    public TextView mNoStaff;

    private Dialog mDialougeBox;

    private int pos, create_pos;
    private Bundle bundle;
    private Snackbar snackbar;
    private HttpresponseUpd callback;
    private AddStaffAdap adapter;
    private Boolean src_pos = false;
    private int address_id, business_id;
    private String staff_id;
    private TextView mDelete, mCancleTv;
    private String post_tag = "";
    private Sharedpreferences mPref;
    private String staff_serving;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_staff_screen, null);
        ButterKnife.bind(this, Mroot);
        bundle = getArguments();
        callback = this;
        create_pos = bundle.getInt("create_pos");
        address_id = bundle.getInt("address_id");
        business_id = bundle.getInt("business_id");
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        LandingActivity.staff = false;
        //initialize static  staff array and add index 0
        LandingActivity.staff_data_array = new ArrayList<>();
        pos = 0;
        LandingActivity.staff_data_array.add(new StaffData("No", "No", mPref.getAddressId() /*LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()*/
                , "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));


        try {
            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "staffList")
                    .appendQueryParameter("address_id", /*LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()*/
                            mPref.getAddressId());

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            add_staff_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle = new Bundle();
                  /*  bundle.putString("src", "createstaff");
                    bundle.putInt("pos", i);
                 */
                    bundle.putString("staff_id", LandingActivity.staff_data_array.get(i).getStaff_id().toString());


                    Log.d("AsF", "staff_id" + LandingActivity.staff_data_array.get(i).getStaff_id().toString());
                    // go next page
                    replaceFrag(new NewStaffFrag(), bundle, AddStaffFrag.class.getName());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mroot;
    }

    @OnClick(R.id.staff_refress_page_iv)
    void refress() {
        LandingActivity.staff_data_array.clear();

        //initialize static  staff array and add index 0
        LandingActivity.staff_data_array = new ArrayList<>();
      /*  pos = 0;
        LandingActivity.staff_data_array.add(new StaffData("No", "No", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id(), "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));
*/
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "staffList")
                .appendQueryParameter("address_id", mPref.getAddressId());

        Log.e("stafflist", builder.build().toString());
        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    @OnClick(R.id.add_staff_next_btn)
    void go() {


        if (add_staff_onlyme_btn.isChecked()) {
            LandingActivity.staff_data_array.get(pos).setOnly_me_flag("Yes");
        }

      /*  if (!add_staff_onlyme_btn.isChecked() || LandingActivity.staff_data_array.size() == 0) {
            Log.d("AddStaffFrag",""+add_staff_onlyme_btn.isChecked()+"list size"+LandingActivity.staff_data_array.size());
            Toast.makeText(getActivity(), "Add Staff First", Toast.LENGTH_SHORT).show();
        } else {*/

        Log.d("ASF", "" + LandingActivity.staff_data_array.get(pos).getFirst_name() + "checkedddd" + add_staff_onlyme_btn.isChecked());
        if (add_staff_onlyme_btn.isChecked() || LandingActivity.staff_data_array.size() > 1) {
            Bundle _bundle = new Bundle();
            _bundle.putString("src", bundle.getString("src"));
            _bundle.putInt("staff_pos", pos);
            _bundle.putInt("create_pos", create_pos);
            _bundle.putInt("address_id", address_id);
            _bundle.putInt("business_id", business_id);

            replaceFrag(new AddServiceFrag(), _bundle, AddStaffFrag.class.getName());
            // }
        } else if (add_staff_onlyme_btn.isChecked() || LandingActivity.staff_data_array.size() == 1) {

            if (!LandingActivity.staff_data_array.get(pos).getFirst_name().equalsIgnoreCase("")) {
                Bundle _bundle = new Bundle();
                _bundle.putString("src", bundle.getString("src"));
                _bundle.putInt("staff_pos", pos);
                _bundle.putInt("create_pos", create_pos);
                _bundle.putInt("address_id", address_id);
                _bundle.putInt("business_id", business_id);

                replaceFrag(new AddServiceFrag(), _bundle, AddStaffFrag.class.getName());

            } else {
                Toast.makeText(getActivity(), "Add Staff First", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(getActivity(), "Add Staff First", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.add_staff_btn)
    void addStaff() {
        if (add_staff_onlyme_btn.isChecked()) {

        } else {
            //save address id
            Bundle _bundle = new Bundle();
            _bundle.putString("staff_id", "");
            // LandingActivity.staff_data_array.get(pos).setAddress_id(LandingActivity.business_data.getAdderess_data().get(create_pos).getAddress_id());
           /*     Bundle _bundle = new Bundle();
                _bundle.putString("src", bundle.getString("src"));
                if (src_pos) {
                    _bundle.putInt("pos", pos + 1);
                    LandingActivity.staff_data_array.add(pos + 1, new StaffData("No", "No",
                            LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()
                            , "", "", "", "", "", "", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_start(),
                            LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_end()
                            , "", "", "",
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));


                } else {
                    LandingActivity.staff_data_array.add(pos, new StaffData("No", "No",
                            LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()
                            , "", "", "", "", "", "", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_start(),
                            LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getDate_end()
                            , "", "", "",
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                            "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""));


                    _bundle.putInt("pos", pos);
                }

                _bundle.putInt("create_pos", create_pos);
              */  // go next page
            replaceFrag(new NewStaffFrag(), _bundle, AddStaffFrag.class.getName());


        }
    }

    @OnClick(R.id.add_staff_back_btn)
    void backGo() {
        getActivity().onBackPressed();
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

                String only_me_flag = "No";

                if (arr.getJSONObject(0).getString("message").equals("No Record Found")) {
                    add_staff_list.setVisibility(View.GONE);
                    mNoStaff.setVisibility(View.VISIBLE);
                  /*  snackbar = Snackbar.make(Mroot, arr.getJSONObject(0).getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();*/
                } else {
                    src_pos = true;
                    add_staff_list.setVisibility(View.VISIBLE);
                    mNoStaff.setVisibility(View.VISIBLE);
                    try {

                        LandingActivity.staff_data_array.clear();

                        StaffData staff_data;
                        JSONObject obj;

                        for (int i = 0; i < arr.length(); i++) {
                            obj = arr.getJSONObject(i);

                            staff_serving = obj.getString("staff_serving");
                            only_me_flag = obj.getString("onlyme_status");

                            staff_data = new StaffData(obj.getString("admin_status"), obj.getString("onlyme_status"),
                                    obj.getString("address_id"), obj.getString("staff_id"), obj.getString("first_name"),
                                    obj.getString("last_name"), obj.getString("email"), obj.getString("phone"), obj.getString("address"),
                                    obj.getString("start_date"), obj.getString("end_date")

                                    , obj.getString("all_break1_from"), obj.getString("all_break1_to"), obj.getString("all_break2_from"),
                                    obj.getString("all_break2_to"), obj.getString("all_break3_from"), obj.getString("all_break3_to"),
                                    obj.getString("all_break4_from"), obj.getString("all_break4_to"), obj.getString("all_break5_from"),
                                    obj.getString("all_break5_to"),


                                    obj.getString("mon_from_time"), obj.getString("mon_to_time"), obj.getString("mon_break1_from"), obj.getString("mon_break1_to"), obj.getString("mon_break2_from"),
                                    obj.getString("mon_break2_to"), obj.getString("mon_break3_from"), obj.getString("mon_break3_to"),
                                    obj.getString("mon_break4_from"), obj.getString("mon_break4_to"), obj.getString("mon_break5_from"),
                                    obj.getString("mon_break5_to"),


                                    obj.getString("tue_from_time"), obj.getString("tue_to_time"), obj.getString("tue_break1_from"),
                                    obj.getString("tue_break1_to"), obj.getString("tue_break2_from"),
                                    obj.getString("tue_break2_to"), obj.getString("tue_break3_from"), obj.getString("tue_break3_to"),
                                    obj.getString("tue_break4_from"), obj.getString("tue_break4_to"), obj.getString("tue_break5_from"),
                                    obj.getString("tue_break5_to"),


                                    obj.getString("wed_from_time"), obj.getString("wed_to_time"), obj.getString("wed_break1_from"),
                                    obj.getString("wed_break1_to"), obj.getString("wed_break2_from"),
                                    obj.getString("wed_break2_to"), obj.getString("wed_break3_from"), obj.getString("wed_break3_to"),
                                    obj.getString("wed_break4_from"), obj.getString("wed_break4_to"), obj.getString("wed_break5_from"),
                                    obj.getString("wed_break5_to"),


                                    obj.getString("thru_from_time"), obj.getString("thru_to_time"), obj.getString("thru_break1_from"),
                                    obj.getString("thru_break1_to"), obj.getString("thru_break2_from"),
                                    obj.getString("thru_break2_to"), obj.getString("thru_break3_from"), obj.getString("thru_break3_to"),
                                    obj.getString("thru_break4_from"), obj.getString("thru_break4_to"), obj.getString("thru_break5_from"),
                                    obj.getString("thru_break5_to"),


                                    obj.getString("fri_from_time"), obj.getString("fri_to_time"), obj.getString("fri_break1_from"),
                                    obj.getString("fri_break1_to"), obj.getString("fri_break2_from"),
                                    obj.getString("fri_break2_to"), obj.getString("fri_break3_from"), obj.getString("fri_break3_to"),
                                    obj.getString("fri_break4_from"), obj.getString("fri_break4_to"), obj.getString("fri_break5_from"),
                                    obj.getString("fri_break5_to"),


                                    obj.getString("sat_from_time"), obj.getString("sat_to_time"), obj.getString("sat_break1_from"),
                                    obj.getString("sat_break1_to"), obj.getString("sat_break2_from"),
                                    obj.getString("sat_break2_to"), obj.getString("sat_break3_from"), obj.getString("sat_break3_to"),
                                    obj.getString("sat_break4_from"), obj.getString("sat_break4_to"), obj.getString("sat_break5_from"),
                                    obj.getString("sat_break5_to"),

                                    obj.getString("sun_from_time"), obj.getString("sun_to_time"), obj.getString("sun_break1_from"),
                                    obj.getString("sun_break1_to"), obj.getString("sun_break2_from"),
                                    obj.getString("sun_break2_to"), obj.getString("sun_break3_from"), obj.getString("sun_break3_to"),
                                    obj.getString("sun_break4_from"), obj.getString("sun_break4_to"), obj.getString("sun_break5_from"),
                                    obj.getString("sun_break5_to"), obj.getString("staff_serving")

                            );

                            LandingActivity.staff_data_array.add(staff_data);
                        }
                        // set staff list
                        adapter = new AddStaffAdap(getActivity(), LandingActivity.staff_data_array);
                        add_staff_list.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pos = LandingActivity.staff_data_array.size() - 1;
/*
                    adapter.remove(adapter.getItem(pos)); //not working t all! why ?
*/
                    add_staff_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                    Log.d("Serving", "check-status" +LandingActivity.staff_data_array.get(i).getStaff_serving());

                                    if (LandingActivity.staff_data_array.get(i).getStaff_serving().equalsIgnoreCase("Serving")) {
                                        Toast.makeText(getActivity(), "Currently serving customer ", Toast.LENGTH_LONG).show();
                                    } else {
                                        hitAPI("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=deleteStaff&business_id=" +
                                                mPref.getBusnessId()

                                                + "&address_id=" + mPref.getAddressId() + "&staff_id=" + LandingActivity.staff_data_array.get(i).getStaff_id());

                                        //    Toast.makeText(getContext(), "" + LandingActivity.staff_data_array.get(i).getStaff_id() + "bid" + business_id + "add" + address_id, Toast.LENGTH_SHORT).show();


                                        try {
                                            //hit api
                                            Uri.Builder builder = new Uri.Builder();
                                            builder.scheme("http")
                                                    .authority("lifeoninternet.com")
                                                    .appendPath(Utils.stringBuilder())
                                                    .appendPath("api.php")
                                                    .appendQueryParameter("action", "staffList")
                                                    .appendQueryParameter("address_id", /*LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()*/
                                                            mPref.getAddressId());

                                            Log.e("stafflist", builder.build().toString());
                                            if (AppUtils.isNetworkAvailable(getActivity()))
                                                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                                            else {
                                                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
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
                                    Log.e("deleteurl", url);
                                    Log.e("deleteurlres", response);
                                    try {
                                        JSONObject main_obj = new JSONObject(response);
                                        dialog.dismiss();
                                        //  Toast.makeText(getActivity(), "" + main_obj.getString("message") + "\n Press the refresh button", Toast.LENGTH_SHORT).show();

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
                    //   add_staff_list.notify();
                    //    add_staff_list.invalidate();
                    adapter.notifyDataSetChanged();
                }


                add_staff_list.setLongClickable(true);

                if (only_me_flag.equals("No")) {
                    add_staff_onlyme_btn.setChecked(false);
                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }
}
