package adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import atw.lifeoninternet.LandingActivity;
import atw.lifeoninternet.R;
import atw.lifeoninternet.interfaces.UpdateListData;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import frags.MyAdsFrag;
import models.ConsumerListData;

import static frags.MyAdsFrag.business_id;


/**
 * Created by Vivek on 3/6/2018.
 */

public class ConsumerListAdap extends BaseAdapter {

    private Activity activity;
    private ArrayList<ConsumerListData> data = new ArrayList<>();
    private ConsumerListData set;
    Holder holder;
    private UpdateListData updateListData;
    private Sharedpreferences mPref;
    private Dialog mDialougeBox;
    private String atpermise_all;
    private int row_index = -1;

    private CustomerSelectedClick mClick;
    //  private CustomerLandingCallClick mClickCall;

    public interface CustomerSelectedClick {
        void onClick(int id, String position, String service_id);

    }

    public ConsumerListAdap(Activity act, ArrayList<ConsumerListData> _data, String atpermise_all, UpdateListData _updateListData, CustomerSelectedClick mClick) {
        activity = act;
        data = _data;
        this.atpermise_all = atpermise_all;
        updateListData = _updateListData;
        this.mClick = mClick;
        mPref = Sharedpreferences.getUserDataObj(act);

        Collections.sort(data);


    }

    public void filterList(ArrayList<ConsumerListData> filterdNames) {
        this.data = filterdNames;
        notifyDataSetChanged();
    }


    static class Holder {
        TextView token, info, status;
        Button absent_btn;
        RelativeLayout customer_list_rl;
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
            holder.customer_list_rl = (RelativeLayout) view.findViewById(R.id.customer_list_rl);

            holder.absent_btn.setTag(position);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }


        try {

            holder.customer_list_rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mClick.onClick(Integer.parseInt(data.get(position).getId()), String.valueOf(position), data.get(position).getService_id());
                    //  holder.customer_list_rl.getChildAt(position).setBackgroundColor(Color.GRAY);
                    return true;
                }
            });
            set = data.get(position);

            if (atpermise_all.equalsIgnoreCase("Yes")) {

                if (data.get(position).getStatus().equalsIgnoreCase("In")) {
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.status.setVisibility(View.INVISIBLE);
                    holder.absent_btn.setText("In");
                    holder.absent_btn.setBackgroundColor(Color.BLUE);
                } else {
                    holder.absent_btn.setText("Cancel");
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.absent_btn.setBackgroundColor(Color.RED);

                }
            } else {
                if (data.get(position).getStatus().equalsIgnoreCase("In")) {
                    holder.absent_btn.setText("In");
                    holder.absent_btn.setBackgroundColor(Color.BLUE);
                } else {
                    holder.absent_btn.setText("Cancel");
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.absent_btn.setBackgroundColor(Color.RED);

                }
                holder.status.setVisibility(View.VISIBLE);

                if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    //  holder.status.setText("At Premise");

                } else {

                    holder.absent_btn.setVisibility(View.INVISIBLE);
                }

            }
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mPref.getStaffId().equalsIgnoreCase("")) {

                        if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAtPermise&id="
                                    + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                    + MyAdsFrag.address_id + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());
                            holder.status.setText("At Premise");
                        } else {

                            //   holder.status.setText("At Premise");
                            //   holder.status.setText("At Premise");

                            //   Toast.makeText(get, "You already ", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAtPermise&id="
                                    + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                    + MyAdsFrag.address_id + "&staff_id=" + mPref.getStaffId() + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());
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


                    if (data.get(position).getStatus().equalsIgnoreCase("In")) {

                    } else {
                        mDialougeBox = new Dialog(activity);
                        mDialougeBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialougeBox.setContentView(R.layout.item_absent_customer);
                        mDialougeBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialougeBox.getWindow().setGravity(Gravity.CENTER);
                        mDialougeBox.show();

                        TextView mDelete = (TextView) mDialougeBox.findViewById(R.id.absent_customer_tv);
                        TextView mCancleTv = (TextView) mDialougeBox.findViewById(R.id.cancel_customer_tv);

                        mDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mPref.getStaffId().equalsIgnoreCase("")) {
                                    try {
                                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                                + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                                + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date() +
                                                "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    try {
                                        Log.d("CLA", "" + data.get(position).getId());
                                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                                + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                                + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date() +
                                                "&staff_id=" + mPref.getStaffId() + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                mDialougeBox.hide();
                            }
                        });

                        mCancleTv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mDialougeBox.hide();

                            }
                        });

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


