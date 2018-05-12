package adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import frags.MyAdsAddressFrag;
import frags.MyCustomerFrag;
import models.myadsaddress.Businessaddress;
import models.mycustomer.Booking;
import models.mycustomer.MyCustomer;
import r2stech.lifeoninternet.R;


public class MyCustomerAdapter extends RecyclerView.Adapter<MyCustomerAdapter.ViewHolder> {


    private ArrayList<Booking> data;
    private MyCustomerFrag mMyCustomerFrag;
    private Boolean checked_status;
    private boolean isSelectedAll;

    public MyCustomerAdapter(MyCustomerFrag mContext, ArrayList<Booking> data) {
        this.mMyCustomerFrag = mContext;
        this.data = data;
        Collections.sort(data);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_customers_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mName.setText("Customer Name   :" + data.get(position).getUserName());
        holder.mSerialNo.setText((position + 1) + ".");
        holder.mService.setText("Service                   :" + data.get(position).getServiceName());
        holder.mEstimateTime.setText("Estimate Time      :" + data.get(position).getEstimateTime());

        if(isSelectedAll){
            holder.mCheckbox.setChecked(true);
        }else {
            holder.mCheckbox.setChecked(false);
        }
    }

    public void selectAll(boolean b){
        Log.e("onClickSelectAll","yes");
        if(b) {
            isSelectedAll = true;
            notifyDataSetChanged();
        }else {
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

