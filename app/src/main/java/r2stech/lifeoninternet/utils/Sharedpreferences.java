package r2stech.lifeoninternet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;


public class Sharedpreferences {

    Context context;
    private SharedPreferences pref; //added private
    public static Editor editor;
    private int PRIVATE_MODE = 0;
    private static Sharedpreferences userData = null;

    // Shared Preferences file name

    private static final String PREF_NAME = "lifeoninternet.com";


    public static final String TAG_ADDRESS_ID = "addressid";
    public static final String TAG_BUSINESS_ID = "businessid";
    public static final String TAG_BUSNESS_ID = "business_id";
    public static final String TAG_CUST_ID = "cust_id";
    public static final String TAG_EMAIL_ID = "email";
    public static final String TAG_ADDRESS= "address";
    public static final String TAG_NAME_ID = "name";


    public Sharedpreferences(Context c) {
        try {
            this.context = c;
            pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Sharedpreferences getUserDataObj(Context c) {
        if (userData == null) {
            userData = new Sharedpreferences(c);
        }
        return userData;
    }

    public void clearAll(Context c) {
        this.context = c;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref.edit().clear().commit();
    }

    public String getAddressId() {
        return pref.getString(TAG_ADDRESS_ID, "");
    }

    public void setAddressId(String addressId) {
        try {
            editor.putString(TAG_ADDRESS_ID, addressId);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getMainAddress() {
        return pref.getString(TAG_ADDRESS, "");
    }

    public void setMainAddress(String mainAddress) {
        try {
            editor.putString(TAG_ADDRESS, mainAddress);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getCustId() {
        return pref.getString(TAG_CUST_ID, "");
    }

    public void setCustId(String custId) {
        try {
            editor.putString(TAG_CUST_ID, custId);
            editor.commit();
        } catch (Exception e) {
        }
    }
  public String getBusnessId() {
        return pref.getString(TAG_BUSNESS_ID, "");
    }

    public void setBusnessId(String busness_Id) {
        try {
            editor.putString(TAG_BUSNESS_ID, busness_Id);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getBusinessId() {
        return pref.getString(TAG_BUSINESS_ID, "");
    }

    public void setBusinessId(String businessId) {
        try {
            editor.putString(TAG_BUSINESS_ID, businessId);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getEmailId() {
        return pref.getString(TAG_EMAIL_ID, "");
    }

    public void setEmailId(String emailId) {
        try {
            editor.putString(TAG_EMAIL_ID, emailId);
            editor.commit();
        } catch (Exception e) {
        }
    }

    public String getName() {
        return pref.getString(TAG_NAME_ID, "");
    }

    public void setName(String emailId) {
        try {
            editor.putString(TAG_NAME_ID, emailId);
            editor.commit();
        } catch (Exception e) {
        }
    }


}

