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

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import models.StaffData;

import static android.app.Activity.RESULT_OK;

/**
 * Created by teknik on 9/28/2017.
 */

public class NewStaffFrag extends HelperFrags {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_staff_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();


        try {
            if (bundle.getString("src").equals("createstaff")) {

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

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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
        }else {

        }

        if (new_staff_name_input.getText().toString().equals("") || new_staff_name_input.getText().toString().equals(null) || new_staff_name_input.getText().toString()==null) {
           new_staff_name_input.setError("Enter Staff First Name ");
          /*  snackbar = Snackbar.make(Mroot, "Staff first name can't Blank", Snackbar.LENGTH_LONG);
            snackbar.show();
      */  } else if (new_staff_last_name_input.getText().toString().equals("") || new_staff_last_name_input.getText().toString().equals(null) || new_staff_last_name_input.getText().toString()==null) {
            new_staff_last_name_input.setError("Enter Staff Last Name");
          /*  snackbar = Snackbar.make(Mroot, "Staff last name can't Blank", Snackbar.LENGTH_LONG);
            snackbar.show();
      */  } else if (new_staff_phone_input.getText().toString().equals("") || new_staff_phone_input.getText().toString().equals(null) || new_staff_phone_input.getText().toString()== null) {
            new_staff_phone_input.setError("Enter Staff Phone Number");
          /*  snackbar = Snackbar.make(Mroot, "Staff Phone number can't Blank", Snackbar.LENGTH_LONG);
            snackbar.show();
      */  } else {
            //save related data
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setFirst_name(new_staff_name_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setLast_name(new_staff_last_name_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAddress(new_staff_add_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setPhone(new_staff_phone_input.getText().toString());
            LandingActivity.staff_data_array.get(bundle.getInt("pos")).setEmail(new_staff_email_input.getText().toString());

            if (new_staff_admin_btn.isChecked())
                LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAdmin_flag("Yes");
            else
                LandingActivity.staff_data_array.get(bundle.getInt("pos")).setAdmin_flag("No");

            Bundle _bundle = new Bundle();
            _bundle.putString("src", bundle.getString("src"));
            _bundle.putInt("pos", bundle.getInt("pos"));
            _bundle.putInt("create_pos", bundle.getInt("create_pos"));

            // start new page
            replaceFrag(new NewStaffInfoFrag(), _bundle, NewStaffFrag.class.getName());
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
}