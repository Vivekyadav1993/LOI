package r2stech.lifeoninternet;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperActivity;
import helper.HttpresponseUpd;
import r2stech.lifeoninternet.utils.Utils;

public class UpdateProfile extends HelperActivity implements HttpresponseUpd {


    @BindView(R.id.update_name_tv)
    public EditText mName;
    @BindView(R.id.update_email_tv)
    public EditText mEmail;

    private HttpresponseUpd callback;
    private Snackbar snackbar;

    //   private Sharedpreferences mPrefs;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        String name = getIntent().getExtras().getString("name");
        String email = getIntent().getExtras().getString("email");
        //   mPrefs = Sharedpreferences.getUserDataObj(UpdateProfile.this);

        mName.setText(name);
        mEmail.setText(email);
        editor = AppConstants.app_data.edit();


        callback = this;


    }

    @OnClick({R.id.update_profile_btn, R.id.act_update_profile_back_iv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.update_profile_btn:

                hitUpdateApi();
                break;

            case R.id.act_update_profile_back_iv:

                onBackPressed();
                break;
        }

    }

    private void hitUpdateApi() {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "updateUserProfile")
                .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                .appendQueryParameter("name", mName.getText().toString())
                .appendQueryParameter("email", mEmail.getText().toString());

        String myUrl = builder.build().toString();
        Log.e("urlgetLsiAdd", myUrl);

        if (AppUtils.isNetworkAvailable(this))
            AppUtils.getStringData(builder.build().toString(), this, callback);
        else {
          /*  snackbar = Snackbar.make(getApplicationContext(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
          */
            Toast.makeText(this, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void getResponse(String response) {

        Log.e("responce", response);

        if (response.contains("error")) {
            Toast.makeText(this, "Network error occurred!!!" + response, Toast.LENGTH_SHORT).show();

        } else {
            try {
                JSONObject main_obj = new JSONObject(response);
                String statuscode = main_obj.getString("statuscode");

                Log.d("UpdateProfile", "0" + statuscode);
                if (statuscode.equals("200")) {

                      editor.putString("name", mName.getText().toString());
                    editor.putString("email", mEmail.getText().toString());
                    editor.commit();
                         Toast.makeText(this, "" + main_obj.getString("message"), Toast.LENGTH_SHORT).show();

                } else if (statuscode.equals("404")) {

                    Toast.makeText(this, "" + main_obj.getString("message"), Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
