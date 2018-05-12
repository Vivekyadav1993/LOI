package r2stech.lifeoninternet.views;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import adapters.AppointmentDashbordAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frags.CustomerLandingFrag;
import frags.MyCustomerFrag;
import r2stech.lifeoninternet.R;

public class AppointmentDashbord extends AppCompatActivity {


    @BindView(R.id.act_appointment_dashbord_viewpager)
    public ViewPager mViewPager;

    @BindView(R.id.act_appointment_dashbord_tablayout)
    public TabLayout mTabLayout;

    String businessId, addressId;

    private AppointmentDashbordAdapter mAppointmentDashbordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_dashbord);

        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.act_appointment_dashbord_back_iv})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_appointment_dashbord_back_iv:
                onBackPressed();
                break;
        }
    }


    private void initView() {

        businessId = this.getIntent().getExtras().getString("business_id");
        addressId = this.getIntent().getExtras().getString("address_id");

        Log.d("AD", "businessId" + businessId + "addressId" + addressId);

        settingUpTabLayout();
        settingUpViewPager();

    }

    private void settingUpViewPager() {

        mAppointmentDashbordAdapter = new AppointmentDashbordAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), businessId, addressId);
        mViewPager.setAdapter(mAppointmentDashbordAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    private void settingUpTabLayout() {

        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("My Customers"));
        mTabLayout.addTab(mTabLayout.newTab().setText("My History"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                //  tab.getIcon().setColorFilter(Color.parseColor("#e15822"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                //    tab.getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
