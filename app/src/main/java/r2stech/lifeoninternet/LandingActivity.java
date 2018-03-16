package r2stech.lifeoninternet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import frags.AdPublishSelectionFrag;
import frags.BusinessDetailsFrag;
import frags.CustomerLandingFrag;
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
        implements NavigationView.OnNavigationItemSelectedListener , LocationUpd , HttpresponseUpd{


    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private Bundle bundle;

    private  LocationUpd location_upd;

    private HttpresponseUpd http_callback;

    private SharedPreferences.Editor editor;

    TextView toolbar_title ;

    @BindView(R.id.header_search_layout)
    RelativeLayout header_search_layout;

    @BindView(R.id.header_search_input)
    EditText header_search_input;

    @BindView(R.id.header_search_btn)
    ImageView header_search_btn;


    TextView header_name ,header_email;


    public static BusinessData business_data ;

    public static int business_array_pos = 0 ;

    public static  ArrayList<StaffData> staff_data_array ;

    public static  ArrayList<ResourceData> res_data_array ;

    public  static  ArrayList<ServiceData> service_data_array ;

    public static ArrayList<PackageConfigData> ser_package_array ;



    public static boolean staff = false , res= false ;



    public static String staffwise_booking_switch = "", advnoof_day_switch = "",
            packageconfig_switch = "", status_switch = "",

    ingroup_switch = "",  sync_switch = "", ajust_radiobtn_value = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        location_upd = this;

        http_callback =  this;

        ButterKnife.bind(this);

        //initialize share preference
        AppConstants.app_data =getSharedPreferences("AppData", MODE_PRIVATE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  getSupportActionBar().setLogo(R.drawable.loc_headericn);

        toolbar_title = (TextView)findViewById(R.id.toolbar_title);

        toolbar_title.setText("  "+AppConstants.app_data.getString("short_add","Life On Internet"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header_name = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_name);
        header_email = (TextView)navigationView.getHeaderView(0).findViewById(R.id.nav_header_user_email);

        header_name.setText(AppConstants.app_data.getString("name","n/a"));
        header_email.setText(AppConstants.app_data.getString("email","n/a"));

        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.parentcontainer, new Search_location_frag()).commit();


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

        Fragment  frag1 = new CustomerLandingFrag();
       /* Bundle bundle = new Bundle();
        frag.setArguments(bundle);*/

        fragmentTransaction.add(R.id.parentcontainer, frag1)/*.addToBackStack(LandingActivity.class.getName())*/.commit();

        bundle = getIntent().getExtras();



        if (bundle.getString("src").equals("def")){
            // def means current location dialog appear as well as get data by its lat long
            Log.e("erc0", bundle.getString("src"));
            getLocConfirmDialog();

        }
        else if (bundle.getString("src").equals("deff")){
            // deff means previous saved location but no dialog appear as well as get data by its lat long
               if (AppConstants.app_data.getString("lat","").equals("")){
                   // system does not has lat long , calculate lat long
                   calculateLatLongi("http://maps.google.com/maps/api/geocode/json?address=" + AppConstants.app_data.getString("address","").replaceAll(" ", "%20") + "&sensor=false", location_upd);
               }
            else{
                   // system has lat long , get data by saved lat long
               }

        }
        else if (bundle.getString("src").equals("search_current")){
           // search_current means current location but no dialog appear as well as get data by its lat long
           triggerLocation(location_upd);
        }
        else if (bundle.getString("src").equals("search_save")){
            // search_save means desired saved location but no dialog appear as well as get data by its lat long
            // system does not has lat long , calculate lat long

        }
        else if (bundle.getString("src").equals("create_event")){

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

                for (int i = 0; i <address_array.length() ; i++) {
                    JSONObject obj2 = address_array.getJSONObject(i);

                    business_data.getAdderess_data().add(new BusinessHourData(obj2.getString("address") ,obj2.getString("address_id")
                            , obj2.getString("start_date") , obj2.getString("end_date")
                            , obj2.getString("mon_from_time") , obj2.getString("mon_to_time")
                            , obj2.getString("tue_from_time") , obj2.getString("tue_to_time")
                            , obj2.getString("wed_from_time") , obj2.getString("wed_to_time")
                            , obj2.getString("thru_from_time") , obj2.getString("thru_to_time")
                            , obj2.getString("fri_from_time") , obj2.getString("fri_to_time")
                            , obj2.getString("sat_from_time") , obj2.getString("sat_to_time")
                            , obj2.getString("sun_from_time") , obj2.getString("sun_to_time")
                    ));
                }



                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                Fragment  frag = new BusinessDetailsFrag();
                Bundle bundle = new Bundle();

                bundle.putString("src", "create");
                frag.setArguments(bundle);

                fragmentTransaction.add(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();

            }

            catch (JSONException e){
                Toast.makeText(this, "Error occurred! "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }




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

        //noinspection SimplifiableIfStatement
        if (id ==  R.id.action_search) {
                 if (header_search_layout.getTag()==(Object)0){
                     header_search_layout.setVisibility(View.VISIBLE);
                     header_search_layout.setTag(1);
                 }
            else{
                     header_search_layout.setVisibility(View.GONE);
                     header_search_layout.setTag(0);
                 }
                return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId() ;

        if (id == R.id.nav_create_ads) {

            // Handle the camera action
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            Fragment  frag = new AdPublishSelectionFrag();
            Bundle bundle = new Bundle();

            bundle.putString("src", "def");
            frag.setArguments(bundle);

            fragmentTransaction.add(R.id.parentcontainer, frag).addToBackStack(LandingActivity.class.getName()).commit();
        }
        else if (id == R.id.nav_logout){

            // save current location
            editor = AppConstants.app_data.edit();
            editor.putString("user_id","");
            editor.commit();

            startActivity(new Intent(LandingActivity.this , UserAuthACtivity.class));
            finish();
        }
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
        if (loc==null){


            Toast.makeText(this, "user location couldn't fetched!!!", Toast.LENGTH_SHORT).show();

        }
        else{


            // save current location
            editor = AppConstants.app_data.edit();
            editor.putString("lat", loc.getLatitude()+"");
            editor.putString("long", loc.getLongitude()+"");
            editor.commit();

            if (AppConstants.app_data.getString("address","").equals("")) {
                AppUtils.getAdd("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + loc.getLatitude() + "," + loc.getLongitude() + "&sensor=true",this , http_callback);
            }
            else{
                // get data by saved lat long


            }

        }

    }

    @Override
    public void getResponse(String response) {
        Log.e("res", response);

        if (response.contains("error")){
            Toast.makeText(this, response+" occurred!!!", Toast.LENGTH_SHORT).show();
        }
        else {

            String[] address_array = response.split(",");
            String short_address="";

            if (address_array.length==0){

            }
            else{
                short_address = address_array[0];
            }



            // save current address
            editor = AppConstants.app_data.edit();
            editor.putString("address", response);
            editor.putString("short_add",short_address);
            editor.commit();

            getSupportActionBar().setTitle("  "+AppConstants.app_data.getString("short_add","Life On Internet"));

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



        adderess.setText(AppConstants.app_data.getString("address",""));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageInputWindow.dismiss();

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
