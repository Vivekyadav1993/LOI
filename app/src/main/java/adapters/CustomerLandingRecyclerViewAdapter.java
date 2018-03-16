package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import frags.BusinessDetailsFrag;
import frags.BusinessHourFrag;
import frags.CustomerLandingFrag;
import r2stech.lifeoninternet.R;


public class CustomerLandingRecyclerViewAdapter extends RecyclerView.Adapter<CustomerLandingRecyclerViewAdapter.ViewHolder> {


    private ArrayList<String> data;
    private CustomerLandingFrag mCustomerLandingFrag;
    protected CustomerLandingButtonClick mClick;
    public interface CustomerLandingButtonClick {
        void buttonOptionClick(int pos);

    }
    Context mContext;

    LayoutInflater layoutInflater;

    public CustomerLandingRecyclerViewAdapter(CustomerLandingFrag mContext, ArrayList<String> data,CustomerLandingButtonClick mCustomerLandingButtonClick) {
        this.mCustomerLandingFrag = mContext;
        this.data = data;
        mCustomerLandingButtonClick=mCustomerLandingButtonClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_customer_landing_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.mBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClick.buttonOptionClick(position);
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

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }


}

