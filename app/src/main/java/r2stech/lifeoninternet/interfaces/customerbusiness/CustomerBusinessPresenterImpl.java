package r2stech.lifeoninternet.interfaces.customerbusiness;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import frags.CustomerLandingFrag;
import models.businesslist.BusinessListModel;
import r2stech.lifeoninternet.async.AsyncInteractor;
import r2stech.lifeoninternet.async.OnRequestListener;
import r2stech.lifeoninternet.utils.AppConstants;
import r2stech.lifeoninternet.utils.Utils;


/**
 * Created by Vivek on 3/26/2018.
 */

public class CustomerBusinessPresenterImpl implements ICustomerBusinessPresenter, OnRequestListener {

    private ICustomerBusinessView mCustomerBusinessView;
    private CustomerLandingFrag mCustomerLandingFrag;
    private AsyncInteractor mAsyncInteractor;
    private BusinessListModel mBusinessListModel;

    public CustomerBusinessPresenterImpl(ICustomerBusinessView mCustomerBusinessView) {
        this.mCustomerBusinessView = mCustomerBusinessView;
        this.mCustomerLandingFrag = (CustomerLandingFrag) mCustomerBusinessView;
        mAsyncInteractor = new AsyncInteractor();
    }

    @Override
    public void getCustomerBusinessLsit() {

        Utils.showProgress(mCustomerLandingFrag.getActivity());
        mAsyncInteractor.validateCredentialsAsync(Utils.methodGet, this, AppConstants.TAG_ID_CUSTOME_BUSINESS,
                AppConstants.URL.CUSTOMER_BUSINESS.getUrl());

    }

    @Override
    public void onRequestCompletion(int pid, JSONObject responseJson, JSONArray responseArray) {

    }

    @Override
    public void onRequestCompletion(int pid, String responseJson) throws JSONException {

        if (pid == AppConstants.TAG_ID_CUSTOME_BUSINESS) {
            if (responseJson != null) {
              //  Gson gson = new Gson();

                Gson gson = new Gson();
                Log.d("CBP", "1" + responseJson);
                mBusinessListModel = gson.fromJson(responseJson, BusinessListModel.class);
                Log.d("CBP", "22" );
                //  Log.d("CBP", "2" +gson.fromJson(responseJson,BusinessListModel.class));
                mCustomerBusinessView.onCustomerBusinessSuccess(pid, mBusinessListModel);
                Log.d("CBP", "3" + mBusinessListModel.getMessage());
            }
        }

    }

    @Override
    public void onRequestCompletionError(int pid, String error) {

        try {
            if (pid == AppConstants.TAG_ID_CUSTOME_BUSINESS) {
                mCustomerBusinessView.onRecruiterMasterdataError(pid, mBusinessListModel.getMessage().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestCompletionHomeError(int pid, String error) {

    }
}
