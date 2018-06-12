package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import atw.lifeoninternet.R;
import frags.NewSerResourceSelectionFrag;
import frags.NewServiceStaffSelectionFrag;
import models.StaffSelecData;

/**
 * Created by teknik on 10/6/2017.
 */

public class ServiceResourcesSelection extends BaseAdapter {





    private Activity activity;


    Holder holder;

    private ArrayList<StaffSelecData> data = new ArrayList<>();


    public ServiceResourcesSelection(Activity act , ArrayList<StaffSelecData> _data){


        data = _data;

        activity= act;

    }

    static class Holder{


        TextView name_btn  ;


        CheckBox tic;


    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub


        if(view == null){


            LayoutInflater inflater = ((LayoutInflater)activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE));


            view = inflater.inflate(R.layout.newser_staffselection_list_item_screen,parent,false);


            holder = new Holder();


            holder.name_btn = (TextView)view.findViewById(R.id.staff_name);
            holder.tic = (CheckBox)view.findViewById(R.id.select_btn);










            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }


        holder.name_btn.setText(data.get(position).getStaff_name());


        if (data.get(position).getSelect().equals("No")){
            holder.tic.setChecked(false);
        }
        else{
            holder.tic.setChecked(true);
        }








        holder.tic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    data.get(position).setSelect("yes");
                    Log.e("okk", data.get(position).getSelect());
                    NewSerResourceSelectionFrag.resSelec_array.get(position).setSelect("yes");


                }
                else {
                    data.get(position).setSelect("");
                    Log.e("disable", "-"+data.get(position).getSelect());
                    NewSerResourceSelectionFrag.resSelec_array.get(position).setSelect("");

                }
            }
        });











        return view;
    }



}
