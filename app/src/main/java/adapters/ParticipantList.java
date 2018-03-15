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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import models.ContactsData;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/9/2017.
 */

public class ParticipantList extends BaseAdapter {





    private Activity activity;


    Holder holder;

    private  ArrayList<ContactsData> data = new ArrayList<>();


    public ParticipantList(Activity act , ArrayList<ContactsData> aray){


        activity= act;

        data = aray;

    }

    static class Holder{


        TextView letter ,name , number ;


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


            view = inflater.inflate(R.layout.select_participant_list_item_screen,parent,false);


            holder = new Holder();



             holder.letter = (TextView)view.findViewById(R.id.contacts_list_letter_tag);
            holder.name = (TextView)view.findViewById(R.id.contacts_list_name_tag);
            holder.number = (TextView)view.findViewById(R.id.contacts_list_number_tag);
            holder.tic = (CheckBox) view.findViewById(R.id.contacts_list_select_btn);





            holder.tic.setTag(0);
            view.setTag(holder);


        }
        else{

            holder =(Holder)view.getTag();

        }




        holder.letter.setText(String.valueOf(data.get(position).getName().charAt(0)));
        holder.name .setText(data.get(position).getName());
        holder.number.setText(data.get(position).getNumber());


       /* if (holder.tic.getTag()==(Object)0){
            holder.tic.setImageResource(R.drawable.unchecked_demo);
            Log.e("tag0" , holder.tic.getTag()+"");
        }
        else{
            holder.tic.setImageResource(R.drawable.checked_demo);
            Log.e("tag1" , holder.tic.getTag()+"");
        }



        holder.tic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag()==(Object)0){
                    view.setBackgroundResource(R.drawable.checked_demo);
                    view.setTag(1);
                    Log.e("tag0-1" , holder.tic.getTag()+"");
                }
                else{
                    view.setBackgroundResource(R.drawable.unchecked_demo);
                    view.setTag(0);
                    Log.e("tag1-0" , holder.tic.getTag()+"");
                }


            }
        });

*/
        Log.e("pos0 ", position+"---"+data.get(position).isSelected());
        if (data.get(position).isSelected()){
            holder.tic.setChecked(true);
            Log.e("tag0" , holder.tic.getTag()+"");
        }
        else{
            holder.tic.setChecked(false);
            Log.e("tag1" , holder.tic.getTag()+"");
        }












        return view;
    }

    public void setData(ArrayList<ContactsData> arr){
        data = arr;
        notifyDataSetChanged();
    }
}
