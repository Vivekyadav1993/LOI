package adapters;

import android.content.Context;
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
import frags.MyAdsAddressFrag;
import models.businesslist.Output;
import models.myadsaddress.Businessaddress;
import r2stech.lifeoninternet.R;


public class MyAdsAddressAdapter extends RecyclerView.Adapter<MyAdsAddressAdapter.ViewHolder> {


    private ArrayList<Businessaddress> data;
    private MyAdsAddressFrag mMyAdsAddressFrag;
    private MyAdsAddressonClick mClick;


    public interface MyAdsAddressonClick {
        void onClick(int pos , String adddress_id);

    }


    public MyAdsAddressAdapter(MyAdsAddressFrag mContext, ArrayList<Businessaddress> data, MyAdsAddressonClick Click) {
        this.mMyAdsAddressFrag = mContext;
        this.data = data;
        this.mClick = Click;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_ads_address_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            holder.mAddress.setText(data.get(position).getAddress());
            holder.mSerialNo.setText(""+(position+1));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //   Log.d("CLRA", "" + position);
                    mClick.onClick(position,data.get(position).getAddressId().toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

