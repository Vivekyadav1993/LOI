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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.NewSerDiscountList;
import adapters.ServiceCancellationList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.CancelPolicyData;
import models.DiscountPolicyData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/6/2017.
 */

public class NewSerDiscountPolicyFrag extends HelperFrags implements HttpresponseUpd {


    private View Mroot;





    @BindView(R.id.discount_policy_classno_input)
    EditText discount_policy_classno_input;

    @BindView(R.id.discount_policy_period_input)
    EditText discount_policy_period_input;

    @BindView(R.id.discount_policy_days_input)
    EditText discount_policy_days_input;

    @BindView(R.id.discount_policy_dis_input)
    EditText discount_policy_dis_input;



    @BindView(R.id.newser_dispolicy_list)
    ListView newser_dispolicy_list;

    private NewSerDiscountList adapter;

    private Snackbar snackbar;

    private HttpresponseUpd callback;

    private Bundle bundle;

    private ArrayList<DiscountPolicyData> data = new ArrayList<>();

    private String post_tag = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_discount_policy , null);
        ButterKnife.bind(this , Mroot);

        callback = this ;

        bundle =  getArguments();

        //hit get api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getDiscount")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
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




     @OnClick(R.id.discount_policy_add_btn)
     void addPolicy(){

         //
         if (discount_policy_classno_input.getText().toString().equals("")){
             snackbar = Snackbar.make(Mroot,"No of class cannot blank!", Snackbar.LENGTH_LONG);
             snackbar.show();
         }
         else if (discount_policy_period_input.getText().toString().equals("")){
             snackbar = Snackbar.make(Mroot,"No of period cannot blank!", Snackbar.LENGTH_LONG);
             snackbar.show();
         }
         else if (discount_policy_days_input.getText().toString().equals("")){
             snackbar = Snackbar.make(Mroot,"No of days cannot blank!", Snackbar.LENGTH_LONG);
             snackbar.show();
         }
         else if (discount_policy_dis_input.getText().toString().equals("")){
             snackbar = Snackbar.make(Mroot,"discount % cannot blank!", Snackbar.LENGTH_LONG);
             snackbar.show();
         }
         else{
             // add data into array
             data.add(new DiscountPolicyData ("",discount_policy_classno_input.getText().toString()+" Class"
                     ,discount_policy_period_input.getText().toString()+" Period"
             ,discount_policy_days_input.getText().toString()+" Days"
             ,discount_policy_dis_input.getText().toString()+" Discount %"));

             adapter= new NewSerDiscountList(getActivity(),data);
             newser_dispolicy_list.setAdapter(adapter);


             discount_policy_classno_input.setText("");
             discount_policy_period_input.setText("");
             discount_policy_days_input.setText("");
             discount_policy_dis_input.setText("");
         }

     }


    @OnClick(R.id. newser_dispolicy_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id. newser_dispolicy_next_btn)
    void create(){

        JSONArray arrForA =  new JSONArray();

        try {



            //prepare item array for "A"


            for (int i = 0; i <data.size() ; i++) {
                JSONObject itemA = new JSONObject();
                itemA.put("service_id", bundle.getString("ser_id"));
                itemA.put("address_id", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());
                itemA.put("subsequent_class", data.get(i).getClass_count());
                itemA.put("period ", data.get(i).getPeriod_count());
                itemA.put("discount",data.get(i).getDiscount_amount());
                itemA.put("day",data.get(i).getDay_count());


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
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "createDiscount")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id())
                .appendQueryParameter("discount_policy", arrForA.toString()) ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){

            post_tag="create";

            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

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

                    data.add(new DiscountPolicyData(obj.getString("id"),
                            obj.getString("subsequent_class")
                            ,obj.getString("period")
                            ,obj.getString("discount")
                            ,obj.getString("days")));

                }

                adapter = new NewSerDiscountList(getActivity(), data);

                newser_dispolicy_list.setAdapter(adapter);


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

                if (obj.getString("message").equals("Discount created successfully")){
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
