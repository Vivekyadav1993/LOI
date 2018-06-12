package atw.lifeoninternet;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import atw.lifeoninternet.utils.Sharedpreferences;
import butterknife.BindView;
import butterknife.ButterKnife;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperActivity;
import helper.HttpresponseUpd;
import helper.LocationUpd;

/**
 * Created by teknik on 9/21/2017.
 */

public class SplashActivity extends HelperActivity implements LocationUpd, HttpresponseUpd {

    @BindView(R.id.getloc_tag)
    TextView tag;

    @BindView(R.id.dot_progress_bar)
    DotProgressBar progress_bar;

    @BindView(R.id.parent_lay)
    RelativeLayout parent;

    private LocationUpd loc_callback;

    private HttpresponseUpd http_callback;

    private SharedPreferences.Editor editor;

    private Snackbar snackbar;

    private String post_tag = "";

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;


    private boolean mAlreadyStartedService = false;
    private Sharedpreferences mPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mPref = Sharedpreferences.getUserDataObj(this);
        loc_callback = this;

        http_callback = this;

        //initialize butterknife and shared preferance
        ButterKnife.bind(this);

        initializeSharedData();

        // set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

            /*  Intent intent = new Intent(SplashActivity.this, UserAuthACtivity.class);
        intent.putExtra("src", "def");
        startActivity(intent);
        finish();*/

        //trigger location
        triggerLocation(loc_callback);


      /*  if (mPref.getIsUserLoggedIn() == Boolean.FALSE) {
            Intent intent = new Intent(getApplicationContext(), UserAuthACtivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {

            Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("src", "def");
            startActivity(intent);
            finish();
        }*/

      /*  if (AppConstants.app_data.getString("user_id", "").equals("")) {

            Intent intent = new Intent(SplashActivity.this, UserAuthACtivity.class);
            intent.putExtra("src", "def");
            startActivity(intent);
            finish();

        } else {

            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "getBusiness")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""));


            Log.e("url", builder.build().toString());
            post_tag = "check";
            if (AppUtils.isNetworkAvailable(this))
                AppUtils.getStringData(builder.build().toString(), this, http_callback);
            else {
                snackbar = Snackbar.make(parent, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }
        }

*/
    }


    @Override
    public void getLoc(Location loc) {

        Log.e("loc", "okk2");

        if (loc == null) {
            snackbar = Snackbar.make(parent, "user location couldn't fetched!!!", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            tag.setVisibility(View.GONE);
            progress_bar.setVisibility(View.GONE);

            // save current location
            editor = AppConstants.app_data.edit();
            editor.putString("lat", loc.getLatitude() + "");
            editor.putString("long", loc.getLongitude() + "");
            editor.commit();

            mPref.setLat(""+loc.getLatitude());
            mPref.setLong(""+loc.getLongitude());

            Log.d("SS","lat"+loc.getLatitude()+"long"+loc.getLongitude());

            if (AppUtils.isNetworkAvailable(this)) {
                post_tag = "address";
                AppUtils.getAdd("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + loc.getLatitude() + "," + loc.getLongitude() + "&sensor=true", this, http_callback);
            } else {
                snackbar = Snackbar.make(parent, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

        }


    }

    @Override
    public void getResponse(String response) {

        Log.e("res", response + "tag" + post_tag);

        if (response.contains("error")) {
            if (mPref.getIsUserLoggedIn() == Boolean.FALSE) {
                Intent intent = new Intent(getApplicationContext(), UserAuthACtivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {

                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("src", "def");
                startActivity(intent);
                finish();

            }
            Snackbar snackbar = Snackbar.make(parent, response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (post_tag.equals("address")) {

            String[] address_array = response.split(",");
            String short_address = "";

            if (address_array.length == 0) {

            } else {
                short_address = address_array[0];
            }


            // save current address
            editor = AppConstants.app_data.edit();
            editor.putString("address", response);
            editor.putString("short_add", short_address);
            editor.commit();
            Log.d("SplashActivity", "data is" + mPref.getIsUserLoggedIn());

            if (mPref.getIsUserLoggedIn() == Boolean.FALSE) {
                Intent intent = new Intent(getApplicationContext(), UserAuthACtivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {

                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("src", "def");
                startActivity(intent);
                finish();

            }

            Log.d("SplashActivity", "short_address" + response);
        }
    }
}