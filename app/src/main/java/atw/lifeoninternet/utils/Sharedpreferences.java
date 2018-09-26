package atw.lifeoninternet.utils;

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
    public static final String TAG_ADDRESS_ID_1 = "addressid1";
    public static final String TAG_ADDRESS_ID_2 = "addressid2";
    public static final String TAG_BUSINESS_ID = "businessid";
    public static final String TAG_BUSNESS_ID = "business_id";
    public static final String TAG_CUST_ID = "cust_id";
    public static final String TAG_EMAIL_ID = "email";
    public static final String TAG_STAFF_ID = "staffId";
    public static final String TAG_STAFF_ID_1 = "staffId1";
    public static final String TAG_STAFF_ID_2 = "staffId2";
    public static final String TAG_USER_ID = "userid";
    public static final String TAG_STAFF_ADMIN= "staffAdmin";
    public static final String TAG_LAT = "addressLat";
    public static final String TAG_LONG = "addressLong";
    public static final String TAG_BUSINESS_LAT = "businessaddressLat";
    public static final String TAG_BUSINESS_LONG = "businessaddressLong";
    public static final String TAG_OTP_NUMBER= "otpnumber";
    public static final String TAG_NO_OF_DAYS = "noofdays";
    public static final String TAG_USER_MOBILE = "mobile";
    public static final String TAG_AD_TYPE= "adtype";
    public static final String TAG_BUSINESS_NAME= "businessname";
    public static final String TAG_BOOKING_ID = "booking_id";
    public static final String TAG_SELECT_ADDRESS_ID = "selectaddress";
    public static final String TAG_SELECT_BUSINESS_ID = "selectbusiiness";
    public static final String TAG_ADDRESS= "address";
    public static final String TAG_NAME_ID = "name";
    public static final String TAG_INDUSTRY_TYPE= "industryType";
    public static final String TAG_USER_LOGGED_IN = "userloggedinstatus";


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

    public String getIndustryType() {
        return pref.getString(TAG_INDUSTRY_TYPE, "");
    }

    public void setIndustryType(String industryType) {
        try {
            editor.putString(TAG_INDUSTRY_TYPE, industryType);
            editor.commit();
        } catch (Exception e) {
        }
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
    public String getAddressId1() {
        return pref.getString(TAG_ADDRESS_ID_1, "");
    }

    public void setAddressId1(String addressId1) {
        try {
            editor.putString(TAG_ADDRESS_ID_1, addressId1);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getAddressId2() {
        return pref.getString(TAG_ADDRESS_ID_2, "");
    }

    public void setAddressId2(String addressId2) {
        try {
            editor.putString(TAG_ADDRESS_ID_2, addressId2);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getAdType() {
        return pref.getString(TAG_AD_TYPE, "");
    }

    public void setAdType(String adtype) {
        try {
            editor.putString(TAG_AD_TYPE, adtype);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getLat() {
        return pref.getString(TAG_LAT, "");
    }

    public void setLat(String addressLat) {
        try {
            editor.putString(TAG_LAT, addressLat);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getBusinessLat() {
        return pref.getString(TAG_BUSINESS_LAT, "");
    }

    public void setBusinessLat(String businessaddressLat) {
        try {
            editor.putString(TAG_BUSINESS_LAT, businessaddressLat);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getBusinessLong() {
        return pref.getString(TAG_BUSINESS_LONG, "");
    }

    public void setBusinessLong(String businessaddressLong) {
        try {
            editor.putString(TAG_BUSINESS_LONG, businessaddressLong);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getOtpNumber() {
        return pref.getString(TAG_OTP_NUMBER, "");
    }

    public void setOtpNumber(String otpnumber) {
        try {
            editor.putString(TAG_OTP_NUMBER, otpnumber);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getNoFoDays() {
        return pref.getString(TAG_NO_OF_DAYS, "");
    }

    public void setNoOfDays(String noofdays) {
        try {
            editor.putString(TAG_NO_OF_DAYS, noofdays);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getLong() {
        return pref.getString(TAG_LONG, "");
    }

    public void setLong(String addressLong) {
        try {
            editor.putString(TAG_LONG, addressLong);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getBusnessName() {
        return pref.getString(TAG_BUSINESS_NAME, "");
    }

    public void setBusnessName(String businessname) {
        try {
            editor.putString(TAG_BUSINESS_NAME, businessname);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getBookingId() {
        return pref.getString(TAG_BOOKING_ID, "");
    }

    public void setBooingId(String bookingId) {
        try {
            editor.putString(TAG_BOOKING_ID, bookingId);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getSelectAddressId() {
        return pref.getString(TAG_SELECT_ADDRESS_ID, "");
    }

    public void setselecttAddressId(String addressselectId) {
        try {
            editor.putString(TAG_SELECT_ADDRESS_ID, addressselectId);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getSelecttBusinessId() {
        return pref.getString(TAG_SELECT_BUSINESS_ID, "");
    }

    public void setSelecttBusinessId(String businessSelectId) {
        try {
            editor.putString(TAG_SELECT_BUSINESS_ID, businessSelectId);
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
    public String getStaffId() {
        return pref.getString(TAG_STAFF_ID, "");
    }

    public void setStaffId(String staffId) {
        try {
            editor.putString(TAG_STAFF_ID, staffId);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getStaffId1() {
        return pref.getString(TAG_STAFF_ID_1, "");
    }

    public void setStaffId1(String staffId1) {
        try {
            editor.putString(TAG_STAFF_ID_1, staffId1);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getStaffId2() {
        return pref.getString(TAG_STAFF_ID_2, "");
    }

    public void setStaffId2(String staffId2) {
        try {
            editor.putString(TAG_STAFF_ID_2, staffId2);
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
    public String getStaffAdmin() {
        return pref.getString(TAG_STAFF_ADMIN, "");
    }

    public void setStaffAdmin(String staffAdmin) {
        try {
            editor.putString(TAG_STAFF_ADMIN, staffAdmin);
            editor.commit();
        } catch (Exception e) {
        }
    }
    public String getUserId() {
        return pref.getString(TAG_USER_ID, "");
    }

    public void setUserId(String userId) {
        try {
            editor.putString(TAG_USER_ID,userId);
            editor.commit();
        } catch (Exception e) {
        }
    }
  public String getMobile() {
        return pref.getString(TAG_USER_MOBILE, "");
    }

    public void setMobile(String mobile) {
        try {
            editor.putString(TAG_USER_MOBILE,mobile);
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

    /*
      *  Loggedin username
      **/
    public Boolean getIsUserLoggedIn() {
        return pref.getBoolean(TAG_USER_LOGGED_IN, false);
    }

    public void setIsUserLoggedIn(Boolean status) {
        try {
            editor.putBoolean(TAG_USER_LOGGED_IN, status);
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

