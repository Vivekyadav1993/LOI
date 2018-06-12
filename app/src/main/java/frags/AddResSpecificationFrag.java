package frags;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import adapters.AddResSpecList;
import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import helper.HelperFrags;
import models.ResourceSpecification;

/**
 * Created by teknik on 10/10/2017.
 */

public class AddResSpecificationFrag extends HelperFrags {

    private View Mroot;

    @BindView(R.id.addres_spec_list)
    ListView list;

    @BindView(R.id.addres_spec_name_input)
    EditText addres_spec_name_input;

    @BindView(R.id.addres_spec_quantity_input)
    EditText addres_spec_quantity_input;

    private AddResSpecList adapter;

    private ArrayList<ResourceSpecification> res_spec_array = new ArrayList<>();

    private  Snackbar snackbar;

    private Bundle bundle ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Mroot = inflater.inflate(R.layout.add_res_specification_screen , null);
        ButterKnife.bind(this , Mroot);

        bundle = getArguments();

        if (LandingActivity.res_data_array.get(bundle.getInt("pos")).getRes_spec_array().get(0).getRes_name().equals("")){

        }
else {
            res_spec_array = LandingActivity.res_data_array.get(bundle.getInt("pos")).getRes_spec_array();
        }
        adapter = new AddResSpecList(getActivity() , res_spec_array);
        list.setAdapter(adapter);


        return Mroot;
    }

    @OnClick(R.id.newser_cancelpolicy_add_btn)
    void add(){
        //add data into list
        if (addres_spec_name_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Resource name cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();

        }
        else if (addres_spec_quantity_input.getText().toString().equals("")){
            snackbar = Snackbar.make(Mroot, "Resource QTY cannot blank!!!", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else{
            res_spec_array.add(new ResourceSpecification(addres_spec_name_input.getText().toString() , addres_spec_quantity_input.getText().toString()));
            list.setAdapter(adapter);
        }
    }

    @OnClick(R.id.addres_spec_back_btn)
    void back(){
        getActivity().onBackPressed();
    }

    @OnClick(R.id.addres_spec_next_btn)
    void next(){

        LandingActivity.res_data_array.get(bundle.getInt("pos")).setRes_spec_array(res_spec_array);

        Bundle _bundle =   new Bundle();
        _bundle.putString("src",bundle.getString("src"));
        _bundle.putInt("pos",bundle.getInt("pos"));
        _bundle.putInt("create_pos",bundle.getInt("create_pos"));

        replaceFrag(new NewResourceInfoBFrag(), _bundle , AddResSpecificationFrag.class.getName());
    }

}
