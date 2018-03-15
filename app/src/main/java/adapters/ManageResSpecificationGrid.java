package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

import frags.ResSpecManageFrag;
import models.ManageSpecPosData;
import models.SpecQTYDATA;
import r2stech.lifeoninternet.R;

/**
 * Created by teknik on 10/10/2017.
 */

public class ManageResSpecificationGrid extends BaseAdapter {





    private Activity activity;

    private ArrayList<ManageSpecPosData> data = new ArrayList<>();

    private String[] spec_name_array , spec_id_array ;

    Holder holder;

    private ArrayAdapter<String> spinnerArrayAdapter;

    private ArrayList<SpecQTYDATA> QtyData = new ArrayList<>();





    public ManageResSpecificationGrid(Activity act ,
                                      ArrayList<ManageSpecPosData> _data ,
                                      String[] _spec_name_array ,
                                      String[] _spec_id_array
    ,ArrayList<SpecQTYDATA> _QtyData)
    {


        data = _data;
        activity= act;



        spec_name_array = new String[_spec_name_array.length];
        spec_name_array = _spec_name_array;

        spec_id_array = new String[_spec_id_array.length];
        spec_id_array = _spec_id_array;

        QtyData = _QtyData;


    }

    static class Holder{


        Spinner res_spec_spinner ;





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


            view = inflater.inflate(R.layout.res_specification_manage_grid_item_screen,parent,false);


            holder = new Holder();



            holder.res_spec_spinner = (Spinner)view.findViewById(R.id.res_spec_spinner);




           spinnerArrayAdapter = new ArrayAdapter<String>
                    (activity, android.R.layout.simple_spinner_item,
                            spec_name_array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_spinner_dropdown_item);
            holder.res_spec_spinner.setAdapter(spinnerArrayAdapter);


            holder.res_spec_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.e("pos",i+"");

                    if (i==0){
                        data.get(position).setSpec_id("");

                        data.get(position).setSpec_name("");

                        data.get(position).setSpec_pos("");

                        ResSpecManageFrag.data.get(position).setSpec_id("");

                        ResSpecManageFrag.data.get(position).setSpec_name("");

                        ResSpecManageFrag.data.get(position).setSpec_pos("");
                    }
                    else {

                        int total_count , status = 0;

                        for (int j = 0; j <QtyData.size() ; j++) {
                            if (spec_id_array[i].equals(QtyData.get(j).getId())){
                                total_count = QtyData.get(j).getQty();
                                if (total_count-1<0){
                                    Log.e("totalless",total_count-1+"");

                                }
                                else{
                                    status = 1;
                                    String spec_id = QtyData.get(j).getId();
                                    int totl = QtyData.get(j).getQty();

                                    QtyData.remove(j);
                                    QtyData.add(new SpecQTYDATA(spec_id , totl-1));
                                  // QtyData.get(j).setQty(total_count--);
                                    Log.e("total",QtyData.get(j).getQty()+"");

                                }
                            }
                        }

                        if (status==1) {
                            data.get(position).setSpec_id(spec_id_array[i]);

                            data.get(position).setSpec_name(spec_name_array[i]);

                            data.get(position).setSpec_pos(position + "");

                            ResSpecManageFrag.data.get(position).setSpec_id(spec_id_array[i]);

                            ResSpecManageFrag.data.get(position).setSpec_name(spec_name_array[i]);

                            ResSpecManageFrag.data.get(position).setSpec_pos(position + "");
                        }
                        else{
                            holder.res_spec_spinner.setSelection(0);
                            Toast.makeText(activity, "Selected Resource cannot be exceed from Total Count!!!", Toast.LENGTH_SHORT).show();
                        }


                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



            view.setTag(holder);
        }
        else{

            holder =(Holder)view.getTag();

        }























        return view;
    }
}
