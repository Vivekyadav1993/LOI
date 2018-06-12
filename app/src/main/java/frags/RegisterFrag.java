package frags;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
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
 * Created by teknik on 9/25/2017.
 */

public class RegisterFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;


    @BindView(R.id.register_name_input)
    EditText name_input;

    @BindView(R.id.register_phone_input)
    EditText phone_input;

    @BindView(R.id.register_email_input)
    EditText email_input;

    @BindView(R.id.register_password_input)
    EditText pwd_input;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView mOtpSubmitTv, mOtpCancelTv, mResendOtpTv;
    private EditText mOtpEt;

    private HttpresponseUpd callback;

    private SharedPreferences.Editor editor;
    private Dialog verifyOtpDialog;


    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private Snackbar snackbar;
    String post_tag = "";
    private Sharedpreferences mPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.register_screen, null);
        ButterKnife.bind(this, Mroot);

        callback = this;
        mPref = Sharedpreferences.getUserDataObj(getActivity());
        initializeSharedData();

        return Mroot;
    }


    @OnClick(R.id.register_btn)
    void register() {

        if (name_input.getText().toString().equals(""))
            name_input.setError("Name cannot be blank!!!");
        else if (phone_input.getText().toString().equals(""))
            phone_input.setError("Phone Number cannot be blank!!!");
        else if (email_input.getText().toString().equals(""))
            email_input.setError("Email Id cannot be blank!!!");
        else if (!email_input.getText().toString().matches(emailPattern))
            email_input.setError("Email Id is not valid!!!");
        else if (pwd_input.getText().toString().equals(""))
            pwd_input.setError("Email Id cannot be blank!!!");
        else {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "signup")
                    .appendQueryParameter("name", name_input.getText().toString())
                    .appendQueryParameter("email", email_input.getText().toString())
                    .appendQueryParameter("password", pwd_input.getText().toString())
                    .appendQueryParameter("mobile", phone_input.getText().toString())
                    .appendQueryParameter("registration_id", "")
                    .appendQueryParameter("lat", AppConstants.app_data.getString("lat", ""))
                    .appendQueryParameter("longi", AppConstants.app_data.getString("long", ""))
                    .appendQueryParameter("address", AppConstants.app_data.getString("address", ""));

            String myUrl = builder.build().toString();
            Log.e("urlsignin", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "signin";
                AppUtils.getStringData(myUrl, getActivity(), callback);
            } else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }

        }


    }

    @Override
    public void getResponse(String response) {
        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(Mroot, "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (post_tag.equalsIgnoreCase("signin")) {

            try {

                JSONObject main_obj = new JSONObject(response);
                JSONArray main_array = main_obj.getJSONArray("output");
                JSONObject obj = main_array.getJSONObject(0);
                String otp_number = obj.getString("otp_number");
                //   Log.d("RF","user_id"+obj.getString("user_id"));
                mPref.setOtpNumber(otp_number);
                if (obj.getString("message").equals("Verify OTP Number")) {

                    // save user info
                  /*  editor = AppConstants.app_data.edit();
                    editor.putString("name", name_input.getText().toString());
                    editor.putString("email", email_input.getText().toString());
                    editor.putString("phone", phone_input.getText().toString());

                    editor.putString("user_id", obj.getString("user_id"));
                    editor.commit();
*/
                    showVerifyOtpDialog();

                } else {

                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, "Parsing error occurred!!! " + e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else if (post_tag.equalsIgnoreCase("otp")) {
            try {
                JSONObject obj = new JSONObject(response);

                String message = obj.getString("message");
                String statuscode = obj.getString("statuscode");

                if (statuscode.equals("200")) {
                    verifyOtpDialog.dismiss();
                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();

                    mPref.setEmailId(obj.getString("email"));
                    mPref.setName(obj.getString("name"));
                    mPref.setMobile(obj.getString("mobile"));
                    mPref.setUserId(obj.getString("user_id"));
                    mPref.setCustId(obj.getString("cust_id"));
                    mPref.setBusnessId(obj.getString("business_id"));
                    mPref.setAddressId(obj.getString("address_id"));
                    mPref.setLat(AppConstants.app_data.getString("lat", ""));
                    mPref.setLong(AppConstants.app_data.getString("long", ""));
                    mPref.setIsUserLoggedIn(true);
                    // save user info
                    editor = AppConstants.app_data.edit();
                    editor.putString("name", obj.getString("name"));
                    editor.putString("email", obj.getString("email"));
                    editor.putString("phone", obj.getString("mobile"));

                    editor.putString("user_id", obj.getString("user_id"));
                    editor.commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("src", "def");
                    Intent intent = new Intent(getActivity(), LandingActivity.class);
                    intent.putExtra("src", "def");
                    startActivity(intent);
                    getActivity().finish();

                 /*   fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.userauth_container, new LoginFrag()).commit();
*/

                } else {
                    Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void showVerifyOtpDialog() {

        verifyOtpDialog = new Dialog(getContext());
        verifyOtpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        verifyOtpDialog.setContentView(R.layout.custom_otp_dialouge_layout);
        mOtpEt = (EditText) verifyOtpDialog.findViewById(R.id.item_otp_et);
        mOtpSubmitTv = (TextView) verifyOtpDialog.findViewById(R.id.item_otp_submit_tv);
        mOtpCancelTv = (TextView) verifyOtpDialog.findViewById(R.id.item_otp_cancel_tv);
        mResendOtpTv = (TextView) verifyOtpDialog.findViewById(R.id.item_otp_resend_tv);

        verifyOtpDialog.getWindow().setGravity(Gravity.CENTER);
        verifyOtpDialog.show();


        mOtpSubmitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("RF", "otp" + mPref.getOtpNumber() + "otp edit" + mOtpEt.getText().toString());

                if (mOtpEt.getText().toString().equalsIgnoreCase(mPref.getOtpNumber())) {
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath(Utils.stringBuilder())
                            .appendPath("api.php")
                            .appendQueryParameter("action", "verify_otp")
                            .appendQueryParameter("otp", mOtpEt.getText().toString())
                            .appendQueryParameter("name", name_input.getText().toString())
                            .appendQueryParameter("email", email_input.getText().toString())
                            .appendQueryParameter("password", pwd_input.getText().toString())
                            .appendQueryParameter("mobile", phone_input.getText().toString())
                            .appendQueryParameter("lat", AppConstants.app_data.getString("lat", ""))
                            .appendQueryParameter("longi", AppConstants.app_data.getString("long", ""));

                    String myUrl = builder.build().toString();
                    Log.e("urlotp", myUrl);

                    if (AppUtils.isNetworkAvailable(getActivity())) {
                        post_tag = "otp";
                        AppUtils.getStringData(myUrl, getActivity(), callback);
                    } else {
                        snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Opt is not correct", Toast.LENGTH_SHORT).show();
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


