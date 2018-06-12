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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.GroupSelectionList;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.GroupData;


/**
 * Created by teknik on 10/9/2017.
 */

public class GroupSelectionFrag extends HelperFrags implements  HttpresponseUpd{

    private View Mroot;

    @BindView(R.id.group_selec_list)
    ListView list;

    @BindView(R.id.group_selec_createbtn)
    TextView group_selec_createbtn;

    private GroupSelectionList adapter;

    private Bundle bundle;
    private ArrayList<GroupData> data = new ArrayList<>();

    private   Uri.Builder builder;

    private HttpresponseUpd callback;

    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.group_selection_screen , null);
        ButterKnife.bind(this , Mroot);

        callback = this;

        initializeSharedData();

        bundle = getArguments();

        if (bundle.getString("src").equals("private")){
            group_selec_createbtn.setVisibility(View.VISIBLE);
        }
        else{
            group_selec_createbtn.setVisibility(View.GONE);
        }



        adapter = new GroupSelectionList(getActivity(),data);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // save publish_id
                LandingActivity.business_data.setPublish_id(data.get(i).getGroup_id());
                bundle = new Bundle();
                bundle.putString("src","def");

                replaceFrag(new BusinessDetailsFrag(),bundle,GroupSelectionFrag.class.getName());

            }
        });

        if (bundle.getString("src").equals("private")) {
            //hit api
            builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "privateGroupList")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id" , ""));

        }
        else{
            //hit api
            builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "societyList")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id" , ""));

        }

        String myUrl = builder.build().toString();

        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(myUrl, getActivity() ,callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }





        return Mroot;
    }


    @OnClick(R.id.group_selec_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.group_selec_createbtn)
    void createGroup(){
      replaceFrag(new AddParticipantFrag() , new Bundle() , GroupSelectionFrag.class.getName());
    }

    @Override
    public void getResponse(String response) {
        if (response.contains("Error :")){
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else{
            try{
                data.clear();
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                for (int i = 0; i < arr.length(); i++) {
                    data.add(new GroupData(arr.getJSONObject(i).getString("id"),arr.getJSONObject(i).getString("title"),"Total participant : "+arr.getJSONObject(i).getString("total_people_count")));
                }

                adapter = new GroupSelectionList(getActivity(), data);
                list.setAdapter(adapter);

            }
            catch (JSONException e){

            }
        }
        Log.e("res", response);
    }
}
