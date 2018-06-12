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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


/**
 * Created by teknik on 10/9/2017.
 */

public class AddGroupFrag extends HelperFrags implements HttpresponseUpd {

    private View Mroot;

    private Bundle bundle;

    @BindView(R.id.add_group_name_input)
    public EditText name_input;

    private HttpresponseUpd callback;

    private Snackbar snackbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.create_group_screen, null);
        ButterKnife.bind(this, Mroot);

        callback = this;

        bundle = getArguments();

        Log.e("datab", bundle.getString("data"));

        return Mroot;
    }

    @OnClick(R.id.add_grp_back_btn)
    void back() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.add_grp_next_btn)
    void next() {

        if (name_input.getText().toString().equals("")) {

        } else {

            //hit api
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "createGroup")
                    .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""))
                    .appendQueryParameter("grp_name", name_input.getText().toString())
                    .appendQueryParameter("recipent", bundle.getString("data"));


            String myUrl = builder.build().toString();

            Log.e("url", myUrl);

            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(myUrl, getActivity(), callback);
            else {
                snackbar = Snackbar.make(Mroot, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        }

        // replaceFrag(new BusinessDetailsFrag(),new Bundle(),AddGroupFrag.class.getName());

    }


    @Override
    public void getResponse(String response) {


        Log.e("res", response);

        if (response.contains("Error :")) {
            snackbar = Snackbar.make(Mroot, response, Snackbar.LENGTH_LONG);

            snackbar.show();

        } else {

            try {
                //parse data
                JSONObject main_obj = new JSONObject(response);
                JSONArray arr = main_obj.getJSONArray("output");
                JSONObject obj = arr.getJSONObject(0);

                if (obj.getString("group_id").equals("0")) {
                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();
                } else {
                    // save publish_id
                    LandingActivity.business_data.setPublish_id(obj.getString("group_id"));

                    snackbar = Snackbar.make(Mroot, obj.getString("message"), Snackbar.LENGTH_LONG);

                    snackbar.show();


                    bundle = new Bundle();
                    bundle.putString("src", "def");

                    replaceFrag(new BusinessDetailsFrag(), bundle, "");


                }


            } catch (JSONException e) {
                snackbar = Snackbar.make(Mroot, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }


        }
    }
}
