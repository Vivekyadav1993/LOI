
package r2stech.lifeoninternet.utils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import r2stech.lifeoninternet.R;


public class Utils {
    /**
     * For Language
     */

    public static String appCode = "";


    public static Context context;
    public static String authToken = "";
    public static int CHOOSE_DATA_FROM_GIVEN_LIST = 222;
    public static String dataChoosed = "dataChoosed";
    public static String dataType = "dataType";


    /**
     * Data Type
     **/
    public static String dataCompany = "Company";
    public static String dataIndustry = "Industry";
    public static String dataDesignation = "Designation";
    public static String dataSkills = "Skills";
    public static String dataLocation = "Location";
    public static String dataCompanyBenifits = "Company Benifits";
    public static int MAX_IMAGES_SELECTABLE = 5;

  /*  public static void chooseDataFromList(Activity mContext, String dataType, String pageTitle,
                                          String dataTitle, ArrayList<String> dataList) {

        Intent intent = new Intent(mContext, AutoSearchActivity.class);
        intent.putExtra("dataType", dataType);
        intent.putExtra("pageTitle", pageTitle);
        intent.putExtra("dataTitle", dataTitle);
        intent.putStringArrayListExtra("dataList", dataList);
        startActivityForResult(intent, Utils.CHOOSE_DATA_FROM_GIVEN_LIST);// Activity is started with requestCode 2


    }
*/

    /**
     * Method Names
     */
    public static String methodGet = "GET";
    public static String methodDelete = "DELETE";
    public static String methodPost = "POST";
    public static String methodPut = "PUT";
    public static String methodMultipart = "MULTIPART";


    public interface dialogInterface {
        public void dialogClick();
    }


    /* add tail 1 screen params data*/

    public static HashMap<String, String> params = new HashMap<String, String>();


    //public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 987;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 988;
    public static final int MY_PERMISSIONS_REQUEST_READ_PHONE = 456;


    public static boolean editable = false;

    public static IOSDialog progressSimple;
    //  public static SweetAlertDialog progress;
    public static Bitmap bitmap = null;


    public static String refreshedFirebaseTokenValue = "";

    public static void showProgress(Context context) {
//        progress = new ProgressDialog(context);
//        progress.setMessage("Please Wait");
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setCancelable(false);
//
//        // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//        Drawable drawable = new ProgressBar(context).getIndeterminateDrawable().mutate();
//        drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent),
//                PorterDuff.Mode.SRC_IN);
//        progress.setIndeterminateDrawable(drawable);
//        //  }
//
//        progress.show();
        try {
            progressSimple = new IOSDialog.Builder(context)
                    .setTitle("Progressing...")
                    .setTitleColorRes(R.color.colorAccent)
                    .build();
            progressSimple.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String stringBuilder() {
      /*  Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php");
    */    String baseUrl="new_service";
        // String baseUrl = "production";

        return baseUrl;

    }

    public static void showProgress(Context context, String message) {

      /*      progressSimple = new ProgressDialog(context);
            progressSimple.setMessage(message);
            progressSimple.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressSimple.setCancelable(true);
            progressSimple.show();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static void stopProgress(Context context) {
        try {
            progressSimple.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * display Toast message
     */

    public static void showToast(Context context, String str) {
        Toast.makeText(context, "" + str, Toast.LENGTH_SHORT).show();

    }

    public static void makeSnackBar(View view, String message) {
        Snackbar customSnackbar =
                Snackbar.make(view, "" + message, Snackbar.LENGTH_LONG);
        customSnackbar.show();
    }

    /*
     *Check Internet availability
     */
    public static boolean isInternetAvailable(Context context) {
        boolean isInternetAvailable = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager
                    .getActiveNetworkInfo();
            isInternetAvailable = networkInfo != null && networkInfo.isAvailable()
                    && networkInfo.isConnectedOrConnecting();
            if (isInternetAvailable)
                Utils.showToast(context, "Please connect to internet");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isInternetAvailable;
    }

    public static boolean isStatusTrue(JSONObject jsonObject) {
        Boolean aStatus = false;
        if (jsonObject != null) {
            try {
                if (jsonObject.has(/*AppConstants.TAGNAME.STATUS.getValue()*/"status")) {
                    aStatus = jsonObject.getBoolean(/*AppConstants.TAGNAME.STATUS.getValue()
                    */"status");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aStatus;
    }

    /**
     * Check particular node is array.
     *
     * @param jsonObject
     * @return true if particular node is Array.
     */
    public static boolean isJsonArray(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONArray(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is object.
     *
     * @param jsonObject
     * @return true if particular node is Object.
     */
    public static boolean isJsonObject(JSONObject jsonObject, String key) {

        if (!jsonObject.isNull(key)) {

            try {
                jsonObject.getJSONObject(key);
                return true;
            } catch (JSONException e) {
                return false;
            }

        }
        return false;
    }

    /**
     * Check particular node is having particular key.
     *
     * @param jsonObject
     * @return true if particular node is having key.
     */
    public static boolean isJsonKeyAvailable(JSONObject jsonObject, String key) {

        return jsonObject.has(key);

    }

    public static void putStringinPrefs(Context mContext, String mKey,
                                        String mVal) {
        SharedPreferences.Editor prefsEditorr = PreferenceManager
                .getDefaultSharedPreferences(mContext).edit();
        try {
            prefsEditorr.putString(mKey, mVal.toString());
            prefsEditorr.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Create image and save in file system.
     */
    public static File createImage(Context context, int height, int width,
                                   View view, String fileName) {
        Bitmap bitmapCategory = getBitmapFromView(view, height, width);
        return createFile(context, bitmapCategory, fileName);
    }

    /*
     * save bitmap image in phone memory
     */
    public static File createFile(Context context, Bitmap bitmap,
                                  String fileName) {

        File externalStorage = Environment.getExternalStorageDirectory();
        String sdcardPath = externalStorage.getAbsolutePath();
        File reportImageFile = new File(sdcardPath + "/YourFolderName"
                + fileName + ".jpg");
        try {
            if (reportImageFile.isFile()) {
                reportImageFile.delete();
            }
            if (reportImageFile.createNewFile()) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileOutputStream fo = new FileOutputStream(reportImageFile);
                fo.write(bytes.toByteArray());
                bytes.close();
                fo.close();
                return reportImageFile;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Unable to create Image.Try again",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    /*
     * Take view screen shots
     */
    public static Bitmap getBitmapFromView(View view, int totalHeight,
                                           int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);

        view.measure(
                MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY));
        view.layout(0, 0, totalWidth, totalHeight);
        view.draw(canvas);
        return returnedBitmap;
    }


    /*
     * This function copy InputStream bytes to OutputStream bytes
     *
     * @param InputStream
     *
     * @param OutputStream
     */
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    /**
     * this method is used to navigate the user to the native sharing apps on
     * the device so that user can shared the data on the various social sites
     * success : opens the sharing apps list on the device
     */

    public static void navigateToSharedScreen(Context context, String title,
                                              String shareText, String link, Uri url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
//		intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + shareText + " " + link);
        intent.putExtra(Intent.EXTRA_STREAM, url);
        context.startActivity(Intent.createChooser(intent, "Share"));
    }

    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(((Activity) context)
                        .getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert dp to px and vice-versa
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * This method is used to convert the image into blurred bitmap image
     */
    public static Bitmap getBlurImage(Context context, Bitmap image) {
       /* Bitmap Icon = BitmapFactory.decodeResource(getResources(), R.mipmap.home_info);
        * Bitmap blurredBitmap = Utils.getBlurImage(this, Icon);
        * mLogoImg.setBackgroundDrawable(new BitmapDrawable(getResources(), blurredBitmap));
        */

        float BITMAP_SCALE = 0.25f;
        float BLUR_RADIUS = 9.5f;

        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                RenderScript rs = RenderScript.create(context);
                ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
                Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
                theIntrinsic.setRadius(BLUR_RADIUS);
                theIntrinsic.setInput(tmpIn);
                theIntrinsic.forEach(tmpOut);
                tmpOut.copyTo(outputBitmap);
            }
        } catch (Exception e) {

        }

        return outputBitmap;
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public static Bitmap getBitmap() {

        return bitmap;
    }

    public static void setBitMap(Bitmap bitmap) {

        Utils.bitmap = bitmap;
    }

    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    /*Convert a Drawable object to a Bitmap object*/
    public static Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config
                .ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);
        return mutableBitmap;
    }

    /* convert between real pixel (px) and device indipenden pixel (dp, dip)*/
    private float px2Dp(float px, Context ctx) {
        return px / ctx.getResources().getDisplayMetrics().density;
    }

    private float dp2Px(float dp, Context ctx) {
        return dp * ctx.getResources().getDisplayMetrics().density;
    }

    /*
     * API 23 default component permissions
     */
    public static boolean checkPermission(Context context, String callPhone) {
        int result = ContextCompat.checkSelfPermission(context, callPhone);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkCameraPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest
                        .permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface
                            .OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest
                                            .permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission
                            .CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkDocPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest
                        .permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface
                            .OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest
                                            .permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission
                            .READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*create and set Typeface to a textview*/

    public static void setTypeface(Activity activity, TextView view/*, String type*/) {
        Typeface typeface = null;
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-LightItalic.ttf");

//        if (type.contains("bold")) {
//            typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/GeorgiaRegularfont.ttf");
//        } else if (type.contains("regular")) {
//            typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/AGENCYR.TTF");
//
//        }
        view.setTypeface(typeface);
    }


    /* select image*/

    public static String userChoosenTask;


    public static int REQUEST_CAMERA = 121, SELECT_FILE = 1;
    public static int RESULT_LOAD_IMAGE = 11;


    public static void selectImage(final Activity activity) {
        final CharSequence[] items = {"Take Photo", "Choose from Library","Cancel"};

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(activity);
        builder.setTitle("Upload Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // boolean result = Utils.checkPermission(CameraActivity.this);
                //  boolean resultCamera = Utils.checkCameraPermission(CameraActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (true)
                        cameraIntent(activity);

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (true)
                        galleryIntent(activity);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void galleryIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), RESULT_LOAD_IMAGE);
    }

    public static void cameraIntent(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }

    public static String onCaptureImageResult(Intent data, ImageView mImageView, TextView mTextView) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        //setting image captured through camera
        mImageView.setImageBitmap(thumbnail);
        mTextView.setVisibility(View.GONE);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 60, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

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
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

              /*
               *to encode base64 from byte array use following method
               */

        String base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return base64Image;
    }

    public static String onGalleryImageResult(Intent data, ImageView mImageView, TextView mTextView, Activity contextOfActivity) {
        Uri filePath = data.getData();
        String base64Image = null;
        Bitmap compressedBitmap;
        try {
            //Getting the Bitmap from Gallery
            bitmap = MediaStore.Images.Media.getBitmap(contextOfActivity.getContentResolver(), filePath);


            //Setting the Bitmap to ImageView
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes);

            byte[] byteArray = bytes.toByteArray();
            compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            //setting image picked from gallery
            mImageView.setImageBitmap(compressedBitmap);
            mTextView.setVisibility(View.GONE);
            base64Image = Base64.encodeToString(byteArray, Base64.DEFAULT);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64Image;
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    /**
     * Strict Special Characters entering into the edittext
     */

    public static String blockCharacterSet = "~#^|$%&*!.,₹{}[]()+-_/><?:;";


    public static InputFilter sepcialCharRemovalFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };


    /**
     * Strict Special Characters entering into the edittext (second level)
     */
    public static String blockCharacterSetLowLevel = "~#^|$%*!,₹{}[]()+/><?:;";

    public static InputFilter sepcialCharRemovalFilterLowLevel = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSetLowLevel.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };


    //resize image
    public static String resizeImage(Bitmap b) {
        Random r = new Random();
        int i1 = (r.nextInt(80) + 65);
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Bitmap out = Bitmap.createScaledBitmap(b, 800, 800, false);
        File file = new File(dir, "resize" + i1 + ".png");
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
        } catch (Exception e) {
        }
        return file.getAbsolutePath();
    }

    //get storage permission
    public static boolean isStoragePermissionGranted(Activity context) {
        Log.e("Tag", "per");
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("", "Permission is granted");
                return true;
            } else {
                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("", "Permission is granted");
            return true;
        }
    }


    //for get bitmap from uri
    public static Bitmap getBitmapFromUri(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }

    //replace all img src path to null
    public static String replaceSrcToNull(String inputString) {
        String webViewContentExcludeImage = inputString.replaceAll("<img .*?>", "<Img src=\"\" width=200 height=200>");
        return webViewContentExcludeImage;
    }


    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }




    /* From Create JD Fragments go to CreateJDParentPage*/

    public static void closingThisFrag(FragmentManager mFragmentManager) {
        mFragmentManager.popBackStack("CreateJdParentFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
