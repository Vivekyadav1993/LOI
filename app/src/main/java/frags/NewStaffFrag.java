package frags;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import models.StaffData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

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

    static final int PICK_CONTACT = 0;

    private String picked_name, picked_number;

    private Snackbar snackbar;

    private  Bundle bundle;

    private StaffData staffData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_staff_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();



        if (bundle.getString("src").equals("createstaff")){

            staffData = LandingActivity.staff_data_array.get(bundle.getInt("pos"));

            if (!staffData.getFirst_name().equals("null")){
                new_staff_name_input.setText(staffData.getFirst_name());
            }

            if (!staffData.getLast_name().equals("null")){
                new_staff_last_name_input.setText(staffData.getLast_name());
            }

            if (!staffData.getPhone().equals("null")){
                new_staff_phone_input.setText(staffData.getPhone());
            }

            if (!staffData.getEmail().equals("null")){
                new_staff_email_input.setText(staffData.getEmail());
            }

            if (!staffData.getAddress().equals("null")){
                new_staff_add_input.setText(staffData.getAddress());
            }

            if (staffData.getAdmin_flag().equals("Yes")){
               new_staff_admin_btn.setChecked(true);
            }

        }



        return Mroot;
    }

    @OnClick(R.id.new_staff_back_btn)
    void backGo() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.new_staff_next_btn)
    void nextGo() {

        if (new_staff_name_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Staff first name cannot Blank", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else {
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



            Bundle _bundle =   new Bundle();
            _bundle.putString("src",bundle.getString("src"));
            _bundle.putInt("pos",bundle.getInt("pos"));
            _bundle.putInt("create_pos",bundle.getInt("create_pos"));

            // start new page
            replaceFrag(new NewStaffInfoFrag(), _bundle, NewStaffFrag.class.getName());


        }


    }





    @OnClick(R.id.new_staff_contact_icn)
    void pickContacts() {

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS);
        Log.e("denied","denied");
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);

        }

        else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 1);


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

                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);


                } else {
                    Log.e("denied","denied");

                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                    Snackbar snackbar = Snackbar
                            .make(Mroot, "user denied Read Contact permission!!!", Snackbar.LENGTH_LONG);

                    snackbar.show();

                }
                return;
            }




        }
    }



    // pick contact
    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        Log.e("00","00");
        switch (reqCode) {
            case (PICK_CONTACT):

                if (resultCode == Activity.RESULT_OK) {

                  /*  Uri contactData = data.getData();

                    Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        Log.e("1","1");

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getActivity().getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            picked_number = phones.getString(phones.getColumnIndex("data1"));
                            Log.e("2","2");
                        }
                        picked_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                    }

                    Log.e("pick",picked_name+"----"+picked_number);

                    new_staff_name_input.setText(picked_name);

                    new_staff_phone_input.setText(picked_number);*/


                    Uri contactData = data.getData();
                    Cursor c = getActivity().managedQuery(contactData, null, null, null, null);
                    if (c.getCount() > 0) {
                        while (c.moveToNext()) {
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            new_staff_name_input.setText(name);
                        }

                    }
                }
                else{
                    Log.e("0","0");
                }
                break;
        }


    }


}