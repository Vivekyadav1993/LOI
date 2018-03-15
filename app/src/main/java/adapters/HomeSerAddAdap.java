package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.HomeSerData;
import models.ResourceData;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 11/24/2017.
 */

public class HomeSerAddAdap extends BaseAdapter {


    private Activity activity;


   Holder holder;

    private ArrayList<HomeSerData> data = new ArrayList<>();


    public HomeSerAddAdap(Activity act, ArrayList<HomeSerData> aray) {


        activity = act;

        data = aray;

    }

    static class Holder {


        TextView placename , placeradius , travel_time , date ;






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


            LayoutInflater inflater = ((LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE));


            view = inflater.inflate(R.layout.homeservice_list_item_screen, parent, false);


            holder = new Holder();



            holder.placename = (TextView) view.findViewById(R.id.homeser_list_place_name);
            holder.placeradius = (TextView) view.findViewById(R.id.homeser_list_place_radius);
            holder.travel_time = (TextView) view.findViewById(R.id.homeser_list_place_travel_time);
            holder.date = (TextView) view.findViewById(R.id.homeser_list_date);




            view.setTag(holder);


        } else {

            holder = (Holder) view.getTag();

        }



        holder.placename.setText(data.get(position).getLocation());
        holder.placeradius.setText(data.get(position).getNo_of_radius()+" Km");
        holder.travel_time.setText(data.get(position).getTravel_time()+" min.");
        holder.date.setText(data.get(position).getStart_time()+" to "+data.get(position).getEnd_time());





        return view;
    }



}
