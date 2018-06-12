package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import frags.AdDetailsEditFrag;
import frags.CustomerLandingFrag;
import models.addetailsEdit.Staff;
import models.businesslist.Output;



public class ShowStaffAdapter extends RecyclerView.Adapter<ShowStaffAdapter.ViewHolder> {


    private List<Staff>  data;
    private AdDetailsEditFrag mCustomerLandingFrag;

    Context mContext;
    public ShowStaffAdapter(AdDetailsEditFrag mContext, List<Staff>  data) {
        this.mCustomerLandingFrag = mContext;
        this.data = data;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_show_staff, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mSerialNo.setText(""+(position+1)+".");
        holder.mStaffName.setText(data.get(position).getStaffName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.serial_number_of_staff)
        public TextView mSerialNo;
        @BindView(R.id.staff_name)
        public TextView mStaffName;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

