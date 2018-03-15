package frags;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

/**
 * Created by teknik on 9/25/2017.
 */

public class RegisterFrag extends HelperFrags  implements HttpresponseUpd{

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

    private  HttpresponseUpd callback;

    private  SharedPreferences.Editor editor;

    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.register_screen,null);
        ButterKnife.bind(this, Mroot);

        callback = this;

        initializeSharedData();

        return  Mroot;
    }


    @OnClick(R.id.register_btn)
    void register(){

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
                    .appendPath("new_service")
                    .appendPath("api.php")
                    .appendQueryParameter("action", "signup")
                    .appendQueryParameter("name",name_input.getText().toString())
                    .appendQueryParameter("email",email_input.getText().toString())
                    .appendQueryParameter("password",pwd_input.getText().toString())
                    .appendQueryParameter("mobile",phone_input.getText().toString())
                    .appendQueryParameter("registration_id","")
                    .appendQueryParameter("lat", AppConstants.app_data.getString("lat",""))
                    .appendQueryParameter("longi",AppConstants.app_data.getString("long",""))
                    .appendQueryParameter("address",AppConstants.app_data.getString("address",""));

            String myUrl = builder.build().toString();
            Log.e("urlsignin", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(myUrl, getActivity() ,callback);
            else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

        }



        }

    @Override
    public void getResponse(String response) {
        Log.e("responce" , response);

        if (response.contains("error")){
            snackbar = Snackbar.make(Mroot, "Network error occurred!!!"+response, Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else{

            try{

                JSONObject main_obj = new JSONObject(response);
                JSONArray main_array = main_obj.getJSONArray("output");
                JSONObject obj = main_array.getJSONObject(0);

                if (!obj.getString("user_id").equals("0")) {

                    // save user info
                    editor = AppConstants.app_data.edit();
                    editor.putString("name", name_input.getText().toString());
                    editor.putString("email", email_input.getText().toString());
                    editor.putString("phone", phone_input.getText().toString());

                    editor.putString("user_id", obj.getString("user_id"));
                    editor.commit();

                    snackbar = Snackbar.make(Mroot, "Parsing error occurred!!! " + obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();


                    Intent intent = new Intent(getActivity() , LandingActivity.class);
                    intent.putExtra("src","def");
                    startActivity(intent);
                    getActivity().finish();


                }
                else {
                    snackbar = Snackbar.make(Mroot,  obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();

                }

            }
            catch (JSONException e){
                snackbar = Snackbar.make(Mroot, "Parsing error occurred!!! "+e.getMessage(), Snackbar.LENGTH_LONG);

                snackbar.show();
            }


        }
    }
}


