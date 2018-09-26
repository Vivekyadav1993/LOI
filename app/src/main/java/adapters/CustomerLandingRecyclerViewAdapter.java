package adapters;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import atw.lifeoninternet.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import frags.BusinessDetailsFrag;
import frags.BusinessHourFrag;
import frags.CustomerLandingFrag;
import models.businesslist.BusinessListModel;
import models.businesslist.Output;


public class CustomerLandingRecyclerViewAdapter extends RecyclerView.Adapter<CustomerLandingRecyclerViewAdapter.ViewHolder> {


    private ArrayList<Output> data;
    private CustomerLandingFrag mCustomerLandingFrag;
    private CustomerLandingButtonClick mClick;

    public interface CustomerLandingButtonClick {
        void onClick(int pos, String business_id, String addredd_id, String name);

    }

    private LayoutInflater layoutInflater;
    private Context mContext;

    public CustomerLandingRecyclerViewAdapter(CustomerLandingFrag mContext, ArrayList<Output> data, CustomerLandingButtonClick Click) {
        this.mCustomerLandingFrag = mContext;
        this.data = data;
        this.mClick = Click;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_customer_landing_row, parent, false);
        return new ViewHolder(v);
    }

    public void filterList(ArrayList<Output> filterdNames) {
        this.data = filterdNames;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String currentString = data.get(position).getAddress();
        String[] separated = currentString.split("<br/>");


        holder.mName.setText(data.get(position).getName().toString());
        String first_add = "", second_add = "";
        try {
            first_add = separated[0];
            second_add = separated[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (second_add.equalsIgnoreCase("") || second_add.equalsIgnoreCase(null)) {
            holder.mAdd.setText(first_add);

        } else {
            holder.mAdd.setText(first_add + "\n" + second_add);
        }


        if (data.get(position).getAppBooking().equalsIgnoreCase("Yes")) {
            holder.mBookBtn.setVisibility(View.VISIBLE);

        } else {
            holder.mBookBtn.setVisibility(View.INVISIBLE);

        }

        Log.d("CLRA", "address" + data.get(0).getAddress() + "currentString" + currentString);
        holder.mService.setText(data.get(position).getIndustry());
        holder.mCompanyOpenTimeTextV.setText(data.get(position).getOpenStatus().toString());
        holder.mOpentime.setText(data.get(position).getOpenTime());
        try {
            Picasso.get()
                    .load(data.get(position).getPic())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  Glide.with(mContext).load(data.get(position).getPic().toString()).into(holder.mImageView);
        if (data.get(position).getOpenStatus().equalsIgnoreCase("Closed Now")) {

            holder.mCompanyOpenTimeTextV.setText(data.get(position).getOpenStatus().toString());
            holder.mCompanyOpenTimeTextV.setBackgroundColor(Color.RED);
            holder.mOpentime.setText(data.get(position).getOpenTime());
            holder.mOpentime.setBackgroundColor(Color.RED);
        } else {
            holder.mCompanyOpenTimeTextV.setText("Open Now");
            holder.mCompanyOpenTimeTextV.setBackgroundColor(Color.parseColor("#2B71FF"));
            holder.mOpentime.setBackgroundColor(Color.parseColor("#2B71FF"));
            holder.mOpentime.setText(data.get(position).getOpenTime());

        }
        holder.mBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.onClick(position, data.get(position).getBusinessId(), data.get(position).getBusinessAddressId(), data.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.company_book_btn)
        public Button mBookBtn;
        @BindView(R.id.company_call_btn)
        public Button mCallBtn;
        @BindView(R.id.company_name_tv)
        public TextView mName;
        @BindView(R.id.company_address_tv)
        public TextView mAdd;
        @BindView(R.id.company_service_tv)
        public TextView mService;
        @BindView(R.id.company_open_time_tv)
        public TextView mOpentime;

        @BindView(R.id.company_image_iv)
        public ImageView mImageView;

        @BindView(R.id.company_open_time_text_tv)
        public TextView mCompanyOpenTimeTextV;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

