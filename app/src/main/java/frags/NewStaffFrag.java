package frags;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.StaffData;

import static android.app.Activity.RESULT_OK;

/**
 * Created by teknik on 9/28/2017.
 */

public class NewStaffFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.new_staff_admin_btn)
    Switch new_staff_admin_btn;

    @BindView(R.id.new_staff_name_input)
    EditText new_staff_name_input;

    @BindView(R.id.new_staff_last_name_input)
    EditText new_staff_last_name_input;

    @BindView(R.id.new_staff_contact_icn)
    ImageView new_staff_contact_icn;

    @BindView(R.id.new_staff_phone_input)
    EditText new_staff_phone_input;

    @BindView(R.id.new_staff_email_input)
    EditText new_staff_email_input;

    @BindView(R.id.new_staff_add_input)
    EditText new_staff_add_input;

    @BindView(R.id.staff_name_header_tv)
    TextView staff_name_header_tv;

    private HttpresponseUpd callback;

    //   static final int PICK_CONTACT = 0;

    private String picked_name, picked_number;

    private Snackbar snackbar;

    private Bundle bundle;

    private StaffData staffData;

    // Declare
    static final int PICK_CONTACT = 1;

    private static final String TAG = NewStaffFrag.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 100;
    private Uri uriContact;
    private String contactID;     // contacts unique ID
    private final int REQUEST_CODE = 99;
    private String staff_id;
    private String post_tag;
    private Sharedpreferences mPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_staff_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();
        callback = this;
        staff_id = bundle.getString("staff_id");
        mPref=Sharedpreferences.getUserDataObj(getActivity());

        if (staff_id.equalsIgnoreCase("") || staff_id == null) {

            try {
                //hit api
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "staffPersonalInfo")
                        .appendQueryParameter("staff_id", /*LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id()*/
                                "");

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "staffPersonalInfo";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                //hit api
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "staffPersonalInfo")
                        .appendQueryParameter("staff_id", staff_id);

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "staffPersonalInfo";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

      /*  if (bundle.getString("staff_id").equals("createstaff")) {

            staffData = LandingActivity.staff_data_array.get(bundle.getInt("pos"));
            if (!staffData.getFirst_name().equals("null")) {
                new_staff_name_input.setText(staffData.getFirst_name());
            }

            if (!staffData.getLast_name().equals("null")) {
                new_staff_last_name_input.setText(staffData.getLast_name());
            }

            if (!staffData.getPhone().equals("null")) {
                new_staff_phone_input.setText(staffData.getPhone());
            }

            if (!staffData.getEmail().equals("null")) {
                new_staff_email_input.setText(staffData.getEmail());
            }

            if (!staffData.getAddress().equals("null")) {
                new_staff_add_input.setText(staffData.getAddress());
            }

            if (staffData.getAdmin_flag().equals("Yes")) {
                new_staff_admin_btn.setChecked(true);
            }

        } else if (bundle.getString("src").equalsIgnoreCase("newstaff")) {
            Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
        }*/
        return Mroot;
    }

    @OnClick(R.id.new_staff_back_btn)
    void backGo() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.new_staff_next_btn)
    void nextGo() {
        InputMethodManager inputManager = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View currentFocusedView = getActivity().getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {

        }

        if (new_staff_name_input.getText().toString().equals("") || new_staff_name_input.getText().toString().equals(null) || new_staff_name_input.getText().toString() == null) {
            new_staff_name_input.setError("Enter Staff First Name ");
          /*  snackbar = Snackbar.make(Mroot, "Staff first name can't Blank", Snackbar.LENGTH_LONG);
            snackbar.show();
      */
        } else if (new_staff_last_name_input.getText().toString().equals("") || new_staff_last_name_input.getText().toString().equals(null) || new_staff_last_name_input.getText().toString() == null) {
            new_staff_last_name_input.setError("Enter Staff Last Name");
          /*  snackbar = Snackbar.make(Mroot, "Staff last name can't Blank", Snackbar.LENGTH_LONG);
            snackbar.show();
      */
        } else if (new_staff_phone_input.getText().toString().equals("") || new_staff_phone_input.getText().toString().equals(null) || new_staff_phone_input.getText().toString() == null) {
            new_staff_phone_input.setError("Enter Staff Phone Number");
          /*  snackbar = Snackbar.make(Mroot, "Staff Phone number can't Blank", Snackbar.LENGTH_LONG);
            snackbar.show();
      */
        } else {
            String status;
            if (new_staff_admin_btn.isChecked()) {
                status = "Yes";
            } else {
                status = "No";
            }

            try {
                //hit api
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "editstaffPersonalInfo")
                        .appendQueryParameter("staff_id", staff_id)
                        .appendQueryParameter("address_id", mPref.getAddressId())
                        .appendQueryParameter("first_name", new_staff_name_input.getText().toString())
                        .appendQueryParameter("last_name", new_staff_last_name_input.getText().toString())
                        .appendQueryParameter("phone", new_staff_phone_input.getText().toString())
                        .appendQueryParameter("address", new_staff_add_input.getText().toString())
                        .appendQueryParameter("admin_status", status);

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(getActivity())) {
                    post_tag = "editstaffPersonalInfo";
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                } else {
                    snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //save related data
          /*  LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFirst_name(new_staff_name_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setLast_name(new_staff_last_name_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAddress(new_staff_add_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setPhone(new_staff_phone_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setEmail(new_staff_email_input.getText().toString());
*/

        }


    }

    @OnClick(R.id.new_staff_contact_icn)
    void pickContacts() {

        try {
            // using native contacts selection
            // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
            getActivity().startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {


            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    Log.e("NSF", "" + grantResults);
                    startActivityForResult(intent, PICK_CONTACT);
                } else {
                    Log.e("denied", "denied");
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                    Log.e("NSF", "Hi" + grantResults);
                    Snackbar snackbar = Snackbar.make(Mroot, "user denied Read Contact permission!!!", Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
                return;
            }
        }
    }


    // pick contact
    //code


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();

        }

    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getActivity().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getActivity().getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

    }

    @Override
    public void getResponse(String response) {

        Log.e("res", response);
        if (response.contains("Error :")) {

            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else if (post_tag.equalsIgnoreCase("staffPersonalInfo")) {
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                String first_name = main_obj.getString("first_name");
                String last_name = main_obj.getString("last_name");
                String email = main_obj.getString("email");
                String phone = main_obj.getString("phone");
                String address = main_obj.getString("address");
                String admin_status = main_obj.getString("admin_status");
                String message = main_obj.getString("message");

                new_staff_name_input.setText(first_name);
                new_staff_last_name_input.setText(last_name);
                new_staff_phone_input.setText(phone);
                new_staff_email_input.setText(email);
                new_staff_add_input.setText(address);
                staff_name_header_tv.setText(first_name + "" + last_name);
                /*new_staff_m.setText(address);*/
                if (admin_status.equalsIgnoreCase("Yes")) {
                    new_staff_admin_btn.setChecked(true);
                } else {
                    new_staff_admin_btn.setChecked(false);
                }

                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (post_tag.equalsIgnoreCase("editstaffPersonalInfo")) {
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                String message = main_obj.getString("message");
                String staff_id= main_obj.getString("staff_id");

                Bundle _bundle = new Bundle();
                _bundle.putString("staff_id",staff_id);
                //  _bundle.putString("src", bundle.getString("src"));
         /*   _bundle.putInt("pos", bundle.getInt("pos"));
            _bundle.putInt("create_pos", bundle.getInt("create_pos"));
*/
                // start new page
                replaceFrag(new /*NewStaffInfoFrag()*/NewStaffWorkingHourFrag(), _bundle, NewStaffFrag.class.getName());
                Toast.makeText(getActivity(), "" + message, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}