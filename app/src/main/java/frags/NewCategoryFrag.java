package frags;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.AddSerAdap;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.HomeSerData;
import models.ServiceData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/6/2017.
 */

public class NewCategoryFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    protected String[] receivers , receivers_id;

    protected ArrayList<String> selectedReceivers = new ArrayList<>();

    private HttpresponseUpd callback;

    private Bundle bundle;

    private Snackbar snackbar;

    private String post_tag = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.new_category_screen, null);
        ButterKnife.bind(this , Mroot);

        bundle = getArguments();

        callback = this;

        // get service api
        //hit get service api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id())

        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){
            post_tag= "get";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }

        return Mroot;
    }

    @OnClick(R.id.new_ctg_next_btn)
    void next(){

        JSONArray arrForA =  new JSONArray();

        try {



            //prepare item array for "A"




            for (int i = 0; i <selectedReceivers.size() ; i++) {
                JSONObject itemA = new JSONObject();
                itemA.put("cat_id", bundle.getString("cat_id"));
                itemA.put("service_id", selectedReceivers.get(i));




                arrForA.put(itemA);
            }





            //Finally add item arrays for "A" and "B" to main list with key


        }
        catch (JSONException e){
            snackbar = Snackbar.make(Mroot,e.getMessage(), Snackbar.LENGTH_LONG);

            snackbar.show();
        }


        // hit create api
        //hit get service api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "createserviceCategory")
                .appendQueryParameter("servicecategory",
                        arrForA.toString())

        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){
            post_tag= "create";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


    }

    @OnClick(R.id.new_ctg_back_btn)
    void back(){
       getActivity().onBackPressed();
    }


    @OnClick(R.id.new_ctg_service_btn)
    void ctgSer(){
        showSelectReceiversDialog();
    }

    protected void onChangeSelectedReceivers() {
        StringBuilder stringBuilder = new StringBuilder();

        for(CharSequence receivers : selectedReceivers)
            stringBuilder.append(receivers + ",");

        //selectReceiversBtn.setText(stringBuilder.toString());
    }

    protected void showSelectReceiversDialog() {
        boolean[] checkedReceivers = new boolean[receivers.length];
        int count = receivers.length;

        for(int i = 0; i < count; i++)
            checkedReceivers[i] = selectedReceivers.contains(receivers_id[i]);

        DialogInterface.OnMultiChoiceClickListener receiversDialogListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                    selectedReceivers.add(receivers[which]);
                else
                    selectedReceivers.remove(receivers[which]);

                onChangeSelectedReceivers();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Select Receivers")
                .setMultiChoiceItems(receivers, checkedReceivers, receiversDialogListener)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);


        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else if (post_tag.equals("get")) {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                receivers = new String[arr.length()];
                receivers_id = new String[arr.length()];


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    receivers_id[i] = obj.getString("id");
                    receivers[i] = obj.getString("name");



                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

        else{

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                if (arr.getJSONObject(0).getString("service_id").equals("0")){

                }
                else{

                    Bundle _bundle = new Bundle();

                    _bundle.putString("add_id",LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id());
                    replaceFrag(new SequentialFrag() , _bundle ,NewCategoryFrag.class.getName() );

                }

                snackbar = Snackbar.make(Mroot, arr.getJSONObject(0).getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }

    }
}
