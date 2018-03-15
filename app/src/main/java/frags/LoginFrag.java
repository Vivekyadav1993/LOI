package frags;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.SplashActivity;
import r2stech.lifeoninternet.UserAuthACtivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by teknik on 9/25/2017.
 */

public class LoginFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.login_password_input)
    EditText password_input;

    @BindView(R.id.login_phone_input)
    EditText phone_input;

    private HttpresponseUpd callback;

    private Snackbar snackbar;

    private SharedPreferences.Editor editor;

    private String post_tag = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.login_screen, null);

        ButterKnife.bind(this, Mroot);

        initializeSharedData();

        callback = this;


        return Mroot;
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
                    .appendPath("new_service")
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

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.forgot_pwd_dialog_screen, null);


        final PopupWindow messageInputWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        messageInputWindow.setContentView(layout);
        messageInputWindow.setFocusable(true);
        messageInputWindow.setBackgroundDrawable(new BitmapDrawable());
        messageInputWindow.setOutsideTouchable(true);


        final EditText email = (EditText) layout.findViewById(R.id.fp_email_input);

        TextView done = (TextView) layout.findViewById(R.id.fp_submit_btn);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageInputWindow.dismiss();
                if (email.getText().toString().equals("")) {

                } else {

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("lifeoninternet.com")
                            .appendPath("new_service")
                            .appendPath("api.php")
                            .appendQueryParameter("action", "forgotPassword")
                            .appendQueryParameter("email", email.getText().toString())
                    ;

                    String myUrl = builder.build().toString();

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


        try {


            messageInputWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        } catch (WindowManager.BadTokenException e) {


        }
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

                if (!obj.getString("user_id").equals("0")) {

                    // save user info
                    editor = AppConstants.app_data.edit();
                    editor.putString("name", obj.getString("name"));
                    editor.putString("email", obj.getString("email"));
                    editor.putString("phone", obj.getString("mobile"));

                    editor.putString("user_id", obj.getString("user_id"));
                    editor.commit();

                    snackbar = Snackbar.make(Mroot, "Parsing error occurred!!! " + obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();


                    Intent intent = new Intent(getActivity(), LandingActivity.class);
                    intent.putExtra("src", "def");
                    startActivity(intent);
                    getActivity().finish();


                } else {
                    snackbar = Snackbar.make(Mroot, "Parsing error occurred!!! " + obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();

                }

            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, "Parsing error occurred!!! " + e.getMessage(), Snackbar.LENGTH_LONG);

                snackbar.show();
            }


        } else {

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

}
