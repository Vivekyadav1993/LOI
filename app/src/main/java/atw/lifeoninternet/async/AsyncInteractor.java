package atw.lifeoninternet.async;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import atw.lifeoninternet.AppController;
import atw.lifeoninternet.utils.LogToastUtility;
import atw.lifeoninternet.utils.Utils;


/**
 * Created by Vivek on 3 August 2017.
 */
public class AsyncInteractor implements IAsyncInteractor {
    /******************************************************************************************
     * An Interactor helps models cross application boundaries such as networks or serialization
     * This LoginInteractor knows nothing about a UI or the LoginPresenter
     * Because this is an asynchronous call it will call back on the OnRequestListener when complete
     * ******************************************************************************************
     */
    Context context;

    int count = 0;

    String TAG = "ApiCall";

    //   Sharedpreferences mPrefs = Sharedpreferences.getUserDataObj(AppController.getInstance().getApplicationContext());

    //get
    public void validateCredentialsAsync(String method, OnRequestListener listener, final int pid, final
    String url) {
        if (method.equals("GET"))
            onGetMethodServerCall(listener, pid, url);
        else if (method.equals("DELETE"))
            onDeleteMethodServerCall(listener, pid, url);
    }

    //post
    public void validateCredentialsAsync(String method, OnRequestListener listener, final int pid, final
    String url, final Map<String, String> paramsMap) {
        if (method.equals("POST"))
            onPostMethodServerCall(listener, pid, url, paramsMap);
        else if (method.equals("PUT"))
            onPutMethodServerCall(listener, pid, url, paramsMap);
    }

    /**
     * GET Method API Calls
     */

    public void onGetMethodServerCall(final OnRequestListener listener, final int pid, String url) {

        LogToastUtility.LI(TAG, "GetUrl:" + pid + ":" + url);
        AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogToastUtility.LI(TAG, "GetUrl:" + pid + ":success:" + response.toString());
                        try {
                            listener.onRequestCompletion(pid, response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                          //  Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
                            LogToastUtility.LI(TAG, "GetUrl:" + pid + ":error:" + error.toString());
                            listener.onRequestCompletionError(pid, error.toString());

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                //  header.put("Content-Type", "application/json; charset=UTF-8");
                //  header.put("Authorization", mPrefs.getAuthToken());
                return header;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    /**
     * DELETE Method API Calls
     */

    public void onDeleteMethodServerCall(final OnRequestListener listener, final int pid, String url) {

        LogToastUtility.LI(TAG, "GetUrl:" + pid + ":" + url);
        AppController.getInstance().getRequestQueue().getCache().invalidate(url, true);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogToastUtility.LI(TAG, "GetUrl:" + pid + ":success:" + response.toString());
                        try {
                            listener.onRequestCompletion(pid, response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogToastUtility.LI(TAG, "GetUrl:" + pid + ":error:" + error.toString());
                        listener.onRequestCompletionError(pid, error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                //   header.put("Content-Type", "application/json; charset=UTF-8");
                // header.put("Authorization", mPrefs.getAuthToken());
                return header;
            }
        };
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    /**
     * POST Method API Calls
     */

    public void onPostMethodServerCall(final OnRequestListener listener, final int pid,
                                       String url, final Map<String, String> stringMap) {

        LogToastUtility.LI(TAG, "PostUrl:" + pid + ":" + url);
        LogToastUtility.LI(TAG, "PostUrl:" + pid + ":data:" + stringMap.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            LogToastUtility.LI(TAG, "PostUrl:" + pid + ":success:" + response.toString());
                            try {
                                listener.onRequestCompletion(pid, response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            LogToastUtility.LI(TAG, "PostUrl:" + pid + ":success:" + error.toString());
                            VolleyLog.e("Error: ", error.getMessage());
                            String body;
                            //get response body and parse with appropriate encoding
                            if (error.networkResponse.data != null) {
                                try {
                                    body = new String(error.networkResponse.data, "UTF-8");
                                    LogToastUtility.LI(TAG, "PostUrl:" + pid + ":errorbody:" + body.toString());
                                    listener.onRequestCompletionError(pid, body);
                                } catch (UnsupportedEncodingException e) {
                                    error.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                //  header.put("Authorization", mPrefs.getAuthToken());
                return header;
            }
        };


        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    /**
     * PUT Method API Calls
     */

    public void onPutMethodServerCall(final OnRequestListener listener, final int pid,
                                      String url, final Map<String, String> stringMap) {

        LogToastUtility.LI(TAG, "PostUrl:" + pid + ":" + url);
        LogToastUtility.LI(TAG, "PostUrl:" + pid + ":data:" + stringMap.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            LogToastUtility.LI(TAG, "PostUrl:" + pid + ":success:" + response.toString());
                            try {
                                listener.onRequestCompletion(pid, response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            LogToastUtility.LI(TAG, "PostUrl:" + pid + ":success:" + error.toString());
                            VolleyLog.e("Error: ", error.getMessage());
                            String body;
                            //get response body and parse with appropriate encoding
                            if (error.networkResponse.data != null) {
                                try {
                                    body = new String(error.networkResponse.data, "UTF-8");
                                    LogToastUtility.LI(TAG, "PostUrl:" + pid + ":errorbody:" + body.toString());
                                    listener.onRequestCompletionError(pid, body);
                                } catch (UnsupportedEncodingException e) {
                                    error.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return stringMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                //   header.put("Authorization", mPrefs.getAuthToken());
                return header;
            }
        };


        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}