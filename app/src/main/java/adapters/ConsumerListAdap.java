package adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import frags.MyAdsFrag;
import models.ConsumerListData;
import r2stech.lifeoninternet.R;
import r2stech.lifeoninternet.interfaces.UpdateListData;
import r2stech.lifeoninternet.utils.Sharedpreferences;
import r2stech.lifeoninternet.utils.Utils;

/**
 * Created by ROINET on 3/6/2018.
 */

public class ConsumerListAdap extends BaseAdapter {

    private Activity activity;
    private ArrayList<ConsumerListData> data = new ArrayList<>();
    private ConsumerListData set;
    Holder holder;
    private UpdateListData updateListData;
    private Sharedpreferences mPref;

    public ConsumerListAdap(Activity act, ArrayList<ConsumerListData> _data, UpdateListData _updateListData) {
        activity = act;
        data = _data;
        updateListData = _updateListData;
        mPref = Sharedpreferences.getUserDataObj(act);
        // Collections.sort(data);

    }

    static class Holder {
        TextView token, info, status;
        Button absent_btn;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub


        if (view == null) {
            LayoutInflater inflater = ((LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE));
            view = inflater.inflate(R.layout.coonsumer_list_item_screen, parent, false);
            holder = new Holder();
            holder.token = (TextView) view.findViewById(R.id.token);
            holder.info = (TextView) view.findViewById(R.id.token_info);
            holder.status = (TextView) view.findViewById(R.id.token_status);
            holder.absent_btn = (Button) view.findViewById(R.id.absent_btn);

            holder.absent_btn.setTag(position);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        try {

            set = data.get(position);
           /* if (set.getMessage() == "No Customer Found") {

                holder.status.setText(data.get(position).getMessage());

            }

*/
            if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                holder.absent_btn.setVisibility(View.VISIBLE);
                //  holder.status.setText("At Premise");

            } else {

                holder.absent_btn.setVisibility(View.GONE);
            }

            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mPref.getStaffId().equalsIgnoreCase("")) {

                        if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAtPermise&id="
                                    + data.get(position).getId() + "&business_id=" + MyAdsFrag.business_id + "&address_id="
                                    + MyAdsFrag.address_id);
                            holder.status.setText("At Premise");
                        } else {

                            //   holder.status.setText("At Premise");
                            //   holder.status.setText("At Premise");

                            //   Toast.makeText(get, "You already ", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAtPermise&id="
                                    + data.get(position).getId() + "&business_id=" + MyAdsFrag.business_id + "&address_id="
                                    + MyAdsFrag.address_id + "&staff_id=" + mPref.getStaffId());
                            holder.status.setText("At Premise");
                        } else {

                            //   holder.status.setText("At Premise");
                            //   holder.status.setText("At Premise");

                            //   Toast.makeText(get, "You already ", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
            holder.absent_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mPref.getStaffId().equalsIgnoreCase("")) {
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                + data.get(position).getId() + "&business_id=" + MyAdsFrag.business_id + "&address_id="
                                + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date());


                    } else {
                        Log.d("CLA", "" + data.get(position).getId());
                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                + data.get(position).getId() + "&business_id=" + MyAdsFrag.business_id + "&address_id="
                                + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date() + "&staff_id=" + mPref.getStaffId());
                    }
                }
            });


            holder.token.setText(set.getToken_id() + ".");

            if (set.getStatus().equals("IN") || set.getStatus().equals("At Permise")) {
                holder.info.setText(set.getCustomer_name() + "\nEstimated time :" + set.getEstimate_time() + "\nService : " + set.getService_name());
            } else {
                holder.info.setText(set.getCustomer_name() + "\nEstimated time :" + set.getEstimate_time() + "\nService : " + set.getService_name());

            }
            if (set.getStatus().equals("Active")) {
                holder.status.setText("At Premise");

            } else {
                holder.status.setText(set.getStatus());
            }
        } catch (IndexOutOfBoundsException e) {

        }

        return view;

    }
}


