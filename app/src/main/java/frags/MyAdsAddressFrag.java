package frags;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.MyAdsAddressAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.businesslist.Output;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.MyAdsAddress;
import r2stech.lifeoninternet.VenderDetailsActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.utils.Utils;
import r2stech.lifeoninternet.views.AppointmentDashbord;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAdsAddressFrag extends HelperFrags implements HttpresponseUpd {


    private Snackbar snackbar;
    @BindView(R.id.my_ads_address_frag_recycler_view)
    public RecyclerView mRecyclerView;

    @BindView(R.id.my_ads_add_back_btn)
    public ImageView mBack;

    @BindView(R.id.frag_my_ads_address_main_ll)
    public LinearLayout mDataLinearLayout;
    @BindView(R.id.frag_my_ads_address_no_address_ll)
    public LinearLayout mNoDataLinearLayout;

    private MyAdsAddressAdapter mMyAdsAddressAdapter;
    private ArrayList<Output> list;
    private ArrayList<Businessaddress> businessaddressesList;
    private HttpresponseUpd callback;
    public String status;
    public String business_id;

    public MyAdsAddressFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_ads_address, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        list = new ArrayList<>();
        businessaddressesList = new ArrayList<>();
        callback = this;

        status = getArguments().getString("commingfrom");

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("lifeoninternet.com")
                .appendPath(Utils.stringBuilder())
                .appendPath("api.php")
                .appendQueryParameter("action", "getbusinessList")
                .appendQueryParameter("user_id", AppConstants.app_data.getString("user_id", ""));

        String myUrl = builder.build().toString();
        Log.e("urlgetLsiAdd", myUrl);

        if (AppUtils.isNetworkAvailable(getActivity()))
            AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
        else {
            snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @OnClick({R.id.my_ads_add_back_btn, R.id.my_ads_address_frag_float_button})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.my_ads_add_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.my_ads_address_frag_float_button:

                Intent intent = new Intent(getContext(), VenderDetailsActivity.class);
                startActivity(intent);

             /*   Bundle _bundle = new Bundle();
                //  _bundle.putString("address_id", address_id);
                  _bundle.putString("business_id", business_id);
                replaceFrag(new UpLoadCompanyDetailsFrag(), _bundle, AddStaffFrag.class.getName());

*/
                break;

        }

    }


    @Override
    public void getResponse(String response) {
        Log.e("responce", response);

        if (response.contains("error")) {
            snackbar = Snackbar.make(getView(), "Network error occurred!!!" + response, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {

            try {
                Gson gson = new Gson();

                MyAdsAddress myAdsAddress = gson.fromJson(response, MyAdsAddress.class);

                if (myAdsAddress.getOutput().get(0).getBusinessaddress() == null) {

                    mDataLinearLayout.setVisibility(View.GONE);
                    mNoDataLinearLayout.setVisibility(View.VISIBLE);
                }

                //   JSONArray main_array = main_obj.getJSONArray("output");
                else {
                    mDataLinearLayout.setVisibility(View.VISIBLE);
                    mNoDataLinearLayout.setVisibility(View.GONE);

                    business_id = myAdsAddress.getOutput().get(0).getBusinessId();

                    for (int j = 0; j < myAdsAddress.getOutput().get(0).getBusinessaddress().size(); j++) {

                        Businessaddress businessaddress = new Businessaddress();
                        businessaddress.setAddressId(myAdsAddress.getOutput().get(0).getBusinessaddress().get(j).getAddressId());
                        businessaddress.setAddress(myAdsAddress.getOutput().get(0).getBusinessaddress().get(j).getAddress());

                        businessaddressesList.add(businessaddress);
                    }
                    mRecyclerView.setHasFixedSize(true);
                    mMyAdsAddressAdapter = new MyAdsAddressAdapter(this, businessaddressesList, new MyAdsAddressAdapter.MyAdsAddressonClick() {
                        @Override
                        public void onClick(int pos, String address_id) {

                            Bundle _bundle = new Bundle();
                            _bundle.putString("address_id", address_id);
                            _bundle.putString("business_id", business_id);


                            if (status.equals("myads")) {

                                replaceFrag(new MyAdsFrag(), _bundle, AddStaffFrag.class.getName());

                            } else if (status.equals("editads")) {

                                Intent intent = new Intent(getActivity(), AppointmentDashbord.class);
                                intent.putExtra("business_id", business_id);
                                intent.putExtra("address_id", address_id);
                                startActivity(intent);

                                // replaceFrag(new MyCustomerFrag(), _bundle, AddStaffFrag.class.getName());
                            }
                        }
                    });
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mMyAdsAddressAdapter);
                }

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "" + e, Toast.LENGTH_SHORT).show();
            }


        }

    }
}


