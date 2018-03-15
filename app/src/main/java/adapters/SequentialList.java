package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import frags.SequentialFrag;
import models.SequentialData;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/10/2017.
 */

public class SequentialList extends BaseAdapter {





    private Activity activity;


    Holder holder;

    private String[] data  , pos;

    private ArrayAdapter<String> spinnerArrayAdapter;


    public SequentialList(Activity act , String[] _data){

         data =  new String[_data.length];

         activity= act;

         data = _data;

         pos = new String[_data.length+1];
         pos[0] = "Select Position" ;

        for (int i = 0; i <_data.length ; i++) {

                pos[i+1] = (i+1)+"" ;

        }


    }

    static class Holder{


        TextView seqn_ser_tag ;


        Spinner seqn_pos_picker;


    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
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


            view = inflater.inflate(R.layout.sequential_list_item_screen,parent,false);


            holder = new Holder();




            holder.seqn_pos_picker = (Spinner)view.findViewById(R.id.seqn_pos_picker);

            holder.seqn_ser_tag  = (TextView)view.findViewById(R.id.seqn_tag);



            spinnerArrayAdapter = new ArrayAdapter<String>
                    (activity, android.R.layout.simple_spinner_item,
                            pos); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);

            holder.seqn_pos_picker.setAdapter(spinnerArrayAdapter);




            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }




        holder.seqn_pos_picker.setSelection(SequentialFrag.data.get(position).getPos());

        holder.seqn_ser_tag.setText(data[position]);


        holder.seqn_pos_picker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){

                }
                else {
                boolean add= true;

                for (int j = 0; j <SequentialFrag.data.size() ; j++) {

                    if (pos[i].equals(SequentialFrag.data.get(j).getPos()+"")){
                        //  holder.seqn_pos_picker.setSelection(0);
                        //  Toast.makeText(activity, "This position already picked!", Toast.LENGTH_SHORT).show();
                        add = false;

                    }
                    else{


                    }
                }



                    if (add) {
                        SequentialData data = new SequentialData(SequentialFrag.data.get(position).getSer_id() ,SequentialFrag.data.get(position).getPos());
                        SequentialFrag.data.add(position,data);
                    } else {
                        holder.seqn_pos_picker.setSelection(0);
                        Toast.makeText(activity, "This position already picked!", Toast.LENGTH_SHORT).show();

                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
















        return view;
    }
}
