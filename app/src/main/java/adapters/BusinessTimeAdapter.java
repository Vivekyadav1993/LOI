package adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import frags.AdDetailsEditFrag;
import models.addetailsEdit.Staff;


public class BusinessTimeAdapter extends RecyclerView.Adapter<BusinessTimeAdapter.ViewHolder> {


    private List<String> data;
    private List<String> dataTo;
    private AdDetailsEditFrag mCustomerLandingFrag;
    private List<String> day;

    Context mContext;

    public BusinessTimeAdapter(AdDetailsEditFrag mContext, List<String> data,List<String> dataTo,List<String> day) {
        this.mCustomerLandingFrag = mContext;
        this.data = data;
        this.dataTo = dataTo;
        this.day= day;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_business_time, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mSerialNo.setText(day.get(position).toString());

        if(data.get(position).toString().equalsIgnoreCase("null")){
            holder.mBusinessDay.setText("Off");
            holder.mBusinessDay.setTextColor(Color.RED);
        }else {
            holder.mBusinessDay.setText(data.get(position).toString());
        }
        if(dataTo.get(position).toString().equalsIgnoreCase("null")){
            holder.mBusinessTime.setText("Off");
        }else {
            holder.mBusinessTime.setText(dataTo.get(position).toString());
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.serial_number_business_time)
        public TextView mSerialNo;

        @BindView(R.id.business_day)
        public TextView mBusinessDay;

        @BindView(R.id.business_time)
        public TextView mBusinessTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

