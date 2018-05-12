package helper;

import android.content.SharedPreferences;

/**
 * Created by teknik on 9/21/2017.
 */

public class AppConstants {

    public static SharedPreferences app_data;

    public static String Google_place_API_base_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";

    public static String Google_place_APi_Key = "AIzaSyCgsDKYLDAqZpsbfvoxE8ZjxP7a8x4f0b8";

    public static final int LOCATION_INTERVAL = 10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;
}
