package frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
/**
 * Created by teknik on 10/4/2017.
 */

public class NewResourceInfoFrag extends HelperFrags {


    private View Mroot;

    @BindView(R.id.new_resinfo_tag)
    TextView new_resinfo_tag;

    @BindView(R.id.new_resinfo_name_input)
    EditText new_resinfo_name_input;

    @BindView(R.id.new_resinfo_details_input)
    EditText new_resinfo_details_input;

    private Snackbar snackbar;

    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_resources_info_screen, null);
        ButterKnife.bind(this, Mroot);

        bundle = getArguments();

        if (bundle.getString("src").equals("def")){

        }
        else{
            new_resinfo_tag.setText(LandingActivity.res_data_array.get(bundle.getInt("pos")).getName());

            new_resinfo_name_input.setText(LandingActivity.res_data_array.get(bundle.getInt("pos")).getName());
            new_resinfo_details_input.setText(LandingActivity.res_data_array.get(bundle.getInt("pos")).getDetails());
        }

        return  Mroot;
    }

    @OnClick(R.id.new_resinfo_back_btn)
    void back(){
getActivity().onBackPressed();    }



    @OnClick(R.id.new_resinfo_next_btn)
    void go(){

        if (new_resinfo_name_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Resource name cannot blank!!!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else{


            LandingActivity.res_data_array.get(bundle.getInt("pos")).setName(new_resinfo_name_input.getText().toString());
            LandingActivity.res_data_array.get(bundle.getInt("pos")).setDetails(new_resinfo_details_input.getText().toString());

            Bundle _bundle = new Bundle();


            _bundle.putString("src",bundle.getString("src"));
            _bundle.putInt("pos",bundle.getInt("pos"));
            _bundle.putInt("create_pos",bundle.getInt("create_pos"));
            replaceFrag(new NewResourceInfoBFrag(), _bundle , NewResourceInfoFrag.class.getName());


        }

    }

}
