package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import models.DiscountPolicyData;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/9/2017.
 */

public class NewSerDiscountList extends BaseAdapter {





    private Activity activity;


    Holder holder;

    private ArrayList<DiscountPolicyData> data = new ArrayList<>();


    public NewSerDiscountList(Activity act , ArrayList<DiscountPolicyData> _data){


        activity= act;

        data = _data;


    }

    static class Holder{


        TextView dispolicy_class_count , dispolicy_period_count , dispolicy_day_count ,dispolicy_dis_count ;





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


            view = inflater.inflate(R.layout.newser_discount_list_item_screen,parent,false);


            holder = new Holder();



            holder.dispolicy_class_count= (TextView)view.findViewById(R.id.dispolicy_class_count);
            holder.dispolicy_period_count= (TextView)view.findViewById(R.id.dispolicy_period_count);
            holder.dispolicy_day_count= (TextView)view.findViewById(R.id.dispolicy_day_count);
            holder.dispolicy_dis_count= (TextView)view.findViewById(R.id.dispolicy_dis_count);








            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }



        holder.dispolicy_class_count.setText(data.get(position).getClass_count());
        holder.dispolicy_period_count.setText(data.get(position).getPeriod_count());
        holder.dispolicy_day_count.setText(data.get(position).getDay_count());
        holder.dispolicy_dis_count.setText(data.get(position).getDiscount_amount());




















        return view;
    }

}
