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

import java.util.ArrayList;

import adapters.ServiceResourcesSelection;
import adapters.ServiceStaffSelection;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.StaffSelecData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/6/2017.
 */

public class NewSerResourceSelectionFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;


    @BindView(R.id.newser_resselec_list)
    ListView list;

    private ServiceResourcesSelection adapter;

    private Bundle bundle;
    private HttpresponseUpd callback;

    public static ArrayList<StaffSelecData> resSelec_array = new ArrayList<>();

    private Snackbar snackbar ;

    private String post_tag = "";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.newser_resources_selection_screen, null);
        ButterKnife.bind(this , Mroot);

        bundle = getArguments();

        callback = this;





        // hit api
        //hit get service api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getResource")
                .appendQueryParameter("service_id",bundle.getString("ser_id"))
                .appendQueryParameter("address_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id())

        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){
            post_tag = "getstaff";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }








        return Mroot;
    }



    @OnClick(R.id.newser_resselec_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.newser_resselec_next_btn)
    void next() {

        JSONArray arrForA = new JSONArray();

        if (resSelec_array.size()==0){
            Bundle _bundle  = new Bundle();
            _bundle.putInt("create_pos", bundle.getInt("create_pos"));
            _bundle.putString("ser_id", bundle.getString("ser_id"));
            // go next page
            replaceFrag(new NewSerAdTypeSelectionFrag(), _bundle, NewSerResourceSelectionFrag.class.getName());

        }
        else {

            try {


                //prepare item array for "A"


                for (int i = 0; i < resSelec_array.size(); i++) {
                    JSONObject itemA = new JSONObject();
                    itemA.put("service_id", resSelec_array.get(i).getSer_id());
                    itemA.put("resource_id", resSelec_array.get(i).getStaff_id());


                    if (resSelec_array.get(i).getSelect().equals("")) {

                    } else {
                        arrForA.put(itemA);
                    }
                }


                //Finally add item arrays for "A" and "B" to main list with key


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);

                snackbar.show();
            }
            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath("new_service")
                    .appendPath("api.php")
                    .appendQueryParameter("action", "createserviceResource")
                    .appendQueryParameter("service_id", bundle.getString("ser_id"))
                    .appendQueryParameter("serviceresource", arrForA.toString())
            ;

            Log.e("url", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity())) {
                post_tag = "poststaff";
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            } else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                snackbar.show();

            }

        }


    }


    @Override
    public void getResponse(String response) {
        Log.e("res", response);


        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else if (post_tag.equals("getstaff")){

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                resSelec_array.clear();

                for (int i = 0; i <arr.length() ; i++) {

                    JSONObject obj = arr.getJSONObject(i);

                    resSelec_array.add(new StaffSelecData(bundle.getString("ser_id"),
                            obj.getString("id") ,obj.getString("name") ,obj.getString("associate") ));

                }



                // set staff list
                adapter = new ServiceResourcesSelection(getActivity(), resSelec_array);
                list.setAdapter(adapter);






            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
        else{
            // parse data
            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");


                if (arr.getJSONObject(0).getString("message").equals("Resource assigned successfully")){
                    Bundle _bundle  = new Bundle();


                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));
                    _bundle.putString("ser_id", bundle.getString("ser_id"));
                    // go next page
                    replaceFrag(new NewSerAdTypeSelectionFrag(), _bundle, NewSerResourceSelectionFrag.class.getName());

                }
                snackbar = Snackbar.make(Mroot,arr.getJSONObject(0).getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();





            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
    }
}
