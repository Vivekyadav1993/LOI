package atw.lifeoninternet.async;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Vivek on 22/03/18.
 */
public interface OnRequestListener {
    void onRequestCompletion(int pid, JSONObject responseJson, JSONArray responseArray);

    void onRequestCompletion(int pid, String responseJson) throws JSONException;

    void onRequestCompletionError(int pid, String error);

    void onRequestCompletionHomeError(int pid, String error);
}
