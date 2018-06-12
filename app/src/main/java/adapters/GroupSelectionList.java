package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import atw.lifeoninternet.R;
import models.GroupData;

/**
 * Created by teknik on 10/9/2017.
 */

public class GroupSelectionList extends BaseAdapter {


    private ArrayList<GroupData> data = new ArrayList<>();



    private Activity activity;

    Holder holder;


    public GroupSelectionList(Activity act , ArrayList<GroupData> _data){


        activity= act;

        data = _data;

    }

    static class Holder{


        TextView group_name , recip_count ;





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


            view = inflater.inflate(R.layout.group_selection_list_item_screen,parent,false);


            holder = new Holder();



            holder.group_name = (TextView) view.findViewById(R.id.group_name);
            holder.recip_count = (TextView) view.findViewById(R.id.group_recip_count);







            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }




        holder.group_name.setText(data.get(position).getGroup_name());

        holder.recip_count.setText(data.get(position).getGroup_recip_count());
















        return view;
    }


}
