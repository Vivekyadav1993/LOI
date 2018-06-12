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
import models.ContactsData;
import models.StaffData;

/**
 * Created by teknik on 11/14/2017.
 */

public class AddStaffAdap extends BaseAdapter {


    private Activity activity;
    Holder holder;
    private ArrayList<StaffData> data = new ArrayList<>();

    public AddStaffAdap(Activity act, ArrayList<StaffData> aray) {
        activity = act;
        data = aray;
    }

    static class Holder {
        TextView name;
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


        if (view == null) {


            LayoutInflater inflater = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            view = inflater.inflate(R.layout.staff_list_child_screen, parent, false);
            holder = new Holder();

            holder.name = (TextView) view.findViewById(R.id.ad_staff_list_nametag);

            view.setTag(holder);


        } else {

            holder = (Holder) view.getTag();

        }

        Log.e("dat", data.get(position).getAdmin_flag());
        if (!data.get(position).getAdmin_flag().equals("No")) {
            holder.name.setTextColor(activity.getResources().getColor(R.color.blue));
        }


        holder.name.setText(data.get(position).getFirst_name() + " " + data.get(position).getLast_name());

        return view;
    }

    public void setData(ArrayList<StaffData> arr) {
        data = arr;
        notifyDataSetChanged();
    }
}
