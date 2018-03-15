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

import models.CancelPolicyData;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/9/2017.
 */

public class ServiceCancellationList extends BaseAdapter {





    private Activity activity;


    Holder holder;

    private ArrayList<CancelPolicyData> data = new ArrayList<>();



    public ServiceCancellationList(Activity act , ArrayList<CancelPolicyData> _data){


        activity= act;

        data = _data;

    }

    static class Holder{


        TextView newser_cancelpolicy_listitem_daysno , newser_cancelpolicy_listitem_discount ;





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


            view = inflater.inflate(R.layout.newser_cancellationpolicy_list_item_screen,parent,false);


            holder = new Holder();


            holder.newser_cancelpolicy_listitem_daysno = (TextView)view.findViewById(R.id.newser_cancelpolicy_listitem_daysno);

            holder.newser_cancelpolicy_listitem_discount = (TextView)view.findViewById(R.id.newser_cancelpolicy_listitem_discount);







            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }



        holder.newser_cancelpolicy_listitem_daysno.setText(data.get(position).getSer_count());

        holder.newser_cancelpolicy_listitem_discount.setText(data.get(position).getRefund_amount());

















        return view;
    }

}
