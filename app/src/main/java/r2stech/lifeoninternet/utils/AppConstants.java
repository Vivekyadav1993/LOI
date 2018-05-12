package r2stech.lifeoninternet.utils;


import java.util.List;

public class AppConstants {


    public static int SUCCESS_CODE = 200;

    public static int TAG_ID_CUSTOMER= 101;
    public static int TAG_ID_SHOW_DETAILS= 102;
    public static int TAG_ID_CUSTOME_BUSINESS = 103;


    public static String serverUrl = "http://lifeoninternet.com/";
    public static String termsUrl = "http://lifeoninternet.com/";
    public static boolean IS_LIVE_BUILD = false;


    private static String BASE_URL;
    public static String WEBSERVICE_HOST;


    static {
        if (IS_LIVE_BUILD) {
            WEBSERVICE_HOST = "http://lifeoninternet.com/";
        } else {
            WEBSERVICE_HOST = "http://lifeoninternet.com/";

        }
        BASE_URL = WEBSERVICE_HOST;
    }

    /*
     * SERVER CALLS


    /*
     *  Enum to get URL and TAG names
     */
    public enum URL {

        SHOW_DETAILS("/"+Utils.stringBuilder()+"/api.php?"),
        CUSTOMER_BUSINESS("/"+Utils.stringBuilder()+"/api.php?action=businessList");
        private String url;

        public String baseUrl = "http://lifeoninternet.com";


        URL(String url) {

            this.url = baseUrl + url;
        }

        public String getUrl() {
            return url;
        }
    }


    public enum TAGNAME {

        EMAIL("email"), PASSWORD("password"), UID("uid"), TAGS("tags"), DESCRIPTION("description"), SRC
                ("src"), MOBILE("mobile"), KEY("key"), DATA
                ("data"), STATUS("status"), USERID("userId"), DEVICEID("deviceId"), MESSAGE("message"), CMD
                ("cmd"), DEVICE_ID_GCM("deviceid"), FILES("files"),
        LOCATION("location"), LATITUDE("latitude"), LONGITUDE("longitude"), GCMTOKEN("gcmtoken"), TOKEN
                ("token"), FOLLOWERPHONENO("follwerPhoneno"),;

        private String value;
        TAGNAME(String tag) {
            this.value = tag;
        }

        public String getValue() {
            return value;
        }
    }
   /* ---------------------*/
}
