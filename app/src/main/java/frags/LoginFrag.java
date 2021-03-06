package frags;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by Vivek on 9/25/2017.
 */

public class LoginFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.login_password_input)
    public EditText password_input;

    @BindView(R.id.login_phone_input)
    public EditText phone_input;

    @BindView(R.id.staff_login_phone_input)
    public EditText staff_phone_input;

    @BindView(R.id.staff_login_password_input)
    public EditText staff_password_input;


    @BindView(R.id.frag_login_staff_ll)
    public LinearLayout mStaffLi;

    @BindView(R.id.frag_login_user_ll)
    public LinearLayout mUserLl;

    @BindView(R.id.frag_login_user_tv)
    public TextView mUserTv;

    @BindView(R.id.frag_login_staff_tv)
    public TextView mStaffTv;

    @BindView(R.id.login_btn)
    public TextView mLoginBtn;
    @BindView(R.id.staff_login_btn)
    public TextView mStaffLoginBtn;

    private HttpresponseUpd callback;

    private Snackbar snackbar;

    private SharedPreferences.Editor editor;

    private String post_tag = "";

    private Sharedpreferences mPrefs;

    private TextView mOtpSubmitTv, mOtpCancelTv, mResendOtpTv;
    private EditText mOtpEt;
    private Dialog verifyOtpDialog, messageInputWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.login_screen, null);

        ButterKnife.bind(this, Mroot);
        mPrefs = Sharedpreferences.getUserDataObj(getActivity());

        initializeSharedData();
        mUserTv.setTextColor(Color.WHITE);
        mUserTv.setBackgroundColor(Color.BLUE);
        callback = this;


        return Mroot;
    }

    @OnClick({R.id.frag_login_user_tv, R.id.frag_login_staff_tv, R.id.staff_login_btn})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.frag_login_user_tv:
                mUserLl.setVisibility(View.VISIBLE);
                mStaffLi.setVisibility(View.GONE);
                mLoginBtn.setVisibility(View.VISIBLE);
                mStaffLoginBtn.setVisibility(View.GONE);
                mStaffTv.setBackgroundColor(Color.WHITE);
                mStaffTv.setTextColor(Color.BLUE);
                mUserTv.setTextColor(Color.WHITE);
                mUserTv.setBackgroundColor(Color.BLUE);


                break;
            case R.id.frag_login_staff_tv:
                mUserLl.setVisibility(View.GONE);
                mStaffLi.setVisibility(View.VISIBLE);
                mLoginBtn.setVisibility(View.GONE);
                mStaffLoginBtn.setVisibility(View.VISIBLE);
                mStaffTv.setBackgroundColor(Color.BLUE);
                mStaffTv.setTextColor(Color.WHITE);
                mUserTv.setTextColor(Color.BLUE);
                mUserTv.setBackgroundColor(Color.WHITE);

                break;
            case R.id.staff_login_btn:
                staffLoginFun();
                break;


        }

    }

    private void staffLoginFun() {
        if (staff_phone_input.getText().toString().equals(""))
            staff_phone_input.setError("Phone Number cannot be blank!!!");
        else if (staff_password_input.getText().toString().equals(""))
            staff_password_input.setError("Password cannot be blank!!!");
        else {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "staffSignin")
                    .appendQueryParameter("password", staff_password_input.getText().toString())
                    .appendQueryParameter("mobile", staff_phone_input.getText().toString());

            String myUrl = builder.build().toString();
            Log.e("urlStafflogin", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "StaffLogin";
                AppUtils.getStringData(myUrl, getActivity(), callback);
            } else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        }

    }


    @OnClick(R.id.login_btn)
    void login() {
        if (phone_input.getText().toString().equals(""))
            phone_input.setError("Phone Number cannot be blank!!!");
        else if (password_input.getText().toString().equals(""))
            password_input.setError("Password cannot be blank!!!");
        else {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "signin")
                    .appendQueryParameter("password", password_input.getText().toString())
                    .appendQueryParameter("mobile", phone_input.getText().toString());

            String myUrl = builder.build().toString();
            Log.e("urllogin", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "login";
                AppUtils.getStringData(myUrl, getActivity(), callback);
            } else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        }


    }

    @OnClick(R.id.login_forgot_pwd)
    void forgot() {
        getForgotDialog();
    }

    public void getForgotDialog() {

        messageInputWindow = new Dialog(getContext());
        messageInputWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        messageInputWindow.setContentView(R.layout.forgot_pwd_dialog_screen);
        messageInputWindow.getWindow().setGravity(Gravity.CENTER);
        messageInputWindow.show();

        final EditText email = (EditText) messageInputWindow.findViewById(R.id.fp_email_input);

        TextView done = (TextView) messageInputWindow.findViewById(R.id.fp_submit_btn);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")) {

                } else {

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "forgotPassword")
                            .appendQueryParameter("email", email.getText().toString())
                    ;

                    String myUrl = builder.build().toString();
                    Log.e("urlforgot", myUrl);

                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "forgot";
                        AppUtils.getStringData(myUrl, getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                        snackbar.show();

                    }

                }
            }
        });

    }

    @Override
    public void getResponse(String response) {


        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(Mroot, "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);

            snackbar.show();
        } else if (post_tag.equals("login")) {

            try {

                JSONObject main_obj = new JSONObject(response);
                JSONArray main_array = main_obj.getJSONArray("output");
                JSONObject obj = main_array.getJSONObject(0);

                Toast.makeText(getActivity(), "" + main_obj.getString("message"), Toast.LENGTH_SHORT).show();
                if (!obj.getString("user_id").equals("0")) {


                    if (main_obj.getString("message").equalsIgnoreCase("Verify your mobile number")) {

                        showVerifyOtpDialog(obj.getString("user_id"));
                    } else {
                        mPrefs.setEmailId(obj.getString("email"));
                        mPrefs.setName(obj.getString("name"));
                        mPrefs.setUserId(obj.getString("user_id"));
                        mPrefs.setCustId(obj.getString("cust_id"));
                        mPrefs.setMobile(obj.getString("mobile"));
                        mPrefs.setBusnessId(obj.getString("business_id"));
                        mPrefs.setAddressId(obj.getString("address_id"));
                        mPrefs.setIndustryType(obj.getString("industry"));
                        mPrefs.setStaffAdmin("Yes");

                        mPrefs.setIsUserLoggedIn(true);
                        // save user info
                        editor = AppConstants.app_data.edit();
                        editor.putString("name", obj.getString("name"));
                        editor.putString("email", obj.getString("email"));
                        editor.putString("phone", obj.getString("mobile"));
                        editor.putString("user_id", obj.getString("user_id"));
                        editor.commit();

                        Intent intent = new Intent(getActivity(), LandingActivity.class);
                        intent.putExtra("src", "def");
                        startActivity(intent);
                        getActivity().finish();
                    }

                } else {
                    snackbar = Snackbar.make(Mroot, "" + obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, "" + Utils.networkToastMsg(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        } else if (post_tag.equals("StaffLogin")) {

            try {

                JSONObject main_obj = new JSONObject(response);
                JSONArray main_array = main_obj.getJSONArray("output");
                String message = main_obj.getString("message");
                JSONObject obj = main_array.getJSONObject(0);

                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();

                mPrefs.setEmailId(obj.getString("email"));
                mPrefs.setName(obj.getString("name"));
                mPrefs.setUserId(obj.getString("user_id"));
                mPrefs.setCustId(obj.getString("cust_id"));
                //  mPrefs.setStaffId(obj.getString("staff_id"));
                mPrefs.setBusnessId(obj.getString("business_id"));
                mPrefs.setStaffAdmin(obj.getString("admin_status"));

                Log.d("LoginFrag", mPrefs.getStaffAdmin());
                mPrefs.setIndustryType(obj.getString("industry"));
                mPrefs.setIsUserLoggedIn(true);


                try {
                    JSONArray jsonArray = obj.getJSONArray("address_staff");

                    if (jsonArray.length() == 1) {

                        Log.d("LF", "staff_id" + jsonArray.getJSONObject(0).getString("staff_id"));
                        mPrefs.setStaffId(jsonArray.getJSONObject(0).getString("staff_id"));

                    } else {
                        String address_id_1 = jsonArray.getJSONObject(0).getString("address_id");
                        String address_id_2 = jsonArray.getJSONObject(1).getString("address_id");
                        String staff_id_1 = jsonArray.getJSONObject(0).getString("staff_id");
                        String staff_id_2 = jsonArray.getJSONObject(1).getString("staff_id");

                        mPrefs.setStaffId("");
                        mPrefs.setAddressId1(address_id_1);
                        mPrefs.setAddressId2(address_id_2);
                        mPrefs.setStaffId1(staff_id_1);
                        mPrefs.setStaffId2(staff_id_2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                // save user info
                editor = AppConstants.app_data.edit();
                editor.putString("name", obj.getString("name"));
                editor.putString("email", obj.getString("email"));
                editor.putString("phone", obj.getString("mobile"));

                editor.putString("user_id", obj.getString("user_id"));
                editor.commit();


                Intent intent = new Intent(getActivity(), LandingActivity.class);
                intent.putExtra("src", "stafflogin");
                startActivity(intent);
                getActivity().finish();


            } catch (JSONException e) {

            }


        } else if (post_tag.equals("otp_login")) {
            verifyOtpDialog.dismiss();
            try {

                JSONObject main_obj = new JSONObject(response);
                //  String message = main_obj.getString("message");
                snackbar = Snackbar.make(Mroot, "" + main_obj.getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, "" + e.getMessage(), Snackbar.LENGTH_LONG);

                snackbar.show();
            }


        } else {
            messageInputWindow.dismiss();
            try {

                JSONObject main_obj = new JSONObject(response);
                JSONArray main_array = main_obj.getJSONArray("output");
                JSONObject obj = main_array.getJSONObject(0);
                snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();


            } catch (JSONException e) {

            }


        }
    }


    private void showVerifyOtpDialog(final String user_id) {

        verifyOtpDialog = new Dialog(getContext());
        verifyOtpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verifyOtpDialog.setContentView(R.layout.custom_otp_dialouge_login_layout);
        mOtpEt = (EditText) verifyOtpDialog.findViewById(R.id.item_otp_login_et);
        mOtpSubmitTv = (TextView) verifyOtpDialog.findViewById(R.id.item_otp_submit_login_tv);
        mOtpCancelTv = (TextView) verifyOtpDialog.findViewById(R.id.item_otp_cancel_login_tv);
        mResendOtpTv = (TextView) verifyOtpDialog.findViewById(R.id.item_otp_resend_login_tv);

        verifyOtpDialog.getWindow().setGravity(Gravity.CENTER);
        verifyOtpDialog.show();
        mOtpSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "verify_otp")
                        .appendQueryParameter("user_id", user_id)
                        .appendQueryParameter("otp", mOtpEt.getText().toString());

                String myUrl = builder.build().toString();
                Log.e("urlotp", myUrl);

                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "otp_login";
                    AppUtils.getStringData(myUrl, getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }


            }
        });
        mOtpCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtpDialog.dismiss();

            }
        });
        mResendOtpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}
