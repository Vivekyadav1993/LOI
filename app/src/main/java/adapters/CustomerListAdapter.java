package adapters;

/**
 * Created by Vivek on 06-07-2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import atw.lifeoninternet.R;
import atw.lifeoninternet.interfaces.UpdateListData;
import atw.lifeoninternet.utils.Sharedpreferences;
import atw.lifeoninternet.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import frags.MyAdsFrag;
import models.ConsumerListData;
import models.SelectableItem;
import models.addetailsEdit.Resource;

import static frags.MyAdsFrag.business_id;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<ConsumerListData> data = new ArrayList<>();
    private ConsumerListData set;
    ConsumerListAdap.Holder holder;
    private UpdateListData updateListData;
    private Sharedpreferences mPref;
    private Dialog mDialougeBox;
    private int row_index = -1;


    public static final int MULTI_SELECTION = 2;
    public static final int SINGLE_SELECTION = 1;
    CheckedTextView textView;
    SelectableItem mItem;
    AdapterView.OnItemSelectedListener itemSelectedListener;

    boolean selected = true;

    private String[] service_name_array;
    public CustomerSelectedClick mClick;
    private String c = "0";

    public interface CustomerSelectedClick {
        void onClick(String id, int position, String service_id);

    }

    public CustomerListAdapter(Activity act, ArrayList<ConsumerListData> _data/*, String atpermise_all*/, UpdateListData _updateListData, CustomerSelectedClick mClick) {
        activity = act;
        data = _data;
        //  this.atpermise_all = atpermise_all;
        updateListData = _updateListData;
        this.mClick = mClick;
        mPref = Sharedpreferences.getUserDataObj(act);
        Collections.sort(data);

    }

    public void filterList(ArrayList<ConsumerListData> filterdNames) {
        this.data = filterdNames;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coonsumer_list_item_screen, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            if (row_index == position) {


                holder.customer_list_rl.setBackgroundColor(activity.getResources().getColor(R.color.amber_200));
                //   holder.mTitleTV.setTextColor(activity.getResources().getColor(R.color.white));
            } else {
                holder.customer_list_rl.setBackgroundColor(Color.parseColor("#ffffff"));
                //  holder.mTitleTV.setTextColor(activity.getResources().getColor(R.color.black));

            }

            holder.absent_btn.setTag(position);
            service_name_array = new String[data.size()];

            holder.customer_list_rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                 /*   if (c.equalsIgnoreCase(String.valueOf(position))) {

                        holder.customer_list_rl.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    } else {
                        row_index = position;

                        if (row_index == position) {
                            holder.customer_list_rl.setBackgroundColor(activity.getResources().getColor(R.color.amber_200));
                        } else {
                            holder.customer_list_rl.setBackgroundColor(activity.getResources().getColor(R.color.white));
                        }
                    }*/


                 /*   for(int i=0;i<data.size();i++){
                        service_name_array[i]= String.valueOf(position);
                    }*/

                    row_index = position;
                    service_name_array[position] = String.valueOf(position);
                    c = service_name_array[position];
                    notifyDataSetChanged();
                    mClick.onClick(data.get(position).getId(), position, data.get(position).getService_id());
                    return true;
                }
            });


        /*    holder.customer_list_rl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mClick.onClick(Integer.parseInt(data.get(position).getId()), String.valueOf(position), data.get(position).getService_id());
                    //  holder.customer_list_rl.getChildAt(position).setBackgroundColor(Color.GRAY);
                    return true;
                }
            });*/
            set = data.get(position);

            if (data.get(position).getAtpremise_all().equalsIgnoreCase("Yes")) {

                if (data.get(position).getStatus().equalsIgnoreCase("In")) {
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.status.setVisibility(View.GONE);
                    holder.absent_btn.setText("In");
                    holder.absent_btn.setBackgroundColor(/*Color.parseColor("#ca0808")*/activity.getResources().getColor(R.color.blue));
                } else if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                    //  holder.absent_btn.setText("In");
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.absent_btn.setBackgroundColor(Color.parseColor("#ca0808"));
                    holder.status.setVisibility(View.VISIBLE);
                    holder.absent_btn.setText("Cancel");


                } else {
                    holder.absent_btn.setText("Cancel");
                    holder.absent_btn.setVisibility(View.GONE);
                    holder.absent_btn.setBackgroundColor(Color.RED);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setText("At Premise");

                }
            } else {
                if (data.get(position).getStatus().equalsIgnoreCase("In")) {
                    holder.absent_btn.setText("In");
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.absent_btn.setBackgroundColor(/*Color.parseColor("#ca0808")*/activity.getResources().getColor(R.color.blue));
                    holder.status.setVisibility(View.GONE);

                } else if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                    //  holder.absent_btn.setText("In");
                    holder.absent_btn.setVisibility(View.VISIBLE);
                    holder.absent_btn.setBackgroundColor(Color.parseColor("#ca0808"));
                    holder.status.setVisibility(View.VISIBLE);
                    holder.absent_btn.setText("Cancel");


                } else {
                    holder.absent_btn.setText("Cancel");
                    holder.absent_btn.setVisibility(View.GONE);
                    holder.absent_btn.setBackgroundColor(Color.RED);
                    holder.status.setVisibility(View.VISIBLE);
                    holder.status.setText("At Premise");


                }


            }
            holder.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mPref.getStaffId().equalsIgnoreCase("")) {

                        if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAtPermise&id="
                                    + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                    + MyAdsFrag.address_id + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" +
                                    data.get(position).getService_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&adminlogin=" + mPref.getStaffAdmin());
                            holder.status.setText("At Premise");
                        } else {


                        }

                    } else {

                        if (data.get(position).getStatus().equalsIgnoreCase("Active")) {
                            updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAtPermise&id="
                                    + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                    + MyAdsFrag.address_id + "&staff_id=" + mPref.getStaffId() + "&sub_date=" + data.get(position).getSub_date()
                                    + "&service_id=" + data.get(position).getService_id() + "&appointment_date=" + data.get(position).getAppointment_date() + "&adminlogin=" + mPref.getStaffAdmin());
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
                                                "&sub_date=" + data.get(position).getSub_date() + "&service_id=" + data.get(position).getService_id() +
                                                "&absent_type=bottom" + "&adminlogin=" + mPref.getStaffAdmin());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    try {
                                        Log.d("CLA", "" + data.get(position).getId());
                                        updateListData.doUpdate("http://lifeoninternet.com/" + Utils.stringBuilder() + "/api.php?action=updateUserStatusAbsent&id="
                                                + data.get(position).getId() + "&business_id=" + business_id + "&address_id="
                                                + MyAdsFrag.address_id + "&appointment_date=" + data.get(position).getAppointment_date() +
                                                "&staff_id=" + mPref.getStaffId() + "&sub_date=" + data.get(position).getSub_date() + "&service_id=" +
                                                data.get(position).getService_id() + "&absent_type=bottom");
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


            holder.token.setText(data.get(position).getToken_id() + ".");

            if (data.get(position).getStatus().equals("IN") || data.get(position).getStatus().equals("At Permise")) {
                holder.info.setText(data.get(position).getCustomer_name() + "\nEstimated time :" + data.get(position).getEstimate_time() + "\nService : " + data.get(position).getService_name());
            } else {
                holder.info.setText(data.get(position).getCustomer_name() + "\nEstimated time :" + data.get(position).getEstimate_time() + "\nService : " + data.get(position).getService_name());

            }
            if (data.get(position).getStatus().equals("Active")) {
                holder.status.setText("At Premise");

            } else {
                holder.status.setText(data.get(position).getStatus());
            }

        } catch (Exception e) {

        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.token)
        public TextView token;

        @BindView(R.id.token_info)
        public TextView info;
        @BindView(R.id.token_status)
        public TextView status;

        @BindView(R.id.absent_btn)
        public Button absent_btn;
        @BindView(R.id.customer_list_rl)
        public RelativeLayout customer_list_rl;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            ButterKnife.bind(this, itemView);
        }
    }
}