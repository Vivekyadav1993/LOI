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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.AddCtgAdap;
import adapters.AddSerAdap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.CategoryData;
import models.HomeSerData;
import models.ServiceData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/6/2017.
 */

public class AddCategoryFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    @BindView(R.id.ctg_name_input)
    public EditText ctg_name_input;

    @BindView(R.id.add_ctg_list)
    ListView add_ctg_list;

    private int pos = 0;

    private AddCtgAdap adapter;

    private HttpresponseUpd callback;

    private Bundle bundle;

    private ArrayList<CategoryData> ser_category_array = new ArrayList<>();
    private Snackbar snackbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_category_screen, null);
        ButterKnife.bind(this, Mroot);


        bundle = getArguments();

        callback = this;


        add_ctg_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle.putString("cat_id", ser_category_array.get(i).getId());

                replaceFrag(new NewCategoryFrag(), bundle, AddStaffFrag.class.getName());

            }
        });


       /* // get category api
        //hit get service api
        //hit api
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath("new_service")
                .appendPath("api.php")
                .appendQueryParameter("action", "getCategory")
        ;

        Log.e("url",builder.build().toString() );
        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);

            snackbar.show();

        }*/


        return Mroot;
    }


    @OnClick(R.id.add_ctg_next_btn)
    void done() {
        if (ctg_name_input.getText().equals("") && ctg_name_input.getText().equals(null)) {
            ctg_name_input.setError("Category can't be blank");
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
        } else if (!ctg_name_input.getText().equals(null)) {
            Toast.makeText(getContext(), "D", Toast.LENGTH_SHORT).show();
            bundle.putString("cat_id", ctg_name_input.getText().toString());
            replaceFrag(new NewCategoryFrag(), bundle, AddStaffFrag.class.getName());
        }
    }

    @OnClick(R.id.add_ctg_btn)
    void addRes() {

        // go next page
        replaceFrag(new NewCategoryFrag(), new Bundle(), AddCategoryFrag.class.getName());


    }

    @OnClick(R.id.add_ctg_back_btn)
    void backGo() {
        getActivity().onBackPressed();
    }


  /*  @OnClick(R.id.add_ctg_next_btn)
    void next() {
        Bundle _bundle = new Bundle();
        _bundle.putString("add_id", LandingActivity.service_data_array.get(bundle.getInt("pos")).getAdd_id());
        replaceFrag(new AdDetailsEditFrag(), _bundle, AddCategoryFrag.class.getName());

    }*/


    @Override
    public void getResponse(String response) {
        // parse data

        Log.e("res", response);


        if (response.contains("Error :")) {

            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");

                ser_category_array.clear();

                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obja = arr.getJSONObject(j);
                    ser_category_array.add(new CategoryData(obja.getString("id"), obja.getString("title")));
                }

                // set staff list
                adapter = new AddCtgAdap(getActivity(), ser_category_array);

                add_ctg_list.setAdapter(adapter);


            } catch (JSONException e) {

                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
    }
}
