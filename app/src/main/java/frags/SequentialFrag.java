package frags;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.SequentialList;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.SequentialData;


/**
 * Created by teknik on 10/10/2017.
 */

public class SequentialFrag extends HelperFrags /*implements HttpresponseUpd */{

    private View Mroot;

    @BindView(R.id.seq_btn)
    Switch seq_btn;

    @BindView(R.id.seq_listview)
     ListView list;

    private SequentialList adapter;

    private HttpresponseUpd callback;

    public static ArrayList<SequentialData> data = new ArrayList<>();

    private String[] service_name_array;

    private String post_tag = "";

    private Bundle bundle;

    private Snackbar snackbar;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.sequential_screen , null);
        ButterKnife.bind(this , Mroot);

      //  callback = this;

        bundle = getArguments();

        // get service api
        //hit get service api
        //hit api
     /*   Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getService")
                .appendQueryParameter("address_id", bundle.getString("add_id"))

        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){
            post_tag= "get";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        seq_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    list.setVisibility(View.VISIBLE);
                }
                else{
                    list.setVisibility(View.GONE);
                }
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

*/
        return Mroot;
    }

    @OnClick(R.id.seq_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.seq_next_btn)
    void save(){

        JSONArray arrForA =  new JSONArray();

        try {
            //prepare item array for "A"

            for (int i = 0; i <data.size() ; i++) {
                JSONObject itemA = new JSONObject();
                itemA.put("position",data.get(i).getSer_id());
                itemA.put("service_id", data.get(i).getSer_id());

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
      /*  Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "serviceSequence")
                .appendQueryParameter("address_id", bundle.getString("add_id"))
                .appendQueryParameter("servicesequence", arrForA.toString())

        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity())){
            post_tag= "create";
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);}
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
*/    }

  /*  @Override
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

                service_name_array = new String[arr.length()];

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    service_name_array[i] = obj.getString("name");

                    data.add(new SequentialData(obj.getString("id"),0));

                }

                adapter = new SequentialList(getActivity() ,service_name_array);
                list.setAdapter(adapter);

                if (service_name_array.length==0||service_name_array.length==1){
                    Bundle _bundle = new Bundle();

                    _bundle.putString("src", "create");
                    replaceFrag(new BusinessDetailsFrag() , _bundle , "");
                }

            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
                Bundle _bundle = new Bundle();

                _bundle.putString("src", "create");
                replaceFrag(new BusinessDetailsFrag() , _bundle , "");
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

                    _bundle.putString("src", "create");
                    replaceFrag(new BusinessDetailsFrag() , _bundle , "");

                }

                snackbar = Snackbar.make(Mroot, arr.getJSONObject(0).getString("message"), Snackbar.LENGTH_LONG);
                snackbar.show();


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }*/
}
