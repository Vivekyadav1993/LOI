package atw.lifeoninternet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frags.LoginFrag;
import frags.RegisterFrag;
import helper.HelperActivity;

/**
 * Created by teknik on 9/25/2017.
 */

public class UserAuthACtivity extends HelperActivity {

    @BindView(R.id.bottom_layout)
    RelativeLayout bottom_layout;

    @BindView(R.id.userauth_tag)
    TextView userauth_tag;

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userauth_screen);

        ButterKnife.bind(this);

        bottom_layout.setTag(0);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.userauth_container, new LoginFrag()).commit();



    }

    @OnClick(R.id.bottom_layout)
    void changeAuth(){

        if (bottom_layout.getTag()==(Object)0){
            userauth_tag.setText("Already have an account");
            bottom_layout.setTag(1);
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.userauth_container, new RegisterFrag()).commit();
        }
        else{
            bottom_layout.setTag(0);
            userauth_tag.setText("Create Account");
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.userauth_container, new LoginFrag()).commit();
        }

    }
}
