package r2stech.lifeoninternet.interfaces;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import frags.AdDetailsEditFrag;
import models.addetailsEdit.DetailsEditModel;
import r2stech.lifeoninternet.async.AsyncInteractor;
import r2stech.lifeoninternet.async.OnRequestListener;
import r2stech.lifeoninternet.utils.AppConstants;
import r2stech.lifeoninternet.utils.NetworkStatus;
import r2stech.lifeoninternet.utils.Utils;

/**
 * Created by Vivek on 3/26/2018.
 */

public class AddDetailsEditPresenterImpl implements IAddDetailsPresenter, OnRequestListener {

    private IAddDetailsView mIAddDetailsView;
    private AdDetailsEditFrag mAdDetailsEditFrag;
    private AsyncInteractor mAsyncInteractor;
    private DetailsEditModel mDetailsEditModel;


    public AddDetailsEditPresenterImpl(IAddDetailsView mIAddDetailsView) {
        this.mIAddDetailsView = mIAddDetailsView;
        this.mAdDetailsEditFrag = (AdDetailsEditFrag) mIAddDetailsView;
        mAsyncInteractor = new AsyncInteractor();
    }


    @Override
    public void showDetails(String action, String business_id, String address_id) {

        if (NetworkStatus.checkNetworkStatus(mAdDetailsEditFrag.getActivity())) {
          //  Utils.showProgress(this);
            Map<String, String> params = new HashMap<String, String>();
            params.put("action", action);
            params.put("business_id", business_id);
            params.put("address_id", address_id);

            Log.d("params", "is" + params.toString());

            mAsyncInteractor.validateCredentialsAsync(Utils.methodPost,this, AppConstants.TAG_ID_SHOW_DETAILS,
                    AppConstants.URL.SHOW_DETAILS.getUrl(), params);
        } else {
            Utils.showToast(mAdDetailsEditFrag.getActivity(), "Please connect to internet");
        }


    }

    @Override
    public void onRequestCompletion(int pid, JSONObject responseJson, JSONArray responseArray) {

    }

    @Override
    public void onRequestCompletion(int pid, String responseJson) throws JSONException {

        if (pid == AppConstants.TAG_ID_SHOW_DETAILS) {
            if (responseJson != null) {

                Gson gson = new Gson();
                mDetailsEditModel= gson.fromJson(responseJson, DetailsEditModel.class);
                mIAddDetailsView.addDetailsSuccess(pid, mDetailsEditModel);
            }
        }

    }

    @Override
    public void onRequestCompletionError(int pid, String error) {

        if (pid == AppConstants.TAG_ID_SHOW_DETAILS) {
            mIAddDetailsView.addDetailsError(pid, error);
        }

    }

    @Override
    public void onRequestCompletionHomeError(int pid, String error) {

    }
}
