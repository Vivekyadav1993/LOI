package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.NewSerDiscountList;
import adapters.NewSerPackConfigManageList;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.DiscountPolicyData;
import models.PackageConfigData;

/**
 * Created by teknik on 10/10/2017.
 */

public class NewSerPackageConfigManageFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.pack_confimanage_list)
    ListView list;

    private HttpresponseUpd callback;

    private Bundle bundle;

    private NewSerPackConfigManageList adapter;

    private String post_tag = "" ;

    private Snackbar snackbar ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_packageconfig_managelist_screen , null);
        ButterKnife.bind(this  , Mroot);

        bundle = getArguments();

        callback = this;

        //hit get api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getPackage")
                .appendQueryParameter("service_id", bundle.getString("ser_id"))
                .appendQueryParameter("address_id", LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());

        Log.e("url",builder.build().toString());

        if (AppUtils.isNetworkAvailable(getActivity())){

            post_tag="get";

            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }






        return Mroot;
    }

    @OnClick(R.id.pack_confimanage_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.pack_confimanage_next_btn)
    void save(){

        JSONArray arrForA =  new JSONArray();

        try {



            //prepare item array for "A"


            for (int i = 0; i <LandingActivity.ser_package_array.size() ; i++) {
                JSONObject itemA = new JSONObject();
                itemA.put("service_id", bundle.getString("ser_id"));
                itemA.put("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id());
                itemA.put("no_of_days", LandingActivity.ser_package_array.get(i).getNo_of_days());
                itemA.put("duration_per_class",LandingActivity.ser_package_array.get(i).getDuration_per_class() );
                itemA.put("no_of_class", LandingActivity.ser_package_array.get(i).getNo_of_class());
                itemA.put("package_cost", LandingActivity.ser_package_array.get(i).getPackage_cost());
                itemA.put("cancelled_by_vendor_refund", LandingActivity.ser_package_array.get(i).getCancelled_by_vendor_refund());
                itemA.put("cancelled_by_vendor_class_in_next_period",LandingActivity.ser_package_array.get(i).getCancelled_by_vendor_class_in_next_period());
                itemA.put("cancelled_by_customer_refund",LandingActivity.ser_package_array.get(i).getCancelled_by_customer_refund());
                itemA.put("cancelled_by_customer_class_in_next_period",LandingActivity.ser_package_array.get(i).getCancelled_by_customer_class_in_next_period());
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
                .appendQueryParameter("action", "createPackage")
        .appendQueryParameter("service_id", bundle.getString("ser_id"))
        .appendQueryParameter("address_id",LandingActivity.business_data.getAdderess_data().get(bundle.getInt("create_pos")).getAddress_id())

        .appendQueryParameter("package_policy", arrForA.toString()) ;

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

                for (int i = 0; i <arr.length() ; i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    LandingActivity.ser_package_array.add(new PackageConfigData(obj.getString("no_of_days")+" Day",
                            obj.getString("duration_per_class")+" Duration per class"
                            ,obj.getString("no_of_class")+" Class"
                            ,obj.getString("package_cost")+ " Cost"
                            ,obj.getString("cancelled_by_vendor_refund")
                    ,obj.getString("cancelled_by_vendor_class_in_next_period")
                    ,obj.getString("cancelled_by_customer_refund")
                    ,obj.getString("cancelled_by_customer_class_in_next_period")));

                }

                adapter = new NewSerPackConfigManageList(getActivity() ,LandingActivity.ser_package_array);
                list.setAdapter(adapter);



            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();

                adapter = new NewSerPackConfigManageList(getActivity() ,LandingActivity.ser_package_array);
                list.setAdapter(adapter);

            }
        }
        else{

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("message").equals("Package policy created successfully")){
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else {


                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();

                    Bundle _bundle = new Bundle();


                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));
                    _bundle.putString("ser_id", bundle.getString("ser_id"));

                    replaceFrag(new NewSerCustomerPolicyFrag(), _bundle , NewSerCancellationPolicyFrag.class.getName());



                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

    }
}
