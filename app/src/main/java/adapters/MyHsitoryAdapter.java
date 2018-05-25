package adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import frags.CustomerLandingFrag;
import models.History;
import models.businesslist.Output;
import r2stech.lifeoninternet.R;


public class MyHsitoryAdapter extends RecyclerView.Adapter<MyHsitoryAdapter.ViewHolder> {


    private ArrayList<History> data;
    private Context context;
    private CustomerLandingButtonClick mClick;

    public interface CustomerLandingButtonClick {
        void onClick(int pos, String business_id, String addredd_id, String name);

    }

    Context mContext;

    LayoutInflater layoutInflater;

    public MyHsitoryAdapter(Context context, ArrayList<History> data/*, CustomerLandingButtonClick Click*/) {
        this.context = context;
        this.data = data;
        // this.mClick = Click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_history_row, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mName.setText(data.get(position).getBusinessname());
        holder.mService.setText(data.get(position).getServicename());
        holder.mStatus.setText(data.get(position).getStatus());
        holder.mEstimateTime.setText(data.get(position).getEstimatetime());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.company_name_tv)
        public TextView mName;
        @BindView(R.id.company_service_tv)
        public TextView mService;
        @BindView(R.id.company_status_tv)
        public TextView mStatus;
        @BindView(R.id.estimate_time_tv)
        public TextView mEstimateTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

