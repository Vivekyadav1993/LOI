package atw.lifeoninternet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daasuu.bl.BubbleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import frags.AdDetailsEditFrag;
import frags.AdPublishSelectionFrag;
import frags.BusinessDetailsFrag;
import frags.CustomerLandingFrag;
import frags.MyAdsAddressFrag;
import frags.MyAdsFrag;
import frags.MyAppointmentFrag;
import frags.MyHistoryFrag;
import frags.Search_location_frag;
import helper.AppConstants;
import helper.AppUtils;
import helper.HelperActivity;
import helper.HttpresponseUpd;
import helper.LocationUpd;
import models.BusinessData;
import models.BusinessHourData;
import models.CategoryData;
import models.PackageConfigData;
import models.ResourceData;
import models.ServiceData;
import models.StaffData;


public class LandingActivity extends HelperActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationUpd, HttpresponseUpd {


    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private Bundle bundle;

    private LocationUpd location_upd;

    private HttpresponseUpd http_callback;

    private SharedPreferences.Editor editor;

    TextView toolbar_title;

    @BindView(R.id.header_search_layout)
    RelativeLayout header_search_layout;


    @BindView(R.id.header_search_input)
    EditText header_search_input;

    @BindView(R.id.header_search_btn)
    ImageView header_search_btn;

    TextView header_name, header_email, header_mbole;


    public static BusinessData business_data;
    public static int business_array_pos = 0;
    public static ArrayList<StaffData> staff_data_array;
    public static ArrayList<ResourceData> res_data_array;
    public static ArrayList<ServiceData> service_data_array;
    public static ArrayList<PackageConfigData> ser_package_array;
    public static boolean staff = false, res = false;

    public static String staffwise_booking_switch = "", advnoof_day_switch = "",
            packageconfig_switch = "", status_switch = "",

    ingroup_switch = "", sync_switch = "", ajust_radiobtn_value = "";

    private Sharedpreferences mPrefs;

    // ActionBarDrawerToggle
    ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout drawer;
    private LinearLayout header_ll;
    private boolean click = false;
    public String status_login;
    NavigationView navigationView;
    private String post_tag, lat, longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        location_upd = this;
        http_callback = this;


        ButterKnife.bind(this);
        mPrefs = Sharedpreferences.getUserDataObj(this);
        status_login = getIntent().getStringExtra("src");

        Log.d("LandingActivity", "login status" + status_login);
        //initialize share preference
        AppConstants.app_data = getSharedPreferences("AppData", MODE_PRIVATE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //  getSupportActionBar().setLogo(R.drawable.loc_headericn);

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        toolbar_title.setText("  " + AppConstants.app_data.getString("short_add", "Life On Internet"));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // Update navigation header text
                updateNavigationViewHeader();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
      /*  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
     */
        drawer.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header_ll = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.header_ll);
        header_name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_name);
        header_email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_email);
        header_mbole = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_mobile);

        //  header_name.setText(mPrefs.getName());
        // header_name.setText(mPrefs.getEmailId());
        header_name.setText(AppConstants.app_data.getString("name", "n/a"));
        header_email.setText(AppConstants.app_data.getString("email", "n/a"));
        header_mbole.setText(mPrefs.getMobile());

        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.parentcontainer, new Search_location_frag()).commit();


            }
        });
        header_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(LandingActivity.this, UpdateProfile.class);
                intent.putExtra("name", header_name.getText());
                intent.putExtra("email", header_email.getText());
                startActivity(intent);
            }
        });


        header_search_layout.setTag(0);

        header_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                header_search_layout.setVisibility(View.GONE);
                header_search_layout.setTag(0);

            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment frag1 = new CustomerLandingFrag();
       /* Bundle bundle = new Bundle();
        frag.setArguments(bundle);*/

        fragmentTransaction.add(R.id.parentcontainer, frag1)/*.addToBackStack(LandingActivity.class.getName())*/.commit();

        bundle = getIntent().getExtras();

        Log.d("LA", "StaffId" + mPrefs.getStaffId());
        if (mPrefs.getStaffId().equalsIgnoreCase("")) {

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_create_ads).setVisible(true);
            nav_Menu.findItem(R.id.nav_edit_ads).setVisible(true);


        } else {
            hideItem();
        }

        if (bundle.getString("src").equals("def")) {
            // def means current location dialog appear as well as get data by its lat long
            Log.e("erc0", bundle.getString("src"));
            getLocConfirmDialog();

        } else if (bundle.getString("src").equals("stafflogin")) {
            // def means current location dialog appear as well as get data by its lat long
            Log.e("erc110", bundle.getString("src"));
            getLocConfirmDialog();


        } else if (bundle.getString("src").equals("deff")) {
            // deff means previous saved location but no dialog appear as well as get data by its lat long
            if (AppConstants.app_data.getString("lat", "").equals("")) {
                // system does not has lat long , calculate lat long
                calculateLatLongi("http://maps.google.com/maps/api/geocode/json?address=" + AppConstants.app_data.getString("address", "").replaceAll(" ", "%20") + "&sensor=false", location_upd);
            } else {
                // system has lat long , get data by saved lat long
            }

        } else if (bundle.getString("src").equals("search_current")) {
            // search_current means current location but no dialog appear as well as get data by its lat long
            triggerLocation(location_upd);
        } else if (bundle.getString("src").equals("search_save")) {
            // search_save means desired saved location but no dialog appear as well as get data by its lat long
            // system does not has lat long , calculate lat long

        } else if (bundle.getString("src").equals("create_event")) {

            try {
                business_data = new BusinessData();
                JSONObject main_obj = new JSONObject(bundle.getString("data"));
                JSONArray output_array = main_obj.getJSONArray("output");
                JSONObject obj = output_array.getJSONObject(0);
                business_data.setBusiness_id(obj.getString("business_id"));
                business_data.setPublish_type(obj.getString("publish_type"));
                business_data.setPublish_id(obj.getString("publish_id"));
                business_data.setBusiness_name(obj.getString("business_name"));
                business_data.setBusiness_phone(obj.getString("business_phone"));
                business_data.setBusiness_industry_id(obj.getString("industry_id"));

                JSONArray address_array = obj.getJSONArray("businessaddress");

                for (int i = 0; i < address_array.length(); i++) {
                    JSONObject obj2 = address_array.getJSONObject(i);

                    business_data.getAdderess_data().add(new BusinessHourData(obj2.getString("address"), obj2.getString("address_id")
                            , obj2.getString("start_date"), obj2.getString("end_date")
                            , obj2.getString("mon_from_time"), obj2.getString("mon_to_time")
                            , obj2.getString("tue_from_time"), obj2.getString("tue_to_time")
                            , obj2.getString("wed_from_time"), obj2.getString("wed_to_time")
                            , obj2.getString("thru_from_time"), obj2.getString("thru_to_time")
                            , obj2.getString("fri_from_time"), obj2.getString("fri_to_time")
                            , obj2.getString("sat_from_time"), obj2.getString("sat_to_time")
                            , obj2.getString("sun_from_time"), obj2.getString("sun_to_time")
                    ));
                }


                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                Fragment frag = new BusinessDetailsFrag();
                Bundle bundle = new Bundle();

                bundle.putString("src", "create");
                frag.setArguments(bundle);

                fragmentTransaction.add(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();

            } catch (JSONException e) {
                Toast.makeText(this, "Error occurred! " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void hideItem() {

        Log.d("LA", "Staff_status" + mPrefs.getStaffAdmin());
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        if (mPrefs.getStaffAdmin().equalsIgnoreCase("Yes")) {
            nav_Menu.findItem(R.id.nav_create_ads).setVisible(true);
            nav_Menu.findItem(R.id.nav_edit_ads).setVisible(true);
        } else {
            nav_Menu.findItem(R.id.nav_create_ads).setVisible(false);
            nav_Menu.findItem(R.id.nav_edit_ads).setVisible(false);
        }


    }

    private void updateNavigationViewHeader() {

        header_name.setText(AppConstants.app_data.getString("name", "n/a"));
        header_email.setText(AppConstants.app_data.getString("email", "n/a"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* public static void geocodeAddress(String addressStr, Geocoder gc) {
        Address address = null;
        List<Address> addressList = null;
        try {
            if (!TextUtils.isEmpty(addressStr)) {
                addressList = gc.getFromLocationName(addressStr, 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != addressList && addressList.size() > 0) {
            address = addressList.get(0);
        }
        if (null != address && address.hasLatitude()
                && address.hasLongitude()) {
            latitude = address.getLatitude();
            longitude = address.getLongitude();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

   /*     //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {



          *//*  fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = new CustomerLandingFrag();
            Bundle bundle = new Bundle();
            bundle.putString("comming", "showsearch");
            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();
*//*
        } else */if (id == R.id.action_home) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            Fragment frag = new CustomerLandingFrag();
            Bundle bundle = new Bundle();
            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_create_ads) {

            // Handle the camera action
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = new AdPublishSelectionFrag();
            Bundle bundle = new Bundle();

            bundle.putString("src", "def");
            frag.setArguments(bundle);

            fragmentTransaction.add(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();
        } else if (id == R.id.nav_logout) {

            mPrefs.setIsUserLoggedIn(false);

            editor = AppConstants.app_data.edit();
            //  editor.putString("user_id", "");
            editor.remove("user_id");
            editor.remove("name");
            editor.remove("email");
            editor.remove("phone");
            editor.clear();

            mPrefs.clearAll(this);

            startActivity(new Intent(LandingActivity.this, UserAuthACtivity.class));
            finish();
        } else if (id == R.id.nav_my_ads) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = new MyAdsAddressFrag();
            Bundle bundle = new Bundle();
            bundle.putString("commingfrom", "myads");
            frag.setArguments(bundle);

            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();


        } else if (id == R.id.nav_my_cust_list) {


            if (mPrefs.getBusnessId() == null || mPrefs.getBusnessId().equals("")) {
                Toast.makeText(this, "Create Business First", Toast.LENGTH_SHORT).show();
            } else {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                Fragment frag = new MyAdsAddressFrag();
                Bundle bundle = new Bundle();
                bundle.putString("commingfrom", "editads");
                frag.setArguments(bundle);
                fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();

            }

        } else if (id == R.id.nav_edit_ads) {

            try {
                if (mPrefs.getBusnessId() == null || mPrefs.getBusnessId().equals("")) {
                    Toast.makeText(this, "Create Ads First", Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("LandingActivity", "business_id:" + mPrefs.getBusnessId());

                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment frag = new AdDetailsEditFrag();
                    Bundle bundle = new Bundle();
                    frag.setArguments(bundle);
                    fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "First Create Ads", Toast.LENGTH_SHORT).show();
                // Log.d("LA","Error"+e);
            }

        } else if (id == R.id.nav_my_appointment) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = new MyAppointmentFrag();
            Bundle bundle = new Bundle();
            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();

        }/* else if (id == R.id.nav_booking_history) {

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment frag = new MyHistoryFrag();
            Bundle bundle = new Bundle();
            fragmentTransaction.replace(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();
        }*/
        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void getLoc(Location loc) {
        if (loc == null) {

            Toast.makeText(this, "user location couldn't fetched!!!", Toast.LENGTH_SHORT).show();

        } else {

            lat = String.valueOf(loc.getLatitude());
            longi = String.valueOf(loc.getLongitude());

            mPrefs.setLat(lat);
            mPrefs.setLong(longi);

            Log.d("Lat", "Lat" + lat + "long" + longi);
            // save current location
            editor = AppConstants.app_data.edit();
            editor.putString("lat", loc.getLatitude() + "");
            editor.putString("long", loc.getLongitude() + "");
            editor.commit();

            if (AppConstants.app_data.getString("address", "").equals("")) {
                AppUtils.getAdd("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + loc.getLatitude() + "," + loc.getLongitude() + "&sensor=true", this, http_callback);
            } else {
                // get data by saved lat long


            }

        }

    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);

        if (response.contains("error")) {
            Toast.makeText(this, response + " occurred!!!", Toast.LENGTH_SHORT).show();
        } else if (post_tag.equalsIgnoreCase("sendLatLong")) {
            try {
                JSONObject obj = new JSONObject(response);
                String message = obj.getString("message");
                // Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            String[] address_array = response.split(",");
            String short_address = "";


            if (address_array.length == 0) {

            } else {
                short_address = address_array[0];
            }


            // save current address
            editor = AppConstants.app_data.edit();
            editor.putString("address", response);
            editor.putString("short_add", short_address);
            editor.commit();

            getSupportActionBar().setTitle("  " + AppConstants.app_data.getString("short_add", "Life On Internet"));

            // get data from saved lat long

        }


    }

    public void getLocConfirmDialog() {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.location_confirmation_dialog_screen, null);


        final PopupWindow messageInputWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        messageInputWindow.setContentView(layout);
        messageInputWindow.setFocusable(true);
        messageInputWindow.setBackgroundDrawable(new BitmapDrawable());
        messageInputWindow.setOutsideTouchable(true);


        TextView adderess = (TextView) layout.findViewById(R.id.loc_confirm_add);
        TextView confirm = (TextView) layout.findViewById(R.id.loc_confirm_done);
        TextView change = (TextView) layout.findViewById(R.id.loc_confirm_changeloc_btn);


        adderess.setText(AppConstants.app_data.getString("address", ""));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageInputWindow.dismiss();

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("lifeoninternet.com")
                        .appendPath(Utils.stringBuilder())
                        .appendPath("api.php")
                        .appendQueryParameter("action", "gpsAtPermise")
                        .appendQueryParameter("booking_id", mPrefs.getBookingId())
                        .appendQueryParameter("lat", mPrefs.getLat())
                        .appendQueryParameter("longi", mPrefs.getLong());

                Log.e("stafflist", builder.build().toString());
                if (AppUtils.isNetworkAvailable(LandingActivity.this)) {
                    post_tag = "sendLatLong";
                    AppUtils.getStringData(builder.build().toString(), LandingActivity.this, http_callback);
                } else {
                   /* snackbar = Snackbar.make(getView(), "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Snackbar.LENGTH_LONG);
                    snackbar.show();
             */
                    Toast.makeText(LandingActivity.this, "Life On Internet couldn't run without Internet!!! Kindly Switch On your Network Data.", Toast.LENGTH_SHORT).show();
                }

                // get data from saved lat long
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageInputWindow.dismiss();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.parentcontainer, new Search_location_frag()).commit();


            }
        });


        try {

            findViewById(R.id.parent_cordinate).post(new Runnable() {
                public void run() {
                    messageInputWindow.showAtLocation(findViewById(R.id.parent_cordinate), Gravity.CENTER, 0, 0);
                }
            });
            Log.e("erc1", bundle.getString("src"));


        } catch (WindowManager.BadTokenException e) {
            Log.e("erc2", e.toString());

        }
    }


}
