package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frags.MyAdsAddressFrag;
import frags.MyAppointmentFrag;
import models.MyAppointment;
import models.myadsaddress.Businessaddress;
import models.myadsaddress.Output;
import r2stech.lifeoninternet.R;


public class MyAppointmentAdapter extends RecyclerView.Adapter<MyAppointmentAdapter.ViewHolder> {


    private List<MyAppointment> data;
    private MyAppointmentFrag mMyAppointmentFrag;
    private MyApponClick mClick;


    public interface MyApponClick {
        void onClick(int pos, String adddress_id, String bookingid, String apontmentdate, String Serviceid, String businessid);

    }


    public MyAppointmentAdapter(MyAppointmentFrag mContext, List<MyAppointment> data, MyApponClick Click) {
        this.mMyAppointmentFrag = mContext;
        this.data = data;
        this.mClick = Click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_appointment_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mSerialNo.setText("" + (position + 1) + ".");
        holder.mAddress.setText(data.get(position).businessname);
        holder.mEstimateTime.setText("Date :"+data.get(position).getAppointmentdate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //   Log.d("CLRA", "" + position);
                mClick.onClick(position, data.get(position).getAddressid(), data.get(position).getBookingid(), data.get(position).getAppointmentdate(),
                        data.get(position).getServiceid(), data.get(position).getBusinessid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.my_ads_address_item_tv)
        public TextView mAddress;
        @BindView(R.id.serial_no)
        public TextView mSerialNo;

        @BindView(R.id.my_ads_estimale_time_item_tv)
        public TextView mEstimateTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

