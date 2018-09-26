package atw.lifeoninternet;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
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
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperActivity;
import helper.HttpresponseUpd;
import helper.LocationUpd;

/**
 * Created by Vivek yadav on 9/21/2017.
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
   /* private HttpresponseUpd callback;*/

    private String post_tag = "";

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static int SPLASH_TIME_OUT = 3000;


    private boolean mAlreadyStartedService = false;
    private Sharedpreferences mPref;
    private String version;
    private Dialog mDialougeBox;

    private boolean landing_status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mPref = Sharedpreferences.getUserDataObj(this);
        loc_callback = this;

        http_callback = this;


        //initialize butterknife and shared preferance
        ButterKnife.bind(this);
     /*   NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle("Hello Hi").setContentText("Vivek yadav").
                setContentTitle("Imp").setSmallIcon(R.mipmap.ic_launcher).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);*/


        initializeSharedData();

        // set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        triggerLocation(loc_callback);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            Log.d("LandingActivity", "version" + version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        hitVersionApi("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=checkVersion&version=" + version);


    }

    private void hitVersionApi(final String urll) {

        final StringRequest strReq = new StringRequest(Request.Method.GET,
                urll.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("url", urll);
                Log.e("urlres", response);
                try {
                    JSONObject main_obj = new JSONObject(response);
                    String error = main_obj.getString("error");
                    String message = main_obj.getString("message");
                    String version = main_obj.getString("version");
                    String status = main_obj.getString("status");
                    String updated = main_obj.getString("updated");

                    if (error.equalsIgnoreCase("true")) {

                        Toast.makeText(SplashActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                    } else {


                        if (updated.equalsIgnoreCase("no")) {

                            showForceUpdateDialog(message, status);


                        } else {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    // This method will be executed once the timer is over
                                    // Start your app main activity
                                    Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("src", "def");
                                    startActivity(intent);
                                    landing_status = false;
                                    finish();
                                }
                            }, SPLASH_TIME_OUT);

                        }
                    }

                } catch (JSONException e) {
                    //  Utils.stopProgress(getActivity());
                    Log.e("error", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";

                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //   Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();
            }
        });
       /* strReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        try {
            Volley.newRequestQueue(this).add(strReq);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void showForceUpdateDialog(String message, String status) {


        mDialougeBox = new Dialog(this);
        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // mDialougeBox.setContentView(R.layout.rate_application_items);
        mDialougeBox.setContentView(R.layout.update_application_items);
        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
        mDialougeBox.show();

        Button update_now_btn = (Button) mDialougeBox.findViewById(R.id.update_now_btn);
        TextView message_tv = (TextView) mDialougeBox.findViewById(R.id.update_dialouge_message_tv);

        message_tv.setText(message);

        update_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=atw.lifeoninternet");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        Button remindme_later = (Button) mDialougeBox.findViewById(R.id.remindme_later_btn);
        remindme_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("src", "def");
                startActivity(intent);
                landing_status = false;
                finish();
            }
        });

        mDialougeBox.setCancelable(false);


    }

    @Override
    protected void onResume() {
        super.onResume();

        triggerLocation(loc_callback);
        hitVersionApi("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=checkVersion&version=" + version);


    }

    @Override
    public void getLoc(Location loc) {

        Log.e("loc", "okk2");

     /*   if (loc == null) {

            snackbar = Snackbar.make(parent, "User location couldn't fetched!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
            Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("src", "def");
            startActivity(intent);
            finish();


        } else {*/

        tag.setVisibility(View.GONE);
        progress_bar.setVisibility(View.GONE);

        try {
            // save current location
            editor = AppConstants.app_data.edit();
            editor.putString("lat", loc.getLatitude() + "");
            editor.putString("long", loc.getLongitude() + "");
            editor.commit();

            mPref.setLat("" + loc.getLatitude());
            mPref.setLong("" + loc.getLongitude());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (AppUtils.isNetworkAvailable(this)) {
            try {
                post_tag = "address";
                AppUtils.getAdd("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + loc.getLatitude() + "," + loc.getLongitude() + "&sensor=true", this, http_callback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            snackbar = Snackbar.make(parent, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }

    }


    @Override
    public void getResponse(String response) {

        Log.e("res", response + "tag" + post_tag);

        if (response.contains("error")) {
            if (mPref.getIsUserLoggedIn() == Boolean.FALSE) {
             /*   Intent intent = new Intent(getApplicationContext(), UserAuthACtivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
            } else {

              /*  if (landing_status == true) {

                    Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("src", "def");
                    startActivity(intent);
                    finish();
                } else {

                }*/
            }
           /* Snackbar snackbar = Snackbar.make(parent, "Not able to find location", Snackbar.LENGTH_LONG);
            snackbar.show();*/
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

      /*      if (mPref.getIsUserLoggedIn() == Boolean.FALSE) {
                Intent intent = new Intent(getApplicationContext(), UserAuthACtivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {*/

          /*  if (landing_status == true) {

                Intent intent = new Intent(getApplicationContext(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("src", "def");
                startActivity(intent);
                finish();
            } else {

            }*/

            /*}*/

            Log.d("SplashActivity", "short_address" + response);
        }
    }
}
