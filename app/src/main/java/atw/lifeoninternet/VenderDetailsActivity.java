package atw.lifeoninternet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class VenderDetailsActivity extends AppCompatActivity implements HttpresponseUpd {


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

    int PICK_IMAGE_REQUEST = 101;
    Bitmap bitmap;

    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private Uri photoPath;
    private String profileUrl;
    private String business_id;
    private Uri filePath;
    private String imageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake);
        ButterKnife.bind(this);
        initilizerView();
    }

    private void initilizerView() {

        mPref = Sharedpreferences.getUserDataObj(this);

        callback = this;
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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
            Uri.Builder builder = new Uri.Builder();
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
                AppUtils.getStringData(builder.build().toString(), this, callback);
            else {
         /*   snackbar = Snackbar.make(this, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();*/
                Toast.makeText(this, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void getResponse(String response) {

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

    }
}
