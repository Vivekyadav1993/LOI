package frags;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;


import static android.app.Activity.RESULT_OK;

/**
 * Created by teknik on 10/3/2017.
 */

public class NewStaffInfoFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.new_staffinfo_browse_button)
    ImageView new_staffinfo_browse_button;

    @BindView(R.id.new_staffinfo_tag)
    TextView new_staffinfo_tag;

    private Bundle bundle;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Snackbar snackbar;

    private HttpresponseUpd callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_staff_pic_info_screen, null);

        callback = this;

        ButterKnife.bind(this, Mroot);

        bundle = getArguments();

        //set staff name
     /*   new_staffinfo_tag.setText(LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFirst_name() + " "
                + LandingActivity.staff_data_array.get(bundle.getInt("pos")).getLast_name());
*/
        return Mroot;
    }

    @OnClick(R.id.new_staffinfo_next_btn)
    void gonext() {

        // call api to create event id
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "createStaff")
                .appendQueryParameter("staff_id", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getStaff_id())
                .appendQueryParameter("address_id", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAddress_id())
                .appendQueryParameter("onlyme_status", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getOnly_me_flag())
                .appendQueryParameter("admin_status", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAdmin_flag())
                .appendQueryParameter("first_name", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFirst_name())
                .appendQueryParameter("last_name", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getLast_name())
                .appendQueryParameter("email", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getEmail())
                .appendQueryParameter("phone", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getPhone())
                .appendQueryParameter("address", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAddress())
                .appendQueryParameter("all_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break1_from())
                .appendQueryParameter("all_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break1_to())
                .appendQueryParameter("all_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break2_from())
                .appendQueryParameter("all_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break2_to())
                .appendQueryParameter("all_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break3_from())
                .appendQueryParameter("all_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break3_to())
                .appendQueryParameter("all_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break4_from())
                .appendQueryParameter("all_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break4_to())
                .appendQueryParameter("all_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break5_from())
                .appendQueryParameter("all_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getAll_break5_to())

                .appendQueryParameter("start_date", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getStart_date())
                .appendQueryParameter("end_date", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getEnd_date())


                .appendQueryParameter("mon_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_from_time())
                .appendQueryParameter("mon_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_to_time())

                .appendQueryParameter("mon_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break1_from())
                .appendQueryParameter("mon_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break1_to())
                .appendQueryParameter("mon_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break2_from())
                .appendQueryParameter("mon_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break2_to())
                .appendQueryParameter("mon_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break3_from())
                .appendQueryParameter("mon_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break3_to())
                .appendQueryParameter("mon_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break4_from())
                .appendQueryParameter("mon_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break4_to())
                .appendQueryParameter("mon_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break5_from())
                .appendQueryParameter("mon_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getMon_break5_to())


                .appendQueryParameter("tue_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_from_time())
                .appendQueryParameter("tue_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_to_time())

                .appendQueryParameter("tue_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break1_from())
                .appendQueryParameter("tue_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break1_to())
                .appendQueryParameter("tue_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break2_from())
                .appendQueryParameter("tue_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break2_to())
                .appendQueryParameter("tue_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break3_from())
                .appendQueryParameter("tue_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break3_to())
                .appendQueryParameter("tue_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break4_from())
                .appendQueryParameter("tue_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break4_to())
                .appendQueryParameter("tue_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break5_from())
                .appendQueryParameter("tue_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getTue_break5_to())

                .appendQueryParameter("wed_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_from_time())
                .appendQueryParameter("wed_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_to_time())

                .appendQueryParameter("wed_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break1_from())
                .appendQueryParameter("wed_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break1_to())
                .appendQueryParameter("wed_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break2_from())
                .appendQueryParameter("wed_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break2_to())
                .appendQueryParameter("wed_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break3_from())
                .appendQueryParameter("wed_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break3_to())
                .appendQueryParameter("wed_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break4_from())
                .appendQueryParameter("wed_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break4_to())
                .appendQueryParameter("wed_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break5_from())
                .appendQueryParameter("wed_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getWed_break5_to())

                .appendQueryParameter("thru_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_from_time())
                .appendQueryParameter("thru_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_to_time())

                .appendQueryParameter("thru_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break1_from())
                .appendQueryParameter("thru_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break1_to())
                .appendQueryParameter("thru_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break2_from())
                .appendQueryParameter("thru_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break2_to())
                .appendQueryParameter("thru_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break3_from())
                .appendQueryParameter("thru_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break3_to())
                .appendQueryParameter("thru_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break4_from())
                .appendQueryParameter("thru_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break4_to())
                .appendQueryParameter("thru_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break5_from())
                .appendQueryParameter("thru_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getThru_break5_to())

                .appendQueryParameter("fri_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_from_time())
                .appendQueryParameter("fri_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_to_time())

                .appendQueryParameter("fri_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break1_from())
                .appendQueryParameter("fri_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break1_to())
                .appendQueryParameter("fri_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break2_from())
                .appendQueryParameter("fri_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break2_to())
                .appendQueryParameter("fri_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break3_from())
                .appendQueryParameter("fri_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break3_to())
                .appendQueryParameter("fri_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break4_from())
                .appendQueryParameter("fri_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break4_to())
                .appendQueryParameter("fri_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break5_from())
                .appendQueryParameter("fri_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getFri_break5_to())


                .appendQueryParameter("sat_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_from_time())
                .appendQueryParameter("sat_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_to_time())

                .appendQueryParameter("sat_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break1_from())
                .appendQueryParameter("sat_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break1_to())
                .appendQueryParameter("sat_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break2_from())
                .appendQueryParameter("sat_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break2_to())
                .appendQueryParameter("sat_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break3_from())
                .appendQueryParameter("sat_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break3_to())
                .appendQueryParameter("sat_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break4_from())
                .appendQueryParameter("sat_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break4_to())
                .appendQueryParameter("sat_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break5_from())
                .appendQueryParameter("sat_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSat_break5_to())

                .appendQueryParameter("sun_from_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_from_time())
                .appendQueryParameter("sun_to_time", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_to_time())

                .appendQueryParameter("sun_break1_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break1_from())
                .appendQueryParameter("sun_break1_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break1_to())
                .appendQueryParameter("sun_break2_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break2_from())
                .appendQueryParameter("sun_break2_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break2_to())
                .appendQueryParameter("sun_break3_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break3_from())
                .appendQueryParameter("sun_break3_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break3_to())
                .appendQueryParameter("sun_break4_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break4_from())
                .appendQueryParameter("sun_break4_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break4_to())
                .appendQueryParameter("sun_break5_from", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break5_from())
                .appendQueryParameter("sun_break5_to", LandingActivity.staff_data_array.get(bundle.getInt("pos")).getSun_break5_to());


        Log.e("url", builder.build().toString());


        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
        // replaceFrag(new AddStaffFrag(), new Bundle() , NewStaffInfoFrag.class.getName());
    }


    @OnClick(R.id.new_staffinfo_back_btn)
    void goBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.new_staffinfo_workingdays_btn)
    void goNext() {
        Bundle _bundle = new Bundle();
      /*  _bundle.putString("src", bundle.getString("src"));
        _bundle.putInt("pos", bundle.getInt("pos"));
     */  // _bundle.putInt("create_pos", bundle.getInt("create_pos"));
        replaceFrag(new NewStaffWorkingHourFrag(), _bundle, NewStaffInfoFrag.class.getName());
    }

    @OnClick(R.id.new_staffinfo_breaks_btn)
    void goNextBreak() {
        Bundle _bundle = new Bundle();
      //  _bundle.putString("src", bundle.getString("src"));
      //  _bundle.putInt("pos", bundle.getInt("pos"));

        replaceFrag(new NewStaffBreakTimeFrag(), _bundle, NewStaffInfoFrag.class.getName());
    }

    @OnClick(R.id.new_staffinfo_browse_button)
    void browse() {


        //checkPermission(new_staffinfo_browse_button);
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
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("staff_id").equals("0")) {
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    // save data
                    LandingActivity.staff_data_array.get(bundle.getInt("pos")).setStaff_id(obj.getString("staff_id"));
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();

                    Bundle _bundle = new Bundle();

                    _bundle.putInt("new_staff_id", Integer.parseInt(obj.getString("staff_id")));
                    _bundle.putInt("address_id", Integer.parseInt(obj.getString("address_id")));

                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));
                 /*   fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    AddStaffFrag addStaffFrag = new AddStaffFrag();
                    addStaffFrag.setArguments(bundle);
                    getFragmentManager().popBackStack();
                    fragmentTransaction.replace(R.id.parentcontainer, addStaffFrag).addToBackStack(addStaffFrag.getTag()).commit();
                */    // fragmentTransaction.replace(R.id.parentcontainer,addStaffFrag).addToBackStack(null).commit();
                    replaceFrag(new AddStaffFrag(), _bundle, BusinessDetailsFrag.class.getName());
                }

            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }


    }


}
