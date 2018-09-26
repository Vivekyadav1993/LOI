package atw.lifeoninternet.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by lenovo on 9/1/2018.
 */

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
//Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//Displaying token on logcat
        Log.d(TAG, "Refreshed token:" + refreshedToken);
    }
}
