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

import atw.lifeoninternet.R;
import models.ResourceSpecification;

/**
 * Created by teknik on 10/10/2017.
 */

public class AddResSpecList extends BaseAdapter {





    private Activity activity;

    Holder holder;

    ArrayList<ResourceSpecification> spec_data = new ArrayList<>();


    public AddResSpecList(Activity act , ArrayList<ResourceSpecification> _spec_data){


        activity= act;

        spec_data = _spec_data;

    }

    static class Holder{


        TextView name_btn  , qty;





    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return spec_data.size();
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


            view = inflater.inflate(R.layout.add_res_specification_list_item_screen,parent,false);


            holder = new Holder();

            holder.name_btn = (TextView) view.findViewById(R.id.newres_spec_list_name_tag);
            holder.qty = (TextView) view.findViewById(R.id.newres_spec_list_qty_tag);













            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }





         holder.name_btn.setText(spec_data.get(position).getRes_name());

         holder.name_btn.setText(spec_data.get(position).getRes_qty());

















        return view;
    }
}
