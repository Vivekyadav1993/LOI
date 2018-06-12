package frags;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import adapters.MyAdsAddressAdapter;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.MyAdsAddress;

import static android.app.Activity.RESULT_OK;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpLoadCompanyDetailsFrag extends HelperFrags /*implements HttpresponseUpd */{

    @BindView(R.id.company_details_upload_company_name_et)
    public EditText mCname;
    @BindView(R.id.company_details_upload_company_email_et)
    public EditText mEmail;

    @BindView(R.id.company_details_upload_company_key_et)
    public EditText mKey;

    @BindView(R.id.company_details_upload_company_description_et)
    public EditText mDescription;

    @BindView(R.id.company_details_profile_iv)
    public ImageView mImage;

    int PICK_IMAGE_REQUEST = 101;
    Bitmap bitmap;

    private HttpresponseUpd callback;
    private Snackbar snackbar;
    private Uri photoPath;
    private String profileUrl;
    private String business_id;
    private Uri filePath;

    private String imagepath = null;
    private String imageString;

    private Sharedpreferences mPref;

    public UpLoadCompanyDetailsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_load_company_details, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        mPref = Sharedpreferences.getUserDataObj(getActivity());
      //  callback = this;

        /*business_id = mPref.getBusnessId();*/


    }

    @OnClick({R.id.upload_company_details_btn, R.id.company_details_profile_iv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.upload_company_details_btn:
                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                // uploadFun();
                break;
            case R.id.company_details_profile_iv:

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
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                Toast.makeText(getActivity(), "" + bitmap, Toast.LENGTH_SHORT).show();
                mImage.setImageBitmap(bitmap);
                Log.d("ULCDF", "bitmap" + bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Picking image
     **/
    //capture and select image from gallery
    private void selectAndCaptureImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(intent, 101);
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    getActivity().startActivityForResult(Intent.createChooser(intent, "Select picture"), 100);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    //handling the image chooser activity result
    private void uploadFun() {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 4, baos);
        byte[] imageBytes = baos.toByteArray();
        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.d("ULCD", "photoPath" + imageString);

      /*  if (photoPath != null) {

            Utils.showProgress(getActivity());
            //  StorageReference reff = mStorageRef.child("users").child(mPrefs.getUserId()).child(photoPath.getLastPathSegment());
            String profileUrl = photoPath.getLastPathSegment();
            Log.d("ULCD", "profileUrl" + profileUrl);

        }
*/
      /*  Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "updatebusinessDetails")
                .appendQueryParameter("business_id", mPref.getBusnessId())
                .appendQueryParameter("mobile", mCname.getText().toString())
                .appendQueryParameter("email", mEmail.getText().toString())
                .appendQueryParameter("keyword", mKey.getText().toString())
                .appendQueryParameter("description", mDescription.getText().toString())
                .appendQueryParameter("uploaded_file", imageString);

        String myUrl = builder.build().toString();
        Log.e("url", myUrl);

        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }*/

    }

/*
    @Override
    public void getResponse(String response) {
        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(getView(), "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            try {
                JSONObject main_obj = new JSONObject(response);

                Toast.makeText(getActivity(), "" + main_obj.getString("message"), Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }*/
}
