package frags;


import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapters.AddStaffAdap;
import adapters.CustomerLandingRecyclerViewAdapter;
import adapters.ExpandableListAdapter;
import adapters.FaqChildAdapter;
import adapters.ServiceStaffAdapter;
import adapters.ShowStaffAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.AppUtils;
import helper.HelperFrags;
import helper.HttpresponseUpd;
import models.StaffData;
import models.addetailsEdit.Business;
import models.addetailsEdit.DetailsEditModel;
import models.addetailsEdit.Resource;
import models.addetailsEdit.Service;
import models.addetailsEdit.Servicestaff;
import models.addetailsEdit.Staff;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.interfaces.AddDetailsEditPresenterImpl;
import r2stech.lifeoninternet.interfaces.IAddDetailsPresenter;
import r2stech.lifeoninternet.interfaces.IAddDetailsView;
import r2stech.lifeoninternet.utils.Sharedpreferences;
import r2stech.lifeoninternet.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdDetailsEditFrag extends HelperFrags implements HttpresponseUpd {

    @BindView(R.id.frag_details_edit_lvExp)
    public ExpandableListView expListView;

    @BindView(R.id.ad_details_edit_category_tv)
    public TextView mCategory;


    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private List<Staff> staffList;
    private List<Resource> resourcesList;
    private ExpandableListAdapter listAdapter;

    private IAddDetailsPresenter mIAddDetailsPresenter;
    //Parent Context
    private Bundle bundle;
    private Snackbar snackbar;
    private HttpresponseUpd callback;

    //Fragment Manager Instance
    private FragmentManager fragmentManager;
    private DetailsEditModel mDetailsEditModel;

    @BindView(R.id.ad_details_edit_business_tv)
    public TextView mBusinessNametv;

    @BindView(R.id.ad_details_edit_address_tv)
    public TextView mBusinessAdd;

    @BindView(R.id.ad_details_edit_phone_tv)
    public TextView mPhone;

    @BindView(R.id.ad_details_edit_day_tv)
    public TextView mDate;

    @BindView(R.id.frag_add_edit_details_staff_rv)
    public RecyclerView mStaffRv;

    @BindView(R.id.frag_details_edit_rv)
    public RecyclerView mRecyclerView;

    @BindView(R.id.frag_details_edit_chield_staff_rv)
    public RecyclerView mChildRv;

    @BindView(R.id.frag_ad_details_edit_Sv)
    public ScrollView mScrollView;

    @BindView(R.id.frag_ad_details_edit_tv)
    public TextView mNoDataTv;

    private FaqChildAdapter mFaqChildAdapter;


    private ShowStaffAdapter mShowStaffAdapter;

    private String service_name;

    private List<String> staff_name_service;
    private String staff_name;

    private LinearLayoutManager linearLayoutManager;

    private ServiceStaffAdapter mAdapter;

    private Sharedpreferences mPrefs;

    public String business_id, address_id;

    public AdDetailsEditFrag() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ad_details_edit, container, false);
        ButterKnife.bind(this, view);
        initilizerView();
        return view;
    }

    private void initilizerView() {
        mPrefs = Sharedpreferences.getUserDataObj(getContext());
        staffList = new ArrayList<>();
        resourcesList = new ArrayList<>();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        staff_name_service = new ArrayList<>();
        bundle = getArguments();
        callback = this;
        business_id = mPrefs.getBusnessId();
        address_id = mPrefs.getAddressId();
        //  mPrefs.setBusnessId(business_id);
        Log.d("AdEF", "bus_id" + business_id + "add" + address_id);

        if (business_id.equalsIgnoreCase("null") && address_id.equalsIgnoreCase("null")) {
            mScrollView.setVisibility(View.GONE);
            mNoDataTv.setVisibility(View.VISIBLE);
        } else {
            //hit api

            mScrollView.setVisibility(View.VISIBLE);
            mNoDataTv.setVisibility(View.GONE);

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("lifeoninternet.com")
                    .appendPath(Utils.stringBuilder())
                    .appendPath("api.php")
                    .appendQueryParameter("action", "bookingDetails")
                    .appendQueryParameter("business_id", business_id)
                    .appendQueryParameter("address_id", address_id);

            Log.e("stafflist", builder.build().toString());
            if (AppUtils.isNetworkAvailable(getActivity()))
                AppUtils.getStringData(builder.build().toString(), getActivity(), callback);
            else {
                snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }

        // preparing list data
        //prepareListData();

        //  mIAddDetailsPresenter.showDetails("bookingDetails", "361", "119");

    }

    @OnClick({R.id.ad_details_edit_back_btn, R.id.ad_details_edit_submit_btn, R.id.ad_details_edit_business_iv, R.id.ad_details_edit_staff_iv,
            R.id.ad_details_edit_resource_iv, R.id.ad_details_edit_category_iv, R.id.ad_details_edit_service_iv})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ad_details_edit_back_btn:
                getActivity().onBackPressed();
                break;

            case R.id.ad_details_edit_submit_btn:

                bundle.putString("src", "value");
                replaceFrag(new CustomerLandingFrag(), bundle, "");
                break;

            case R.id.ad_details_edit_service_iv:
                bundle.putString("src", "value");
                replaceFrag(new AddServiceFrag(), bundle, AdDetailsEditFrag.class.getName());
                break;

            case R.id.ad_details_edit_business_iv:

                bundle.putString("src", "value");
                replaceFrag(new BusinessDetailsFrag(), bundle, BusinessDetailsFrag.class.getName());
              /*  Fragment fragment = new BusinessDetailsFrag();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.parentcontainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                break;

            case R.id.ad_details_edit_staff_iv:
                bundle.putString("src", "value");
                replaceFrag(new AddStaffFrag(), bundle, AdDetailsEditFrag.class.getName());
                break;

            case R.id.ad_details_edit_resource_iv:

                break;

            case R.id.ad_details_edit_category_iv:

                bundle.putString("src", "value");
                replaceFrag(new AddCategoryFrag(), bundle, AdDetailsEditFrag.class.getName());

                break;

        }

    }


    @Override
    public void getResponse(String response) {

        Log.e("res", response);

        if (response.contains("Error :")) {
            snackbar = Snackbar.make(getView(), response, Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {

            try {
                //parse data
                final JSONObject main_obj = new JSONObject(response);

                Log.d("ADEF", "" + main_obj);
                Gson gson = new Gson();
                DetailsEditModel detailsEditModel = gson.fromJson(response, DetailsEditModel.class);

                if (detailsEditModel.getBusiness().get(0).getBusinessName() == null) {
                    mScrollView.setVisibility(View.GONE);
                    mNoDataTv.setVisibility(View.VISIBLE);

                } else {

                    mScrollView.setVisibility(View.VISIBLE);
                    mNoDataTv.setVisibility(View.GONE);

                    mBusinessNametv.setText(detailsEditModel.getBusiness().get(0).getBusinessName());
                    mBusinessAdd.setText(detailsEditModel.getBusiness().get(0).getBusinessAddress());
                    mPhone.setText(detailsEditModel.getBusiness().get(0).getBusinessPhone());
                    //  mCategory.setText(detailsEditModel.getBusiness().get(0));

                    JSONArray staff_array = main_obj.getJSONArray("staff");

                    // Staff staff = new Staff();
                    for (int i = 0; i < staff_array.length(); i++) {
                        JSONObject obj = staff_array.getJSONObject(i);

                        staffList.add(new Staff(obj.getString("staff_id"),
                                obj.getString("staff_name")

                        ));

                    }
                    mStaffRv.setHasFixedSize(true);
                    mShowStaffAdapter = new ShowStaffAdapter(this, staffList);
                    mStaffRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mStaffRv.setAdapter(mShowStaffAdapter);


                /* service with staff list*/

                    final JSONArray service_array = main_obj.getJSONArray("service");

                    for (int i = 0; i < service_array.length(); i++) {

                        JSONObject servic_staff_obj = service_array.getJSONObject(i);
                        listDataHeader.add(servic_staff_obj.getString("service_name"));

                        //  listDataChild.put(listDataHeader.get(i), staff_name_service);
                    }

                    mRecyclerView.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mAdapter = new ServiceStaffAdapter(getActivity(), listDataHeader/*, staff_name_service*/, new ServiceStaffAdapter.EditDetailsViewAdapter() {
                        @Override
                        public void onClick(int pos) {
                            try {
                                staff_name_service.clear();
                                JSONArray service_array = main_obj.getJSONArray("service");
                                JSONObject servic_staff_obj = service_array.getJSONObject(pos);
                                JSONArray staff_Jsonarray = servic_staff_obj.getJSONArray("servicestaff");
                                for (int j = 0; j < staff_Jsonarray.length(); j++) {
                                    JSONObject _obj = staff_Jsonarray.getJSONObject(j);
                                    staff_name_service.add(_obj.getString("staff_name"));
                                }
                                mChildRv.setHasFixedSize(true);
                                linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                mChildRv.setLayoutManager(linearLayoutManager);
                                mFaqChildAdapter = new FaqChildAdapter(getActivity(), staff_name_service);
                                mChildRv.setAdapter(mFaqChildAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);

                }
             /*   listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
                // setting list adapter
                expListView.setAdapter(listAdapter);
*/
             /*   for (int i=0; i<listDataHeader.size();i++){

                    Log.d("ADF","header" + listDataHeader.get(i));
                }
*/
                //   listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);
                // setting list adapter
                //   expListView.setAdapter(listAdapter);


                //  mPhone.setText(detailsEditModel.getBusiness().get(0).);

                // JSONArray arr = main_obj.getJSONArray("business");
                //   JSONObject jo = arr.getJSONObject(0);

             /*   mBusinessNametv.setText(jo.getString("business_name"));
                mBusinessAdd.setText(jo.getString("business_address"));
                mPhone.setText(jo.getString("business_phone"));

                String Mon = jo.getString("mon_from_time");
                String Tue = jo.getString("tue_from_time");
                String Wed = jo.getString("wed_from_time");
                String Thru = jo.getString("thru_from_time");
                String Fri = jo.getString("fri_from_time");
                String Sat = jo.getString("sat_from_time");
                String Sun = jo.getString("sun_from_time");

                if (!Mon.equals(null)) {
                    mDate.setText("Mon");
                } else {
                    mDate.setText("");
                }
                if (!Tue.equals(null)) {
                    mDate.append(" Tue");
                } else {
                    mDate.append("");
                }
                if (!Wed.equals(null)) {
                    mDate.append(" Wed");
                } else {
                    mDate.append("");
                }
                if (!Thru.equals(null)) {
                    mDate.append(" Thru");
                } else {
                    mDate.append("");
                }
                if (!Fri.equals(null)) {
                    mDate.append(" Fri");
                } else {
                    mDate.append("");
                }
                if (!Sat.equals(null)) {
                    mDate.append(" Sat");
                } else {
                    mDate.append("");
                }
                if (!Sun.equals(null)) {
                    mDate.append(" Sun");
                } else {
                    mDate.append("");
                }

                JSONArray arr1 = main_obj.getJSONArray("staff");
                Log.d("AEF", "" + arr1);
                ArrayList<Staff> staffList = new ArrayList<>();
                Staff staff = new Staff();
                JSONObject obj;
                for (int i = 0; i < arr1.length(); i++) {
                    obj = arr1.getJSONObject(i);
                    staff.setStaffId(obj.getString("staff_id"));
                    staff.setStaffName(obj.getString("staff_name"));

                }
                staffList.add(staff);

                JSONArray arrResource = main_obj.getJSONArray("resource");
                ArrayList<Resource> resourceList = new ArrayList<>();
                Resource resource = new Resource();
                JSONObject robj;
                for (int i = 0; i < arr1.length(); i++) {
                    robj = arrResource.getJSONObject(i);

                    // resource.setMessage(robj .getString("staff_id"));
                    //  resource.setStaffName(robj .getString("staff_name"));

                }
                //resourceList.add(staff);

                JSONArray arrService = main_obj.getJSONArray("service");
                ArrayList<Service> serviceList = new ArrayList<>();
                Service service = new Service();
                JSONObject sobj;
                Services taff servicestaff = new Servicestaff();
                for (int i = 0; i < arr1.length(); i++) {
                    sobj = arrResource.getJSONObject(i);

                    service.setServiceId(sobj.getString("service_id"));
                    service.setServiceName(sobj.getString("service_name"));
                    service.setServiceDuration(sobj.getString("service_duration"));
                    service.setServiceBuffertime(sobj.getString("service_buffertime"));
                    //  service.setServicestaff(sobj.getJSONArray("servicestaff"));
                    JSONArray serviceStafff = sobj.getJSONArray("servicestaff");
                *//*
                    for (int j=0;j>serviceStafff.length();j++){

                        JSONObject objectStaff =
                                servicestaff.setStaffId(serviceStafff.getString("staff_id"));
                        servicestaff.setStaffName(serviceStafff.getString("staff_name"));


                    }*//*


                    // resource.setMessage(robj .getString("staff_id"));
                    //  resource.setStaffName(robj .getString("staff_name"));

                }
                staffList.add(staff);
                // Log.d("ADF","staff list size"+staffList.get(0).getStaffName());
*/

            } catch (JSONException e) {
                snackbar = Snackbar.make(getView(), e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

    }
}
