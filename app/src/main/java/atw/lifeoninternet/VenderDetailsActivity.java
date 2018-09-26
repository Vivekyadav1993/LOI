package atw.lifeoninternet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HttpresponseUpd;

public class VenderDetailsActivity extends AppCompatActivity /*implements HttpresponseUpd */{

    String imageString;


    @BindView(R.id.company_details_profile_ivvv)
    public ImageView mImage;


    @BindView(R.id.company_details_upload_company_name_et)
    public EditText mCname;
    @BindView(R.id.company_details_upload_company_email_et)
    public EditText mEmail;

    @BindView(R.id.company_details_upload_company_key_et)
    public EditText mKey;

    @BindView(R.id.company_details_upload_company_description_et)
    public EditText mDescription;

    Sharedpreferences mPref;
    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap;

    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private Uri photoPath;
    private String profileUrl;
    private String business_id;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake);
        ButterKnife.bind(this);
        initilizerView();
    }

    private void initilizerView() {

        mPref = Sharedpreferences.getUserDataObj(this);

       // callback = this;
        business_id = mPref.getBusnessId();
    }

    @OnClick({R.id.upload_company_details_upload_tv, R.id.company_details_profile_ivvv, R.id.company_details_upload_back_btn})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.upload_company_details_upload_tv:

                uploadFun();
                break;

            case R.id.company_details_upload_back_btn:
                onBackPressed();
                break;

            case R.id.company_details_profile_ivvv:

                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);

           /*     Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

       */
             /*   if (Utils.isStoragePermissionGranted(getActivity())) {
                    selectAndCaptureImage();
                }*/
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ULCDF", "requestCode" + requestCode);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Toast.makeText(this, "" + bitmap, Toast.LENGTH_SHORT).show();
                mImage.setImageBitmap(bitmap);
                Log.d("ULCDF", "bitmap" + bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadFun() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

      /*  ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
         imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
*/
        //  Log.d("ULCD", "photoPath" + baos);

        if (mCname.getText().toString().equals("")) {
            mCname.setError("Phone Number cannot be blank!!!");

        } else if (mEmail.getText().toString().equals("")) {
            mEmail.setError("Email cannot be blank!!!");

        } else if (mKey.getText().toString().equals("")) {
            mKey.setError("Key cannot be blank!!!");
        } else {

            hitAPI("http://lifeoninternet.com/"+Utils.stringBuilder()+"/api.php?action=updatebusinessDetails&business_id="+mPref.getBusnessId()+
                    "&mobile="+mCname.getText().toString()+"&email="+mEmail.getText().toString()+"&keyword="+mKey.getText().toString()+
                    "&description="+mDescription.getText().toString()+"&uploaded_file="+imageString);
         /*   Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "updatebusinessDetails")
                    .appendQueryParameter("business_id", business_id)
                    .appendQueryParameter("mobile", mCname.getText().toString())
                    .appendQueryParameter("email", mEmail.getText().toString())
                    .appendQueryParameter("keyword", mKey.getText().toString())
                    .appendQueryParameter("description", mDescription.getText().toString())
                    .appendQueryParameter("uploaded_file", imageString);

            String myUrl = builder.build().toString();
            Log.e("url", myUrl);

            if (AppUtils.isNetworkAvailable(this))
                AppUtils.getStringData(builder.build().toString(), VenderDetailsActivity.this, callback);
            else {
         *//*   snackbar = Snackbar.make(this, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();*//*
                Toast.makeText(this, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Toast.LENGTH_SHORT).show();
            }*/
        }

    }

    private void hitAPI(final String url) {

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url.replaceAll(" ", "%20"), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("url", url);
                Log.e("urlres", response);
             /*   try {
                    //parse data and put all to list
                    JSONObject main_obj = new JSONObject(response);
                    JSONArray output_array = main_obj.getJSONArray("results");


                } catch (JSONException e) {
                    Log.e("error", e.getMessage());

                }*/


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String message = "";
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
                //  Utils.stopProgress(getActivity());
            }
        });

        Volley.newRequestQueue(this).add(strReq);


    }

/*    @Override
    public void getResponse(String response) {


       // http://lifeoninternet.com/production/api.php?action=updatebusinessDetails&business_id=74&mobile=5666&email=hjff%40gmail.com&keyword=hdxhs&description=dxch&uploaded_file=
        Log.e("res", response);

        if (response.contains("error")) {
            Toast.makeText(this, response + " occurred!!!", Toast.LENGTH_SHORT).show();
        } else {

            try {
                JSONObject obj = new JSONObject(response);
                String message = obj.getString("message");

                Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }*/
}
