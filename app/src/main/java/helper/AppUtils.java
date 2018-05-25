package helper;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/12/2017.
 */

public class AppUtils {

    private static int mHour, mMinute, mYear, mMonth, mDay;

    public static IOSDialog dialog;


    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void getStringData(String url, Context c, final HttpresponseUpd updateCallback) {
 /*String Request*/

        dialog = new IOSDialog.Builder(c)
                .setTitle("Progressing...")
                .setTitleColorRes(R.color.colorAccent)
                .build();

        try {
            dialog.show();

        } catch (IllegalArgumentException e) {

        }


        RequestQueue requestQueue = Volley.newRequestQueue(c);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            dialog.dismiss();
                        } catch (IllegalArgumentException e) {

                        }

                        updateCallback.getResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {

                            dialog.dismiss();
                        } catch (IllegalArgumentException e) {

                        }
                        updateCallback.getResponse("Error : " + error.getMessage());
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //add request to queue
        requestQueue.add(stringRequest);
    }

    // Method for fetch address
    public static void getAdd(String url, Context c, final HttpresponseUpd callback) {

        RequestQueue requestQueue = Volley.newRequestQueue(c);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, new JSONObject(),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) throws IllegalArgumentException {


                        try {

                            JSONArray results = response.getJSONArray("results");

                            JSONObject obj = results.getJSONObject(0);


                            callback.getResponse(obj.getString("formatted_address"));


                        } catch (JSONException e) {

                            callback.getResponse("Parsing error occurred!! Try again");
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
                callback.getResponse("Error :" + error.getMessage());


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

        //add request to queue
        requestQueue.add(jsonObjReq);
    }


    /*public static void getTime(final Context context ,final TextView text , final String fromTime , final String toTime){

        // Get Current Time
        Calendar c = Calendar.getInstance();



        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
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




                        try {

                            Date time1 = new SimpleDateFormat("hh:mm a").parse(fromTime);
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.setTime(time1);


                            Date time2 = new SimpleDateFormat("hh:mm a").parse(toTime);
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTime(time2);
                            calendar2.add(Calendar.DATE, 1);


                            Date d = new SimpleDateFormat("hh:mm a").parse(aTime);
                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.setTime(d);
                            calendar3.add(Calendar.DATE, 1);

                            Date x = calendar3.getTime();
                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                //checkes whether the current time is between 14:49:00 and 20:11:13.
                                Log.e("lies","okk");

                                text.setText(aTime);
                            }
                            else{
                                Toast.makeText(context, "Invalid Time!!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            Log.e("error",e.getMessage());
                            text.setText(aTime);

                        }





                    }
                }, mHour, mMinute, false);



        timePickerDialog.show();


    }
*/

    public void getTime(final Context context, final TextView text, final String previousTime) {
        // Get Current Time


        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Log.e("time", mHour + "" + mMinute);


                        mHour = hourOfDay;
                        mMinute = minute;

                        String timeSet = "";

                        if (mHour > 12) {
                            mHour -= 12;
                            timeSet = "PM";
                        } else if (mHour == 0) {
                            mHour += 12;
                            timeSet = "AM";
                        } else if (mHour == 12) {
                            timeSet = "PM";
                        } else {
                            timeSet = "AM";
                        }

                        String min = "";
                        if (mMinute < 10)
                            min = "0" + mMinute;
                        else
                            min = String.valueOf(mMinute);


                        String hr = "";

                        if (mHour < 10)
                            hr = "0" + mHour;
                        else
                            hr = String.valueOf(mHour);


                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hr).append(':')
                                .append(min).append(" ").append(timeSet).toString();


                        if (previousTime.equals("")) {
                            text.setText(aTime);

                        } else {

                            // text.setText(aTime);

                            try {
                                Date mToday = new Date();

                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                String curTime = sdf.format(mToday);
                                Date start = sdf.parse(previousTime);
                                Date end = sdf.parse(aTime);




/* else if (LandingActivity.business_data.getAdderess_data().get(LandingActivity.business_array_pos).getDate_start()
                                        .equals(LandingActivity.business_data.getAdderess_data().get(LandingActivity.business_array_pos).getDate_end())){
                                    long difference = end.getTime() - start.getTime();
                                    int  days = (int) (difference / (1000*60*60*24));
                                    int  hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                                    int  mi = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                                    int  hour = (hours < 0 ? -hours : hours);
                                    Log.e("hour",hour+"");
                                    if (hour>=12){
                                        showMSg("Invalid Time!!!");
                                    }
                                    else if (start.before(end)){
                                        showMSg("Invalid Time!!!");
                                    }
                                    else{
                                        text.setText(aTime);
                                    }

                                }*//*


                              */


                                long mills = start.getTime() - end.getTime();
                                int hours = (int) (mills / (1000 * 60 * 60));
                                int mins = (int) mills % (1000 * 60 * 60);

                                String diff = hours + ":" + mins;

                                Log.e("diff", diff);


                                if (hours == 24 || hours < 24) {

                                    if (start.before(end)) {


                                        Toast.makeText(context, "Invalid Time!!!", Toast.LENGTH_SHORT).show();


                                    } else {


                                        text.setText(aTime);


                                    }


                                } else {
                                    Toast.makeText(context, "Invalid Time!!!", Toast.LENGTH_SHORT).show();

                                }


                            } catch (ParseException e) {
                                // Invalid date was entered

                                Log.e("error", e.toString());
                            }


                        }


                    }
                }, mHour, mMinute, false);


        timePickerDialog.show();


    }


    public static void getDate(final TextView text, final Context context, final String from_date, final String to_date) {
        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String oldFormat = "yyyy-MM-dd";

                        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(oldFormat);
                        Date startDate = null, end_date = null, currentdate = null;

                        try {

                            startDate = dateFormat.parse(from_date);
                            end_date = dateFormat.parse(to_date);
                            currentdate = dateFormat.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }


                        if (currentdate.after(startDate) && currentdate.before(end_date) || currentdate.equals(startDate) || currentdate.equals(end_date)) {
                            text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else {
                            Toast.makeText(context, "Invalid date!!", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        datePickerDialog.show();


    }


    public static void getDatewithCallback(final TextView text, final Context context, final String from_date,
                                           final String to_date, final HttpresponseUpd callback) {
        // Get Current Date
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String oldFormat = "yyyy-MM-dd";

                        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(oldFormat);
                        Date startDate = null, end_date = null, currentdate = null;

                        try {

                            startDate = dateFormat.parse(from_date);
                            end_date = dateFormat.parse(to_date);
                            currentdate = dateFormat.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }


                        if (currentdate.after(startDate) && currentdate.before(end_date) || currentdate.equals(startDate) || currentdate.equals(end_date)) {
                            text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            callback.getResponse("Date");
                        } else {
                            Toast.makeText(context, "Invalid date!!", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


        datePickerDialog.show();


    }


    public void getTimeback(final Context context, final TextView text, final String previousTime) {
        // Get Current Time

        Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Log.e("time", mHour + "" + mMinute);


                        mHour = hourOfDay;
                        mMinute = minute;

                        String timeSet = "";

                        if (mHour > 12) {
                            mHour -= 12;
                            timeSet = "PM";
                        } else if (mHour == 0) {
                            mHour += 12;
                            timeSet = "AM";
                        } else if (mHour == 12) {
                            timeSet = "PM";
                        } else {
                            timeSet = "AM";
                        }

                        String min = "";
                        if (mMinute < 10)
                            min = "0" + mMinute;
                        else
                            min = String.valueOf(mMinute);


                        String hr = "";

                        if (mHour < 10)
                            hr = "0" + mHour;
                        else
                            hr = String.valueOf(mHour);


                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hr).append(':')
                                .append(min).append(" ").append(timeSet).toString();


                        if (previousTime.equals("")) {
                            text.setText(aTime);

                        } else {

                            // text.setText(aTime);

                            try {
                                Date mToday = new Date();

                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                String curTime = sdf.format(mToday);
                                Date start = sdf.parse(previousTime);
                                Date end = sdf.parse(aTime);




/* else if (LandingActivity.business_data.getAdderess_data().get(LandingActivity.business_array_pos).getDate_start()
                                        .equals(LandingActivity.business_data.getAdderess_data().get(LandingActivity.business_array_pos).getDate_end())){
                                    long difference = end.getTime() - start.getTime();
                                    int  days = (int) (difference / (1000*60*60*24));
                                    int  hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                                    int  mi = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                                    int  hour = (hours < 0 ? -hours : hours);
                                    Log.e("hour",hour+"");
                                    if (hour>=12){
                                        showMSg("Invalid Time!!!");
                                    }
                                    else if (start.before(end)){
                                        showMSg("Invalid Time!!!");
                                    }
                                    else{
                                        text.setText(aTime);
                                    }

                                }*//*


                              */


                                long mills = start.getTime() - end.getTime();
                                int hours = (int) (mills / (1000 * 60 * 60));
                                int mins = (int) mills % (1000 * 60 * 60);

                                String diff = hours + ":" + mins;

                                Log.e("diff", diff);


                                if (hours == 24 || hours < 24 || hours < -24) {

                                    if (end.before(start)) {


                                        Toast.makeText(context, "Invalid Time!!!", Toast.LENGTH_SHORT).show();


                                    } else {


                                        text.setText(aTime);


                                    }


                                } else {


                                    Toast.makeText(context, "Invalid Time!!!", Toast.LENGTH_SHORT).show();


                                }


                            } catch (ParseException e) {
                                // Invalid date was entered

                                Log.e("error", e.toString());
                            }


                        }


                    }
                }, mHour, mMinute, false);


        timePickerDialog.show();


    }


}
