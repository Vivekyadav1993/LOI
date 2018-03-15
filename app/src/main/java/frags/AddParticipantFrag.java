package frags;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import adapters.ParticipantList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import models.ContactsData;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/9/2017.
 */

public class AddParticipantFrag extends HelperFrags {

    private View Mroot;

    @BindView(R.id.add_parti_list)
    ListView list;

    @BindView(R.id.add_parti_phone_input)
    EditText add_parti_phone_input;



    private  Snackbar snackbar;



    private  ParticipantList adapter;

    // Cursor to load contacts list
    Cursor phones;

    private ArrayList<ContactsData> contacts_array = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.select_participant_screen , null );
        ButterKnife.bind(this , Mroot);


        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_CONTACTS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //Execute location service call if user has explicitly granted ACCESS_FINE_LOCATION..
            phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            LoadContact loadContact = new LoadContact();
            loadContact.execute();
        }

        else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_CONTACTS}, 3);


        }



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CheckBox tic = (CheckBox) view.findViewById(R.id.contacts_list_select_btn);
                Log.e("okk", "click");
                if (!contacts_array.get(position).isSelected()) {
                    tic.setChecked(true);
                    ContactsData c = new ContactsData(contacts_array.get(position).getName(), contacts_array.get(position).getNumber(),true);
                    contacts_array.remove(position);
                    contacts_array.add(position ,c);

                } else {
                    tic.setChecked(false);
                    ContactsData c = new ContactsData(contacts_array.get(position).getName(), contacts_array.get(position).getNumber(),false);
                    contacts_array.remove(position);
                    contacts_array.add(position ,c);



                }

                adapter.setData(contacts_array);
            }
        });




        return Mroot;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                    LoadContact loadContact = new LoadContact();
                    loadContact.execute();

                } else {
                    Log.e("loc","null");


                    showMSg("Get Contact permission denied by user!!!");
                }
                return;
            }




        }
    }


    @OnClick(R.id.add_parti_back_btn)
    void back(){
        getActivity().onBackPressed();
    }


    @OnClick(R.id.add_parti_createbtn)
    void create(){


        JSONArray jsonArray = new JSONArray();
        try {

            for (int i = 0; i <contacts_array.size() ; i++) {
                if (contacts_array.get(i).isSelected()) {
                    JSONObject participants = new JSONObject();
                    participants.put("recip_name",contacts_array.get(i).getName());
                    participants.put("recip_mobile",contacts_array.get(i).getNumber());
                    jsonArray.put(participants);
                }
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }






        Bundle bundle = new Bundle();
        bundle.putString("data",jsonArray.toString());

       replaceFrag(new AddGroupFrag() , bundle , AddParticipantFrag.class.getName());
    }

    @OnClick(R.id.add_parti_phone_invitebtn)
    void addPatcipant(){

        if (add_parti_phone_input.getText().toString().equals("")){

            snackbar = Snackbar.make(Mroot, "Phone number cannot blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
        else{

            ContactsData c = new ContactsData("Guest", add_parti_phone_input.getText().toString(),true);
            contacts_array.add(c);
            snackbar = Snackbar.make(Mroot, "Phone number added successfully!!!", Snackbar.LENGTH_LONG);
            snackbar.show();

        }



    }


    // Load data on background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                Log.e("count", "" + phones.getCount());
                if (phones.getCount() == 0) {
                    Toast.makeText(getActivity(), "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    contacts_array.add(new ContactsData(name , phoneNumber , false));
                   Log.e("data","name="+name+"---"+"phoneNumber="+phoneNumber+"---");
                }
            } else {

            }
            phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Collections.sort(contacts_array, new Comparator<ContactsData>() {
                @Override
                public int compare(ContactsData o1, ContactsData o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });

            adapter = new ParticipantList(getActivity() , contacts_array);
            list.setAdapter(adapter);
        }
    }




}
