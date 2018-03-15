package frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperActivity;
import helper.HelperFrags;
import models.BusinessData;
import r2stech.lifeoninternet.LandingActivity;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 9/27/2017.
 */

public class AdPublishSelectionFrag extends HelperFrags {

    private View Mroot;

    @BindView(R.id.ad_pub_back_btn)
    ImageView ad_pub_back_btn;

    @BindView(R.id.ad_pub_next_btn)
    TextView ad_pub_next_btn;


    private Bundle bundle ;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.ad_publish_selection,null);
        ButterKnife.bind(this, Mroot);
        LandingActivity.business_data = new BusinessData();
        return  Mroot;

    }

    @OnClick(R.id.ad_pub_forall_btn)
    void forAll(){
        // save public in  publish type variable
        LandingActivity.business_data.setPublish_type("Public");
        LandingActivity.business_data.setPublish_id("0");

        bundle = new Bundle();
        bundle.putString("src","def");

        replaceFrag(new BusinessDetailsFrag(),bundle,AdPublishSelectionFrag.class.getName());
    }

    @OnClick(R.id.ad_pub_society_btn)
    void forSociety(){

        // save Society in  publish type variable
        LandingActivity.business_data.setPublish_type("Society");

        bundle = new Bundle();
        bundle.putString("src","society");
        replaceFrag(new GroupSelectionFrag(),bundle,AdPublishSelectionFrag.class.getName());

    }

    @OnClick(R.id.ad_pub_private_btn)
    void forPrivate(){
        // save Private in  publish type variable
        LandingActivity.business_data.setPublish_type("Private");

        bundle = new Bundle();
        bundle.putString("src","private");
        replaceFrag(new GroupSelectionFrag(),bundle,AdPublishSelectionFrag.class.getName());

    }
}
