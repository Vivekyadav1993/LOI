package helper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import atw.lifeoninternet.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by teknik on 9/25/2017.
 */

public class HelperFrags extends Fragment {


    private Calendar c;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;


    private static final int FILE_SELECT_CODE = 3;

    private static final int REQUEST_CAMERA = 0, SELECT_FILE = 111;

    private int MY_PERMISSIONS_REQUEST_CAMERA = 3;

    private int MY_PERMISSIONS_REQUEST_SELECTFILE = 4;

    private Bitmap select_pic;

    private String pic_path = "", song_path = "";

    private Uri song_uri;

    private ImageView upload_pic_holder;

    public void addFrag(Fragment frag, Bundle bundle) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frag.setArguments(bundle);
        fragmentTransaction.add(R.id.parentcontainer, frag).commit();

    }

    public void replaceFrag(Fragment frag, Bundle bundle, String backclass) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        frag.setArguments(bundle);
        if (backclass.equals(""))
            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(null).commit();
        else
            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(backclass).commit();

    }

    public void initializeSharedData() {

        //initialize share preference
        AppConstants.app_data = getActivity().getSharedPreferences("AppData", getActivity().MODE_PRIVATE);

    }


    public void showMSg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void getDate(final TextView text) {
        // Get Current Date
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String oldFormat = "yyyy-MM-dd";
                        String newFormat = "dd-MMM-yy";

                        String formatedDate = "";
                        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(oldFormat);
                        Date startDate = null, currentdate = null;

                        try {

                            startDate = dateFormat.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            currentdate = dateFormat.parse(mYear + "-" + (mMonth + 1) + "-" + mDay);


                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat(newFormat);
                        formatedDate = timeFormat.format(startDate);


                        // text.setText(formatedDate);
                        text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    public void getDateWithcallback(final TextView text, final HttpresponseUpd callback) {
        // Get Current Date
        c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String oldFormat = "yyyy-MM-dd";
                        String newFormat = "dd-MMM-yy";

                        String formatedDate = "";
                        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(oldFormat);
                        Date startDate = null, currentdate = null;

                        try {

                            startDate = dateFormat.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            currentdate = dateFormat.parse(mYear + "-" + (mMonth + 1) + "-" + mDay);


                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat(newFormat);
                        formatedDate = timeFormat.format(startDate);

                        // text.setText(formatedDate);
                        text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        callback.getResponse("Date");

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void getBreakTime(final TextView text, final String frmTime, final String toTime) {

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
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

                        try {
                            Date mToday = new Date();

                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("hh:mm aa");
                            String curTime = sdf.format(mToday);
                            Date start = sdf.parse(frmTime);
                            Date end = sdf.parse(toTime);
                            Date taken = sdf.parse(aTime);

                            if (taken.before(end) && taken.after(start)) {
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

    public String getcurrentTime() {

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


        return aTime;


    }

    public String getcurrentDate() {
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        return (mYear + "-" + (mMonth + 1) + "-" + mDay);


    }

    public void getTime(final TextView text, final String previousTime) {
        // Get Current Time
        Calendar datetime = Calendar.getInstance();

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
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


                                        showMSg("Invalid Time!!!");

                                    } else {
                                        text.setText(aTime);
                                    }
                                } else {
                                    showMSg("Invalid Time!!!");

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


    public void getTimeback(final TextView text, final String previousTime) {
        // Get Current Time
        Calendar datetime = Calendar.getInstance();

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
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


                                        showMSg("Invalid Time!!!");

                                    } else {


                                        text.setText(aTime);


                                    }


                                } else {
                                    showMSg("Invalid Time!!!");

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


    public void hitApi(final String url, final HttpresponseUpd callback) {

        String tag_string_req = "login_req";


        try {

            String _url = url.replaceAll(" ", "%20");

            StringRequest strReq = new StringRequest(Request.Method.GET,
                    _url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {


                    callback.getResponse(response);


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.getResponse("error");

                }
            });

            Volley.newRequestQueue(getActivity()).add(strReq);


        } catch (IndexOutOfBoundsException e1) {

        } catch (IllegalArgumentException e) {

        }


    }


    public void getTimestaff(final TextView text, final String start_time, final String previousTime) {
        // Get Current Time
        Calendar datetime = Calendar.getInstance();

        c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
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
                                Date start = sdf.parse(start_time);
                                Date to = sdf.parse(previousTime);
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


                                long mills = to.getTime() - end.getTime();
                                int hours = (int) (mills / (1000 * 60 * 60));
                                int mins = (int) mills % (1000 * 60 * 60);

                                String diff = hours + ":" + mins;

                                Log.e("diff", diff);


                                if (hours == 24 || hours < 24 || hours < -24) {

                                    if (end.before(start) || end.after(to)) {


                                        showMSg("Invalid Time!!!");

                                    } else {


                                        text.setText(aTime);


                                    }


                                } else {
                                    showMSg("Invalid Time!!!");

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


    public void checkPermission(ImageView upload_pic_holder) {

        this.upload_pic_holder = upload_pic_holder;


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_SELECTFILE);

            Log.e("okk", "0");

             /*   // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.


                    selectImage();


                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_SELECTFILE);

                    // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }*/
        } else {
            selectImage();
            Log.e("okk", "1");

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay! Do the
            // contacts-related task you need to do.


            selectImage();

        } else {

            // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
        return;

    }


    // method to invoke chooser dialog
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Method for get path of selected audio file
    public static String getSongPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    // method to invoke chooser dialog for image
    private void selectImage() {
        final CharSequence[] items = {"Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Choose from Library")) {
                    Log.e("okk", "3.01");
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a pic for Staff picture"),
                            SELECT_FILE);

                    Log.e("okk", "3.01");
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    // override method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data);
            Log.e("okk", "3.0");
        } else {
            Log.e("okk", "3");
        }
    }


    private void onCaptureImageResult(Intent data) {
        select_pic = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        select_pic.compress(Bitmap.CompressFormat.JPEG, 90, bytes);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        pic_path = destination.toString();

        // current_pic.setTag(pic_path.size()-1);
        upload_pic_holder.setImageBitmap(select_pic);


        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();

        Log.e("data", data.toString() + "uuuu");

        String wholeID = null;
        String selectedImagePath = "";

        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null,
                null);


        if (cursor == null) { // Source is Dropbox or other similar local file path
            pic_path = selectedImageUri.getPath();

            Log.e("cursor", "-null---");
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            pic_path = cursor.getString(idx);
            cursor.close();
        }

       /* int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        pic_path = cursor.getString(column_index);*/

        Log.e("picpath", pic_path + "----");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pic_path, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        select_pic = BitmapFactory.decodeFile(pic_path, options);


        Log.e("pic", select_pic.toString() + "juhgjgt");
        upload_pic_holder.setImageBitmap(select_pic);

    }


    public ArrayList<String> getListDate(String str_date, String end_date) {

        List<Date> dates = new ArrayList<Date>();


        DateFormat formatter;

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        try {
            startDate = (Date) formatter.parse(str_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = (Date) formatter.parse(end_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60; // 1 hour in millis
        long endTime = endDate.getTime(); // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        for (int i = 0; i < dates.size(); i++) {
            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);

        }



       /* //get day name
        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = date_format.parse("2008-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        ArrayList<String> day_name = new ArrayList<>();

        for (int i = 0; i < dates.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dates.get(i));// Note that Month value is 0-based. e.g., 0 for January.

            int reslut = calendar.get(Calendar.DAY_OF_WEEK);

            switch (reslut) {

                case Calendar.MONDAY:
                    day_name.add("Mon");
                    break;

                case Calendar.TUESDAY:
                    day_name.add("Tue");
                    break;

                case Calendar.WEDNESDAY:
                    day_name.add("Wed");
                    break;
                case Calendar.THURSDAY:
                    day_name.add("Thu");
                    break;
                case Calendar.FRIDAY:
                    day_name.add("Fri");
                    break;

                case Calendar.SATURDAY:
                    day_name.add("Sat");
                    break;
                case Calendar.SUNDAY:
                    day_name.add("Sun");
                    break;


            }
        }


        return day_name;
    }


}
