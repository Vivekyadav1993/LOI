package adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import atw.lifeoninternet.R;
import helper.HelperFrags;
import models.PackageConfigData;

/**
 * Created by teknik on 10/10/2017.
 */

public class NewSerPackConfigManageList extends BaseAdapter {





    private Activity activity;

    private ArrayList<PackageConfigData> data = new ArrayList<>();


    Holder holder;


    public NewSerPackConfigManageList(Activity act , ArrayList<PackageConfigData> _data){


        activity= act;

        data = _data;


    }

    static class Holder{


        TextView pack_config_list_day , pack_config_list_duration , pack_config_list_class ,
                pack_config_list_cost , pack_config_list_cancelbyvendor , pack_config_list_cancelbycustomer ;


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
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub


        if(view == null){


            LayoutInflater inflater = ((LayoutInflater)activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE));


            view = inflater.inflate(R.layout.newser_package_config_managelist_item_screen,parent,false);


            holder = new Holder();



            holder.pack_config_list_day = (TextView)view.findViewById(R.id.pack_config_list_day) ;

            holder.pack_config_list_duration = (TextView)view.findViewById(R.id.pack_config_list_duration) ;

            holder.pack_config_list_class = (TextView)view.findViewById(R.id.pack_config_list_class) ;

            holder.pack_config_list_cost = (TextView)view.findViewById(R.id.pack_config_list_cost) ;

            holder.pack_config_list_cancelbyvendor = (TextView)view.findViewById(R.id.pack_config_list_cancelbyvendor) ;

            holder.pack_config_list_cancelbycustomer = (TextView)view.findViewById(R.id.pack_config_list_cancelbycustomer) ;











            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }


        holder.pack_config_list_day.setText(data.get(position).getNo_of_days());

        holder.pack_config_list_duration.setText(data.get(position).getDuration_per_class());

        holder.pack_config_list_class.setText(data.get(position).getNo_of_class());

        holder.pack_config_list_cost.setText(data.get(position).getPackage_cost());

        holder.pack_config_list_cancelbyvendor.setText(data.get(position).getCancelled_by_vendor_refund());

        holder.pack_config_list_cancelbycustomer.setText(data.get(position).getCancelled_by_customer_refund());
























        return view;
    }

}
