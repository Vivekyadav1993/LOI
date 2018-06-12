package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.ServiceCancellationList;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.CancelPolicyData;
import models.HomeSerData;

/**
 * Created by teknik on 10/6/2017.
 */

public class NewSerCancellationPolicyFrag extends HelperFrags implements HttpresponseUpd{

    private View Mroot;

    @BindView(R.id.newser_cancelpolicy_list)
    ListView list;

    @BindView(R.id.newser_cancelpolicy_noofser_input)
    EditText newser_cancelpolicy_noofser_input;

    @BindView(R.id.newser_cancelpolicy_refund_amount_input)
    EditText newser_cancelpolicy_refund_amount_input;

    private ServiceCancellationList adapter;

    private Snackbar snackbar;

    private ArrayList<CancelPolicyData> data = new ArrayList<>();

    private  HttpresponseUpd callback;

    private  String post_tag = "" ;

    private  Bundle bundle;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_cancellationpolicy_screen, null);
        ButterKnife.bind(this  , Mroot);

        callback = this;

        bundle = getArguments();


        // get cancel policy
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getCancellation")
                .appendQueryParameter("business_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){

            post_tag="get";

            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }




        return Mroot;
    }
    @OnClick(R.id. newser_cancelpolicy_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id. newser_cancelpolicy_next_btn)
    void create(){

        JSONArray arrForA =  new JSONArray();

        try {



            //prepare item array for "A"


            for (int i = 0; i <data.size() ; i++) {
                JSONObject itemA = new JSONObject();
                itemA.put("service_id", bundle.getString("ser_id"));
                itemA.put("address_id", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());
                itemA.put("no_of_service_in_last", data.get(i).getSer_count());
                itemA.put("refund_amount", data.get(i).getRefund_amount());



                arrForA.put(itemA);
            }





            //Finally add item arrays for "A" and "B" to main list with key


        }
        catch (JSONException e){
            snackbar = Snackbar.make(Mroot,e.getMessage(), Snackbar.LENGTH_LONG);

            snackbar.show();
        }


        // get cancel policy
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "createCancellation")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id())

                .appendQueryParameter("cancel_policy", arrForA.toString()) ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){

            post_tag="create";

            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }


    }

    @OnClick(R.id.newser_cancelpolicy_add_btn)
    void addCancelPolicy(){

        //
        if (newser_cancelpolicy_noofser_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot,"No of service cannot blank!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if (newser_cancelpolicy_refund_amount_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot,"Refund amount cannot blank!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{
            // add data into array
            data.add(new CancelPolicyData("",newser_cancelpolicy_noofser_input.getText().toString()+ " Period"
            ,newser_cancelpolicy_refund_amount_input.getText().toString()+ " Refund %"));

            adapter = new ServiceCancellationList(getActivity(),data);
            list.setAdapter(adapter);

            newser_cancelpolicy_noofser_input.setText("");
            newser_cancelpolicy_refund_amount_input.setText("");
        }
    }


    @Override
    public void getResponse(String response) {

        Log.e("res", response);
        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else if (post_tag.equals("get")){
            // parse data
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                data.clear();
                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    data.add(new CancelPolicyData(obj.getString("id"),obj.getString("no_of_service_in_last")
                    ,obj.getString("refund_amount")));

                }

                adapter = new ServiceCancellationList(getActivity(),data);
                list.setAdapter(adapter);


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
                JSONObject obj = arr.getJSONObject(0);

           if (obj.getString("message").equals("Cancellation policy created successfully")){
               snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

               snackbar.show();
               Bundle _bundle = new Bundle();


               _bundle.putInt("create_pos", bundle.getInt("create_pos"));
               _bundle.putString("ser_id", bundle.getString("ser_id"));

               replaceFrag(new NewSerCustomerPolicyFrag(), _bundle , NewSerCancellationPolicyFrag.class.getName());

           }

                else {


               snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

               snackbar.show();




           }


        } catch (JSONException e) {
            snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        }
    }
}
