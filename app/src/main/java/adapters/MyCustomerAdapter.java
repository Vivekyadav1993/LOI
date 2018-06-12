package adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import frags.MyAdsAddressFrag;
import frags.MyCustomerFrag;
import frags.NewServiceStaffSelectionFrag;
import models.CancelCustomerSelection;
import models.StaffSelecData;
import models.myadsaddress.Businessaddress;
import models.mycustomer.Booking;
import models.mycustomer.MyCustomer;


public class MyCustomerAdapter extends RecyclerView.Adapter<MyCustomerAdapter.ViewHolder> {


    private ArrayList<Booking> data;
    private MyCustomerFrag mMyCustomerFrag;
    private Boolean checked_status;
    private boolean isSelectedAll;

    private MyCustomerAdapter.CheckBoxChecked mCheck;

    public interface CheckBoxChecked {
        void onClick(int pos, String business_id, String addredd_id, String name);

    }

    private String cancel, str = "";

    public MyCustomerAdapter(MyCustomerFrag mContext, ArrayList<Booking> data) {
        this.mMyCustomerFrag = mContext;
        this.data = data;

        // Collections.sort(data);
        //  customerCancelArray = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_customers_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mName.setText("Customer Name   :" + data.get(position).getUserName());
        holder.mSerialNo.setText((position + 1) + ".");
        holder.mService.setText("Service                   :" + data.get(position).getServiceName());
        holder.mEstimateTime.setText("Estimate Time      :" + data.get(position).getEstimateTime());

        if (isSelectedAll) {
            holder.mCheckbox.setChecked(true);

            for (int i=0;i<data.size();i++){
                data.get(i).setStatus("Yes");
                MyCustomerFrag.customerCancelArray.get(i).setStatus("Yes");
            }

        } else {
            holder.mCheckbox.setChecked(false);
            for (int i=0;i<data.size();i++){
                data.get(i).setStatus("No");
                MyCustomerFrag.customerCancelArray.get(i).setStatus("No");
            }

        }

    /*    if (data.get(position).getChkStatus().equalsIgnoreCase("No")) {
            MyCustomerFrag.customerCancelArray.get(position).setStatus("No");
        } else {
            //   holder.mCheckbox.setChecked(true);
        }*/
        try {

            holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (b) {
                        data.get(position).setStatus("Yes");
                        Log.e("okk", data.get(position).getBookingId());

                        MyCustomerFrag.customerCancelArray.get(position).setStatus("Yes");

                    } else {
                        data.get(position).setStatus("No");
                        Log.e("disable", "-" + data.get(position).getStatus());
                        MyCustomerFrag.customerCancelArray.get(position).setStatus("No");

                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Finally add item arrays for "A" and "B" to main list with key


    }

    public void selectAll(boolean b) {
        Log.e("onClickSelectAll", "yes");
        if (b) {
            isSelectedAll = true;
            notifyDataSetChanged();
        } else {
            isSelectedAll = false;
            notifyDataSetChanged();

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_customer_name_item_tv)
        public TextView mName;
        @BindView(R.id.serial_no_customer)
        public TextView mSerialNo;

        @BindView(R.id.my_customer_service_item_tv)
        public TextView mService;
        @BindView(R.id.my_customer_est_time_item_tv)
        public TextView mEstimateTime;

        @BindView(R.id.select_cancel_customer_cb)
        public CheckBox mCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

