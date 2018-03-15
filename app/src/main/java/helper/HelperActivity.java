package helper;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import r2stech.lifeoninternet.R;


public class HelperActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        ResultCallback<LocationSettingsResult> {

protected static final String TAG = "location-settings";
/**
 * Constant used in the location settings dialog.
 */
protected static final int REQUEST_CHECK_SETTINGS = 0x1;
/**
 * The desired interval for location updates. Inexact. Updates may be more or less frequent.
 */
public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 50000;
/**
 * The fastest rate for active location updates. Exact. Updates will never be more frequent
 * than this value.
 */
public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
        UPDATE_INTERVAL_IN_MILLISECONDS / 2;
// Keys for storing activity state in the Bundle.
protected final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
protected final static String KEY_LOCATION = "location";
protected final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";
/**
 * Provides the entry point to Google Play services.
 */
protected GoogleApiClient mGoogleApiClient;
/**
 * Stores parameters for requests to the FusedLocationProviderApi.
 */
protected LocationRequest mLocationRequest;
/**
 * Stores the types of location services the client is interested in using. Used for checking
 * settings to determine if the device has optimal location settings.
 */
protected LocationSettingsRequest mLocationSettingsRequest;
/**
 * Represents a geographical location.
 */
protected Location mCurrentLocation;

protected Boolean mRequestingLocationUpdates;
/**
 * Time when the location was updated represented as a String.
 */
protected String mLastUpdateTime;
// 28.5273° N, 77.1515° E



    private int mYear, mMonth, mDay, mHour, mMinute;


    private Calendar c;

    private LocationUpd _callback;

    private Bundle _savedInstanceState;

    private String loc_tag = "";

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _savedInstanceState= savedInstanceState;
        updateValuesFromBundle(_savedInstanceState);
        // Kick off the process of building the GoogleApiClient, LocationRequest, and
        // LocationSettingsRequest objects.
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

    }



   /* public void addFrag(Fragment frag , Bundle bundle){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frag.setArguments(bundle);
        fragmentTransaction.add(R.id.container, frag).commit();

    }*/

  /*  public void replaceFrag(Fragment frag , Bundle bundle , String backclass){
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frag.setArguments(bundle);
        fragmentTransaction.replace(R.id.result_frame, frag).addToBackStack(backclass).commit();

    }
*/


    public void initializeSharedData(){

         //initialize share preference
        AppConstants.app_data =getSharedPreferences("AppData", MODE_PRIVATE);

    }

    public void triggerLocation( LocationUpd callback){

        _callback = callback;
       startUpdatesButtonHandler();

        Log.e("location","okk");


    }


    public void triggerLocationConti( LocationUpd callback){
         loc_tag = "continue";
        _callback = callback;
        startUpdatesButtonHandler();




    }

    public void showMSg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }





    public void getDate(final TextView text, final TextView text2){
        // Get Current Date
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);




        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String oldFormat= "yyyy-MM-dd";
                        String newFormat= "dd-MMM-yy";

                        String formatedDate = "";
                        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(oldFormat);
                        Date startDate = null , currentdate= null;

                        try {

                            startDate = dateFormat.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            currentdate= dateFormat.parse(mYear + "-" + (mMonth + 1) + "-" + mDay);


                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat(newFormat);
                        formatedDate = timeFormat.format(startDate);



                        if (text==text2) {

                            text.setText(formatedDate);

                        }
                        else{



                            text.setText(formatedDate);

                            text2.setText(formatedDate);





                        }





                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);



        datePickerDialog.show();


    }

    public void getBreakTime(final TextView text , final String frmTime , final String toTime){



        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        mHour = hourOfDay;
                        mMinute = minute;

                        String timeSet = "";

                        if (mHour > 12) {
                            mHour -= 12;
                            timeSet = "PM";
                        } else if (mHour == 0) {
                            mHour += 12;
                            timeSet = "AM";
                        } else if (mHour == 12){
                            timeSet = "PM";
                        }else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (mMinute < 10)
                            min = "0" + mMinute ;
                        else
                            min = String.valueOf(mMinute);


                        String hr="";

                        if (mHour < 10)
                            hr = "0" + mHour ;
                        else
                            hr = String.valueOf(mHour);




                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hr).append(':')
                                .append(min ).append(" ").append(timeSet).toString();





                            try {
                                Date mToday = new Date();

                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm aa");
                                String curTime = sdf.format(mToday);
                                Date start = sdf.parse(frmTime);
                                Date end = sdf.parse(toTime);
                                Date taken = sdf.parse(aTime);




                                if (taken.before(end)&&taken.after(start)) {
                                    text.setText(aTime);

                                } else {
                                    showMSg("Invalid Time!!!");

                                }
                            } catch (ParseException e) {
                                // Invalid date was entered
                            }







                    }
                }, mHour, mMinute, false);



        timePickerDialog.show();


    }

    public String getcurrentTime(){

        Calendar datetime = Calendar.getInstance();

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        String timeSet = "";

        if (mHour > 12) {
            mHour -= 12;
            timeSet = "PM";
        } else if (mHour == 0) {
            mHour += 12;
            timeSet = "AM";
        } else if (mHour == 12){
            timeSet = "PM";
        }else{
            timeSet = "AM";
        }

        String min = "";
        if (mMinute < 10)
            min = "0" + mMinute ;
        else
            min = String.valueOf(mMinute);


        String hr="";

        if (mHour < 10)
            hr = "0" + mHour ;
        else
            hr = String.valueOf(mHour);




        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hr).append(':')
                .append(min ).append(" ").append(timeSet).toString();




        return  aTime;


    }

    public String getcurrentDate(){
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

      return  (mYear+ "-" + (mMonth + 1) + "-" +mDay);


    }




    public void getTime(final TextView text , final String previousTime){
        // Get Current Time
        Calendar datetime = Calendar.getInstance();

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Log.e("time",mHour+""+mMinute);


                        mHour = hourOfDay;
                        mMinute = minute;

                        String timeSet = "";

                        if (mHour > 12) {
                            mHour -= 12;
                            timeSet = "PM";
                        } else if (mHour == 0) {
                            mHour += 12;
                            timeSet = "AM";
                        } else if (mHour == 12){
                            timeSet = "PM";
                        }else{
                            timeSet = "AM";
                        }

                        String min = "";
                        if (mMinute < 10)
                            min = "0" + mMinute ;
                        else
                            min = String.valueOf(mMinute);


                       String hr="";

                        if (mHour < 10)
                            hr = "0" + mHour ;
                        else
                            hr = String.valueOf(mHour);




                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hr).append(':')
                                .append(min ).append(" ").append(timeSet).toString();




                        if (previousTime.equals("")){
                            text.setText(aTime);

                        }

                        else {

                            try {
                                Date mToday = new Date();

                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH );
                                String curTime = sdf.format(mToday);
                                Date start = sdf.parse(previousTime);
                                Date end = sdf.parse(aTime);



                                if (end.before(start)) {
                                    showMSg("Invalid Time!!!");

                                } else {



                                }
                            } catch (ParseException e) {
                                // Invalid date was entered

                                Log.e("error",e.toString());
                            }


                        }




                    }
                }, mHour, mMinute, false);



        timePickerDialog.show();


    }


    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }
            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }
            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            //updateUI();
        }
    }
    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    protected void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        mGoogleApiClient,
                        mLocationSettingsRequest
                );
        result.setResultCallback(this);
    }

    @Override
    public void onResult(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {

                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                }
                break;
        }
    }
    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler() {
        checkLocationSettings();
    }
    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */
    public void stopUpdatesButtonHandler() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        stopLocationUpdates();
    }
    /**
     * Requests location updates from the FusedLocationApi.
     */


    protected void startLocationUpdates() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION..

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient,
                    mLocationRequest,
                    this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    mRequestingLocationUpdates = true;
                    //setButtonsEnabledState();
                }
            });
        }

        else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    startLocationUpdates();

                } else {
                    Log.e("loc","null");

                     _callback.getLoc(null);
                    showMSg("Location permission denied by user!!!");
                }
                return;
            }




        }
    }


/**
 * Disables both buttons when functionality is disabled due to insuffucient location settings.
 * Otherwise ensures that only one button is enabled at any time. The Start Updates button is
 * enabled if the user is not requesting location updates. The Stop Updates button is enabled
 * if the user is requesting location updates.
 */

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient,
                    this
            ).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    mRequestingLocationUpdates = false;
                    // setButtonsEnabledState();
                }
            });

        }
    }






    @Override
    public void onStart() {
        super.onStart();
     if (mGoogleApiClient==null){

        }else {
    mGoogleApiClient.connect();
     }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (mGoogleApiClient==null){

        }else {
            mGoogleApiClient.disconnect();
        }


    }
    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        if (mCurrentLocation == null) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION..
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                mLastUpdateTime = DateFormat.getTimeInstance().format(new java.util.Date());

            }

            else{

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 3);


            }


        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("failed",connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {


        if (_callback==null) {
            Toast.makeText(this, "Location Interface is null!!! Kindly Try again .", Toast.LENGTH_SHORT).show();
        }
        else{
            _callback.getLoc(location);
        }

        if (loc_tag.equals("continue")) {

        } else {

            stopLocationUpdates();


        }

    }





    // Method for fetch address
    public void calculateLatLongi(String url , final LocationUpd callback) {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response)throws IllegalArgumentException {


                        Log.e("response",response.toString());

                        try {

                            JSONArray results =       response.getJSONArray("results");

                            JSONObject obj = results.getJSONObject(0);

                            JSONObject geometry = obj.getJSONObject("geometry");

                            JSONObject location = geometry.getJSONObject("location");

                            Location loc = new Location("");
                            loc.setLatitude(Double.valueOf(location.getString("lat")));
                            loc.setLongitude(Double.valueOf(location.getString("lng")));

                            callback.getLoc(loc);








                        } catch (JSONException e) {
                            Log.e("JSONException", e.toString());

                            e.printStackTrace();
                        }





                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());


            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        Volley.newRequestQueue(this).add(jsonObjReq);
    }


    public void hitApi(final String url , final LocationUpd callback) {

        String tag_string_req = "login_req";


        final ProgressDialog pDialog = new ProgressDialog(HelperActivity.this);
        pDialog.setMessage("Processing...");
        try {
            pDialog.show();
        }
        catch (WindowManager.BadTokenException e){

        }
        Log.e("url", url);

try {

    String _url = url.replaceAll(" ","%20");

    StringRequest strReq = new StringRequest(Request.Method.GET,
            _url, new Response.Listener<String>() {

        @Override
        public void onResponse(String response)   {


         //   callback.updateValues(response);

            pDialog.dismiss();


        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {

            pDialog.hide();
        }
    });

    Volley.newRequestQueue(this).add(strReq);


}
catch (IndexOutOfBoundsException e1){

}
catch (IllegalArgumentException e){

}


    }










}
