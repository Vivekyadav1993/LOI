package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.ManageResSpecificationGrid;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.ManageSpecPosData;
import models.ResourceSpecification;
import models.SpecQTYDATA;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/10/2017.
 */

public class ResSpecManageFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.manageres_apec_grid_list)
    GridView grid;

    private ManageResSpecificationGrid adapter;

    private Bundle bundle;

    private String[] spec_name_array ,spec_id_array;

    private int total_count=0;

    private String res_id  ;

    public static ArrayList<ManageSpecPosData> data = new ArrayList<>();

    private  HttpresponseUpd callback;

    private Snackbar snackbar;

    private ArrayList<SpecQTYDATA> QtyData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.res_specification_manage_screen , null);
        ButterKnife.bind(this , Mroot);

        callback = this;

        bundle = getArguments();


    try {

    JSONArray raw_data = new JSONArray(bundle.getString("res_spec_rawdata"));

    // extract spec type
    spec_name_array =  new String[raw_data.length()+1];
    spec_id_array =  new String[raw_data.length()+1];
    spec_name_array[0] = "SELECT";
    spec_id_array[0] = "0";

    for (int i = 0; i <raw_data.length() ; i++) {
        JSONObject obj = raw_data.getJSONObject(i);
        spec_name_array[i+1]= obj.getString("type");
        spec_id_array[i+1]= obj.getString("id");

        res_id = obj.getString("resource_id");

        QtyData.add(new SpecQTYDATA(obj.getString("id"),Integer.parseInt(obj.getString("total_count"))));

        total_count += Integer.parseInt(obj.getString("total_count"));

    }

        data.clear();
    // add total length in array
    for (int i = 0; i < total_count; i++) {
          data.add(new ManageSpecPosData(res_id , "","","",""));

    }


}catch (JSONException e)
{

}



        adapter = new ManageResSpecificationGrid(getActivity(), data ,spec_name_array , spec_id_array ,QtyData );
        grid.setAdapter(adapter);



        return Mroot;
    }

    @OnClick(R.id.manageres_spec_back_btn)
    void back(){
        getActivity().onBackPressed();
    }


    @OnClick(R.id.manageres_spec_next_btn)
    void save() {

        // hit api

        //Maint list object
        JSONObject objMainList = new JSONObject();
        JSONArray arrForA = new JSONArray();
        try {


            int status = 0;
            for (int i = 0; i < data.size(); i++) {


                if (data.get(i).getSpec_id().equals("")) {


                    status = 1;
                    break;

                }

            }

            if (status == 1) {
                snackbar = Snackbar.make(Mroot, "All Position must be select!!!", Snackbar.LENGTH_LONG);

                snackbar.show();
            } else {

                //prepare item array for "A"


                for (int i = 0; i < data.size(); i++) {


                    JSONObject itemA = new JSONObject();
                    itemA.put("spec_id", data.get(i).getSpec_id());
                    itemA.put("spec_name", data.get(i).getSpec_name());
                    itemA.put("spec_position", data.get(i).getSpec_pos());
                    arrForA.put(itemA);

                }


                // call api to create event id
                //hit api
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath("new_service")
                        .appendPath("api.php")
                        .appendQueryParameter("action", "createPosition")
                        .appendQueryParameter("resource_id", LandingActivity.res_data_array.get(bundle.getInt("pos")).getRes_id())

                        .appendQueryParameter("position", arrForA.toString())

                ;


                Log.e("url", builder.build().toString());


                if (AppUtils.isNetworkAvailable(getActivity()))
                    AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
                else {
                    snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

                    snackbar.show();

                }

            }


            }
            catch(JSONException e){
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);

                snackbar.show();
            }






    }


    @Override
    public void getResponse(String response) {


        Log.e("res", response);

        if (response.contains("Error :")){
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else{

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("resource_id").equals("0")){
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else{

                    // save data

                    LandingActivity.res_data_array.get(bundle.getInt("pos")).setRes_id(obj.getString("resource_id"));




                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();

                    Bundle _bundle  = new Bundle();


                    _bundle.putInt("create_pos", bundle.getInt("create_pos"));


                    replaceFrag(new AddServiceFrag(),_bundle , AddResourcesFrag.class.getName());


                }





            }
            catch (JSONException e){
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }




    }
}
